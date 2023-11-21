package com.example.quanlysinhvien_app.Select;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import com.example.quanlysinhvien_app.Adapter.SinhVienAdapter;
import com.example.quanlysinhvien_app.DatabaseHelper;
import com.example.quanlysinhvien_app.Database.SINHVIEN;
import com.example.quanlysinhvien_app.R;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class Select_Sinhvien extends AppCompatActivity {

    private List<SINHVIEN> sinhVienList;
    private SinhVienAdapter adapter;
    private ListView listView;
    private boolean isAscendingOrder = true; // Biến để theo dõi trạng thái sắp xếp

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.danhsachsinhvien);

        // Lấy danh sách sinh viên từ cơ sở dữ liệu hoặc nguồn dữ liệu khác
        DatabaseHelper databaseHelper = new DatabaseHelper(this);
        sinhVienList = databaseHelper.getAllSinhVien();

        // Tìm ListView bằng ID
        listView = findViewById(R.id.listView_Sinhvien);

        // Tạo một instance của SinhVienAdapter và đặt adapter cho ListView
        adapter = new SinhVienAdapter(this, sinhVienList);
        listView.setAdapter(adapter);

        // Tìm nút lọc sinh viên theo tên và mã sinh viên bằng ID
        TextView btnLocSV = findViewById(R.id.btn_locsv);
        // Bổ sung sự kiện lắng nghe cho mỗi mục trong ListView
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // Lấy thông tin của sinh viên được chọn
                SINHVIEN selectedSinhVien = sinhVienList.get(position);

                // Chuyển đến màn hình thông tin sinh viên và truyền mã sinh viên
                Intent intent = new Intent(Select_Sinhvien.this, Select_Thongtinsinhvien.class);
                intent.putExtra("MASV", selectedSinhVien.getMaSV());
                startActivity(intent);
            }
        });
        btnLocSV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Xử lý sự kiện khi nút lọc được nhấn
                // Sắp xếp dữ liệu từ A đến Z hoặc từ Z đến A tùy vào giá trị của isAscendingOrder
                Collections.sort(sinhVienList, new Comparator<SINHVIEN>() {
                    @Override
                    public int compare(SINHVIEN sv1, SINHVIEN sv2) {
                        int result;
                        if (isAscendingOrder) {
                            result = sv1.getMaSV().compareToIgnoreCase(sv2.getMaSV());
                        } else {
                            result = sv2.getMaSV().compareToIgnoreCase(sv1.getMaSV());
                        }
                        return result;
                    }
                });

                // Đảo ngược giá trị của biến isAscendingOrder để sắp xếp ngược lại ở lần tiếp theo
                isAscendingOrder = !isAscendingOrder;

                // Cập nhật dữ liệu trong adapter và hiển thị lại ListView
                adapter.notifyDataSetChanged();
            }
        });
    }


}

