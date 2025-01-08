package com.example.a12thdreamapp;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import java.util.ArrayList;
import java.util.List;
import android.util.Log;
import com.example.a12thdreamapp.Coach;

public class FirebaseManager {
    private static FirebaseManager instance;
    private final DatabaseReference database;

    private FirebaseManager() {
        database = FirebaseDatabase.getInstance().getReference();
    }

    public static FirebaseManager getInstance() {
        if (instance == null) {
            instance = new FirebaseManager();
        }
        return instance;
    }

    public static void initializeDatabase() {
        getInstance();
    }

    // Oyuncu metodları
    public static void addPlayer(Player player, OnPlayerAddedListener listener) {
        getInstance().addPlayerInternal(player, listener);
    }

    private void addPlayerInternal(Player player, OnPlayerAddedListener listener) {
        String playerId = database.child("players").push().getKey();
        if (playerId != null) {
            player.setId(playerId);
            database.child("players").child(playerId).setValue(player)
                .addOnSuccessListener(aVoid -> listener.onPlayerAdded())
                .addOnFailureListener(e -> listener.onError(e.getMessage()));
        }
    }

    // Takım metodları
    public static void getAllTeams(OnTeamsLoadedListener listener) {
        getInstance().getAllTeamsInternal(listener);
    }

    private void getAllTeamsInternal(OnTeamsLoadedListener listener) {
        database.child("teams").get()
            .addOnSuccessListener(snapshot -> {
                List<Team> teams = new ArrayList<>();
                for (DataSnapshot teamSnapshot : snapshot.getChildren()) {
                    Team team = teamSnapshot.getValue(Team.class);
                    if (team != null) {
                        teams.add(team);
                    }
                }
                listener.onTeamsLoaded(teams);
            })
            .addOnFailureListener(e -> listener.onError(e.getMessage()));
    }

    public static void deleteTeam(Team team, OnTeamDeletedListener listener) {
        getInstance().deleteTeamInternal(team, listener);
    }

    private void deleteTeamInternal(Team team, OnTeamDeletedListener listener) {
        if (team.getId() != null) {
            database.child("teams").child(team.getId()).removeValue()
                .addOnSuccessListener(aVoid -> listener.onTeamDeleted())
                .addOnFailureListener(e -> listener.onError(e.getMessage()));
        }
    }

    public static void getFavoriteTeams(String userId, OnFavoriteTeamsLoadedListener listener) {
        getInstance().getFavoriteTeamsInternal(userId, listener);
    }

    private void getFavoriteTeamsInternal(String userId, OnFavoriteTeamsLoadedListener listener) {
        database.child("favoriteTeams")
            .orderByChild("userId")
            .equalTo(userId)
            .get()
            .addOnSuccessListener(snapshot -> {
                List<FavoriteTeam> teams = new ArrayList<>();
                for (DataSnapshot teamSnapshot : snapshot.getChildren()) {
                    FavoriteTeam team = teamSnapshot.getValue(FavoriteTeam.class);
                    if (team != null) {
                        teams.add(team);
                    }
                }
                listener.onTeamsLoaded(teams);
            })
            .addOnFailureListener(e -> listener.onError(e.getMessage()));
    }

    public static void deleteFavoriteTeam(FavoriteTeam team, OnTeamDeletedListener listener) {
        getInstance().deleteFavoriteTeamInternal(team, listener);
    }

    private void deleteFavoriteTeamInternal(FavoriteTeam team, OnTeamDeletedListener listener) {
        if (team.getId() != null) {
            database.child("favoriteTeams").child(team.getId()).removeValue()
                .addOnSuccessListener(aVoid -> listener.onTeamDeleted())
                .addOnFailureListener(e -> listener.onError(e.getMessage()));
        }
    }

    // Coach metodları
    public static void addCoach(Coach coach, OnCoachAddedListener listener) {
        getInstance().addCoachInternal(coach, listener);
    }

    private void addCoachInternal(Coach coach, OnCoachAddedListener listener) {
        String coachId = database.child("coaches").push().getKey();
        if (coachId != null) {
            coach.setId(coachId);
            database.child("coaches").child(coachId).setValue(coach)
                .addOnSuccessListener(aVoid -> listener.onCoachAdded())
                .addOnFailureListener(e -> listener.onError(e.getMessage()));
        }
    }

