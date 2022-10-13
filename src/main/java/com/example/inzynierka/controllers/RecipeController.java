package com.example.inzynierka.controllers;

import com.example.inzynierka.models.Recipe;
import com.example.inzynierka.services.RecipeService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RecipeController {

    private final RecipeService recipeService;

    public RecipeController(RecipeService recipeService) {
        this.recipeService = recipeService;
    }

    @PostMapping("/addRecipe")
    private ResponseEntity<Recipe> addRecipe(@RequestBody Recipe recipe){
        return ResponseEntity.ok().body(recipeService.addRecipe(recipe));
    }
}
