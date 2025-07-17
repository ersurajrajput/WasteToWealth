package com.example.wastetowealth.screens;

import static android.widget.Toast.LENGTH_SHORT;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import com.example.wastetowealth.BuildConfig;

import com.example.wastetowealth.R;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Properties;
import java.util.Random;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class ForgetPasswordActivity extends AppCompatActivity {
    Button btn_sendopt,btn_verifyotp;
    EditText email,otp;
    FirebaseFirestore db;
    String fotp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_forget_password);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        btn_sendopt = findViewById(R.id.btn_sendotp);
        btn_verifyotp = findViewById(R.id.btn_otpverify);
        email = findViewById(R.id.et_email);
        otp = findViewById(R.id.et_otp);
        db = FirebaseFirestore.getInstance();
        FirebaseApp.initializeApp(getApplicationContext());
        String myEmail = BuildConfig.EMAIL_USER;
        String myEmailPass = BuildConfig.EMAIL_PASS;

        btn_sendopt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (email.getText().toString().trim().isEmpty()){
                    email.setError("required");
                    return;
                }
                // chacke if ser exists
                String uemail = email.getText().toString().trim();
                String emailKey = email.getText().toString().trim().replace(".", "_"); //making email as primary key (but firebase dose not support . so store _ insted
                //get refrence of database
                DatabaseReference userRef = FirebaseDatabase.getInstance().getReference("users").child(emailKey);
                userRef.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if (snapshot.exists()){
                            Random random = new Random();
                            int eotp = 100000 + random.nextInt(900000);
                            fotp =String.valueOf(eotp);
                            new Thread(() -> {
                                try {
                                    final String fromEmail = myEmail;
                                    final String password = myEmailPass;
                                    final String toEmail = snapshot.child("email").getValue(String.class);


                                    Properties props = new Properties();
                                    props.put("mail.smtp.auth", "true");
                                    props.put("mail.smtp.starttls.enable", "true");
                                    props.put("mail.smtp.host", "smtp.gmail.com");
                                    props.put("mail.smtp.port", "587");

                                    Session session = Session.getInstance(props, new Authenticator() {
                                        protected PasswordAuthentication getPasswordAuthentication() {
                                            return new PasswordAuthentication(fromEmail, password);
                                        }
                                    });

                                    Message message = new MimeMessage(session);
                                    message.setFrom(new InternetAddress(fromEmail));
                                    message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(toEmail));
                                    message.setSubject("Your OTP Code");
                                    message.setText("Your OTP is: " + fotp);

                                    Transport.send(message);

                                    runOnUiThread(() -> Toast.makeText(getApplicationContext(), "OTP sent to email", Toast.LENGTH_SHORT).show());

                                } catch (Exception e) {
                                    e.printStackTrace();
                                    runOnUiThread(() -> Toast.makeText(getApplicationContext(), "Failed to send email", Toast.LENGTH_SHORT).show());
                                }
                            }).start();
                            email.setVisibility(View.GONE);
                            btn_sendopt.setVisibility(View.GONE);

                            // Show OTP field with fade-in
                            otp.setAlpha(0f);
                            otp.setVisibility(View.VISIBLE);
                            otp.animate().alpha(1f).setDuration(1000).start();

                            // Show verify button with fade-in
                            btn_verifyotp.setAlpha(0f);
                            btn_verifyotp.setVisibility(View.VISIBLE);
                            btn_verifyotp.animate().alpha(1f).setDuration(1000).start();







                        }else{
                            Toast.makeText(getApplicationContext(),"Wrong Email",LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

            }
        });
        
        btn_verifyotp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (otp.getText().toString().isEmpty()){
                    otp.setError("required");
                }
                if (otp.getText().toString().trim().equals(fotp)){
                    Toast.makeText(getApplicationContext(),"OTP verified",LENGTH_SHORT).show();
                }else{
                    Toast.makeText(getApplicationContext(),"Wrong OTP",LENGTH_SHORT).show();

                }
            }
        });

    }
}