package com.puj.stepfitnessapp;

import com.puj.stepfitnessapp.player.inventory.PlayerInventory;
import com.puj.stepfitnessapp.player.inventory.PlayerInventoryConverter;
import com.puj.stepfitnessapp.playerstatistics.PlayerStatisticsService;
import com.puj.stepfitnessapp.playerstatistics.completedchallenges.CompletedChallenges;
import com.puj.stepfitnessapp.playerstatistics.completedchallenges.CompletedChallengesConverter;
import org.junit.Test;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.Arrays;

public class JacksonTest {

    @Test
    public void test() {
        CompletedChallengesConverter mapper = new CompletedChallengesConverter();
        CompletedChallenges challenges1 = new CompletedChallenges(1);
        challenges1.addCompletedChallenge(1L);
        challenges1.addCompletedChallenge(2L);
        challenges1.addCompletedChallenge(3L);
        challenges1.addCompletedChallenge(4L);
        CompletedChallenges challenges2 = new CompletedChallenges(2);
        CompletedChallenges challenges3 = new CompletedChallenges(3);
        ArrayList<CompletedChallenges> list = new ArrayList<>(Arrays.asList(challenges1, challenges2, challenges3));
        var json = mapper.fromCompletedChallengesListToJson(list);
        System.out.println(json);
        var newList = mapper.fromJsonToCompletedChallengesList(json);
        System.out.println(newList.get(0).getLevel());
    }

    @Test
    public void test2() {
        PlayerInventoryConverter mapper = new PlayerInventoryConverter();
        PlayerInventory inventory = new PlayerInventory();
    }

    @Test
    public void test3() {
        System.out.println(OffsetDateTime.now());
    }
}
