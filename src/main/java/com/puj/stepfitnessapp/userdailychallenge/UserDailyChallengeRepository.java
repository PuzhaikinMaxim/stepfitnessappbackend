package com.puj.stepfitnessapp.userdailychallenge;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface UserDailyChallengeRepository extends JpaRepository<UserDailyChallenge, Long> {

    @Query("SELECT uc FROM UserDailyChallenge uc WHERE uc.user_id = ?1")
    Optional<UserDailyChallenge> getUserDailyChallengeByUser_id(Long userId);
}
