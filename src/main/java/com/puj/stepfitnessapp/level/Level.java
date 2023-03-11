package com.puj.stepfitnessapp.level;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Setter
@Getter
@EqualsAndHashCode
@NoArgsConstructor
@Entity
@Table(name = "levels")
public class Level {

    @Id
    private int level;

    private int xp;
}
