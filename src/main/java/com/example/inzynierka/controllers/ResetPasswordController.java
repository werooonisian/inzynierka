package com.example.inzynierka.controllers;

import com.example.inzynierka.models.PasswordResetToken;
import com.example.inzynierka.models.Recipe;
import com.example.inzynierka.services.AccountService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class ResetPasswordController {
    private final AccountService accountService;

    public ResetPasswordController(AccountService accountService) {
        this.accountService = accountService;
    }

    @PostMapping("/sendEmailToResetPassword/{email}")
    public void sendEmailToResetPassword(@PathVariable String email){
        accountService.sendEmailToResetPassword(email);
    }

    @PostMapping("/resetPassword/{token}")
    public void resetPassword(@PathVariable String token,
                              @RequestBody String newPassword){
        accountService.resetPassword(newPassword,token);
    }

    @GetMapping("/validateResetPasswordToken/{token}")
    public ResponseEntity<PasswordResetToken> validateResetPasswordToken(@PathVariable String token){
        return ResponseEntity.ok().body(accountService.validateResetPasswordToken(token));
    }

}
