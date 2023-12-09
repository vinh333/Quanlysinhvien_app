package com.example.quanlysinhvien_app;


import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.quanlysinhvien_app.Bieudo.SinhVienVang;
import com.example.quanlysinhvien_app.Select.Select_Khoa;
import com.example.quanlysinhvien_app.Select.Select_Lop;
import com.example.quanlysinhvien_app.Select.Select_Sinhvien;
import com.example.quanlysinhvien_app.Tinhnang.Diemdanhsv;
import com.example.quanlysinhvien_app.Tinhnang.HienThiDiemDanhActivity;
import com.example.quanlysinhvien_app.Tinhnang.Nhapdiem;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private DatabaseHelper mDatabaseHelper;
    private DatabaseReference diemDanhRef;
    private int newMode;
    private int tonghocsinh, hocsinhvang;
    private LinearLayout LLsv, LLkhoa, LLlop, LLdsdiemdanh, LLdsdiem, LLnhapdiem, LLnhapdiemdanh, LLthemsv;
    private LinearLayout LLcaidat;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


            setContentView(R.layout.activity_main);
        LLsv = findViewById(R.id.LLsv);
        LLkhoa = findViewById(R.id.LLkhoa);
        LLlop = findViewById(R.id.LLlop);
        LLdsdiemdanh = findViewById(R.id.LLdsdiemdanh);
        LLdsdiem = findViewById(R.id.LLdsdiem);
        LLnhapdiem = findViewById(R.id.LLnhapdiem);
        LLnhapdiemdanh = findViewById(R.id.LLnhapdiemdanh);
        LLthemsv = findViewById(R.id.LLthemsv);

        LLcaidat = findViewById(R.id.LLcaidat);

            PieChart pieChart1 = findViewById(R.id.pieChart1);
            PieChart pieChart2 = findViewById(R.id.pieChart2);
            TextView txtThongTinThemBieuDo = findViewById(R.id.textView_xemthembieudo);

            LLnhapdiem.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // Chuyển sang màn hình Trang Chủ
                    Intent intent = new Intent(MainActivity.this, Nhapdiem.class);
                    startActivity(intent);
                }
            });
            LLdsdiemdanh.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // Chuyển sang màn hình Trang Chủ
                    Intent intent = new Intent(MainActivity.this, Diemdanhsv.class);
                    startActivity(intent);
                }
            });

            LLcaidat.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // Chuyển sang màn hình Trang Chủ
                    Intent intent = new Intent(MainActivity.this, chucnangthem.class);
                    startActivity(intent);
                }
            });
            LLsv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // Chuyển sang màn hình Trang Chủ
                    Intent intent = new Intent(MainActivity.this, Select_Sinhvien.class);
                    intent.putExtra("malop", "Tất cả");
                    startActivity(intent);
                }
            });

            LLkhoa.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
//                     Chuyển sang màn hình Trang Chủ
                    Intent intent = new Intent(MainActivity.this, Select_Khoa.class);
                    startActivity(intent);
                }
            });

            LLlop.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
    //                     Chuyển sang màn hình Trang Chủ
                    Intent intent = new Intent(MainActivity.this, Select_Lop.class);
                    intent.putExtra("MA_NGANH", "Tất cả");
                    startActivity(intent);
                }
            });


            LLdsdiemdanh.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
            // Chuyển sang màn hình Trang Chủ
                    Intent intent = new Intent(MainActivity.this, HienThiDiemDanhActivity.class);
                    startActivity(intent);
                }
            });

        txtThongTinThemBieuDo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Chuyển sang màn hình Trang Chủ
                Intent intent = new Intent(MainActivity.this, SinhVienVang.class);
                startActivity(intent);
            }
        });
//        } else {
//            // Mã PIN đã cài đặt, chuyển đến trang nhập mã PIN
//            Intent intent = new Intent(this, PinEntryActivity.class);
//            startActivity(intent);
//
//        }
        // Truyền học kỳ vào 1,2,all
        loadDataToListView("1",pieChart1);
        loadDataToListView("2",pieChart2);
    }

    private void loadDataToListView(String hocKy, PieChart pieChart) {
        diemDanhRef = FirebaseDatabase.getInstance().getReference("diemdanh");

        diemDanhRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                tonghocsinh = 0;
                hocsinhvang = 0;

                // Duyệt qua tất cả các học kỳ
                for (DataSnapshot hocKySnapshot : dataSnapshot.getChildren()) {
                    // Duyệt qua tất cả các lớp trong học kỳ
                    for (DataSnapshot lopSnapshot : hocKySnapshot.getChildren()) {
                        // Duyệt qua tất cả các ngày trong lớp
                        String lopKey = lopSnapshot.getKey();
                        if(lopKey.equals(hocKy) || hocKy == "all"){
                            for (DataSnapshot ngaySnapshot : lopSnapshot.getChildren()) {
                                // Duyệt qua tất cả các học sinh trong ngày
                                for (DataSnapshot studentSnapshot : ngaySnapshot.getChildren()) {
                                    boolean tinhTrangDiemDanh = studentSnapshot.child("tinhtrangdiemdanh").getValue(Boolean.class);
                                    tonghocsinh++;
                                    if (!tinhTrangDiemDanh) {
                                        hocsinhvang++;
                                    }
                                }
                            }
                        }

                    }
                }

                // Now you can use tonghocsinh and hocsinhvang as needed.
                Log.d("Absent Students", "Total students: " + tonghocsinh + ", Absent students: " + hocsinhvang);

                // Tạo dữ liệu cho biểu đồ tròn
                ArrayList<PieEntry> entries = new ArrayList<>();
                entries.add(new PieEntry(tonghocsinh-hocsinhvang, "Học"));
                entries.add(new PieEntry(hocsinhvang, "Vắng"));

                PieDataSet dataSet = new PieDataSet(entries, "Học Kỳ " + hocKy);



                // Tạo một mảng các màu bạn muốn sử dụng
                int color1 = Color.parseColor("#2D8BBA"); // Màu xanh da trời
                int color2 = Color.parseColor("#41B8D5"); // Màu xanh dương
                int[] colors = {color1, color2};
                dataSet.setColors(colors);


                PieData pieData = new PieData(dataSet);

                // Cài đặt dữ liệu cho biểu đồ tròn
                pieChart.setData(pieData);

                // Tắt chú thích và chú thích mô tả
//                pieChart.getLegend().setEnabled(false);
                pieChart.getDescription().setEnabled(false);
                float textSize = 10f; // Đặt kích thước chữ số là 18 sp
                dataSet.setValueTextSize(textSize);

                dataSet.setValueFormatter(new ValueFormatter() {
                    @Override
                    public String getFormattedValue(float value) {
                        return String.valueOf((int) value); // Chuyển đổi giá trị thành số nguyên
                    }
                });

                // Hiển thị biểu đồ tròn
                pieChart.invalidate();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Handle error
            }
        });
    }

}
