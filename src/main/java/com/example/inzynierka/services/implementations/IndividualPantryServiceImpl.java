package com.example.inzynierka.services.implementations;

import com.example.inzynierka.exceptions.ResourceNotFoundException;
import com.example.inzynierka.models.Account;
import com.example.inzynierka.models.IndividualPantry;
import com.example.inzynierka.models.Ingredient;
import com.example.inzynierka.repository.AccountPreferencesRepository;
import com.example.inzynierka.repository.AccountRepository;
import com.example.inzynierka.repository.IndividualPantryRepository;
import com.example.inzynierka.repository.IngredientRepository;
import com.example.inzynierka.services.IndividualPantryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

@Slf4j
@Service
public class IndividualPantryServiceImpl implements IndividualPantryService {
    private final IndividualPantryRepository individualPantryRepository;
    private final IngredientRepository ingredientRepository;
    private final AccountPreferencesRepository accountPreferencesRepository;
    private final AccountRepository accountRepository;

    public IndividualPantryServiceImpl(IndividualPantryRepository individualPantryRepository,
                                       IngredientRepository ingredientRepository,
                                       AccountPreferencesRepository accountPreferencesRepository,
                                       AccountRepository accountRepository) {
        this.individualPantryRepository = individualPantryRepository;
        this.ingredientRepository = ingredientRepository;
        this.accountPreferencesRepository = accountPreferencesRepository;
        this.accountRepository = accountRepository;
    }

    @Override
    public Ingredient addIngredient(long individualPantryId, long ingredientId) {
        IndividualPantry individualPantry = verifyAccessToIndividualPantry(individualPantryId);
        ingredientRepository.findById(ingredientId).ifPresentOrElse(ingredient -> {
            ingredient.getPresentInPantries().add(individualPantry);
            individualPantry.getPantry().add(ingredient);
            individualPantryRepository.save(individualPantry);
            ingredientRepository.save(ingredient);
            log.info("User {} added {} to their individual pantry",
                    SecurityContextHolder.getContext().getAuthentication().getName(), ingredient.getName());},
                () -> { throw new ResourceNotFoundException(String.format("Ingredient with id %s not found", ingredientId));}
        );
        return null; //TODO: zwracać czy nie zwracać, oto jest pytanie
    }

    @Override
    public Ingredient deleteIngredient(long individualPantryId, long ingredientId) {
        IndividualPantry individualPantry = verifyAccessToIndividualPantry(individualPantryId);
        ingredientRepository.findById(ingredientId).ifPresentOrElse(ingredient -> {
                    ingredient.getPresentInPantries().remove(individualPantry);
                    individualPantry.getPantry().remove(ingredient);
                    individualPantryRepository.save(individualPantry);
                    ingredientRepository.save(ingredient);
                    log.info("User {} removed {} from their individual pantry",
                            SecurityContextHolder.getContext().getAuthentication().getName(), ingredient.getName());},
                () -> { throw new ResourceNotFoundException(String.format("Ingredient with id %s not found", ingredientId));}
        );
        return null; //TODO: XDDDD
    }

    private IndividualPantry verifyAccessToIndividualPantry(long individualPantryId){
        Account account = accountRepository
                .findByLogin(SecurityContextHolder.getContext().getAuthentication().getName())
                .orElseThrow(() -> {
                    throw new ResourceNotFoundException("Token not found");
                });
        Assert.isTrue(accountPreferencesRepository.getReferenceById(
                        account.getId()).getIndividualPantry().getId()==individualPantryId,
                String.format("User doesn't have access to pantry with id %s", individualPantryId));
        return individualPantryRepository.findById(individualPantryId).orElseThrow(
                () -> new ResourceNotFoundException(String.format("Individual pantry with id %s not found", individualPantryId))
        );
    }
}
