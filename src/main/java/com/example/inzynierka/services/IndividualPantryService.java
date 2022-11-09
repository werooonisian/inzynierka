package com.example.inzynierka.services;

import com.example.inzynierka.models.Ingredient;

import java.util.Set;

public interface IndividualPantryService {
    Ingredient addIngredient(long individualPantryId, long ingredientId);
    Ingredient deleteIngredient(long individualPantryId, long ingredientId);
    Set<Ingredient> getAllIngredients();
}
