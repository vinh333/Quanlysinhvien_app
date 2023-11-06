package com.example.quanlysinhvien_app;


import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Switch;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import com.example.quanlysinhvien_app.Select.Select_Sinhvien;
import com.example.quanlysinhvien_app.Tinhnang.Diemdanhsv;
import com.example.quanlysinhvien_app.Tinhnang.Nhapdiem;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity {
    private DatabaseHelper mDatabaseHelper;
    private DatabaseReference diemDanhRef;
    private int newMode;
    private int tonghocsinh, hocsinhvang;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


            setContentView(R.layout.activity_main);
            LinearLayout linearLayoutTrangChu = findViewById(R.id.LLnhapdiem);
            LinearLayout linearLayoutDiemdanh = findViewById(R.id.LLdiemdanh);
            LinearLayout linearLayoutCaidat = findViewById(R.id.LLcaidat);
            Button btnSV = findViewById(R.id.btnsv);
            Button btnKhoa = findViewById(R.id.btnkhoa);


            linearLayoutTrangChu.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // Chuyển sang màn hình Trang Chủ
                    Intent intent = new Intent(MainActivity.this, Nhapdiem.class);
                    startActivity(intent);
                }
            });
            linearLayoutDiemdanh.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // Chuyển sang màn hình Trang Chủ
                    Intent intent = new Intent(MainActivity.this, Diemdanhsv.class);
                    startActivity(intent);
                }
            });

            linearLayoutCaidat.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // Chuyển sang màn hình Trang Chủ
                    Intent intent = new Intent(MainActivity.this, chucnangthem.class);
                    startActivity(intent);
                }
            });
            btnSV.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // Chuyển sang màn hình Trang Chủ
                    Intent intent = new Intent(MainActivity.this, Select_Sinhvien.class);
                    startActivity(intent);
                }
            });

            btnKhoa.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // Chuyển sang màn hình Trang Chủ
//                    Intent intent = new Intent(MainActivity.this, dskhoa.class);
//                    startActivity(intent);
                }
            });

//        } else {
//            // Mã PIN đã cài đặt, chuyển đến trang nhập mã PIN
//            Intent intent = new Intent(this, PinEntryActivity.class);
//            startActivity(intent);
//
//        }

        diemDanhRef = FirebaseDatabase.getInstance().getReference("diemdanh");
        diemDanhRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                tonghocsinh = 0;
                hocsinhvang = 0;

                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    boolean tinhTrangDiemDanh = snapshot.child("tinhtrangdiemdanh").getValue(Boolean.class);
                    tonghocsinh++;
                    if (!tinhTrangDiemDanh) {
                        hocsinhvang++;
                    }
                }

                // Now you can use tonghocsinh and hocsinhvang as needed.
                Log.d("Absent Students", "Total students: " + tonghocsinh + ", Absent students: " + hocsinhvang);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Handle error
            }
        });
    }
}
