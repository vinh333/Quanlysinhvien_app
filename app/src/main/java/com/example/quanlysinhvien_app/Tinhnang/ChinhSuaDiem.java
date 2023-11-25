package com.example.quanlysinhvien_app.Tinhnang;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.quanlysinhvien_app.DatabaseHelper;
import com.example.quanlysinhvien_app.MainActivity;
import com.example.quanlysinhvien_app.R;

public class ChinhSuaDiem extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chinh_sua_diem);

        // Nhận dữ liệu từ Intent
        Intent intent = getIntent();
        String masv = intent.getStringExtra("MASV");
        String mamon = intent.getStringExtra("MAMON");
        String lanthi = intent.getStringExtra("LANTHI");
        String hocky = intent.getStringExtra("HOCKY");
        double diem = intent.getDoubleExtra("DIEM", 0.0); // 0.0 là giá trị mặc định nếu không tìm thấy key "DIEM"

        // Gán giá trị vào EditText tương ứng
        EditText editTextMasv = findViewById(R.id.editTextMasv);
        EditText editTextMon = findViewById(R.id.editTextMon);
        EditText editTextLanthi = findViewById(R.id.editTextLanThi);
        EditText editTextHocky = findViewById(R.id.editTextHocky);
        EditText editTextDiem = findViewById(R.id.editTextDiem);
        Button btn_capnhat_diem = findViewById(R.id.btn_capnhat_diem);

        editTextMasv.setText(masv);
        editTextMon.setText(mamon);
        editTextLanthi.setText(lanthi);
        editTextHocky.setText(hocky);
        editTextDiem.setText(String.valueOf(diem));

        btn_capnhat_diem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onSaveButtonClick();
            }
        });
    }

    // Thêm sự kiện cho nút "Lưu"
    public void onSaveButtonClick() {
        // Lấy thông tin từ EditText
        String maSV = ((EditText) findViewById(R.id.editTextMasv)).getText().toString();
        String maMonHoc = ((EditText) findViewById(R.id.editTextMon)).getText().toString();
        String lanThi = ((EditText) findViewById(R.id.editTextLanThi)).getText().toString();
        String hocKy = ((EditText) findViewById(R.id.editTextHocky)).getText().toString();
        double diem = Double.parseDouble(((EditText) findViewById(R.id.editTextDiem)).getText().toString());

        // Thực hiện cập nhật điểm vào cơ sở dữ liệu
        DatabaseHelper dbHelper = new DatabaseHelper(this);
        boolean updated = dbHelper.updateDiemThi(maSV, maMonHoc, lanThi, hocKy, diem);

        if (updated) {
            // Hiển thị thông báo cập nhật thành công
            Toast.makeText(this, "Cập nhật thành công", Toast.LENGTH_SHORT).show();

            // Chuyển về MainActivity
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);

            // Kết thúc Activity hiện tại
            finish();
        } else {
            // Hiển thị thông báo cập nhật không thành công hoặc xử lý theo logic của bạn
            Toast.makeText(this, "Cập nhật không thành công", Toast.LENGTH_SHORT).show();
        }

        dbHelper.close();
    }
}
