package com.example.inzynierka.controllers;

import com.example.inzynierka.models.PagedRecipeResult;
import com.example.inzynierka.models.Recipe;
import com.example.inzynierka.models.RecipeDataFilter;
import com.example.inzynierka.repository.ImageRepository;
import com.example.inzynierka.services.AccountDetailsService;
import com.example.inzynierka.services.RecipeService;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;

@RestController
@RequestMapping("/recipe")
public class RecipeController {

    private final RecipeService recipeService;
    private final AccountDetailsService accountDetailsService;

    public RecipeController(RecipeService recipeService,
                            ImageRepository imageRepository,
                            AccountDetailsService accountDetailsService) {
        this.recipeService = recipeService;
        this.accountDetailsService = accountDetailsService;
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

    @PostMapping("/{recipeId}/deleteFromFavourite")
    public void deleteFromFavourite(@PathVariable long recipeId){
        recipeService.deleteFromFavourite(recipeId);
    }

    @PostMapping("/all")
    public ResponseEntity<PagedRecipeResult> getAllRecipes(@RequestBody RecipeDataFilter recipeDataFilter,
                                                           @RequestParam int pageNumber){
        return ResponseEntity.ok().body(recipeService.getAllRecipes(pageNumber, recipeDataFilter));
    }

    @GetMapping("/{recipeId}")
    public ResponseEntity<Recipe> getRecipe(@PathVariable long recipeId){
        return ResponseEntity.ok().body(recipeService.getRecipe(recipeId));
    }

    @DeleteMapping("/{recipeId}/delete")
    public void deleteMyRecipe(@PathVariable long recipeId){
        recipeService.deleteMyRecipe(recipeId);
    }

    @PutMapping() //TODO: dodać dodawanie zdjęcia
    public ResponseEntity<Recipe> editMyRecipe(@RequestBody Recipe recipe){
        return ResponseEntity.ok().body(recipeService.editMyRecipe(recipe));
    }

    @GetMapping("/{recipeId}/isRecipeInFavourited")
    public boolean isRecipeInFavourited(@PathVariable Long recipeId){
        return accountDetailsService.isRecipeInFavourited(recipeId);
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
