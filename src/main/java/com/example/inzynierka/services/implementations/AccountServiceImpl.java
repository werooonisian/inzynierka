package com.example.inzynierka.services.implementations;

import com.example.inzynierka.exceptions.ResourceNotFoundException;
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
    private final AccountRepository accountRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final EmailService emailService;
    private final RoleRepository roleRepository;

    public AccountServiceImpl(AccountRepository accountRepository, EmailService emailService, RoleRepository roleRepository) {
        this.accountRepository = accountRepository;
        this.emailService = emailService;
        this.roleRepository = roleRepository;
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
            throw new IllegalStateException(String.format(LOGIN_ALREADY_EXISTS, request.getLogin()));
        } else if (accountRepository.findByEmail(request.getEmail()).isPresent()) {
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
        emailService.send(request.getEmail(), buildEmail(request.getFirstName(), link));

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

    //TODO: mail do zmiany
    //poniższy mail jest atrapa i trzeba go zmienić
    private String buildEmail(String name, String link) {
        return "<table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" width=\"100%\" style=\"table-layout:fixed;background-color:#f9f9f9\" id=\"bodyTable\">\n" +
                "\t<tbody>\n" +
                "\t\t<tr>\n" +
                "\t\t\t<td style=\"padding-right:2%;padding-left:2%;\" align=\"center\" valign=\"top\" id=\"bodyCell\">\n" +
                "\t\t\t\t<table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" width=\"100%\" class=\"wrapperBody\" style=\"max-width:800px\">\n" +
                "\t\t\t\t\t<tbody>\n" +
                "\t\t\t\t\t\t<tr>\n" +
                "\t\t\t\t\t\t\t<td align=\"center\" valign=\"top\">\n" +
                "\t\t\t\t\t\t\t\t<table style=\"background-color:#fff;border-color:#e5e5e5;border-style:solid;border-width:0 1px 1px 1px;\">\n" +
                "\t\t\t\t\t\t\t\t\t<tbody>\n" +
                "\t\t\t\t\t\t\t\t\t\t<tr>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t<td style=\"background-color:#515151;font-size:1px;line-height:3px\" class=\"topBorder\" height=\"3\">&nbsp;</td>\n" +
                "\t\t\t\t\t\t\t\t\t\t</tr>\n" +
                "\t\t\t\t\t\t\t\t\t\t<tr>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t<td style=\"padding-bottom: 1%; padding-top:3%\" align=\"center\" valign=\"top\" class=\"title\">\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t<p class=\"text\" style=\"color:#000;font-family:Tahoma;font-size:28px;font-weight:500;line-height:130%;text-align:center\">Hi \" + name\"</p>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t</td>\n" +
                "\t\t\t\t\t\t\t\t\t\t</tr>\n" +
                "\t\t\t\t\t\t\t\t\t\t<tr>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t<td style=\"padding-bottom: 30px; padding-left: 20px; padding-right: 20px;\" align=\"center\" valign=\"top\" class=\"subTitle\">\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t<h4 class=\"text\" style=\"color:#999;font-family:Poppins,Helvetica,Arial,sans-serif;font-size:16px;font-weight:500;font-style:normal;letter-spacing:normal;line-height:24px;text-transform:none;text-align:center;padding:0;margin:0\">TUTAJ LOGO</h4>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t</td>\n" +
                "\t\t\t\t\t\t\t\t\t\t</tr>\n" +
                "\t\t\t\t\t\t\t\t\t\t<tr>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t<td style=\"padding-left:3%;padding-right:3%\" align=\"center\" valign=\"center\" class=\"message\">\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t<table border=\"0\" cellspacing=\"0\">\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t\t<tbody>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t\t\t<tr>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t<td style=\"padding-bottom: 3%;\" align=\"center\" valign=\"center\">\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t<p class=\"message\" style=\"color:#666;font-family:Tahoma;font-size:14px;font-weight:400;line-height:22px;;text-align:center;\">Witaj wśród użytkowników Hot Spoon, aby potwierdzić swoje konto kliknij w poniższy przycisk :)</p>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t</td>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t\t\t</tr>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t\t</tbody>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t</table>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t<table>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t\t<tbody>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t\t\t<tr>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t<td style=\"padding-top:5%;padding-bottom:13%\" align=\"center\" valign=\"top\">\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t<table align=\"center\">\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t<tbody>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t<tr>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t<td style=\"background-color: #515151; padding: 7% 15%; border-radius: 50px;\" align=\"center\" class=\"linkButton\"> <a href=\"#\" style=\"color:#fff;font-family:Tahoma;font-size:13px;font-weight:600;letter-spacing:1px;line-height:20px;text-decoration:none;display:block\" target=\"_blank\">POTWIERDŹ KONTO</a>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t</td>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t</tr>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t</tbody>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t</table>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t</td>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t\t\t</tr>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t\t</tbody>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t</table>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t</td>\n" +
                "\t\t\t\t\t\t\t\t\t\t</tr>\n" +
                "\t\t\t\t\t\t\t\t\t</tbody>\n" +
                "\t\t\t\t\t\t\t\t</table>\n" +
                "\t\t\t\t\t\t\t</td>\n" +
                "\t\t\t\t\t\t</tr>\n" +
                "\t\t\t\t\t</tbody>\n" +
                "\t\t\t\t</table>\n" +
                "\t\t\t\t<table cellspacing=\"13\" width=\"100%\" style=\"max-width:800px\">\n" +
                "\t\t\t\t\t<tbody>\n" +
                "\t\t\t\t\t\t<tr>\n" +
                "\t\t\t\t\t\t\t<td align=\"center\" valign=\"top\">\n" +
                "\t\t\t\t\t\t\t\t<table width=\"100%\" class=\"footer\">\n" +
                "\t\t\t\t\t\t\t\t\t<tbody>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t<td style=\"padding: 1%\" align=\"center\" valign=\"top\" class=\"footerLinks\">\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t<p class=\"text\" style=\"color:#bbb;font-family:Tahoma;font-size:12px;font-weight:400;line-height:20px;text-align:center\"> <a href=\"#\" style=\"color:#bbb;text-decoration:underline\" target=\"_blank\">View Web Version </a>&nbsp;|&nbsp; <a href=\"#\" style=\"color:#bbb;text-decoration:underline\" target=\"_blank\">Email Preferences </a>&nbsp;|&nbsp; <a href=\"#\" style=\"color:#bbb;text-decoration:underline\" target=\"_blank\">Privacy Policy</a>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t</p>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t</td>\n" +
                "\t\t\t\t\t\t\t\t\t\t</tr>\n" +
                "\t\t\t\t\t\t\t\t\t\t<tr>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t<td style=\"padding: 1%;\" align=\"center\" valign=\"top\" class=\"footerEmailInfo\">\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t<p class=\"text\" style=\"color:#bbb;font-family:Tahoma;font-size:12px;font-weight:400;line-height:20px;text-align:center;\">Jeśli masz jakieś pytania, skontaktuj się z nami <a href=\"#\" style=\"color:#bbb;text-decoration:underline\" target=\"_blank\">support@mail.com.</a>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t\t<br> <a href=\"#\" style=\"color:#bbb;text-decoration:underline\" target=\"_blank\">Unsubscribe</a> from our mailing lists</p>\n" +
                "\t\t\t\t\t\t\t\t\t\t</tr>\n" +
                "\t\t\t\t\t\t\t\t\t</tbody>\n" +
                "\t\t\t\t\t\t\t\t</table>\n" +
                "\t\t\t\t\t\t\t</td>\n" +
                "\t\t\t\t\t\t</tr>\n" +
                "\t\t\t\t\t</tbody>\n" +
                "\t\t\t\t</table>\n" +
                "\t\t\t</td>\n" +
                "\t\t</tr>\n" +
                "\t</tbody>\n" +
                "</table>";
    }
}
