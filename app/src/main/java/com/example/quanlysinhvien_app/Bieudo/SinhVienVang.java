package com.example.quanlysinhvien_app.Bieudo;

import android.os.Bundle;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.quanlysinhvien_app.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;

public class SinhVienVang extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bieudo_sinhvienvang); // Thay thế your_layout bằng tên layout của bạn

        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("diemdanh").child("LH001").child("1").child("2023-11-01");

        // Truy vấn dữ liệu điểm danh cho tháng 11/2023
//        Query query = databaseReference.orderByChild("ngaydiemdanh").equalTo("2023-11-01");

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Map<String, Integer> absentStudentsByClass = new HashMap<>();

                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    String maLop = snapshot.child("malop").getValue(String.class);
                    boolean tinhTrangDiemDanh = snapshot.child("tinhtrangdiemdanh").getValue(Boolean.class);

                    // Kiểm tra nếu học sinh vắng (tinhtrangdiemdanh = false)
                    if (!tinhTrangDiemDanh) {
                        // Kiểm tra xem lớp đã tồn tại trong absentStudentsByClass chưa
                        if (absentStudentsByClass.containsKey(maLop)) {
                            // Nếu tồn tại, tăng giá trị
                            int currentCount = absentStudentsByClass.get(maLop);
                            absentStudentsByClass.put(maLop, currentCount + 1);
                        } else {
                            // Nếu chưa tồn tại, thêm mới
                            absentStudentsByClass.put(maLop, 1);
                        }
                    }
                }

                // Sử dụng absentStudentsByClass ở đây (đây chính là số lượng học sinh vắng theo từng lớp)
                // absentStudentsByClass là một Map với key là mã lớp và value là số lượng học sinh vắng
                // TODO: Xử lý dữ liệu (ví dụ: hiển thị trên giao diện người dùng)
                Log.d("Absent Students", absentStudentsByClass.toString());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Xử lý lỗi nếu có
                Log.e("Firebase Error", databaseError.getMessage());
            }
        });
    }
}
