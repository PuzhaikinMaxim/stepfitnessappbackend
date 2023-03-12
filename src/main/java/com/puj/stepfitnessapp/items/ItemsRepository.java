package com.puj.stepfitnessapp.items;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface ItemsRepository extends JpaRepository<Item, Integer> {

    @Query("SELECT MAX (i.rarity) FROM Item i")
    public Optional<Integer> getMaximumItemRarity();
}
