package com.puj.stepfitnessapp.challenge;

import com.puj.stepfitnessapp.playerstatistics.PlayerStatisticsService;
import com.puj.stepfitnessapp.playerstatistics.completedchallenges.CompletedChallenges;
import com.puj.stepfitnessapp.user.User;
import com.puj.stepfitnessapp.userschallenges.ActiveChallengesController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.function.Predicate;

@RestController()
@RequestMapping("challenges")
public class ChallengeController {

    private final ChallengeService challengeService;
    private final PlayerStatisticsService playerStatisticsService;
    private final ActiveChallengesController activeChallengesController;

    @Autowired
    public ChallengeController(
            ChallengeService challengeService,
            PlayerStatisticsService playerStatisticsService,
            ActiveChallengesController activeChallengesController
    ) {
        this.challengeService = challengeService;
        this.playerStatisticsService = playerStatisticsService;
        this.activeChallengesController = activeChallengesController;
    }

    @GetMapping(value = "/{level}")
    public ResponseEntity<List<ChallengeDto>> getChallengeListByLevel(@PathVariable int level) {
        final var result = challengeService.getChallengeListByLevel(level);
        final var completedChallengesByLevel =
                playerStatisticsService.getStatistics(getUserId()).getCompletedChallenges();

        final var activeChallenge = activeChallengesController.getUserChallengesByUser();
        if(activeChallenge.getBody() != null){
            result.removeIf(challengeDto -> challengeDto.getId() == activeChallenge.getBody().getChallengeId());
        }
        if(result == null){
            return createResponseEntity(HttpStatus.NOT_FOUND, null);
        }
        for(CompletedChallenges completedChallenges : completedChallengesByLevel){
            if(completedChallenges.getLevel() == level){
                result.removeIf(challengeDto -> {
                    var challengeId = Long.valueOf(challengeDto.getId());
                    return completedChallenges.getChallenges().contains(challengeId);
                });
                break;
            }
        }
        return createResponseEntity(HttpStatus.OK, result);
    }

    @GetMapping("/challenge_statistics/{level}")
    public ResponseEntity<ChallengeStatistics> getChallengeStatistics(@PathVariable Integer level) {
        return createResponseEntity(HttpStatus.OK, challengeService.getChallengeStatistics(level, getUserId()));
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
