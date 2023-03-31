package com.puj.stepfitnessapp.achievement;

import javax.persistence.AttributeConverter;

public class AchievementCategoryConverter implements AttributeConverter<AchievementCategory, String> {

    @Override
    public String convertToDatabaseColumn(AchievementCategory attribute) {
        return null;
    }

    @Override
    public AchievementCategory convertToEntityAttribute(String dbData) {
        return null;
    }
}
