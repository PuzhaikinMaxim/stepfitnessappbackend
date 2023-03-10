package com.puj.stepfitnessapp;

import com.puj.stepfitnessapp.playerstatistics.CompletedChallenges;
import com.puj.stepfitnessapp.playerstatistics.CompletedChallengesMapper;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;

public class JacksonTest {

    @Test
    public void test() {
        CompletedChallengesMapper mapper = new CompletedChallengesMapper();
        CompletedChallenges challenges1 = new CompletedChallenges(1);
        challenges1.addCompletedChallenge(1);
        challenges1.addCompletedChallenge(2);
        challenges1.addCompletedChallenge(3);
        challenges1.addCompletedChallenge(4);
        CompletedChallenges challenges2 = new CompletedChallenges(2);
        CompletedChallenges challenges3 = new CompletedChallenges(3);
        ArrayList<CompletedChallenges> list = new ArrayList<>(Arrays.asList(challenges1, challenges2, challenges3));
        var json = mapper.fromCompletedChallengesListToJson(list);
        System.out.println(json);
        var newList = mapper.fromJsonToCompletedChallengesList(json);
        System.out.println(newList.get(0).getLevel());
    }
}
