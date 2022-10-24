package com.example.inzynierka.services.implementations;

import com.example.inzynierka.exceptions.AccountNotFoundException;
import com.example.inzynierka.exceptions.AddRecipeException;
import com.example.inzynierka.exceptions.RecipeNotFoundException;
import com.example.inzynierka.models.*;
import com.example.inzynierka.repository.*;
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
    private final AccountPreferencesRepository accountPreferencesRepository;

    public RecipeServiceImpl(RecipeRepository recipeRepository,
                             AccountRepository accountRepository,
                             ImageRepository imageRepository,
                             IngredientRepository ingredientRepository,
                             AccountPreferencesRepository accountPreferencesRepository) {
        this.recipeRepository = recipeRepository;
        this.accountRepository = accountRepository;
        this.imageRepository = imageRepository;
        this.ingredientRepository = ingredientRepository;
        this.accountPreferencesRepository = accountPreferencesRepository;
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
                        () -> {throw new AccountNotFoundException("Token not found");});

        return recipe;
    }

    private void uploadImages(Recipe recipe, MultipartFile[] imagesBytes) {
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

    private void checkDietTypes(Recipe recipe){
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

    @Override
    public String addToFavourite(long id) {
        Account account = accountRepository
                .findByLogin(SecurityContextHolder.getContext().getAuthentication().getName())
                .orElseThrow(() -> {
                    throw new AccountNotFoundException("Token not found");});

        AccountPreferences accountPreferences = accountPreferencesRepository.getReferenceById(account.getId());   //TODO: czy to wgl dziłaął
        recipeRepository.findById(id).ifPresentOrElse(recipe -> {
            accountPreferences.getFavouriteRecipes().add(recipe);
            recipe.getFavouritedBy().add(accountPreferences);
            accountPreferencesRepository.save(accountPreferences);
            recipeRepository.save(recipe);
        },
                () -> {throw new RecipeNotFoundException(String.format("Recipe with id %s not found", id));});


        return String.format("Account with id %s added recipe with id %s", account.getId(), id);
    }
}
