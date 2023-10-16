package com.example.quanlysinhvien_app.Tinhnang;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import androidx.appcompat.app.AppCompatActivity;

import com.example.quanlysinhvien_app.Database.MonHoc;
import com.example.quanlysinhvien_app.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Themmonhoc extends AppCompatActivity {

    private EditText txtMaMonHoc, txtTenMonHoc, txt_TongTinChi;
    private Button btnLuu;
    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.nhapmonhoc);

        txtMaMonHoc = findViewById(R.id.editTextText_mamonhoc);
        txtTenMonHoc = findViewById(R.id.editTextText_tenmonhoc);
        txt_TongTinChi = findViewById(R.id.editTextText_tongtinchi);
        btnLuu = findViewById(R.id.button_luu_nhapmonhoc);
        databaseReference = FirebaseDatabase.getInstance().getReference("monhoc");

        btnLuu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String maMonHoc = txtMaMonHoc.getText().toString().trim();
                String tenMonHoc = txtTenMonHoc.getText().toString().trim();
                String tongTinChi = txt_TongTinChi.getText().toString().trim();

                if (!maMonHoc.isEmpty() && !tenMonHoc.isEmpty() && !tongTinChi.isEmpty()) {
                    // Tạo đối tượng MonHoc để đẩy lên Firebase
                    MonHoc monHoc = new MonHoc(maMonHoc, tenMonHoc, Integer.parseInt(tongTinChi));

                    // Thực hiện việc ghi dữ liệu lên Firebase
                    databaseReference.child(maMonHoc).setValue(monHoc);
                } else {
                    // Xử lý trường hợp khi có trường dữ liệu chưa được nhập
                }
            }
        });
    }
}
