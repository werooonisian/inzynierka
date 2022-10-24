package com.example.inzynierka.services.implementations;

import com.example.inzynierka.models.GroceryList;
import com.example.inzynierka.repository.AccountRepository;
import com.example.inzynierka.repository.GroceryListRepository;
import com.example.inzynierka.services.GroceryListService;
import org.springframework.stereotype.Service;

@Service
public class GroceryListServiceImpl implements GroceryListService {

    private final GroceryListRepository groceryListRepository;
    private final AccountRepository accountRepository;

    public GroceryListServiceImpl(GroceryListRepository groceryListRepository, AccountRepository accountRepository) {
        this.groceryListRepository = groceryListRepository;
        this.accountRepository = accountRepository;
    }


    @Override
    public GroceryList createGroceryList(GroceryList groceryList) {
        return null;
    }
}
