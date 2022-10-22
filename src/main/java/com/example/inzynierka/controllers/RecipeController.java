package com.example.inzynierka.controllers;

import com.example.inzynierka.models.Image;
import com.example.inzynierka.models.Recipe;
import com.example.inzynierka.repository.ImageRepository;
import com.example.inzynierka.services.RecipeService;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;

@RestController
public class RecipeController {

    private final RecipeService recipeService;
    private final ImageRepository imageRepository;

    public RecipeController(RecipeService recipeService, ImageRepository imageRepository) {
        this.recipeService = recipeService;
        this.imageRepository = imageRepository;
    }

    @PostMapping(value = "/addRecipe", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    private ResponseEntity<Recipe> addRecipe(@RequestPart Recipe recipe,
                                             @RequestParam(value = "file", required = false) MultipartFile[] imagesBytes){
        return ResponseEntity.ok().body(recipeService.addRecipe(recipe, imagesBytes));
    }

    @GetMapping("/downloadFile/{fileName:.+}")
    public ResponseEntity<Resource> downloadFile(@PathVariable String fileName, HttpServletRequest request) {
        // Load file as Resource
        Image databaseFile = imageRepository.findById(2L).get();

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(MediaType.MULTIPART_FORM_DATA_VALUE))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + fileName + "\"")
                .body(new ByteArrayResource(databaseFile.getBytes()));
    }
}
