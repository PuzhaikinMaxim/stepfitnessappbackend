package com.puj.stepfitnessapp.guild;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface GuildRepository extends JpaRepository<Guild, Long> {

    @Query("SELECT g FROM Guild g JOIN Player p ON p.guild = g WHERE p.user_id = ?1")
    Optional<Guild> getGuildByUserId(Long userId);
}
