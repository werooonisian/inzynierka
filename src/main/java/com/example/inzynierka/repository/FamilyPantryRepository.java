package com.example.inzynierka.repository;

import com.example.inzynierka.models.FamilyPantry;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FamilyPantryRepository extends JpaRepository<FamilyPantry, Long> {
}
