package com.puj.stepfitnessapp.guildchallengesreward;

import com.puj.stepfitnessapp.items.Item;
import com.puj.stepfitnessapp.player.inventory.item.InventoryItem;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@AllArgsConstructor
@Getter
@Setter
public class FinishedGuildChallengeReward {

    private int xp;

    private List<InventoryItem> reward;
}
