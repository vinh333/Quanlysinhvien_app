package com.example.quanlysinhvien_app.Select;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.quanlysinhvien_app.Database.MONHOC;
import com.example.quanlysinhvien_app.DatabaseHelper;
import com.example.quanlysinhvien_app.R;
import com.example.quanlysinhvien_app.Tinhnang.ChinhSuaMonHoc;

import java.util.List;

public class Select_MonHoc extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subject_list);

        // Trong hoạt động hoặc fragment của bạn
        DatabaseHelper dbHelper = new DatabaseHelper(this); // Sử dụng context của bạn ở đây
        List<MONHOC> monHocList = dbHelper.getAllMonHoc();
        dbHelper.close();

        // Tạo danh sách chuỗi để hiển thị trong ListView
        ArrayAdapter<MONHOC> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, monHocList);
        ListView listView = findViewById(R.id.listViewMonHoc);
        listView.setAdapter(adapter);

        // Trong Activity hoặc Fragment chứa ListView
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // Lấy thông tin môn học từ danh sách
                MONHOC selectedMonHoc = monHocList.get(position);

                // Tạo Intent để chuyển đến trang ChinhSuaMonHoc
                Intent intent = new Intent(Select_MonHoc.this, ChinhSuaMonHoc.class);

                // Đặt thông tin môn học vào Intent
                intent.putExtra("MAMONHOC", selectedMonHoc.getMaMonHoc());
                intent.putExtra("TENMONHOC", selectedMonHoc.getTenMonHoc());
                intent.putExtra("LYTHUYET", selectedMonHoc.getLyThuyet());
                intent.putExtra("THUCHANH", selectedMonHoc.getThucHanh());

                // Chuyển sang trang ChinhSuaMonHoc
                startActivity(intent);
            }
        });
    }
}
