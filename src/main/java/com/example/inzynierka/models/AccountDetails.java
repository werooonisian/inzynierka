package com.example.inzynierka.models;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "AccountDetails")
public class AccountDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @OneToOne(mappedBy = "accountDetails")
    private Account account;

    @ManyToMany
    @JoinTable(
            name="account_avoidedIngredients",
            joinColumns = @JoinColumn(name = "account_id"),
            inverseJoinColumns = @JoinColumn(name = "ingredient_id")
    )
    private Set<Ingredient> avoidedIngredients; //alergeny i coś co nie lubi
    @JsonIdentityInfo(generator = ObjectIdGenerators.UUIDGenerator.class, property = "@id")
    @ManyToMany
    @JoinTable(
            name="account_favouriteRecipes",
            joinColumns = @JoinColumn(name = "account_id"),
            inverseJoinColumns = @JoinColumn(name = "recipe_id")
    )
    private Set<Recipe> favouriteRecipes;
    @JsonIdentityInfo(generator = ObjectIdGenerators.UUIDGenerator.class, property = "@id")
    @ManyToMany
    @JoinTable(
            name="account_groceryList",
            joinColumns = @JoinColumn(name = "account_id"),
            inverseJoinColumns = @JoinColumn(name = "groceryList_id")
    )
    private Set<GroceryList> groceryLists = new HashSet<>();

    @ElementCollection(targetClass = DietType.class)
    @CollectionTable(name = "account_dietType", joinColumns = @JoinColumn(name = "account_id"))
    @Enumerated(EnumType.STRING)
    private Set<DietType> dietTypes; //diety jakie stosuje

    @JsonIdentityInfo(generator = ObjectIdGenerators.UUIDGenerator.class, property = "@id")
    @OneToMany(mappedBy = "addedBy", cascade = CascadeType.REMOVE, orphanRemoval = true) //????
    private Set<Recipe> addedRecipes; // nazwa????

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "individual_pantry_id", referencedColumnName = "id")
    private IndividualPantry individualPantry;

    @JsonIdentityInfo(generator = ObjectIdGenerators.UUIDGenerator.class, property = "@id")
    @ManyToOne
    @JoinColumn(name = "family_pantry_id")
    private FamilyPantry familyPantry;

    @OneToMany(mappedBy = "accountDetails", cascade = CascadeType.REMOVE, orphanRemoval = true)
    private Set<FamilyPantryToken> familyPantryTokens;


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

    public IndividualPantry getIndividualPantry() {
        return individualPantry;
    }

    public void setIndividualPantry(IndividualPantry individualPantry) {
        this.individualPantry = individualPantry;
    }

    public FamilyPantry getFamilyPantry() {
        return familyPantry;
    }

    public void setFamilyPantry(FamilyPantry familyPantry) {
        this.familyPantry = familyPantry;
    }

    public Set<FamilyPantryToken> getFamilyPantryTokens() {
        return familyPantryTokens;
    }

    public void setFamilyPantryTokens(Set<FamilyPantryToken> familyPantryTokens) {
        this.familyPantryTokens = familyPantryTokens;
    }
}
