package com.example.quanlysinhvien_app.Select;

import android.os.Bundle;
import android.widget.ListView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.quanlysinhvien_app.Adapter.LopAdapter;
import com.example.quanlysinhvien_app.Database.LOP;
import com.example.quanlysinhvien_app.DatabaseHelper;
import com.example.quanlysinhvien_app.R;

import java.util.List;

public class Select_Lop extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_class_list);

        // Khởi tạo DatabaseHelper và lấy danh sách lớp
        DatabaseHelper dbHelper = new DatabaseHelper(this);
        List<LOP> classList = dbHelper.getAllClasses();

        // Khởi tạo Adapter và gán nó cho ListView
        LopAdapter adapter = new LopAdapter(this, classList);
        ListView listViewClasses = findViewById(R.id.listViewClasses);
        listViewClasses.setAdapter(adapter);
    }
}
