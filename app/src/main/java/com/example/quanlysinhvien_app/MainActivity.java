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
        setContentView(R.layout.activity_main);

//        // Khởi tạo DatabaseHelper khi mở MainActivity
//        mDatabaseHelper = new DatabaseHelper(this);
//
//        // Mở cơ sở dữ liệu để đọc hoặc ghi
//        SQLiteDatabase db = mDatabaseHelper.getWritableDatabase();3
//        // Hoặc sử dụng để đọc dữ liệu: SQLiteDatabase db = mDatabaseHelper.getReadableDatabase();
//
//        // Thực hiện các thao tác với cơ sở dữ liệu ở đây
//        //...
//
//        // Đừng quên đóng cơ sở dữ liệu khi bạn đã sử dụng xong
//
//
//        // Tìm LinearLayout theo id
//        LinearLayout btnCaiDat = findViewById(R.id.btn_caidat);
//
//        // Đặt OnClickListener cho LinearLayout
//        btnCaiDat.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                // Chuyển sang CaidatActivity khi nhấn vào btn_caidat
//                Intent intent = new Intent(MainActivity.this, CaidatActivity.class);
//                startActivity(intent);
//            }
//        });

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
    }
}
