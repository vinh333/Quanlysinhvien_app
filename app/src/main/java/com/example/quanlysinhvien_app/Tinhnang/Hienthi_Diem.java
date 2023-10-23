package com.example.quanlysinhvien_app.Tinhnang;

import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.util.Log;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.quanlysinhvien_app.R;

public class Hienthi_Diem extends AppCompatActivity {
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_hienthi_diem);

        // Nhận Intent từ Activity trước
        Intent intent = getIntent();
        if (intent != null) {
            String selectedYear = intent.getStringExtra("selectedYear");

            // In điểm ra Log
            Log.d("Hienthi_Diem", "Điểm cho năm học " + selectedYear);
        }
    }
}
