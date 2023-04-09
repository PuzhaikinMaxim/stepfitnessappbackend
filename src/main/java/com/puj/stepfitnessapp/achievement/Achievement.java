package com.puj.stepfitnessapp.achievement;

import com.puj.stepfitnessapp.achievement.categories.AchievementCategory;
import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@Entity
@Table(name = "achievements")
public class Achievement {

    @Id
    @SequenceGenerator(
            name = "challenge_sequence",
            sequenceName = "challenge_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "challenge_sequence"
    )
    private Integer achievementId;

    @Column(nullable = false, length = 50)
    private String achievementName;

    @Convert(converter = AchievementCategoryConverter.class)
    @Column(columnDefinition = "TEXT", nullable = false)
    private AchievementCategory<?> achievementCategory;
}
