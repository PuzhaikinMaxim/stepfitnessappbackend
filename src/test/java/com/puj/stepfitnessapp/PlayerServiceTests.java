package com.puj.stepfitnessapp;

import com.puj.stepfitnessapp.level.LevelService;
import com.puj.stepfitnessapp.player.Player;
import com.puj.stepfitnessapp.player.PlayerRepository;
import com.puj.stepfitnessapp.player.PlayerService;
import com.puj.stepfitnessapp.player.inventory.PlayerInventory;
import com.puj.stepfitnessapp.player.inventory.item.InventoryItem;
import com.puj.stepfitnessapp.playerstatistics.PlayerStatisticsService;
import com.puj.stepfitnessapp.user.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Arrays;
import java.util.Optional;

@SpringBootTest
public class PlayerServiceTests {

    private PlayerService playerService;

    @MockBean
    private PlayerRepository repository;

    @MockBean
    private PlayerStatisticsService playerStatisticsService;

    @MockBean
    private LevelService levelService;

    @Captor
    private ArgumentCaptor<Player> playerArgumentCaptor;

    private User user;

    private Player player;

    @BeforeEach
    public void setup() {
        playerService = new PlayerService(repository, playerStatisticsService, levelService);
        user = getUser();
        player = getPlayer();
    }

    @Test
    void testCreatePlayer() {
        playerService.addPlayer(user);
        Mockito.verify(repository).save(playerArgumentCaptor.capture());
        Player player = playerArgumentCaptor.getValue();
        Assertions.assertEquals(0, player.getXp());
        Assertions.assertEquals(1, player.getEndurance());
        Assertions.assertEquals(1, player.getStrength());
        Assertions.assertEquals(user.getUsername(),player.getUser().getUsername());
    }

    @Test
    void testAddInventoryItems() {
        var itemsList = Arrays.asList(getInventoryItem());
        playerService.addInventoryItems(player,itemsList);
        Assertions.assertEquals(1,player.getInventory().getInventoryItems().size());
    }

    @Test
    void testCalculation() {
        var inventory = player.getInventory();
        inventory.addItem(getInventoryItem());
        inventory.addItem(getInventoryItem());
        inventory.equipItem(0,1);
        inventory.equipItem(1,2);
        var pointsGained = playerService.calculatePointsGained(player, 100);
        Assertions.assertEquals(280, pointsGained);
        var additionalMinutes = playerService.calculateMinutesToFinishChallenge(player,60);
        Assertions.assertEquals(180,additionalMinutes);
    }

    private User getUser() {
        user = new User("test","test@gmail.com","testpass");
        user.setUserId(1L);
        return user;
    }

    private InventoryItem getInventoryItem() {
        var inventoryItem = new InventoryItem();
        inventoryItem.setPointsFixed(20);
        inventoryItem.setPointsMultiplier(0.5);
        inventoryItem.setPlusTimeMinutes(15);
        inventoryItem.setTimeMultiplier(0.5);
        return inventoryItem;
    }

    private Player getPlayer() {
        var player = new Player();
        player.setUser_id(1L);
        player.setInventory(new PlayerInventory());
        player.setEndurance(0);
        player.setStrength(0);
        return player;
    }
}
