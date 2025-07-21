package com.example.wastetowealthapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.wastetowealthapp.ui.LoginActivity;
import com.example.wastetowealthapp.ui.MainActivity;

public class StartScreenActivity extends AppCompatActivity {
//    FirebaseDatabase db;
    Button btn_login,btn_skip;
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
//        db = FirebaseDatabase.getInstance();
//        FirebaseApp.initializeApp(getApplicationContext());

        btn_login = findViewById(R.id.btn_signup);
        btn_skip = findViewById(R.id.btn_skip);

//
//        HashMap<String,String> hm = new HashMap<>();
//        hm.put("name","suraj");
//        hm.put("email","thsurajsinghrajput@gmail.com");
//        hm.put("mob","7668659783");
//        hm.put("pass","admin");
//
//        DatabaseReference myref = db.getReference("users");
//        myref.setValue(hm);












        btn_skip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(i);
            }
        });
        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(i);
            }
        });

    }
}