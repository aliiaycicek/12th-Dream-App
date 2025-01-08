package com.example.a12thdreamapp;

import android.app.Application;
import android.util.Log;
import com.google.firebase.database.FirebaseDatabase;

public class DreamTeamApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        
        // Firebase offline capabilities
        FirebaseDatabase.getInstance().setPersistenceEnabled(true);
        
        // Firebase'i başlat ve örnek verileri ekle
        Log.d("DreamTeamApplication", "Firebase başlatılıyor...");
        FirebaseManager.initializeDatabase();
    }
} 