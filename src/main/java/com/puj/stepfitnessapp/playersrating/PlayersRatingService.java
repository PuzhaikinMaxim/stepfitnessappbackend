package com.puj.stepfitnessapp.playersrating;

import com.puj.stepfitnessapp.player.Player;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PlayersRatingService {

    private final PlayersRatingRepository playersRatingRepository;

    private final PlayersRatingMapper playersRatingMapper = new PlayersRatingMapper();

    @Autowired
    public PlayersRatingService(PlayersRatingRepository playersRatingRepository) {
        this.playersRatingRepository = playersRatingRepository;
    }

    public List<PlayersRatingDto> getTopOneHundredPlayersByStepAmount(Long userId) {
        var playersRatingListResponse
                = playersRatingRepository.findTop100ByOrderByAmountOfStepsDesc();
        if(playersRatingListResponse.isEmpty()) return List.of();
        var playersRatingList = playersRatingListResponse.get();
        return playersRatingMapper.mapToPlayersRatingDtoList(
                playersRatingList,
                playersRatingRepository.getPlayersRatingByUserId(userId).orElse(null)
        );
    }

    public List<PlayersRatingDto> getTopOneHundredPlayersByAmountOfDuelsWon(Long userId) {
        var playersRatingListResponse
                = playersRatingRepository.findTop100ByOrderByAmountOfDuelsWonDesc();
        if(playersRatingListResponse.isEmpty()) return List.of();
        var playersRatingList = playersRatingListResponse.get();
        return playersRatingMapper.mapToPlayersRatingDtoList(
                playersRatingList,
                playersRatingRepository.getPlayersRatingByUserId(userId).orElse(null)
        );
    }

    public void addAmountOfStepsForPlayer(Player player, Integer amountOfSteps) {
        var playerRatingResponse = playersRatingRepository.getPlayersRatingByUserId(player.getUser_id());
        if(playerRatingResponse.isEmpty()){
            var playersRating = new PlayersRating(
                    player,
                    amountOfSteps,
                    0
            );
            playersRatingRepository.save(playersRating);
            return;
        }
        var playerRating = playerRatingResponse.get();
        amountOfSteps = playerRating.getAmountOfSteps() + amountOfSteps;
        playerRating.setAmountOfSteps(amountOfSteps);
        playersRatingRepository.save(playerRating);
    }

    public void incrementAmountOfDuelsWonForPlayer(Player player) {
        var playerRatingResponse = playersRatingRepository.getPlayersRatingByUserId(player.getUser_id());
        if(playerRatingResponse.isEmpty()){
            var playersRating = new PlayersRating(
                    player,
                    0,
                    1
            );
            playersRatingRepository.save(playersRating);
            return;
        }
        var playerRating = playerRatingResponse.get();
        var amountOfDuelsWon = playerRating.getAmountOfDuelsWon() + 1;
        playerRating.setAmountOfDuelsWon(amountOfDuelsWon);
        playersRatingRepository.save(playerRating);
    }
}
