package com.puj.stepfitnessapp.player;

import com.puj.stepfitnessapp.player.inventory.PlayerInventory;
import com.puj.stepfitnessapp.player.inventory.PlayerInventoryConverter;
import com.puj.stepfitnessapp.user.User;
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
@Table(name = "players")
public class Player {

    @Id
    private Long user_id;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id", referencedColumnName = "user_id")
    private User user;

    @NotNull
    private int level;

    @NotNull
    private int xp;

    @NotNull
    private int xpToNextLevel;

    @NotNull
    private int endurance;

    @NotNull
    private int strength;

    @NotNull
    private int unassignedPoints;

    @Convert(converter = PlayerInventoryConverter.class)
    @Column(columnDefinition = "TEXT", nullable = false)
    private PlayerInventory inventory;

    public Player(
            Long user_id,
            User user,
            @NotNull int level,
            @NotNull int xp,
            @NotNull int xpToNextLevel,
            @NotNull int endurance,
            @NotNull int strength,
            @NotNull int unassignedPoints,
            @NotNull PlayerInventory inventory
    ) {
        this.user_id = user_id;
        this.user = user;
        this.level = level;
        this.xp = xp;
        this.xpToNextLevel = xpToNextLevel;
        this.endurance = endurance;
        this.strength = strength;
        this.unassignedPoints = unassignedPoints;
        this.inventory = inventory;
    }

    public void incrementStrength() {
        if(unassignedPoints > 0) {
            unassignedPoints = unassignedPoints - 1;
            strength = strength + 1;
        }
    }

    public void incrementEndurance() {
        if(unassignedPoints > 0) {
            unassignedPoints = unassignedPoints - 1;
            endurance = endurance + 1;
        }
    }

    public int calculateHp(int baseHp) {
        return (int) (inventory.calculateAmountOfAdditionalHp(baseHp, endurance));
    }

    public Double calculatePointMultiplier() {
        return inventory.calculatePointsMultiplier() + (strength + 100.0)/100.0;
    }

    public int calculateAmountOfAdditionalPoints() {
        return inventory.calculateAmountOfAdditionalPoints();
    }
}
