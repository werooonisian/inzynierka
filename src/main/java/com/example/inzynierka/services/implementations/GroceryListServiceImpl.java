package com.example.inzynierka.services.implementations;

import com.example.inzynierka.exceptions.ResourceNotFoundException;
import com.example.inzynierka.models.*;
import com.example.inzynierka.repository.*;
import com.example.inzynierka.services.AccountService;
import com.example.inzynierka.services.GroceryListService;
import io.jsonwebtoken.lang.Assert;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Slf4j
public class GroceryListServiceImpl implements GroceryListService {
    private final static String GROCERY_LIST_NOT_FOUND = "Grocery list with id %s not found";
    private final static String INGREDIENT_NOT_FOUND = "Ingredient with id %s not found";
    private final GroceryListRepository groceryListRepository;
    private final AccountRepository accountRepository;
    private final AccountDetailsRepository accountDetailsRepository;
    private final AccountService accountService;
    private final IngredientRepository ingredientRepository;
    private final IngredientQuantityGroceryListRepository ingredientQuantityGroceryListRepository;
    private final IndividualPantryRepository individualPantryRepository;
    private final FamilyPantryRepository familyPantryRepository;

    public GroceryListServiceImpl(GroceryListRepository groceryListRepository,
                                  AccountRepository accountRepository,
                                  AccountDetailsRepository accountDetailsRepository,
                                  IngredientRepository ingredientRepository,
                                  AccountService accountService,
                                  IngredientQuantityGroceryListRepository ingredientQuantityGroceryListRepository,
                                  IndividualPantryRepository individualPantryRepository,
                                  FamilyPantryRepository familyPantryRepository) {
        this.groceryListRepository = groceryListRepository;
        this.accountRepository = accountRepository;
        this.accountDetailsRepository = accountDetailsRepository;
        this.ingredientRepository = ingredientRepository;
        this.accountService = accountService;
        this.ingredientQuantityGroceryListRepository = ingredientQuantityGroceryListRepository;
        this.individualPantryRepository = individualPantryRepository;
        this.familyPantryRepository = familyPantryRepository;
    }


    @Override
    public GroceryList createGroceryList(String name) {
        GroceryList groceryList = new GroceryList();
        accountRepository.findByLogin(SecurityContextHolder.getContext().getAuthentication().getName())
                .ifPresentOrElse(account -> {
                    groceryList.setName(name);
                    groceryList.getOwners().add(account.getAccountDetails());
                    account.getAccountDetails().getGroceryLists().add(groceryList);
                    groceryListRepository.save(groceryList);
                    accountDetailsRepository.save(account.getAccountDetails());
                    log.info("User with id {} created grocery list with id {}", account.getId(), groceryList.getId());
                },
                        () -> {throw new ResourceNotFoundException("Token not found");});
        return groceryList;
    }

    @Override
    public GroceryList addOwner(long accountId, long groceryListId) {
        Account account = accountRepository.findById(accountId)
                .orElseThrow(() ->
                {throw new ResourceNotFoundException(String.format("Account with id %s not found", accountId));});
        GroceryList groceryList = groceryListRepository.findById(groceryListId)
                .orElseThrow(() ->
                {throw new ResourceNotFoundException(String.format(GROCERY_LIST_NOT_FOUND, groceryListId));});

        if(!groceryList.getOwners().contains(account.getAccountDetails())){
            account.getAccountDetails().getGroceryLists().add(groceryList);
            groceryList.getOwners().add(account.getAccountDetails());

            accountDetailsRepository.save(account.getAccountDetails());
            groceryListRepository.save(groceryList);

            log.info("User with id {} was added to grocery list with id {}", accountId, groceryListId);
        }
        else{ log.info("User with id {} already in grocery list with id {}", accountId, groceryListId);}

        return groceryList;
    }

    @Override
    public void addIngredient(IngredientGroceryListRequest ingredientGroceryListRequest) {
        GroceryList groceryList = verifyAccessToGroceryList(ingredientGroceryListRequest.getGroceryListId());
        ingredientRepository.findById(ingredientGroceryListRequest.getIngredientId()).ifPresentOrElse(ingredient -> {
            if(!groceryList.getIngredientsList().stream().map(IngredientQuantity::getIngredient)
                    .collect(Collectors.toSet()).contains(ingredient)){
                IngredientQuantityGroceryList ingredientQuantityGroceryList =
                        IngredientQuantityGroceryList.builder()
                                .groceryList(groceryList)
                                .ingredient(ingredient)
                                .quantity(ingredientGroceryListRequest.getQuantity())
                                .ingredientUnit(ingredientGroceryListRequest.getIngredientUnit()).build();
                groceryList.getIngredientsList().add(ingredientQuantityGroceryList);
                ingredientQuantityGroceryListRepository.save(ingredientQuantityGroceryList);
                ingredientRepository.save(ingredient);
                groceryListRepository.save(groceryList);
            }},
                () -> { throw new ResourceNotFoundException(String.format(INGREDIENT_NOT_FOUND,
                        ingredientGroceryListRequest.getIngredientId()));});
    }

