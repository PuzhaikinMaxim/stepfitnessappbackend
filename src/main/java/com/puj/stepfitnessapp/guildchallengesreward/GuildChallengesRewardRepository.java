package com.puj.stepfitnessapp.guildchallengesreward;

import com.puj.stepfitnessapp.player.Player;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface GuildChallengesRewardRepository extends JpaRepository<GuildChallengesReward, Long> {

    Optional<GuildChallengesReward> getGuildChallengesRewardByPlayer(Player player);
}
