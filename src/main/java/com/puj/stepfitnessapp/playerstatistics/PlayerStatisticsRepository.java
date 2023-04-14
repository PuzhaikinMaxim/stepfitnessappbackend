package com.puj.stepfitnessapp.playerstatistics;

import com.puj.stepfitnessapp.duel.Duel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface PlayerStatisticsRepository extends JpaRepository<PlayerStatistics, Long> {

    @Query("SELECT new com.puj.stepfitnessapp.playerstatistics.DuelStatistics(s.amountOfDuelsWon, s.amountOfDuelsLost, s.rank) FROM PlayerStatistics s WHERE s.player_id = ?1")
    Optional<DuelStatistics> getDuelStatistics(Long userId);
}
