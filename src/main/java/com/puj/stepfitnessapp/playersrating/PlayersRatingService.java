package com.puj.stepfitnessapp.playersrating;

import com.puj.stepfitnessapp.player.Player;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.List;

@Service
public class PlayersRatingService {

    private final PlayersRatingRepository playersRatingRepository;

    private final PlayersRatingMapper playersRatingMapper = new PlayersRatingMapper();

    private final Long ONE_MINUTE = 60L;

    private final Long ONE_HOUR = 60 * ONE_MINUTE;

    private final Long ONE_DAY = 24 * ONE_HOUR;

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

    public String getRatingListUpdateCountdown() {
        var now = LocalDateTime.now().toEpochSecond(ZoneOffset.UTC);
        var resetDate = LocalDateTime.now()
                .plusMonths(1)
                .withDayOfMonth(1)
                .withHour(0)
                .withMinute(0)
                .withSecond(1)
                .toEpochSecond(ZoneOffset.UTC);
        var res = Math.max(0, resetDate - now);
        var countdown = "";
        if((res / ONE_DAY) != 0){
            countdown = countdown + (res / ONE_DAY) + "д ";
            res = res % ONE_DAY;
        }
        if((res / ONE_HOUR) != 0){
            countdown = countdown + (res / ONE_HOUR) + "ч ";
            res = res % ONE_HOUR;
        }
        if((res / ONE_MINUTE) != 0){
            countdown = countdown + (res / ONE_MINUTE) + "м ";
            res = res % ONE_MINUTE;
        }
        countdown = countdown + res + "с";
        return countdown;
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
