package com.example.inzynierka.controllers;

import com.example.inzynierka.models.GroceryList;
import com.example.inzynierka.models.Ingredient;
import com.example.inzynierka.services.GroceryListService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
public class GroceryListController {

    private final GroceryListService groceryListService;

    public GroceryListController(GroceryListService groceryListService) {
        this.groceryListService = groceryListService;
    }

    @PostMapping("/createGroceryList")
    public ResponseEntity<GroceryList> createGroceryList(@RequestBody String groceryListName) {
        return ResponseEntity.ok().body(groceryListService.createGroceryList(groceryListName));
    }

    @PostMapping("/{groceryListId}/addOwner/{accountId}")
    public ResponseEntity<GroceryList> addOwner(@PathVariable(value = "groceryListId") long groceryListId,
                                                @PathVariable(value = "accountId") long accountId){
        return ResponseEntity.ok().body(groceryListService.addOwner(accountId, groceryListId));
    }

    @PostMapping("/groceryList/{groceryListId}/addIngredient/{ingredientId}")
    public ResponseEntity<Ingredient> addIngredient(@PathVariable(value = "groceryListId") long groceryListId,
                                                    @PathVariable(value = "ingredientId") long ingredientId){
        return ResponseEntity.ok().body(groceryListService.addIngredient(groceryListId, ingredientId));
    }

    @DeleteMapping("/groceryList/{groceryListId}/deleteIngredient/{ingredientId}")
    public ResponseEntity<Ingredient> deleteIngredient(@PathVariable(value = "groceryListId") long groceryListId,
                                                       @PathVariable(value = "ingredientId") long ingredientId){
        return ResponseEntity.ok().body(groceryListService.deleteIngredient(groceryListId, ingredientId));
    }

    @GetMapping("/groceryList/{groceryListId}/getAllIngredients")
    public ResponseEntity<Set<Ingredient>> getAllIngredients(@PathVariable(value = "groceryListId") long groceryListId){
        return ResponseEntity.ok().body(groceryListService.getAllIngredients(groceryListId));
    }

    @PutMapping("/groceryList/{groceryListId}/delete")
    public void deleteGroceryList(@PathVariable(value = "groceryListId") long groceryListId){
        groceryListService.deleteGroceryList(groceryListId);
    }
}
