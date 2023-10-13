package com.example.quanlysinhvien_app.Tinhnang;

import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.quanlysinhvien_app.Database.SinhVien;
import com.example.quanlysinhvien_app.DatabaseHelper;
import com.example.quanlysinhvien_app.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Date;

public class Themsinhvien extends AppCompatActivity {

    private ImageView imageView;
    private EditText txtMasv, txtHoTen, txtTen, txtGioiTinh, txtNgaySinh, txtNoiSinh,
            txtDiaChi, txtMaTinh, txtQuan, txtMaLop, txtHocBong;
    private Button btnLuu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.nhapthongtinsv);

        // Ánh xạ các phần tử từ layout XML
        imageView = findViewById(R.id.imageView3);
        txtMasv = findViewById(R.id.txtmasv);
        txtHoTen = findViewById(R.id.txtho);

        txtGioiTinh = findViewById(R.id.txtgioitinh);
        txtNgaySinh = findViewById(R.id.txtngaysinh);
        txtNoiSinh = findViewById(R.id.txtnoisinh);
        txtDiaChi = findViewById(R.id.editTextText5);
        txtMaTinh = findViewById(R.id.txtmatinh);
        txtQuan = findViewById(R.id.txtquan);
        txtMaLop = findViewById(R.id.txtmalop);
        txtHocBong = findViewById(R.id.txthocbong);
        btnLuu = findViewById(R.id.button_luu);

        // Thêm các sự kiện xử lý vào đây
        btnLuu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Xử lý sự kiện khi nút Lưu được nhấn
                String masv = txtMasv.getText().toString();
                String hotensv = txtHoTen.getText().toString();

                // Chuyển đổi giới tính từ chuỗi sang boolean
                boolean gioitinh = Boolean.parseBoolean(txtGioiTinh.getText().toString());
                String diaChi = txtDiaChi.getText().toString();
                String ngaySinh = txtNgaySinh.getText().toString();
                String noiSinh = txtNoiSinh.getText().toString();

                String maTinh = txtMaTinh.getText().toString();
                String quan = txtQuan.getText().toString();
                String maLop = txtMaLop.getText().toString();

                // Chuyển đổi hocBong từ chuỗi sang int
                int hocBong = Integer.parseInt(txtHocBong.getText().toString());

                // Kiểm tra xem tất cả các trường đã được nhập chưa
                if (!masv.isEmpty() && !hotensv.isEmpty() && !ngaySinh.isEmpty() && !noiSinh.isEmpty() &&
                        !diaChi.isEmpty() && !maTinh.isEmpty() && !quan.isEmpty() && !maLop.isEmpty()) {

                    // Tạo đối tượng SinhVien để đẩy lên Firebase
                    SinhVien sinhVien = new SinhVien( masv,  hotensv,  gioitinh,  diaChi,  hocBong,
                            maLop,  maTinh,  ngaySinh,  noiSinh,  quan);

                    // Thực hiện việc ghi dữ liệu lên Firebase
                    FirebaseDatabase database = FirebaseDatabase.getInstance();
                    DatabaseReference myRef = database.getReference("sinhvien");
                    myRef.child(masv).setValue(sinhVien);


                } else {

                }
            }
        });

    }
}
