package com.example.inzynierka.services.implementations;

import com.example.inzynierka.exceptions.AddRecipeException;
import com.example.inzynierka.models.DietType;
import com.example.inzynierka.models.Image;
import com.example.inzynierka.models.Recipe;
import com.example.inzynierka.repository.AccountRepository;
import com.example.inzynierka.repository.ImageRepository;
import com.example.inzynierka.repository.IngredientRepository;
import com.example.inzynierka.repository.RecipeRepository;
import com.example.inzynierka.services.RecipeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.*;

@Service
@Slf4j
public class RecipeServiceImpl implements RecipeService {
    private final RecipeRepository recipeRepository;
    private final AccountRepository accountRepository;
    private final ImageRepository imageRepository;

    private final IngredientRepository ingredientRepository;

    public RecipeServiceImpl(RecipeRepository recipeRepository,
                             AccountRepository accountRepository,
                             ImageRepository imageRepository,
                             IngredientRepository ingredientRepository) {
        this.recipeRepository = recipeRepository;
        this.accountRepository = accountRepository;
        this.imageRepository = imageRepository;
        this.ingredientRepository = ingredientRepository;
    }

    @Override
    public Recipe addRecipe(Recipe recipe, MultipartFile[] imagesBytes) {
        accountRepository.findByLogin(SecurityContextHolder.getContext().getAuthentication().getName())
                .ifPresentOrElse(account -> {
                    checkDietTypes(recipe);
                    recipe.setAddedBy(account.getAccountPreferences());
                    if(imagesBytes!=null && imagesBytes.length!=0){
                        uploadImages(recipe, imagesBytes);
                    }
                    recipeRepository.save(recipe); //TODO: Sprawdzić czy dobre
                    log.info("Recipe has been added by" + account.getLogin());
                },
                        () -> {throw new IllegalStateException("Token not found");});

        return recipe;
    }

    @Override
    public void uploadImages(Recipe recipe, MultipartFile[] imagesBytes) {
        Arrays.stream(imagesBytes).filter(multipartFile -> multipartFile.getOriginalFilename() != null)
                .filter(multipartFile -> !StringUtils.cleanPath(multipartFile.getOriginalFilename()).contains(".."))
                .forEach(multipartFile -> {
                    try {
                        Image image = new Image();
                        image.setBytes(multipartFile.getBytes());
                        image.setName(multipartFile.getOriginalFilename());
                        image.setRecipe(recipe);
                        recipe.getImages().add(image);
                        recipeRepository.save(recipe);
                        imageRepository.save(image);
                    } catch (IOException e) {
                        throw new AddRecipeException("Image couldn't be added");
                    }
                });
    }

    public void checkDietTypes(Recipe recipe){
        EnumSet<DietType> allDietTypes = EnumSet.allOf(DietType.class);
        Iterator<DietType> iterator = allDietTypes.iterator();
        while (iterator.hasNext()) {
            recipe.getIngredientsList().forEach(ingredient -> {
                ingredientRepository.findById(ingredient.getId()).ifPresent(foundIngredient -> {
                    if(!foundIngredient.getDietTypes().contains(iterator.next()))
                    {
                        iterator.remove();
                    }
                });
            });
        }
        recipe.getDietTypes().addAll(allDietTypes);
    }
}
