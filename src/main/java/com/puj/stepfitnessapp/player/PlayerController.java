package com.puj.stepfitnessapp.player;

import com.puj.stepfitnessapp.player.inventory.item.dto.InventoryItemDto;
import com.puj.stepfitnessapp.player.inventory.item.InventoryItemMapper;
import com.puj.stepfitnessapp.player.inventory.item.dto.ItemEquipDataDto;
import com.puj.stepfitnessapp.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("player")
public class PlayerController {

    private PlayerService service;

    private InventoryItemMapper inventoryItemMapper = new InventoryItemMapper();

    @Autowired
    public PlayerController(PlayerService service) {
        this.service = service;
    }

    @GetMapping("get_player_data")
    public ResponseEntity<PlayerDataDto> getPlayerData() {
        var userDataDto = service.getPlayerDataByUserId(getUserId());
        return createResponseEntity(HttpStatus.OK, userDataDto);
    }

    @GetMapping("get_characteristics")
    public ResponseEntity<CharacteristicsDto> getCharacteristics() {
        var characteristics = service.getCharacteristics(getUserId());
        if(characteristics != null){
            return createResponseEntity(HttpStatus.OK, characteristics);
        }
        return createResponseEntity(HttpStatus.NOT_FOUND, null);
    }

    @PutMapping("increase_endurance")
    public void increaseEndurance() {
        var player = service.getPlayerById(getUserId());
        service.incrementEndurance(player);
    }

    @PutMapping("increase_strength")
    public void increaseStrength() {
        var player = service.getPlayerById(getUserId());
        service.incrementStrength(player);
    }

    @PutMapping("equip_item")
    public ResponseEntity<String> equipItem(@RequestBody ItemEquipDataDto itemEquipDataDto) {
        var player = service.getPlayerById(getUserId());
        service.equipItem(player, itemEquipDataDto.getInventoryId(),  itemEquipDataDto.getSlot());
        return createResponseEntity(HttpStatus.OK, "Success");
    }

    @PutMapping("un_equip_item/{slot}")
    public ResponseEntity<String> unEquipItem(@PathVariable int slot) {
        var player = service.getPlayerById(getUserId());
        service.unEquipItem(player, slot);
        return createResponseEntity(HttpStatus.OK, "Success");
    }

    @GetMapping("get_inventory_items")
    public ResponseEntity<List<InventoryItemDto>> getInventoryItems() {
        var player = service.getPlayerById(getUserId());
        var inventoryItemDtoList = inventoryItemMapper.mapInventoryItemListToInventoryItemDtoList(
                player.getInventory().getInventoryItems(),
                player.getInventory().getEquippedItems()
        );
        return createResponseEntity(HttpStatus.OK, inventoryItemDtoList);
    }

    private long getUserId() {
        final var userDetails = (User) SecurityContextHolder.getContext().getAuthentication().getDetails();
        return userDetails.getUserId();
    }

    private <T> ResponseEntity<T> createResponseEntity(HttpStatus status, T body) {
        return ResponseEntity
                .status(status)
                .body(body);
    }
}
