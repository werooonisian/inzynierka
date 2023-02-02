package com.example.inzynierka.services;

import com.example.inzynierka.models.Account;
import com.example.inzynierka.models.FamilyPantry;

import java.util.Set;

public interface FamilyPantryService {
    FamilyPantry createFamilyPantry();
    FamilyPantry getIngredients();
    Set<Account> getOwners();
    void addIngredient(long ingredientId);
    void deleteIngredient(long ingredientId);
    void sendInvitation(long userId);
    void acceptInvitation(String token);
    void leaveFamilyPantry();
    void moveToMyIndividualPantry(long ingredientId);
}
