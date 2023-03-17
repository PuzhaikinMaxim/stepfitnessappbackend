package com.puj.stepfitnessapp.challengelevel;

import com.puj.stepfitnessapp.challenge.ChallengeService;
import com.puj.stepfitnessapp.player.PlayerService;
import com.puj.stepfitnessapp.playerstatistics.PlayerStatisticsService;
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
@RequestMapping("challenge_level")
public class ChallengeLevelController {

    private final ChallengeLevelService challengeLevelService;

    private final PlayerStatisticsService playerStatisticsService;

    private final ChallengeService challengeService;

    private final ChallengeLevelMapper challengeLevelMapper = new ChallengeLevelMapper();

    @Autowired
    public ChallengeLevelController(
            ChallengeLevelService challengeLevelService,
            PlayerStatisticsService playerStatisticsService,
            ChallengeService challengeService
    ) {
        this.challengeLevelService = challengeLevelService;
        this.playerStatisticsService = playerStatisticsService;
        this.challengeService = challengeService;
    }

    @GetMapping("get_challenge_levels")
    public ResponseEntity<List<ChallengeLevelDto>> getChallengeLevels() {
        var playerStatistics = playerStatisticsService.getStatistics(getUserId());
        var levelChallenges = challengeService.getLevelChallengesList();
        var challengeLevels = challengeLevelService.getChallengeLevelList();
        var challengeLevelDtoList
                = challengeLevelMapper.mapPlayerStatisticsAndChallengeLevelListToChallengeLevelDto(
                playerStatistics,
                challengeLevels,
                levelChallenges
        );
        return createResponseEntity(HttpStatus.OK, challengeLevelDtoList);
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
