package com.example.inzynierka.models;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.With;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.HashSet;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@With
@Entity
@Table(name = "Recipe")
public class Recipe {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @NotEmpty(message = "Name may not be empty")
    private String name;
    @NotEmpty(message = "Ingredient list may not be empty")
    //@JsonManagedReference
    //@JsonIdentityInfo(generator = ObjectIdGenerators.UUIDGenerator.class, property = "@id")
    @OneToMany(mappedBy = "recipe", cascade = CascadeType.REMOVE, orphanRemoval = true)
    private Set<IngredientQuantityRecipe> ingredientsList = new HashSet<>();

    @OneToMany(mappedBy = "recipe", cascade = CascadeType.REMOVE, orphanRemoval = true)
    private Set<Image> images = new HashSet<>();
    @Min(1)
    private int servingsCount; //dla ilu zjadaczy posiłek
    @Min(1)
    private int preparationTime; //czy string czy coś innego??
    @Min(0)
    private int kcal; //czy kcal jest na cały posiłek czy pojedyncza porcję?
    @NotEmpty(message = "Description may not be empty")
    private String preparationDescription;
    @JsonIdentityInfo(generator = ObjectIdGenerators.UUIDGenerator.class, property = "@id")
    @ManyToOne
    @JoinColumn(name = "account_id", nullable = false)
    private AccountDetails addedBy; //nazwa????
    @NotNull(message = "Meal type may not be empty")
    @Enumerated(EnumType.STRING)
    private MealType mealType;

    @ManyToMany(mappedBy = "favouriteRecipes", cascade = CascadeType.REMOVE)
    private Set<AccountDetails> favouritedBy;
    @ElementCollection(targetClass = DietType.class)
    @CollectionTable(name = "recipe_dietType", joinColumns = @JoinColumn(name = "recipe_id"))
    @Enumerated(EnumType.STRING)
    private Set<DietType> dietTypes = new HashSet<>();



    public MealType getMealType() {
        return mealType;
    }

    public void setMealType(MealType mealType) {
        this.mealType = mealType;
    }

    public AccountDetails getAddedBy() {
        return addedBy;
    }

    public void setAddedBy(AccountDetails addedBy) {
        this.addedBy = addedBy;
    }

    public Set<DietType> getDietTypes() {
        return dietTypes;
    }

    public void setDietTypes(Set<DietType> dietTypes) {
        this.dietTypes = dietTypes;
    }

    public Set<AccountDetails> getFavouritedBy() {
        return favouritedBy;
    }

    public void setFavouritedBy(Set<AccountDetails> favouritedBy) {
        this.favouritedBy = favouritedBy;
    }
    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<IngredientQuantityRecipe> getIngredientsList() {
        return ingredientsList;
    }

    public void setIngredientsList(Set<IngredientQuantityRecipe> ingredientsList) {
        this.ingredientsList = ingredientsList;
    }

    public int getServingsCount() {
        return servingsCount;
    }

    public void setServingsCount(int servingsCount) {
        this.servingsCount = servingsCount;
    }

    public int getPreparationTime() {
        return preparationTime;
    }

    public void setPreparationTime(int preparationTime) {
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

    public Set<Image> getImages() {
        return images;
    }

    public void setImages(Set<Image> images) {
        this.images = images;
    }
}
