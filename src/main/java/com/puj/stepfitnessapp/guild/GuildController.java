package com.puj.stepfitnessapp.guild;

import com.puj.stepfitnessapp.guildenterrequest.GuildEnterRequestService;
import com.puj.stepfitnessapp.player.PlayerService;
import com.puj.stepfitnessapp.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("guild")
public class GuildController {

    private final GuildService guildService;

    private final GuildMapper guildMapper = new GuildMapper();

    private final PlayerService playerService;

    private final GuildEnterRequestService guildEnterRequestService;

    @Autowired
    public GuildController(
            GuildService guildService,
            PlayerService playerService,
            GuildEnterRequestService guildEnterRequestService
    ) {
        this.guildService = guildService;
        this.playerService = playerService;
        this.guildEnterRequestService = guildEnterRequestService;
    }

    @PostMapping("create_guild")
    public ResponseEntity<GuildDto> createGuild(@RequestBody GuildEditionInfo guildEditionInfo) {
        guildService.createGuild(getUserId(), guildEditionInfo.getGuildName(), guildEditionInfo.getGuildLogoId());
        var guild = guildService.findGuildByUserId(getUserId());
        return createResponseEntity(HttpStatus.OK, guildMapper.mapToGuildDto(guild));
    }

    @PutMapping("expel_player/{expelled_player_id}")
    public void expelPlayer(@PathVariable Long expelled_player_id) {
        guildService.expelPlayer(getUserId(), expelled_player_id);
    }

    @PutMapping("leave_guild")
    public void leaveGuild() {
        guildService.leaveGuild(getUserId());
    }

    @GetMapping("get_guild_list")
    public ResponseEntity<List<GuildListItemDto>> getGuildList() {
        var response = guildService.getGuildList();
        var player = playerService.getPlayerById(getUserId());
        var guildListItems = guildMapper.mapToGuildListItemDto(
                response,
                player,
                guildEnterRequestService.getPlayerEnterRequests(player)
        );
        return createResponseEntity(HttpStatus.OK, guildListItems);
    }

    @GetMapping("get_guild_data")
    public ResponseEntity<GuildDataDto> getGuildData() {
        var response = guildService.getGuild(getUserId());
        var guildData = guildMapper.mapToGuildDataDto(response);
        return createResponseEntity(HttpStatus.OK, guildData);
    }

    @GetMapping("get_guild_statistics")
    public ResponseEntity<GuildStatisticsDto> getGuildStatistics() {
        var response = guildService.getGuild(getUserId());
        var guildStatistics = guildMapper.mapToGuildStatisticsDto(response);
        return createResponseEntity(HttpStatus.OK, guildStatistics);
    }

    @GetMapping("get_guild_participants")
    public ResponseEntity<List<GuildParticipantDto>> getGuildParticipants() {
        var response = guildService.getGuild(getUserId());
        var guildChallenges = guildMapper.mapToGuildParticipantDto(response,getUserId());
        return createResponseEntity(HttpStatus.OK, guildChallenges);
    }

    @GetMapping("get_is_owner")
    public ResponseEntity<Boolean> getIsOwner() {
        return createResponseEntity(HttpStatus.OK, guildService.getIsOwner(getUserId()));
    }

    @PutMapping("edit_guild_data")
    public void editGuildData(@RequestBody GuildEditionInfo guildEditionInfo) {
        guildService.editGuildData(guildEditionInfo, getUserId());
    }

    @GetMapping("get_guild_edition_info")
    public ResponseEntity<GuildEditionInfo> getGuildEditionInfo() {
        var response = guildService.getGuild(getUserId());
        var guildData = guildMapper.mapToGuildEditionInfo(response);
        return createResponseEntity(HttpStatus.OK, guildData);
    }

    /*
    @PostMapping("send_guild_request/{guild_id}")
    public void sendGuildEnterRequest(@PathVariable Long guild_id) {
        var user = getUserId();
        guildEnterRequestService.sendGuildEnterRequest(getUserId(), guild_id);
    }

    @PutMapping("accept_guild_request/{request_id}")
    public void acceptGuildRequest(@PathVariable Long request_id) {
        guildEnterRequestService.acceptGuildRequest(request_id, getUserId());
    }

    @DeleteMapping("cancel_guild_request/{guild_id}")
    public void cancelGuildRequest(@PathVariable Long guild_id) {
        var guildEnterRequest = guildEnterRequestService.getGuildEnterRequestByGuildAndPlayer(
                getUserId(),
                guild_id
        );
        guildEnterRequestService.cancelGuildEnterRequest(guildEnterRequest);
    }

    @DeleteMapping("refuse_guild_request/{request_id}")
    public void refuseGuildRequest(@PathVariable Long request_id) {
        var guildEnterRequest = guildEnterRequestService.getGuildEnterRequestById(
                request_id
        );
        guildEnterRequestService.cancelGuildEnterRequest(guildEnterRequest);
    }

     */

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
