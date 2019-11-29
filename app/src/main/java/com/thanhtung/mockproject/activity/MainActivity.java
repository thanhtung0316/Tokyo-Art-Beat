package com.thanhtung.mockproject.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.thanhtung.mockproject.R;


public class MainActivity extends AppCompatActivity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        BottomNavigationView navView = findViewById(R.id.nav_view);
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        Intent intent = getIntent();


        if (intent.getBooleanExtra("ACTION", false)) {
            navController.navigate(R.id.navigation_mypage);
            NavigationUI.setupWithNavController(navView, navController);
        } else {
            NavigationUI.setupWithNavController(navView, navController);
        }
//        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
//                R.id.navigation_home, R.id.navigation_near, R.id.navigation_browse,R.id.navigation_mypage)
//                .build();


//        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
    }

}
