package com.example.quanlysinhvien_app.Tinhnang;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import com.example.quanlysinhvien_app.Database.SinhVien;
import com.example.quanlysinhvien_app.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Thongtinsinhvien extends AppCompatActivity {
    private SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

    private TextView txtMaSV, txtTenSV, txtGioiTinh, txtNgaySinh, txtDiaChi, txtMaLop, txtNoiSinh, txtNamHoc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_thongtisinhvien);

        txtMaSV = findViewById(R.id.txt_masv);
        txtTenSV = findViewById(R.id.txt_tensv);
        txtGioiTinh = findViewById(R.id.txt_gioitinh);
        txtNgaySinh = findViewById(R.id.txt_ngaysinh);
        txtDiaChi = findViewById(R.id.txt_diachi);
        txtMaLop = findViewById(R.id.txt_malop);
        txtNoiSinh = findViewById(R.id.txt_noisinh);
        txtNamHoc = findViewById(R.id.txt_namhoc); // Ánh xạ TextView cho Niên khóa

        Intent intent = getIntent();
        if (intent != null) {
            String masv = intent.getStringExtra("masv");
            loadDataFromFirebaseByMaSV(masv);
        }
    }

    private void loadDataFromFirebaseByMaSV(String masvToSearch) {
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("sinhvien");
        Query query = databaseReference.orderByChild("masv").equalTo(masvToSearch);

        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    if (snapshot.exists()) {
                        SinhVien sinhVien = snapshot.getValue(SinhVien.class);
                        if (sinhVien != null) {
                            txtMaSV.setText(sinhVien.getMasv());
                            txtTenSV.setText(sinhVien.getHotensv());

                            // Kiểm tra giới tính và hiển thị "Nam" hoặc "Nữ"
                            if (sinhVien.getGioitinh()) {
                                txtGioiTinh.setText("Nam");
                            } else {
                                txtGioiTinh.setText("Nữ");
                            }

                            String ngaySinhString = String.valueOf(sinhVien.getNgaysinh()); // Đọc chuỗi ngày tháng từ Firebase
                            try {
                                Date ngaySinhDate = dateFormat.parse(ngaySinhString); // Chuyển đổi chuỗi thành Date
                                txtNgaySinh.setText(dateFormat.format(ngaySinhDate)); // Hiển thị ngày sinh dưới dạng chuỗi
                            } catch (ParseException e) {
                                e.printStackTrace();
                                // Xử lý khi có lỗi xảy ra trong quá trình chuyển đổi
                            }
                            txtDiaChi.setText(sinhVien.getDiachi());
                            txtMaLop.setText(sinhVien.getMalop());
                            txtNoiSinh.setText(sinhVien.getNoisinh());

                            // Lấy mã lớp từ sinh viên
                            String maLop = sinhVien.getMalop();

                            // Truy vấn niên khóa từ mã lớp
                            DatabaseReference lopReference = FirebaseDatabase.getInstance().getReference("lop").child(maLop);
                            lopReference.addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot lopSnapshot) {
                                    if (lopSnapshot.exists()) {
                                        String maKhoaHoc = lopSnapshot.child("makhoahoc").getValue(String.class);

                                        // Lấy tên khoá học từ nút khoahoc
                                        DatabaseReference khoaHocReference = FirebaseDatabase.getInstance().getReference("khoahoc").child(maKhoaHoc);
                                        khoaHocReference.addListenerForSingleValueEvent(new ValueEventListener() {
                                            @Override
                                            public void onDataChange(@NonNull DataSnapshot khoaHocSnapshot) {
                                                if (khoaHocSnapshot.exists()) {
                                                    String tenKhoaHoc = khoaHocSnapshot.child("tenkhoahoc").getValue(String.class);

                                                    // Hiển thị niên khóa trong TextView
                                                    txtNamHoc.setText("Năm học: " + tenKhoaHoc);
                                                }
                                            }

                                            @Override
                                            public void onCancelled(@NonNull DatabaseError databaseError) {
                                                // Xử lý khi truy vấn bị hủy bỏ hoặc thất bại
                                            }
                                        });
                                    }
                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError databaseError) {
                                    // Xử lý khi truy vấn bị hủy bỏ hoặc thất bại
                                }
                            });
                        }
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Xử lý khi truy vấn bị hủy bỏ hoặc thất bại
            }
        });
    }
}
