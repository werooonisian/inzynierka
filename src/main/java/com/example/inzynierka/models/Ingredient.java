package com.example.inzynierka.models;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "Ingredient")
public class Ingredient {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String name;
    @Enumerated(EnumType.STRING)
    private FoodGroup foodGroup;

    public Ingredient() {
    }

    @ManyToMany(mappedBy = "avoidedIngredients")
    private Set<AccountPreferences> avoidedBy;
    @ManyToMany(mappedBy = "ingredientsList")
    private Set<GroceryList> presentInGroceryLists; //tutaj nazwa????
    @JsonIgnore
    @ManyToMany(mappedBy = "ingredientsList")
    private Set<Recipe> presentInRecipes; //tutaj nazwa???

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "details_id", referencedColumnName = "id")
    private IngredientDetails ingredientDetails;

    public Set<Recipe> getPresentInRecipes() {
        return presentInRecipes;
    }

    public void setPresentInRecipes(Set<Recipe> presentInRecipes) {
        this.presentInRecipes = presentInRecipes;
    }

    public Set<GroceryList> getPresentInGroceryLists() {
        return presentInGroceryLists;
    }

    public void setPresentInGroceryLists(Set<GroceryList> presentInGroceryLists) {
        this.presentInGroceryLists = presentInGroceryLists;
    }

    public Set<AccountPreferences> getAvoidedBy() {
        return avoidedBy;
    }

    public void setAvoidedBy(Set<AccountPreferences> avoidedBy) {
        this.avoidedBy = avoidedBy;
    }

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

    public FoodGroup getFoodGroup() {
        return foodGroup;
    }

    public void setFoodGroup(FoodGroup foodGroup) {
        this.foodGroup = foodGroup;
    }

    public IngredientDetails getIngredientDetails() {
        return ingredientDetails;
    }

    public void setIngredientDetails(IngredientDetails ingredientDetails) {
        this.ingredientDetails = ingredientDetails;
    }
}
