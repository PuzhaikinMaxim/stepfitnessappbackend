package com.puj.stepfitnessapp.guildchallenges;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface GuildChallengesRepository extends JpaRepository<GuildChallenge, Long> {

    Optional<GuildChallenge> findGuildChallengeByGuildChallengeIdAndIsStartedTrue(Long guildId);
}
