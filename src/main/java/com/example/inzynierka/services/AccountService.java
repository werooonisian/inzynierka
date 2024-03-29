package com.example.inzynierka.services;

import com.example.inzynierka.models.Account;
import com.example.inzynierka.payload.ChangePasswordRequest;
import com.example.inzynierka.payload.EditAccountRequest;
import com.example.inzynierka.models.PasswordResetToken;
import com.example.inzynierka.payload.RegistrationRequest;

import java.util.List;

public interface AccountService {
    Account signUp(RegistrationRequest request);
    List<Account> findAccounts(String searchPhrase);
    Account getPrincipal();
    Account editMyAccount(EditAccountRequest editAccountRequest);
    void sendEmailToResetPassword(String email);
    PasswordResetToken validateResetPasswordToken(String token);
    void resetPassword(String newPassword, String token);
    void changePassword(ChangePasswordRequest changePasswordRequest);
    void deleteAccount();
    //boolean isAccount
}
