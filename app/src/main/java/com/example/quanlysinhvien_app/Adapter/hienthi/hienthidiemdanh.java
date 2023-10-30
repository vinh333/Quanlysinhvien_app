package com.example.quanlysinhvien_app.Adapter.hienthi;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.quanlysinhvien_app.Adapter.DiemDanhAdapter;
import com.example.quanlysinhvien_app.Database.Diemdanh;
import com.example.quanlysinhvien_app.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class hienthidiemdanh extends AppCompatActivity {
    private ListView listViewDaDiemDanh;
    private DiemDanhAdapter daDiemDanhAdapter;
    private List<Diemdanh> diemdanhList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.hienthidiemdanh);

        listViewDaDiemDanh = findViewById(R.id.list_danhsachdiemdanh);

        // Lấy thông tin lớp, môn học, và ngày diểm danh từ Intent
        Intent intent = getIntent();
        String lop = intent.getStringExtra("lop");
        String monHoc = intent.getStringExtra("monhoc");
        String ngayDiemDanh = intent.getStringExtra("ngaydiemdanh");

        // Tạo tham chiếu đến Firebase
        DatabaseReference diemDanhRef = FirebaseDatabase.getInstance().getReference("diemdanh")
                .child(lop + "_" + monHoc + "_" + ngayDiemDanh);

        diemdanhList = new ArrayList<>();

        diemDanhRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                diemdanhList.clear(); // Xóa dữ liệu cũ trước khi thêm dữ liệu mới

                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Diemdanh diemdanh = snapshot.getValue(Diemdanh.class);
                    if (diemdanh != null) {
                        diemdanhList.add(diemdanh);
                    }
                }

                // Khởi tạo adapter và đặt adapter cho ListView
                daDiemDanhAdapter = new DiemDanhAdapter(hienthidiemdanh.this, diemdanhList);
                listViewDaDiemDanh.setAdapter(daDiemDanhAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Xử lý khi việc lấy dữ liệu bị hủy bỏ hoặc thất bại
            }
        });
    }
}
