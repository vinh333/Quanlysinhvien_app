package com.example.quanlysinhvien_app.Tinhnang;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import androidx.appcompat.app.AppCompatActivity;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Hienthi_Diem_Chitiet extends AppCompatActivity {
    private String masv, monhoc;
    private Long tongtinchi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent intent = getIntent();
        if (intent != null) {
            masv = intent.getStringExtra("masv");
            monhoc = intent.getStringExtra("monhoc");

            // Kết nối tới Firebase Realtime Database
            DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("monhoc");
            // Lắng nghe sự kiện khi dữ liệu thay đổi trên Firebase
            databaseReference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        // Lấy giá trị của thuộc tính "tenmonhoc" từ mỗi nút con
                        String tenmonhocFirebase = snapshot.child("tenmonhoc").getValue(String.class);

                        // So sánh giá trị monhoc với tenmonhocFirebase
                        if (tenmonhocFirebase != null && tenmonhocFirebase.equals(monhoc)) {
                            // Nếu trùng, lấy giá trị của "tongtinchi" tương ứng
                            tongtinchi = snapshot.child("tongtinchi").getValue(Long.class);
                            Log.d("Hienthi_Diem", "monhoc: " + monhoc + ", tongtinchi: " + tongtinchi);

                            // Kết nối tới Firebase Realtime Database danh sach diem
                            DatabaseReference databaseReference2 = FirebaseDatabase.getInstance().getReference("bangdiemthihocky").child(masv).child(monhoc);
                            // Lắng nghe sự kiện khi dữ liệu thay đổi trên Firebase
                            databaseReference2.addValueEventListener(new ValueEventListener() {
                                @Override
                                public void onDataChange(DataSnapshot dataSnapshot) {
                                    int totalTinChi = 0;
                                    int totalDiem = 0;

                                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                                        // Lấy giá trị của thuộc tính "diem" và "tinchi" từ mỗi nút con dưới dạng chuỗi
                                        String diemString = snapshot.child("diem").getValue(String.class);
                                        String tinchiString = snapshot.child("tinchi").getValue(String.class);

                                        // Chuyển đổi chuỗi thành số nguyên
                                        try {
                                            int diem = Integer.parseInt(diemString);
                                            int tinchi = Integer.parseInt(tinchiString);

                                            // Tính tổng điểm và tổng tín chỉ
                                            if (tinchi == tongtinchi.intValue()) {
                                                totalDiem += diem * 2;
                                            } else {
                                                totalDiem += diem;
                                            }

                                            totalTinChi += tinchi;
                                        } catch (NumberFormatException e) {
                                            // Xử lý lỗi chuyển đổi chuỗi thành số, nếu cần thiết
                                            e.printStackTrace();
                                        }
                                    }

                                    // Tính điểm trung bình
                                    double diemTrungBinh = (double) totalDiem / totalTinChi;
                                    Log.d("Hienthi_Diem", "Diem trung binh: " + diemTrungBinh);
                                }

                                @Override
                                public void onCancelled(DatabaseError databaseError) {
                                    // Xử lý khi có lỗi xảy ra khi đọc dữ liệu từ Firebase
                                }
                            });
                            break;
                        }
                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                    // Xử lý khi có lỗi xảy ra khi đọc dữ liệu từ Firebase
                }
            });
        }
    }
}
