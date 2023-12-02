package com.example.quanlysinhvien_app.Select;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.quanlysinhvien_app.Database.SINHVIEN;
import com.example.quanlysinhvien_app.DatabaseHelper;
import com.example.quanlysinhvien_app.R;
import com.example.quanlysinhvien_app.Tinhnang.ChinhSuaThongTinSinhVien;

import java.util.Random;

public class Select_Thongtinsinhvien extends AppCompatActivity {
    private int[] randomDrawables_Nam = {R.drawable.nam__1_, R.drawable.nam__2_, R.drawable.nam__3_};
    private int[] randomDrawables_Nu = { R.drawable.nu__1_, R.drawable.nu__2_,R.drawable.nu__3_};
    private TextView txtEditThongTinSinhVien, txtMaSV, txtTenSV, txtGioiTinh, txtNgaySinh, txtNoiSinh, txtDiaChi, txtMaLop, txtHocBong;
    private  String  maSV;
    private ImageView imageView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_thongtinsinhvien);

        // Ánh xạ các TextView từ layout
        txtEditThongTinSinhVien = findViewById(R.id.txt_edit_thongtinsinhvien);
        txtMaSV = findViewById(R.id.txt_tbmon);
        txtTenSV = findViewById(R.id.txt_tensv);
        txtGioiTinh = findViewById(R.id.txt_gioitinh);
        txtNgaySinh = findViewById(R.id.txt_ngaysinh);
        txtNoiSinh = findViewById(R.id.txt_noisinh);
        txtDiaChi = findViewById(R.id.txt_diachi);
        txtMaLop = findViewById(R.id.txt_malop);
        txtHocBong = findViewById(R.id.txt_hocbong);
        TextView txtEditThongTinSinhVien = findViewById(R.id.txt_edit_thongtinsinhvien);
        imageView = findViewById(R.id.imgAvatar);
        // Lấy mã sinh viên từ Intent
        Intent intent = getIntent();
        if (intent != null) {
             maSV = intent.getStringExtra("MASV");
            Log.d("test", maSV);

            // Hiển thị thông tin sinh viên dựa trên mã sinh viên
            showSinhVienDetails(maSV);
        }

        // Lắng nghe sự kiện nhấn vào TextView txt_edit_thongtinsinhvien
        txtEditThongTinSinhVien.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Khi nhấn vào, chuyển sang trang ChinhSuaThongTinSinhVien
                Intent chinhSuaIntent = new Intent(Select_Thongtinsinhvien.this, ChinhSuaThongTinSinhVien.class);
                chinhSuaIntent.putExtra("MASV", maSV); // Truyền mã sinh viên sang trang ChinhSuaThongTinSinhVien
                startActivity(chinhSuaIntent);
            }
        });


        // ... (các phần khác)
    }

    private void showSinhVienDetails(String maSV) {
        // Lấy thông tin sinh viên từ cơ sở dữ liệu
        DatabaseHelper databaseHelper = new DatabaseHelper(this);
        SINHVIEN sinhVien = databaseHelper.getSinhVienByMaSV(maSV);

        // Kiểm tra xem sinh viên có tồn tại không
        if (sinhVien != null) {
            // Hiển thị thông tin sinh viên lên giao diện
            txtMaSV.setText(sinhVien.getMaSV());
            txtTenSV.setText(sinhVien.getHoTen());
            txtGioiTinh.setText(sinhVien.isGioiTinh() ? "Nam" : "Nữ");
            txtNgaySinh.setText(sinhVien.getNgaySinh());
            txtNoiSinh.setText(sinhVien.getNoiSinh());
            txtDiaChi.setText(sinhVien.getDiaChi());
            txtMaLop.setText(sinhVien.getMaLop());
            txtHocBong.setText(String.valueOf(sinhVien.getHocBong()));
            // Chọn ngẫu nhiên một drawable từ mảng randomDrawables
            boolean gioitinh = sinhVien.isGioiTinh();
            if (gioitinh){
                int randomIndex = new Random().nextInt(randomDrawables_Nam.length);
                imageView.setImageResource(randomDrawables_Nam[randomIndex]);


            }else {
                int randomIndex = new Random().nextInt(randomDrawables_Nu.length);
                imageView.setImageResource(randomDrawables_Nu[randomIndex]);


            }
            // Nếu có thông tin về năm học, thì bạn cần có một phương thức tương ứng để lấy thông tin này từ cơ sở dữ liệu.
            // txtNamHoc.setText(databaseHelper.getNamHocByMaSV(maSV));
        } else {
            // Nếu không tìm thấy sinh viên, bạn có thể xử lý thông báo hoặc thực hiện các hành động khác.
            Toast.makeText(this, "Không tìm thấy thông tin sinh viên", Toast.LENGTH_SHORT).show();
        }
    }

    // ... (các phần khác)
}

