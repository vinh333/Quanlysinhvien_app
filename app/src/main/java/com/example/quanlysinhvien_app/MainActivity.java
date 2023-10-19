package com.example.quanlysinhvien_app;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Switch;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

public class MainActivity extends AppCompatActivity {
    private DatabaseHelper mDatabaseHelper;
    private int newMode;;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//
//        PinManager pinManager = new PinManager(this);
//        String savedPin = pinManager.getPin();
//
//        if (savedPin == null) {
            setContentView(R.layout.activity_main);

            LinearLayout linearLayoutTrangChu = findViewById(R.id.LLnhapdiem);
            LinearLayout linearLayoutDiemdanh = findViewById(R.id.LLdiemdanh);
            LinearLayout linearLayoutCaidat = findViewById(R.id.LLcaidat);
            Button btnSV = findViewById(R.id.btnsv);
            Button btnKhoa = findViewById(R.id.btnkhoa);


            linearLayoutTrangChu.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // Chuyển sang màn hình Trang Chủ
                    Intent intent = new Intent(MainActivity.this, nhapdiem.class);
                    startActivity(intent);
                }
            });
            linearLayoutDiemdanh.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // Chuyển sang màn hình Trang Chủ
                    Intent intent = new Intent(MainActivity.this, diemdanh.class);
                    startActivity(intent);
                }
            });

            linearLayoutCaidat.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // Chuyển sang màn hình Trang Chủ
                    Intent intent = new Intent(MainActivity.this, chucnangthem.class);
                    startActivity(intent);
                }
            });
            btnSV.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // Chuyển sang màn hình Trang Chủ
                    Intent intent = new Intent(MainActivity.this, sinhvien.class);
                    startActivity(intent);
                }
            });

            btnKhoa.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // Chuyển sang màn hình Trang Chủ
                    Intent intent = new Intent(MainActivity.this, dskhoa.class);
                    startActivity(intent);
                }
            });

//        } else {
//            // Mã PIN đã cài đặt, chuyển đến trang nhập mã PIN
//            Intent intent = new Intent(this, PinEntryActivity.class);
//            startActivity(intent);
//
//        }
    }

}
