package com.puj.stepfitnessapp.duel;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Service;

@Getter
@Service
@AllArgsConstructor
@NoArgsConstructor
public class DuelMessageData {

    private Boolean opponentIsFound;

    private String firstUserName;

    private String secondUserName;
}
