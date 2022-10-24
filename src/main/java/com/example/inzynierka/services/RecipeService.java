package com.example.inzynierka.services;

import com.example.inzynierka.models.Recipe;
import org.springframework.web.multipart.MultipartFile;

import java.util.Set;

public interface RecipeService {
    Recipe addRecipe(Recipe recipe, MultipartFile[] imagesBytes);
    String addToFavourite(long id);
}
