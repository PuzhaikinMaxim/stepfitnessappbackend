package com.puj.stepfitnessapp.guildchallenges;

import com.puj.stepfitnessapp.player.PlayerService;
import com.puj.stepfitnessapp.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("guild_challenge")
public class GuildChallengesController {

    private final GuildChallengesService guildChallengesService;

    private final PlayerService playerService;

    private final GuildChallengesMapper guildChallengesMapper = new GuildChallengesMapper();

    @Autowired
    public GuildChallengesController(
            GuildChallengesService guildChallengesService,
            PlayerService playerService
    ) {
        this.guildChallengesService = guildChallengesService;
        this.playerService = playerService;
    }

    @PutMapping("update_progress")
    public Boolean updateProgress(@RequestBody ProgressData progressData) {
        var player = playerService.getPlayerById(getUserId());
        guildChallengesService.updateProgress(progressData.getAmountOfSteps(), player.getGuild());
        return true;
    }

    @PutMapping("start_guild_challenge/{challenge_id}")
    public void startGuildChallenge(@PathVariable Long challenge_id) {
        var guildChallenge = guildChallengesService.getGuildChallengeById(challenge_id);
        guildChallengesService.startGuildChallenge(guildChallenge, getUserId());
    }

    @PostMapping("generate_guild_challenges")
    public ResponseEntity<List<GuildChallengeDto>> generateGuildChallenges() {
        var guild = playerService.getPlayerById(getUserId()).getGuild();
        return createResponseEntity(
                HttpStatus.OK,
                guildChallengesService.generateGuildChallenges(guild, getUserId())
        );
    }

    @GetMapping("get_current_guild_challenge")
    public ResponseEntity<CurrentGuildChallengeDto> getCurrentGuildChallenge() {
        var response = guildChallengesService.getCurrentGuildChallenge(getUserId());
        var guildChallenges = guildChallengesMapper.mapToCurrentGuildChallengeDto(response);
        return createResponseEntity(HttpStatus.OK, guildChallenges);
    }

    @GetMapping("get_guild_challenges")
    public ResponseEntity<List<GuildChallengeDto>> getGuildChallenges() {
        var response = guildChallengesService.getGuildChallenges(getUserId());
        var guildChallenges = guildChallengesMapper.mapToGuildChallengeDtoList(response);
        return createResponseEntity(HttpStatus.OK, guildChallenges);
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
