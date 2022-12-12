package com.example.inzynierka.services.implementations;

import com.example.inzynierka.exceptions.ResourceNotFoundException;
import com.example.inzynierka.models.*;
import com.example.inzynierka.repository.AccountDetailsRepository;
import com.example.inzynierka.repository.AccountRepository;
import com.example.inzynierka.repository.GroceryListRepository;
import com.example.inzynierka.repository.IngredientRepository;
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

    private final GroceryListRepository groceryListRepository;
    private final AccountRepository accountRepository;
    private final AccountDetailsRepository accountDetailsRepository;
    private final AccountService accountService;
    private final IngredientRepository ingredientRepository;

    public GroceryListServiceImpl(GroceryListRepository groceryListRepository,
                                  AccountRepository accountRepository,
                                  AccountDetailsRepository accountDetailsRepository,
                                  IngredientRepository ingredientRepository,
                                  AccountService accountService) {
        this.groceryListRepository = groceryListRepository;
        this.accountRepository = accountRepository;
        this.accountDetailsRepository = accountDetailsRepository;
        this.ingredientRepository = ingredientRepository;
        this.accountService = accountService;
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
                .orElseThrow(() -> {throw new ResourceNotFoundException(String.format("Account with id %s not found", accountId));});
        GroceryList groceryList = groceryListRepository.findById(groceryListId)
                .orElseThrow(() -> {throw new ResourceNotFoundException("Grocery list not found");});

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
    public Ingredient addIngredient(long groceryListId, long ingredientId) {
        GroceryList groceryList = verifyAccessToGroceryList(groceryListId);
        ingredientRepository.findById(ingredientId).ifPresentOrElse(ingredient -> {
                    if(!ingredient.getPresentInGroceryLists().contains(groceryList)) {
                        ingredient.getPresentInGroceryLists().add(groceryList);
                        groceryList.getIngredientsList().add(ingredient);
                        groceryListRepository.save(groceryList);
                        ingredientRepository.save(ingredient);
                        log.info("User {} added {} to their grocery list",
                                SecurityContextHolder.getContext().getAuthentication().getName(), ingredient.getName());}
                    else{log.info("Ingredient {} already present in grocery list {}",
                            ingredient.getName(), groceryList.getId());}
                    },
                () -> { throw new ResourceNotFoundException(String.format("Ingredient with id %s not found", ingredientId));});
        return null;
    }

    @Override
    public Ingredient deleteIngredient(long groceryListId, long ingredientId) {
        GroceryList groceryList = verifyAccessToGroceryList(groceryListId);
        ingredientRepository.findById(ingredientId).ifPresentOrElse(ingredient -> {
                    if(!ingredient.getPresentInGroceryLists().contains(groceryList)){
                        log.info(String.format("Ingredient with id %s not found in grocery list", ingredientId));
                        return;
                    }
                    ingredient.getPresentInGroceryLists().remove(groceryList);
                    groceryList.getIngredientsList().remove(ingredient);
                    groceryListRepository.save(groceryList);
                    ingredientRepository.save(ingredient);
                    log.info("User {} removed {} from their grocery list",
                            SecurityContextHolder.getContext().getAuthentication().getName(), ingredient.getName());},
                () -> { throw new ResourceNotFoundException(String.format("Ingredient with id %s not found", ingredientId));}
        );
        return null;
    }

    @Override
    public Set<Ingredient> getAllIngredients(long id) {
        GroceryList groceryList = verifyAccessToGroceryList(id);
        return new HashSet<>(groceryList.getIngredientsList());
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

    private GroceryList verifyAccessToGroceryList(long groceryListId){
        Account account = accountService.getPrincipal();
        Assert.isTrue(accountDetailsRepository.getReferenceById(account.getId()).getGroceryLists()
                .stream().map(GroceryList::getId).collect(Collectors.toList()).contains(groceryListId),
                String.format("User doesn't have access to grocery list with id %s", groceryListId));
        return groceryListRepository.findById(groceryListId).orElseThrow(
                () -> new ResourceNotFoundException(String.format("Grocery list with id %s not found", groceryListId))
        );
    }
}
