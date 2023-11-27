package com.example.quanlysinhvien_app.Select;

import android.os.Bundle;
import android.widget.ListView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.quanlysinhvien_app.Adapter.KhoaAdapter;
import com.example.quanlysinhvien_app.Database.KHOA;
import com.example.quanlysinhvien_app.DatabaseHelper;
import com.example.quanlysinhvien_app.R;

import java.util.List;

public class Select_Khoa extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_department_list);

        // Khởi tạo DatabaseHelper và lấy danh sách khoa
        DatabaseHelper dbHelper = new DatabaseHelper(this);
        List<KHOA> departmentList = dbHelper.getAllDepartments();

        // Khởi tạo Adapter và gán nó cho ListView
        KhoaAdapter adapter = new KhoaAdapter(this, departmentList);
        ListView listViewDepartments = findViewById(R.id.listViewMonHoc);
        listViewDepartments.setAdapter(adapter);
    }
}

