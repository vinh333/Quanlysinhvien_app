package com.example.quanlysinhvien_app.Tinhnang;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;

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
    private Spinner spinnerHocky;
    private  String hockyValue;

    private int totalDiem = 0;
    private int tongtinchi = 0;
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

        // Khởi tạo Spinner và thiết lập Adapter cho Spinner
        spinnerHocky = findViewById(R.id.spinner_hocky_hienthidiem);
        ArrayAdapter<CharSequence> spinnerAdapter = ArrayAdapter.createFromResource(this, R.array.hocky_array, android.R.layout.simple_spinner_item);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerHocky.setAdapter(spinnerAdapter);

        // Thiết lập sự kiện khi lựa chọn từ Spinner
        spinnerHocky.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                // Lấy giá trị được chọn từ Spinner
                String selectedHocky = parentView.getItemAtPosition(position).toString();

                // Cập nhật mDatabase dựa trên học kỳ được chọn
                hockyValue = selectedHocky.equals("Học Kỳ 1") ? "1" : "2";
                mDatabase = FirebaseDatabase.getInstance().getReference().child("bangdiemthihocky").child(masv).child(hockyValue);

                // Gọi lại ValueEventListener nếu cần thiết
                mDatabase.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        // Xử lý dữ liệu khi thay đổi
                        subjects.clear();
                        for (DataSnapshot subjectSnapshot : dataSnapshot.getChildren()) {
                            // Lấy giá trị của thuộc tính "monhoc" từ mỗi nút con
                            String monHocValue = subjectSnapshot.getKey();
                            // Thêm vào danh sách subjects
                            subjects.add(monHocValue);
                        }


                        // Khai báo biến để lưu tổng điểm và tổng số tín chỉ
                         totalDiem = 0;
                         tongtinchi = 0;

                        // Duyệt qua danh sách subjects và lấy dữ liệu từ Firebase
                        for (String monHoc : subjects) {
                            // Tham chiếu đến dữ liệu trên Firebase
                            DatabaseReference monHocReference = FirebaseDatabase.getInstance().getReference("monhoc");
                            monHocReference.addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                                        String tenMonHocFirebase = snapshot.child("tenmonhoc").getValue(String.class);
                                        if (tenMonHocFirebase != null && tenMonHocFirebase.equals(monHoc)) {
                                            Long tongtinchi = snapshot.child("tongtinchi").getValue(Long.class);
                                            DatabaseReference diemHocKyReference = FirebaseDatabase.getInstance()
                                                    .getReference("bangdiemthihocky").child(masv).child(hockyValue).child(monHoc);
                                            diemHocKyReference.addListenerForSingleValueEvent(new ValueEventListener() {
                                                @Override
                                                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                                    int totalTinChi = 0;
                                                    int totalDiem = 0;
                                                    StringBuilder danhSachDiem = new StringBuilder();
                                                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                                                        String diemString = snapshot.child("diem").getValue(String.class);
                                                        String tinchiString = snapshot.child("tinchi").getValue(String.class);
                                                        danhSachDiem.append(" ").append(diemString);
                                                        try {
                                                            int diem = Integer.parseInt(diemString);
                                                            int tinchi = Integer.parseInt(tinchiString);
                                                            if (tinchi == tongtinchi.intValue()) {
                                                                totalDiem += diem * 2;
                                                            } else {
                                                                totalDiem += diem;
                                                            }

                                                        } catch (NumberFormatException e) {
                                                            e.printStackTrace();
                                                        }
                                                    }
                                                    double diemTrungBinh = (double) totalDiem / tongtinchi;

                                                    Log.d("Hienthi_Diem", "Diem trung binh: " + diemTrungBinh + danhSachDiem);

                                                }

                                                @Override
                                                public void onCancelled(@NonNull DatabaseError databaseError) {
                                                    // Xử lý khi có lỗi xảy ra khi đọc dữ liệu từ Firebase
                                                }
                                            });
                                            break;
                                        }
                                    }
                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError databaseError) {
                                    // Xử lý khi có lỗi xảy ra khi đọc dữ liệu từ Firebase
                                }
                            });
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

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // Xử lý khi không có item nào được chọn
            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // Lấy giá trị monhoc được chọn từ ListView tại vị trí (position)
                String selectedMonHoc = (String) parent.getItemAtPosition(position);

                // Tạo Intent và chuyển giá trị monhoc và hocky thông qua Intent khi mở Hienthi_Diem_Chitiet Activity
                Intent intent = new Intent(Hienthi_Diem.this, Hienthi_Diem_Chitiet.class);
                intent.putExtra("masv", masv);
                intent.putExtra("monhoc", selectedMonHoc);
                intent.putExtra("hocky", hockyValue);
                startActivity(intent);
            }
        });
    }
}
