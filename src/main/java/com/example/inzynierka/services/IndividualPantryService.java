package com.example.inzynierka.services;

import com.example.inzynierka.models.Ingredient;

public interface IndividualPantryService {
    Ingredient addIngredient(long individualPantryId, long ingredientId);
    Ingredient deleteIngredient(long individualPantryId, long ingredientId);
}
