package com.example.inzynierka.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "IngredientQuantity")
public class IngredientQuantity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private double quantity;
    @NotNull(message = "Unit may not be empty")
    @Enumerated(EnumType.STRING)
    private IngredientUnit ingredientUnit;
    //@JsonBackReference
    @ManyToOne
    @JoinColumn(name = "ingredient_id", nullable = false)
    private Ingredient ingredient;
    //@JsonBackReference
    @JsonIdentityInfo(generator = ObjectIdGenerators.UUIDGenerator.class, property = "@id")
    @ManyToOne
    @JoinColumn(name = "recipe_id", nullable = false)
    private Recipe recipe;

    public long getId() {
        return id;
    }

    public double getQuantity() {
        return quantity;
    }

    public void setQuantity(double quantity) {
        this.quantity = quantity;
    }

    public IngredientUnit getIngredientUnit() {
        return ingredientUnit;
    }

    public void setIngredientUnit(IngredientUnit ingredientUnit) {
        this.ingredientUnit = ingredientUnit;
    }

    public Ingredient getIngredient() {
        return ingredient;
    }

    public void setIngredient(Ingredient ingredient) {
        this.ingredient = ingredient;
    }

    public Recipe getRecipe() {
        return recipe;
    }

    public void setRecipe(Recipe recipe) {
        this.recipe = recipe;
    }
}
