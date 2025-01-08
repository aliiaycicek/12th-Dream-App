package com.example.a12thdreamapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;
import java.util.ArrayList;

public class MenuActivity extends AppCompatActivity {

    private static final String TAG = "MenuActivity";

    // UI Bileşenleri
    private TextView textViewWelcome;
    private ImageButton buttonLogout;
    private CardView dreamKadroButton;

    // Firebase Bileşenleri
    private FirebaseAuth mAuth;
    private FirebaseDatabase database;
    private DatabaseReference usersRef;

    private TeamAdapter teamAdapter;

    private String currentUserId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        // Firebase Auth'dan kullanıcı ID'sini al
        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        if (currentUser != null) {
            currentUserId = currentUser.getUid();
        }

        initializeFirebase();
        initializeViews();
        setupUserInfo();
        setupClickListeners();

        // TeamAdapter'ı başlat
        teamAdapter = new TeamAdapter();
        teamAdapter.setOnTeamClickListener(team -> {
            // Takıma tıklandığında yapılacak işlemler
        });
        loadTeams(); // Takımları yükle
    }

    private void initializeFirebase() {
        try {
            mAuth = FirebaseAuth.getInstance();
            database = FirebaseDatabase.getInstance();
            usersRef = database.getReference("Users");

            FirebaseUser currentUser = mAuth.getCurrentUser();
            if (currentUser == null) {
                Log.d(TAG, "Kullanıcı oturum açmamış");
                goToLoginActivity();
            }
        } catch (Exception e) {
            Log.e(TAG, "Firebase başlatma hatası: ", e);
            Toast.makeText(this, "Bağlantı hatası oluştu", Toast.LENGTH_SHORT).show();
        }
    }

    private void initializeViews() {
        try {
            // Top bar elemanları
            textViewWelcome = findViewById(R.id.textViewWelcome);
            buttonLogout = findViewById(R.id.buttonLogout);
            dreamKadroButton = findViewById(R.id.dreamKadroButton);

            // Varsayılan hoşgeldin mesajı
            textViewWelcome.setText("Hoş geldiniz");

        } catch (Exception e) {
            Log.e(TAG, "View başlatma hatası: ", e);
        }
    }

    private void setupUserInfo() {
        FirebaseUser user = mAuth.getCurrentUser();
        if (user != null) {
            String uid = user.getUid();
            Log.d(TAG, "Kullanıcı bilgileri alınıyor. UID: " + uid);

            usersRef.child(uid).get()
                    .addOnSuccessListener(new OnSuccessListener<DataSnapshot>() {
                        @Override
                        public void onSuccess(DataSnapshot dataSnapshot) {
                            try {
                                if (dataSnapshot.exists()) {
                                    String name = dataSnapshot.child("name").getValue(String.class);
                                    String welcomeText = "Hoş geldin, " +
                                            (name != null && !name.isEmpty() ? name : user.getEmail());
                                    textViewWelcome.setText(welcomeText);
                                    Log.d(TAG, "Kullanıcı bilgileri başarıyla alındı: " + welcomeText);
                                } else {
                                    textViewWelcome.setText("Hoş geldin, " + user.getEmail());
                                    Log.d(TAG, "Kullanıcı verisi bulunamadı");
                                }
                            } catch (Exception e) {
                                Log.e(TAG, "Veri işleme hatası: ", e);
                            }
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Log.e(TAG, "Veri çekme hatası: ", e);
                            textViewWelcome.setText("Hoş geldin, " + user.getEmail());
                            Toast.makeText(MenuActivity.this,
                                    "Kullanıcı bilgileri alınamadı",
                                    Toast.LENGTH_SHORT).show();
                        }
                    });
        }
    }

    private void setupClickListeners() {
        // Çıkış butonu
        buttonLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                logoutUser();
            }
        });

        // Dream Kadro butonu
        dreamKadroButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "Dream Kadro butonuna tıklandı");
                startActivity(new Intent(MenuActivity.this, MainActivity.class));
            }
        });

        // Add Player butonu
        findViewById(R.id.addPlayerButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MenuActivity.this, AddPlayerActivity.class);
                startActivity(intent);
            }
        });

        // Favori Kadrolar butonu için tıklama olayı
        findViewById(R.id.favoriteTeamsButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MenuActivity.this, FavoriteTeamsActivity.class));
            }
        });
    }

    private void logoutUser() {
        try {
            Log.d(TAG, "Kullanıcı çıkış yapıyor");
            mAuth.signOut();

            // SharedPreferences temizle
            SharedPreferences preferences = getSharedPreferences("login", MODE_PRIVATE);
            preferences.edit().clear().apply();

            goToLoginActivity();

        } catch (Exception e) {
            Log.e(TAG, "Çıkış yapma hatası: ", e);
            Toast.makeText(this, "Çıkış yapılırken hata oluştu", Toast.LENGTH_SHORT).show();
        }
    }

    private void goToLoginActivity() {
        Intent intent = new Intent(MenuActivity.this, LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (mAuth.getCurrentUser() == null) {
            goToLoginActivity();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        setupUserInfo(); // Kullanıcı bilgilerini yenile
    }

    private void loadTeams() {
        FirebaseManager.getFavoriteTeams(currentUserId, new FirebaseManager.OnFavoriteTeamsLoadedListener() {
            @Override
            public void onTeamsLoaded(List<FavoriteTeam> teams) {
                if (teamAdapter != null) {
                    teamAdapter.setTeams(teams);
                }
            }

            @Override
            public void onError(String error) {
                Toast.makeText(MenuActivity.this, 
                    "Takımlar yüklenirken hata oluştu: " + error, Toast.LENGTH_SHORT).show();
            }
        });
    }
}
