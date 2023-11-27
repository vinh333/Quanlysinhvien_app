package com.example.quanlysinhvien_app.Tinhnang;

import static com.example.quanlysinhvien_app.DatabaseHelper.SUBJECT_CODE_COLUMN_NAME;
import static com.example.quanlysinhvien_app.DatabaseHelper.SUBJECT_NAME_COLUMN_NAME;
import static com.example.quanlysinhvien_app.DatabaseHelper.SUBJECT_PRACTICE_COLUMN_NAME;
import static com.example.quanlysinhvien_app.DatabaseHelper.SUBJECT_THEORY_COLUMN_NAME;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.quanlysinhvien_app.Database.MONHOC;
import com.example.quanlysinhvien_app.DatabaseHelper;
import com.example.quanlysinhvien_app.MainActivity;
import com.example.quanlysinhvien_app.R;
import com.example.quanlysinhvien_app.Select.Select_MonHoc;

public class ChinhSuaMonHoc extends AppCompatActivity {

    EditText editTextMaMonHoc, editTextTenMonHoc, editTextLyThuyet, editTextThucHanh;
    Button btnSave;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chinh_sua_mon_hoc);

        editTextMaMonHoc = findViewById(R.id.editTextMaMonHoc);
        editTextTenMonHoc = findViewById(R.id.editTextTenMonHoc);
        editTextLyThuyet = findViewById(R.id.editTextLyThuyet);
        editTextThucHanh = findViewById(R.id.editTextThucHanh);
        btnSave = findViewById(R.id.buttonSaveChanges);

        // Nhận dữ liệu từ Intent
        Intent intent = getIntent();
        String maMonHoc = intent.getStringExtra("MAMONHOC");
        String tenMonHoc = intent.getStringExtra("TENMONHOC");
        int lyThuyet = intent.getIntExtra("LYTHUYET", 0);
        int thucHanh = intent.getIntExtra("THUCHANH", 0);

        // Hiển thị thông tin môn học trong các EditText
        editTextMaMonHoc.setText(maMonHoc);
        editTextTenMonHoc.setText(tenMonHoc);
        editTextLyThuyet.setText(String.valueOf(lyThuyet));
        editTextThucHanh.setText(String.valueOf(thucHanh));

        // Xử lý sự kiện khi nhấn nút "Lưu"
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Lưu thông tin đã chỉnh sửa vào cơ sở dữ liệu hoặc thực hiện hành động cần thiết
                saveChanges();
            }
        });
    }

    // Phương thức xử lý khi nhấn nút "Lưu"
    public void saveChanges() {
        String maMonHoc = editTextMaMonHoc.getText().toString();
        String tenMonHoc = editTextTenMonHoc.getText().toString();
        int lyThuyet = Integer.parseInt(editTextLyThuyet.getText().toString());
        int thucHanh = Integer.parseInt(editTextThucHanh.getText().toString());


        MONHOC monHoc = new MONHOC(maMonHoc, tenMonHoc, lyThuyet, thucHanh);

        DatabaseHelper dbHelper = new DatabaseHelper(this);
        dbHelper.updateMonHoc(monHoc);
        Toast.makeText(this, "Cập nhật môn học thành công", Toast.LENGTH_SHORT).show();


        dbHelper.close();
        // Chuyển sang CaidatActivity khi nhấn vào btn_caidat
        Intent intent = new Intent(ChinhSuaMonHoc.this, MainActivity.class);
        startActivity(intent);
        finish();

    }



}
