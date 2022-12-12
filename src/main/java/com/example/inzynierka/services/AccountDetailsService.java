package com.example.inzynierka.services;

import com.example.inzynierka.models.Recipe;

import java.util.Set;

public interface AccountPreferencesService {
    Set<Recipe> getMyRecipes();
}
