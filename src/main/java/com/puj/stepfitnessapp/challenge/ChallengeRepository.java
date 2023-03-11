package com.puj.stepfitnessapp.challenge;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface ChallengeRepository extends JpaRepository<Challenge, Long> {

    @Query("SELECT c FROM Challenge c WHERE c.level.challengeLevel = ?1")
    Optional<List<Challenge>> getChallengeListByLevel(int challengeLevel);

    @Query("SELECT c FROM Challenge c WHERE c.challengeId = ?1")
    Optional<Challenge> getChallengeByChallengeId(long challengeId);
}
