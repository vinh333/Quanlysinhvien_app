package com.example.quanlysinhvien_app.Tinhnang;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.quanlysinhvien_app.Adapter.CustomAdapter;
import com.example.quanlysinhvien_app.Database.SinhVien;
import com.example.quanlysinhvien_app.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Edit_Thongtinsinhvien extends AppCompatActivity {
    private EditText editMaSV,editTenSV, editNgaySinh, editDiaChi, editGioiTinh, editMaLop, editNoiSinh, editNamHoc;
    private Button btnLuu;
    private DatabaseReference databaseReference;
    private String tenKhoaHoc;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_thongtinsinhvien);

        editMaSV = findViewById(R.id.editMaSV);
        editTenSV = findViewById(R.id.editTenSV);
        editNgaySinh = findViewById(R.id.editNgaySinh);
        editDiaChi = findViewById(R.id.editDiaChi);
        editGioiTinh = findViewById(R.id.editGioiTinh);
        editMaLop = findViewById(R.id.editMaLop);
        editNoiSinh = findViewById(R.id.editNoiSinh);
        editNamHoc = findViewById(R.id.editNamHoc);
        btnLuu = findViewById(R.id.btnLuu);

        // Nhận mã sinh viên từ Intent
        Intent intent = getIntent();
        if (intent != null) {
            final String masv = intent.getStringExtra("masv");

            // Kết nối tới Firebase Database

            databaseReference = FirebaseDatabase.getInstance().getReference("sinhvien").child(masv);

            // Lấy dữ liệu từ Firebase và hiển thị lên các EditText
            databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    if (dataSnapshot.exists()) {
                        SinhVien sinhVien = dataSnapshot.getValue(SinhVien.class);
                        if (sinhVien != null) {
                            editMaSV.setText(sinhVien.getMasv());
                            editTenSV.setText(sinhVien.getHotensv());
                            editNgaySinh.setText(sinhVien.getNgaysinh());
                            editDiaChi.setText(sinhVien.getDiachi());
                            editGioiTinh.setText(sinhVien.getGioitinh() ? "Nam" : "Nữ");
                            editMaLop.setText(sinhVien.getMalop());
                            editNoiSinh.setText(sinhVien.getNoisinh());

                            String maLop = sinhVien.getMalop();
                            DatabaseReference lopReference = FirebaseDatabase.getInstance().getReference("lop").child(maLop);
                            lopReference.addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot lopSnapshot) {
                                    if (lopSnapshot.exists()) {
                                        String maKhoaHoc = lopSnapshot.child("makhoahoc").getValue(String.class);

                                        DatabaseReference khoaHocReference = FirebaseDatabase.getInstance().getReference("khoahoc").child(maKhoaHoc);
                                        khoaHocReference.addListenerForSingleValueEvent(new ValueEventListener() {
                                            @Override
                                            public void onDataChange(@NonNull DataSnapshot khoaHocSnapshot) {
                                                if (khoaHocSnapshot.exists()) {
                                                    tenKhoaHoc = khoaHocSnapshot.child("tenkhoahoc").getValue(String.class);
                                                    editNamHoc.setText(tenKhoaHoc);

                                                    // Tạo danh sách niên khóa từ năm hiện tại đến 4 năm tiếp theo



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

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    // Xử lý khi truy vấn bị hủy bỏ hoặc thất bại
                }
            });

            // Xử lý sự kiện khi người dùng nhấn nút "Lưu"
            btnLuu.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // Lấy dữ liệu từ các EditText
                    String tenSV = editTenSV.getText().toString();
                    String ngaySinh = editNgaySinh.getText().toString();
                    String diaChi = editDiaChi.getText().toString();
                    boolean gioiTinh = editGioiTinh.getText().toString().equalsIgnoreCase("Nam");
                    String maLop = editMaLop.getText().toString();
                    String noiSinh = editNoiSinh.getText().toString();
                    String namHoc = editNamHoc.getText().toString();

                    // Cập nhật dữ liệu lên Firebase
                    databaseReference.child("hotensv").setValue(tenSV);
                    databaseReference.child("ngaysinh").setValue(ngaySinh);
                    databaseReference.child("diachi").setValue(diaChi);
                    databaseReference.child("gioitinh").setValue(gioiTinh);
                    databaseReference.child("malop").setValue(maLop);
                    databaseReference.child("noisinh").setValue(noiSinh);
                    databaseReference.child("namhoc").setValue(namHoc);

                    // Hiển thị thông báo cho người dùng biết rằng dữ liệu đã được cập nhật
                    Toast.makeText(Edit_Thongtinsinhvien.this, "Thông tin sinh viên đã được cập nhật!", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }
}
