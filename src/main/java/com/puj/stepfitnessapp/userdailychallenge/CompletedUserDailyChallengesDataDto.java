package com.puj.stepfitnessapp.userdailychallenge;

import com.puj.stepfitnessapp.items.Item;
import com.puj.stepfitnessapp.items.ItemDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CompletedUserDailyChallengesDataDto {

    private int xp;

    private List<ItemDto> reward;
}
