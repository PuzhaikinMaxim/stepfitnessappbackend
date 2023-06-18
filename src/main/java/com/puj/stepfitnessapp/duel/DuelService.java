package com.puj.stepfitnessapp.duel;

import com.puj.stepfitnessapp.items.Item;
import com.puj.stepfitnessapp.items.ItemService;
import com.puj.stepfitnessapp.level.LevelService;
import com.puj.stepfitnessapp.player.Player;
import com.puj.stepfitnessapp.player.PlayerService;
import com.puj.stepfitnessapp.playersduel.PlayersDuelService;
import com.puj.stepfitnessapp.playerstatistics.PlayerStatisticsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class DuelService {

    private final DuelRepository duelRepository;

    private final PlayerSearchQueue playerSearchQueue;

    private final PlayerService playerService;

    private final PlayerStatisticsService playerStatisticsService;

    private final ItemService itemService;

    private final PlayersDuelService playersDuelService;

    private final DuelMapper duelMapper = new DuelMapper();

    @Autowired
    public DuelService(
            DuelRepository duelRepository,
            LevelService levelService,
            PlayerService playerService,
            PlayerStatisticsService playerStatisticsService,
            PlayersDuelService playersDuelService,
            ItemService itemService
    ) {
        this.duelRepository = duelRepository;
        this.playerService = playerService;
        this.playerStatisticsService = playerStatisticsService;
        this.itemService = itemService;
        this.playersDuelService = playersDuelService;
        var maxLevel = levelService.getMaximumLevel();
        playerSearchQueue = new PlayerSearchQueue(maxLevel);
    }

    public Optional<Duel> getDuelByUserId(Long userId) {
        return duelRepository.getDuelWithUserIfExists(userId);
    }

    public DuelMessageData tryFindOpponent(Long userId, String username) {
        var duelResponse = getDuelByUserId(userId);
        if(duelResponse.isPresent()){
            var duel = duelResponse.get();
            var firstPlayerName = "";
            var secondPlayerName = "";
            var playersNames = Arrays.asList(firstPlayerName, secondPlayerName);
            var duelPlayers = duel.getPlayersDuel();
            for(int i = 0; i < duelPlayers.size(); i++){
                playersNames.set(i, duelPlayers.get(i).getPlayer().getUser().getUsername());
            }
            return new DuelMessageData(true, firstPlayerName, secondPlayerName);
        }
        var player = playerService.getPlayerById(userId);
        var playersInQueueSet = playerSearchQueue.getPlayerSetByLevel(player.getLevel());
        if(!playersInQueueSet.contains(player.getUser_id())){
            playersInQueueSet.add(player.getUser_id());
        }
        else{
            return startDuel(playersInQueueSet, player);
        }
        return new DuelMessageData(false, username, null);
    }

    public void removeFromQueue(Long userId) {
        var player = playerService.getPlayerById(userId);
        var playersInQueueSet = playerSearchQueue.getPlayerSetByLevel(player.getLevel());
        playersInQueueSet.remove(userId);
    }

    public void cancelDuel(Long userId) {
        var player = playerService.getPlayerById(userId);
        var response = getDuelByUserId(userId);
        if(response.isPresent()){
            var duel = response.get();

            var opponent = duel.getPlayersDuel().stream().filter((playersDuel) -> !playersDuel.getPlayer().getUser_id().equals(userId)).findFirst();

            if(opponent.isEmpty()) return;

            duel.setLooser(player);
            duel.setIsDuelCancelled(true);
            duel.setWinner(opponent.get().getPlayer());
            duelRepository.save(duel);
        }
    }

    public FinishedDuelRewardDto claimReward(Long userId) {
        var player = playerService.getPlayerById(userId);
        var duelResponse = getDuelByUserId(userId);
        var xp = 0;

        List<Item> itemsList;

        if(duelResponse.isEmpty() || duelResponse.get().getWinner() == null) {
            return new FinishedDuelRewardDto(0, Collections.emptyList());
        }

        var duel = duelResponse.get();

        var duelPlayerResponse = duel.getPlayersDuel().stream().filter(((playersDuel) -> {
            return playersDuel.getPlayer().getUser_id().equals(userId);
        })).findFirst();

        if(duelPlayerResponse.isEmpty()) return new FinishedDuelRewardDto(0, Collections.emptyList());

        var duelPlayer = duelPlayerResponse.get();

        if(duel.getWinner() == player){
            itemsList = itemService.generateRewardItemsForDuel(
                    player.getLevel(),
                    duel.getLooser().getLevel(),
                    1.0
            );

            xp = player.getLevel()*5 + duel.getLooser().getLevel()*15;
            playerService.addPlayerXp(player, xp);
            playerService.addItems(player, itemsList);
            playersDuelService.removePlayerDuel(duelPlayer);
            playerStatisticsService.incrementAmountOfDuelsWon(player);
            return new FinishedDuelRewardDto(xp, itemsList);
        }

        if(duel.getIsDuelCancelled()){
            playersDuelService.removePlayerDuel(duelPlayer);
            playerStatisticsService.incrementAmountOfDuelsLost(player);
            return new FinishedDuelRewardDto(xp, Collections.emptyList());
        }

        itemsList = itemService.generateRewardItemsForDuel(
                player.getLevel(),
                duel.getLooser().getLevel(),
                0.25
        );
        xp = player.getLevel() + duel.getWinner().getLevel()*5;
        playerService.addPlayerXp(player, xp);
        playerService.addItems(player, itemsList);
        playerStatisticsService.incrementAmountOfDuelsLost(player);
        playersDuelService.removePlayerDuel(duelPlayer);

        if(duel.getPlayersDuel().size() == 0){
            duelRepository.delete(duel);
        }

        return new FinishedDuelRewardDto(xp, itemsList);
    }

    public Boolean isDuelNotFinished(Long userId) {
        var duel = getDuelByUserId(userId);
        return duel.isPresent();
    }

    public void updateProgress(int amountOfSteps, Long userId, Duel duel) {
        var opponentResponse = duel.getPlayersDuel().stream().filter((playersDuel) -> {
            return !playersDuel.getPlayer().getUser_id().equals(userId);
        }).findFirst();

        var playerResponse = duel.getPlayersDuel().stream().filter((playersDuel) -> {
            return playersDuel.getPlayer().getUser_id().equals(userId);
        }).findFirst();

        if(opponentResponse.isEmpty() || playerResponse.isEmpty()) return;

        var opponent = opponentResponse.get();

        var player = playerResponse.get();

        playersDuelService.decreaseOpponentHp(player, opponent, amountOfSteps);

        if(opponent.getHp() == 0){
            duel.setWinner(player.getPlayer());
            duel.setLooser(opponent.getPlayer());
        }

        duelRepository.save(duel);
    }

    private synchronized DuelMessageData startDuel(LinkedHashSet<Long> playersInQueue, Player player) {
        var array = playersInQueue.toArray();
        if(array.length < 2) return new DuelMessageData(
                false,
                player.getUser().getUsername(),
                null
        );
        var firstPlayer = playerService.getPlayerById((Long) array[0]);
        var secondPlayer = playerService.getPlayerById((Long) array[1]);
        var duel = duelMapper.mapToDuel(firstPlayer,secondPlayer);

        duelRepository.save(duel);

        playersDuelService.addPlayerDuel(firstPlayer, duel);
        playersDuelService.addPlayerDuel(secondPlayer, duel);

        playersInQueue.remove(firstPlayer.getUser_id());
        playersInQueue.remove(secondPlayer.getUser_id());

        return new DuelMessageData(
                firstPlayer == player || secondPlayer == player,
                firstPlayer.getUser().getUsername(),
                secondPlayer.getUser().getUsername()
        );
    }
}
