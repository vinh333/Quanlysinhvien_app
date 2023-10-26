package com.example.quanlysinhvien_app;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Switch;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import com.example.quanlysinhvien_app.DatabaseHelper;
import com.example.quanlysinhvien_app.PinManager;
import com.example.quanlysinhvien_app.R;
import com.example.quanlysinhvien_app.User.LoginActivity;
import com.google.firebase.auth.FirebaseAuth; // Import thư viện Firebase Auth

public class chucnangthem extends AppCompatActivity {
    private DatabaseHelper mDatabaseHelper;
    private int newMode;
    private PinManager pinManager;
    private Button logout;
    private FirebaseAuth mAuth; // Khai báo đối tượng FirebaseAuth

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.chucnangthem);

        mAuth = FirebaseAuth.getInstance(); // Khởi tạo đối tượng FirebaseAuth

        int currentMode = AppCompatDelegate.getDefaultNightMode();
        TextView mapin = findViewById(R.id.mapin);
        Switch switchButton = findViewById(R.id.giaodien);
        logout = findViewById(R.id.dangxuat);

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

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Đăng xuất người dùng
                signOut();
            }
        });
    }

    // Hàm đăng xuất người dùng
    private void signOut() {
        mAuth.signOut(); // Đăng xuất người dùng khỏi Firebase Authentication

        // Đánh dấu người dùng đã đăng xuất
        setLoggedInStatus(false);

        // Chuyển đến màn hình đăng nhập
        Intent intent = new Intent(chucnangthem.this, LoginActivity.class);
        startActivity(intent);
    }

    // Phương thức để thiết lập trạng thái đăng nhập trong SharedPreferences
    private void setLoggedInStatus(boolean isLoggedIn) {
        SharedPreferences preferences = getSharedPreferences("my_prefs", MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();

        // Xóa thông tin đăng nhập (email và mật khẩu)
        editor.putString("email", "");  // Đặt email thành chuỗi rỗng
        editor.putString("password", ""); // Đặt mật khẩu thành chuỗi rỗng

        editor.putBoolean("is_logged_in", isLoggedIn);
        editor.apply();
    }
}
