package com.example.inzynierka.repository;

import com.example.inzynierka.models.Account;
import com.example.inzynierka.models.AccountPreferences;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AccountPreferencesRepository extends JpaRepository<AccountPreferences, Long> {
    Optional<AccountPreferences> findByAccount(Account account);
}
