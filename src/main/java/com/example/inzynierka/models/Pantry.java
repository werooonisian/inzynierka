package com.example.inzynierka.models;

import javax.persistence.*;
import java.util.Set;

@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public abstract class Pantry {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @ManyToMany
    @JoinTable(
            name = "pantry_ingredient",
            joinColumns = @JoinColumn(name = "pantry_id"),
            inverseJoinColumns = @JoinColumn(name = "ingredient_id")
    )
    private Set<Ingredient> pantry;

    public long getId() {
        return id;
    }

    public Set<Ingredient> getPantry() {
        return pantry;
    }

    public void setPantry(Set<Ingredient> pantry) {
        this.pantry = pantry;
    }
}
