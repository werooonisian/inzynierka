package com.example.inzynierka.controllers;

import com.example.inzynierka.models.Account;
import com.example.inzynierka.payload.RegistrationRequest;
import com.example.inzynierka.services.RegistrationService;
import org.springframework.web.bind.annotation.*;

@RestController
public class RegistrationController {

    private final RegistrationService registrationService;

    public RegistrationController(RegistrationService registrationService) {
        this.registrationService = registrationService;
    }

    @PostMapping("/registration")
    public Account register(@RequestBody RegistrationRequest request) {
        return registrationService.register(request);
    }

    @GetMapping("/registration/confirm")
    public String confirm(@RequestParam("token") String token){
        return registrationService.confirmToken(token);
    }
}
