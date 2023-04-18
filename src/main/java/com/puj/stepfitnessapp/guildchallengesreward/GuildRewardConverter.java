package com.puj.stepfitnessapp.guildchallengesreward;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.puj.stepfitnessapp.items.Item;
import com.puj.stepfitnessapp.player.inventory.PlayerInventory;

import javax.persistence.AttributeConverter;
import java.util.List;

public class GuildRewardConverter implements AttributeConverter<List<Item>, String> {

    private final ObjectMapper objectMapper = new ObjectMapper();

    public List<Item> fromJsonToItemsList(String json){
        try {
            return objectMapper.readValue(json,  new TypeReference<>() {});
        }
        catch (JsonProcessingException ex) {
            return null;
        }
    }

    public String fromItemsListToJson(List<Item> itemsList){
        try {
            return objectMapper.writeValueAsString(itemsList);
        }
        catch (JsonProcessingException ex) {
            return "";
        }
    }

    @Override
    public String convertToDatabaseColumn(List<Item> attribute) {
        return fromItemsListToJson(attribute);
    }

    @Override
    public List<Item> convertToEntityAttribute(String dbData) {
        return fromJsonToItemsList(dbData);
    }
}
