package com.puj.stepfitnessapp.player;

import com.puj.stepfitnessapp.items.Item;
import com.puj.stepfitnessapp.player.inventory.PlayerInventory;
import com.puj.stepfitnessapp.player.inventory.item.InventoryItemMapper;
import com.puj.stepfitnessapp.playerstatistics.PlayerStatisticsService;
import com.puj.stepfitnessapp.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PlayerService {

    private final PlayerRepository repository;

    private final PlayerStatisticsService playerStatisticsService;

    private final PlayerDataMapper playerDataMapper = new PlayerDataMapper();

    @Autowired
    public PlayerService(PlayerRepository repository, PlayerStatisticsService playerStatisticsService){
        this.repository = repository;
        this.playerStatisticsService = playerStatisticsService;
    }

    public void addPlayer(User user) {
        Player p = new Player(
                user.getUserId(),
                user,
                1,
                0,
                200,
                1,
                1,
                0,
                new PlayerInventory()
        );
        repository.save(p);
        addPlayerStatistics(p);
    }

    private void addPlayerStatistics(Player player) {
        playerStatisticsService.addStatistics(player);
    }

    public void addInventoryItems(Player player, List<Item> items) {
        final var mapper = new InventoryItemMapper();
        final var inventoryItems = mapper.mapItemListToInventoryItemList(items);

        player.getInventory().addItems(inventoryItems);

        repository.save(player);
    }

    public void addInventoryItem(Player player,Item item) {
        final var mapper = new InventoryItemMapper();
        final var inventoryItem = mapper.mapItemToInventoryItem(item);

        player.getInventory().addItem(inventoryItem);

        repository.save(player);
    }

    public void equipItem(User user, int inventoryItemId, int itemSlot){
        final var player = getPlayerById(user.getUserId());

        player.getInventory().equipItem(inventoryItemId, itemSlot);

        repository.save(player);
    }

    public int calculateMinutesToFinishChallenge(Player player, int baseMinutesToFinish){
        return (int) (
                player.getInventory().calculateAmountOfMinutes(baseMinutesToFinish)
                        *((100.0+player.getEndurance())/100.0)
        );
    }

    public int calculatePointsGained(Player player, int amountOfSteps){
        return (int) (
                player.getInventory().calculateAmountOfPoints(amountOfSteps)
                        *((100.0+player.getStrength())/100.0)
                );
    }

    public PlayerDataDto getPlayerDataByUserId(Long userId) {
        var player = getPlayerById(userId);
        return playerDataMapper.mapPlayerToPlayerDataDto(player);
    }

    public Player getPlayerById(Long userId){
        return repository.findPlayerByUser_id(userId).get();
    }
}
