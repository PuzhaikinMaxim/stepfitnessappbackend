package com.puj.stepfitnessapp;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class TestDbModel {

    private String id;

    public void setId(String id) {
        this.id = id;
    }

    @Id
    public String getId() {
        return id;
    }
}
