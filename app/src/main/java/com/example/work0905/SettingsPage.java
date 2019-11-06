package com.example.work0905;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;

public class SettingsPage extends AppCompatActivity {

    private ImageButton statsBtn;
    private ImageButton homeBtn;
    private ImageButton changePass;
    private ImageButton changeMail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings_page);

        statsBtn = findViewById(R.id.statsBtn);
        statsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SettingsPage.this, StatsPage.class);
                startActivity(intent);
            }
        });

        homeBtn = findViewById(R.id.homeBtn);
        homeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SettingsPage.this, HomePage.class);
                startActivity(intent);
            }
        });

        changeMail = findViewById(R.id.change_email);
        changeMail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SettingsPage.this, ChangeEmail.class);
                startActivity(intent);
            }
        });

        changePass = findViewById(R.id.change_password);
        changePass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SettingsPage.this, ChangePass.class);
                startActivity(intent);
            }
        });
    }
}
