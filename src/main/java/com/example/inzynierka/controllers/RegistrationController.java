package com.example.inzynierka.controllers;

import com.example.inzynierka.models.Account;
import com.example.inzynierka.payload.RegistrationRequest;
import com.example.inzynierka.services.implementations.RegistrationServiceImpl;
import org.springframework.web.bind.annotation.*;

@RestController("/registration")
public class RegistrationController {

    private final RegistrationServiceImpl registrationService;

    public RegistrationController(RegistrationServiceImpl registrationService) {
        this.registrationService = registrationService;
    }

    @PostMapping("/registration")
    public Account register(@RequestBody RegistrationRequest request) {
        return registrationService.register(request);
    }

    @GetMapping("/confirm")
    public String confirm(@RequestParam("token") String token){
        return registrationService.confirmToken(token);
    }
}
