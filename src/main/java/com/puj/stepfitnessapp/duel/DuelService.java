package com.puj.stepfitnessapp.duel;

import com.puj.stepfitnessapp.items.Item;
import com.puj.stepfitnessapp.items.ItemService;
import com.puj.stepfitnessapp.level.LevelService;
import com.puj.stepfitnessapp.player.Player;
import com.puj.stepfitnessapp.player.PlayerService;
import com.puj.stepfitnessapp.playersduel.PlayersDuel;
import com.puj.stepfitnessapp.playerstatistics.PlayerStatisticsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.Optional;

@Service
public class DuelService {

    private final DuelRepository duelRepository;

    private final PlayerSearchQueue playerSearchQueue;

    private final PlayerService playerService;

    private final PlayerStatisticsService playerStatisticsService;

    private final ItemService itemService;

    private final DuelMapper duelMapper = new DuelMapper();

    @Autowired
    public DuelService(
            DuelRepository duelRepository,
            LevelService levelService,
            PlayerService playerService,
            PlayerStatisticsService playerStatisticsService,
            ItemService itemService
    ) {
        this.duelRepository = duelRepository;
        this.playerService = playerService;
        this.playerStatisticsService = playerStatisticsService;
        this.itemService = itemService;
        var maxLevel = levelService.getMaximumLevel();
        playerSearchQueue = new PlayerSearchQueue(maxLevel);
    }

    public Optional<Duel> getDuelByUserId(Long userId) {
        return duelRepository.getDuelWithUserIfExists(userId);
    }

