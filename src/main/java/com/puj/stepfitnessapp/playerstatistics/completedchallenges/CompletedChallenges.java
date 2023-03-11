package com.puj.stepfitnessapp.playerstatistics.completedchallenges;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class CompletedChallenges {

    private int level;

    private ArrayList<Integer> challenges;

    public CompletedChallenges(int level){
        this.level = level;
        challenges = new ArrayList<>();
    }

    public void addCompletedChallenge(int challengeId){
        challenges.add(challengeId);
    }

    public List<Integer> getChallengesList(){
        return challenges;
    }

    @JsonIgnore
    public int getAmountOfCompletedChallenges(){
        return challenges.size();
    }
}
