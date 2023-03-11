package com.puj.stepfitnessapp.challengelevel;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ChallengeLevelService {

    private final ChallengeLevelRepository repository;

    @Autowired
    public ChallengeLevelService(ChallengeLevelRepository repository) {
        this.repository = repository;
    }

    public ChallengeLevel getChallengeLevel(int level) {
        var result = repository.findById(level);
        if(result.isEmpty()) return null;
        return result.get();
    }

    public List<ChallengeLevel> getChallengeLevelList() {
        return repository.findAll();
    }
}
