package com.example.wastetowealth;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.wastetowealth.screens.LoginActivity;
import com.example.wastetowealth.screens.MainActivity;

public class StartScreen extends AppCompatActivity {
    Button btn_login,btn_guest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_start_screen);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        //session chake
        SharedPreferences sharedPref = getSharedPreferences("UserSession", MODE_PRIVATE);
        boolean isLoggedIn = sharedPref.getBoolean("isLoggedIn", false);

        if (isLoggedIn) {
            Intent i = new Intent(StartScreen.this, MainActivity.class);
            startActivity(i);
            finish();
            // redirect to home/dashboard
        }

        btn_login = findViewById(R.id.btn_login);
        btn_guest = findViewById(R.id.btn_guest);

        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(StartScreen.this, LoginActivity.class);
                startActivity(i);
                finish();
            }
        });

        btn_guest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(StartScreen.this, MainActivity.class);
                startActivity(i);
                finish();
            }
        });
    }
}