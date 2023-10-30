package com.example.quanlysinhvien_app.Select;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.quanlysinhvien_app.Adapter.LopAdapter;
import com.example.quanlysinhvien_app.Database.Lop;
import com.example.quanlysinhvien_app.R;
import com.example.quanlysinhvien_app.Tinhnang.Nhapdiem;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class Select_Lop extends AppCompatActivity {

    private List<Lop> lopList;
    private LopAdapter adapter;
    private ListView listView;
    private boolean isAscendingOrder = true; // Biến để theo dõi trạng thái sắp xếp
    private String maNganh; // Biến để lưu giá trị "manganh" nhận từ Intent

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.danhsachlop);

        Intent intent = getIntent();
        if (intent != null) {
            // Nhận giá trị "manganh" từ Intent
            maNganh = intent.getStringExtra("MA_NGANH");
        }

        // Tìm ListView bằng ID
        listView = findViewById(R.id.listView_Lop);
        lopList = new ArrayList<>();

        // Tạo một instance của LopAdapter và đặt adapter cho ListView
        adapter = new LopAdapter(this, lopList);
        listView.setAdapter(adapter);

        loadDataFromFirebase(); // Load dữ liệu từ Firebase khi activity được tạo

        // Tìm nút lọc lớp theo tên và mã lớp bằng ID
        TextView btnLocLop = findViewById(R.id.btn_loclop);
        btnLocLop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Xử lý sự kiện khi nút lọc được nhấn
                // Sắp xếp dữ liệu từ A đến Z hoặc từ Z đến A tùy vào giá trị của isAscendingOrder
                Collections.sort(lopList, new Comparator<Lop>() {
                    @Override
                    public int compare(Lop lop1, Lop lop2) {
                        int result;
                        if (isAscendingOrder) {
                            result = lop1.getMaLop().compareToIgnoreCase(lop2.getMaLop());
                        } else {
                            result = lop2.getMaLop().compareToIgnoreCase(lop1.getMaLop());
                        }
                        return result;
                    }
                });

                // Đảo ngược giá trị của biến isAscendingOrder để sắp xếp ngược lại ở lần tiếp theo
                isAscendingOrder = !isAscendingOrder;

                // Cập nhật dữ liệu trong adapter và hiển thị lại ListView
                adapter.notifyDataSetChanged();
            }
        });

        // chọn lớp từ listview
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // Lấy lớp được chọn từ danh sách
                Lop selectedLop = lopList.get(position);

                // Tạo Intent để chuyển dữ liệu sang trang Nhapdiem
                Intent intent = new Intent(Select_Lop.this, Select_Sinhvien.class);

                // Đặt dữ liệu (maLop) vào Intent
                intent.putExtra("malop", selectedLop.getMaLop());

                // Chuyển đến trang Nhapdiem
                startActivity(intent);
            }
        });
    }

    private void loadDataFromFirebase() {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference databaseReference = database.getReference("lop");

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                lopList.clear(); // Xóa dữ liệu cũ trước khi thêm dữ liệu mới từ Firebase

                // Lặp qua dữ liệu đã lấy từ Firebase
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    // Kiểm tra xem dữ liệu từ Firebase có tồn tại và có thuộc "manganh" đã chọn không
                    if (snapshot.exists() && snapshot.child("manganh").getValue(String.class).equals(maNganh)) {
                        Lop lop = new Lop();
                        lop.setMaLop(snapshot.child("malop").getValue(String.class));
                        lop.setTenLop(snapshot.child("tenlop").getValue(String.class));
                        lopList.add(lop);
                    }
                }

                // Cập nhật dữ liệu trong adapter và hiển thị lại ListView
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Xử lý lỗi nếu có
            }
        });
    }
}
