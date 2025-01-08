package com.example.a12thdreamapp;

public class Coach {
    private String id;
    private String name;
    private boolean isFenerbahceCoach;

    // Firebase için boş constructor
    public Coach() {}

    public Coach(String name, boolean isFenerbahceCoach) {
        this.name = name;
        this.isFenerbahceCoach = isFenerbahceCoach;
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

    public boolean isFenerbahceCoach() {
        return isFenerbahceCoach;
    }

    public void setFenerbahceCoach(boolean fenerbahceCoach) {
        isFenerbahceCoach = fenerbahceCoach;
    }
} 