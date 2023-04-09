package com.puj.stepfitnessapp.playerstatistics;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.persistence.AttributeConverter;
import java.util.Collections;
import java.util.Set;

public class CompletedAchievementsConverter implements AttributeConverter<Set<Integer>, String> {

    private final ObjectMapper objectMapper = new ObjectMapper();

    public Set<Integer> fromJsonToList(String json){
        try {
            return objectMapper.readValue(json, new TypeReference<>() {});
        }
        catch (JsonProcessingException ex){
            return Collections.emptySet();
        }
    }

    public String fromListToJson(Set<Integer> list) {
        try {
            return objectMapper.writeValueAsString(list);
        }
        catch (JsonProcessingException ex){
            return "";
        }
    }

    @Override
    public String convertToDatabaseColumn(Set<Integer> attribute) {
        return fromListToJson(attribute);
    }

    @Override
    public Set<Integer> convertToEntityAttribute(String dbData) {
        return fromJsonToList(dbData);
    }
}
