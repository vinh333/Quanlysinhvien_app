package com.example.quanlysinhvien_app.Select;

import android.os.Bundle;
import android.widget.ListView;
import androidx.appcompat.app.AppCompatActivity;
import com.example.quanlysinhvien_app.Adapter.SinhVienAdapter;
import com.example.quanlysinhvien_app.DatabaseHelper;
import com.example.quanlysinhvien_app.Database.SINHVIEN;
import com.example.quanlysinhvien_app.R;
import java.util.List;

public class Select_Sinhvien extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.danhsachsinhvien);

        // Lấy danh sách sinh viên từ cơ sở dữ liệu hoặc nguồn dữ liệu khác
        DatabaseHelper databaseHelper = new DatabaseHelper(this);
        List<SINHVIEN> sinhVienList = databaseHelper.getAllSinhVien();

        // Tìm ListView bằng ID
        ListView listView = findViewById(R.id.listView_Sinhvien);

        // Tạo một instance của SinhVienAdapter và đặt adapter cho ListView
        SinhVienAdapter adapter = new SinhVienAdapter(this, sinhVienList);
        listView.setAdapter(adapter);
    }
}
