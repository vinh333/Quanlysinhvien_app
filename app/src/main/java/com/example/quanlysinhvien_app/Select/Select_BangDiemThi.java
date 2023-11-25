package com.example.quanlysinhvien_app.Select;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;

import com.example.quanlysinhvien_app.Adapter.BangDiemAdapter;
import com.example.quanlysinhvien_app.Database.BANGDIEMTHI;
import com.example.quanlysinhvien_app.DatabaseHelper;
import com.example.quanlysinhvien_app.R;
import com.example.quanlysinhvien_app.Tinhnang.ChinhSuaDiem;

import java.util.ArrayList;
import java.util.List;

public class Select_BangDiemThi extends AppCompatActivity {

    private ListView listViewBangDiem;

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

        listViewBangDiem = findViewById(R.id.list_view_bang_diem);
        bangDiemList = new ArrayList<>();
        databaseHelper = new DatabaseHelper(this);

        bangDiemAdapter = new BangDiemAdapter(this, bangDiemList);
        listViewBangDiem.setAdapter(bangDiemAdapter);

        // Initialize UI components for filtering
        spinnerMonHoc = findViewById(R.id.spinner_mon_hoc);
        spinnerLanThi = findViewById(R.id.spinner_lan_thi);
        spinnerHocKy = findViewById(R.id.spinner_hoc_ky);
        buttonApplyFilter = findViewById(R.id.button_apply_filter);

        // Initialize data for MonHoc Spinner
        List<String> monHocList = databaseHelper.getAllMaMonHoc();
        monHocList.add(0, "Tất cả");
        ArrayAdapter<String> monHocAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, monHocList);
        monHocAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerMonHoc.setAdapter(monHocAdapter);

        // Initialize data for LanThi Spinner
        List<String> lanThiList = databaseHelper.getAllLanThi();
        lanThiList.add(0, "Tất cả");
        ArrayAdapter<String> lanThiAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, lanThiList);
        lanThiAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerLanThi.setAdapter(lanThiAdapter);

        // Initialize data for HocKy Spinner
        List<String> hocKyList = databaseHelper.getAllHocKy();
        hocKyList.add(0, "Tất cả");
        ArrayAdapter<String> hocKyAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, hocKyList);
        hocKyAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerHocKy.setAdapter(hocKyAdapter);

        // Handle the event when the user applies the filter
        buttonApplyFilter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handleFilterOptions();
            }
        });

        // Perform the query and display the exam scores
        loadBangDiem();

        listViewBangDiem.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // Lấy BANGDIEMTHI tại vị trí được chọn
                BANGDIEMTHI selectedBangDiem = bangDiemAdapter.getItemAtPosition(position);

                // Tạo Intent để mở Activity mới
                Intent intent = new Intent(Select_BangDiemThi.this, ChinhSuaDiem.class);

                // Đóng gói dữ liệu vào Intent
                intent.putExtra("MASV", selectedBangDiem.getMaSV());
                intent.putExtra("MAMON", selectedBangDiem.getMaMonHoc());
                intent.putExtra("LANTHI", selectedBangDiem.getLanThi());
                intent.putExtra("HOCKY", selectedBangDiem.getHocKy());
                intent.putExtra("DIEM", selectedBangDiem.getDiem());

                // Mở Activity mới
                startActivity(intent);
            }
        });

    }

    private void loadBangDiem() {
        // Query the database to get the list of exam scores
        bangDiemList.clear();
        bangDiemList.addAll(databaseHelper.getAllBangDiem());

        // Update the list in the Adapter
        bangDiemAdapter.notifyDataSetChanged();
    }

    private void handleFilterOptions() {
        // Get filter conditions from Spinners
        String selectedMonHoc = spinnerMonHoc.getSelectedItem().toString();
        String selectedLanThi = spinnerLanThi.getSelectedItem().toString();
        String selectedHocKy = spinnerHocKy.getSelectedItem().toString();

        // Build the SQL query with selected conditions
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

        // Perform the query and update the UI
        executeQueryAndUpdateUI(query);
    }

    private void executeQueryAndUpdateUI(String query) {
        // Perform a new database query with the selected conditions
        bangDiemList.clear();
        bangDiemList.addAll(databaseHelper.executeCustomQuery(query));

        // Update the list in the Adapter
        bangDiemAdapter.notifyDataSetChanged();
    }
}
