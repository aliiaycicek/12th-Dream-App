package com.example.a12thdreamapp;

public class Player {
    private int id;
    private String name;
    private String position;

    public Player(int id, String name, String position) {
        this.id = id;
        this.name = name;
        this.position = position;
    }

    public int getId() { return id; }
    public String getName() { return name; }
    public String getPosition() { return position; }
}

