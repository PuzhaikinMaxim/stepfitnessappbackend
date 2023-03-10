package com.puj.stepfitnessapp.player;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface PlayerRepository extends JpaRepository<Player, Long> {

    @Query("SELECT p FROM Player p WHERE p.user_id = ?1")
    Optional<Player> findPlayerByUser_id(Long userId);
}
