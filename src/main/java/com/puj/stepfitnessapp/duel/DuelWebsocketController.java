package com.puj.stepfitnessapp.duel;

import com.puj.stepfitnessapp.user.UserService;
import org.springframework.messaging.Message;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.MessageHeaderAccessor;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DuelWebsocketController {

    private final SimpMessagingTemplate simpleMessagingTemplate;

    private final UserService userService;

    private final DuelService duelService;

    public DuelWebsocketController(
            SimpMessagingTemplate simpleMessagingTemplate,
            UserService userService,
            DuelService duelService
    ) {
        this.simpleMessagingTemplate = simpleMessagingTemplate;
        this.userService = userService;
        this.duelService = duelService;
    }

    @MessageMapping("/try_find_opponent")
    public void tryFindOpponent(Message<?> message) {
        var userId = getUserId(message);
        var result = duelService.tryFindOpponent(userId, getUsername(message));
        if(result.getFirstUserName() != null && result.getFirstUserName() != null){
            simpleMessagingTemplate.convertAndSend(
                    "/topic/duel/" + result.getFirstUserName(),
                    result.getOpponentIsFound()
            );
            simpleMessagingTemplate.convertAndSend(
                    "/topic/duel/" + result.getSecondUserName(),
                    result.getOpponentIsFound()
            );
        }
        simpleMessagingTemplate.convertAndSend(
                "/topic/duel/" + getUsername(message),
                result.getOpponentIsFound()
        );
    }

    private Long getUserId(Message message) {
        StompHeaderAccessor accessor = MessageHeaderAccessor.getAccessor(
                message,
                StompHeaderAccessor.class
        );
        var username = accessor.getUser().getName();
        return userService.getUser(username).get().getUserId();
    }

    private String getUsername(Message message) {
        StompHeaderAccessor accessor = MessageHeaderAccessor.getAccessor(
                message,
                StompHeaderAccessor.class
        );
        return accessor.getUser().getName();
    }
}
