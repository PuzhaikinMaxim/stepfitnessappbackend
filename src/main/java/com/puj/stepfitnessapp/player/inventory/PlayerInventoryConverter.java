package com.puj.stepfitnessapp.player.inventory;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.persistence.AttributeConverter;

public class PlayerInventoryConverter implements AttributeConverter<PlayerInventory, String> {

    private final ObjectMapper objectMapper = new ObjectMapper();

    public PlayerInventory fromJsonToPlayerInventory(String json){
        try {
            return objectMapper.readValue(json, PlayerInventory.class);
        }
        catch (JsonProcessingException ex) {
            return null;
        }
    }

    public String fromPlayerInventoryToJson(PlayerInventory inventory){
        try {
            return objectMapper.writeValueAsString(inventory);
        }
        catch (JsonProcessingException ex) {
            return "";
        }
    }

    @Override
    public String convertToDatabaseColumn(PlayerInventory attribute) {
        return fromPlayerInventoryToJson(attribute);
    }

    @Override
    public PlayerInventory convertToEntityAttribute(String dbData) {
        return fromJsonToPlayerInventory(dbData);
    }
}
