package com.example.inzynierka.services;

import com.example.inzynierka.models.*;

import java.util.Set;

public interface AccountDetailsService {
    Set<Recipe> getMyRecipes();
    Set<Recipe> getFavouriteRecipes();
    Set<Ingredient> getAvoidedIngredients();
    Set<DietType> getMyDiets();
    AccountDetails getPrincipalsDetails();
    void addIngredientToAvoided(Long id);
    void deleteIngredientFromAvoided(Long id);
    Set<GroceryList> getAllMyGroceryLists();
    DietType addDietToMyDiets(String dietType);
    void deleteDietFromMyDiets(String dietType);
    boolean isRecipeInFavourited(Long recipeId);
}
