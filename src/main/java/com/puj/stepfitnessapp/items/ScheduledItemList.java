package com.puj.stepfitnessapp.items;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
@Getter
public class ScheduledItemList {

    private ItemsRepository repository;

    private final static int FIVE_MINUTE_DELAY = 1000 * 60 * 5;

    private Map<Integer, List<Item>> itemGroupsByRarity;

    @Autowired
    public ScheduledItemList(ItemsRepository repository) {
        this.repository = repository;
    }

    @Scheduled(fixedDelay = FIVE_MINUTE_DELAY)
    public void updateItemList() {
        itemGroupsByRarity = repository.findAll().stream().collect(Collectors.groupingBy(
                s -> s.getRarity().getRarityLevel()
        ));
    }
}
