package com.example.quanlysinhvien_app.Tinhnang;

import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.quanlysinhvien_app.DatabaseHelper;
import com.example.quanlysinhvien_app.R;

public class Themsinhvien extends AppCompatActivity {

    private ImageView imageView;
    private EditText txtMasv, txtHo, txtTen, txtGioiTinh, txtNgaySinh, txtNoiSinh,
            txtDiaChi, txtMaTinh, txtQuan, txtMaLop, txtHocBong;
    private Button btnLuu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.nhapthongtinsv);

        // Ánh xạ các phần tử từ layout XML
        imageView = findViewById(R.id.imageView3);
        txtMasv = findViewById(R.id.txtmasv);
        txtTen = findViewById(R.id.txtten);
        txtGioiTinh = findViewById(R.id.txtgioitinh);
        txtNgaySinh = findViewById(R.id.txtngaysinh);
        txtNoiSinh = findViewById(R.id.txtnoisinh);
        txtDiaChi = findViewById(R.id.editTextText5);

        txtMaLop = findViewById(R.id.txtmalop);
        txtHocBong = findViewById(R.id.txthocbong);
        btnLuu = findViewById(R.id.button_luu);

        // Thêm các sự kiện xử lý vào đây
        btnLuu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Xử lý sự kiện khi nút Lưu được nhấn
                String masv = txtMasv.getText().toString();
                String hovaten = txtTen.getText().toString();
                String gioiTinh = txtGioiTinh.getText().toString();
                String ngaySinh = txtNgaySinh.getText().toString();
                String noiSinh = txtNoiSinh.getText().toString();
                String diaChi = txtDiaChi.getText().toString();

                String maLop = txtMaLop.getText().toString();
                String hocBong = txtHocBong.getText().toString();

                // Xử lý dữ liệu hoặc gửi dữ liệu đi đây

                // Thêm dữ liệu vào cơ sở dữ liệu
                DatabaseHelper databaseHelper = new DatabaseHelper(Themsinhvien.this);
                SQLiteDatabase db = databaseHelper.getWritableDatabase();

                // Tạo câu lệnh SQL INSERT
                String insertDataQuery = "INSERT INTO SINHVIEN (MASV, HOTENSV, GIOITINH, NGAYSINH, NOISINH, DIACHI, MALOP, HOCBONG) VALUES ('" +
                        masv + "', '" +
                        hovaten + "', '" +
                        gioiTinh + "', '" +
                        ngaySinh + "', '" +
                        noiSinh + "', '" +
                        diaChi + "', '" +
                        maLop + "', '" +
                        hocBong + "')";


                // Thực hiện lệnh SQL INSERT
                db.execSQL(insertDataQuery);

                // Đóng kết nối với cơ sở dữ liệu
                db.close();
            }
        });
    }
}
