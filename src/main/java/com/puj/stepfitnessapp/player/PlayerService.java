package com.puj.stepfitnessapp.player;

import com.puj.stepfitnessapp.items.Item;
import com.puj.stepfitnessapp.player.inventory.PlayerInventory;
import com.puj.stepfitnessapp.player.inventory.item.InventoryItemMapper;
import com.puj.stepfitnessapp.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PlayerService {

    private final PlayerRepository repository;

    @Autowired
    public PlayerService(PlayerRepository repository){
        this.repository = repository;
    }

    public void addPlayer(User user) {
        Player p = new Player(
                user.getUserId(),
                user,
                0,
                0,
                200,
                1,
                1,
                0,
                new PlayerInventory()
        );
        repository.save(p);
    }

    public void addInventoryItems(User user, List<Item> items) {
        final var player = getPlayerById(user.getUserId());

        final var mapper = new InventoryItemMapper();
        final var inventoryItems = mapper.mapItemListToInventoryItemList(items);

        player.getInventory().addItems(inventoryItems);

        repository.save(player);
    }

    public void addInventoryItem(User user,Item item) {
        final var player = getPlayerById(user.getUserId());

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

    private Player getPlayerById(Long id) {
        return repository.findPlayerByUser_id(id).get();
    }
}
