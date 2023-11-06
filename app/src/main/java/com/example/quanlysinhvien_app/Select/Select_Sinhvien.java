package com.example.quanlysinhvien_app.Select;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import com.example.quanlysinhvien_app.Adapter.SinhVienAdapter;
import com.example.quanlysinhvien_app.Database.SinhVien;
import com.example.quanlysinhvien_app.R;
import com.example.quanlysinhvien_app.Tinhnang.Thongtinsinhvien;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class Select_Sinhvien extends AppCompatActivity {

    private List<SinhVien> sinhVienList;
    private SinhVienAdapter adapter;
    private ListView listView;
    private boolean isAscendingOrder = true; // Biến để theo dõi trạng thái sắp xếp
    private String maLop;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.danhsachsinhvien);

        // Tìm ListView bằng ID
        listView = findViewById(R.id.listView_Lop);
        sinhVienList = new ArrayList<>();

        // Tạo một instance của SinhVienAdapter và đặt adapter cho ListView
        adapter = new SinhVienAdapter(this, sinhVienList);
        listView.setAdapter(adapter);
        Intent intent = getIntent();
        if (intent != null) {
            // Nhận giá trị "manganh" từ Intent
            maLop = intent.getStringExtra("malop");
        }

        loadDataFromFirebase(); // Load dữ liệu từ Firebase khi activity được tạo

        // Tìm nút lọc sinh viên theo tên và mã sinh viên bằng ID
        TextView btnLocSV = findViewById(R.id.btn_loclop);
        btnLocSV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Xử lý sự kiện khi nút lọc được nhấn
                // Sắp xếp dữ liệu từ A đến Z hoặc từ Z đến A tùy vào giá trị của isAscendingOrder
                Collections.sort(sinhVienList, new Comparator<SinhVien>() {
                    @Override
                    public int compare(SinhVien sv1, SinhVien sv2) {
                        int result;
                        if (isAscendingOrder) {
                            result = sv1.getMasv().compareToIgnoreCase(sv2.getMasv());
                        } else {
                            result = sv2.getMasv().compareToIgnoreCase(sv1.getMasv());
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
        // chọn sinh viên từ listview
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // Lấy sinh viên được chọn từ danh sách
                SinhVien selectedSinhVien = sinhVienList.get(position);

                // Tạo Intent để chuyển dữ liệu sang trang Thongtisinhvien
                Intent intent = new Intent(Select_Sinhvien.this, Thongtinsinhvien.class);

                // Đặt dữ liệu (masv) vào Intent
                intent.putExtra("masv", selectedSinhVien.getMasv());

                // Chuyển đến trang Thongtisinhvien
                startActivity(intent);
            }
        });

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
                    // Kiểm tra xem dữ liệu từ Firebase có tồn tại và có thuộc mã lớp đã chọn không
                    if ((snapshot.exists() && snapshot.child("malop").getValue(String.class).equals(maLop)) || maLop.equals("all")) {
                        SinhVien sinhVien = new SinhVien();
                        sinhVien.setMasv(snapshot.child("masv").getValue(String.class));
                        sinhVien.setHotensv(snapshot.child("hotensv").getValue(String.class));
                        sinhVien.setGioitinh(snapshot.child("gioitinh").getValue(Boolean.class));
                        sinhVien.setDiachi(snapshot.child("diachi").getValue(String.class));
                        sinhVien.setHocbong(snapshot.child("hocbong").getValue(Integer.class));
                        sinhVien.setMalop(snapshot.child("malop").getValue(String.class));
                        sinhVien.setMatinh(snapshot.child("matinh").getValue(String.class));
                        // Phải sử dụng phương thức getValue(Long.class) cho ngày sinh vì Firebase trả về Long
//                    sinhVien.setNgaysinh(new Date(snapshot.child("ngaysinh").getValue(Long.class)));
                        sinhVien.setNoisinh(snapshot.child("noisinh").getValue(String.class));
                        sinhVien.setQuan(snapshot.child("quan").getValue(String.class));

                        String avatarUrl = snapshot.child("avatarUrl").getValue(String.class);
                        sinhVien.setAvatarUrl(avatarUrl);

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
