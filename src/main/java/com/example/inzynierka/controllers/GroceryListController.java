package com.example.inzynierka.controllers;

import com.example.inzynierka.models.Account;
import com.example.inzynierka.models.GroceryList;
import com.example.inzynierka.models.Ingredient;
import com.example.inzynierka.models.IngredientGroceryListRequest;
import com.example.inzynierka.services.GroceryListService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
@RequestMapping("/groceryList")
public class GroceryListController {

    private final GroceryListService groceryListService;

    public GroceryListController(GroceryListService groceryListService) {
        this.groceryListService = groceryListService;
    }

    @PostMapping("/create")
    public ResponseEntity<GroceryList> createGroceryList(@RequestBody String groceryListName) {
        return ResponseEntity.ok().body(groceryListService.createGroceryList(groceryListName));
    }

    @PostMapping("/{groceryListId}/addOwner/{accountId}")
    public ResponseEntity<GroceryList> addOwner(@PathVariable(value = "groceryListId") long groceryListId,
                                                @PathVariable(value = "accountId") long accountId){
        return ResponseEntity.ok().body(groceryListService.addOwner(accountId, groceryListId));
    }

    @PostMapping("/{groceryListId}/addIngredient")
    public void addIngredient(@RequestBody IngredientGroceryListRequest ingredientGroceryListRequest){
        groceryListService.addIngredient(ingredientGroceryListRequest);
    }

    @DeleteMapping("/{groceryListId}/deleteIngredient/{ingredientId}")
    public void deleteIngredient(@PathVariable(value = "groceryListId") long groceryListId,
                                                       @PathVariable(value = "ingredientId") long ingredientId){
        groceryListService.deleteIngredient(groceryListId, ingredientId);
    }

    @GetMapping("/{groceryListId}/getAllIngredients")
    public ResponseEntity<Set<Ingredient>> getAllIngredients(@PathVariable(value = "groceryListId") long groceryListId){
        return ResponseEntity.ok().body(groceryListService.getAllIngredients(groceryListId));
    }

    @PutMapping("/{groceryListId}/delete")
    public void deleteGroceryList(@PathVariable(value = "groceryListId") long groceryListId){
        groceryListService.deleteGroceryList(groceryListId);
    }

    @GetMapping()
    public ResponseEntity<Set<GroceryList>> getAllMyGroceryLists(){
        return ResponseEntity.ok().body(groceryListService.getAllMyGroceryLists());
    }

    @GetMapping("/{groceryListId}/getOwners")
    public ResponseEntity<Set<Account>> getOwners(@PathVariable long groceryListId){
        return ResponseEntity.ok().body(groceryListService.getOwners(groceryListId));
    }
}
