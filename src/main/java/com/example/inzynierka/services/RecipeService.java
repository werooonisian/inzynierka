package com.example.inzynierka.services;

import com.example.inzynierka.models.Recipe;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Set;

public interface RecipeService {
    Recipe addRecipe(Recipe recipe, MultipartFile[] imagesBytes);
    String addToFavourite(long id);
    void deleteFromFavourite(long id);
    List<Recipe> getAllRecipes();
    Recipe getRecipe(Long id);
    void deleteMyRecipe(Long id);
    boolean isPrincipalsRecipe(Long id);
}
