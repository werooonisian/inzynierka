package com.example.inzynierka.models;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "Family_Pantry")
public class FamilyPantry extends Pantry{
    @OneToMany(mappedBy = "familyPantry")
    private Set<AccountDetails> owners = new HashSet<>();

    @OneToMany(mappedBy = "familyPantry", cascade = CascadeType.REMOVE, orphanRemoval = true)
    private Set<FamilyPantryToken> tokens = new HashSet<>();

    public Set<AccountDetails> getOwners() {
        return owners;
    }

    public void setOwners(Set<AccountDetails> owners) {
        this.owners = owners;
    }

    public Set<FamilyPantryToken> getTokens() {
        return tokens;
    }

    public void setTokens(Set<FamilyPantryToken> tokens) {
        this.tokens = tokens;
    }
}
