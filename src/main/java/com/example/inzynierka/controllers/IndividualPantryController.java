package com.example.inzynierka.controllers;

import com.example.inzynierka.models.Ingredient;
import com.example.inzynierka.services.IndividualPantryService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
public class IndividualPantryController {
    private final IndividualPantryService individualPantryService;

    public IndividualPantryController(IndividualPantryService individualPantryService) {
        this.individualPantryService = individualPantryService;
    }

    @PostMapping("/pantry/individual/{pantryId}/addIngredient/{ingredientId}")
    public ResponseEntity<Ingredient> addIngredientToPantry(@PathVariable(value = "pantryId") long pantryId,
                                                            @PathVariable(value = "ingredientId") long ingredientId){
        return ResponseEntity.ok().body(individualPantryService.addIngredient(pantryId, ingredientId));
    }

    @DeleteMapping("/pantry/individual/{pantryId}/deleteIngredient/{ingredientId}")
    public ResponseEntity<Ingredient> deleteIngredientFromPantry(@PathVariable(value = "pantryId") long pantryId,
                                                                 @PathVariable(value = "ingredientId") long ingredientId){
        return ResponseEntity.ok().body(individualPantryService.deleteIngredient(pantryId, ingredientId));
    }

    @GetMapping("/pantry/individual")
    public ResponseEntity<Set<Ingredient>> getAllIngredientsFromPantry(){
        return ResponseEntity.ok().body(individualPantryService.getAllIngredients());
    }
}
