package com.example.inzynierka.controllers;

import com.example.inzynierka.models.GroceryList;
import com.example.inzynierka.services.GroceryListService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

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
}
