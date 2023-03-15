package com.puj.stepfitnessapp.challenge;

import com.puj.stepfitnessapp.playerstatistics.PlayerStatisticsService;
import com.puj.stepfitnessapp.playerstatistics.completedchallenges.CompletedChallenges;
import com.puj.stepfitnessapp.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController()
@RequestMapping("challenges")
public class ChallengeController {

    private final ChallengeService challengeService;
    private final PlayerStatisticsService playerStatisticsService;

    @Autowired
    public ChallengeController(
            ChallengeService challengeService,
            PlayerStatisticsService playerStatisticsService
    ) {
        this.challengeService = challengeService;
        this.playerStatisticsService = playerStatisticsService;
    }

    @GetMapping(value = "/{level}")
    public ResponseEntity<List<ChallengeDto>> getChallengeListByLevel(@PathVariable int level) {
        final var result = challengeService.getChallengeListByLevel(level);
        final var completedChallengesByLevel =
                playerStatisticsService.getStatistics(getUserId()).getCompletedChallenges();

        if(result == null){
            return createResponseEntity(HttpStatus.NOT_FOUND, null);
        }
        else {
            for(CompletedChallenges completedChallenges : completedChallengesByLevel){
                if(completedChallenges.getLevel() == level){
                    for(ChallengeDto challengeDto : result){
                        var challengeId = Long.valueOf(challengeDto.getId());
                        if(completedChallenges.getChallenges().contains(challengeId)){
                            result.remove(challengeDto);
                        }
                    }
                    break;
                }
            }
            return createResponseEntity(HttpStatus.OK, result);
        }
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
