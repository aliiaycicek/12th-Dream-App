package com.example.a12thdreamapp;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity {
    private EditText emailEditText, passwordEditText;
    private Button loginButton;
    private TextView textViewRegister;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // Firebase Auth instance'ını başlat
        mAuth = FirebaseAuth.getInstance();

        // View elemanlarını bağlama
        emailEditText = findViewById(R.id.emailEditText);
        passwordEditText = findViewById(R.id.passwordEditText);
        loginButton = findViewById(R.id.loginButton);
        textViewRegister = findViewById(R.id.textViewRegister);

        // Login butonu click listener
        loginButton.setOnClickListener(v -> loginUser());

        // Register text click listener
        textViewRegister.setOnClickListener(v -> startRegisterActivity());
    }

    @Override
    protected void onStart() {
        super.onStart();
        // Kullanıcı zaten giriş yapmış mı kontrol et
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser != null){
            startMenuActivity();
        }
    }

    private void loginUser() {
        String email = emailEditText.getText().toString().trim();
        String password = passwordEditText.getText().toString().trim();

        if (email.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, "Email ve şifre boş olamaz", Toast.LENGTH_SHORT).show();
            return;
        }

        // Firebase ile giriş işlemi
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, task -> {
                    if (task.isSuccessful()) {
                        // Kullanıcı bilgilerini SharedPreferences'a kaydet
                        saveUserLoginState();
                        // MenuActivity'ye yönlendir
                        startMenuActivity();
                    } else {
                        // Giriş başarısız
                        Toast.makeText(LoginActivity.this,
                                "Giriş başarısız: " + task.getException().getMessage(),
                                Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void saveUserLoginState() {
        SharedPreferences preferences = getSharedPreferences("login", MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        FirebaseUser user = mAuth.getCurrentUser();
        if (user != null) {
            editor.putString("userEmail", user.getEmail());
            editor.putString("userId", user.getUid());
            editor.putBoolean("isLoggedIn", true);
            editor.apply();
        }
    }

    private void startMenuActivity() {
        Intent intent = new Intent(LoginActivity.this, MenuActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();
    }

    private void startRegisterActivity() {
        Intent intent = new Intent(LoginActivity.this, Register.class);
        startActivity(intent);
    }
}
