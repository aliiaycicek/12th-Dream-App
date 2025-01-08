package com.example.a12thdreamapp;

public class Formation {
    private String name;
    private int defenders;
    private int midfielders;
    private int forwards;

    public Formation(String name, int defenders, int midfielders, int forwards) {
        this.name = name;
        this.defenders = defenders;
        this.midfielders = midfielders;
        this.forwards = forwards;
    }

    public String getName() {
        return name;
    }

    public int getDefenders() {
        return defenders;
    }

    public int getMidfielders() {
        return midfielders;
    }

    public int getForwards() {
        return forwards;
    }

    @Override
    public String toString() {
        return name;
    }
} 