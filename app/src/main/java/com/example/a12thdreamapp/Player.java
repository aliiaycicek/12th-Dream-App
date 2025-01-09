package com.example.a12thdreamapp;

public class Player {
    private String id;
    private String name;
    private String position;
    private boolean isFenerbahcePlayer;
    private PlayerCategory category;


    public Player() {}

    public Player(String name, String position) {
        this.name = name;
        this.position = position;
        this.isFenerbahcePlayer = true;
        this.category = PlayerCategory.CURRENT;
    }

    public Player(String name, String position, boolean isFenerbahcePlayer, PlayerCategory category) {
        this.name = name;
        this.position = position;
        this.isFenerbahcePlayer = isFenerbahcePlayer;
        this.category = category;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPosition() {
        return position;
    }


    public PlayerCategory getCategory() {
        return category;
    }
    
    public enum PlayerCategory {
        LEGEND,    // Efsane
        MODERN,    // 2010-2024 arası
        CURRENT,   // Mevcut kadro
        CUSTOM     // Kullanıcı eklemeleri
    }

    @Override
    public String toString() {
        return name;
    }
}

