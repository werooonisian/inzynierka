package com.example.inzynierka.models;

import javax.persistence.*;

@Entity
@Table(name = "Ingredient_Details")
public class IngredientDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @OneToOne(mappedBy = "ingredientDetails")
    private Ingredient ingredient;

    public long getId() {
        return id;
    }

    public Ingredient getIngredient() {
        return ingredient;
    }

    public void setIngredient(Ingredient ingredient) {
        this.ingredient = ingredient;
    }
}
