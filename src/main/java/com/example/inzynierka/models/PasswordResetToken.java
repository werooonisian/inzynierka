package com.example.inzynierka.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Date;

@Entity
@Table(name = "PasswordResetToken")
public class PasswordResetToken {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String token;

    @JsonIgnore
    @OneToOne(targetEntity = Account.class, fetch = FetchType.EAGER)
    @JoinColumn(nullable = false, name = "account_id")
    private Account account;
    private LocalDateTime expiryDate;

    public PasswordResetToken() {
    }

    public PasswordResetToken(String token, Account account) {
        this.token = token;
        this.account = account;
        this.expiryDate = LocalDateTime.now().plusHours(2);
    }

    public long getId() {
        return id;
    }

    public String getToken() {
        return token;
    }

    public Account getAccount() {
        return account;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public LocalDateTime getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(LocalDateTime expiryDate) {
        this.expiryDate = expiryDate;
    }
}
