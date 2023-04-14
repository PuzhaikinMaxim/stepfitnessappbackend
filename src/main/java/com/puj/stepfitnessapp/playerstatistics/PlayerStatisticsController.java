package com.puj.stepfitnessapp.playerstatistics;

import com.puj.stepfitnessapp.achievement.AchievementDto;
import com.puj.stepfitnessapp.achievement.AchievementService;
import com.puj.stepfitnessapp.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("statistics")
public class PlayerStatisticsController {

    private final PlayerStatisticsService playerStatisticsService;

    private final AchievementMapper achievementMapper = new AchievementMapper();

    private final AchievementService achievementService;

    @Autowired
    public PlayerStatisticsController(
            PlayerStatisticsService playerStatisticsService,
            AchievementService achievementService
    ) {
        this.playerStatisticsService = playerStatisticsService;
        this.achievementService = achievementService;
    }

    @PutMapping("update_step_count")
    public void updateStepCount(@RequestBody int amountOfSteps) {
        var playerStatistics = playerStatisticsService.getStatistics(getUserId());

        playerStatisticsService.addAmountOfSteps(playerStatistics, amountOfSteps);
    }

    @GetMapping("get_achievements")
    public ResponseEntity<List<AchievementDto>> getAchievements() {
        var playerStatistics = playerStatisticsService.getStatistics(getUserId());
        var achievementList = achievementService.getAchievementList();
        var achievementDtoList = achievementMapper.mapAchievementListAndCompletedAchievementsToAchievementDtoList(
                achievementList,
                playerStatistics.getCompletedAchievements()
        );
        return createResponseEntity(HttpStatus.OK, achievementDtoList);
    }

    @GetMapping("get_duel_statistics")
    public ResponseEntity<DuelStatistics> getDuelStatistics() {
        var response = playerStatisticsService.getDuelStatistics(getUserId());
        return createResponseEntity(HttpStatus.OK, response);
    }

    private <T> ResponseEntity<T> createResponseEntity(HttpStatus status, T body) {
        return ResponseEntity
                .status(status)
                .body(body);
    }

    private long getUserId() {
        final var userDetails = (User) SecurityContextHolder.getContext().getAuthentication().getDetails();
        return userDetails.getUserId();
    }
}
