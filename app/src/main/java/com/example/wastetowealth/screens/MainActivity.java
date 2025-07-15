package com.example.wastetowealth.screens;
import static android.widget.Toast.LENGTH_SHORT;

import com.example.wastetowealth.R;


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

public class MainActivity extends AppCompatActivity {
    BottomNavigationView bottomNavigationView;
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
        ///  hide status bar


        bottomNavigationView = findViewById(R.id.bottomNavigationView);
        drawerLayout = findViewById(R.id.drawerLayout);
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

    }
}