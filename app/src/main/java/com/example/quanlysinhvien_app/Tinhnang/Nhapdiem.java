package com.example.quanlysinhvien_app.Tinhnang;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.quanlysinhvien_app.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

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

        btnLuu = findViewById(R.id.button_luu2); // Thêm dòng này để ánh xạ nút lưu

        // Thêm sự kiện lắng nghe cho nút Lưu
        btnLuu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Lấy dữ liệu từ Spinner
                String masv = spinner_masv.getSelectedItem().toString();
                String monhoc = spinner_tenmonhoc.getSelectedItem().toString();
                String diem = spinner_diem.getSelectedItem().toString();
                String hocky = spinner_hocky.getSelectedItem().toString();
                String tinchi = spinner_tinchi.getSelectedItem().toString();

                // Kiểm tra xem các giá trị đã được chọn chưa
                if (!masv.isEmpty() && !monhoc.isEmpty() && !diem.isEmpty() && !hocky.isEmpty()) {
                    // Thực hiện việc ghi dữ liệu lên Firebase
                    FirebaseDatabase database = FirebaseDatabase.getInstance();
                    DatabaseReference myRef = database.getReference("bangdiemthihocky");
                    myRef.child(masv).child(hocky).child(monhoc).setValue(diem);
                } else {
                    // Hiển thị thông báo hoặc xử lý lỗi nếu cần
                }
            }
        });

        // Tham chiếu đến Firebase Database và tải dữ liệu vào Spinner mã sinh viên
        DatabaseReference databaseReferenceMasv = FirebaseDatabase.getInstance().getReference("sinhvien");
        loadSpinnerData(databaseReferenceMasv, spinner_masv);

        // Tham chiếu đến Firebase Database và tải dữ liệu vào Spinner mã môn học
        DatabaseReference databaseReferenceMonhoc = FirebaseDatabase.getInstance().getReference("monhoc");
        loadSpinnerData(databaseReferenceMonhoc, spinner_tenmonhoc);

        // Tham chiếu đến Firebase Database và tải dữ liệu vào Spinner học kỳ
        DatabaseReference databaseReferenceTongTinChi = FirebaseDatabase.getInstance().getReference("monhoc");
        loadSpinnerData(databaseReferenceTongTinChi, spinner_tinchi);

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
    }

    private void loadSpinnerData(DatabaseReference databaseReference, final Spinner spinner) {
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                ArrayList<String> dataList = new ArrayList<>();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    // Lấy giá trị của thuộc tính "tenmonhoc" từ DataSnapshot
                    String tenmonhoc = snapshot.child("tenmonhoc").getValue(String.class);
                    if (tenmonhoc != null) {
                        dataList.add(tenmonhoc);
                    }

                    // Lấy giá trị của thuộc tính "masv" từ DataSnapshot
                    String masv = snapshot.child("masv").getValue(String.class);
                    if (masv != null) {
                        dataList.add(masv);
                    }

//                    // Lấy giá trị của thuộc tính "tongtinchi" từ DataSnapshot và chuyển đổi thành chuỗi
//                    Integer tongtinchi = snapshot.child("tongtinchi").getValue(Integer.class);
//                    if (tongtinchi != null) {
//                        dataList.add(String.valueOf(tongtinchi));
//                    }
                    String tenMonHoc = snapshot.child("tenmonhoc").getValue(String.class);
                    Integer soTinChi = snapshot.child("tongtinchi").getValue(Integer.class);
                    if (tenMonHoc != null) {
                        dataList.add(tenMonHoc);
                        // Thêm số lượng tín chỉ vào danh sách
                        for (int i = 1; i <= soTinChi; i++) {
                            dataList.add(String.valueOf(i));
                        }
                    }
                }

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
