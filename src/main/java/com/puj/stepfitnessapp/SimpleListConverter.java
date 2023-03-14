package com.puj.stepfitnessapp;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.persistence.AttributeConverter;
import java.util.List;

public class SimpleListConverter<T> implements AttributeConverter<List<T>, String> {

    private final ObjectMapper objectMapper = new ObjectMapper();

    public List<T> fromJsonToList(String json){
        try {
            return objectMapper.readValue(json, new TypeReference<>() {});
        }
        catch (JsonProcessingException ex){
            ex.printStackTrace();
            return null;
        }
    }

    public String fromListToJson(List<T> list) {
        try {
            return objectMapper.writeValueAsString(list);
        }
        catch (JsonProcessingException ex){
            return "";
        }
    }

    @Override
    public String convertToDatabaseColumn(List<T> attribute) {
        return fromListToJson(attribute);
    }

    @Override
    public List<T> convertToEntityAttribute(String dbData) {
        return fromJsonToList(dbData);
    }
}
