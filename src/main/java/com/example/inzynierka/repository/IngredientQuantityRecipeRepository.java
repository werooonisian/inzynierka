package com.example.inzynierka.repository;

import com.example.inzynierka.models.IngredientQuantityRecipe;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IngredientQuantityRecipeRepository extends JpaRepository<IngredientQuantityRecipe, Long> {
}
