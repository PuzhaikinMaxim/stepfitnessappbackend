package com.puj.stepfitnessapp.duel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashSet;

public class PlayerSearchQueue {

    private final HashMap<Integer, LinkedHashSet<Long>> playerInQueueByLevels;

    public PlayerSearchQueue(int maxLevel) {
        playerInQueueByLevels = new HashMap<>();
        setPlayerInQueueByLevelsMap(maxLevel);
    }

    public LinkedHashSet<Long> getPlayerSetByLevel(int level) {
        var key = getLevelIntervalKey(level);
        return playerInQueueByLevels.get(key);
    }

    private int getLevelIntervalKey(int level) {
        return level % 10 != 0 ? (level / 10) + 1 : level / 10;
    }

    private void setPlayerInQueueByLevelsMap(int maxLevel) {
        for(int i = 1; i < maxLevel/10; i++){
            playerInQueueByLevels.put(i,new LinkedHashSet<>());
        }
    }
}
