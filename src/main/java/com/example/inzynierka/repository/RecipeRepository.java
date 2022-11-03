package com.example.inzynierka.repository;

import com.example.inzynierka.models.Recipe;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RecipeRepository extends JpaRepository<Recipe, Long> {
    List<Recipe> findAll();
    Optional<Recipe> findByName(String name);
}
