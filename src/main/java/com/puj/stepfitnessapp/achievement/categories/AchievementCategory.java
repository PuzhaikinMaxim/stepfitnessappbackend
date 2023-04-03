package com.puj.stepfitnessapp.achievement.categories;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        include = JsonTypeInfo.As.PROPERTY,
        property = "type"
)
@JsonSubTypes({
        @JsonSubTypes.Type(value = AchievementCategoryChallengeAmount.class, name = "challenge_amount"),
        @JsonSubTypes.Type(value = AchievementCategoryChallengeAmount.class, name = "duel_amount"),
        @JsonSubTypes.Type(value = AchievementCategoryChallengeAmount.class, name = "step_amount"),
})
public interface AchievementCategory<T> {

    String getDescription();

    T getComparableValue();

    Boolean isCompleted(T value);

    Integer getType();
}
