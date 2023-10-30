package com.example.quanlysinhvien_app.Select;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import com.example.quanlysinhvien_app.Adapter.Nganh_Adapter;
import com.example.quanlysinhvien_app.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import java.util.ArrayList;

public class Select_Nganh extends AppCompatActivity {

    private DatabaseReference nganhRef;
    private ArrayList<String> tenNganhList;
    private ListView listView;
    private Nganh_Adapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_nganh);

        Intent intent = getIntent();
        if (intent != null) {
            String makhoa = intent.getStringExtra("MA_KHOA");

            // Kiểm tra xem mã khoa có tồn tại trong Firebase không
            DatabaseReference khoaRef = FirebaseDatabase.getInstance().getReference().child("khoa").child(makhoa);
            khoaRef.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    if (dataSnapshot.exists()) {
                        // Mã khoa hợp lệ, tiếp tục truy vấn dữ liệu từ Firebase
                        nganhRef = FirebaseDatabase.getInstance().getReference("nganhhoc");
                        Query query = nganhRef.orderByChild("makhoa").equalTo(makhoa);

                        tenNganhList = new ArrayList<>();
                        listView = findViewById(R.id.listViewNganh);

                        // Khởi tạo adapter
                        adapter = new Nganh_Adapter(Select_Nganh.this, R.layout.list_item_nganh, tenNganhList);
                        listView.setAdapter(adapter);

                        // Thực hiện truy vấn dữ liệu từ Firebase
                        query.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                if (dataSnapshot.exists()) {
                                    for (DataSnapshot nganhSnapshot : dataSnapshot.getChildren()) {
                                        String tenNganh = nganhSnapshot.child("tennganh").getValue(String.class);
                                        tenNganhList.add(tenNganh);
                                    }
                                    adapter.notifyDataSetChanged();
                                } else {
                                    // Không tìm thấy ngành học cho mã khoa đã cho
                                    Toast.makeText(Select_Nganh.this, "Không tìm thấy ngành học cho mã khoa này", Toast.LENGTH_SHORT).show();
                                    finish(); // Kết thúc activity nếu không tìm thấy ngành học
                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {
                                // Xử lý lỗi nếu có
                                Toast.makeText(Select_Nganh.this, "Không thể đọc dữ liệu từ Firebase", Toast.LENGTH_SHORT).show();
                            }
                        });
                    } else {
                        // Mã khoa không hợp lệ, thông báo cho người dùng
                        Toast.makeText(Select_Nganh.this, "Mã khoa không hợp lệ", Toast.LENGTH_SHORT).show();
                        finish(); // Kết thúc activity nếu mã khoa không hợp lệ
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    // Xử lý lỗi nếu có
                    Toast.makeText(Select_Nganh.this, "Không thể đọc dữ liệu từ Firebase", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }
}
