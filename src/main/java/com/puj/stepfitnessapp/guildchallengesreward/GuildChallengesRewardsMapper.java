package com.puj.stepfitnessapp.guildchallengesreward;

import com.puj.stepfitnessapp.guild.Guild;
import com.puj.stepfitnessapp.player.Player;
import com.puj.stepfitnessapp.player.inventory.item.InventoryItem;

import java.util.List;

public class GuildChallengesRewardsMapper {

    public GuildChallengesReward mapToGuildChallengesReward(
            Player player,
            Guild guild,
            int xp,
            List<InventoryItem> rewards
            ) {
        return new GuildChallengesReward(
                guild,
                player,
                xp,
                rewards
        );
    }
}
