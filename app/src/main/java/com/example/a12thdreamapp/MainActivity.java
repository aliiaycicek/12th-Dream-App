package com.example.a12thdreamapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.FirebaseApp;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    private TextView textViewWelcome;
    private ImageButton buttonLogout;
    private ApiService apiService;
    private FirebaseAnalytics mFirebaseAnalytics;
    private RecyclerView recyclerView;
    private PlayerAdapter playerAdapter;
    private List<Player> playerList;

    // Saha pozisyonları için ImageButton'lar
    private ImageButton goalkeeper;
    private ImageButton leftBack;
    private ImageButton centerBackLeft;
    private ImageButton centerBackRight;
    private ImageButton rightBack;
    private ImageButton defensiveMidfielder;
    private ImageButton leftMidfielder;
    private ImageButton rightMidfielder;
    private ImageButton attackingMidfielder;
    private ImageButton strikerLeft;
    private ImageButton strikerRight;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Firebase initialization
        FirebaseApp.initializeApp(this);
        mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);

        initializeViews();
        setupRecyclerView();
        setupClickListeners();
        setupApiService();
        getPlayersData();
    }

    private void setupRecyclerView() {
        recyclerView = findViewById(R.id.playerRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        playerList = new ArrayList<>();
        playerAdapter = new PlayerAdapter(playerList);
        recyclerView.setAdapter(playerAdapter);
    }

    private void initializeViews() {
        textViewWelcome = findViewById(R.id.textViewWelcome);
        buttonLogout = findViewById(R.id.buttonLogout);

        // Saha pozisyonları
        goalkeeper = findViewById(R.id.goalkeeper);
        leftBack = findViewById(R.id.left_back);
        centerBackLeft = findViewById(R.id.center_back_left);
        centerBackRight = findViewById(R.id.center_back_right);
        rightBack = findViewById(R.id.right_back);
        defensiveMidfielder = findViewById(R.id.defensive_midfielder);
        leftMidfielder = findViewById(R.id.left_midfielder);
        rightMidfielder = findViewById(R.id.right_midfielder);
        attackingMidfielder = findViewById(R.id.attacking_midfielder);
        strikerLeft = findViewById(R.id.striker_left);
        strikerRight = findViewById(R.id.striker_right);

        setupFieldPositionListeners();
    }

    private void setupFieldPositionListeners() {
        ImageButton[] fieldPositions = {
                goalkeeper, leftBack, centerBackLeft, centerBackRight, rightBack,
                defensiveMidfielder, leftMidfielder, rightMidfielder, attackingMidfielder,
                strikerLeft, strikerRight
        };

        for (ImageButton position : fieldPositions) {
            position.setOnClickListener(v -> handlePositionClick((ImageButton) v));
        }
    }

    private void handlePositionClick(ImageButton position) {
        // Pozisyon tıklandığında yapılacak işlemler
        // Örnek: Dialog gösterme veya oyuncu seçim ekranını açma
        String positionId = getResources().getResourceEntryName(position.getId());
        Toast.makeText(this, "Seçilen pozisyon: " + positionId, Toast.LENGTH_SHORT).show();
    }

    private void setupClickListeners() {
        buttonLogout.setOnClickListener(v -> {
            SharedPreferences preferences = getSharedPreferences("login", MODE_PRIVATE);
            SharedPreferences.Editor editor = preferences.edit();
            editor.clear();
            editor.apply();

            Intent intent = new Intent(MainActivity.this, LoginActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
            finish();
        });
    }

    private void setupApiService() {
        apiService = RetrofitClient.getInstance().getApiService();
    }

    private void getPlayersData() {
        Call<JsonObject> call = apiService.getTeamPlayers(33);
        call.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                if (response.isSuccessful() && response.body() != null) {
                    handleSuccessfulResponse(response.body());
                } else {
                    Toast.makeText(MainActivity.this,
                            "Veri çekme hatası: " + response.code(),
                            Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                Toast.makeText(MainActivity.this,
                        "Bağlantı hatası: " + t.getMessage(),
                        Toast.LENGTH_SHORT).show();
                Log.e("API_ERROR", "Error: " + t.getMessage());
            }
        });
    }

    private void handleSuccessfulResponse(JsonObject data) {
        if (data.has("response")) {
            JsonArray playersArray = data.getAsJsonArray("response");
            List<Player> players = new ArrayList<>();

            for (JsonElement element : playersArray) {
                JsonObject playerObj = element.getAsJsonObject();
                if (playerObj.has("player")) {
                    JsonObject playerData = playerObj.getAsJsonObject("player");
                    players.add(createPlayerFromJson(playerData));
                }
            }

            playerList.clear();
            playerList.addAll(players);
            playerAdapter.notifyDataSetChanged();
        }
    }

    private Player createPlayerFromJson(JsonObject playerData) {
        return new Player(
                playerData.get("id").getAsInt(),
                playerData.get("name").getAsString(),
                playerData.has("position") ?
                        playerData.get("position").getAsString() : "Unknown"
        );
    }
}

