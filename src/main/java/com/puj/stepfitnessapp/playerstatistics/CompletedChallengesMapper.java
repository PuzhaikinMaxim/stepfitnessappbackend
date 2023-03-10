package com.puj.stepfitnessapp.playerstatistics;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.List;

public class CompletedChallengesMapper {

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
}
