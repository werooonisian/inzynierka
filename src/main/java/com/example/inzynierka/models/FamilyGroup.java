package com.example.inzynierka.models;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "Family_Group")
public class FamilyGroup {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String name;
    @OneToMany(mappedBy = "familyGroup")
    private Set<AccountDetails> accounts;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "family_pantry_id", referencedColumnName = "id")
    private FamilyPantry familyPantry;


    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<AccountDetails> getAccounts() {
        return accounts;
    }

    public void setAccounts(Set<AccountDetails> accounts) {
        this.accounts = accounts;
    }

    public FamilyPantry getFamilyPantry() {
        return familyPantry;
    }

    public void setFamilyPantry(FamilyPantry familyPantry) {
        this.familyPantry = familyPantry;
    }
}
