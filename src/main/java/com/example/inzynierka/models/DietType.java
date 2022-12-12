package com.example.inzynierka.models;

public enum DietType {
    VEGAN("wegańska"),
    VEGETARIAN("wegetariańska"),
    GLUTEN_FREE("bezglutenowa"),
    LACTOSE_FREE("bez laktozy"),
    DIABETIC("cukrzycowa"), //(cukrycowa) //TODO: IDK CZY TAK CZY NIE
    LIGHT("lekka"),
    ;

    DietType(String name) {
        this.name = name;
    }

    final String name;
}
