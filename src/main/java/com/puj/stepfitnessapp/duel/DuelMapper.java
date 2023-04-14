package com.puj.stepfitnessapp.duel;

import com.puj.stepfitnessapp.player.Player;
import lombok.AllArgsConstructor;

public class DuelMapper {

    public Duel mapToDuel(Player firstPlayer, Player secondPlayer) {
        return new Duel(
                firstPlayer,
                secondPlayer,
                firstPlayer.calculateHp(10000),
                secondPlayer.calculateHp(10000),
                firstPlayer.calculatePointMultiplier(),
                secondPlayer.calculatePointMultiplier(),
                firstPlayer.calculateAmountOfAdditionalPoints(),
                secondPlayer.calculateAmountOfAdditionalPoints(),
                null
        );
    }
    
    public DuelDto mapDuelToDuelDto(Duel duel, Long userId) {
        PlayerDuelData playerDuelData;
        PlayerDuelData opponentDuelData;

        boolean isDuelFinished = false;
        boolean isWon = false;

        if(duel.getWinner() != null) isDuelFinished = true;
        if(duel.getWinner() != null && duel.getWinner().getUser_id().equals(userId)) isWon = true;

        if(duel.getFirstPlayer().getUser_id().equals(userId)){
            playerDuelData = new PlayerDuelData(
                    duel.getFirstPlayer().getUser().getUsername(),
                    duel.getFirstPlayerHp(),
                    duel.getFirstPlayerInitialHp()
            );
            opponentDuelData = new PlayerDuelData(
                    duel.getSecondPlayer().getUser().getUsername(),
                    duel.getSecondPlayerHp(),
                    duel.getSecondPlayerInitialHp()
            );
        }
        else{
            playerDuelData = new PlayerDuelData(
                    duel.getSecondPlayer().getUser().getUsername(),
                    duel.getSecondPlayerHp(),
                    duel.getSecondPlayerInitialHp()
            );
            opponentDuelData = new PlayerDuelData(
                    duel.getFirstPlayer().getUser().getUsername(),
                    duel.getFirstPlayerHp(),
                    duel.getFirstPlayerInitialHp()
            );
        }

        return new DuelDto(
                playerDuelData.hp,
                playerDuelData.initialHp,
                opponentDuelData.hp,
                opponentDuelData.initialHp,
                playerDuelData.username,
                opponentDuelData.username,
                isDuelFinished,
                isWon
        );
    }

    @AllArgsConstructor
    private static class PlayerDuelData {
        String username;
        int hp;
        int initialHp;
    }
}
