package com.example.inzynierka.services.implementations;

import com.example.inzynierka.config.validators.EmailValidator;
import com.example.inzynierka.exceptions.AccessDeniedException;
import com.example.inzynierka.exceptions.ResourceNotFoundException;
import com.example.inzynierka.exceptions.TokenExpiredException;
import com.example.inzynierka.mailSender.EmailFactory;
import com.example.inzynierka.models.*;
import com.example.inzynierka.payload.RegistrationRequest;
import com.example.inzynierka.repository.*;
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
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

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
    private static final String EMAIL_NOT_VALID = "Email %s not valid";
    private final static String REGISTRATION_EMAIL_SUBJECT = "Potwierdzenie rejestracji";
    private final static String RESET_PASSWORD_EMAIL_SUBJECT = "Reset hasła";
    private final AccountRepository accountRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final EmailService emailService;
    private final RoleRepository roleRepository;
    private final EmailFactory emailFactory;
    private final PasswordResetTokenRepository passwordResetTokenRepository;
    private final RecipeRepository recipeRepository;
    private final IngredientRepository ingredientRepository;
    private final GroceryListRepository groceryListRepository;
    private final EmailValidator emailValidator;

    public AccountServiceImpl(AccountRepository accountRepository,
                              EmailService emailService,
                              RoleRepository roleRepository,
                              EmailFactory emailFactory,
                              PasswordResetTokenRepository passwordResetTokenRepository,
                              RecipeRepository recipeRepository,
                              IngredientRepository ingredientRepository,
                              GroceryListRepository groceryListRepository,
                              EmailValidator emailValidator) {
        this.accountRepository = accountRepository;
        this.emailService = emailService;
        this.roleRepository = roleRepository;
        this.emailFactory = emailFactory;
        this.passwordResetTokenRepository = passwordResetTokenRepository;
        this.recipeRepository = recipeRepository;
        this.ingredientRepository = ingredientRepository;
        this.groceryListRepository = groceryListRepository;
        this.emailValidator = emailValidator;
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

        String link = "http://localhost:49430/#/confirm-account/" + account.getConfirmationToken();
        emailService.send(request.getEmail(),
                emailFactory.buildRegistrationEmail(request.getFirstName(), link), REGISTRATION_EMAIL_SUBJECT);

        log.info("Saving new user to the database: " + account);

        return accountRepository.save(account);
    }

    @Override
    public List<Account> findAccounts(String searchPhrase) {
        List<Account> foundAccount = accountRepository.findByLoginContains(searchPhrase);
        foundAccount.addAll(accountRepository.findByEmailContains(searchPhrase));
        List<Account> foundAccountsWithoutDuplicate = foundAccount.stream().distinct().collect(Collectors.toList());
        foundAccountsWithoutDuplicate.remove(getPrincipal());
        return foundAccountsWithoutDuplicate;
    }

    @Override
    public Account getPrincipal() {
        return accountRepository
                .findByLogin(SecurityContextHolder.getContext().getAuthentication().getName())
                .orElseThrow(() -> {
                    throw new ResourceNotFoundException("Token not found");});
    }

    @Override
    public Account editMyAccount(EditAccountRequest editAccountRequest) {
        Account principal = getPrincipal();
        boolean isValidEmail = emailValidator.test(editAccountRequest.getEmail());
        if(!isValidEmail){
            throw new IllegalStateException(String.format(EMAIL_NOT_VALID, editAccountRequest.getEmail()));
        }
        if(accountRepository.findByEmail(editAccountRequest.getEmail()).isPresent() &&
                !principal.getEmail().equals(editAccountRequest.getEmail())){
            log.info("Email is already taken");
            throw new IllegalStateException(String.format(EMAIL_ALREADY_EXISTS, editAccountRequest.getEmail()));
        }
        if(editAccountRequest.getFirstName().isEmpty() || editAccountRequest.getLastName().isEmpty()){
            log.info("Edit account data cannot be empty");
            throw new IllegalStateException("Edit account data cannot be empty");
        }
        principal = accountRepository.findById(principal.getId()).map(account -> account
                .withEmail(editAccountRequest.getEmail())
                .withFirstName(editAccountRequest.getFirstName())
                .withLastName(editAccountRequest.getLastName())).orElseThrow(
                        () -> {throw new ResourceNotFoundException("Account not found");});
        return accountRepository.save(principal);
    }

    @Override
    public void sendEmailToResetPassword(String email) {
        Account account = accountRepository.findByEmail(email).orElseThrow(
                () -> new ResourceNotFoundException(String.format("Account with email %s not found", email)));


        String token = UUID.randomUUID().toString();

        if(account.getPasswordResetToken() != null){
            Long id = account.getPasswordResetToken().getId();
            passwordResetTokenRepository.findById(id).ifPresentOrElse(
                    passwordResetTokenRepository::delete,
                    () -> {throw new ResourceNotFoundException("Token not found");});
        }
        createPasswordResetTokenForUser(account, token);

        String link = "http://localhost:49430/#/reset-password/" + token; //TODO: link do sprawdzenia
        emailService.send(email, emailFactory.buildResetPasswordEmail(
                account.getFirstName(), link), RESET_PASSWORD_EMAIL_SUBJECT);
    }

    @Override
    public PasswordResetToken validateResetPasswordToken(String token) {
        PasswordResetToken passwordResetToken = findPasswordResetToken(token);
        if(passwordResetToken.getExpiryDate().isBefore(LocalDateTime.now())){
            passwordResetTokenRepository.delete(passwordResetToken); //TODO: JAK usuwać token, bo nie działa
            throw new TokenExpiredException("Token expired");
        }
        return passwordResetToken;
    }

    @Override
    public void resetPassword(String newPassword, String token) {
        //TODO: maybe jakiś validacja hasła?

        PasswordResetToken passwordResetToken = validateResetPasswordToken(token);
        Account account = passwordResetToken.getAccount();
        account.setPassword(bCryptPasswordEncoder.encode(newPassword));
        accountRepository.save(account);
        passwordResetTokenRepository.delete(passwordResetToken);
    }

    @Override
    public void changePassword(ChangePasswordRequest changePasswordRequest) {
        Account account = getPrincipal();
        if(bCryptPasswordEncoder.matches(changePasswordRequest.getOldPassword(), account.getPassword())){
            account.setPassword(bCryptPasswordEncoder.encode(changePasswordRequest.getNewPassword()));
        } else {
            throw new AccessDeniedException("The old password is wrong");
        }
    }

    @Override
    public void deleteAccount() {
        Account account = getPrincipal();
        AccountDetails accountDetails = account.getAccountDetails();
        accountDetails.getFavouriteRecipes().forEach(recipe -> {
            recipe.getFavouritedBy().remove(accountDetails);
            recipeRepository.save(recipe);
        });
        accountDetails.getAvoidedIngredients().forEach(ingredient -> {
            ingredient.getAvoidedBy().remove(accountDetails);
            ingredientRepository.save(ingredient);
        });
        accountDetails.getGroceryLists().forEach(groceryList -> {
            groceryList.getOwners().remove(accountDetails);
            if(groceryList.getOwners().isEmpty()){
                groceryListRepository.delete(groceryList);
                log.info(String.format("Grocery list with id %s was deleted indefinitely", groceryList.getId()));
            }
            groceryListRepository.save(groceryList);
        });
        accountRepository.delete(account);
    }

    private void createPasswordResetTokenForUser(Account account, String token) { //TODO: save account??
        PasswordResetToken passwordResetToken = new PasswordResetToken(token, account);
        passwordResetTokenRepository.save(passwordResetToken);
    }

    private PasswordResetToken findPasswordResetToken(String token){
        return passwordResetTokenRepository.findByToken(token).orElseThrow(
                () -> new ResourceNotFoundException("Reset password token not found"));
    }

}
