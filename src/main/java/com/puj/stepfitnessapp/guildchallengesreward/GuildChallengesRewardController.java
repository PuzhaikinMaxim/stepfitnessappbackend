package com.puj.stepfitnessapp.guildchallengesreward;

import com.puj.stepfitnessapp.player.PlayerService;
import com.puj.stepfitnessapp.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("guild_challenges_reward")
public class GuildChallengesRewardController {

    private final GuildChallengesRewardService guildChallengesRewardService;

    private final PlayerService playerService;

    @Autowired
    public GuildChallengesRewardController(
            GuildChallengesRewardService guildChallengesRewardService,
            PlayerService playerService
    ) {
        this.guildChallengesRewardService = guildChallengesRewardService;
        this.playerService = playerService;
    }

    @DeleteMapping("claim_reward")
    public ResponseEntity<FinishedGuildChallengeReward> claimReward() {
        var player = playerService.getPlayerById(getUserId());
        return createResponseEntity(HttpStatus.OK, guildChallengesRewardService.claimReward(player));
    }

    @GetMapping("get_has_reward")
    public ResponseEntity<Boolean> getHasReward() {
        return createResponseEntity(HttpStatus.OK, guildChallengesRewardService.getHasReward(getUserId()));
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
