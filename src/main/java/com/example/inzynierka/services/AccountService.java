package com.example.inzynierka.services;

import com.example.inzynierka.models.Account;
import com.example.inzynierka.payload.RegistrationRequest;

public interface AccountService {
    Account signUp(RegistrationRequest request);

}
