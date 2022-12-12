package com.example.inzynierka.services.implementations;

import com.example.inzynierka.exceptions.AddRecipeException;
import com.example.inzynierka.exceptions.ResourceNotFoundException;
import com.example.inzynierka.models.*;
import com.example.inzynierka.repository.*;
import com.example.inzynierka.services.AccountDetailsService;
import com.example.inzynierka.services.AccountService;
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
    private final AccountDetailsRepository accountDetailsRepository;
    private final AccountService accountService;
    private final AccountDetailsService accountDetailsService;

    public RecipeServiceImpl(RecipeRepository recipeRepository,
                             AccountRepository accountRepository,
                             ImageRepository imageRepository,
                             IngredientRepository ingredientRepository,
                             AccountDetailsRepository accountDetailsRepository,
                             AccountService accountService,
                             AccountDetailsService accountDetailsService) {
        this.recipeRepository = recipeRepository;
        this.accountRepository = accountRepository;
        this.imageRepository = imageRepository;
        this.ingredientRepository = ingredientRepository;
        this.accountDetailsRepository = accountDetailsRepository;
        this.accountService = accountService;
        this.accountDetailsService = accountDetailsService;
    }

    @Override
    public Recipe addRecipe(Recipe recipe, MultipartFile[] imagesBytes) {
        accountRepository.findByLogin(SecurityContextHolder.getContext().getAuthentication().getName())
                .ifPresentOrElse(account -> {
                    checkDietTypes(recipe);
                    recipe.setAddedBy(account.getAccountDetails());
                    if(imagesBytes!=null && imagesBytes.length!=0){
                        uploadImages(recipe, imagesBytes);
                    }
                    recipeRepository.save(recipe);
                    log.info("Recipe has been added by" + account.getLogin());
                },
                        () -> {throw new ResourceNotFoundException("Token not found");});

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
        Account account = accountService.getPrincipal();

        AccountDetails accountDetails = accountDetailsRepository.getReferenceById(account.getId());
        recipeRepository.findById(id).ifPresentOrElse(recipe -> {
            accountDetails.getFavouriteRecipes().add(recipe);
            recipe.getFavouritedBy().add(accountDetails);
            accountDetailsRepository.save(accountDetails);
            recipeRepository.save(recipe);
            log.info("User with id {} added recipe with id {} to favorite", account.getId(), recipe.getId());
        },
                () -> {throw new ResourceNotFoundException(String.format("Recipe with id %s not found", id));});


        return String.format("Account with id %s added recipe with id %s", account.getId(), id);
    }

    @Override
    public void deleteFromFavourite(long id) {
        AccountDetails accountDetails = accountDetailsService.getPrincipalsDetails();
        recipeRepository.findById(id).ifPresentOrElse(recipe -> {
            accountDetails.getFavouriteRecipes().remove(recipe);
            recipe.getFavouritedBy().remove(accountDetails);
            accountDetailsRepository.save(accountDetails);
            recipeRepository.save(recipe);
        },
                () -> {throw new ResourceNotFoundException(String.format("Recipe with id %s not found", id));});
    }

    @Override
    public List<Recipe> getAllRecipes() {
        return recipeRepository.findAll();
    }

    @Override
    public Recipe getRecipe(Long id) {
        return recipeRepository.findById(id).orElseThrow(
                () -> {throw new ResourceNotFoundException(String.format("Recipe with id %s not found", id));});
    }

    @Override
    public void deleteMyRecipe(Long id) {
        if(isPrincipalsRecipe(id)){
            Recipe recipe = getRecipe(id);
            //List<Image> allImages = imageRepository.findAll();
            //Iterator<Image> iterator = allImages.iterator();
            //accountDetailsService.getMyRecipes().remove(recipe);
            accountDetailsRepository.findAll().forEach(accountDetails -> {
                accountDetails.getFavouriteRecipes().remove(recipe);
                accountDetailsRepository.save(accountDetails);
            });
            //while(iterator.hasNext())
            recipeRepository.delete(recipe);

            log.info("Recipe with id {} was deleted", id);
        }
    }

    @Override
    public boolean isPrincipalsRecipe(Long id) {
        Recipe recipe = getRecipe(id);
        return accountDetailsService.getPrincipalsDetails().getAddedRecipes().contains(recipe);
    }
}
