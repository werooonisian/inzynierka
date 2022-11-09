package com.example.inzynierka.repository;

import com.example.inzynierka.models.GroceryList;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface GroceryListRepository extends JpaRepository<GroceryList, Long> {
}
