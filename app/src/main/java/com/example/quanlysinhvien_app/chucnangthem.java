package com.example.quanlysinhvien_app;


import android.os.Bundle;
import android.widget.Switch;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

public class chucnangthem extends AppCompatActivity {
    private DatabaseHelper mDatabaseHelper;
    private int newMode;;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.chucnangthem);
        int currentMode = AppCompatDelegate.getDefaultNightMode();

        Switch switchButton = findViewById(R.id.giaodien);

        switchButton.setOnCheckedChangeListener((buttonView, isChecked) -> {

            if (isChecked) {
                newMode = AppCompatDelegate.MODE_NIGHT_YES;
            } else {
                newMode = AppCompatDelegate.MODE_NIGHT_NO;
            }

            if (currentMode != newMode) {
                AppCompatDelegate.setDefaultNightMode(newMode);
//                recreate();
            }

        });


    }
}
