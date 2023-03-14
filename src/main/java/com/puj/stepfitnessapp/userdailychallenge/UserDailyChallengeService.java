package com.puj.stepfitnessapp.userdailychallenge;

import com.puj.stepfitnessapp.userdailychallenge.dailychallenge.DailyChallenge;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserDailyChallengeService {

    private final UserDailyChallengeRepository repository;

    @Autowired
    public UserDailyChallengeService(UserDailyChallengeRepository repository) {
        this.repository = repository;
    }

    public UserDailyChallenge getUserDailyChallenges(long userId) {
        return repository.getReferenceById(userId);
    }

    public void updateProgress(long userId, int amountOfSteps) {
        final var userDailyChallenges = repository.findById(userId).get();
        amountOfSteps = userDailyChallenges.getAmountOfSteps() + amountOfSteps;
        userDailyChallenges.setAmountOfSteps(amountOfSteps);
        final var dailyChallenges = userDailyChallenges.getDailyChallenges();
        for(DailyChallenge challenge : dailyChallenges){
            if(!challenge.isCompleted()){
                challenge.setIsCompleted(amountOfSteps);
            }
        }
        repository.save(userDailyChallenges);
    }
}
