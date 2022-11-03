package com.example.inzynierka.services.implementations;

import com.example.inzynierka.config.validators.EmailValidator;
import com.example.inzynierka.models.Account;
import com.example.inzynierka.payload.RegistrationRequest;
import com.example.inzynierka.repository.AccountRepository;
import com.example.inzynierka.repository.IndividualPantryRepository;
import com.example.inzynierka.services.RegistrationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Slf4j
@Transactional
public class RegistrationServiceImpl implements RegistrationService {
    private static final String EMAIL_NOT_VALID = "Email %s not valid";
    private final EmailValidator emailValidator;
    private final AccountServiceImpl accountService;
    private final AccountRepository accountRepository;

    public RegistrationServiceImpl(EmailValidator emailValidator,
                                   AccountServiceImpl accountService,
                                   AccountRepository accountRepository) {
        this.emailValidator = emailValidator;
        this.accountService = accountService;
        this.accountRepository = accountRepository;
    }

    @Override
    public Account register(RegistrationRequest request) {
        boolean isValidEmail = emailValidator.test(request.getEmail());
        if(!isValidEmail){
            throw new IllegalStateException(String.format(EMAIL_NOT_VALID, request.getEmail()));
        }
        return accountService.signUp(request);
    }

    @Override
    public String confirmToken(String token) {
        accountRepository.findByConfirmationToken(token)
                .ifPresentOrElse(account -> {
                    account.setEmailConfirmed(true);
                            accountRepository.save(account);
                            log.info("Account {} confirmed", account.getEmail());
                        },
                        () -> {throw new IllegalStateException("Token not found");});

        return "Account confirmed";
    }
}
