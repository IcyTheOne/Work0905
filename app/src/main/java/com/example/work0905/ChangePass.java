package com.example.work0905;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import com.example.work0905.util.DatabaseHandler;

public class ChangePass extends AppCompatActivity {
    DatabaseHandler db = new DatabaseHandler();

    ImageButton backBtn;
    ImageButton savePasswordBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_pass);
        savePasswordBtn = findViewById(R.id.change_email);
        savePasswordBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Prepared statement update
                db.openDbConnection(4);


            }
        });


        backBtn = findViewById(R.id.back_btn);
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (getSupportFragmentManager().getBackStackEntryCount() > 0) {
                    getSupportFragmentManager().popBackStackImmediate();
                } else {
                    finish();
                }
            }
        });
    }
}
