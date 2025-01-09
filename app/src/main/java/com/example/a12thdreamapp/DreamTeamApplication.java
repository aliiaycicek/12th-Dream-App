package com.example.a12thdreamapp;

import android.app.Application;
import android.util.Log;
import com.google.firebase.database.FirebaseDatabase;

public class DreamTeamApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        

        FirebaseDatabase.getInstance().setPersistenceEnabled(true);
        

        Log.d("DreamTeamApplication", "Firebase başlatılıyor...");
        FirebaseManager.initializeDatabase();
    }
} 