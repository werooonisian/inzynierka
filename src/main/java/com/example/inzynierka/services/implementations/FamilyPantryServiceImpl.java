package com.example.inzynierka.services.implementations;

import com.example.inzynierka.exceptions.PantryCreationException;
import com.example.inzynierka.exceptions.ResourceNotFoundException;
import com.example.inzynierka.models.*;
import com.example.inzynierka.repository.AccountDetailsRepository;
import com.example.inzynierka.repository.FamilyPantryRepository;
import com.example.inzynierka.repository.IngredientRepository;
import com.example.inzynierka.services.AccountDetailsService;
import com.example.inzynierka.services.AccountService;
import com.example.inzynierka.services.FamilyPantryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.Set;
import java.util.stream.Collectors;

@Service
@Slf4j
public class FamilyPantryServiceImpl implements FamilyPantryService {
    private final FamilyPantryRepository familyPantryRepository;
    private final AccountDetailsService accountDetailsService;
    private final AccountDetailsRepository accountDetailsRepository;
    private final IngredientRepository ingredientRepository;
    private final AccountService accountService;

    public FamilyPantryServiceImpl(FamilyPantryRepository familyPantryRepository,
                                   AccountDetailsService accountDetailsService,
                                   AccountDetailsRepository accountDetailsRepository,
                                   IngredientRepository ingredientRepository,
                                   AccountService accountService) {
        this.familyPantryRepository = familyPantryRepository;
        this.accountDetailsService = accountDetailsService;
        this.accountDetailsRepository = accountDetailsRepository;
        this.ingredientRepository = ingredientRepository;
        this.accountService = accountService;
    }

    @Override
    public FamilyPantry createFamilyPantry() {
        AccountDetails accountDetails = accountDetailsService.getPrincipalsDetails();
        if(accountDetails.getFamilyPantry() != null)
        {
            throw new PantryCreationException("Logged in user already has family pantry");
        }
        FamilyPantry familyPantry = new FamilyPantry();
        accountDetails.setFamilyPantry(familyPantry);
        familyPantry.getOwners().add(accountDetails);
        familyPantryRepository.save(familyPantry);
        accountDetailsRepository.save(accountDetails);
        return familyPantry;
    }

    @Override
    public Set<Ingredient> getIngredients() throws NullPointerException{
        return verifyAccessToFamilyPantry().getPantry();
    }

    @Override
    public Set<Account> getOwners() {
        return verifyAccessToFamilyPantry().getOwners().stream()
                .map(accountDetails -> accountDetails.getAccount()).collect(Collectors.toSet());
    }

    @Override
    public void addIngredient(long ingredientId) {
        try {
            FamilyPantry familyPantry = accountDetailsService.getPrincipalsDetails().getFamilyPantry();
            Ingredient ingredient = ingredientRepository.findById(ingredientId).orElseThrow(
                    () -> {throw new ResourceNotFoundException(String.format("Ingredient with id %s not found", ingredientId));});
            familyPantry.getPantry().add(ingredient);
            ingredient.getPresentInPantries().add(familyPantry);
            familyPantryRepository.save(familyPantry);
            ingredientRepository.save(ingredient);
        }
        catch(NullPointerException exc){
            throw new ResourceNotFoundException("Logged in user does not have family pantry");
        }
    }

    @Override
    public void deleteIngredient(long ingredientId) {
        FamilyPantry familyPantry = verifyAccessToFamilyPantry();
        ingredientRepository.findById(ingredientId).ifPresentOrElse(ingredient -> {
                    if(!ingredient.getPresentInPantries().contains(familyPantry)){
                        log.info(String.format("Ingredient with id %s not found in family pantry", ingredientId));
                        return;
                    }
                    ingredient.getPresentInPantries().remove(familyPantry);
                    familyPantry.getPantry().remove(ingredient);
                    familyPantryRepository.save(familyPantry);
                    ingredientRepository.save(ingredient);
                    log.info("User {} removed {} from their family pantry",
                            SecurityContextHolder.getContext().getAuthentication().getName(), ingredient.getName());},
                () -> { throw new ResourceNotFoundException(String.format("Ingredient with id %s not found", ingredientId));}
        );
    }

    private FamilyPantry verifyAccessToFamilyPantry(){
        try{
            return accountDetailsService.getPrincipalsDetails().getFamilyPantry();
        }
        catch (NullPointerException exc){
            throw new ResourceNotFoundException("Logged in user does not have family pantry");
        }
    }
}
