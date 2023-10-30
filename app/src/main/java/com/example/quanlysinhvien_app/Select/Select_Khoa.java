package com.example.quanlysinhvien_app.Select;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import com.example.quanlysinhvien_app.Adapter.KhoaAdapter;
import com.example.quanlysinhvien_app.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import java.util.ArrayList;

public class Select_Khoa extends AppCompatActivity {

    private DatabaseReference khoaRef;
    private ArrayList<String> tenKhoaList;
    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_khoa);

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        khoaRef = database.getReference("khoa");
        tenKhoaList = new ArrayList<>();
        listView = findViewById(R.id.listViewKhoa);

        khoaRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                tenKhoaList.clear();
                for (DataSnapshot khoaSnapshot : dataSnapshot.getChildren()) {
                    String tenKhoa = khoaSnapshot.child("tenkhoa").getValue(String.class);
                    tenKhoaList.add(tenKhoa);
                }

                KhoaAdapter adapter = new KhoaAdapter(Select_Khoa.this, R.layout.list_item_khoa, tenKhoaList);
                listView.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Xử lý lỗi nếu có
            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String selectedKhoa = tenKhoaList.get(position);
                getMaKhoaFromFirebase(selectedKhoa, new FirebaseCallback() {
                    @Override
                    public void onCallback(String maKhoa) {
                        Intent intent = new Intent(Select_Khoa.this, Select_Nganh.class);
                        intent.putExtra("MA_KHOA", maKhoa);
                        startActivity(intent);
                    }
                });
            }
        });
    }

    private void getMaKhoaFromFirebase(final String tenkhoa, final FirebaseCallback callback) {
        final String[] maKhoa = {""};
        DatabaseReference khoaRef = FirebaseDatabase.getInstance().getReference("khoa");
        khoaRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot khoaSnapshot : dataSnapshot.getChildren()) {
                    String tenKhoaFirebase = khoaSnapshot.child("tenkhoa").getValue(String.class);
                    if (tenKhoaFirebase != null && tenKhoaFirebase.equals(tenkhoa)) {
                        maKhoa[0] = khoaSnapshot.child("makhoa").getValue(String.class);
                        break;
                    }
                }
                callback.onCallback(maKhoa[0]);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Xử lý lỗi nếu có
            }
        });
    }

    public interface FirebaseCallback {
        void onCallback(String maKhoa);
    }
}
