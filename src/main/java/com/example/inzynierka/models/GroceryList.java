package com.example.inzynierka.models;

import javax.persistence.*;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "GroceryList")
public class GroceryList {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String name;
   @OneToMany(mappedBy = "groceryList", cascade = CascadeType.REMOVE, orphanRemoval = true)
    private Set<IngredientQuantityGroceryList> ingredientsList;

    @ManyToMany(mappedBy = "groceryLists")
    private Set<AccountDetails> owners = new HashSet<>();

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<AccountDetails> getOwners() {
        return owners;
    }

    public void setOwners(Set<AccountDetails> owners) {
        this.owners = owners;
    }

    public Set<IngredientQuantityGroceryList> getIngredientsList() {
        return ingredientsList;
    }

    public void setIngredientsList(Set<IngredientQuantityGroceryList> ingredientsList) {
        this.ingredientsList = ingredientsList;
    }
}
