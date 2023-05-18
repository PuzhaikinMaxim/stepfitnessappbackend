package com.puj.stepfitnessapp.playersrating;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class ScheduledPlayersRating {

    private PlayersRatingRepository playersRatingRepository;

    @Autowired
    public ScheduledPlayersRating(
            PlayersRatingRepository playersRatingRepository
    ) {
        this.playersRatingRepository = playersRatingRepository;
    }

    @Scheduled(cron = "1 0 0 1 * *")
    public void resetPlayersRating() {
        playersRatingRepository.deleteAll();
    }
}
