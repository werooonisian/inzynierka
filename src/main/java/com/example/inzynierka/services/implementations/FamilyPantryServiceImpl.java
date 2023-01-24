package com.example.inzynierka.services.implementations;

import com.example.inzynierka.exceptions.AccessDeniedException;
import com.example.inzynierka.exceptions.PantryCreationException;
import com.example.inzynierka.exceptions.ResourceNotFoundException;
import com.example.inzynierka.mailSender.EmailFactory;
import com.example.inzynierka.models.*;
import com.example.inzynierka.repository.*;
import com.example.inzynierka.services.AccountDetailsService;
import com.example.inzynierka.services.AccountService;
import com.example.inzynierka.services.EmailService;
import com.example.inzynierka.services.FamilyPantryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Slf4j
public class FamilyPantryServiceImpl implements FamilyPantryService {
    private final String EMAIL_SUBJECT = "Zaproszenie do rodzinnej spiżarni";
    private final FamilyPantryRepository familyPantryRepository;
    private final AccountDetailsService accountDetailsService;
    private final AccountDetailsRepository accountDetailsRepository;
    private final IngredientRepository ingredientRepository;
    private final AccountService accountService;
    private final EmailService emailService;
    private final EmailFactory emailFactory;
    private final AccountRepository accountRepository;
    private final FamilyPantryTokenRepository familyPantryTokenRepository;
    private final IndividualPantryRepository individualPantryRepository;

    public FamilyPantryServiceImpl(FamilyPantryRepository familyPantryRepository,
                                   AccountDetailsService accountDetailsService,
                                   AccountDetailsRepository accountDetailsRepository,
                                   IngredientRepository ingredientRepository,
                                   AccountService accountService,
                                   EmailService emailService,
                                   EmailFactory emailFactory,
                                   AccountRepository accountRepository,
                                   FamilyPantryTokenRepository familyPantryTokenRepository,
                                   IndividualPantryRepository individualPantryRepository) {
        this.familyPantryRepository = familyPantryRepository;
        this.accountDetailsService = accountDetailsService;
        this.accountDetailsRepository = accountDetailsRepository;
        this.ingredientRepository = ingredientRepository;
        this.accountService = accountService;
        this.emailService = emailService;
        this.emailFactory = emailFactory;
        this.accountRepository = accountRepository;
        this.familyPantryTokenRepository = familyPantryTokenRepository;
        this.individualPantryRepository = individualPantryRepository;
    }

    @Override
    public FamilyPantry createFamilyPantry() {
        AccountDetails accountDetails = accountDetailsService.getPrincipalsDetails();
        if(accountDetails.getFamilyPantry() != null)
        {
            throw new PantryCreationException("Logged in user already has family pantry");
        }
        FamilyPantry familyPantry = new FamilyPantry();
        accountDetails.setFamilyPantry(familyPantry);
        familyPantry.getOwners().add(accountDetails);
        familyPantryRepository.save(familyPantry);
        accountDetailsRepository.save(accountDetails);
        return familyPantry;
    }

    @Override
    public FamilyPantry getIngredients() throws NullPointerException{
        return verifyAccessToFamilyPantry();
    }

    @Override
    public Set<Account> getOwners() {
        return verifyAccessToFamilyPantry().getOwners().stream()
                .map(accountDetails -> accountDetails.getAccount()).collect(Collectors.toSet());
    }

    @Override
    public void addIngredient(long ingredientId) {
        try {
            FamilyPantry familyPantry = accountDetailsService.getPrincipalsDetails().getFamilyPantry();
            Ingredient ingredient = ingredientRepository.findById(ingredientId).orElseThrow(
                    () -> {throw new ResourceNotFoundException(String.format("Ingredient with id %s not found", ingredientId));});
            familyPantry.getPantry().add(ingredient);
            ingredient.getPresentInPantries().add(familyPantry);
            familyPantryRepository.save(familyPantry);
            ingredientRepository.save(ingredient);
        }
        catch(NullPointerException exc){
            throw new ResourceNotFoundException("Logged in user does not have family pantry");
        }
    }

    @Override
    public void deleteIngredient(long ingredientId) {
        FamilyPantry familyPantry = verifyAccessToFamilyPantry();
        ingredientRepository.findById(ingredientId).ifPresentOrElse(ingredient -> {
                    if(!ingredient.getPresentInPantries().contains(familyPantry)){
                        log.info(String.format("Ingredient with id %s not found in family pantry", ingredientId));
                        return;
                    }
                    ingredient.getPresentInPantries().remove(familyPantry);
                    familyPantry.getPantry().remove(ingredient);
                    familyPantryRepository.save(familyPantry);
                    ingredientRepository.save(ingredient);
                    log.info("User {} removed {} from their family pantry",
                            SecurityContextHolder.getContext().getAuthentication().getName(), ingredient.getName());},
                () -> { throw new ResourceNotFoundException(String.format("Ingredient with id %s not found", ingredientId));}
        );
    }

