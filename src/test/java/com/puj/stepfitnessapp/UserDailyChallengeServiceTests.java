package com.puj.stepfitnessapp;

import com.puj.stepfitnessapp.items.ItemService;
import com.puj.stepfitnessapp.player.Player;
import com.puj.stepfitnessapp.player.PlayerService;
import com.puj.stepfitnessapp.userdailychallenge.UserDailyChallenge;
import com.puj.stepfitnessapp.userdailychallenge.UserDailyChallengeRepository;
import com.puj.stepfitnessapp.userdailychallenge.UserDailyChallengeService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.time.OffsetDateTime;
import java.util.Optional;

@SpringBootTest
public class UserDailyChallengeServiceTests {

    private UserDailyChallengeService userDailyChallengeService;

    @MockBean
    private UserDailyChallengeRepository repository;

    @MockBean
    private PlayerService playerService;

    @MockBean
    private ItemService itemService;

    @Captor
    private ArgumentCaptor<UserDailyChallenge> dailyChallengesCaptor;

    private Player player;

    @BeforeEach
    public void setup() {
        userDailyChallengeService = new UserDailyChallengeService(repository, playerService, itemService);
        player = getPlayer();
    }

    @Test
    void generateDailyChallengesListTest() {
        var offsetDateTime = OffsetDateTime.now().toString();
        Mockito.when(playerService.getPlayerById(player.getUser_id())).thenReturn(player);
        userDailyChallengeService.generateDailyChallengeData(offsetDateTime,1L);
        Mockito.verify(repository).save(dailyChallengesCaptor.capture());
        UserDailyChallenge userDailyChallenge = dailyChallengesCaptor.getValue();
        Assertions.assertEquals(7,userDailyChallenge.getDailyChallenges().size());
        Assertions.assertEquals(200,userDailyChallenge.getDailyChallenges().get(0).getAmountOfStepsToComplete());
    }

    @Test
    void completeDailyChallengeTest() {
        var offsetDateTime = OffsetDateTime.now().toString();
        Mockito.when(playerService.getPlayerById(player.getUser_id())).thenReturn(player);
        userDailyChallengeService.generateDailyChallengeData(offsetDateTime,1L);
        Mockito.verify(repository).save(dailyChallengesCaptor.capture());
        UserDailyChallenge userDailyChallenge = dailyChallengesCaptor.getValue();

        Mockito.when(repository.findById(1L)).thenReturn(Optional.of(userDailyChallenge));

        userDailyChallengeService.updateProgress(1L, 200);
        Assertions.assertTrue(userDailyChallenge.getDailyChallenges().get(0).isCompleted());

        userDailyChallengeService.updateProgress(1L, 300);

        Assertions.assertTrue(userDailyChallenge.getDailyChallenges().get(1).isCompleted());
        Assertions.assertFalse(userDailyChallenge.getDailyChallenges().get(2).isCompleted());
    }

    @Test
    void claimRewardTest() {
        var offsetDateTime = OffsetDateTime.now().toString();
        Mockito.when(playerService.getPlayerById(player.getUser_id())).thenReturn(player);
        userDailyChallengeService.generateDailyChallengeData(offsetDateTime,1L);
        Mockito.verify(repository).save(dailyChallengesCaptor.capture());
        UserDailyChallenge userDailyChallenge = dailyChallengesCaptor.getValue();

        Mockito.when(repository.findById(1L)).thenReturn(Optional.of(userDailyChallenge));
        Mockito.when(repository.getUserDailyChallengeByUser_id(1L)).thenReturn(Optional.of(userDailyChallenge));

        userDailyChallengeService.updateProgress(1L, 500);
        userDailyChallengeService.claimReward(1L);
        Assertions.assertTrue(userDailyChallenge.getDailyChallenges().get(0).isRewardClaimed());
        Assertions.assertTrue(userDailyChallenge.getDailyChallenges().get(1).isRewardClaimed());
        Assertions.assertFalse(userDailyChallenge.getDailyChallenges().get(2).isRewardClaimed());

    }

    private Player getPlayer() {
        var player = new Player();
        player.setUser_id(1L);
        return player;
    }
}
