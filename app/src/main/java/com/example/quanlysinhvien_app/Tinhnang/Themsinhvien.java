package com.example.quanlysinhvien_app.Tinhnang;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import com.example.quanlysinhvien_app.Database.SinhVien;
import com.example.quanlysinhvien_app.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

public class Themsinhvien extends AppCompatActivity {

    private ImageView imageView;
    private EditText txtMasv, txtHoTen, txtGioiTinh, txtNgaySinh, txtNoiSinh,
            txtDiaChi, txtMaTinh, txtQuan, txtMaLop, txtHocBong;
    private Button btnLuu;
    private static final int PICK_IMAGE_REQUEST = 1;
    private Uri imageUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.nhapthongtinsv);

        // Ánh xạ các phần tử từ layout XML
        imageView = findViewById(R.id.imgAvatar);
        txtMasv = findViewById(R.id.txtmasv);
        txtHoTen = findViewById(R.id.txtho);
        txtGioiTinh = findViewById(R.id.txtgioitinh);
        txtNgaySinh = findViewById(R.id.txtngaysinh);
        txtNoiSinh = findViewById(R.id.txtnoisinh);
        txtDiaChi = findViewById(R.id.editTextText5);
        txtMaTinh = findViewById(R.id.txtmatinh);
        txtQuan = findViewById(R.id.txtquan);
        txtMaLop = findViewById(R.id.txtmalop);
        txtHocBong = findViewById(R.id.txthocbong);
        btnLuu = findViewById(R.id.button_luu);

        // Thêm sự kiện click cho ImageView để chọn hình ảnh
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openGallery();
            }
        });

        // Thêm sự kiện click cho nút Lưu
        btnLuu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveDataToFirebase();
            }
        });
    }

    private void openGallery() {
        Intent galleryIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(galleryIntent, PICK_IMAGE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            imageUri = data.getData();
            imageView.setImageURI(imageUri);
        }
    }

    private void saveDataToFirebase() {
        String masv = txtMasv.getText().toString();
        String hotensv = txtHoTen.getText().toString();
        String diaChi = txtDiaChi.getText().toString();
        String ngaySinh = txtNgaySinh.getText().toString();
        String noiSinh = txtNoiSinh.getText().toString();
        String maTinh = txtMaTinh.getText().toString();
        String quan = txtQuan.getText().toString();
        boolean gioitinh = Boolean.parseBoolean(txtGioiTinh.getText().toString());

        String maLop = txtMaLop.getText().toString();
        int hocBong = Integer.parseInt(txtHocBong.getText().toString());

        if (!masv.isEmpty() && !hotensv.isEmpty() && imageUri != null && !ngaySinh.isEmpty() && !noiSinh.isEmpty() &&
                !diaChi.isEmpty() && !maTinh.isEmpty() && !quan.isEmpty() && !maLop.isEmpty()) {

            // Tải hình ảnh lên Firebase Storage
            StorageReference storageRef = FirebaseStorage.getInstance().getReference("avatars").child(masv + ".jpg");
            storageRef.putFile(imageUri)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            // Lấy đường dẫn đến hình ảnh đã tải lên Firebase Storage
                            storageRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri uri) {
                                    String avatarUrl = uri.toString();
                                    // Tạo đối tượng SinhVien để đẩy lên Firebase Realtime Database
                                    SinhVien sinhVien = new SinhVien(masv, hotensv, gioitinh, diaChi, hocBong, maLop, maTinh, ngaySinh, noiSinh, quan, avatarUrl);


                                    // Thực hiện việc ghi dữ liệu lên Firebase Realtime Database
                                    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("sinhvien");
                                    databaseReference.child(masv).setValue(sinhVien)
                                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                                @Override
                                                public void onSuccess(Void aVoid) {
                                                    // Xử lý khi dữ liệu được ghi thành công
                                                }
                                            })
                                            .addOnFailureListener(new OnFailureListener() {
                                                @Override
                                                public void onFailure(@NonNull Exception e) {
                                                    // Xử lý khi việc ghi dữ liệu không thành công
                                                }
                                            });
                                }
                            });
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception exception) {
                            // Xử lý khi việc tải hình ảnh lên không thành công
                        }
                    });
        }
    }
}
