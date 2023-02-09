package com.example.inzynierka.repository;

import com.example.inzynierka.models.GroceryList;
import com.example.inzynierka.models.Ingredient;
import com.example.inzynierka.models.IngredientQuantityGroceryList;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IngredientQuantityGroceryListRepository extends JpaRepository<IngredientQuantityGroceryList, Long> {
    Optional<IngredientQuantityGroceryList> findByIngredient(Ingredient ingredient);
    Optional<IngredientQuantityGroceryList> findByIngredientAndGroceryList(Ingredient ingredient, GroceryList groceryList);
}
