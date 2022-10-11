package com.example.inzynierka.models;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "Recipe")
public class Recipe {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String name;
    @ManyToMany
    @JoinTable(
            name = "recipe_ingredientsList",
            joinColumns = @JoinColumn(name = "recipe_id"),
            inverseJoinColumns = @JoinColumn(name = "ingredient_id")
    )
    private Set<Ingredient> ingredientsList;
    private int servingsCount; //dla ilu zjadaczy posiłek
    private String preparationTime; //czy string czy coś innego??
    private int kcal; //czy kcal jest na cały posiłek czy pojedyncza porcję?
    private String preparationDescription;
    @ManyToOne
    @JoinColumn(name = "account_id", nullable = false)
    private AccountPreferences addedBy; //nazwa????
    @Enumerated(EnumType.STRING)
    private MealType mealType;

    @ManyToMany(mappedBy = "favouriteRecipes")
    private Set<AccountPreferences> favouritedBy;
    @ElementCollection(targetClass = DietType.class)
    @CollectionTable(name = "recipe_dietType", joinColumns = @JoinColumn(name = "recipe_id"))
    @Enumerated(EnumType.STRING)
    private Set<DietType> dietTypes;


    public MealType getMealType() {
        return mealType;
    }

    public void setMealType(MealType mealType) {
        this.mealType = mealType;
    }

    public AccountPreferences getAddedBy() {
        return addedBy;
    }

    public void setAddedBy(AccountPreferences addedBy) {
        this.addedBy = addedBy;
    }

    public Set<DietType> getDietTypes() {
        return dietTypes;
    }

    public void setDietTypes(Set<DietType> dietTypes) {
        this.dietTypes = dietTypes;
    }

    public Set<AccountPreferences> getFavouritedBy() {
        return favouritedBy;
    }

    public void setFavouritedBy(Set<AccountPreferences> favouritedBy) {
        this.favouritedBy = favouritedBy;
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

    public Set<Ingredient> getIngredientsList() {
        return ingredientsList;
    }

    public void setIngredientsList(Set<Ingredient> ingredientsList) {
        this.ingredientsList = ingredientsList;
    }

    public int getServingsCount() {
        return servingsCount;
    }

    public void setServingsCount(int servingsCount) {
        this.servingsCount = servingsCount;
    }

    public String getPreparationTime() {
        return preparationTime;
    }

    public void setPreparationTime(String preparationTime) {
        this.preparationTime = preparationTime;
    }

    public int getKcal() {
        return kcal;
    }

    public void setKcal(int kcal) {
        this.kcal = kcal;
    }

    public String getPreparationDescription() {
        return preparationDescription;
    }

    public void setPreparationDescription(String preparationDescription) {
        this.preparationDescription = preparationDescription;
    }
}