    @Override
    public void deleteIngredient(long groceryListId, long ingredientQuantityId){
        GroceryList groceryList = verifyAccessToGroceryList(groceryListId);
        ingredientQuantityGroceryListRepository.findById(ingredientQuantityId).ifPresentOrElse(ingredientQuantity -> {
            if(ingredientQuantity.getGroceryList().equals(groceryList)){
                groceryList.getIngredientsList().remove(ingredientQuantity);
                groceryListRepository.save(groceryList);
                ingredientQuantityGroceryListRepository.delete(ingredientQuantity);
                log.info("User {} removed {} from their grocery list",
                        SecurityContextHolder.getContext().getAuthentication().getName(),
                        ingredientQuantity.getIngredient().getName());
            }else{
                log.info(String.format("Ingredient with id %s not found in grocery list", ingredientQuantityId));
            }
        }, () -> { throw new ResourceNotFoundException(String.format(INGREDIENT_NOT_FOUND, ingredientQuantityId));}
        );
    }

    @Override
    public Set<Ingredient> getAllIngredients(long id) {
        GroceryList groceryList = verifyAccessToGroceryList(id);
        return groceryList.getIngredientsList().stream().map(IngredientQuantity::getIngredient)
                .collect(Collectors.toSet());
    }

    @Override
    public void deleteGroceryList(long id) {
        GroceryList groceryList = verifyAccessToGroceryList(id);
        Account account = accountService.getPrincipal();
        AccountDetails accountDetails = accountDetailsRepository.getReferenceById(account.getId());
        accountDetails.getGroceryLists().remove(groceryList);
        groceryList.getOwners().remove(accountDetails);
        groceryListRepository.save(groceryList);
        accountDetailsRepository.save(accountDetails);
        log.info(String.format("Grocery list with id %s was deleted from user %s", groceryList.getId(), account.getLogin()));
        if(groceryList.getOwners().isEmpty()){
            groceryListRepository.delete(groceryList);
            log.info(String.format("Grocery list with id %s was deleted indefinitely", groceryList.getId()));
        }
    }

    @Override
    public Set<GroceryList> getAllMyGroceryLists() {
        return accountService.getPrincipal().getAccountDetails().getGroceryLists();
    }

    @Override
    public Set<Account> getOwners(long id) {
        GroceryList groceryList = verifyAccessToGroceryList(id);
        Set<Account> accounts = new HashSet<>();
        groceryList.getOwners().forEach(accountDetails -> accounts.add(accountDetails.getAccount()));
        return accounts;
    }

    @Override
    public void moveIngredientToIndividualPantry(long ingredientId, long groceryListId) {
        verifyAccessToGroceryList(groceryListId);
        ingredientRepository.findById(ingredientId).ifPresentOrElse(ingredient -> {
            findGroceryListAndRemoveIngredient(ingredient, groceryListId);
            IndividualPantry individualPantry = accountService.getPrincipal().getAccountDetails().getIndividualPantry();
            individualPantry.getPantry().add(ingredient);
            individualPantryRepository.save(individualPantry);
            ingredientRepository.save(ingredient);
        }, () -> {throw new ResourceNotFoundException(String.format(INGREDIENT_NOT_FOUND, ingredientId));});
    }

    @Override
    public void moveIngredientToFamilyPantry(long ingredientId, long groceryListId) {
        verifyAccessToGroceryList(groceryListId);
        if(accountService.getPrincipal().getAccountDetails().getFamilyPantry() == null ){
            throw new ResourceNotFoundException("Logged in user does not have family pantry");
        }
        ingredientRepository.findById(ingredientId).ifPresentOrElse(ingredient -> {
            findGroceryListAndRemoveIngredient(ingredient, groceryListId);
            FamilyPantry familyPantry = accountService.getPrincipal().getAccountDetails().getFamilyPantry();
            familyPantry.getPantry().add(ingredient);
            familyPantryRepository.save(familyPantry);
            ingredientRepository.save(ingredient);
        },() -> {throw new ResourceNotFoundException(String.format(INGREDIENT_NOT_FOUND, ingredientId));});
    }

    private GroceryList verifyAccessToGroceryList(long groceryListId){
        Account account = accountService.getPrincipal();
        Assert.isTrue(accountDetailsRepository.getReferenceById(account.getId()).getGroceryLists()
                .stream().map(GroceryList::getId).collect(Collectors.toList()).contains(groceryListId),
                String.format("User doesn't have access to grocery list with id %s", groceryListId));
        return groceryListRepository.findById(groceryListId).orElseThrow(
                () -> new ResourceNotFoundException(String.format(GROCERY_LIST_NOT_FOUND, groceryListId))
        );
    }

    private void findGroceryListAndRemoveIngredient(Ingredient ingredient, long groceryListId){
        groceryListRepository.findById(groceryListId).ifPresentOrElse(groceryList -> {
            IngredientQuantityGroceryList ingredientQuantity =
                    ingredientQuantityGroceryListRepository.findByIngredientAndGroceryList(ingredient, groceryList)
                            .orElseThrow(() -> {throw new ResourceNotFoundException("Ingredient quantity not found");});
            groceryList.getIngredientsList().remove(ingredientQuantity);
            groceryListRepository.save(groceryList);
            ingredientQuantityGroceryListRepository.delete(ingredientQuantity);
        }, () -> {throw new ResourceNotFoundException(String.format(GROCERY_LIST_NOT_FOUND, groceryListId));});
    }
}
