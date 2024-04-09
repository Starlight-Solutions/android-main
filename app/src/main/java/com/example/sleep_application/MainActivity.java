package com.example.sleep_application;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast
import android.view.View;
import android.widget.TextView;

import com.example.sleep_application.ui.music_player.BackgroundMusicService;
import com.google.android.material.navigation.NavigationView;

import androidx.appcompat.app.AppCompatDelegate;
import androidx.annotation.NonNull;
import androidx.core.view.GravityCompat;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;

import com.example.sleep_application.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity implements DrawerLayout.DrawerListener {

    private AppBarConfiguration mAppBarConfiguration;
    private ActivityMainBinding binding;
    boolean isNightModeOn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.appBarMain.toolbar);
        DrawerLayout drawer = binding.drawerLayout;
        NavigationView navigationView = binding.navView;
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_sleep_tracking, R.id.nav_slideshow, R.id.nav_login)
                .setOpenableLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);

        drawer.addDrawerListener(this);

        Intent serviceIntent = new Intent(this, BackgroundMusicService.class);
        startService(serviceIntent);

    }

    @Override   // for interface
    public void onDrawerSlide(@NonNull View drawerView, float slideOffset) {    }

    @Override
    public void onDrawerOpened(@NonNull View drawerView) {
        // set current user
        SharedPreferences sharedPref = this.getPreferences(Context.MODE_PRIVATE);
        View headerView = binding.navView.getHeaderView(0);
        TextView headerUsername = (TextView) headerView.findViewById(R.id.menu_username);
        TextView headerEmail = (TextView) headerView.findViewById(R.id.menu_email);
        headerUsername.setText(sharedPref.getString("login_username", "Not logged in"));
        headerEmail.setText(sharedPref.getString("login_email", "No email"));
    }

    @Override // for interface
    public void onDrawerClosed(@NonNull View drawerView) {    }

    @Override // for interface
    public void onDrawerStateChanged(int newState) {    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){

        switch(item.getItemId()){

            case R.id.action_switch_theme:
                Toast.makeText(getApplicationContext(), "Change Theme", Toast.LENGTH_SHORT).show();
                switchTheme();
                break;
            case R.id.action_settings:
                Toast.makeText(getApplicationContext(), "Settings Selected", Toast.LENGTH_SHORT).show();
                break;

        }

        return super.onOptionsItemSelected(item);
    }

    public void switchTheme() {
        if (AppCompatDelegate.getDefaultNightMode() == AppCompatDelegate.MODE_NIGHT_NO) {
            isNightModeOn = false;
        } else if (AppCompatDelegate.getDefaultNightMode() == AppCompatDelegate.MODE_NIGHT_YES) {
            isNightModeOn = true;
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        }

        if (isNightModeOn) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
            isNightModeOn = false;

        } else {
            AppCompatDelegate.setDefaultNightMode((AppCompatDelegate.MODE_NIGHT_YES));
            isNightModeOn = true;
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }
}