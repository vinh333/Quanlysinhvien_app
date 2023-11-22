package com.example.quanlysinhvien_app.Tinhnang;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.quanlysinhvien_app.Database.SINHVIEN;
import com.example.quanlysinhvien_app.DatabaseHelper;
import com.example.quanlysinhvien_app.MainActivity;
import com.example.quanlysinhvien_app.R;

import java.util.List;

public class ChinhSuaThongTinSinhVien extends AppCompatActivity {

    private EditText edtMaSV, edtTenSV, edtNgaySinh, edtNoiSinh, edtDiaChi,edt_HocBong;
    private RadioGroup radioGroupGioiTinh;
    private RadioButton radioButtonNam, radioButtonNu;
    private Spinner spinnerMaLop;
    private Button btnCapNhat;

    private String maSV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_chinhsuathongtinsinhvien);

        // Ánh xạ các View từ layout
        edtMaSV = findViewById(R.id.edt_masv);
        edtTenSV = findViewById(R.id.edt_tensv);
        edtNgaySinh = findViewById(R.id.edt_ngaysinh);
        edtNoiSinh = findViewById(R.id.edt_noisinh);
        edtDiaChi = findViewById(R.id.edt_diachi);
        edt_HocBong = findViewById(R.id.edt_hocbong);
        radioGroupGioiTinh = findViewById(R.id.radio_group_gioitinh);
        radioButtonNam = findViewById(R.id.radio_nam);
        radioButtonNu = findViewById(R.id.radio_nu);
        spinnerMaLop = findViewById(R.id.spinner_malop);
        btnCapNhat = findViewById(R.id.btn_capnhat);

        // Lấy mã sinh viên từ Intent
        maSV = getIntent().getStringExtra("MASV");

        // Hiển thị thông tin sinh viên dựa trên mã sinh viên
        showSinhVienDetails(maSV);

        // Xử lý sự kiện khi nhấn nút Cập nhật
//        btnCapNhat.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                // Gọi phương thức cập nhật thông tin sinh viên
//                updateSinhVien();
//            }
//        });
    }

    private void showSinhVienDetails(String maSV) {
        // Lấy thông tin sinh viên từ cơ sở dữ liệu
        DatabaseHelper databaseHelper = new DatabaseHelper(this);
        SINHVIEN sinhVien = databaseHelper.getSinhVienByMaSV(maSV);

        // Hiển thị thông tin sinh viên lên giao diện
        if (sinhVien != null) {
            edtMaSV.setText(sinhVien.getMaSV());
            edtTenSV.setText(sinhVien.getHoTen());
            edtNgaySinh.setText(sinhVien.getNgaySinh());
            edtNoiSinh.setText(sinhVien.getNoiSinh());
            edtDiaChi.setText(sinhVien.getDiaChi());
            edt_HocBong.setText(String.valueOf(sinhVien.getHocBong()));

            // Hiển thị giới tính bằng cách chọn mục phù hợp trên RadioGroup
            if (sinhVien.isGioiTinh()) {
                radioButtonNam.setChecked(true);
            } else {
                radioButtonNu.setChecked(true);
            }

            // Hiển thị mã lớp bằng cách chọn mục phù hợp trên Spinner
            String maLop = sinhVien.getMaLop();
            // Điền dữ liệu vào Spinner từ bảng LOP
            List<String> lopList = databaseHelper.getAllLopNames();
            ArrayAdapter<String> lopAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, lopList);
            lopAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinnerMaLop.setAdapter(lopAdapter);

            // Tìm index của maLop trong danh sách lopList
            int maLopIndex = lopList.indexOf(maLop);

            // Nếu tìm thấy, set selection cho Spinner
            if (maLopIndex != -1) {
                spinnerMaLop.setSelection(maLopIndex);
            } else {
                // Xử lý trường hợp không tìm thấy maLop trong danh sách
                // Có thể hiển thị thông báo hoặc thực hiện các hành động khác tùy thuộc vào yêu cầu của bạn.
            }

            btnCapNhat.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    updateSinhVien();
                }
            });


        }
    }

    private void updateSinhVien() {
        // Lấy thông tin từ các trường nhập liệu
        String tenSV = edtTenSV.getText().toString().trim();
        String ngaySinh = edtNgaySinh.getText().toString().trim();
        String noiSinh = edtNoiSinh.getText().toString().trim();
        String diaChi = edtDiaChi.getText().toString().trim();
        boolean gioiTinh = radioButtonNam.isChecked(); // true nếu là Nam, false nếu là Nữ
        String maLop = spinnerMaLop.getSelectedItem().toString();
        float hocBong = Float.parseFloat(edt_HocBong.getText().toString().trim());

        // Kiểm tra xem các trường cần nhập liệu có đầy đủ không
        if (tenSV.isEmpty() || ngaySinh.isEmpty() || noiSinh.isEmpty() || diaChi.isEmpty()) {
            Toast.makeText(this, "Vui lòng nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show();
            return;
        }

        // Gọi phương thức cập nhật sinh viên trong cơ sở dữ liệu
        DatabaseHelper databaseHelper = new DatabaseHelper(this);
        boolean isSuccess = databaseHelper.updateSinhVien(maSV, tenSV, gioiTinh, ngaySinh, noiSinh, diaChi, maLop, hocBong);

        // Kiểm tra kết quả và thông báo cho người dùng
        if (isSuccess) {
            Toast.makeText(this, "Cập nhật thông tin thành công", Toast.LENGTH_SHORT).show();
            // Chuyển sang CaidatActivity khi nhấn vào btn_caidat
            Intent intent = new Intent(ChinhSuaThongTinSinhVien.this, MainActivity.class);
            startActivity(intent);
        } else {
            Toast.makeText(this, "Cập nhật thông tin thất bại", Toast.LENGTH_SHORT).show();
        }
    }
}
