package com.example.inzynierka.models;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "IngredientQuantity")
public class IngredientQuantity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private double quantity;
    @NotNull(message = "Unit may not be empty")
    @Enumerated(EnumType.STRING)
    private IngredientUnit unit;

}
