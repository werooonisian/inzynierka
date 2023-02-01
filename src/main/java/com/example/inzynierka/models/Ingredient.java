package com.example.inzynierka.models;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

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

    @ElementCollection(targetClass = DietType.class)
    @CollectionTable(name = "ingredient_dietType", joinColumns = @JoinColumn(name = "ingredient_id"))
    @Enumerated(EnumType.STRING)
    private Set<DietType> dietTypes;

    @JsonIdentityInfo(generator = ObjectIdGenerators.UUIDGenerator.class, property = "@id") //TODO: IDK CZY TRZEBA
    @ManyToMany(mappedBy = "pantry")
    private Set<Pantry> presentInPantries;
    public Ingredient() { }

    @JsonIgnore
    @ManyToMany(mappedBy = "avoidedIngredients")
    private Set<AccountDetails> avoidedBy;
/*    @JsonIdentityInfo(generator = ObjectIdGenerators.UUIDGenerator.class, property = "@id")
    @ManyToMany(mappedBy = "ingredientsList")
    private Set<GroceryList> presentInGroceryLists; //tutaj nazwa????*/
    //@JsonManagedReference
    @JsonIdentityInfo(generator = ObjectIdGenerators.UUIDGenerator.class, property = "@id")
    @OneToMany(mappedBy = "ingredient", cascade = CascadeType.REMOVE, orphanRemoval = true)
    private Set<IngredientQuantityRecipe> ingredientQuantities;

    public Set<AccountDetails> getAvoidedBy() {
        return avoidedBy;
    }

    public void setAvoidedBy(Set<AccountDetails> avoidedBy) {
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

    public Set<DietType> getDietTypes() {
        return dietTypes;
    }

    public void setDietTypes(Set<DietType> dietTypes) {
        this.dietTypes = dietTypes;
    }

    public Set<IngredientQuantityRecipe> getIngredientQuantities() {
        return ingredientQuantities;
    }

    public void setIngredientQuantities(Set<IngredientQuantityRecipe> ingredientQuantities) {
        this.ingredientQuantities = ingredientQuantities;
    }

    public Set<Pantry> getPresentInPantries() {
        return presentInPantries;
    }

    public void setPresentInPantries(Set<Pantry> presentInPantries) {
        this.presentInPantries = presentInPantries;
    }
}
