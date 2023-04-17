package com.puj.stepfitnessapp.duel;

import com.puj.stepfitnessapp.player.Player;
import com.puj.stepfitnessapp.playersduel.PlayersDuelMapper;

public class DuelMapper {

    private final PlayersDuelMapper playersDuelMapper = new PlayersDuelMapper();

    public Duel mapToDuel(Player firstPlayer, Player secondPlayer) {
        return new Duel();
    }

    public DuelDto mapDuelToDuelDto(Duel duel, Long userId) {
        boolean isDuelFinished = false;
        boolean isWon = false;
        boolean isCancelled = false;

        if(duel.getWinner() != null) isDuelFinished = true;
        if(duel.getWinner() != null && duel.getWinner().getUser_id().equals(userId)) isWon = true;
        if(duel.getLooser() != null && duel.getLooser().getUser_id().equals(userId) && duel.getIsDuelCancelled()) isCancelled = true;

        var duelD = duel;

        var playerOptional = duel.getPlayersDuel().stream().filter(
                (playersDuel) -> {
                    return playersDuel.getPlayer_id().equals(userId);
                }
        ).findFirst();

        var opponentOptional = duel.getPlayersDuel().stream().filter(PlayersDuel -> !PlayersDuel.getPlayer_id().equals(userId)).findFirst();

        var playerDuelDto = playersDuelMapper.mapPlayersDuelToPlayersDuelDto(playerOptional.orElse(null));

        var opponentDuelDto = playersDuelMapper.mapPlayersDuelToPlayersDuelDto(opponentOptional.orElse(null));

        return new DuelDto(
                playerDuelDto,
                opponentDuelDto,
                isDuelFinished,
                isWon,
                isCancelled
        );
    }
}
