package com.example.quanlysinhvien_app;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.drawable.RoundedBitmapDrawable;
import androidx.core.graphics.drawable.RoundedBitmapDrawableFactory;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.example.quanlysinhvien_app.Select.Select_Sinhvien;
import com.example.quanlysinhvien_app.Tinhnang.ThemMonHoc;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity extends AppCompatActivity {
    private DatabaseHelper mDatabaseHelper;
    private Button button_SinhVien,button_Test;
    private LinearLayout btnCaiDat;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Khởi tạo DatabaseHelper khi mở MainActivity
        mDatabaseHelper = new DatabaseHelper(this);

        // Tìm  theo id
        btnCaiDat = findViewById(R.id.btn_caidat);
        button_SinhVien = findViewById(R.id.button_SinhVien);
        button_Test = findViewById(R.id.button_Test);

        // Mở cơ sở dữ liệu để đọc hoặc ghi
        SQLiteDatabase db = mDatabaseHelper.getWritableDatabase();

        // Kiểm tra xem cơ sở dữ liệu có dữ liệu hay không
        if (isDatabaseEmpty()) {
            // Nếu cơ sở dữ liệu trống, thêm dữ liệu mẫu
            mDatabaseHelper.insertSampleData();
        }

        // Đóng cơ sở dữ liệu sau khi sử dụng
        db.close();
        // Đặt OnClickListener cho LinearLayout
        button_Test.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Chuyển sang CaidatActivity khi nhấn vào btn_caidat
                Intent intent = new Intent(MainActivity.this, ThemMonHoc.class);
                startActivity(intent);
            }
        });
        // Đặt OnClickListener cho LinearLayout
        btnCaiDat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Chuyển sang CaidatActivity khi nhấn vào btn_caidat
                Intent intent = new Intent(MainActivity.this, CaidatActivity.class);
                startActivity(intent);
            }
        });

        button_SinhVien.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Chuyển sang CaidatActivity khi nhấn vào btn_caidat
                Intent intent = new Intent(MainActivity.this, Select_Sinhvien.class);
                startActivity(intent);
            }
        });
    }

    // Phương thức kiểm tra xem cơ sở dữ liệu có dữ liệu hay không
    private boolean isDatabaseEmpty() {
        SQLiteDatabase db = mDatabaseHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM SINHVIEN", null);
        boolean isEmpty = cursor.getCount() == 0;
        cursor.close();
        db.close();
        return isEmpty;
    }
}
