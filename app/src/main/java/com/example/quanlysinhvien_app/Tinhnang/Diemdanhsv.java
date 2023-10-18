package com.example.quanlysinhvien_app.Tinhnang;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.quanlysinhvien_app.Adapter.DiemDanhAdapter;
import com.example.quanlysinhvien_app.Database.Diemdanh;
import com.example.quanlysinhvien_app.R;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class Diemdanhsv extends AppCompatActivity {

    private List<Diemdanh> diemDanhList;
    private DiemDanhAdapter adapter;
    private ListView listView;
    private Spinner spinnerLop;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.diemdanh);

        listView = findViewById(R.id.listView_Diemdanh);
        spinnerLop = findViewById(R.id.spinner_lop);
        diemDanhList = new ArrayList<>();
        adapter = new DiemDanhAdapter(this, diemDanhList);
        listView.setAdapter(adapter);

        // Khởi tạo ArrayAdapter cho Spinner
        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerLop.setAdapter(spinnerAdapter);

        // Lấy danh sách tên lớp từ Firebase và đưa vào Spinner
        DatabaseReference lopRef = FirebaseDatabase.getInstance().getReference("lop");
        lopRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                List<String> tenLopList = new ArrayList<>();

                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    String tenLop = snapshot.child("malop").getValue(String.class);
                    if (tenLop != null) {
                        tenLopList.add(tenLop);
                    }
                }

                // Cập nhật danh sách lớp trong Spinner
                spinnerAdapter.clear();
                spinnerAdapter.addAll(tenLopList);
                spinnerAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Xử lý khi việc lấy dữ liệu bị hủy bỏ hoặc thất bại
            }
        });

        // Xử lý sự kiện khi chọn một lớp từ Spinner
        spinnerLop.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String tenLopChon = parent.getItemAtPosition(position).toString();

                // Lấy danh sách sinh viên tương ứng với lớp đã chọn từ Firebase
                DatabaseReference sinhVienRef = FirebaseDatabase.getInstance().getReference("sinhvien");
                diemDanhList.clear(); // Xóa dữ liệu cũ trước khi thêm dữ liệu mới

                // Lắng nghe sự kiện thêm sinh viên vào lớp được chọn
                sinhVienRef.orderByChild("malop").equalTo(tenLopChon).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                            Diemdanh diemdanh = snapshot.getValue(Diemdanh.class);
                            if (diemdanh != null) {
                                diemDanhList.add(diemdanh);
                            }

                        }
                        adapter.notifyDataSetChanged();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                        // Xử lý khi việc lấy dữ liệu bị hủy bỏ hoặc thất bại
                    }
                });
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Xử lý khi không có lớp nào được chọn
            }
        });

    }
}
