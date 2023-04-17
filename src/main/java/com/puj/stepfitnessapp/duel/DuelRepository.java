package com.puj.stepfitnessapp.duel;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface DuelRepository extends JpaRepository<Duel, Long> {


    @Query("SELECT d FROM Duel d JOIN PlayersDuel pd ON pd.duel = d WHERE pd.player_id = ?1")
    Optional<Duel> getDuelWithUserIfExists(Long userId);

    /*
    @Query("SELECT 1 FROM Duel d WHERE d.firstPlayer.user_id = ?1 OR d.secondPlayer.user_id = ?1")
    Optional<Integer> getIsUserExists(Long userId);

    @Query("SELECT d FROM Duel d WHERE d.firstPlayer.user_id =?1 OR d.secondPlayer.user_id = ?1")
    Optional<Duel> getDuelWithUserIfExists(Long userId);

     */
}
