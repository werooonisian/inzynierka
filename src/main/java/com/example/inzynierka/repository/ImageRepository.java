package com.example.inzynierka.repository;

import com.example.inzynierka.models.Image;
import com.example.inzynierka.models.Recipe;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.Set;

@Repository
public interface ImageRepository extends JpaRepository<Image, Long> {
    Optional<Image> findById(Long id);
    Set<Image> findByRecipe(Recipe recipe);
}
