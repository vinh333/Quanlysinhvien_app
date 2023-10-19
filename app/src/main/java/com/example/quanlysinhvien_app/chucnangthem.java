package com.example.quanlysinhvien_app;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

public class chucnangthem extends AppCompatActivity {
    private DatabaseHelper mDatabaseHelper;
    private int newMode;
    private PinManager pinManager;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.chucnangthem);

        int currentMode = AppCompatDelegate.getDefaultNightMode();
        TextView mapin = findViewById(R.id.mapin);
        Switch switchButton = findViewById(R.id.giaodien);

        // Khởi tạo PinManager
        pinManager = new PinManager(this);

        switchButton.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                newMode = AppCompatDelegate.MODE_NIGHT_YES;
            } else {
                newMode = AppCompatDelegate.MODE_NIGHT_NO;
            }

            if (currentMode != newMode) {
                AppCompatDelegate.setDefaultNightMode(newMode);
            }
        });

        mapin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Chuyển sang màn hình cài đặt mã PIN
                Intent intent = new Intent(chucnangthem.this, mapin.class);
                startActivity(intent);
            }
        });
    }
}
