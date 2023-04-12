package com.puj.stepfitnessapp.duel;

import com.puj.stepfitnessapp.user.UserService;
import org.springframework.messaging.Message;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.MessageHeaderAccessor;
import org.springframework.web.bind.annotation.RestController;

@RestController
//@RequestMapping("/duel")
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

    @MessageMapping("/sock_test")
    @SendTo("/topic/test")
    public Boolean socketTest(Message<?> message) {
        var userId = getUserId(message);
        return duelService.tryFindGame(userId);
    }

    private Long getUserId(Message message) {
        StompHeaderAccessor accessor = MessageHeaderAccessor.getAccessor(
                message,
                StompHeaderAccessor.class
        );
        var username = accessor.getUser().getName();
        return userService.getUser(username).get().getUserId();
    }
}
