package com.puj.stepfitnessapp.guildchallengesreward;

import com.puj.stepfitnessapp.items.Item;
import com.puj.stepfitnessapp.items.ItemDto;
import com.puj.stepfitnessapp.player.inventory.item.InventoryItem;
import com.puj.stepfitnessapp.player.inventory.item.dto.InventoryItemDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@AllArgsConstructor
@Getter
@Setter
public class FinishedGuildChallengeReward {

    private int amountOfXp;

    private List<ItemDto> items;
}
