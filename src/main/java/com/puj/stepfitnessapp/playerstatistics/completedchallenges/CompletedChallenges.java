package com.puj.stepfitnessapp.playerstatistics.completedchallenges;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;

@Getter
@Setter
@NoArgsConstructor
public class CompletedChallenges {

    private int level;

    private HashSet<Long> challenges;

    public CompletedChallenges(int level){
        this.level = level;
        challenges = new HashSet<>();
    }

    public void addCompletedChallenge(Long challengeId){
        challenges.add(challengeId);
    }

    @JsonIgnore
    public int getAmountOfCompletedChallenges(){
        return challenges.size();
    }
}
