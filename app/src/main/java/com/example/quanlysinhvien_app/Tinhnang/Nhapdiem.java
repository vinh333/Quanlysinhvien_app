package com.example.quanlysinhvien_app.Tinhnang;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.quanlysinhvien_app.Database.BangDiemHocKy;
import com.example.quanlysinhvien_app.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;

public class Nhapdiem extends AppCompatActivity {

    private Spinner spinner_masv, spinner_tenmonhoc, spinner_hocky, spinner_diem,spinner_tinchi;
    private Button btnLuu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.nhapdiem);

        // Ánh xạ các phần tử từ layout XML
        spinner_masv = findViewById(R.id.spinner_sinhvien);
        spinner_hocky = findViewById(R.id.spinner_hocky);
        spinner_tenmonhoc = findViewById(R.id.spinner_tenmonhoc);
        spinner_diem = findViewById(R.id.spinner_diem);
        spinner_tinchi = findViewById(R.id.spinner_tinchi);
        btnLuu = findViewById(R.id.button_luu_nhapmonhoc); // Thêm dòng này để ánh xạ nút lưu

        // Thêm sự kiện lắng nghe cho nút Lưu
        btnLuu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String masv = spinner_masv.getSelectedItem().toString();
                String monhoc = spinner_tenmonhoc.getSelectedItem().toString();
                String diem = spinner_diem.getSelectedItem().toString();
                String hocky = spinner_hocky.getSelectedItem().toString();
                String tinchi = spinner_tinchi.getSelectedItem().toString();

                if (!masv.isEmpty() && !monhoc.isEmpty() && !diem.isEmpty() && !hocky.isEmpty() && !tinchi.isEmpty()) {
                    // Tạo đối tượng DiemThiHocKy
                    BangDiemHocKy diemThiHocKy = new BangDiemHocKy(masv, hocky, monhoc, tinchi, diem);

                    // Thực hiện việc ghi dữ liệu lên Firebase
                    FirebaseDatabase database = FirebaseDatabase.getInstance();
                    DatabaseReference myRef = database.getReference("bangdiemthihocky");
                    myRef.child(masv).child(hocky).child(monhoc).child(tinchi).setValue(diemThiHocKy);

                } else {
                    // Xử lý lỗi hoặc hiển thị thông báo nếu cần
                }
            }
        });



        // Tham chiếu đến Firebase Database và tải dữ liệu vào Spinner mã sinh viên
        DatabaseReference databaseReferenceMasv = FirebaseDatabase.getInstance().getReference("sinhvien");
        loadSpinnerData(databaseReferenceMasv, spinner_masv, "masv");


        // Tham chiếu đến Firebase Database và tải dữ liệu vào Spinner mã môn học
        DatabaseReference databaseReferenceMonhoc = FirebaseDatabase.getInstance().getReference("monhoc");
        loadSpinnerData(databaseReferenceMonhoc, spinner_tenmonhoc, "tenmonhoc");
        loadSpinnerData(databaseReferenceMonhoc, spinner_tinchi, "tongtinchi");


        // Tạo một danh sách điểm và cập nhật Spinner điểm
        ArrayList<String> diemList = new ArrayList<>();
        for (int i = 0; i <= 10; i++) {
            diemList.add(String.valueOf(i));
        }
        ArrayAdapter<String> diemAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, diemList);
        diemAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_diem.setAdapter(diemAdapter);

        // Tạo một danh sách hoc ky và cập nhật Spinner
        ArrayList<String> hockyList = new ArrayList<>();
        for (int i = 1; i <= 2; i++) {
            hockyList.add(String.valueOf(i));
        }
        ArrayAdapter<String> hockyAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, hockyList);
        hockyAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_hocky.setAdapter(hockyAdapter);



        // Thêm sự kiện lắng nghe cho Spinner spinner_tenmonhoc
        spinner_tenmonhoc.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                // Lấy tên môn học được chọn từ Spinner spinner_tenmonhoc
                String selectedMonHoc = parentView.getSelectedItem().toString();

                // Gọi phương thức loadSpinnerData để cập nhật giá trị cho Spinner spinner_tinchi
                loadSpinnerData(databaseReferenceMonhoc, spinner_tinchi, "tongtinchi");
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // Xử lý khi không có môn học nào được chọn
            }
        });

    }

    private void loadSpinnerData(DatabaseReference databaseReference, final Spinner spinner, final String childKey) {
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                ArrayList<String> dataList = new ArrayList<>();
                HashMap<String, Integer> monhocTinChiMap = new HashMap<>();
                HashMap<String, String> sinhVienMap = new HashMap<>();

                // Lặp qua các môn học và lấy thông tin tên môn và số tín chỉ
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    String tenMonHoc = snapshot.child("tenmonhoc").getValue(String.class);
                    Integer soTinChi = snapshot.child("tongtinchi").getValue(Integer.class);
                    String masv = snapshot.child("masv").getValue(String.class);

                    // Lưu thông tin số tín chỉ vào HashMap với key là tên môn học
                    if (tenMonHoc != null && soTinChi != null) {
                        monhocTinChiMap.put(tenMonHoc, soTinChi);
                    }



                    // Lưu giá trị masv vào dataList
                    if (masv != null) {
                        dataList.add(masv);
                    }
                }

                // Kiểm tra xem childKey có phải là tên môn học không
                if (childKey.equals("tenmonhoc")) {
                    for (String tenMonHoc : monhocTinChiMap.keySet()) {
                        dataList.add(tenMonHoc);
                    }
                } else if (childKey.equals("tongtinchi")) {
                    // Lấy số tín chỉ tương ứng với tên môn học được chọn
                    String monHocDuocChon = spinner_tenmonhoc.getSelectedItem().toString();
                    Integer soTinChi = monhocTinChiMap.get(monHocDuocChon);

                    // Tạo danh sách từ 1 đến số tín chỉ
                    if (soTinChi != null) {
                        for (int i = 1; i <= soTinChi; i++) {
                            dataList.add(String.valueOf(i));
                        }
                    }
                }

                // Cập nhật Spinner với danh sách dữ liệu
                ArrayAdapter<String> dataAdapter = new ArrayAdapter<>(Nhapdiem.this, android.R.layout.simple_spinner_item, dataList);
                dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinner.setAdapter(dataAdapter);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Xử lý lỗi nếu có
            }
        });
    }







}
