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

import com.example.quanlysinhvien_app.Select.Select_BangDiemThi;
import com.example.quanlysinhvien_app.Select.Select_Khoa;
import com.example.quanlysinhvien_app.Select.Select_Lop;
import com.example.quanlysinhvien_app.Select.Select_MonHoc;
import com.example.quanlysinhvien_app.Select.Select_Sinhvien;
import com.example.quanlysinhvien_app.Tinhnang.ThemDiemThiActivity;
import com.example.quanlysinhvien_app.Tinhnang.ThemLopActivity;
import com.example.quanlysinhvien_app.Tinhnang.ThemMonHoc;
import com.example.quanlysinhvien_app.Tinhnang.Themsinhvien;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity extends AppCompatActivity {
    private DatabaseHelper mDatabaseHelper;
    private LinearLayout btnCaiDat;
    private LinearLayout infoButtonsContainer;
    private LinearLayout addStudentContainer;
    private LinearLayout addCourseContainer;
    private LinearLayout addGradeContainer;
    private LinearLayout addClassContainer;
    private Button btnKhoa;
    private Button btnSinhVien;
    private Button btnLop;
    private Button btnMonHoc;
    private Button btnDiemThi;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Khởi tạo DatabaseHelper khi mở MainActivity
        mDatabaseHelper = new DatabaseHelper(this);

        // Tìm  theo id
        // Tham chiếu đến các ButtonsContainer
//        infoButtonsContainer = findViewById(R.id.infoButtonsContainer);
        addStudentContainer = findViewById(R.id.addStudentContainer);
        addCourseContainer = findViewById(R.id.addCourseContainer);
        addGradeContainer = findViewById(R.id.addGradeContainer);
        addClassContainer = findViewById(R.id.addClassContainer);
        // Tham chiếu đến các Button trong ScrollView
        btnKhoa = findViewById(R.id.btnkhoa);
        btnSinhVien = findViewById(R.id.btnsv);
        btnLop = findViewById(R.id.btnlop);
        btnMonHoc = findViewById(R.id.button_monhoc);
        btnDiemThi = findViewById(R.id.button_diemthi);

        // Mở cơ sở dữ liệu để đọc hoặc ghi
        SQLiteDatabase db = mDatabaseHelper.getWritableDatabase();

        // Kiểm tra xem cơ sở dữ liệu có dữ liệu hay không
        if (isDatabaseEmpty()) {
            // Nếu cơ sở dữ liệu trống, thêm dữ liệu mẫu
            mDatabaseHelper.insertSampleData();
        }

        // Đóng cơ sở dữ liệu sau khi sử dụng
        db.close();



        btnSinhVien.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Chuyển sang CaidatActivity khi nhấn vào btn_caidat
                Intent intent = new Intent(MainActivity.this, Select_Sinhvien.class);
                startActivity(intent);
            }
        });
        btnKhoa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Chuyển sang CaidatActivity khi nhấn vào btn_caidat
                Intent intent = new Intent(MainActivity.this, Select_Khoa.class);
                startActivity(intent);
            }
        });
        btnLop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Chuyển sang CaidatActivity khi nhấn vào btn_caidat
                Intent intent = new Intent(MainActivity.this, Select_Lop.class);
                startActivity(intent);
            }
        });
        btnMonHoc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Chuyển sang CaidatActivity khi nhấn vào btn_caidat
                Intent intent = new Intent(MainActivity.this, Select_MonHoc.class);
                startActivity(intent);
            }
        });
        btnDiemThi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Chuyển sang CaidatActivity khi nhấn vào btn_caidat
                Intent intent = new Intent(MainActivity.this, Select_BangDiemThi.class);
                startActivity(intent);
            }
        });
         addStudentContainer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Chuyển sang CaidatActivity khi nhấn vào btn_caidat
                Intent intent = new Intent(MainActivity.this, Themsinhvien.class);
                startActivity(intent);
            }
        });
         addCourseContainer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Chuyển sang CaidatActivity khi nhấn vào btn_caidat
                Intent intent = new Intent(MainActivity.this, ThemMonHoc.class);
                startActivity(intent);
            }
        });
         addGradeContainer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Chuyển sang CaidatActivity khi nhấn vào btn_caidat
                Intent intent = new Intent(MainActivity.this, ThemDiemThiActivity.class);
                startActivity(intent);
            }
        });
         addClassContainer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Chuyển sang CaidatActivity khi nhấn vào btn_caidat
                Intent intent = new Intent(MainActivity.this, ThemLopActivity.class);
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
