package com.puj.stepfitnessapp.rarity;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface RarityRepository extends JpaRepository<Rarity, Integer> {

    @Query("SELECT r FROM Rarity r WHERE r.rarityLevel = ?1")
    Optional<Rarity> getRarityByRarityLevel(int rarityLevel);
}
