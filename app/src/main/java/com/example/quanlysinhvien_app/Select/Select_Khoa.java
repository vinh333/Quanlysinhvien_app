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
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class Select_Khoa extends AppCompatActivity {
    private ListView khoaListView;
    private DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.danhsachkhoa);

        khoaListView = findViewById(R.id.list_khoa);
        mDatabase = FirebaseDatabase.getInstance().getReference();

        DatabaseReference khoaRef = FirebaseDatabase.getInstance().getReference().child("khoa");

        khoaRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot khoaDataSnapshot) {
                List<Khoa> khoaList = new ArrayList<>();

                for (DataSnapshot khoaSnapshot : khoaDataSnapshot.getChildren()) {
                    Khoa khoa = khoaSnapshot.getValue(Khoa.class);
                    khoaList.add(khoa);

                    // Lấy dữ liệu ngành học từ Firebase Realtime Database dựa trên mã khoa
                    DatabaseReference nganhRef = FirebaseDatabase.getInstance().getReference().child("nganhhoc");
                    Query query = nganhRef.orderByChild("manganh");

                    query.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot nganhDataSnapshot) {
                            List<NganhHoc> nganhList = new ArrayList<>();

                            for (DataSnapshot nganhSnapshot : nganhDataSnapshot.getChildren()) {
                                NganhHoc nganhHoc = nganhSnapshot.getValue(NganhHoc.class);
                                nganhList.add(nganhHoc);
                            }

                            // Tạo Adapter và thiết lập cho ListView Khoa
                            KhoaAdapter khoaAdapter = new KhoaAdapter(Select_Khoa.this, R.layout.item_khoa, khoaList, nganhList);
                            khoaListView.setAdapter(khoaAdapter);
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {
                            // Xử lý khi có lỗi truy vấn dữ liệu
                        }
                    });

                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Xử lý lỗi nếu cần
            }
        });

    }
}
