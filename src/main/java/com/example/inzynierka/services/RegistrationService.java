package com.example.inzynierka.services;

import com.example.inzynierka.models.Account;
import com.example.inzynierka.payload.RegistrationRequest;

public interface RegistrationService {
    Account register(RegistrationRequest request);
    String confirmToken(String token);
}
