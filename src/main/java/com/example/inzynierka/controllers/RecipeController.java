package com.example.inzynierka.controllers;

import com.example.inzynierka.models.Recipe;
import com.example.inzynierka.services.RecipeService;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
public class RecipeController {

    private final RecipeService recipeService;

    public RecipeController(RecipeService recipeService) {
        this.recipeService = recipeService;
    }

    @PostMapping(value = "/addRecipe", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    private ResponseEntity<Recipe> addRecipe(@RequestPart Recipe recipe,
                                             @RequestParam(value = "file", required = false) MultipartFile[] imagesBytes){
        return ResponseEntity.ok().body(recipeService.addRecipe(recipe, imagesBytes));
    }
}
