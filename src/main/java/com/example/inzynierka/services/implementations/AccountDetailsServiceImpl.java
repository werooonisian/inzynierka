package com.example.inzynierka.services.implementations;

import com.example.inzynierka.models.Account;
import com.example.inzynierka.models.AccountPreferences;
import com.example.inzynierka.models.Recipe;
import com.example.inzynierka.repository.AccountPreferencesRepository;
import com.example.inzynierka.services.AccountPreferencesService;
import com.example.inzynierka.services.AccountService;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class AccountPreferencesServiceImpl implements AccountPreferencesService {
    private final AccountService accountService;
    private final AccountPreferencesRepository accountPreferencesRepository;

    public AccountPreferencesServiceImpl(AccountService accountService,
                                         AccountPreferencesRepository accountPreferencesRepository) {
        this.accountService = accountService;
        this.accountPreferencesRepository = accountPreferencesRepository;
    }

    @Override
    public Set<Recipe> getMyRecipes() {
        Account account = accountService.getPrincipal();
        AccountPreferences accountPreferences = accountPreferencesRepository.getReferenceById(account.getId());
        return accountPreferences.getAddedRecipes();
    }
}
