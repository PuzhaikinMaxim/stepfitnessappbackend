package com.puj.stepfitnessapp.userdailychallenge.dailychallenge;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.puj.stepfitnessapp.SimpleListConverter;
import com.puj.stepfitnessapp.playerstatistics.completedchallenges.CompletedChallenges;

import java.util.List;

public class DailyChallengeConverter extends SimpleListConverter<DailyChallenge> {

    private final ObjectMapper objectMapper = new ObjectMapper();

    public List<DailyChallenge> fromJsonToCompletedChallengesList(String json){
        try {
            return objectMapper.readValue(json, new TypeReference<>() {});
        }
        catch (JsonProcessingException ex){
            ex.printStackTrace();
            return null;
        }
    }

    public String fromCompletedChallengesListToJson(List<DailyChallenge> list) {
        try {
            return objectMapper.writeValueAsString(list);
        }
        catch (JsonProcessingException ex){
            return "";
        }
    }

    @Override
    public String convertToDatabaseColumn(List<DailyChallenge> attribute) {
        return fromCompletedChallengesListToJson(attribute);
    }

    @Override
    public List<DailyChallenge> convertToEntityAttribute(String dbData) {
        return fromJsonToCompletedChallengesList(dbData);
    }
}
