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
    public AccountDetails getPrincipalsDetails(){
        Account account = accountService.getPrincipal();
        return accountDetailsRepository.getReferenceById(account.getId());
    }

    @Override
    public void addIngredientToAvoided(Long id) {
        AccountDetails accountDetails = getPrincipalsDetails();
        ingredientRepository.findById(id).ifPresentOrElse(ingredient -> {
                accountDetails.getAvoidedIngredients().add(ingredient);
                log.info("Ingredient {} was added to avoided ingredients", ingredient.getName());},
                () -> {throw new ResourceNotFoundException(String.format("Ingredient with id %s not found", id));});
    }
}
