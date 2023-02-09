package com.example.inzynierka.services;

import com.example.inzynierka.models.Account;
import com.example.inzynierka.models.GroceryList;
import com.example.inzynierka.models.Ingredient;
import com.example.inzynierka.models.IngredientGroceryListRequest;

import java.util.List;
import java.util.Set;

public interface GroceryListService{
    GroceryList createGroceryList(String name);
    GroceryList addOwner(long accountId, long groceryListId);
    void addIngredient(IngredientGroceryListRequest ingredientGroceryListRequest);
    void deleteIngredient(long groceryListId, long ingredientQuantityId);
    Set<Ingredient> getAllIngredients(long id);
    void deleteGroceryList(long id);
    Set<GroceryList> getAllMyGroceryLists();
    Set<Account> getOwners(long id);
    void moveIngredientToIndividualPantry(long ingredientId, long groceryListId);
    void moveIngredientToFamilyPantry(long ingredientId, long groceryListId);
}
