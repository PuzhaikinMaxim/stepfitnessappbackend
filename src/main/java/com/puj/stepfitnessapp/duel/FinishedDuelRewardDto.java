package com.puj.stepfitnessapp.duel;

import com.puj.stepfitnessapp.items.Item;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class FinishedDuelRewardDto {

    private int xp;

    private List<Item> reward;

}
