package com.puj.stepfitnessapp.user;

import com.puj.stepfitnessapp.player.PlayerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
public class UserController {

    UserService userService;

    PlayerService playerService;

    PasswordEncoder encoder;

    Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    UserController(UserService userService, PasswordEncoder encoder, PlayerService playerService) {
        this.userService = userService;
        this.encoder = encoder;
        this.playerService = playerService;
    }

    @PostMapping(value = "/registerUser")
    public ResponseEntity<String> registerUser(@RequestBody UserDto newUser) {
        logger.trace(newUser.getUsername());
        try {
            userService.addUser(newUser);

            final var user = userService.getUser(newUser.getUsername()).get();
            playerService.addPlayer(user);
            return createResponseEntity(HttpStatus.OK, "User is created");
        }
        catch (Exception e) {
            return createResponseEntity(HttpStatus.CONFLICT, "User already exists");
        }
    }

    @GetMapping(value = "/getUser")
    public User getData() {
        return new User("Test", "test@gmail.com", "testPassowrd");
    }

    @GetMapping(value = "/test")
    public String test() {
        return "Test";
    }

    @GetMapping(value = "/authorized")
    public ResponseEntity<String> authorized() {
        return createResponseEntity(HttpStatus.OK, "Authorized");
    }

    @GetMapping(value = "/getUserF")
    public User getUser() {
        var req = userService.getUser("function");
        if(req.isEmpty()) return null;
        return req.get();
    }

    @PostMapping(value = "/login")
    public ResponseEntity<String> login(@RequestBody UserCredentialsDto loginUser){
        var result = userService.getUser(loginUser.getUsername());
        if(result.isEmpty()){
            var body = String.format("User %s not found", loginUser.getUsername());
            return createResponseEntity(HttpStatus.NOT_FOUND, body);
        }

        var user = result.get();
        var rawPassword = loginUser.getPassword();
        if(!encoder.matches(rawPassword, user.getPassword())) {
            return createResponseEntity(HttpStatus.UNAUTHORIZED, "Password does not match");
        }

        var token = user.getUsername() + UUID.randomUUID().toString();
        userService.addEnterToken(token, user);
        return createResponseEntity(HttpStatus.OK, token);
    }

    private <T> ResponseEntity<T> createResponseEntity(HttpStatus status, T body) {
        return ResponseEntity
                .status(status)
                .body(body);
    }
}
