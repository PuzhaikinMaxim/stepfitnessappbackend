package com.puj.stepfitnessapp.achievement;

public interface AchievementCategory<T> {

    String getDescription();

    T getComparableValue();

    Boolean isCompleted(T value);
}
