package com.example.inzynierka.exceptions;

public class GroceryListNotFound extends RuntimeException{
    public GroceryListNotFound(String message) {
        super(message);
    }
}
