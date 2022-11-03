package com.example.inzynierka.models;

import javax.persistence.*;

@Entity
@Table(name = "Group_Pantry")
public class FamilyPantry extends Pantry{
    @OneToOne(mappedBy = "familyPantry")
    private FamilyGroup familyGroup;


    public FamilyGroup getFamilyGroup() {
        return familyGroup;
    }

    public void setFamilyGroup(FamilyGroup familyGroup) {
        this.familyGroup = familyGroup;
    }
}
