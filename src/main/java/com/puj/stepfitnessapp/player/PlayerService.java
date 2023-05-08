package com.puj.stepfitnessapp.player;

import com.puj.stepfitnessapp.guild.Guild;
import com.puj.stepfitnessapp.items.Item;
import com.puj.stepfitnessapp.level.Level;
import com.puj.stepfitnessapp.level.LevelService;
import com.puj.stepfitnessapp.player.inventory.PlayerInventory;
import com.puj.stepfitnessapp.player.inventory.item.InventoryItem;
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

    private final LevelService levelService;

    private final PlayerDataMapper playerDataMapper = new PlayerDataMapper();

    private final CharacteristicsMapper characteristicsMapper = new CharacteristicsMapper();

    @Autowired
    public PlayerService(
            PlayerRepository repository,
            PlayerStatisticsService playerStatisticsService,
            LevelService levelService
    ){
        this.repository = repository;
        this.playerStatisticsService = playerStatisticsService;
        this.levelService = levelService;
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

    public void addItems(Player player, List<Item> items) {
        final var mapper = new InventoryItemMapper();
        final var inventoryItems = mapper.mapItemListToInventoryItemList(items);

        player.getInventory().addItems(inventoryItems);

        repository.save(player);
    }

    public void addInventoryItems(Player player, List<InventoryItem> items){
        player.getInventory().addItems(items);

        repository.save(player);
    }

    public void addInventoryItem(Player player,Item item) {
        final var mapper = new InventoryItemMapper();
        final var inventoryItem = mapper.mapItemToInventoryItem(item);

        player.getInventory().addItem(inventoryItem);

        repository.save(player);
    }

    public void equipItem(Player player, int inventoryItemId, int itemSlot){
        player.getInventory().equipItem(inventoryItemId, itemSlot);

        repository.save(player);
    }

    public void unEquipItem(Player player,int itemSlot) {
        player.getInventory().unEquipItem(itemSlot);
        repository.save(player);
    }

    public void incrementStrength(Player player) {
        player.incrementStrength();
        repository.save(player);
    }

    public void incrementEndurance(Player player) {
        player.incrementEndurance();
        repository.save(player);
    }

    public void assignToGuild(Player player, Guild guild) {
        player.setGuild(guild);
        repository.save(player);
    }

    public void removeFromGuild(Player player) {
        player.setGuild(null);
        repository.save(player);
    }

    public CharacteristicsDto getCharacteristics(Long userId) {
        var player = getPlayerById(userId);
        return characteristicsMapper.mapPlayerToCharacteristicsDto(player);
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

    public PlayerProfileData getPlayerProfileData(Long userId) {
        var playerResponse = repository.findPlayerByUser_id(userId);
        if(playerResponse.isEmpty()) return null;
        var player = playerResponse.get();
        var playerStatistics = playerStatisticsService.getStatistics(userId);
        return playerDataMapper.mapToPlayerProfileData(player,playerStatistics);
    }

    public void addPlayerXp(Player player, int xp){
        int playerXp = player.getXp() + xp;
        if(playerXp >= player.getXpToNextLevel()){
            playerXp -= player.getXpToNextLevel();

            int newPlayerLevel = player.getLevel();
            Level level = levelService.getLevel(newPlayerLevel);
            player.setLevel(level.getLevel());
            player.setXpToNextLevel(level.getXp());
            addPlayerXp(player, playerXp);
        }
        player.setXp(playerXp);
    }

    public PlayerDataDto getPlayerDataByUserId(Long userId) {
        var player = getPlayerById(userId);
        return playerDataMapper.mapPlayerToPlayerDataDto(player);
    }

    public void unassignPlayerFromGuild(Player player) {
        player.setGuild(null);
        repository.save(player);
    }

    public Player getPlayerById(Long userId){
        return repository.findPlayerByUser_id(userId).get();
    }
}
