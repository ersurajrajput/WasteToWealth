package com.example.wastetowealth.screens;
import static android.widget.Toast.LENGTH_SHORT;

import com.example.wastetowealth.R;


import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowInsets;
import android.view.WindowInsetsController;
import android.view.WindowManager;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.graphics.Insets;
import androidx.core.view.GravityCompat;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.example.wastetowealth.R;
import com.google.android.material.bottomnavigation.BottomNavigationMenuView;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.android.material.navigation.NavigationView;

public class MainActivity extends AppCompatActivity {
    BottomNavigationView bottomNavigationView;
    NavigationView navigationView;
    DrawerLayout drawerLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

         // session chake
        SharedPreferences sharedPref = getSharedPreferences("UserSession", MODE_PRIVATE);
        boolean isLoggedIn = sharedPref.getBoolean("isLoggedIn", false);

        if (!isLoggedIn) {
            Intent i = new Intent(getApplicationContext(), LoginActivity.class);
            startActivity(i);
            finish();
            // redirect to home/dashboard
        }
        ///  hide status bar


        bottomNavigationView = findViewById(R.id.bottomNavigationView);
        drawerLayout = findViewById(R.id.drawerLayout);
        navigationView = findViewById(R.id.nav_view);
        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {


                if (menuItem.getItemId() == R.id.home){
                    Toast.makeText(getApplicationContext(),"Home",LENGTH_SHORT).show();
                    return true;
                }else if (menuItem.getItemId() == R.id.cart){
                    Toast.makeText(getApplicationContext(),"Cart",LENGTH_SHORT).show();
                    return true;
                }if (menuItem.getItemId() == R.id.search){
                    Toast.makeText(getApplicationContext(),"Search",LENGTH_SHORT).show();
                    return true;
                }if (menuItem.getItemId() == R.id.history){
                    Toast.makeText(getApplicationContext(),"History",LENGTH_SHORT).show();
                    return true;
                }if (menuItem.getItemId() == R.id.profile){
                    drawerLayout.openDrawer(GravityCompat.END);
                    Toast.makeText(getApplicationContext(),"Profile",LENGTH_SHORT).show();
                    return true;
                }
                return false;
            }
        });
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                if (menuItem.getItemId() == R.id.nav_login){
                    Intent i = new Intent(MainActivity.this,LoginActivity.class);
                    startActivity(i);

                }else if (menuItem.getItemId() == R.id.nav_aboutus){
                    Intent i = new Intent(MainActivity.this,AboutUsActivity.class);
                    startActivity(i);
                }else if (menuItem.getItemId() == R.id.nav_contactus){
                    Toast.makeText(MainActivity.this, "Login clicked", Toast.LENGTH_SHORT).show();
                }


                return false;
            }
        });


    }
}