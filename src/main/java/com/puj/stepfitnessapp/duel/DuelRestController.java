package com.puj.stepfitnessapp.duel;

import com.puj.stepfitnessapp.StepCount;
import com.puj.stepfitnessapp.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("duel")
public class DuelRestController {

    private final DuelService duelService;

    private final DuelMapper duelMapper = new DuelMapper();

    @Autowired
    public DuelRestController(DuelService duelService) {
        this.duelService = duelService;
    }

    @GetMapping("get_duel")
    public ResponseEntity<DuelDto> getDuel() {
        var response = duelService.getDuelByUserId(getUserId());
        if(response.isPresent()){
            var duelDto = duelMapper.mapDuelToDuelDto(response.get(), getUserId());
            return createResponseEntity(HttpStatus.OK, duelDto);
        }
        return createResponseEntity(HttpStatus.NOT_FOUND, null);
    }

    @PutMapping("cancel_duel")
    public ResponseEntity<Boolean> cancelDuel() {
        duelService.cancelDuel(getUserId());
        return createResponseEntity(HttpStatus.OK, true);
    }

    @PutMapping("update_progress")
    public ResponseEntity<Boolean> updateProgress(@RequestBody StepCount stepCount) {
        var response = duelService.getDuelByUserId(getUserId());

        if(response.isEmpty()){
            return createResponseEntity(HttpStatus.NOT_FOUND,false);
        }
        var duel = response.get();
        if(duel.getWinner() != null) return createResponseEntity(HttpStatus.OK, true);

        duelService.updateProgress(stepCount.getStepCount(), getUserId(), duel);
        return createResponseEntity(HttpStatus.OK, true);
    }

    @PutMapping("claim_reward")
    public ResponseEntity<FinishedDuelRewardDto> claimReward() {
        return createResponseEntity(HttpStatus.OK, duelService.claimReward(getUserId()));
    }

    @GetMapping("is_duel_not_finished")
    public ResponseEntity<Boolean> isDuelNotFinished() {
        return createResponseEntity(HttpStatus.OK, duelService.isDuelNotFinished(getUserId()));
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
