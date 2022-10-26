package com.example.inzynierka.services;

import com.example.inzynierka.models.Account;
import com.example.inzynierka.models.GroceryList;

import java.util.List;

public interface GroceryListService{
    GroceryList createGroceryList(String name);
    GroceryList addOwner(long accountId, long groceryListId);
}
