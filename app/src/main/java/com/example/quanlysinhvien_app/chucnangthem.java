package com.example.quanlysinhvien_app;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import com.example.quanlysinhvien_app.User.LoginActivity;

public class chucnangthem extends AppCompatActivity {
    private DatabaseHelper mDatabaseHelper;

    private Switch darkModeSwitch;
    private TextView txtThongTin;
    private TextView txtDoiMK;
    private TextView txtNgonNgu;
    private LinearLayout txtMaPin;

    private TextView textView6;
    private TextView txtDangXuat;
    private TextView txtAppVersion;
    private PinManager pinManager;
    private int newMode;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.caidat1);

        darkModeSwitch = findViewById(R.id.switchCheDoToi);
        txtThongTin = findViewById(R.id.txt_thongtin);
        txtDoiMK = findViewById(R.id.txt_doimk);
        txtNgonNgu = findViewById(R.id.txt_ngonngu);
        txtMaPin = findViewById(R.id.txt_mapin);
        txtDangXuat = findViewById(R.id.txt_dangxuat);

        int currentMode = AppCompatDelegate.getDefaultNightMode();


        // Khởi tạo PinManager
        pinManager = new PinManager(this);

        darkModeSwitch.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                newMode = AppCompatDelegate.MODE_NIGHT_YES;
            } else {
                newMode = AppCompatDelegate.MODE_NIGHT_NO;
            }

            if (currentMode != newMode) {
                AppCompatDelegate.setDefaultNightMode(newMode);
            }
        });

        txtMaPin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Chuyển sang màn hình cài đặt mã PIN
                Intent intent = new Intent(chucnangthem.this, mapin.class);
                startActivity(intent);
            }
        });

        txtDangXuat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Đánh dấu người dùng đã đăng xuất
                setLoggedInStatus(false);

                // Chuyển đến màn hình đăng nhập
                Intent intent = new Intent(chucnangthem.this, LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    // Phương thức để thiết lập trạng thái đăng nhập trong SharedPreferences
    private void setLoggedInStatus(boolean isLoggedIn) {
        SharedPreferences preferences = getSharedPreferences("my_prefs", MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putBoolean("is_logged_in", isLoggedIn);
        editor.apply();
    }
}
