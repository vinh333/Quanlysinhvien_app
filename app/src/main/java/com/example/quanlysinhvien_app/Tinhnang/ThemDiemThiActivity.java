package com.example.quanlysinhvien_app.Tinhnang;


import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.quanlysinhvien_app.DatabaseHelper;
import com.example.quanlysinhvien_app.R;

import java.util.List;

// (import statements)
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.quanlysinhvien_app.DatabaseHelper;
import com.example.quanlysinhvien_app.R;

import java.util.List;

public class ThemDiemThiActivity extends AppCompatActivity {

    private DatabaseHelper databaseHelper;
    private Spinner spinnerMaSV, spinnerMaMonHoc;
    private EditText editTextLanThi, editTextHocKy, editTextDiem;
    private Button buttonThemDiem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_themdiemthi);

        // Khởi tạo DatabaseHelper
        databaseHelper = new DatabaseHelper(this);

        // Ánh xạ các thành phần giao diện
        spinnerMaSV = findViewById(R.id.spinnerMaSV);
        spinnerMaMonHoc = findViewById(R.id.spinnerMaMonHoc);
        editTextLanThi = findViewById(R.id.editTextLanThi);
        editTextHocKy = findViewById(R.id.editTextHocKy);
        editTextDiem = findViewById(R.id.editTextDiem);
        buttonThemDiem = findViewById(R.id.buttonThemDiem);

        // Thiết lập danh sách Mã Sinh Viên và Mã Môn Học cho Spinner
        setupSpinnerData();

        // Thiết lập sự kiện khi người dùng nhấn nút Thêm Điểm
        buttonThemDiem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                themDiemThi();
            }
        });
    }

    private void setupSpinnerData() {
        // Lấy danh sách Mã Sinh Viên từ cơ sở dữ liệu và thiết lập cho Spinner
        List<String> maSinhVienList = databaseHelper.getAllMaSinhVien();
        ArrayAdapter<String> maSinhVienAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, maSinhVienList);
        maSinhVienAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerMaSV.setAdapter(maSinhVienAdapter);

        // Lấy danh sách Mã Môn Học từ cơ sở dữ liệu và thiết lập cho Spinner
        List<String> maMonHocList = databaseHelper.getAllMaMonHoc();
        ArrayAdapter<String> maMonHocAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, maMonHocList);
        maMonHocAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerMaMonHoc.setAdapter(maMonHocAdapter);
    }

    private void themDiemThi() {
        // Lấy dữ liệu từ các trường nhập liệu
        String maSV = spinnerMaSV.getSelectedItem().toString();
        String maMonHoc = spinnerMaMonHoc.getSelectedItem().toString();
        String lanThi = editTextLanThi.getText().toString();
        String hocKy = editTextHocKy.getText().toString();
        String diem = editTextDiem.getText().toString();

        // Thêm Điểm Thi vào cơ sở dữ liệu
        databaseHelper.addDiemThiFromUI(maSV, maMonHoc, lanThi, hocKy, Double.parseDouble(diem));

        // Hiển thị thông báo thành công
        Toast.makeText(this, "Thêm Điểm Thi thành công", Toast.LENGTH_SHORT).show();

        // Xóa dữ liệu từ các trường nhập liệu
        clearInputFields();
    }

    private void clearInputFields() {
        // Xóa giá trị của các Spinner và các trường nhập liệu khác
        spinnerMaSV.setSelection(0);
        spinnerMaMonHoc.setSelection(0);
        editTextLanThi.getText().clear();
        editTextHocKy.getText().clear();
        editTextDiem.getText().clear();
    }
}
