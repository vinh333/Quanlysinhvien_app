package com.example.quanlysinhvien_app;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.drawable.RoundedBitmapDrawable;
import androidx.core.graphics.drawable.RoundedBitmapDrawableFactory;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;


import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        // Hoặc sử dụng để đọc dữ liệu: SQLiteDatabase db = mDatabaseHelper.getReadableDatabase();

        // Thực hiện các thao tác với cơ sở dữ liệu ở đây
        //...

        // Đừng quên đóng cơ sở dữ liệu khi bạn đã sử dụng xong


        // Tìm LinearLayout theo id
        LinearLayout btnCaiDat = findViewById(R.id.btn_caidat);

        // Đặt OnClickListener cho LinearLayout
        btnCaiDat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Chuyển sang CaidatActivity khi nhấn vào btn_caidat
                Intent intent = new Intent(MainActivity.this, CaidatActivity.class);
                startActivity(intent);
            }
        });

    }





}
