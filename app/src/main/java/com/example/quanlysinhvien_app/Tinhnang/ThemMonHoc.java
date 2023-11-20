package com.example.quanlysinhvien_app.Tinhnang;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.quanlysinhvien_app.DatabaseHelper;
import com.example.quanlysinhvien_app.R;

public class ThemMonHoc extends AppCompatActivity {

    private DatabaseHelper databaseHelper;

    private EditText editTextSubjectCode;
    private EditText editTextSubjectName;
    private EditText editTextLyThuyet;
    private EditText editTextThucHanh;
    private Button buttonAddSubject;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_themmonhoc);

        // Khởi tạo DatabaseHelper
        databaseHelper = new DatabaseHelper(this);

        // Ánh xạ các thành phần giao diện
        editTextSubjectCode = findViewById(R.id.editTextSubjectCode);
        editTextSubjectName = findViewById(R.id.editTextSubjectName);
        editTextLyThuyet = findViewById(R.id.editTextLyThuyet);
        editTextThucHanh = findViewById(R.id.editTextThucHanh);
        buttonAddSubject = findViewById(R.id.buttonAddSubject);

        // Thiết lập sự kiện khi người dùng nhấn nút Thêm Môn Học
        buttonAddSubject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                themMonHoc();
            }
        });
    }

    private void themMonHoc() {
        // Lấy dữ liệu từ các trường nhập liệu
        String maMonHoc = editTextSubjectCode.getText().toString();
        String tenMonHoc = editTextSubjectName.getText().toString();
        int lyThuyet = Integer.parseInt(editTextLyThuyet.getText().toString());
        int thucHanh = Integer.parseInt(editTextThucHanh.getText().toString());

        // Thêm Môn Học vào cơ sở dữ liệu
        databaseHelper.addMonHocFromUI(maMonHoc, tenMonHoc, lyThuyet, thucHanh);

        // Hiển thị thông báo thành công
        Toast.makeText(this, "Thêm Môn Học thành công", Toast.LENGTH_SHORT).show();

        // Xóa dữ liệu từ các trường nhập liệu
        clearInputFields();
    }

    private void clearInputFields() {
        editTextSubjectCode.setText("");
        editTextSubjectName.setText("");
        editTextLyThuyet.setText("");
        editTextThucHanh.setText("");
    }
}

