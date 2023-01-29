package com.puj.stepfitnessapp.userschallenges;

import com.puj.stepfitnessapp.challenge.ChallengeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserChallengesService {

    private final UserChallengesRepository userChallengesRepository;

    private final UserChallengesMapper userChallengesMapper = new UserChallengesMapper();

    @Autowired
    public UserChallengesService(UserChallengesRepository userChallengesRepository) {
        this.userChallengesRepository = userChallengesRepository;
    }

    public UserChallengesDto getUserChallengesByUser(long userId) {
        final var result = userChallengesRepository.getUserChallengesByUser(userId);
        if(result.isEmpty()){
            return null;
        }
        else {
            return userChallengesMapper.mapUserChallengesToDto(result.get());
        }
    }
}