    // Interface tanımlamaları
    public interface OnPlayerAddedListener {
        void onPlayerAdded();
        void onError(String error);
    }

    public interface OnTeamsLoadedListener {
        void onTeamsLoaded(List<Team> teams);
        void onError(String error);
    }

    public interface OnTeamDeletedListener {
        void onTeamDeleted();
        void onError(String error);
    }

    public interface OnFavoriteTeamsLoadedListener {
        void onTeamsLoaded(List<FavoriteTeam> teams);
        void onError(String error);
    }

    public interface OnCoachAddedListener {
        void onCoachAdded();
        void onError(String error);
    }

    public static void getAllPlayers(OnPlayersLoadedListener listener) {
        getInstance().getAllPlayersInternal(listener);
    }

    private void getAllPlayersInternal(OnPlayersLoadedListener listener) {
        database.child("players").get()
            .addOnSuccessListener(snapshot -> {
                List<Player> players = new ArrayList<>();
                for (DataSnapshot playerSnapshot : snapshot.getChildren()) {
                    Player player = playerSnapshot.getValue(Player.class);
                    if (player != null) {
                        players.add(player);
                    }
                }
                listener.onPlayersLoaded(players);
            })
            .addOnFailureListener(e -> listener.onError(e.getMessage()));
    }

    public static void clearDatabase(OnDatabaseClearedListener listener) {
        getInstance().clearDatabaseInternal(listener);
    }

    private void clearDatabaseInternal(OnDatabaseClearedListener listener) {
        // Önce players düğümünü temizle
        database.child("players").removeValue()
            .addOnSuccessListener(aVoid -> {
                // Sonra coaches düğümünü temizle
                database.child("coaches").removeValue()
                    .addOnSuccessListener(aVoid2 -> listener.onDatabaseCleared())
                    .addOnFailureListener(e -> Log.e("FirebaseManager", 
                        "Teknik direktörler temizlenirken hata: " + e.getMessage()));
            })
            .addOnFailureListener(e -> Log.e("FirebaseManager", 
                "Oyuncular temizlenirken hata: " + e.getMessage()));
    }

    public interface OnDatabaseClearedListener {
        void onDatabaseCleared();
    }

    public interface OnPlayersLoadedListener {
        void onPlayersLoaded(List<Player> players);
        void onError(String error);
    }

    public interface OnCoachesLoadedListener {
        void onCoachesLoaded(List<Coach> coaches);
        void onError(String error);
    }

    public static void getAllCoaches(OnCoachesLoadedListener listener) {
        getInstance().getAllCoachesInternal(listener);
    }

    private void getAllCoachesInternal(OnCoachesLoadedListener listener) {
        database.child("coaches").get()
            .addOnSuccessListener(snapshot -> {
                List<Coach> coaches = new ArrayList<>();
                for (DataSnapshot coachSnapshot : snapshot.getChildren()) {
                    Coach coach = coachSnapshot.getValue(Coach.class);
                    if (coach != null) {
                        coaches.add(coach);
                    }
                }
                listener.onCoachesLoaded(coaches);
            })
            .addOnFailureListener(e -> listener.onError(e.getMessage()));
    }

    public interface OnFavoriteTeamAddedListener {
        void onFavoriteTeamAdded();
        void onError(String error);
    }

    public static void addFavoriteTeam(FavoriteTeam team, OnFavoriteTeamAddedListener listener) {
        getInstance().addFavoriteTeamInternal(team, listener);
    }

    private void addFavoriteTeamInternal(FavoriteTeam team, OnFavoriteTeamAddedListener listener) {
        String teamId = database.child("favoriteTeams").push().getKey();
        if (teamId != null) {
            team.setId(teamId);
            database.child("favoriteTeams").child(teamId).setValue(team)
                .addOnSuccessListener(aVoid -> listener.onFavoriteTeamAdded())
                .addOnFailureListener(e -> listener.onError(e.getMessage()));
        }
    }
} 