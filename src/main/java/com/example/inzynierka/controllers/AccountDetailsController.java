package com.example.inzynierka.controllers;

import com.example.inzynierka.models.Recipe;
import com.example.inzynierka.services.AccountPreferencesService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Set;

@RestController("/accountPreferences")
public class AccountPreferencesController {
    private final AccountPreferencesService accountPreferencesService;

    public AccountPreferencesController(AccountPreferencesService accountPreferencesService) {
        this.accountPreferencesService = accountPreferencesService;
    }

    @GetMapping("/myRecipes")
    public ResponseEntity<Set<Recipe>> getMyRecipes(){
        return ResponseEntity.ok().body(accountPreferencesService.getMyRecipes());
    }
}
