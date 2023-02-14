package com.example.inzynierka.services;

import com.example.inzynierka.results.PagedRecipeResult;
import com.example.inzynierka.models.Recipe;
import com.example.inzynierka.payload.RecipeDataFilter;
import org.springframework.web.multipart.MultipartFile;

public interface RecipeService {
    Recipe addRecipe(Recipe recipe, MultipartFile[] imagesBytes);
    String addToFavourite(long id);
    void deleteFromFavourite(long id);
    PagedRecipeResult getAllRecipes(int pageNumber, RecipeDataFilter recipeDataFilter);
    Recipe getRecipe(Long id);
    void deleteMyRecipe(Long id);
    Recipe editMyRecipe(Recipe recipe);
    boolean isPrincipalsRecipe(Long id);
}
