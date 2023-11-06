package com.example.quanlysinhvien_app.Tinhnang;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.quanlysinhvien_app.Adapter.Hienthidiem_CustomAdapter;
import com.example.quanlysinhvien_app.Database.Hienthidiem_Custom;
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
    TextView txtdiemtbhocky , txtxephanghocsinh;
    private ArrayAdapter<Hienthidiem_Custom> adapter;
    private List<String> subjects;
    private Spinner spinnerHocky;
    private  String hockyValue;

    private int totalDiem = 0;
    private int tongtinchi = 0;
    private List<Hienthidiem_Custom> subjects2;
   private double diemtatcamon ;
   private double diemTbtatcamon ;
   private int somonTb ;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_hienthi_diem);
        txtdiemtbhocky = findViewById(R.id.diemtbcahocky);
        txtxephanghocsinh = findViewById(R.id.xephanghocsinh);

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
        subjects2 = new ArrayList<>();
        subjects = new ArrayList<>();
        adapter = new Hienthidiem_CustomAdapter(this, R.layout.list_item_diem_mon, subjects2);
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
                        subjects2.clear();


                        for (DataSnapshot subjectSnapshot : dataSnapshot.getChildren()) {
                            // Lấy giá trị của thuộc tính "monhoc" từ mỗi nút con
                            String monHocValue = subjectSnapshot.getKey();
                            // Thêm vào danh sách subjects
                            subjects.add(monHocValue);
                        }


                        // Khai báo biến để lưu tổng điểm và tổng số tín chỉ
                         totalDiem = 0;
                         tongtinchi = 0;
                         diemTbtatcamon = 0;
                         diemtatcamon = 0;
                         somonTb = 0;
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

                                                    String diemString = null;
                                                    String danhsachdiem ="";
                                                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                                                        diemString = snapshot.child("diem").getValue(String.class);
                                                        String tinchiString = snapshot.child("tinchi").getValue(String.class);
                                                         danhsachdiem = danhsachdiem + " " + diemString;

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
                                                    // Tinh diem trung binh ca hoc ky
                                                    Log.d("Hienthi_Diem", "Diem trung binh: " + diemTrungBinh + diemString);
                                                    diemtatcamon = diemTbtatcamon + diemTrungBinh;
                                                    somonTb ++;
                                                    diemTbtatcamon = diemtatcamon / somonTb;
                                                    txtdiemtbhocky.setText(String.valueOf(diemTbtatcamon)); // Đưa điểm trung bình vào textView_diemtb_hk_4
                                                    // xếp loại
                                                    String xepLoai = "";

                                                    if (diemTbtatcamon >= 9) {
                                                        xepLoai = "Xuất sắc";
                                                    } else if (diemTbtatcamon >= 8) {
                                                        xepLoai = "Giỏi";
                                                    } else if (diemTbtatcamon >= 6.5) {
                                                        xepLoai = "Khá";
                                                    } else if (diemTbtatcamon >= 5) {
                                                        xepLoai = "Trung bình";
                                                    } else {
                                                        xepLoai = "Yếu";
                                                    }
                                                    txtxephanghocsinh.setText(String.valueOf(xepLoai)); // Đưa điểm trung bình vào textView_diemtb_hk_4

                                                    Log.d("diemTbtatcamon", String.valueOf(diemTbtatcamon));
                                                    Hienthidiem_Custom hienthidiem = new Hienthidiem_Custom(tenMonHocFirebase, diemTrungBinh, danhsachdiem);
                                                    subjects2.add(hienthidiem);
                                                    adapter.notifyDataSetChanged();

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
    }
}
