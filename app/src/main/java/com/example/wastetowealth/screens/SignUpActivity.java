package com.example.wastetowealth.screens;

import static android.widget.Toast.LENGTH_SHORT;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.wastetowealth.R;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class SignUpActivity extends AppCompatActivity {
    // declear all variable
    EditText email,name,mob, dateofbirth,pass,cpass;
    RadioGroup usertype;
    RadioButton radioButton;
    Button btn_google,btn_facebook,btn_signup;
    TextView olduser;
    FirebaseFirestore db;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_sign_up);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        db = FirebaseFirestore.getInstance();
        FirebaseApp.initializeApp(this);

        DatabaseReference myRef = FirebaseDatabase.getInstance().getReference("users");
        String userId = myRef.push().getKey();




        //innitialize all variable
        email = findViewById(R.id.et_email);
        name = findViewById(R.id.et_name);
        mob =  findViewById(R.id.et_mob);
        dateofbirth= findViewById(R.id.et_dob);
        pass = findViewById(R.id.et_pass);
        cpass = findViewById(R.id.et_cpass);
        btn_google = findViewById(R.id.btn_google);
        btn_facebook = findViewById(R.id.btn_facebook);
        olduser = findViewById(R.id.tv_newuser);
        btn_signup = findViewById(R.id.btn_login);
        usertype = findViewById(R.id.rg_usertype);

        /// ///////////pop up calender

        dateofbirth.setOnClickListener(v -> {
            final Calendar calendar = Calendar.getInstance();
            int year = calendar.get(Calendar.YEAR);
            int month = calendar.get(Calendar.MONTH);
            int day = calendar.get(Calendar.DAY_OF_MONTH);

            DatePickerDialog datePickerDialog = new DatePickerDialog(SignUpActivity.this,
                    (view, selectedYear, selectedMonth, selectedDay) -> {
                        // Months are indexed from 0, so add +1
                        String dob = selectedDay + "/" + (selectedMonth + 1) + "/" + selectedYear;
                        dateofbirth.setText(dob);
                    },
                    year, month, day);

            datePickerDialog.show();
        });

        /// ///////"listen" the btn_signup
        btn_signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String v_email,v_name,v_mob,v_dob,v_pass,v_cpass,v_utype;
                v_email = email.getText().toString().trim();
                v_name = name.getText().toString().trim();
                v_mob = mob.getText().toString().trim();
                v_dob = dateofbirth.getText().toString().trim();
                v_pass = pass.getText().toString().trim();
                v_cpass = cpass.getText().toString().trim();

                //get id of selected radio button
                int id = usertype.getCheckedRadioButtonId();
                if (id == -1){
                    Toast.makeText(getApplicationContext(),"Select user type",LENGTH_SHORT).show();
                    return;}

                    if (!v_pass.equals(v_cpass) || v_cpass.isEmpty() || v_pass.isEmpty()){
                        pass.setBackgroundResource(R.drawable.error_et_bg);
                        cpass.setBackgroundResource(R.drawable.error_et_bg);
                    Toast.makeText(getApplicationContext(),"Password do not match",LENGTH_SHORT).show();
                    return;
                }
                    //target selected radio buttion using id
                 radioButton = findViewById(id);
                 v_utype = radioButton.getText().toString().trim();

                //put all data in userData map
                HashMap<String, String> userData = new HashMap<>();
                userData.put("email", v_email);
                userData.put("name", v_name);

                userData.put("mob", v_mob);
                userData.put("dob", v_dob);
                userData.put("pass", v_pass);
                userData.put("utype", v_utype);

                //add userData to firebase
                String emailKey = v_email.replace(".", "_"); //making email as primary key (but firebase dose not support . so store _ insted
                //get refrence of database
                DatabaseReference userRef = FirebaseDatabase.getInstance().getReference("users").child(emailKey);

                // Check if user already exists
                userRef.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if (snapshot.exists()) {
                            Toast.makeText(getApplicationContext(), "User already registered", Toast.LENGTH_SHORT).show();
                        } else {
                            userRef.setValue(userData);
                            Toast.makeText(getApplicationContext(), "User registered", Toast.LENGTH_SHORT).show();
                            //store session
                            SharedPreferences sharedPref = getSharedPreferences("UserSession", MODE_PRIVATE);
                            SharedPreferences.Editor editor = sharedPref.edit();
                            editor.putString("email", "thsurajsinghrajput@gmail.com");
                            editor.putString("name", "Suraj Rajput");
                            editor.putString("utype", "NGO");
                            editor.putBoolean("isLoggedIn", true);
                            editor.apply();
                            Intent i = new Intent(getApplicationContext(), MainActivity.class);
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        Log.e("Firebase", "Error: " + error.getMessage());
                    }
                });







            }
        });
        btn_google.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "coming soon", Toast.LENGTH_SHORT).show();
            }
        });
        btn_facebook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "coming soon", Toast.LENGTH_SHORT).show();
            }
        });

        olduser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(SignUpActivity.this, LoginActivity.class);
                startActivity(i);
                finish();
            }
        });

    }
}