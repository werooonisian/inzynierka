package com.example.inzynierka.repository;

import com.example.inzynierka.models.IndividualPantry;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IndividualPantryRepository extends JpaRepository<IndividualPantry, Long> {

}
