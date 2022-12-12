package com.example.inzynierka.services;

import com.example.inzynierka.models.AccountDetails;
import com.example.inzynierka.models.DietType;
import com.example.inzynierka.models.Ingredient;
import com.example.inzynierka.models.Recipe;

import java.util.Set;

public interface AccountDetailsService {
    Set<Recipe> getMyRecipes();
    Set<Recipe> getFavouriteRecipes();
    Set<Ingredient> getAvoidedIngredients();
    Set<DietType> getMyDiets();
    AccountDetails getPrincipalsDetails();
    void addIngredientToAvoided(Long id);
}
