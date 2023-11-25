package com.example.quanlysinhvien_app.Select;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.quanlysinhvien_app.R;

public class ChinhSuaDiem extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chinh_sua_diem);

        // Nhận dữ liệu từ Intent
        Intent intent = getIntent();
        String masv = intent.getStringExtra("MASV");
        String malop = intent.getStringExtra("MALOP");
        String lanthi = intent.getStringExtra("LANTHI");
        String hocky = intent.getStringExtra("HOCKY");
        String diem = intent.getStringExtra("DIEM");

        // Bây giờ bạn có thể sử dụng masv, malop, lanthi, hocky trong Activity của mình
        // Ví dụ: Hiển thị chúng trên TextViews hoặc thực hiện các thao tác khác.
    }
}

