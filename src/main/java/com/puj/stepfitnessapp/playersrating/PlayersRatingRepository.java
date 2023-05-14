package com.puj.stepfitnessapp.playersrating;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface PlayersRatingRepository extends JpaRepository<PlayersRating, Long> {

    Optional<List<PlayersRating>> findTop100ByOrderByAmountOfStepsDesc();

    Optional<List<PlayersRating>> findTop100ByOrderByAmountOfDuelsWonDesc();

    @Query("SELECT p FROM PlayersRating p WHERE p.player.user_id = ?1")
    Optional<PlayersRating> getPlayersRatingByUserId(Long userId);
}
