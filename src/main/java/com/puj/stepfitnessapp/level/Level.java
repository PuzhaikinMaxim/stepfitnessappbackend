package com.puj.stepfitnessapp.level;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Setter
@Getter
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "levels")
public class Level {

    @Id
    private int level;

    private int xp;
}
