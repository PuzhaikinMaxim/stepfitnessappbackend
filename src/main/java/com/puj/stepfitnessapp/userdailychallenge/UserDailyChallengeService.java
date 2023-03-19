package com.puj.stepfitnessapp.userdailychallenge;

import com.puj.stepfitnessapp.items.Item;
import com.puj.stepfitnessapp.items.ItemService;
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
    private final ItemService itemService;

    private static final int[] amountOfStepsToComplete = new int[]{
            200, 500, 1000, 2000, 5000, 10000, 15000
    };

    private static final int[] amountOfXpForSteps = new int[]{
            10, 10, 20, 30, 50, 60, 80
    };

    @Autowired
    public UserDailyChallengeService(
            UserDailyChallengeRepository repository,
            PlayerService playerService,
            ItemService itemService
    ) {
        this.repository = repository;
        this.playerService = playerService;
        this.itemService = itemService;
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
        for (int i = 0; i < amountOfStepsToComplete.length; i++){
            dailyChallenges.add(
                    new DailyChallenge(amountOfStepsToComplete[i], amountOfXpForSteps[i])
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

    public CompletedUserDailyChallengesDataDto claimReward(Long userId) {
        final var rewards = new ArrayList<Item>();
        final var player = playerService.getPlayerById(userId);
        var amountOfXp = 0;
        final var userDailyChallenges =
                repository.getUserDailyChallengeByUser_id(userId).orElse(null);

        if(userDailyChallenges == null){
            return null;
        }

        for(var userDailyChallenge : userDailyChallenges.getDailyChallenges()){
            if(userDailyChallenge.isCompleted() && !userDailyChallenge.isRewardClaimed()){
                amountOfXp += userDailyChallenge.getAmountOfXp();
                var item = itemService.generateRewardItemForDailyChallenge(
                        userDailyChallenge.getAmountOfStepsToComplete(),
                        player.getLevel()
                );
                if(item != null){
                    rewards.add(item);
                }
            }
        }

        if(amountOfXp != 0){
            playerService.addPlayerXp(player, amountOfXp);
        }
        if(rewards.size() != 0){
            playerService.addInventoryItems(player, rewards);
        }

        return new CompletedUserDailyChallengesDataDto(amountOfXp, rewards);
    }
}