    @Override
    public void sendInvitation(long userId) {
        Account account = accountRepository.findById(userId).orElseThrow( () ->
                new ResourceNotFoundException(String.format("User with id %s does not exist", userId)));
        Account senderAccount = accountService.getPrincipal();
        if(senderAccount.getAccountDetails().getFamilyPantry() == null){
            throw new ResourceNotFoundException("Principal does not have family pantry");
        }
        String token = UUID.randomUUID().toString();
        String link = "http://localhost:8080/familyPantry/acceptInvitation/" + token;
        FamilyPantryToken familyPantryToken = new FamilyPantryToken(token);
        familyPantryToken.setAccountDetails(account.getAccountDetails());
        familyPantryToken.setFamilyPantry(senderAccount.getAccountDetails().getFamilyPantry());
        account.getAccountDetails().getFamilyPantryTokens().add(familyPantryToken);
        accountDetailsRepository.save(account.getAccountDetails());
        familyPantryTokenRepository.save(familyPantryToken);
        emailService.send(account.getEmail(), emailFactory.buildJoinFamilyPantryEmail(
                account.getFirstName(), senderAccount.getLogin(), link),EMAIL_SUBJECT);
    }

    @Override
    public void acceptInvitation(String token) {
        FamilyPantryToken familyPantryToken = familyPantryTokenRepository.findByToken(token).orElseThrow(() ->
                new ResourceNotFoundException("Token not found"));
        FamilyPantry familyPantry = familyPantryToken.getFamilyPantry();
        AccountDetails account = familyPantryToken.getAccountDetails();
        if(account != accountService.getPrincipal().getAccountDetails()){
            log.info("Access denied");
            throw new AccessDeniedException("Access denied");
        }
        if(account.getFamilyPantry() != null){
            account.getFamilyPantry().getOwners().remove(account);
            familyPantryRepository.save(account.getFamilyPantry());
        }
        account.setFamilyPantry(familyPantry);
        familyPantry.getOwners().add(account);
        accountDetailsRepository.save(account);
        familyPantryRepository.save(familyPantry);
        familyPantryTokenRepository.delete(familyPantryToken);
    }

    @Override
    public void leaveFamilyPantry() {
        AccountDetails account = accountService.getPrincipal().getAccountDetails();
        if(account.getFamilyPantry() == null){
            log.info("User does not have family pantry");
            throw new ResourceNotFoundException("User does not have family pantry");
        }
        FamilyPantry familyPantry = account.getFamilyPantry();
        familyPantry.getOwners().remove(account);
        account.setFamilyPantry(null);
        if(familyPantry.getOwners().size() == 0){
            familyPantryRepository.delete(familyPantry);
            log.info("Family pantry was deleted");
        }
        else{
            familyPantryRepository.save(familyPantry);
        }
        accountDetailsRepository.save(account);
        log.info("User {} left family pantry", account.getAccount().getLogin());
    }

    @Override
    public void moveToMyIndividualPantry(long ingredientId) { //dodać throws?
        try {
            AccountDetails account = accountDetailsService.getPrincipalsDetails();
            FamilyPantry familyPantry = account.getFamilyPantry();
            Ingredient ingredient = ingredientRepository.findById(ingredientId).orElseThrow(
                    () -> {throw new ResourceNotFoundException(String.format("Ingredient with id %s not found", ingredientId));});
            if(!familyPantry.getPantry().contains(ingredient)){
                throw new ResourceNotFoundException(String.format("Ingredient with id %s not found in pantry", ingredientId));
            }
            account.getIndividualPantry().getPantry().add(ingredient);
            familyPantry.getPantry().remove(ingredient);
            familyPantryRepository.save(familyPantry);
            individualPantryRepository.save(account.getIndividualPantry());
        }
        catch (NullPointerException e){
            throw new ResourceNotFoundException("Logged in user does not have family pantry");
        }
    }

    private FamilyPantry verifyAccessToFamilyPantry(){
        try{
            return accountDetailsService.getPrincipalsDetails().getFamilyPantry();
        }
        catch (NullPointerException exc){
            throw new ResourceNotFoundException("Logged in user does not have family pantry");
        }
    }
}
