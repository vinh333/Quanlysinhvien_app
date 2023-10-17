package com.example.quanlysinhvien_app;


import android.os.Bundle;
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
        setContentView(R.layout.chucnangthem);

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


        int currentMode = AppCompatDelegate.getDefaultNightMode();

        // Tìm và tham chiếu đến Switch theo ID "giaodien"
        Switch switchButton = findViewById(R.id.giaodien);

        switchButton.setOnCheckedChangeListener((buttonView, isChecked) -> {

            if (isChecked) {
                newMode = AppCompatDelegate.MODE_NIGHT_YES; // Chế độ tối
            } else {
                newMode = AppCompatDelegate.MODE_NIGHT_NO; // Chế độ sáng
            }

            // Kiểm tra xem chế độ mới có khác với chế độ hiện tại không
            if (currentMode != newMode) {
                // Thiết lập chế độ mới
                AppCompatDelegate.setDefaultNightMode(newMode);

                // Cập nhật giao diện
//                recreate();
            }

        });


    }
}
