package com.example.quanlysinhvien_app.Select;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.quanlysinhvien_app.Adapter.BangDiemAdapter;
import com.example.quanlysinhvien_app.Database.BANGDIEMTHI;
import com.example.quanlysinhvien_app.DatabaseHelper;
import com.example.quanlysinhvien_app.R;

import java.util.ArrayList;
import java.util.List;

public class Select_BangDiemThi extends AppCompatActivity {

    private RecyclerView recyclerView;
    private BangDiemAdapter bangDiemAdapter;
    private List<BANGDIEMTHI> bangDiemList;
    private DatabaseHelper databaseHelper;

    private Spinner spinnerMonHoc;
    private Spinner spinnerLanThi;
    private Spinner spinnerHocKy;
    private EditText editTextDiem;
    private Button buttonApplyFilter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bang_diem);

        recyclerView = findViewById(R.id.recycler_view_bang_diem);
        bangDiemList = new ArrayList<>();
        databaseHelper = new DatabaseHelper(this);

        bangDiemAdapter = new BangDiemAdapter(bangDiemList);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(bangDiemAdapter);

        // Khởi tạo các thành phần giao diện cho việc lọc
        spinnerMonHoc = findViewById(R.id.spinner_mon_hoc);
        spinnerLanThi = findViewById(R.id.spinner_lan_thi);
        spinnerHocKy = findViewById(R.id.spinner_hoc_ky);
        editTextDiem = findViewById(R.id.edit_text_diem);
        buttonApplyFilter = findViewById(R.id.button_apply_filter);

        // Xử lý sự kiện khi người dùng áp dụng bộ lọc
        buttonApplyFilter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handleFilterOptions();
            }
        });

        // Thực hiện truy vấn và hiển thị bảng điểm
        loadBangDiem();
    }

    private void loadBangDiem() {
        // Truy vấn cơ sở dữ liệu để lấy danh sách điểm
        bangDiemList.clear();
        bangDiemList.addAll(databaseHelper.getAllBangDiem());

        // Cập nhật danh sách trong Adapter
        bangDiemAdapter.notifyDataSetChanged();
    }

    private void handleFilterOptions() {
        // Nhận thông tin điều kiện lọc từ các thành phần giao diện
        String selectedMonHoc = spinnerMonHoc.getSelectedItem().toString();
        String selectedLanThi = spinnerLanThi.getSelectedItem().toString();
        String selectedHocKy = spinnerHocKy.getSelectedItem().toString();
        String enteredDiem = editTextDiem.getText().toString();

        // Xây dựng câu truy vấn SQL với các điều kiện đã chọn
        String query = "SELECT * FROM BANGDIEMTHI WHERE 1=1"; // 1=1 để kết hợp điều kiện mà không cần kiểm tra điều kiện trước

        if (!TextUtils.isEmpty(selectedMonHoc)) {
            query += " AND MAMONHOC = '" + selectedMonHoc + "'";
        }

        if (!TextUtils.isEmpty(selectedLanThi)) {
            query += " AND LANTHI = '" + selectedLanThi + "'";
        }

        if (!TextUtils.isEmpty(selectedHocKy)) {
            query += " AND HOCKY = '" + selectedHocKy + "'";
        }

        if (!TextUtils.isEmpty(enteredDiem)) {
            query += " AND DIEM >= " + enteredDiem;
        }

        // Thực hiện truy vấn và cập nhật giao diện
        executeQueryAndUpdateUI(query);
    }

    private void executeQueryAndUpdateUI(String query) {
        // Thực hiện câu truy vấn cơ sở dữ liệu mới với các điều kiện đã chọn
        bangDiemList.clear();
        bangDiemList.addAll(databaseHelper.executeCustomQuery(query));

        // Cập nhật danh sách trong Adapter
        bangDiemAdapter.notifyDataSetChanged();
    }
}

