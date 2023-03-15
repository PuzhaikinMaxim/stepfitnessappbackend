package com.puj.stepfitnessapp.userdailychallenge;

import com.puj.stepfitnessapp.player.PlayerService;
import com.puj.stepfitnessapp.userdailychallenge.dailychallenge.DailyChallenge;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.*;
import java.util.ArrayList;
import java.util.List;

@Service
public class UserDailyChallengeService {

    private final UserDailyChallengeRepository repository;
    private final PlayerService playerService;

    private static final int[] amountOfStepsToComplete = new int[]{
            200, 500, 1000, 2000, 5000, 10000, 15000
    };

    @Autowired
    public UserDailyChallengeService(UserDailyChallengeRepository repository, PlayerService playerService) {
        this.repository = repository;
        this.playerService = playerService;
    }

    public UserDailyChallenge getUserDailyChallenges(long userId) {
        return repository.getUserDailyChallengeByUser_id(userId).orElse(null);
    }

    public void generateDailyChallengeData(String offsetDateTime, Long userId) {
        System.out.println(offsetDateTime);
        OffsetDateTime dateTime = OffsetDateTime.parse(offsetDateTime);
        dateTime = dateTime.withHour(23).withMinute(59).withSecond(59);
        final var player = playerService.getPlayerById(userId);
        final var userDailyChallenge = new UserDailyChallenge(
                player.getUser_id(),
                player,
                generateDailyChallengeList(),
                dateTime,
                0
        );
        repository.save(userDailyChallenge);
    }

    public void createNewDailyChallenges(UserDailyChallenge userDailyChallenge) {
        final var offset = userDailyChallenge.getDailyChallengeEndDateTime().getOffset();
        final var dateTimeWithOffset = LocalDateTime.now().atOffset(offset);
        if(dateTimeWithOffset.compareTo(userDailyChallenge.getDailyChallengeEndDateTime()) > 0){
            userDailyChallenge.setDailyChallenges(generateDailyChallengeList());
            userDailyChallenge.setDailyChallengeEndDateTime(
                    dateTimeWithOffset.withHour(23).withMinute(59).withSecond(59)
            );
            userDailyChallenge.setAmountOfSteps(0);
            repository.save(userDailyChallenge);
        }
    }

    private List<DailyChallenge> generateDailyChallengeList() {
        ArrayList<DailyChallenge> dailyChallenges = new ArrayList<>();
        for (int elem : amountOfStepsToComplete){
            dailyChallenges.add(
                    new DailyChallenge(elem)
            );
        }
        return dailyChallenges;
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
