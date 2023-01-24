package com.example.inzynierka.services.implementations;

import com.example.inzynierka.models.Ingredient;
import com.example.inzynierka.repository.IngredientRepository;
import com.example.inzynierka.services.IngredientService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
class IngredientServiceImpl implements IngredientService {

    private final IngredientRepository ingredientRepository;

    IngredientServiceImpl(IngredientRepository ingredientRepository) {
        this.ingredientRepository = ingredientRepository;
    }

    @Override
    public List<Ingredient> getAll() {
        return ingredientRepository.findAll();
    }
}
