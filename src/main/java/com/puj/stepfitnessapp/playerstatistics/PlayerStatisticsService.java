package com.puj.stepfitnessapp.playerstatistics;

import com.puj.stepfitnessapp.player.Player;
import com.puj.stepfitnessapp.playerstatistics.completedchallenges.CompletedChallenges;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class PlayerStatisticsService {

    private final PlayerStatisticsRepository repository;


    @Autowired
    public PlayerStatisticsService(
            PlayerStatisticsRepository repository
    ) {
        this.repository = repository;
    }

    public void addStatistics(Player player) {
        final var playerStatistics = new PlayerStatistics(
                player.getUser_id(),
                player,
                new ArrayList<CompletedChallenges>(),
                0,
                0
        );
    }
}
