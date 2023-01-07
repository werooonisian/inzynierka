package com.example.inzynierka.repository;

import com.example.inzynierka.models.FamilyPantryToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface FamilyPantryTokenRepository extends JpaRepository<FamilyPantryToken, Long> {
    Optional<FamilyPantryToken> findByToken(String token);
}
