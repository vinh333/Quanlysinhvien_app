package com.example.quanlysinhvien_app.Tinhnang;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.quanlysinhvien_app.Database.KHOA;
import com.example.quanlysinhvien_app.DatabaseHelper;
import com.example.quanlysinhvien_app.R;

import java.util.List;

public class ThemLopActivity extends AppCompatActivity {

    private DatabaseHelper databaseHelper;

    private EditText editTextClassCode;
    private EditText editTextClassName;
    private Spinner spinnerDepartments;
    private Button buttonAddClass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_themlophoc);

        // Khởi tạo DatabaseHelper
        databaseHelper = new DatabaseHelper(this);

        // Ánh xạ các thành phần giao diện
        editTextClassCode = findViewById(R.id.editTextClassCode);
        editTextClassName = findViewById(R.id.editTextClassName);
        spinnerDepartments = findViewById(R.id.spinnerDepartments);
        buttonAddClass = findViewById(R.id.buttonAddClass);

        // Thiết lập danh sách Khoa cho Spinner
        setupDepartmentSpinner();

        // Thiết lập sự kiện khi người dùng nhấn nút Thêm Lớp Học
        buttonAddClass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                themLopHoc();
            }
        });
    }

    private void setupDepartmentSpinner() {
        // Lấy danh sách Khoa từ cơ sở dữ liệu
        List<KHOA> departmentList = databaseHelper.getAllDepartments();

        // Tạo ArrayAdapter để hiển thị danh sách Khoa trong Spinner
        ArrayAdapter<KHOA> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, departmentList);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // Thiết lập ArrayAdapter cho Spinner
        spinnerDepartments.setAdapter(adapter);

        // Thiết lập sự kiện khi chọn Khoa từ Spinner
        spinnerDepartments.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                // Lấy Khoa được chọn
                KHOA selectedDepartment = (KHOA) parentView.getItemAtPosition(position);
                // Lưu trữ ID Khoa được chọn (nếu cần thiết)
                // int selectedDepartmentId = selectedDepartment.getId();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // Không có hành động cụ thể khi không có gì được chọn
            }
        });
    }

    private void themLopHoc() {
        // Lấy dữ liệu từ các trường nhập liệu
        String maLop = editTextClassCode.getText().toString();
        String tenLop = editTextClassName.getText().toString();

        // Lấy đối tượng KHOA được chọn từ Spinner
        KHOA selectedDepartment = (KHOA) spinnerDepartments.getSelectedItem();

        if (selectedDepartment != null) {
            // Lấy MAKHOA từ đối tượng KHOA
            String maKhoa = selectedDepartment.getMaKhoa();

            // Thêm Lớp Học vào cơ sở dữ liệu
            databaseHelper.addLopHocFromUI(maLop, tenLop, maKhoa);

            // Hiển thị thông báo thành công
            Toast.makeText(this, "Thêm Lớp Học thành công", Toast.LENGTH_SHORT).show();


        } else {
            // Hiển thị thông báo lỗi nếu không có KHOA nào được chọn
            Toast.makeText(this, "Vui lòng chọn Khoa", Toast.LENGTH_SHORT).show();
        }
    }

}
