package com.example.quanlysinhvien_app.Tinhnang;

import android.os.Bundle;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.quanlysinhvien_app.Adapter.DiemDanhAdapter;
import com.example.quanlysinhvien_app.Database.SinhVien;
import com.example.quanlysinhvien_app.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import java.util.ArrayList;
import java.util.List;

public class Diemdanhsv extends AppCompatActivity {

    private List<SinhVien> sinhVienList;
    private DiemDanhAdapter adapter;
    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.diemdanh);

        // Tìm ListView bằng ID
        listView = findViewById(R.id.listView_Diemdanh);
        sinhVienList = new ArrayList<>();

        // Tạo một instance của DiemDanhAdapter và đặt adapter cho ListView
        adapter = new DiemDanhAdapter(this, sinhVienList);
        listView.setAdapter(adapter);

        loadDataFromFirebase(); // Load dữ liệu từ Firebase khi activity được tạo
    }

    private void loadDataFromFirebase() {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference databaseReference = database.getReference("sinhvien");

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                sinhVienList.clear(); // Xóa dữ liệu cũ trước khi thêm dữ liệu mới từ Firebase

                // Lặp qua dữ liệu đã lấy từ Firebase
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    // Kiểm tra xem dữ liệu từ Firebase có tồn tại không
                    if (snapshot.exists()) {
                        SinhVien sinhVien = snapshot.getValue(SinhVien.class);
                        sinhVienList.add(sinhVien);
                    }
                }

                // Cập nhật dữ liệu trong adapter và hiển thị lại ListView
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Phương thức này được gọi khi việc lấy dữ liệu bị hủy bỏ hoặc thất bại
            }
        });
    }
}
