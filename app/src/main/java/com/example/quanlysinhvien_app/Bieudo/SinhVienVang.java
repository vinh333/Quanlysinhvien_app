package com.example.quanlysinhvien_app.Bieudo;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.quanlysinhvien_app.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class SinhVienVang extends AppCompatActivity {
    private Spinner spinnerLop, spinnerHocKy, spinnerNgay;
    private ArrayAdapter<String> lopAdapter, hocKyAdapter, ngayAdapter;
    private DatabaseReference diemDanhRef;
    private int tonghocsinh, hocsinhvang;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bieudo_sinhvienvang);

        spinnerLop = findViewById(R.id.spinnerLop2);
        spinnerHocKy = findViewById(R.id.spinnerHocKy2);
        spinnerNgay = findViewById(R.id.spinnerNgay2);
        diemDanhRef = FirebaseDatabase.getInstance().getReference("diemdanh");

        lopAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item);
        lopAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerLop.setAdapter(lopAdapter);

        hocKyAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item);
        hocKyAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerHocKy.setAdapter(hocKyAdapter);

        ngayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item);
        ngayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerNgay.setAdapter(ngayAdapter);

        loadLopData();

        spinnerLop.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedLop = parent.getItemAtPosition(position).toString();
                loadHocKyData(selectedLop);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        spinnerHocKy.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedLop = spinnerLop.getSelectedItem().toString();
                String selectedHocKy = parent.getItemAtPosition(position).toString();
                loadNgayData(selectedLop, selectedHocKy);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        spinnerNgay.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedLop = spinnerLop.getSelectedItem().toString();
                String selectedHocKy = spinnerHocKy.getSelectedItem().toString();
                String selectedNgay = parent.getItemAtPosition(position).toString();
                loadDataToListView(selectedLop, selectedHocKy, selectedNgay);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void loadLopData() {
        diemDanhRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                List<String> lopList = new ArrayList<>();
                for (DataSnapshot lopSnapshot : dataSnapshot.getChildren()) {
                    lopList.add(lopSnapshot.getKey());
                }
                lopAdapter.clear();
                lopAdapter.addAll(lopList);
                lopAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Handle error
            }
        });
    }

    private void loadHocKyData(String selectedLop) {
        diemDanhRef.child(selectedLop).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                List<String> hocKyList = new ArrayList<>();
                for (DataSnapshot hocKySnapshot : dataSnapshot.getChildren()) {
                    hocKyList.add(hocKySnapshot.getKey());
                }
                hocKyAdapter.clear();
                hocKyAdapter.addAll(hocKyList);
                hocKyAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Handle error
            }
        });
    }

    private void loadNgayData(String selectedLop, String selectedHocKy) {
        diemDanhRef.child(selectedLop).child(selectedHocKy).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                List<String> ngayList = new ArrayList<>();
                for (DataSnapshot ngaySnapshot : dataSnapshot.getChildren()) {
                    ngayList.add(ngaySnapshot.getKey());
                }
                ngayAdapter.clear();
                ngayAdapter.addAll(ngayList);
                ngayAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Handle error
            }
        });
    }

    private void loadDataToListView(String selectedLop, String selectedHocKy, String selectedNgay) {
        diemDanhRef.child(selectedLop).child(selectedHocKy).child(selectedNgay).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                tonghocsinh = 0;
                hocsinhvang = 0;

                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    boolean tinhTrangDiemDanh = snapshot.child("tinhtrangdiemdanh").getValue(Boolean.class);
                    tonghocsinh++;
                    if (!tinhTrangDiemDanh) {
                        hocsinhvang++;
                    }
                }

                // Now you can use tonghocsinh and hocsinhvang as needed.
                Log.d("Absent Students", "Total students: " + tonghocsinh + ", Absent students: " + hocsinhvang);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Handle error
            }
        });
    }
}
