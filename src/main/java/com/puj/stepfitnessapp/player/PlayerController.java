package com.puj.stepfitnessapp.player;

import com.puj.stepfitnessapp.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("player")
public class PlayerController {

    private PlayerService service;

    @Autowired
    public PlayerController(PlayerService service) {
        this.service = service;
    }

    @GetMapping("get_player_data")
    public ResponseEntity<PlayerDataDto> getPlayerData() {
        var userDataDto = service.getPlayerDataByUserId(getUserId());
        return createResponseEntity(HttpStatus.OK, userDataDto);
    }

    @GetMapping("get_characteristics")
    public ResponseEntity<CharacteristicsDto> getCharacteristics() {
        var characteristics = service.getCharacteristics(getUserId());
        if(characteristics != null){
            return createResponseEntity(HttpStatus.OK, characteristics);
        }
        return createResponseEntity(HttpStatus.NOT_FOUND, null);
    }

    @PutMapping("increase_endurance")
    public void increaseEndurance() {
        var player = service.getPlayerById(getUserId());
        service.incrementEndurance(player);
    }

    @PutMapping("increase_strength")
    public void increaseStrength() {
        var player = service.getPlayerById(getUserId());
        service.incrementStrength(player);
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
