package com.example.inzynierka.payload;

import com.example.inzynierka.models.DietType;
import com.example.inzynierka.models.MealType;
import lombok.Builder;

import javax.validation.constraints.Min;
import java.util.Set;

@Builder
public class RecipeDataFilter {
    private boolean ingredientsMustBeInIndividualPantry;
    private boolean ingredientsMustBeInFamilyPantry;
    private Set<DietType> diets; //belonging to diet
    private MealType mealType;
    private String searchPhrase;
    @Min(1)
    private Integer maxPreparationTime;
    @Min(0)
    private Integer minKcal;
    @Min(0)
    private Integer maxKcal;
    private boolean considerAvoidedIngredientList;

    public boolean isIngredientsMustBeInIndividualPantry() {
        return ingredientsMustBeInIndividualPantry;
    }

    public void setIngredientsMustBeInIndividualPantry(boolean ingredientsMustBeInIndividualPantry) {
        this.ingredientsMustBeInIndividualPantry = ingredientsMustBeInIndividualPantry;
    }

    public boolean isIngredientsMustBeInFamilyPantry() {
        return ingredientsMustBeInFamilyPantry;
    }

    public void setIngredientsMustBeInFamilyPantry(boolean ingredientsMustBeInFamilyPantry) {
        this.ingredientsMustBeInFamilyPantry = ingredientsMustBeInFamilyPantry;
    }

    public Set<DietType> getDiets() {
        return diets;
    }

    public void setDiets(Set<DietType> diets) {
        this.diets = diets;
    }

    public MealType getMealType() {
        return mealType;
    }

    public void setMealType(MealType mealType) {
        this.mealType = mealType;
    }

    public String getSearchPhrase() {
        return searchPhrase;
    }

    public void setSearchPhrase(String searchPhrase) {
        this.searchPhrase = searchPhrase;
    }

    public Integer getMaxPreparationTime() {
        return maxPreparationTime;
    }

    public void setMaxPreparationTime(Integer maxPreparationTime) {
        this.maxPreparationTime = maxPreparationTime;
    }

    public Integer getMinKcal() {
        return minKcal;
    }

    public void setMinKcal(Integer minKcal) {
        this.minKcal = minKcal;
    }

    public Integer getMaxKcal() {
        return maxKcal;
    }

    public void setMaxKcal(Integer maxKcal) {
        this.maxKcal = maxKcal;
    }

    public boolean isConsiderAvoidedIngredientList() {
        return considerAvoidedIngredientList;
    }

    public void setConsiderAvoidedIngredientList(boolean considerAvoidedIngredientList) {
        this.considerAvoidedIngredientList = considerAvoidedIngredientList;
    }
}
