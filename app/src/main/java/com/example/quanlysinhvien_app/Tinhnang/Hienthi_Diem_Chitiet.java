package com.example.quanlysinhvien_app.Tinhnang;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import androidx.appcompat.app.AppCompatActivity;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Hienthi_Diem_Chitiet extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent intent = getIntent();
        if (intent != null) {
            String monhoc = intent.getStringExtra("monhoc");

            // Kết nối tới Firebase Realtime Database
            DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("monhoc");

            // Lắng nghe sự kiện khi dữ liệu thay đổi trên Firebase
            databaseReference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        // Lấy giá trị của thuộc tính "tenmonhoc" từ mỗi nút con
                        String tenmonhocFirebase = snapshot.child("tenmonhoc").getValue(String.class);

                        // So sánh giá trị monhoc với tenmonhocFirebase
                        if (tenmonhocFirebase != null && tenmonhocFirebase.equals(monhoc)) {
                            // Nếu trùng, lấy giá trị của "tongtinchi" tương ứng
                            Long tongtinchi = snapshot.child("tongtinchi").getValue(Long.class);
                            Log.d("Hienthi_Diem", "monhoc: " + monhoc + ", tongtinchi: " + tongtinchi);
                            break;
                        }
                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                    // Xử lý khi có lỗi xảy ra khi đọc dữ liệu từ Firebase
                }
            });
        }
    }
}

