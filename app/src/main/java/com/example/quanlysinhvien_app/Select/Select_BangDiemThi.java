package com.example.quanlysinhvien_app.Select;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
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
        buttonApplyFilter = findViewById(R.id.button_apply_filter);
        // Khởi tạo dữ liệu cho Spinner MonHoc
        List<String> monHocList = databaseHelper.getAllMaMonHoc();
        monHocList.add(0, "Tất cả"); // Thêm lựa chọn "Tất cả" vào đầu danh sách
        ArrayAdapter<String> monHocAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, monHocList);
        monHocAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerMonHoc.setAdapter(monHocAdapter);

        // Khởi tạo dữ liệu cho Spinner LanThi
        List<String> lanThiList = databaseHelper.getAllLanThi();
        lanThiList.add(0, "Tất cả");
        ArrayAdapter<String> lanThiAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, lanThiList);
        lanThiAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerLanThi.setAdapter(lanThiAdapter);

        // Khởi tạo dữ liệu cho Spinner HocKy
        List<String> hocKyList = databaseHelper.getAllHocKy();
        hocKyList.add(0, "Tất cả");
        ArrayAdapter<String> hocKyAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, hocKyList);
        hocKyAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerHocKy.setAdapter(hocKyAdapter);

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
        // Nhận thông tin điều kiện lọc từ các Spinner
        String selectedMonHoc = spinnerMonHoc.getSelectedItem().toString();
        String selectedLanThi = spinnerLanThi.getSelectedItem().toString();
        String selectedHocKy = spinnerHocKy.getSelectedItem().toString();

        // Xây dựng câu truy vấn SQL với các điều kiện đã chọn
        StringBuilder queryBuilder = new StringBuilder("SELECT * FROM BANGDIEMTHI WHERE 1=1");

        if (!TextUtils.isEmpty(selectedMonHoc) && !selectedMonHoc.equals("Tất cả")) {
            queryBuilder.append(" AND MAMONHOC = '").append(selectedMonHoc).append("'");
        }

        if (!TextUtils.isEmpty(selectedLanThi) && !selectedLanThi.equals("Tất cả")) {
            queryBuilder.append(" AND LANTHI = '").append(selectedLanThi).append("'");
        }

        if (!TextUtils.isEmpty(selectedHocKy) && !selectedHocKy.equals("Tất cả")) {
            queryBuilder.append(" AND HOCKY = '").append(selectedHocKy).append("'");
        }

        String query = queryBuilder.toString();

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
