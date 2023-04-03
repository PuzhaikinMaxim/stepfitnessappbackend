package com.puj.stepfitnessapp.achievement;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.puj.stepfitnessapp.achievement.categories.AchievementCategory;

import javax.persistence.AttributeConverter;

public class AchievementCategoryConverter implements AttributeConverter<AchievementCategory<?>, String> {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public String convertToDatabaseColumn(AchievementCategory attribute) {
        try {
            return objectMapper.writeValueAsString(attribute);
        }
        catch (JsonProcessingException ex) {
            return "";
        }
    }

    @Override
    public AchievementCategory<?> convertToEntityAttribute(String dbData) {
        try {
            return objectMapper.readValue(dbData, AchievementCategory.class);
        }
        catch (JsonProcessingException ex) {
            return null;
        }
    }
}
