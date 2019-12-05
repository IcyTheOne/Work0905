package com.example.work0905;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.Image;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;

import com.example.work0905.model.Employee;

public class NavigationBar extends AppCompatActivity {

    private static final String TAG = "NavigationBar";

    public SharedPreferences sharedPreferences;
    public Employee employee;

    // User info that can be accessed by the fragments attached on this activity
    public static final String PREF_NAME = "prefs";
    public static final String KEY_USERNAME = "username";
    public static final String KEY_PASSWORD = "password";
    public static String username;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigation_bar);

        employee = (Employee) getIntent().getSerializableExtra("Employee");
        Log.i(TAG, "User employee: " + employee.getEmail());

        sharedPreferences = getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        username = sharedPreferences.getString(KEY_USERNAME,"");

        FragmentManager fm = getSupportFragmentManager();
        final Fragment fragment = fm.findFragmentById(R.id.fragment_container);
        fm.beginTransaction().add(R.id.fragment_container, new HomeFragment()).addToBackStack(null).commit();

        ImageButton homeBtn = findViewById(R.id.homeBtn);
        ImageButton statsBtn = findViewById(R.id.statsBtn);
        ImageButton settingsBtn = findViewById(R.id.settingsBtn);

        homeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                ft.replace(R.id.fragment_container, new HomeFragment());
                ft.addToBackStack(null).commit();
            }
        });

        statsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                ft.replace(R.id.fragment_container, new StatsFragment());
                ft.addToBackStack(null).commit();
            }
        });

        settingsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                ft.replace(R.id.fragment_container, new SettingsFragment());
                ft.addToBackStack(null).commit();
            }
        });

    }
}
