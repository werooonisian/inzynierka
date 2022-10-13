package com.example.inzynierka.services.implementations;

import com.example.inzynierka.models.Recipe;
import com.example.inzynierka.repository.AccountRepository;
import com.example.inzynierka.repository.RecipeRepository;
import com.example.inzynierka.services.AccountService;
import com.example.inzynierka.services.RecipeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class RecipeServiceImpl implements RecipeService {
    private final RecipeRepository recipeRepository;
    private final AccountRepository accountRepository;

    public RecipeServiceImpl(RecipeRepository recipeRepository, AccountService accountService, AccountRepository accountRepository) {
        this.recipeRepository = recipeRepository;
        this.accountRepository = accountRepository;
    }

    @Override
    public Recipe addRecipe(Recipe recipe) {
        //TODO: pobawić się w obsługę optionala :)
        accountRepository.findByLogin(SecurityContextHolder.getContext().getAuthentication().getName())
                .ifPresentOrElse(account -> {
                    recipe.setAddedBy(account.getAccountPreferences());
                    recipeRepository.save(recipe);
                    log.info("Recipe has been added by" + account.getLogin());
                },
                        () -> {throw new IllegalStateException("Token not found");});

        return recipe;
    }
}
