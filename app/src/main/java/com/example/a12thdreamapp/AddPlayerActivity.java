package com.example.a12thdreamapp;

import android.os.Bundle;
import android.os.AsyncTask;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import android.widget.ArrayAdapter;
import java.util.List;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;

public class AddPlayerActivity extends AppCompatActivity {
    private EditText nameInput;
    private Spinner positionSpinner, categorySpinner;
    private Button addButton;
    private PlayerAdapter adapter;
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_player);

        // View'ları bağla
        nameInput = findViewById(R.id.playerNameInput);
        positionSpinner = findViewById(R.id.positionSpinner);
        categorySpinner = findViewById(R.id.categorySpinner);
        addButton = findViewById(R.id.addPlayerButton);
        recyclerView = findViewById(R.id.playersRecyclerView);

        // RecyclerView ve adapter'ı ayarla
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new PlayerAdapter(new ArrayList<>(), player -> {
            // Oyuncu tıklama işlemleri
        });
        recyclerView.setAdapter(adapter);

        // Spinner'ları ayarla
        setupSpinners();

        addButton.setOnClickListener(v -> addPlayer());
        
        // Oyuncuları yükle
        loadPlayers();
    }

    private void setupSpinners() {
        // Pozisyon Spinner'ı
        ArrayAdapter<CharSequence> positionAdapter = ArrayAdapter.createFromResource(this,
                R.array.positions, android.R.layout.simple_spinner_item);
        positionAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        positionSpinner.setAdapter(positionAdapter);

        // Kategori Spinner'ı
        ArrayAdapter<CharSequence> categoryAdapter = ArrayAdapter.createFromResource(this,
                R.array.categories, android.R.layout.simple_spinner_item);
        categoryAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        categorySpinner.setAdapter(categoryAdapter);
    }

    private void addPlayer() {
        String name = nameInput.getText().toString().trim();
        String position = positionSpinner.getSelectedItem().toString();
        String categoryStr = categorySpinner.getSelectedItem().toString();

        if (name.isEmpty()) {
            nameInput.setError("İsim boş olamaz");
            return;
        }

        // Kategoriyi enum'a çevir
        Player.PlayerCategory category;
        switch (categoryStr) {
            case "Efsane":
                category = Player.PlayerCategory.LEGEND;
                break;
            case "Modern":
                category = Player.PlayerCategory.MODERN;
                break;
            case "Mevcut":
                category = Player.PlayerCategory.CURRENT;
                break;
            default:
                category = Player.PlayerCategory.CUSTOM;
                break;
        }

        // Yeni oyuncuyu oluştur
        Player newPlayer = new Player(name, position, true, category);

        // Firebase'e kaydet
        FirebaseManager.addPlayer(newPlayer, new FirebaseManager.OnPlayerAddedListener() {
            @Override
            public void onPlayerAdded() {
                Toast.makeText(AddPlayerActivity.this, 
                    "Oyuncu başarıyla eklendi", Toast.LENGTH_SHORT).show();
                
                // Oyuncu listesini güncelle
                loadPlayers();
                
                // Input alanlarını temizle
                nameInput.setText("");
                positionSpinner.setSelection(0);
                categorySpinner.setSelection(0);
            }

            @Override
            public void onError(String error) {
                Toast.makeText(AddPlayerActivity.this, 
                    "Oyuncu eklenirken hata oluştu: " + error, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void loadPlayers() {
        FirebaseManager.getAllPlayers(new FirebaseManager.OnPlayersLoadedListener() {
            @Override
            public void onPlayersLoaded(List<Player> players) {
                // Oyuncuları adapter'a set et
                if (adapter != null) {
                    adapter.updatePlayers(players);
                }
            }

            @Override
            public void onError(String error) {
                Toast.makeText(AddPlayerActivity.this, 
                    "Oyuncular yüklenirken hata oluştu: " + error, Toast.LENGTH_SHORT).show();
            }
        });
    }
} 