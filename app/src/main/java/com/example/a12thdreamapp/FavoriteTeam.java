package com.example.a12thdreamapp;

import java.util.List;

public class FavoriteTeam {
    private String id;
    private String name;
    private String formation;
    private List<String> players;
    private String coach;

    public FavoriteTeam() {
        // Firebase için boş constructor gerekli
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

    public String getFormation() {
        return formation;
    }

    public void setFormation(String formation) {
        this.formation = formation;
    }

    public List<String> getPlayers() {
        return players;
    }

    public void setPlayers(List<String> players) {
        this.players = players;
    }

    public String getCoach() {
        return coach;
    }

    public void setCoach(String coach) {
        this.coach = coach;
    }
} 