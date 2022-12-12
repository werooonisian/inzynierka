package com.example.inzynierka.repository;

import com.example.inzynierka.models.Account;
import com.example.inzynierka.models.AccountDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AccountDetailsRepository extends JpaRepository<AccountDetails, Long> {
    Optional<AccountDetails> findByAccount(Account account);
}
