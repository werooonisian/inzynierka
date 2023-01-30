package com.example.inzynierka.services.implementations;

import com.example.inzynierka.exceptions.ResourceNotFoundException;
import com.example.inzynierka.models.*;
import com.example.inzynierka.repository.AccountDetailsRepository;
import com.example.inzynierka.repository.IngredientRepository;
import com.example.inzynierka.services.AccountDetailsService;
import com.example.inzynierka.services.AccountService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
@Slf4j
public class AccountDetailsServiceImpl implements AccountDetailsService {
    private final AccountService accountService;
    private final AccountDetailsRepository accountDetailsRepository;
    private final IngredientRepository ingredientRepository;

    public AccountDetailsServiceImpl(AccountService accountService,
                                     AccountDetailsRepository accountDetailsRepository,
                                     IngredientRepository ingredientRepository) {
        this.accountService = accountService;
        this.accountDetailsRepository = accountDetailsRepository;
        this.ingredientRepository = ingredientRepository;
    }
    @Override
    public AccountDetails getPrincipalsDetails(){
        Account account = accountService.getPrincipal();
        return accountDetailsRepository.getReferenceById(account.getId());
    }

    @Override
    public Set<Recipe> getMyRecipes() {
        return getPrincipalsDetails().getAddedRecipes();
    }

    @Override
    public Set<Recipe> getFavouriteRecipes() {
        return getPrincipalsDetails().getFavouriteRecipes();
    }

    @Override
    public Set<Ingredient> getAvoidedIngredients() {
        return getPrincipalsDetails().getAvoidedIngredients();
    }

    @Override
    public Set<DietType> getMyDiets() {
        return getPrincipalsDetails().getDietTypes();
    }

    @Override
    public void addIngredientToAvoided(Long id) {
        AccountDetails accountDetails = getPrincipalsDetails();
        ingredientRepository.findById(id).ifPresentOrElse(ingredient -> {
                accountDetails.getAvoidedIngredients().add(ingredient);
                ingredient.getAvoidedBy().add(accountDetails);
                accountDetailsRepository.save(accountDetails);
                ingredientRepository.save(ingredient);
                log.info("Ingredient {} was added to avoided ingredients", ingredient.getName());},
                () -> {throw new ResourceNotFoundException(String.format("Ingredient with id %s not found", id));});
    }

    @Override
    public void deleteIngredientFromAvoided(Long id) {
        AccountDetails accountDetails = getPrincipalsDetails();
        ingredientRepository.findById(id).ifPresentOrElse(ingredient -> {
            if(accountDetails.getAvoidedIngredients().contains(ingredient)){
                accountDetails.getAvoidedIngredients().remove(ingredient);
                accountDetailsRepository.save(accountDetails);
                }
            if(ingredient.getAvoidedBy().contains(accountDetails)){
                ingredient.getAvoidedBy().remove(accountDetails);
                ingredientRepository.save(ingredient);
                log.info("Ingredient {} was removed from avoided ingredients", ingredient.getName());
            }
            },
                () -> {throw new ResourceNotFoundException(String.format("Ingredient with id %s not found", id));});
    }

    @Override
    public Set<GroceryList> getAllMyGroceryLists() {
        return getPrincipalsDetails().getGroceryLists();
    }

    @Override
    public DietType addDietToMyDiets(String dietType) {
        AccountDetails accountDetails = getPrincipalsDetails();
        DietType diet = DietType.valueOf(dietType);
        accountDetails.getDietTypes().add(diet);
        accountDetailsRepository.save(accountDetails);
        return diet;
    }

    @Override
    public void deleteDietFromMyDiets(String dietType) {
        AccountDetails accountDetails = getPrincipalsDetails();
        DietType diet = DietType.valueOf(dietType);
        accountDetails.getDietTypes().remove(diet);
        accountDetailsRepository.save(accountDetails);
    }
}
