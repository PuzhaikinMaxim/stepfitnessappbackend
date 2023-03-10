package com.puj.stepfitnessapp.userschallenges;

import com.puj.stepfitnessapp.challenge.ChallengeService;
import com.puj.stepfitnessapp.user.User;
import com.puj.stepfitnessapp.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController()
@RequestMapping("active_challenges")
public class ActiveChallengesController {

    private ActiveChallengesService activeChallengesService;

    private ChallengeService challengeService;

    private UserService userService;

    @Autowired
    public ActiveChallengesController(
            ActiveChallengesService activeChallengesService,
            ChallengeService challengeService,
            UserService userService
    ){
        this.activeChallengesService = activeChallengesService;
        this.challengeService = challengeService;
        this.userService = userService;
    }

    @GetMapping("active_challenge")
    public ResponseEntity<UserChallengesDto> getUserChallengesByUser() {
        final var userId = getUserId();

        final var result = activeChallengesService.getUserChallengesByUser(userId);

        final var httpStatus = result != null ? HttpStatus.OK : HttpStatus.NOT_FOUND;
        return createResponseEntity(httpStatus, result);
    }

    @PostMapping("start_challenge")
    public ResponseEntity<String> startChallenge(@RequestBody int challenge_id) {
        final var userId = getUserId();

        final var user = userService.getUserById(userId).get();

        final var challenge = challengeService.getChallengeById(challenge_id).get();

        if(activeChallengesService.getUserChallengesByUser(userId) != null){
            return createResponseEntity(HttpStatus.CONFLICT, "Another challenge is already active");
        }

        LocalDateTime challengeEndTime = LocalDateTime.now().plusHours(challenge.getBaseHoursToFinish());

        activeChallengesService.addUserChallenge(
                new UserChallenges(
                        new UserChallengesKey(
                                userId,
                                (long) challenge_id
                        ),
                        user,
                        challenge,
                        0,
                        challengeEndTime,
                        0
                )
        );

        return createResponseEntity(HttpStatus.OK, "Challenge started");
    }

    @DeleteMapping("cancel_active_challenge")
    public ResponseEntity<String> cancelChallenge() {
        activeChallengesService.deleteUserChallenge(getUserId());

        return createResponseEntity(HttpStatus.OK, "User was deleted");
    }

    @PutMapping("update_user_progress")
    public ResponseEntity<String> updateUserProgress(@RequestBody int amountOfSteps) {
        var amountOfPoints = amountOfSteps;

        activeChallengesService.updateUserChallengesProgress(amountOfPoints, getUserId());
        
        final var activeChallenge = activeChallengesService.getUserChallengesByUser(getUserId());

        if(activeChallenge.getAmountOfPoints() <= activeChallenge.getProgress()){
            activeChallengesService.deleteUserChallenge(getUserId());

            return createResponseEntity(HttpStatus.OK, "Challenge is finished");
        }

        return createResponseEntity(HttpStatus.OK, "New data has been accepted");
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
