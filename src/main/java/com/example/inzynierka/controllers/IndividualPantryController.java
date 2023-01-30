package com.example.inzynierka.controllers;

import com.example.inzynierka.models.IndividualPantry;
import com.example.inzynierka.models.Ingredient;
import com.example.inzynierka.services.IndividualPantryService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/pantry/individual")
public class IndividualPantryController {
    private final IndividualPantryService individualPantryService;

    public IndividualPantryController(IndividualPantryService individualPantryService) {
        this.individualPantryService = individualPantryService;
    }

    @PostMapping("/{pantryId}/addIngredient/{ingredientId}")
    public ResponseEntity<Ingredient> addIngredientToPantry(@PathVariable(value = "pantryId") long pantryId,
                                                            @PathVariable(value = "ingredientId") long ingredientId){
        return ResponseEntity.ok().body(individualPantryService.addIngredient(pantryId, ingredientId));
    }

    @DeleteMapping("/{pantryId}/deleteIngredient/{ingredientId}")
    public ResponseEntity<Ingredient> deleteIngredientFromPantry(@PathVariable(value = "pantryId") long pantryId,
                                                                 @PathVariable(value = "ingredientId") long ingredientId){
        return ResponseEntity.ok().body(individualPantryService.deleteIngredient(pantryId, ingredientId));
    }

    @GetMapping()
    public ResponseEntity<IndividualPantry> getAllIngredientsFromPantry(){
        return ResponseEntity.ok().body(individualPantryService.getAllIngredients());
    }

    @PostMapping("/moveToFamilyPantry/{ingredientId}")
    public void moveToFamilyPantry(@PathVariable long ingredientId){
        individualPantryService.moveToFamilyPantry(ingredientId);
    }

}
