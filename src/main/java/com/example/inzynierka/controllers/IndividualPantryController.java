package com.example.inzynierka.controllers;

import com.example.inzynierka.models.Ingredient;
import com.example.inzynierka.services.IndividualPantryService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class IndividualPantryController {
    private final IndividualPantryService individualPantryService;

    public IndividualPantryController(IndividualPantryService individualPantryService) {
        this.individualPantryService = individualPantryService;
    }

    @PostMapping("/pantry/{pantryId}/addIngredient/{ingredientId}")
    public ResponseEntity<Ingredient> addIngredientToPantry(@PathVariable(value = "pantryId") long pantryId,
                                                            @PathVariable(value = "ingredientId") long ingredientId){
        return ResponseEntity.ok().body(individualPantryService.addIngredient(pantryId, ingredientId));
    }

    @DeleteMapping("/pantry/{pantryId}/deleteIngredient/{ingredientId}")
    public ResponseEntity<Ingredient> deleteIngredientFromPantry(@PathVariable(value = "pantryId") long pantryId,
                                                                 @PathVariable(value = "ingredientId") long ingredientId){
        return ResponseEntity.ok().body(individualPantryService.deleteIngredient(pantryId, ingredientId));
    }
}
