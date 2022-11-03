package com.example.inzynierka.models;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import javax.persistence.*;

@Entity
@Table(name = "Individual_Pantry")
public class IndividualPantry extends Pantry {
    @JsonIdentityInfo(generator = ObjectIdGenerators.UUIDGenerator.class, property = "@id") //TODO: IDK CZY POTRZEBE
    @OneToOne(mappedBy = "individualPantry")
    private AccountPreferences account;


    public AccountPreferences getAccount() {
        return account;
    }

    public void setAccount(AccountPreferences account) {
        this.account = account;
    }
}
