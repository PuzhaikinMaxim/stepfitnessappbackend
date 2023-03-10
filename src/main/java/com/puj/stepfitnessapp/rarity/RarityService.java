package com.puj.stepfitnessapp.rarity;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RarityService {

    private final RarityRepository rarityRepository;

    @Autowired
    public RarityService(RarityRepository rarityRepository) {
        this.rarityRepository = rarityRepository;
    }

    public Rarity getRarityByRarityLevel(int rarityLevel) {
        var response = rarityRepository.getRarityByRarityLevel(rarityLevel);
        if(response.isEmpty()){
            throw new RuntimeException("Rarity level does not exist");
        }
        return response.get();
    }
}
