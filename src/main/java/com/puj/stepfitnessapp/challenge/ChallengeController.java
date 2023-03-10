package com.puj.stepfitnessapp.challenge;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController()
@RequestMapping("challenges")
public class ChallengeController {

    private final ChallengeService challengeService;

    @Autowired
    public ChallengeController(ChallengeService challengeService) {
        this.challengeService = challengeService;
    }

    @GetMapping(value = "/{level}")
    public ResponseEntity<List<ChallengeDto>> getChallengeListByLevel(@PathVariable int level) {
        final var result = challengeService.getChallengeListByLevel(level);
        if(result == null){
            return createResponseEntity(HttpStatus.NOT_FOUND, null);
        }
        else {
            return createResponseEntity(HttpStatus.OK, result);
        }
    }

    private <T> ResponseEntity<T> createResponseEntity(HttpStatus status, T body) {
        return ResponseEntity
                .status(status)
                .body(body);
    }
}
