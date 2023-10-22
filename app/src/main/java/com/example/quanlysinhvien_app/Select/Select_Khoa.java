package com.example.quanlysinhvien_app.Select;

import android.os.Bundle;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.quanlysinhvien_app.Adapter.KhoaAdapter;
import com.example.quanlysinhvien_app.Adapter.NganhAdapter;
import com.example.quanlysinhvien_app.Database.Khoa;
import com.example.quanlysinhvien_app.Database.NganhHoc;
import com.example.quanlysinhvien_app.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class Select_Khoa extends AppCompatActivity {
    private ListView khoaListView, nganhListView;
    private DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.danhsachkhoa);

        khoaListView = findViewById(R.id.list_khoa);
//        nganhListView = findViewById(R.id.list_nganh);
        mDatabase = FirebaseDatabase.getInstance().getReference();

        // Lấy dữ liệu Khoa từ Firebase Realtime Database
        mDatabase.child("khoa").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                List<Khoa> khoaList = new ArrayList<>();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Khoa khoa = snapshot.getValue(Khoa.class);
                    khoaList.add(khoa);
                }

                // Tạo Adapter và thiết lập cho ListView Khoa
                KhoaAdapter khoaAdapter = new KhoaAdapter(Select_Khoa.this, R.layout.item_khoa, khoaList);
                khoaListView.setAdapter(khoaAdapter);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Xử lý lỗi nếu cần
            }
        });

        // Lấy dữ liệu NganhHoc từ Firebase Realtime Database
        mDatabase.child("nganhhoc").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                List<NganhHoc> nganhList = new ArrayList<>();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    NganhHoc nganhHoc = snapshot.getValue(NganhHoc.class);
                    nganhList.add(nganhHoc);
                }

                // Tạo Adapter và thiết lập cho ListView NganhHoc
                NganhAdapter nganhAdapter = new NganhAdapter(Select_Khoa.this, R.layout.item_nganh, nganhList);
                nganhListView.setAdapter(nganhAdapter);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Xử lý lỗi nếu cần
            }
        });
    }
}
