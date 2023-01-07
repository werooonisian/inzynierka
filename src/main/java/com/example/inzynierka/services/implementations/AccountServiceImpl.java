package com.example.inzynierka.services.implementations;

import com.example.inzynierka.exceptions.ResourceNotFoundException;
import com.example.inzynierka.mailSender.EmailFactory;
import com.example.inzynierka.models.Account;
import com.example.inzynierka.models.AccountDetails;
import com.example.inzynierka.models.CustomAccount;
import com.example.inzynierka.models.IndividualPantry;
import com.example.inzynierka.payload.RegistrationRequest;
import com.example.inzynierka.repository.AccountRepository;
import com.example.inzynierka.repository.RoleRepository;
import com.example.inzynierka.services.AccountService;
import com.example.inzynierka.services.EmailService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@Service
@Slf4j
@Transactional
public class AccountServiceImpl implements UserDetailsService, AccountService {
    private final static String USER_NOT_FOUND =
            "User with login %s not found";

    private final static String LOGIN_ALREADY_EXISTS =
            "User with login %s already exists";

    private final static String EMAIL_ALREADY_EXISTS =
            "User with email %s already exists";
    private final static String EMAIL_SUBJECT = "Potwierdzenie rejestracji";
    private final AccountRepository accountRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final EmailService emailService;
    private final RoleRepository roleRepository;
    private final EmailFactory emailFactory;

    public AccountServiceImpl(AccountRepository accountRepository,
                              EmailService emailService,
                              RoleRepository roleRepository,
                              EmailFactory emailFactory) {
        this.accountRepository = accountRepository;
        this.emailService = emailService;
        this.roleRepository = roleRepository;
        this.emailFactory = emailFactory;
        this.bCryptPasswordEncoder = new BCryptPasswordEncoder();
    }

    @Override
    public UserDetails loadUserByUsername(String username)
            throws UsernameNotFoundException {
        Account account = accountRepository.findByLogin(username)
                .orElseThrow(() -> new UsernameNotFoundException(String.format(USER_NOT_FOUND, username)));
        //log.info("User {} found", account.getLogin());
        return new CustomAccount(account);
    }

    @Override
    public Account signUp(RegistrationRequest request){
        if(accountRepository.findByLogin(request.getLogin()).isPresent()){
            log.info("Login is already taken");
            throw new IllegalStateException(String.format(LOGIN_ALREADY_EXISTS, request.getLogin()));
        } else if (accountRepository.findByEmail(request.getEmail()).isPresent()) {
            log.info("Email is already taken");
            throw new IllegalStateException(String.format(EMAIL_ALREADY_EXISTS, request.getEmail()));
        }
        Account account =  new Account();
        account.setLogin(request.getLogin());
        account.setEmail(request.getEmail());
        account.setPassword(bCryptPasswordEncoder.encode(request.getPassword()));
        account.setFirstName(request.getFirstName());
        account.setLastName(request.getLastName());
        account.setRoles(Set.of(roleRepository.findByName("USER")));//getRoles().add(new Role("USER"));
        account.setConfirmationToken(UUID.randomUUID().toString());
        account.setAccountDetails(new AccountDetails());

        account.getAccountDetails().setIndividualPantry(new IndividualPantry());

        String link = "http://localhost:8080/registration/confirm?token=" + account.getConfirmationToken();
        emailService.send(request.getEmail(),
                emailFactory.buildRegistrationEmail(request.getFirstName(), link), EMAIL_SUBJECT);

        log.info("Saving new user to the database: " + account);

        return accountRepository.save(account);
    }

    @Override
    public List<Account> findAccounts(String searchPhrase) {
        List<Account> foundAccount = accountRepository.findByLoginContains(searchPhrase);
        foundAccount.addAll(accountRepository.findByEmailContains(searchPhrase));
        return foundAccount;
    }

    @Override
    public Account getPrincipal() {
        return accountRepository
                .findByLogin(SecurityContextHolder.getContext().getAuthentication().getName())
                .orElseThrow(() -> {
                    throw new ResourceNotFoundException("Token not found");});
    }

    @Override
    public Account editMyAccount(Account account) {
        return null;
    }
}
