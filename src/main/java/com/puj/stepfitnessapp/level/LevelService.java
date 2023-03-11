package com.puj.stepfitnessapp.level;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LevelService {

    private final LevelRepository levelRepository;

    @Autowired
    public LevelService(LevelRepository levelRepository){
        this.levelRepository = levelRepository;
    }

    public Level getLevel(int level){
        var response = levelRepository.getLevel(level);
        if(response.isEmpty()){
            throw new RuntimeException("Level does not exist");
        }
        return response.get();
    }
}
