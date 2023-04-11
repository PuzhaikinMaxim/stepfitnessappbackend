package com.puj.stepfitnessapp.duel;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
//@RequestMapping("/duel")
public class DuelController {

    private final SimpMessagingTemplate simpleMessagingTemplate;

    public DuelController(SimpMessagingTemplate simpleMessagingTemplate) {
        this.simpleMessagingTemplate = simpleMessagingTemplate;
    }

    @MessageMapping("/sock_test")
    @SendTo("/topic/test")
    public String socketTest() {
        //simpleMessagingTemplate.convertAndSend("Test");
        return "Test";
    }
}
