package com.puj.stepfitnessapp.userschallenges;

import com.puj.stepfitnessapp.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserChallengesRepository extends JpaRepository<UserChallenges, UserChallengesKey> {

    @Query("SELECT uc FROM UserChallenges uc WHERE uc.id.userId = ?1")
    Optional<UserChallenges> getUserChallengesByUser(long userId);
}
