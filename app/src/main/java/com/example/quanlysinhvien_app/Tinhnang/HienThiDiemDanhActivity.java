package com.example.quanlysinhvien_app.Tinhnang;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.quanlysinhvien_app.Database.Diemdanh;
import com.example.quanlysinhvien_app.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class HienThiDiemDanhActivity extends AppCompatActivity {

    private Spinner spinnerLop, spinnerHocKy, spinnerNgay;
    private ListView listViewDiemDanh;
    private ArrayAdapter<String> lopAdapter, hocKyAdapter, ngayAdapter;
    private DatabaseReference diemDanhRef;
    private String selectedLop, selectedHocKy, selectedNgay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hienthidiemdanh);

        spinnerLop = findViewById(R.id.spinnerLop);
        spinnerHocKy = findViewById(R.id.spinnerHocKy);
        spinnerNgay = findViewById(R.id.spinnerNgay);
        listViewDiemDanh = findViewById(R.id.listViewDiemDanh);

        lopAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item);
        lopAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerLop.setAdapter(lopAdapter);

        hocKyAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item);
        hocKyAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerHocKy.setAdapter(hocKyAdapter);

        ngayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item);
        ngayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerNgay.setAdapter(ngayAdapter);

        diemDanhRef = FirebaseDatabase.getInstance().getReference("diemdanh");

        loadLopData();

        spinnerLop.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedLop = parent.getItemAtPosition(position).toString();
                loadHocKyData(selectedLop);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        spinnerHocKy.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedHocKy = parent.getItemAtPosition(position).toString();
                loadNgayData(selectedLop, selectedHocKy);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        spinnerNgay.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedNgay = parent.getItemAtPosition(position).toString();
                loadDataToListView(selectedLop, selectedHocKy, selectedNgay);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        listViewDiemDanh.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String maSV = parent.getItemAtPosition(position).toString();
                showEditDialog(maSV);
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

            }
        });
    }

    private void loadDataToListView(String selectedLop, String selectedHocKy, String selectedNgay) {
        diemDanhRef.child(selectedLop).child(selectedHocKy).child(selectedNgay).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                List<String> diemDanhInfoList = new ArrayList<>();
                for (DataSnapshot svSnapshot : dataSnapshot.getChildren()) {
                    String maSV = svSnapshot.getKey();
                    Diemdanh diemDanh = svSnapshot.getValue(Diemdanh.class);
                    if (diemDanh != null) {
                        String tinhTrang = diemDanh.getTinhtrangdiemdanh() ? "Có" : "Vắng";
                        String diemDanhInfo = "Mã SV: " + diemDanh.getHotensv() +
                                "\nNgày Điểm Danh: " + diemDanh.getNgaydiemdanh() +
                                "\nMôn Học: " + diemDanh.getMamonhoc() +
                                "\nLớp: " + diemDanh.getMalop() +
                                "\nTình Trạng Điểm Danh: " + tinhTrang +
                                "\nGhi Chú: " + diemDanh.getGhiChu() +
                                "\n----------------------------------------";

                        diemDanhInfoList.add(diemDanhInfo);
                    }
                }
                ArrayAdapter<String> adapter = new ArrayAdapter<>(HienThiDiemDanhActivity.this, android.R.layout.simple_list_item_1, diemDanhInfoList);
                listViewDiemDanh.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void showEditDialog(final String maSVInfo) {
        // Tách thông tin từ chuỗi maSVInfo
        String[] parts = maSVInfo.split("Ngày Điểm Danh:");
        if (parts.length >= 1) {
            String[] subParts = parts[0].split("Mã SV:");
            if (subParts.length >= 2) {
                final String tenSV = subParts[1].trim(); // Lấy tên sinh viên sau khi tách chuỗi

                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                View view = getLayoutInflater().inflate(R.layout.dialog_edit_diemdanh, null);
                builder.setView(view);

                final CheckBox checkBoxTinhTrang = view.findViewById(R.id.checkBoxTinhTrang);
                final EditText editTextGhiChu = view.findViewById(R.id.editTextGhiChu);
                Button buttonLuu = view.findViewById(R.id.buttonLuu);

                final AlertDialog alertDialog = builder.create();

                buttonLuu.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        boolean tinhTrang = checkBoxTinhTrang.isChecked();
                        String ghiChu = editTextGhiChu.getText().toString();

                        // Cập nhật thông tin điểm danh vào Firebase
                        diemDanhRef.child(selectedLop).child(selectedHocKy).child(selectedNgay).child(tenSV).child("tinhtrangdiemdanh").setValue(tinhTrang);
                        diemDanhRef.child(selectedLop).child(selectedHocKy).child(selectedNgay).child(tenSV).child("ghiChu").setValue(ghiChu);

                        // Hiển thị thông báo cho người dùng biết rằng thông tin đã được cập nhật
                        Toast.makeText(HienThiDiemDanhActivity.this, "Thông tin điểm danh của " + tenSV + " đã được cập nhật!", Toast.LENGTH_SHORT).show();

                        alertDialog.dismiss(); // Đóng AlertDialog sau khi nhấn nút "Lưu"
                    }
                });

                builder.setNegativeButton("Hủy", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });

                alertDialog.show();
            }
        }
    }

}
