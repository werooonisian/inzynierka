package com.example.inzynierka.models;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "AccountPreferences")
public class AccountPreferences {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @OneToOne(mappedBy = "accountPreferences")
    private Account account;

    @ManyToMany
    @JoinTable(
            name="account_avoidedIngredients",
            joinColumns = @JoinColumn(name = "account_id"),
            inverseJoinColumns = @JoinColumn(name = "ingredient_id")
    )
    private Set<Ingredient> avoidedIngredients; //alergeny i coś co nie lubi
    @ManyToMany
    @JoinTable(
            name="account_favouriteRecipes",
            joinColumns = @JoinColumn(name = "account_id"),
            inverseJoinColumns = @JoinColumn(name = "recipe_id")
    )
    private Set<Recipe> favouriteRecipes;
    @ManyToMany
    @JoinTable(
            name="account_groceryList",
            joinColumns = @JoinColumn(name = "account_id"),
            inverseJoinColumns = @JoinColumn(name = "groceryList_id")
    )
    private Set<GroceryList> groceryLists;
    @ElementCollection(targetClass = DietType.class)
    @CollectionTable(name = "account_dietType", joinColumns = @JoinColumn(name = "account_id"))
    @Enumerated(EnumType.STRING)
    private Set<DietType> dietTypes; //diety jakie stosuje

    @OneToMany(mappedBy = "addedBy") //????
    private Set<Recipe> addedRecipes; // nazwa????


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public Set<Ingredient> getAvoidedIngredients() {
        return avoidedIngredients;
    }

    public void setAvoidedIngredients(Set<Ingredient> avoidedIngredients) {
        this.avoidedIngredients = avoidedIngredients;
    }

    public Set<Recipe> getFavouriteRecipes() {
        return favouriteRecipes;
    }

    public void setFavouriteRecipes(Set<Recipe> favouriteRecipes) {
        this.favouriteRecipes = favouriteRecipes;
    }

    public Set<GroceryList> getGroceryLists() {
        return groceryLists;
    }

    public void setGroceryLists(Set<GroceryList> groceryLists) {
        this.groceryLists = groceryLists;
    }

    public Set<DietType> getDietTypes() {
        return dietTypes;
    }

    public void setDietTypes(Set<DietType> dietTypes) {
        this.dietTypes = dietTypes;
    }

    public Set<Recipe> getAddedRecipes() {
        return addedRecipes;
    }

    public void setAddedRecipes(Set<Recipe> addedRecipes) {
        this.addedRecipes = addedRecipes;
    }
}
