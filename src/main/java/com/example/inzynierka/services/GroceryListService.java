package com.example.inzynierka.services;

import com.example.inzynierka.models.Account;
import com.example.inzynierka.models.GroceryList;
import com.example.inzynierka.models.Ingredient;

import java.util.List;
import java.util.Set;

public interface GroceryListService{
    GroceryList createGroceryList(String name);
    GroceryList addOwner(long accountId, long groceryListId);
    Ingredient addIngredient(long groceryListId, long ingredientId);
    Ingredient deleteIngredient(long groceryListId, long ingredientId);
    Set<Ingredient> getAllIngredients(long id);
    void deleteGroceryList(long id);
    Set<GroceryList> getAllMyGroceryLists();
    Set<Account> getOwners(long id);
}
