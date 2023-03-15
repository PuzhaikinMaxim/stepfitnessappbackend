package com.puj.stepfitnessapp.userdailychallenge;

import com.puj.stepfitnessapp.user.User;
import com.puj.stepfitnessapp.userdailychallenge.dailychallenge.DailyChallenge;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.time.ZoneOffset;
import java.util.List;

@RestController
@RequestMapping("daily_challenges")
public class UserDailyChallengeController {

    private final UserDailyChallengeService service;

    @Autowired
    public UserDailyChallengeController(UserDailyChallengeService service) {
        this.service = service;
    }

    @GetMapping("generate_daily_challenge_list")
    public ResponseEntity<String> generateDailyChallengeData(@RequestBody String offsetDateTime) {
        final var userDailyChallenges = service.getUserDailyChallenges(getUserId());
        if(userDailyChallenges == null){
            service.generateDailyChallengeData(offsetDateTime, getUserId());
            return createResponseEntity(HttpStatus.OK, "Data has been generated");
        }
        else {
            service.createNewDailyChallenges(userDailyChallenges);
            return createResponseEntity(HttpStatus.OK, "New daily challenges has been created");
        }
    }

    @PutMapping("update_user_progress")
    public ResponseEntity<String> updateUserProgress(@RequestBody int amountOfSteps){
        service.updateProgress(getUserId(), amountOfSteps);
        return createResponseEntity(HttpStatus.OK, "New data has been accepted");
    }

    @GetMapping("get_daily_challenges_list")
    public ResponseEntity<List<DailyChallenge>> getDailyChallengesList() {
        var dailyChallenges = service.getUserDailyChallenges(getUserId());
        return createResponseEntity(HttpStatus.OK, dailyChallenges.getDailyChallenges());
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
