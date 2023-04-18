package com.puj.stepfitnessapp.guild;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GuildService {

    private final GuildRepository guildRepository;

    @Autowired
    public GuildService(GuildRepository guildRepository) {
        this.guildRepository = guildRepository;
    }
}
