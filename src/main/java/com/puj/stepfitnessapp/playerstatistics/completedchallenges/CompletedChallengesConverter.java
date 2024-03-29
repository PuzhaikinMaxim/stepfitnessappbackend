package com.puj.stepfitnessapp.playerstatistics.completedchallenges;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.puj.stepfitnessapp.SimpleListConverter;

import java.util.List;

public class CompletedChallengesConverter extends SimpleListConverter<CompletedChallenges> {

    private final ObjectMapper objectMapper = new ObjectMapper();

    public List<CompletedChallenges> fromJsonToCompletedChallengesList(String json){
        try {
            return objectMapper.readValue(json, new TypeReference<>() {});
        }
        catch (JsonProcessingException ex){
            ex.printStackTrace();
            return null;
        }
    }

    public String fromCompletedChallengesListToJson(List<CompletedChallenges> list) {
        try {
            return objectMapper.writeValueAsString(list);
        }
        catch (JsonProcessingException ex){
            return "";
        }
    }

    @Override
    public String convertToDatabaseColumn(List<CompletedChallenges> attribute) {
        return fromCompletedChallengesListToJson(attribute);
    }

    @Override
    public List<CompletedChallenges> convertToEntityAttribute(String dbData) {
        return fromJsonToCompletedChallengesList(dbData);
    }
}
