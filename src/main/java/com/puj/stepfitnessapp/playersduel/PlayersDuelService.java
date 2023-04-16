package com.puj.stepfitnessapp.playersduel;

import com.puj.stepfitnessapp.duel.Duel;
import com.puj.stepfitnessapp.player.Player;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PlayersDuelService {

    private final PlayersDuelRepository playersDuelRepository;

    private final PlayersDuelMapper playersDuelMapper = new PlayersDuelMapper();

    @Autowired
    public PlayersDuelService(PlayersDuelRepository playersDuelRepository) {
        this.playersDuelRepository = playersDuelRepository;
    }

    public void addPlayerDuel(Player player, Duel duel) {
        var playerDuel = playersDuelMapper.mapToPlayersDuel(player, duel);
        playersDuelRepository.save(playerDuel);
    }

    public void decreaseOpponentHp(PlayersDuel player, PlayersDuel opponent, int amountOfSteps) {
        var opponentHp = opponent.getHp() - calculateAmountOfPoints(
                amountOfSteps,
                player.getPointsFixed(),
                player.getPointsMultiplier()
        );
        opponentHp = Math.max(opponentHp, 0);

        opponent.setHp(opponentHp);

        playersDuelRepository.save(opponent);
    }

    private int calculateAmountOfPoints(int amountOfSteps, int amountOfPointsFixed, double amountOfPointsMultiplier) {
        var amountOfPointsAdded = (amountOfPointsFixed * amountOfPointsFixed)/100;
        return (int) ((amountOfSteps + amountOfPointsAdded)*amountOfPointsMultiplier);
    }
}
