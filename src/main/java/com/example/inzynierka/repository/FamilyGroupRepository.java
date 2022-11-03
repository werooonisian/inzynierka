package com.example.inzynierka.repository;

import com.example.inzynierka.models.FamilyGroup;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FamilyGroupRepository extends JpaRepository<FamilyGroup, Long> {
}
