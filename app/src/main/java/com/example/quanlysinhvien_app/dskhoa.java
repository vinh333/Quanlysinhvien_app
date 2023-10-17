package com.example.quanlysinhvien_app;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class dskhoa extends AppCompatActivity {
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dskhoas);

        LinearLayout LLkhoa21 = findViewById(R.id.LLkhoa21);

        LLkhoa21.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Chuyển sang màn hình Trang Chủ
                Intent intent = new Intent(dskhoa.this, khoa.class);
                startActivity(intent);
            }
        });
    }
}
