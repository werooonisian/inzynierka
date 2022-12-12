package com.example.inzynierka.controllers;

import com.example.inzynierka.models.Account;
import com.example.inzynierka.services.AccountService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/account")
public class AccountController {

    private final AccountService accountService;

    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @GetMapping("/search")
    public ResponseEntity<List<Account>> searchAccounts(@RequestBody String searchPhrase){
        return ResponseEntity.ok().body(accountService.findAccounts(searchPhrase));
    }
    @GetMapping
    public ResponseEntity<Account> getMyAccount(){
        return ResponseEntity.ok().body(accountService.getPrincipal());
    }
}
