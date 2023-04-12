package com.puj.stepfitnessapp.duel;

import com.puj.stepfitnessapp.level.LevelService;
import com.puj.stepfitnessapp.player.Player;
import com.puj.stepfitnessapp.player.PlayerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.LinkedHashSet;

@Service
public class DuelService {

    private final DuelRepository duelRepository;

    private final PlayerSearchQueue playerSearchQueue;

    private final PlayerService playerService;

    private final DuelMapper duelMapper = new DuelMapper();

    @Autowired
    public DuelService(DuelRepository duelRepository, LevelService levelService, PlayerService playerService) {
        this.duelRepository = duelRepository;
        this.playerService = playerService;
        var maxLevel = levelService.getMaximumLevel();
        playerSearchQueue = new PlayerSearchQueue(maxLevel);
    }

    public Boolean tryFindGame(Long userId) {
        var player = playerService.getPlayerById(userId);
        var playersInQueueSet = playerSearchQueue.getPlayerSetByLevel(player.getLevel());
        if(!playersInQueueSet.contains(player.getUser_id())){
            playersInQueueSet.add(player.getUser_id());
        }
        else{
            return startDuel(playersInQueueSet, player);
        }
        return false;
    }

    private synchronized Boolean startDuel(LinkedHashSet<Long> playersInQueue, Player player) {
        var array = playersInQueue.toArray();
        if(array.length < 2) return false;
        var firstPlayer = playerService.getPlayerById((Long) array[0]);
        var secondPlayer = playerService.getPlayerById((Long) array[1]);
        var duel = duelMapper.mapToDuel(firstPlayer,secondPlayer);
        duelRepository.save(duel);
        return firstPlayer == player || secondPlayer == player;
    }
}
