package com.example.quanlysinhvien_app.Select;

import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import com.example.quanlysinhvien_app.Adapter.SinhVienAdapter;
import com.example.quanlysinhvien_app.Database.SinhVien;
import com.example.quanlysinhvien_app.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class Select_Sinhvien extends AppCompatActivity {

    private List<SinhVien> sinhVienList;
    private SinhVienAdapter adapter;
    private ListView listView;
    private boolean isAscendingOrder = true; // Biến để theo dõi trạng thái sắp xếp

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.danhsachsinhvien);

        // Tìm ListView bằng ID
        listView = findViewById(R.id.listView_Diemdanh);
        sinhVienList = new ArrayList<>();

        // Tạo một instance của SinhVienAdapter và đặt adapter cho ListView
        adapter = new SinhVienAdapter(this, sinhVienList);
        listView.setAdapter(adapter);

        loadDataFromFirebase(); // Load dữ liệu từ Firebase khi activity được tạo

        // Tìm nút lọc sinh viên theo tên và mã sinh viên bằng ID
        TextView btnLocSV = findViewById(R.id.btn_locsv);
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
                        SinhVien sinhVien = new SinhVien();
                        sinhVien.setMasv(snapshot.child("masv").getValue(String.class));
                        sinhVien.setHotensv(snapshot.child("hotensv").getValue(String.class));
                        sinhVien.setGioitinh(snapshot.child("gioitinh").getValue(Boolean.class));
                        sinhVien.setDiachi(snapshot.child("diachi").getValue(String.class));
                        sinhVien.setHocbong(snapshot.child("hocbong").getValue(Integer.class));
                        sinhVien.setMalop(snapshot.child("malop").getValue(String.class));
                        sinhVien.setMatinh(snapshot.child("matinh").getValue(String.class));
                        // Phải sử dụng phương thức getValue(Long.class) cho ngày sinh vì Firebase trả về Long
//                        sinhVien.setNgaysinh(new Date(snapshot.child("ngaysinh").getValue(Long.class)));
                        sinhVien.setNoisinh(snapshot.child("noisinh").getValue(String.class));
                        sinhVien.setQuan(snapshot.child("quan").getValue(String.class));

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
