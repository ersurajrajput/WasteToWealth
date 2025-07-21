package com.example.wastetowealthapp.ui;

import static android.widget.Toast.LENGTH_SHORT;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.wastetowealthapp.R;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class LoginActivity extends AppCompatActivity {
    Button btn_login,btn_google,btn_facebook;
    SharedPreferences sharedPreferences;
    FirebaseDatabase db;
    EditText et_email,et_pass;
    TextView tv_forgetPass,tv_newUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        btn_facebook = findViewById(R.id.btn_facebook);
        btn_google = findViewById(R.id.btn_google);
        btn_login = findViewById(R.id.btn_signup);

        et_email = findViewById(R.id.et_email);
        et_pass = findViewById(R.id.et_pass);

        tv_forgetPass = findViewById(R.id.tv_forgetpass);
        tv_newUser = findViewById(R.id.tv_olduser);

        db = FirebaseDatabase.getInstance();
        FirebaseApp.initializeApp(getApplicationContext());



        tv_newUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(getApplicationContext(),SignUpActivity.class);
                startActivity(i);
            }
        });

        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = et_email.getText().toString().trim();
                String pass = et_pass.getText().toString().trim();

                if (pass.isEmpty()){
                    et_pass.setError("required");
                    return;
                }
                if (email.isEmpty()){
                    et_email.setError("required");
                    return;
                }

                String emailKey = email.replace(".","_");
                DatabaseReference myref = db.getReference("users").child(emailKey);
                myref.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if (snapshot.exists()) {
                            String storedEmail = snapshot.child("email").getValue(String.class);
                            String storedPass = snapshot.child("pass").getValue(String.class);

                            if (storedEmail != null && storedPass != null &&
                                    storedEmail.equals(email) && storedPass.equals(pass)) {

                                // Save login state in shared preferences
                                SharedPreferences sharedPreferences = getSharedPreferences("MyAppPrefs", MODE_PRIVATE);
                                SharedPreferences.Editor editor = sharedPreferences.edit();
                                editor.putBoolean("IsLoggedIn", true);
                                editor.putString("name", snapshot.child("name").getValue(String.class));
                                editor.putString("email", email);
                                editor.apply();

                                // Go to MainActivity
                                Intent i = new Intent(getApplicationContext(), MainActivity.class);
                                startActivity(i);
                                finish(); // optional: to close login screen

                            } else {
                                Toast.makeText(getApplicationContext(), "Wrong Credentials", LENGTH_SHORT).show();
                            }
                        } else {
                            Toast.makeText(getApplicationContext(), "User not found", LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        Toast.makeText(getApplicationContext(),error.getMessage(),LENGTH_SHORT).show();
                    }
                });

            }
        });

    }
}