package com.puj.stepfitnessapp.items;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class ItemService {

    private final ItemsRepository repository;

    private final ScheduledItemList scheduledItemList;

    private SplittableRandom splittableRandom = new SplittableRandom();

    private static final int ONE_HUNDRED = 101;

    private static final int MIN_NOT_GUARANTIED_RARITY = 2;

    private static final int AMOUNT_OF_ITEMS_BASE = 2;

    @Autowired
    public ItemService(ItemsRepository repository, ScheduledItemList scheduledItemList) {
        this.repository = repository;
        this.scheduledItemList = scheduledItemList;
    }

    public List<Item> generateRewardItemsForChallenge(int challengeLevel, int playerLevel) {
        final var items = new ArrayList<Item>();
        final var itemGroupsByRarity = scheduledItemList.getItemGroupsByRarity();
        final var maxRarity = itemGroupsByRarity.size();
        int maxAmountOfItems = AMOUNT_OF_ITEMS_BASE + (challengeLevel / 3);
        final var rewardChances = getRewardChancesForChallenge(challengeLevel, playerLevel, maxRarity);
        for(int i = maxAmountOfItems; i >= 0; i--){
            Item addedItem = null;
            for(int rarity = maxRarity; rarity >= MIN_NOT_GUARANTIED_RARITY; rarity--){
                int chance = rewardChances.get(rarity);
                if(splittableRandom.nextInt(1,ONE_HUNDRED) >= chance){
                    var itemsList = itemGroupsByRarity.get(rarity);
                    var listSize = itemsList.size();
                    addedItem = itemsList.get(splittableRandom.nextInt(0, listSize));
                    break;
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

    private Map<Integer, Integer> getRewardChancesForChallenge(int challengeLevel, int playerLevel, int maxRarity){
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

    public Item generateRewardItemForDailyChallenge(int amountOfSteps, int playerLevel) {
        Item item = null;
        final var itemGroupsByRarity = scheduledItemList.getItemGroupsByRarity();
        final var maxRarity = itemGroupsByRarity.size() - 2;
        final var rewardChances = getRewardChancesForDailyChallenge(
                amountOfSteps,
                playerLevel,
                maxRarity
        );
        for(int rarity = maxRarity; rarity >= 1; rarity--){
            int chance = rewardChances.get(rarity);
            if(splittableRandom.nextInt(1,101) >= chance){
                var itemsList = itemGroupsByRarity.get(rarity);
                var listSize = itemsList.size();
                item = itemsList.get(splittableRandom.nextInt(0, listSize));
                break;
            }
        }
        return item;
    }

    private Map<Integer, Integer> getRewardChancesForDailyChallenge(int amountOfSteps, int playerLevel, int maxRarity){
        final var rewardChances = new HashMap<Integer, Integer>();
        var percentLeft = 100;
        for(int i = maxRarity; i >= 1; i--){
            int chance = (amountOfSteps/100+playerLevel)/i;
            rewardChances.put(i, 100 - chance);
            percentLeft -= chance;
            if(percentLeft <= 0) break;
        }
        return rewardChances;
    }
}
