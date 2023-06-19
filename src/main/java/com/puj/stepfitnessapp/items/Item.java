package com.puj.stepfitnessapp.items;

import com.puj.stepfitnessapp.rarity.Rarity;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Setter
@Getter
@EqualsAndHashCode
@NoArgsConstructor
@Entity
@Table(name = "items")
public class Item {

    @SequenceGenerator(
            name = "user_sequence",
            sequenceName = "user_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "user_sequence"
    )
    @Id
    private int itemId;

    @NotNull
    private String itemName;

    @NotNull
    private int plusTimeMinutes = 0;

    @NotNull
    private double timeMultiplier = 1.0;

    @NotNull
    private int pointsFixed = 0;

    @NotNull
    private double pointsMultiplier = 1.0;

    /*
    @NotNull
    private int rarityLevel;

     */

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "rarity_level")
    private Rarity rarity;

    private Integer imageId;

    public Item(String itemName){
        this.itemName = itemName;
        //this.rarity = rarity;
    }
}
