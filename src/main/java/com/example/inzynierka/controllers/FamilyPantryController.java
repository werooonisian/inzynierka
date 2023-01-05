package com.example.inzynierka.controllers;

import com.example.inzynierka.models.Account;
import com.example.inzynierka.models.FamilyPantry;
import com.example.inzynierka.models.Ingredient;
import com.example.inzynierka.services.FamilyPantryService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
@RequestMapping("/familyPantry")
public class FamilyPantryController {
    private final FamilyPantryService familyPantryService;

    public FamilyPantryController(FamilyPantryService familyPantryService) {
        this.familyPantryService = familyPantryService;
    }

    @PostMapping("/create")
    public ResponseEntity<FamilyPantry> createFamilyPantry(){
        return ResponseEntity.ok().body(familyPantryService.createFamilyPantry());
    }

    @GetMapping("/ingredients")
    public ResponseEntity<Set<Ingredient>> getIngredients(){
        return ResponseEntity.ok().body(familyPantryService.getIngredients());
    }

    @GetMapping("/owners")
    public ResponseEntity<Set<Account>> getOwners(){
        return ResponseEntity.ok().body(familyPantryService.getOwners());
    }

    @PostMapping("/ingredients/add/{ingredientId}")
    public void addIngredient(@PathVariable long ingredientId){
        familyPantryService.addIngredient(ingredientId);
    }

    @PostMapping("/ingredients/delete/{ingredientId}")
    public void deleteIngredient(@PathVariable long ingredientId){
        familyPantryService.deleteIngredient(ingredientId);
    }
}
