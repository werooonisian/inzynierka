package com.example.inzynierka.controllers;

import com.example.inzynierka.models.Recipe;
import com.example.inzynierka.repository.ImageRepository;
import com.example.inzynierka.services.RecipeService;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.util.List;

@RestController
public class RecipeController {

    private final RecipeService recipeService;

    public RecipeController(RecipeService recipeService, ImageRepository imageRepository) {
        this.recipeService = recipeService;
    }

    @PostMapping(value = "/addRecipe", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Recipe> addRecipe(@Valid @RequestPart Recipe recipe, //TODO: do przemyślenia
                                             @RequestParam(value = "file", required = false) MultipartFile[] imagesBytes){
        return ResponseEntity.ok().body(recipeService.addRecipe(recipe, imagesBytes));
    }

    @PostMapping("/{recipeId}/addToFavourite")
    public ResponseEntity<String> addToFavourite(@PathVariable long recipeId){
        return ResponseEntity.ok().body(recipeService.addToFavourite(recipeId));
    }

    @GetMapping("/recipe/all")
    public ResponseEntity<List<Recipe>> getAllRecipes(){
        return ResponseEntity.ok().body(recipeService.getAllRecipes());
    }







 /*   @GetMapping("/downloadFile/{fileName:.+}")
    public ResponseEntity<Resource> downloadFile(@PathVariable String fileName, HttpServletRequest request) {
        // Load file as Resource
        Image databaseFile = imageRepository.findById(2L).get();

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(MediaType.MULTIPART_FORM_DATA_VALUE))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + fileName + "\"")
                .body(new ByteArrayResource(databaseFile.getBytes()));
    }*/
}
