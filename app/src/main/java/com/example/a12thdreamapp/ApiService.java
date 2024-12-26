package com.example.a12thdreamapp;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;
import com.google.gson.JsonObject;

public interface ApiService {
    // Headers'ı kaldırıyoruz çünkü RetrofitClient'da ekliyoruz
    @GET("v3/players/squads")
    Call<JsonObject> getTeamPlayers(@Query("team") int teamId);

    @GET("v3/players")
    Call<JsonObject> getPlayerStats(
            @Query("id") int playerId,
            @Query("season") int season
    );

    @GET("v3/teams")
    Call<JsonObject> getTeamInfo(@Query("id") int teamId);

    @GET("v3/fixtures")
    Call<JsonObject> getTeamFixtures(
            @Query("team") int teamId,
            @Query("last") int lastMatches
    );
}

