package com.example.wastetowealth.screens;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.wastetowealth.R;

import java.util.Calendar;

public class SignUpActivity extends AppCompatActivity {
    EditText email,name,mob, dateofbirth,pass,cpass;
    RadioGroup usertype;
    RadioButton radioButton;
    Button btn_google,btn_facebook,btn_signup;
    TextView olduser;

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

        /// ///////"listen" the login btn
        btn_signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String res =  email.getText().toString().trim()+":"+name.getText().toString().trim()+":"+mob.getText().toString().trim()+":"+dateofbirth.getText().toString().trim()+":"+pass.getText().toString().trim()+":"+cpass.getText().toString().trim()+":";
                /// get id of selected radio button from radio group
                int selectedid =  usertype.getCheckedRadioButtonId();

                ///target selected radio button using that id
                radioButton = findViewById(selectedid);

                /// get text from that selected radio button
                String utype = radioButton.getText().toString().trim();
                res = res+":"+utype;
                Toast.makeText(getApplicationContext(), res, Toast.LENGTH_SHORT).show();

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