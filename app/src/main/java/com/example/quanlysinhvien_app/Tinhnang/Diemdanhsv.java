package com.example.quanlysinhvien_app.Tinhnang;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

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


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class Diemdanhsv extends AppCompatActivity {

    private List<Diemdanh> diemDanhList;
    private DiemDanhAdapter adapter;
    private ListView listView;
    private Spinner spinnerLop , spinnerMon;
    private EditText dateEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.diemdanh);

        listView = findViewById(R.id.listView_Diemdanh);
        spinnerLop = findViewById(R.id.spinner_lop);
        spinnerMon = findViewById(R.id.spiner_mon);
        diemDanhList = new ArrayList<>();
        adapter = new DiemDanhAdapter(this, diemDanhList);
        listView.setAdapter(adapter);
        Button luuDiemDanhButton = findViewById(R.id.luudiemdanh);

        // Khởi tạo ArrayAdapter cho Spinner lớp
        ArrayAdapter<String> spinnerLopAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item);
        spinnerLopAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerLop.setAdapter(spinnerLopAdapter);

// Lấy danh sách tên lớp từ Firebase và đưa vào Spinner lớp
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

                // Cập nhật danh sách lớp trong Spinner lớp
                spinnerLopAdapter.clear();
                spinnerLopAdapter.addAll(tenLopList);
                spinnerLopAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Xử lý khi việc lấy dữ liệu bị hủy bỏ hoặc thất bại
            }
        });

        // Khởi tạo ArrayAdapter cho Spinner môn
        ArrayAdapter<String> spinnerMonAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item);
        spinnerMonAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerMon.setAdapter(spinnerMonAdapter);

        // Lấy danh sách tên môn từ Firebase và đưa vào Spinner môn
        DatabaseReference monRef = FirebaseDatabase.getInstance().getReference("monhoc");
        monRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                List<String> tenMonList = new ArrayList<>();

                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    String tenMon = snapshot.child("tenmonhoc").getValue(String.class);
                    if (tenMon != null) {
                        tenMonList.add(tenMon);
                    }
                }

                // Cập nhật danh sách môn trong Spinner môn
                spinnerMonAdapter.clear();
                spinnerMonAdapter.addAll(tenMonList);
                spinnerMonAdapter.notifyDataSetChanged();
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

        // Thêm sự kiện onClickListener cho nút "luudiemdanh"
        luuDiemDanhButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Gọi phương thức để lưu dữ liệu lên Firebase
                luuDanhSachDiemDanh();
            }
        });

        // Ánh xạ EditText ngày ( chọn ngày datepicker)
        dateEditText = findViewById(R.id.dateEditText);

        // Thiết lập sự kiện khi EditText ngày được nhấn
        dateEditText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePickerDialog();
            }
        });
    }

    private void luuDanhSachDiemDanh() {
        // Lấy ngày và môn điểm danh từ EditText và Spinner
        String ngayDiemDanhStr = dateEditText.getText().toString();
        String monDiemDanh = spinnerMon.getSelectedItem().toString();
        String lop = spinnerLop.getSelectedItem().toString();
        String maSV = ""; // Bạn cần lấy mã sinh viên từ dữ liệu nơi đó (chẳng hạn như EditText)

        // Chuyển đổi chuỗi ngày thành đối tượng java.util.Date
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        SimpleDateFormat dateFormatkey = new SimpleDateFormat("yyyy/MM/dd");
        try {
            // Chuyển đổi chuỗi ngày thành đối tượng java.util.Date
            java.util.Date parsedDate = dateFormat.parse(ngayDiemDanhStr);
            // Chuyển đổi ngày thành chuỗi ngày theo định dạng key
            String ngayDiemDanhKey = dateFormatkey.format(parsedDate);

            DatabaseReference diemdanhRef = FirebaseDatabase.getInstance().getReference("diemdanh");

            // Tạo key dựa trên lớp, môn, ngày và mã sinh viên
            String diemdanhId = lop + "_" + monDiemDanh + "_" + ngayDiemDanhKey ;

            // Đặt ngày và môn điểm danh cho đối tượng diemdanh (dưới dạng chuỗi)
            Diemdanh diemdanh = new Diemdanh();
            diemdanh.setNgaydiemdanh(ngayDiemDanhStr);
            diemdanh.setMamonhoc(monDiemDanh);
            diemdanh.setMalop(lop);

            // Đưa dữ liệu lên Firebase tại key được tạo
            diemdanhRef.child(diemdanhId).setValue(diemdanh);

            // Thông báo cho người dùng biết rằng dữ liệu đã được lưu thành công (nếu cần thiết)
            Toast.makeText(this, "Dữ liệu đã được lưu thành công!", Toast.LENGTH_SHORT).show();
        } catch (ParseException e) {
            e.printStackTrace();
            // Xử lý lỗi khi không thể chuyển đổi ngày
        }
    }






    private void showDatePickerDialog() {
        // Lấy ngày hiện tại
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        // Hiển thị DatePickerDialog
        DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        // Xử lý khi người dùng chọn ngày
                        // Hiển thị ngày được chọn trong EditText ngày
                        dateEditText.setText(dayOfMonth + "/" + (month + 1) + "/" + year);
                    }
                }, year, month, day);
        // Hiển thị DatePickerDialog
        datePickerDialog.show();
    }
}
