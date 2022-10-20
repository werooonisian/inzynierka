package com.example.inzynierka.models;

import javax.persistence.*;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "GroceryList")
public class GroceryList {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String name;
    @ManyToMany
    @JoinTable(
            name = "groceryList_ingredient",
            joinColumns = @JoinColumn(name = "groceryList_id"),
            inverseJoinColumns = @JoinColumn(name = "ingredient_id")
    )
    private List<Ingredient> ingredientsList;
    @ManyToMany(mappedBy = "groceryLists")
    private Set<AccountPreferences> owners;

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

    public List<Ingredient> getIngredientsList() {
        return ingredientsList;
    }

    public void setIngredientsList(List<Ingredient> ingredientsList) {
        this.ingredientsList = ingredientsList;
    }

    public Set<AccountPreferences> getOwners() {
        return owners;
    }

    public void setOwners(Set<AccountPreferences> owners) {
        this.owners = owners;
    }
}