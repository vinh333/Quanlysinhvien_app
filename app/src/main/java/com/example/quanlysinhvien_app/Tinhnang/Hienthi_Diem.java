package com.example.quanlysinhvien_app.Tinhnang;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
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
    protected void onCreate(@Nullable Bundle savedInstanceState) {
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
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // Lấy giá trị monhoc được chọn từ ListView tại vị trí (position)
                String selectedMonHoc = (String) parent.getItemAtPosition(position);

                // Tạo Intent và chuyển giá trị monhoc thông qua Intent khi mở Hienthi_Diem Activity
                Intent intent = new Intent(Hienthi_Diem.this, Hienthi_Diem_Chitiet.class);
                intent.putExtra("masv", masv); // masvValue là giá trị của masv bạn muốn chuyển
                intent.putExtra("monhoc", selectedMonHoc); // Chuyển giá trị monhoc được chọn
                startActivity(intent);
            }
        });

        // Lắng nghe sự kiện khi dữ liệu thay đổi trên Firebase
        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                // Dữ liệu đã thay đổi, xử lý dữ liệu ở đây
                subjects.clear();
                for (DataSnapshot subjectSnapshot : dataSnapshot.getChildren()) {
                    // Lấy giá trị của thuộc tính "monhoc" từ mỗi nút con
                    String monHocValue = subjectSnapshot.child("monhoc").getValue(String.class);

                    // Thêm vào danh sách subjects
                    subjects.add(monHocValue);
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
