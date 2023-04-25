package com.puj.stepfitnessapp.guildenterrequest;

import com.puj.stepfitnessapp.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("guild_enter_request")
public class GuildEnterRequestController {

    private final GuildEnterRequestService guildEnterRequestService;

    private final GuildEnterRequestMapper guildEnterRequestMapper = new GuildEnterRequestMapper();

    @Autowired
    public GuildEnterRequestController(
            GuildEnterRequestService guildEnterRequestService
    ) {
        this.guildEnterRequestService = guildEnterRequestService;
    }

    @PostMapping("send_guild_request/{guild_id}")
    public void sendGuildEnterRequest(@PathVariable Long guild_id) {
        var user = getUserId();
        guildEnterRequestService.sendGuildEnterRequest(getUserId(), guild_id);
    }

    @DeleteMapping("accept_guild_request/{request_id}")
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

    @GetMapping("get_guild_enter_requests")
    public ResponseEntity<List<GuildEnterRequestDto>> getGuildEnterRequests() {
        var response = guildEnterRequestService.getGuildEnterRequests(getUserId());
        var requestsList = guildEnterRequestMapper.mapToGuildEnterRequestDtoList(response);
        return createResponseEntity(HttpStatus.OK, requestsList);
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
