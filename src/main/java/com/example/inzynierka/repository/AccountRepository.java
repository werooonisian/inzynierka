package com.example.inzynierka.repository;

import com.example.inzynierka.models.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {
    Optional<Account> findByLogin(String login);
    Optional<Account> findByEmail(String email);
    Optional<Account> findByConfirmationToken(String confirmationToken);
}
