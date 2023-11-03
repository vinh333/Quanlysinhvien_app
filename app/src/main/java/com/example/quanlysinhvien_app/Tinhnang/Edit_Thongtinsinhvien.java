package com.example.quanlysinhvien_app.Tinhnang;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.quanlysinhvien_app.Database.SinhVien;
import com.example.quanlysinhvien_app.R;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

public class Edit_Thongtinsinhvien extends AppCompatActivity {
    private EditText editMaSV, editTenSV, editNgaySinh, editDiaChi, editGioiTinh, editMaLop, editNoiSinh, editNamHoc;
    private Button btnLuu;
    private ImageView imageViewAvatar;
    private DatabaseReference databaseReference;
    private StorageReference storageReference;
    private String tenKhoaHoc;
    private static final int PICK_IMAGE_REQUEST = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_thongtinsinhvien);

        editMaSV = findViewById(R.id.editMaSV);
        editTenSV = findViewById(R.id.editTenSV);
        editNgaySinh = findViewById(R.id.editNgaySinh);
        editDiaChi = findViewById(R.id.editDiaChi);
        editGioiTinh = findViewById(R.id.editGioiTinh);
        editMaLop = findViewById(R.id.editMaLop);
        editNoiSinh = findViewById(R.id.editNoiSinh);
        editNamHoc = findViewById(R.id.editNamHoc);
        btnLuu = findViewById(R.id.btnLuu);
        imageViewAvatar = findViewById(R.id.imageViewAvatar);

        // Nhận mã sinh viên từ Intent
        Intent intent = getIntent();
        if (intent != null) {
            final String masv = intent.getStringExtra("masv");

            // Kết nối tới Firebase Database
            databaseReference = FirebaseDatabase.getInstance().getReference("sinhvien").child(masv);

            // Kết nối tới Firebase Storage
            storageReference = FirebaseStorage.getInstance().getReference("avatars").child(masv + ".jpg");

            // Lấy dữ liệu từ Firebase và hiển thị lên các EditText và ImageView
            databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    if (dataSnapshot.exists()) {
                        SinhVien sinhVien = dataSnapshot.getValue(SinhVien.class);
                        if (sinhVien != null) {
                            // Hiển thị thông tin sinh viên
                            editMaSV.setText(sinhVien.getMasv());
                            editTenSV.setText(sinhVien.getHotensv());
                            editNgaySinh.setText(sinhVien.getNgaysinh());
                            editDiaChi.setText(sinhVien.getDiachi());
                            editGioiTinh.setText(sinhVien.getGioitinh() ? "Nam" : "Nữ");
                            editMaLop.setText(sinhVien.getMalop());
                            editNoiSinh.setText(sinhVien.getNoisinh());

                            // Tải và hiển thị avatar từ Firebase Storage
                            Picasso.get().load(sinhVien.getAvatarUrl()).into(imageViewAvatar);
                            Log.d("AvatarURL","sdasd");

                        }
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    // Xử lý khi truy vấn bị hủy bỏ hoặc thất bại
                }
            });

            // Xử lý sự kiện khi người dùng nhấn nút "Lưu"
            btnLuu.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // Lấy dữ liệu từ các EditText
                    String tenSV = editTenSV.getText().toString();
                    String ngaySinh = editNgaySinh.getText().toString();
                    String diaChi = editDiaChi.getText().toString();
                    boolean gioiTinh = editGioiTinh.getText().toString().equalsIgnoreCase("Nam");
                    String maLop = editMaLop.getText().toString();
                    String noiSinh = editNoiSinh.getText().toString();
                    String namHoc = editNamHoc.getText().toString();

                    // Cập nhật dữ liệu lên Firebase
                    databaseReference.child("hotensv").setValue(tenSV);
                    databaseReference.child("ngaysinh").setValue(ngaySinh);
                    databaseReference.child("diachi").setValue(diaChi);
                    databaseReference.child("gioitinh").setValue(gioiTinh);
                    databaseReference.child("malop").setValue(maLop);
                    databaseReference.child("noisinh").setValue(noiSinh);
                    databaseReference.child("namhoc").setValue(namHoc);

                    // Hiển thị thông báo cho người dùng biết rằng dữ liệu đã được cập nhật
                    Toast.makeText(Edit_Thongtinsinhvien.this, "Thông tin sinh viên đã được cập nhật!", Toast.LENGTH_SHORT).show();
                }
            });

            // Xử lý sự kiện khi người dùng nhấn vào ImageView để chọn hình ảnh mới
            imageViewAvatar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    openGallery();
                }
            });
        }
    }

    private void openGallery() {
        Intent galleryIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(galleryIntent, PICK_IMAGE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            Uri imageUri = data.getData();
            // Tải hình ảnh lên Firebase Storage và cập nhật đường dẫn avatar trong Firebase Realtime Database
            storageReference.putFile(imageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    // Lấy đường dẫn của hình ảnh đã tải lên Firebase Storage
                    storageReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            String avatarUrl = uri.toString();
                            // Cập nhật đường dẫn avatar trong Firebase Realtime Database
                            databaseReference.child("avatarUrl").setValue(avatarUrl);
                            // Hiển thị hình ảnh mới trong ImageView sử dụng Picasso
                            Picasso.get().load(avatarUrl).into(imageViewAvatar);
                            Toast.makeText(Edit_Thongtinsinhvien.this, "Hình ảnh đã được cập nhật!", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            });
        }
    }
}
