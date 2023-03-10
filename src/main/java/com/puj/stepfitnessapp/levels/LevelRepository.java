package com.puj.stepfitnessapp.levels;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface LevelRepository extends JpaRepository<Level, Integer> {

    @Query("SELECT l FROM Level l where l.level = ?1")
    Optional<Level> getLevel(int level);
}
