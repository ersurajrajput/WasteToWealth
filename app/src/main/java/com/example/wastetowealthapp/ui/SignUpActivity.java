package com.example.wastetowealthapp.ui;

import static android.widget.Toast.LENGTH_SHORT;

import android.os.Bundle;
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

import com.example.wastetowealthapp.Models.UserModel;
import com.example.wastetowealthapp.R;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SignUpActivity extends AppCompatActivity {
    EditText et_email,et_name,et_mob,et_pass,et_cpass;
    RadioGroup radioGroup;
    RadioButton rd_regular,rd_ngo,rd_collector;
    String userType="";
    Button btn_signup;
    TextView tv_olduser;

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
        FirebaseDatabase db = FirebaseDatabase.getInstance();
        FirebaseApp.initializeApp(getApplicationContext());
        DatabaseReference myref = db.getReference("users");

        et_mob = findViewById(R.id.et_mob);
        et_name = findViewById(R.id.et_name);
        et_pass = findViewById(R.id.et_pass);
        et_cpass = findViewById(R.id.et_cpass);
        et_email = findViewById(R.id.et_email);
        rd_collector = findViewById(R.id.rb_collector);
        rd_ngo = findViewById(R.id.rb_ngo);
        rd_regular = findViewById(R.id.rb_regualrUser);
        btn_signup = findViewById(R.id.btn_signup);
        tv_olduser = findViewById(R.id.tv_olduser);
        radioGroup = findViewById(R.id.rg_usertype);

        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(@NonNull RadioGroup group, int checkedId) {
                if (checkedId == R.id.rb_regualrUser){
                    userType = "regular";
                }
                else if (checkedId == R.id.rb_ngo){
                    userType = "ngo";

                }else if (checkedId == R.id.rb_collector){
                    userType = "collector";
                }
                else {
                    userType = "";
                }
            }
        });

            btn_signup.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                   String email = et_email.getText().toString().trim();
                   String name = et_name.getText().toString().trim();
                   String mob = et_mob.getText().toString().trim();
                   String pass = et_pass.getText().toString().trim();
                   String cpass = et_cpass.getText().toString().trim();


                   if (email.isEmpty() || name.isEmpty() || mob.isEmpty() || pass.isEmpty() || cpass.isEmpty() || userType.isEmpty()){
                       Toast.makeText(getApplicationContext(),"All Fields are Require",LENGTH_SHORT).show();
                       return;
                   }
                   if (!cpass.equals(pass)){
                       Toast.makeText(getApplicationContext(),"Passwords Do Not Match",LENGTH_SHORT).show();
                        return;
                   }
                    UserModel userModel = new UserModel(email,name,mob,pass,userType);
                    String emailKey = email.replace(".","_");
                    myref.child(emailKey).setValue(userModel)
                            .addOnSuccessListener(unused -> {
                                Toast.makeText(SignUpActivity.this, "Signup successful", Toast.LENGTH_SHORT).show();
                                // You can also redirect user here
                            })
                            .addOnFailureListener(e -> {
                                Toast.makeText(SignUpActivity.this, "Signup failed: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                            });



                }
            });


    }
}