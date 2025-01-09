package com.example.a12thdreamapp;

import java.util.ArrayList;
import java.util.List;

public class Team {
    private String id;
    private String name;
    private List<Player> players;

    // Firebas boş constructor
    public Team() {}

    public Team(String name) {
        this.name = name;
        this.players = new ArrayList<>();
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

    public List<Player> getPlayers() {
        return players;
    }

    public void setPlayers(List<Player> players) {
        this.players = players;
    }
}
