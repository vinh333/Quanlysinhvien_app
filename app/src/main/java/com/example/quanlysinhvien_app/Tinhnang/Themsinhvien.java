package com.example.quanlysinhvien_app.Tinhnang;

import android.app.DatePickerDialog;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;

import com.example.quanlysinhvien_app.DatabaseHelper;
import com.example.quanlysinhvien_app.R;

import java.util.Calendar;
import java.util.List;

public class Themsinhvien extends AppCompatActivity {

    private ImageView imageView;
    private EditText txtMasv, txtTen, txtNgaySinh, txtNoiSinh, txtDiaChi, txtHocBong;
    private Button btnLuu;
    private Spinner spinnerMaLop, spinnerGioiTinh;
    private String selectedMaLop, selectedGioiTinh;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.nhapthongtinsv);

        // Ánh xạ các phần tử từ layout XML
        imageView = findViewById(R.id.imageView3);
        txtMasv = findViewById(R.id.txtmasv);
        txtTen = findViewById(R.id.txtten);
        txtNgaySinh = findViewById(R.id.txtngaysinh);
        txtNoiSinh = findViewById(R.id.txtnoisinh);
        txtDiaChi = findViewById(R.id.editTextText5);
        txtHocBong = findViewById(R.id.txthocbong);
        btnLuu = findViewById(R.id.button_luu);
        spinnerMaLop = findViewById(R.id.spinnerMaLop);
        spinnerGioiTinh = findViewById(R.id.spinnerGioiTinh);

        // Điền dữ liệu vào Spinner từ bảng LOP
        DatabaseHelper databaseHelper = new DatabaseHelper(Themsinhvien.this);
        List<String> lopList = databaseHelper.getAllLopNames();
        ArrayAdapter<String> lopAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, lopList);
        lopAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerMaLop.setAdapter(lopAdapter);

        // Sự kiện chọn Spinner MaLop
        spinnerMaLop.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                selectedMaLop = parentView.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // Không làm gì ở đây
            }
        });

        // Sự kiện chọn Spinner GioiTinh
        spinnerGioiTinh.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                selectedGioiTinh = parentView.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // Không làm gì ở đây
            }
        });

        // Sự kiện khi nhấn nút Lưu
        btnLuu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Xử lý sự kiện khi nút Lưu được nhấn
                String masv = txtMasv.getText().toString();
                String hovaten = txtTen.getText().toString();
                String ngaySinh = txtNgaySinh.getText().toString();
                String noiSinh = txtNoiSinh.getText().toString();
                String diaChi = txtDiaChi.getText().toString();
                String hocBong = txtHocBong.getText().toString();

                // Chuyển đổi giới tính thành true hoặc false
                boolean gioiTinhValue = selectedGioiTinh.equals("Nam");

                // Thêm dữ liệu vào cơ sở dữ liệu
                DatabaseHelper databaseHelper = new DatabaseHelper(Themsinhvien.this);
                SQLiteDatabase db = databaseHelper.getWritableDatabase();

                // Tạo câu lệnh SQL INSERT
                String insertDataQuery = "INSERT INTO SINHVIEN (MASV, HOTENSV, GIOITINH, NGAYSINH, NOISINH, DIACHI, MALOP, HOCBONG) VALUES ('" +
                        masv + "', '" +
                        hovaten + "', " +
                        gioiTinhValue + ", '" +
                        ngaySinh + "', '" +
                        noiSinh + "', '" +
                        diaChi + "', '" +
                        selectedMaLop + "', '" +
                        hocBong + "')";

                // Thực hiện lệnh SQL INSERT
                db.execSQL(insertDataQuery);

                // Đóng kết nối với cơ sở dữ liệu
                db.close();
            }
        });

        // Sự kiện khi nhấn vào trường ngày sinh
        txtNgaySinh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePickerDialog();
            }
        });
    }

    private void showDatePickerDialog() {
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(
                this,
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        // Mặc định tháng bắt đầu từ 0, nên cần cộng thêm 1
                        month = month + 1;
                        String selectedDate = dayOfMonth + "/" + month + "/" + year;
                        txtNgaySinh.setText(selectedDate);
                    }
                },
                year, month, day
        );

        datePickerDialog.show();
    }
}
