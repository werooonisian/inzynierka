package com.example.inzynierka.repository;

import com.example.inzynierka.models.IngredientQuantity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IngredientQuantityRepository extends JpaRepository<IngredientQuantity, Long> {
}
