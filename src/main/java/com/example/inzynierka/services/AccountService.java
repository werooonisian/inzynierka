package com.example.inzynierka.services;

import com.example.inzynierka.models.Account;
import com.example.inzynierka.payload.RegistrationRequest;

import java.util.List;

public interface AccountService {
    Account signUp(RegistrationRequest request);
    List<Account> findAccounts(String searchPhrase);
    Account getPrincipal();

}
