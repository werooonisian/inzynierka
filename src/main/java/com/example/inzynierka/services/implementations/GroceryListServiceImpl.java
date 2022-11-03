package com.example.inzynierka.services.implementations;

import com.example.inzynierka.exceptions.AccountNotFoundException;
import com.example.inzynierka.exceptions.GroceryListNotFound;
import com.example.inzynierka.exceptions.ResourceNotFoundException;
import com.example.inzynierka.models.Account;
import com.example.inzynierka.models.GroceryList;
import com.example.inzynierka.repository.AccountPreferencesRepository;
import com.example.inzynierka.repository.AccountRepository;
import com.example.inzynierka.repository.GroceryListRepository;
import com.example.inzynierka.services.GroceryListService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class GroceryListServiceImpl implements GroceryListService {

    private final GroceryListRepository groceryListRepository;
    private final AccountRepository accountRepository;
    private final AccountPreferencesRepository accountPreferencesRepository;

    public GroceryListServiceImpl(GroceryListRepository groceryListRepository,
                                  AccountRepository accountRepository,
                                  AccountPreferencesRepository accountPreferencesRepository) {
        this.groceryListRepository = groceryListRepository;
        this.accountRepository = accountRepository;
        this.accountPreferencesRepository = accountPreferencesRepository;
    }


    @Override
    public GroceryList createGroceryList(String name) {
        GroceryList groceryList = new GroceryList();
        accountRepository.findByLogin(SecurityContextHolder.getContext().getAuthentication().getName())
                .ifPresentOrElse(account -> {
                    groceryList.setName(name);
                    groceryList.getOwners().add(account.getAccountPreferences());
                    account.getAccountPreferences().getGroceryLists().add(groceryList);
                    groceryListRepository.save(groceryList);
                    accountPreferencesRepository.save(account.getAccountPreferences());
                    log.info("User with id {} created grocery list with id {}", account.getId(), groceryList.getId());
                },
                        () -> {throw new ResourceNotFoundException("Token not found");});
        return groceryList;
    }

    @Override
    public GroceryList addOwner(long accountId, long groceryListId) {
        Account account = accountRepository.findById(accountId)
                .orElseThrow(() -> {throw new ResourceNotFoundException(String.format("Account with id %s not found", accountId));});
        GroceryList groceryList = groceryListRepository.findById(groceryListId)
                .orElseThrow(() -> {throw new ResourceNotFoundException("Grocery list not found");});

        if(!groceryList.getOwners().contains(account.getAccountPreferences())){
            account.getAccountPreferences().getGroceryLists().add(groceryList);
            groceryList.getOwners().add(account.getAccountPreferences());

            accountPreferencesRepository.save(account.getAccountPreferences());
            groceryListRepository.save(groceryList);

            log.info("User with id {} was added to grocery list with id {}", accountId, groceryListId);
        }
        else{ log.info("User with id {} already in grocery list with id {}", accountId, groceryListId);}

        return groceryList;
    }
}
