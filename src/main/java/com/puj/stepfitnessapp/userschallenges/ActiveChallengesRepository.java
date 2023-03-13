package com.puj.stepfitnessapp.userschallenges;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
public interface ActiveChallengesRepository extends JpaRepository<UserChallenges, UserChallengesKey> {

    @Query("SELECT uc FROM UserChallenges uc WHERE uc.id.userId = ?1")
    Optional<UserChallenges> getUserChallengesByUser(long userId);

    @Query("UPDATE UserChallenges uc SET uc.progress = uc.progress + ?1, uc.amountOfSteps = uc.amountOfSteps + ?2 WHERE uc.id.userId = ?3")
    @Modifying
    @Transactional
    void updateUserChallengesProgress(int newProgress, int amountOfSteps, long userId);
}
