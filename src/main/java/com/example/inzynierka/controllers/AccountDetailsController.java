package com.example.inzynierka.controllers;

import com.example.inzynierka.models.DietType;
import com.example.inzynierka.models.GroceryList;
import com.example.inzynierka.models.Ingredient;
import com.example.inzynierka.models.Recipe;
import com.example.inzynierka.services.AccountDetailsService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
@RequestMapping("/accountDetails")
public class AccountDetailsController {
    private final AccountDetailsService accountDetailsService;

    public AccountDetailsController(AccountDetailsService accountDetailsService) {
        this.accountDetailsService = accountDetailsService;
    }

    @GetMapping("/myRecipes")
    public ResponseEntity<Set<Recipe>> getMyRecipes(){
        return ResponseEntity.ok().body(accountDetailsService.getMyRecipes());
    }

    @GetMapping("/favouriteRecipes")
    public ResponseEntity<Set<Recipe>> getFavouriteRecipes(){
        return ResponseEntity.ok().body(accountDetailsService.getFavouriteRecipes());
    }

    @GetMapping("/avoidedIngredients")
    public ResponseEntity<Set<Ingredient>> getAvoidedIngredients(){
        return ResponseEntity.ok().body(accountDetailsService.getAvoidedIngredients());
    }

    @GetMapping("/myDiets")
    public ResponseEntity<Set<DietType>> getMyDiets(){
        return ResponseEntity.ok().body(accountDetailsService.getMyDiets());
    }

    @PostMapping("/avoidedIngredients/add/{ingredientId}")
    public void addIngredientToAvoided(@PathVariable long ingredientId){
        accountDetailsService.addIngredientToAvoided(ingredientId);
    }

    @DeleteMapping("/avoidedIngredients/delete/{ingredientId}")
    public void deleteIngredient(@PathVariable long ingredientId){
        accountDetailsService.deleteIngredientFromAvoided(ingredientId);
    }

    @GetMapping("/myGroceryLists")
    public ResponseEntity<Set<GroceryList>> getAllMyGroceryLists(){
        return ResponseEntity.ok().body(accountDetailsService.getAllMyGroceryLists());
    }

    @PostMapping("/myDiets/add")
    public ResponseEntity<DietType> addDietToMyDiets(@RequestBody String dietType){
        return ResponseEntity.ok().body(accountDetailsService.addDietToMyDiets(dietType));
    }

    @PostMapping("/myDiets/delete")
    public void deleteDietFromMyDiets(@RequestBody String dietType){
        accountDetailsService.deleteDietFromMyDiets(dietType);
    }
}
