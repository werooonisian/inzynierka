package com.example.inzynierka.services;

import com.example.inzynierka.models.PagedRecipeResult;
import com.example.inzynierka.models.Recipe;
import com.example.inzynierka.models.RecipeDataFilter;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Set;

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
