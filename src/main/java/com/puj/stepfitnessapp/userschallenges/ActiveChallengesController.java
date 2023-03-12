package com.puj.stepfitnessapp.userschallenges;

import com.puj.stepfitnessapp.challenge.ChallengeService;
import com.puj.stepfitnessapp.items.Item;
import com.puj.stepfitnessapp.items.ItemService;
import com.puj.stepfitnessapp.player.Player;
import com.puj.stepfitnessapp.player.PlayerService;
import com.puj.stepfitnessapp.playerstatistics.PlayerStatisticsService;
import com.puj.stepfitnessapp.user.User;
import com.puj.stepfitnessapp.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController()
@RequestMapping("active_challenges")
public class ActiveChallengesController {

    private ActiveChallengesService activeChallengesService;

    private ChallengeService challengeService;

    private UserService userService;

    private PlayerStatisticsService playerStatisticsService;

    private PlayerService playerService;

    private ItemService itemService;

    @Autowired
    public ActiveChallengesController(
            ActiveChallengesService activeChallengesService,
            ChallengeService challengeService,
            UserService userService,
            PlayerStatisticsService playerStatisticsService,
            PlayerService playerService,
            ItemService itemService
    ){
        this.activeChallengesService = activeChallengesService;
        this.challengeService = challengeService;
        this.userService = userService;
        this.playerStatisticsService = playerStatisticsService;
        this.playerService = playerService;
        this.itemService = itemService;
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

        final var player = playerService.getPlayerById(userId);

        final var challenge = challengeService.getChallengeById(challenge_id).get();

        if(activeChallengesService.getUserChallengesByUser(userId) != null){
            return createResponseEntity(HttpStatus.CONFLICT, "Another challenge is already active");
        }

        LocalDateTime challengeEndTime = LocalDateTime.now().plusMinutes(
                playerService.calculateMinutesToFinishChallenge(
                        player,
                        challenge.getBaseHoursToFinish()*60
                )
        );

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
        final var activeChallenge = activeChallengesService.getUserChallengesByUser(getUserId());
        if(!activeChallenge.isCompleted()){
            activeChallengesService.deleteUserChallenge(getUserId());
            createResponseEntity(HttpStatus.CONFLICT, "Completed challenge can not be canceled");
        }

        return createResponseEntity(HttpStatus.OK, "User was deleted");
    }

    @PutMapping("update_user_progress")
    public ResponseEntity<String> updateUserProgress(@RequestBody int amountOfSteps) {
        final var player = getPlayerById(getUserId());

        var amountOfPoints = playerService.calculatePointsGained(
                player,
                amountOfSteps
        );

        activeChallengesService.updateUserChallengesProgress(amountOfPoints, amountOfSteps, getUserId());
        
        final var activeChallenge = activeChallengesService.getUserChallengesByUser(getUserId());

        if(activeChallenge.getAmountOfPoints() <= activeChallenge.getProgress()){
            addCompletedChallenge(player, activeChallenge);
            activeChallengesService.setChallengeCompleted(getUserId());

            return createResponseEntity(HttpStatus.OK, "Challenge is finished");
        }

        return createResponseEntity(HttpStatus.OK, "New data has been accepted");
    }

    public ResponseEntity<List<Item>> claimCompletedChallengeReward() {
        final var activeChallenge = activeChallengesService.getUserChallengesByUser(getUserId());
        final var player = playerService.getPlayerById(getUserId());
        if(activeChallenge.isCompleted()){
            var items = itemService.generateRewardItemsForChallenge(
                    activeChallenge.getChallengeLevel(),
                    player.getLevel()
            );
            playerService.addInventoryItems(player, items);
            return createResponseEntity(HttpStatus.OK, items);
        }
        else {
            return createResponseEntity(HttpStatus.CONFLICT, null);
        }
    }

    private void addCompletedChallenge(Player player, UserChallengesDto userChallenges) {
        playerStatisticsService.addCompletedChallenge(
                player,
                userChallenges.getChallengeLevel(),
                userChallenges.getChallengeId()
        );
    }

    private Player getPlayerById(Long userId){
        return playerService.getPlayerById(userId);
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
