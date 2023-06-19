package com.puj.stepfitnessapp.items;

import lombok.*;

@Setter
@Getter
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class ItemDto {

    private int itemId;

    private String itemName;

    private int plusTimeMinutes = 0;

    private double timeMultiplier = 1.0;

    private int pointsFixed = 0;

    private double pointsMultiplier = 1.0;

    private Integer rarityLevel;

    private Integer imageId;
}
