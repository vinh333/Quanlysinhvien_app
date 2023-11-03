package com.example.quanlysinhvien_app.Tinhnang;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.quanlysinhvien_app.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Hienthi_Diem_Chitiet extends AppCompatActivity {
    private String masv, monhoc, hocky;
    private Long tongtinchi;

    private TextView textViewDiemHK1, textViewDiemTbHK1, textTenMonIndiem, textHockyIndiem,textViewDiemTbHK4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_item_hienthidiem);

        // Ánh xạ các TextView từ giao diện
        textViewDiemHK1 = findViewById(R.id.textView_diem_hk1);
        textViewDiemTbHK1 = findViewById(R.id.textView_diemtb_hk1);
        textViewDiemTbHK4 = findViewById(R.id.textView_diemtb_hk_4);
        textTenMonIndiem = findViewById(R.id.text_tenmon_indiem);
        textHockyIndiem = findViewById(R.id.text_hocky_indiem);

        // Nhận dữ liệu từ Intent
        Intent intent = getIntent();
        if (intent != null) {
            masv = intent.getStringExtra("masv");
            monhoc = intent.getStringExtra("monhoc");
            hocky = intent.getStringExtra("hocky");

            // Tham chiếu đến dữ liệu trên Firebase
            DatabaseReference monHocReference = FirebaseDatabase.getInstance().getReference("monhoc");
            monHocReference.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        String tenMonHocFirebase = snapshot.child("tenmonhoc").getValue(String.class);
                        if (tenMonHocFirebase != null && tenMonHocFirebase.equals(monhoc)) {
                            tongtinchi = snapshot.child("tongtinchi").getValue(Long.class);
                            DatabaseReference diemHocKyReference = FirebaseDatabase.getInstance()
                                    .getReference("bangdiemthihocky").child(masv).child(hocky).child(monhoc);
                            diemHocKyReference.addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                    int totalTinChi = 0;
                                    int totalDiem = 0;
                                    StringBuilder danhSachDiem = new StringBuilder();
                                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                                        String diemString = snapshot.child("diem").getValue(String.class);
                                        String tinchiString = snapshot.child("tinchi").getValue(String.class);
                                        danhSachDiem.append(" ").append(diemString);
                                        try {
                                            int diem = Integer.parseInt(diemString);
                                            int tinchi = Integer.parseInt(tinchiString);
                                            if (tinchi == tongtinchi.intValue()) {
                                                totalDiem += diem * 2;
                                            } else {
                                                totalDiem += diem;
                                            }

                                        } catch (NumberFormatException e) {
                                            e.printStackTrace();
                                        }
                                    }
                                    double diemTrungBinh = (double) totalDiem / tongtinchi;
                                    double diemTrungBinh4 = diemTrungBinh / 10 * 4; // Chuyển điểm trung bình sang thang điểm 4

                                    Log.d("Hienthi_Diem", "Diem trung binh: " + diemTrungBinh + danhSachDiem);
                                    // Hiển thị dữ liệu lên TextViews
                                    runOnUiThread(() -> {
                                        textViewDiemHK1.setText(danhSachDiem.toString().trim());
                                        textViewDiemTbHK1.setText(String.valueOf(diemTrungBinh));
                                        textTenMonIndiem.setText(monhoc);
                                        textViewDiemTbHK4.setText(String.valueOf(diemTrungBinh4)); // Đưa điểm trung bình vào textView_diemtb_hk_4

                                        textHockyIndiem.setText(hocky.equals("1") ? "Học Kỳ 1" : "Học Kỳ 2");
                                    });
                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError databaseError) {
                                    // Xử lý khi có lỗi xảy ra khi đọc dữ liệu từ Firebase
                                }
                            });
                            break;
                        }
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    // Xử lý khi có lỗi xảy ra khi đọc dữ liệu từ Firebase
                }
            });
        }
    }
}
