package com.puj.stepfitnessapp.items;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class ItemService {

    private final ItemsRepository repository;

    private final ScheduledItemList scheduledItemList;

    private SplittableRandom splittableRandom = new SplittableRandom();

    @Autowired
    public ItemService(ItemsRepository repository, ScheduledItemList scheduledItemList) {
        this.repository = repository;
        this.scheduledItemList = scheduledItemList;
    }

    public List<Item> generateRewardItemsForChallenge(int challengeLevel, int playerLevel) {
        final var items = new ArrayList<Item>();
        final var itemGroupsByRarity = scheduledItemList.getItemGroupsByRarity();
        final var maxRarity = itemGroupsByRarity.size();
        int maxAmountOfItems = 3 + (challengeLevel / 3);
        final var rewardChances = getRewardChances(challengeLevel, playerLevel, maxRarity);
        for(int i = maxAmountOfItems; i >= 0; i--){
            Item addedItem = null;
            for(int rarity = maxRarity; rarity >= 2; rarity--){
                int chance = rewardChances.get(rarity);
                if(splittableRandom.nextInt(1,101) <= chance){
                    var itemsList = itemGroupsByRarity.get(rarity);
                    var listSize = itemsList.size();
                    addedItem = itemsList.get(splittableRandom.nextInt(0, listSize));
                }
            }
            if(addedItem == null){
                var itemsList = itemGroupsByRarity.get(1);
                var listSize = itemsList.size();
                addedItem = itemsList.get(splittableRandom.nextInt(0, listSize));
            }
            items.add(addedItem);
        }
        return items;
    }

    private Map<Integer, Integer> getRewardChances(int challengeLevel, int playerLevel, int maxRarity){
        final var rewardChances = new HashMap<Integer, Integer>();
        var percentLeft = 100;
        for(int i = maxRarity; i >= 2; i--){
            int chance = (playerLevel/5+challengeLevel*10)/i;
            rewardChances.put(i, 100 - chance);
            percentLeft -= chance;
            if(percentLeft <= 0) break;
        }
        return rewardChances;
    }
}
