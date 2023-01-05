package com.example.inzynierka.models;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "Group_Pantry") //TODO: zmienić kiedyś przy ponownym stawianiu bazki
public class FamilyPantry extends Pantry{
    @OneToMany(mappedBy = "familyPantry")
    private Set<AccountDetails> owners = new HashSet<>();

    public Set<AccountDetails> getOwners() {
        return owners;
    }

    public void setOwners(Set<AccountDetails> owners) {
        this.owners = owners;
    }
}
