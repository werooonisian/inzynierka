package com.example.inzynierka.models;

public enum DietType {
    VEGAN("wegańska"),
    VEGETARIAN("wegetariańska"),
    GLUTEN_FREE("bezglutenowa"),
    LACTOSE_FREE("bez laktozy"),
    MEAT_DIET("dieta mięsna"),
    LIGHT("lekka"),
    ;

    DietType(String name) {
        this.name = name;
    }

    final String name;
}
