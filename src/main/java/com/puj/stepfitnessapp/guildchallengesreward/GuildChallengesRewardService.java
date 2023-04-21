package com.puj.stepfitnessapp.guildchallengesreward;

import com.puj.stepfitnessapp.guild.Guild;
import com.puj.stepfitnessapp.guildchallenges.GuildChallenge;
import com.puj.stepfitnessapp.items.ItemService;
import com.puj.stepfitnessapp.player.Player;
import com.puj.stepfitnessapp.player.PlayerService;
import com.puj.stepfitnessapp.player.inventory.item.InventoryItemMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class GuildChallengesRewardService {

    private final GuildChallengesRewardRepository guildChallengesRewardRepository;

    private final GuildChallengesRewardsMapper guildChallengesRewardsMapper = new GuildChallengesRewardsMapper();

    private final InventoryItemMapper inventoryItemMapper = new InventoryItemMapper();

    private final ItemService itemService;

    private final PlayerService playerService;

    @Autowired
    public GuildChallengesRewardService(
            GuildChallengesRewardRepository guildChallengesRewardRepository,
            ItemService itemService,
            PlayerService playerService
    ) {
        this.guildChallengesRewardRepository = guildChallengesRewardRepository;
        this.itemService = itemService;
        this.playerService = playerService;
    }

    public void generateReward(Guild guild, GuildChallenge guildChallenge) {
        var guildChallengeRewards = guild.getGuildChallengesRewards();
        guildChallengesRewardRepository.deleteAll(guildChallengeRewards);
        int collectiveLevel;
        var guildParticipants = guild.getPlayers();
        collectiveLevel = guildParticipants.stream().mapToInt(Player::getLevel).sum();
        var xp = (100 + collectiveLevel/2) * guildChallenge.getDifficultyRewardMultiplier();
        var challengeRewards = new ArrayList<GuildChallengesReward>();
        for(Player player : guildParticipants){
            var items = itemService.generateRewardItemsForGuildChallenge(
                    player.getLevel(),
                    collectiveLevel,
                    guildChallenge.getDifficultyRewardMultiplier()
            );
            challengeRewards.add(guildChallengesRewardsMapper.mapToGuildChallengesReward(
                    player,
                    guild,
                    (int) xp,
                    inventoryItemMapper.mapItemListToInventoryItemList(items)
            ));
        }
        guildChallengesRewardRepository.saveAll(challengeRewards);
    }

    public FinishedGuildChallengeReward claimReward(Player player) {
        var response = guildChallengesRewardRepository.getGuildChallengesRewardByPlayer(player);
        if(response.isEmpty()) return null;

        var guildChallengeReward = response.get();
        var finishedGuildChallengeReward = new FinishedGuildChallengeReward(
                guildChallengeReward.getXp(),
                guildChallengeReward.getReward()
        );

        playerService.addInventoryItems(player, guildChallengeReward.getReward());
        playerService.addPlayerXp(player, guildChallengeReward.getXp());

        guildChallengesRewardRepository.delete(guildChallengeReward);

        return finishedGuildChallengeReward;
    }
}
