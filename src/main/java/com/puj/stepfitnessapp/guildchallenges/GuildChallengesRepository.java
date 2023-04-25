package com.puj.stepfitnessapp.guildchallenges;

import com.puj.stepfitnessapp.guild.Guild;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

public interface GuildChallengesRepository extends JpaRepository<GuildChallenge, Long> {

    Optional<GuildChallenge> findGuildChallengeByGuildAndIsStartedTrue(Guild guild);

    @Modifying
    @Transactional
    void deleteByIsStartedFalse();

    Optional<List<GuildChallenge>> findGuildChallengeByGuild(Guild guild);

    Optional<GuildChallenge> findGuildChallengeByGuild_Owner_User_UserId(Long userId);
}
