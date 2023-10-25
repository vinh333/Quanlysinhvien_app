package com.example.quanlysinhvien_app.Tinhnang;

import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.quanlysinhvien_app.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class Hienthi_Diem extends AppCompatActivity {
    private DatabaseReference mDatabase;
    private String masv;
    private ListView listView;
    private ArrayAdapter<String> adapter;
    private List<String> subjects;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_hienthi_diem);

        // Nhận Intent từ Activity trước
        Intent intent = getIntent();
        if (intent != null) {
            String selectedYear = intent.getStringExtra("selectedYear");
            masv = intent.getStringExtra("masv");
            // In điểm ra Log
            Log.d("Hienthi_Diem", "Điểm cho năm học " + selectedYear + masv);
        }

        // Khởi tạo ListView và Adapter
        listView = findViewById(R.id.list_danhsachmondiem);
        subjects = new ArrayList<>();
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, subjects);
        listView.setAdapter(adapter);

        // Kết nối tới Firebase Realtime Database
        mDatabase = FirebaseDatabase.getInstance().getReference().child("bangdiemthihocky").child(masv);

        // Lắng nghe sự kiện khi dữ liệu thay đổi trên Firebase
        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                // Dữ liệu đã thay đổi, xử lý dữ liệu ở đây
                subjects.clear();
                for (DataSnapshot subjectSnapshot : dataSnapshot.getChildren()) {
                    String subjectName = subjectSnapshot.getKey();
                    subjects.add(subjectName);
                }
                // Thông báo cho Adapter rằng dữ liệu đã thay đổi, cần cập nhật giao diện
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Xử lý khi có lỗi xảy ra khi đọc dữ liệu từ Firebase
            }
        });
    }
}