    public DuelMessageData tryFindOpponent(Long userId, String username) {
        var duelResponse = duelRepository.getDuelWithUserIfExists(userId);
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

            duel.setCancelDuelPlayer(player);
            duel.setWinner(opponent.get().getPlayer());
            duelRepository.save(duel);
        }
    }

    public FinishedDuelRewardDto claimReward(Long userId) {
        var player = playerService.getPlayerById(userId);
        var duelResponse = duelRepository.getDuelWithUserIfExists(userId);
        var xp = 0;
        var itemsList = new ArrayList<Item>();
        if(duelResponse.isPresent() && duelResponse.get().getWinner() != null){
            var duel = duelResponse.get();
            var duelPlayer = duel.getPlayersDuel().stream().filter(((playersDuel) -> {
                return playersDuel.getPlayer().getUser_id().equals(userId);
            })).findFirst().get();

            if(duel.getWinner().getUser_id().equals(userId)){
                var items = itemService.generateRewardItemsForDuel(
                        player.getLevel(),
                        opponent.getLevel(),
                        1.0
                );
                xp = player.getLevel()*5 + opponent.getLevel()*15;
                playerService.addInventoryItems(player, items);
                playerService.addPlayerXp(player, xp);
                if(duel.getFirstPlayer().getUser_id().equals(userId)){
                    duel.setFirstPlayer(null);
                }
                else{
                    duel.setSecondPlayer(null);
                }
                itemsList.addAll(items);
                playerStatisticsService.incrementAmountOfDuelsWon(player);
            }
            else if(!duel.getCancelDuelPlayer().getUser_id().equals(userId)){

            }
            else {

            }
        }

        return new FinishedDuelRewardDto(0, itemsList);
    }

    public void updateProgress(int amountOfSteps, Long userId, Duel duel) {
        if(duel.getFirstPlayer().getUser_id().equals(userId)){
            var secondPlayerHp = duel.getSecondPlayerHp() - calculateAmountOfPoints(
                    amountOfSteps,
                    duel.getFirstPlayerPointsFixed(),
                    duel.getFirstPlayerPointsMultiplier()
            );
            secondPlayerHp = Math.max(secondPlayerHp, 0);

            duel.setSecondPlayerHp(secondPlayerHp);

            if(secondPlayerHp == 0){
                duel.setWinner(duel.getFirstPlayer());
            }
        }
        else{
            var firstPlayerHp = duel.getSecondPlayerHp() - calculateAmountOfPoints(
                    amountOfSteps,
                    duel.getSecondPlayerPointsFixed(),
                    duel.getSecondPlayerPointsMultiplier()
            );
            firstPlayerHp = Math.max(firstPlayerHp, 0);

            duel.setFirstPlayerHp(firstPlayerHp);

            if(firstPlayerHp == 0){
                duel.setWinner(duel.getSecondPlayer());
            }
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
        playersInQueue.remove(firstPlayer.getUser_id());
        playersInQueue.remove(secondPlayer.getUser_id());
        return new DuelMessageData(
                firstPlayer == player || secondPlayer == player,
                firstPlayer.getUser().getUsername(),
                secondPlayer.getUser().getUsername()
        );
    }

    /*
    public Optional<Duel> getDuelByUserId(Long userId) {
        return duelRepository.getDuelWithUserIfExists(userId);
    }

    public DuelMessageData tryFindOpponent(Long userId, String username) {
        var duelResponse = duelRepository.getDuelWithUserIfExists(userId);
        if(duelResponse.isPresent()){
            var duel = duelResponse.get();
            var firstPlayerId = duel.getFirstPlayer().getUser_id();
            var opponent = firstPlayerId.equals(userId) ? duel.getSecondPlayer() : duel.getFirstPlayer();
            return new DuelMessageData(true, username, opponent.getUser().getUsername());
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

            var opponent = userId.equals(
                    duel.getFirstPlayer().getUser_id()
            ) ? duel.getSecondPlayer() : duel.getFirstPlayer();

            duel.setCancelDuelPlayer(player);
            duel.setWinner(opponent);
            duelRepository.save(duel);
        }
    }

    public FinishedDuelRewardDto claimReward(Long userId) {
        var player = playerService.getPlayerById(userId);
        var duelResponse = duelRepository.getDuelWithUserIfExists(userId);
        var xp = 0;
        var itemsList = new ArrayList<Item>();
        if(duelResponse.isPresent()){
            var duel = duelResponse.get();
            var opponent = duel.getFirstPlayer().getUser_id().equals(userId) ? duel.getSecondPlayer() : duel.getFirstPlayer();
            if(duel.getWinner() == player){
                var items = itemService.generateRewardItemsForDuel(
                        player.getLevel(),
                        opponent.getLevel(),
                        1.0
                );
                xp = player.getLevel()*5 + opponent.getLevel()*15;
                playerService.addInventoryItems(player, items);
                playerService.addPlayerXp(player, xp);
                if(duel.getFirstPlayer().getUser_id().equals(userId)){
                    duel.setFirstPlayer(null);
                }
                else{
                    duel.setSecondPlayer(null);
                }
                itemsList.addAll(items);
                playerStatisticsService.incrementAmountOfDuelsWon(player);
            }
            else if(duel.getWinner() != player && duel.getCancelDuelPlayer() != player){
                var items = itemService.generateRewardItemsForDuel(
                        player.getLevel(),
                        opponent.getLevel(),
                        0.25
                );
                xp = player.getLevel() + opponent.getLevel()*5;
                playerService.addPlayerXp(player, xp);
                playerService.addInventoryItems(player, items);
                if(duel.getFirstPlayer().getUser_id().equals(userId)){
                    duel.setFirstPlayer(null);
                }
                else{
                    duel.setSecondPlayer(null);
                }
                itemsList.addAll(items);
                playerStatisticsService.incrementAmountOfDuelsLost(player);
            }
            else{
                if(duel.getFirstPlayer().getUser_id().equals(userId)){
                    duel.setFirstPlayer(null);
                }
                else{
                    duel.setSecondPlayer(null);
                }
                playerStatisticsService.incrementAmountOfDuelsLost(player);
            }
            duelRepository.save(duel);
            if(duel.getFirstPlayer() == null && duel.getSecondPlayer() == null){
                duelRepository.delete(duel);
            }
        }
        return new FinishedDuelRewardDto(0, itemsList);
    }

     */

    /*
    public void updateProgress(int amountOfSteps, Long userId, Duel duel) {
        if(duel.getFirstPlayer().getUser_id().equals(userId)){
            var secondPlayerHp = duel.getSecondPlayerHp() - calculateAmountOfPoints(
                    amountOfSteps,
                    duel.getFirstPlayerPointsFixed(),
                    duel.getFirstPlayerPointsMultiplier()
            );
            secondPlayerHp = Math.max(secondPlayerHp, 0);

            duel.setSecondPlayerHp(secondPlayerHp);

            if(secondPlayerHp == 0){
                duel.setWinner(duel.getFirstPlayer());
            }
        }
        else{
            var firstPlayerHp = duel.getSecondPlayerHp() - calculateAmountOfPoints(
                    amountOfSteps,
                    duel.getSecondPlayerPointsFixed(),
                    duel.getSecondPlayerPointsMultiplier()
            );
            firstPlayerHp = Math.max(firstPlayerHp, 0);

            duel.setFirstPlayerHp(firstPlayerHp);

            if(firstPlayerHp == 0){
                duel.setWinner(duel.getSecondPlayer());
            }
        }

        duelRepository.save(duel);
    }

     */

    /*
    private int calculateAmountOfPoints(int amountOfSteps, int amountOfPointsFixed, double amountOfPointsMultiplier) {
        var amountOfPointsAdded = (amountOfPointsFixed * amountOfPointsFixed)/100;
        return (int) ((amountOfSteps + amountOfPointsAdded)*amountOfPointsMultiplier);
    }

     */
}
