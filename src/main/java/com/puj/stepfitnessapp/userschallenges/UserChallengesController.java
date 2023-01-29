package com.puj.stepfitnessapp.userschallenges;

import com.puj.stepfitnessapp.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController()
@RequestMapping("user_challenges")
public class UserChallengesController {

    private UserChallengesService userChallengesService;

    @Autowired
    public UserChallengesController(UserChallengesService userChallengesService){
        this.userChallengesService = userChallengesService;
    }

    @GetMapping("active_challenges")
    public ResponseEntity<UserChallengesDto> getUserChallengesByUser() {
        final var userDetails = (User) SecurityContextHolder.getContext().getAuthentication().getDetails();
        final var userId = userDetails.getUserId();

        final var result = userChallengesService.getUserChallengesByUser(userId);

        final var httpStatus = result != null ? HttpStatus.OK : HttpStatus.NOT_FOUND;
        return createResponseEntity(httpStatus, result);
    }

    private <T> ResponseEntity<T> createResponseEntity(HttpStatus status, T body) {
        return ResponseEntity
                .status(status)
                .body(body);
    }
}
