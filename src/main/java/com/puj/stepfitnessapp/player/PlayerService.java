package com.puj.stepfitnessapp.player;

import com.puj.stepfitnessapp.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PlayerService {

    private final PlayerRepository repository;

    @Autowired
    public PlayerService(PlayerRepository repository){
        this.repository = repository;
    }

    public void addPlayer(User user) {
        Player p = new Player(
                user.getUserId(),
                user,
                0,
                0,
                200,
                1,
                1,
                0
        );
        repository.save(p);
    }
}
