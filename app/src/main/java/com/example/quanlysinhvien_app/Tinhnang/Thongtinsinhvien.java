package com.example.quanlysinhvien_app.Tinhnang;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.quanlysinhvien_app.Adapter.CustomAdapter;
import com.example.quanlysinhvien_app.Database.SinhVien;
import com.example.quanlysinhvien_app.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Thongtinsinhvien extends AppCompatActivity {
    private SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

    private TextView txtMaSV, txtTenSV, txtGioiTinh, txtNgaySinh, txtDiaChi, txtMaLop, txtNoiSinh, txtNamHoc;

    private String tenKhoaHoc;
    private  List<String> khoaHocList = new ArrayList<>();
    private  String masv ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_thongtisinhvien);

        txtMaSV = findViewById(R.id.txt_tbmon);
        txtTenSV = findViewById(R.id.txt_tensv);
        txtGioiTinh = findViewById(R.id.txt_gioitinh);
        txtNgaySinh = findViewById(R.id.txt_ngaysinh);
        txtDiaChi = findViewById(R.id.txt_diachi);
        txtMaLop = findViewById(R.id.txt_malop);
        txtNoiSinh = findViewById(R.id.txt_noisinh);
        txtNamHoc = findViewById(R.id.txt_namhoc);

        Intent intent = getIntent();
        if (intent != null) {
             masv = intent.getStringExtra("masv");
            loadDataFromFirebaseByMaSV(masv);
        }

        // Khai báo ListView
        ListView listView = findViewById(R.id.list_nienkhoa);



        // Ánh xạ ListView và thiết lập CustomAdapter
        CustomAdapter adapter = new CustomAdapter(Thongtinsinhvien.this, R.layout.layout_thongtisinhvien, khoaHocList);
        listView.setAdapter(adapter);

        // Thiết lập sự kiện khi người dùng nhấn vào một mục trong ListView

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // Lấy năm học từ danh sách niên khóa
                String selectedYear = khoaHocList.get(position);

                // Tạo Intent để chuyển dữ liệu năm học và masv qua trang Hienthi_Diem
                Intent intent = new Intent(Thongtinsinhvien.this, Hienthi_Diem.class);
                intent.putExtra("selectedYear", selectedYear);
                intent.putExtra("masv", masv);

                // Khởi chạy Intent
                startActivity(intent);
            }
        });

    }

    private void loadDataFromFirebaseByMaSV(String masvToSearch) {
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("sinhvien");
        Query query = databaseReference.orderByChild("masv").equalTo(masvToSearch);

        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    if (snapshot.exists()) {
                        SinhVien sinhVien = snapshot.getValue(SinhVien.class);
                        if (sinhVien != null) {
                            txtMaSV.setText(sinhVien.getMasv());
                            txtTenSV.setText(sinhVien.getHotensv());

                            // Kiểm tra giới tính và hiển thị "Nam" hoặc "Nữ"
                            if (sinhVien.getGioitinh()) {
                                txtGioiTinh.setText("Nam");
                            } else {
                                txtGioiTinh.setText("Nữ");
                            }

                            String ngaySinhString = String.valueOf(sinhVien.getNgaysinh());
                            try {
                                Date ngaySinhDate = dateFormat.parse(ngaySinhString);
                                txtNgaySinh.setText(dateFormat.format(ngaySinhDate));
                            } catch (ParseException e) {
                                e.printStackTrace();
                            }
                            txtDiaChi.setText(sinhVien.getDiachi());
                            txtMaLop.setText(sinhVien.getMalop());
                            txtNoiSinh.setText(sinhVien.getNoisinh());

                            String maLop = sinhVien.getMalop();
                            DatabaseReference lopReference = FirebaseDatabase.getInstance().getReference("lop").child(maLop);
                            lopReference.addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot lopSnapshot) {
                                    if (lopSnapshot.exists()) {
                                        String maKhoaHoc = lopSnapshot.child("makhoahoc").getValue(String.class);

                                        DatabaseReference khoaHocReference = FirebaseDatabase.getInstance().getReference("khoahoc").child(maKhoaHoc);
                                        khoaHocReference.addListenerForSingleValueEvent(new ValueEventListener() {
                                            @Override
                                            public void onDataChange(@NonNull DataSnapshot khoaHocSnapshot) {
                                                if (khoaHocSnapshot.exists()) {
                                                    tenKhoaHoc = khoaHocSnapshot.child("tenkhoahoc").getValue(String.class);
                                                    txtNamHoc.setText(tenKhoaHoc);

                                                    // Tạo danh sách niên khóa từ năm hiện tại đến 4 năm tiếp theo

                                                    tenKhoaHoc = tenKhoaHoc.substring(8); // Bỏ "Khoahoc " từ đầu chuỗi
                                                    int currentYear = Integer.parseInt(tenKhoaHoc);
                                                    for (int i = currentYear; i < currentYear + 4; i++) {
                                                        khoaHocList.add(String.valueOf(i));
                                                    }

                                                    // Ánh xạ ListView
                                                    ListView listView = findViewById(R.id.list_nienkhoa);

                                                    // Tạo CustomAdapter và gắn vào ListView
                                                    CustomAdapter adapter = new CustomAdapter(Thongtinsinhvien.this, R.layout.layout_thongtisinhvien, khoaHocList);
                                                    listView.setAdapter(adapter);
                                                }
                                            }

                                            @Override
                                            public void onCancelled(@NonNull DatabaseError databaseError) {
                                                // Xử lý khi truy vấn bị hủy bỏ hoặc thất bại
                                            }
                                        });
                                    }
                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError databaseError) {
                                    // Xử lý khi truy vấn bị hủy bỏ hoặc thất bại
                                }
                            });
                        }
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Xử lý khi truy vấn bị hủy bỏ hoặc thất bại
            }
        });
    }
}
