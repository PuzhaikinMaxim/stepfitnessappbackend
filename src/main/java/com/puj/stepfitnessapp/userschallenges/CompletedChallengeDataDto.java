package com.puj.stepfitnessapp.userschallenges;

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
public class CompletedChallengeDataDto {

    private int amountOfXp;

    private List<ItemDto> items;
}
