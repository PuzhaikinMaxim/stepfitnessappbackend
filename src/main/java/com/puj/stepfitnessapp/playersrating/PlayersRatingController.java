package com.puj.stepfitnessapp.playersrating;

import com.puj.stepfitnessapp.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("players_rating")
public class PlayersRatingController {

    private final PlayersRatingService playersRatingService;

    @Autowired
    public PlayersRatingController(PlayersRatingService playersRatingService) {
        this.playersRatingService = playersRatingService;
    }

    @GetMapping("get_top_players_by_step_amount")
    public ResponseEntity<List<PlayersRatingDto>> getTopOneHundredPlayersByStepAmount() {
        return createResponseEntity(
                HttpStatus.OK,
                playersRatingService.getTopOneHundredPlayersByStepAmount(getUserId())
        );
    }

    @GetMapping("get_top_players_by_amount_of_duels_won")
    public ResponseEntity<List<PlayersRatingDto>> getTopOneHundredPlayersByAmountOfDuelsWon() {
        return createResponseEntity(
                HttpStatus.OK,
                playersRatingService.getTopOneHundredPlayersByAmountOfDuelsWon(getUserId())
        );
    }

    @GetMapping("get_rating_list_update_countdown")
    public ResponseEntity<String> getRatingListUpdateCountdown() {
        return createResponseEntity(
                HttpStatus.OK,
                playersRatingService.getRatingListUpdateCountdown()
        );
    }

    private long getUserId() {
        final var userDetails = (User) SecurityContextHolder.getContext().getAuthentication().getDetails();
        return userDetails.getUserId();
    }

    private <T> ResponseEntity<T> createResponseEntity(HttpStatus status, T body) {
        return ResponseEntity
                .status(status)
                .body(body);
    }
}
