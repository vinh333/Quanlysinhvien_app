package com.example.quanlysinhvien_app.Select;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.quanlysinhvien_app.Adapter.LopAdapter;
import com.example.quanlysinhvien_app.Database.LOP;
import com.example.quanlysinhvien_app.DatabaseHelper;
import com.example.quanlysinhvien_app.R;

import java.util.List;

public class Select_Lop extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_class_list);
        Intent intent = getIntent();

        if ("All".equals(intent.getStringExtra("CHECK"))) {
            // Khởi tạo DatabaseHelper và lấy danh sách lớp
            DatabaseHelper dbHelper = new DatabaseHelper(this);
            List<LOP> classList = dbHelper.getAllClasses();

            // Khởi tạo Adapter và gán nó cho ListView
            LopAdapter adapter = new LopAdapter(this, classList);
            ListView listViewClasses = findViewById(R.id.listViewClasses);
            listViewClasses.setAdapter(adapter);

            // Set an OnItemClickListener on the ListView
            listViewClasses.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    // Get the selected LOP object
                    LOP selectedLop = classList.get(position);

                    // Intent to start the Makhoa activity, passing the class code
                    Intent intent = new Intent(Select_Lop.this, Select_Sinhvien.class);
                    intent.putExtra("MALOP", selectedLop.getMaLop());
                    // Add more data if needed

                    // Start the Makhoa activity
                    startActivity(intent);
                }
            });
        } else {
            // Lấy maKhoa từ Intent
            String maKhoa = intent.getStringExtra("maKhoa");

            // Khởi tạo DatabaseHelper và lấy danh sách lớp theo maKhoa
            DatabaseHelper dbHelper = new DatabaseHelper(this);
            List<LOP> classList = dbHelper.getClassesByMaKhoa(maKhoa);

            // Khởi tạo Adapter và gán nó cho ListView
            LopAdapter adapter = new LopAdapter(this, classList);
            ListView listViewClasses = findViewById(R.id.listViewClasses);
            listViewClasses.setAdapter(adapter);

            // Set an OnItemClickListener on the ListView
            listViewClasses.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    // Get the selected LOP object
                    LOP selectedLop = classList.get(position);

                    // Intent to start the Makhoa activity, passing the class code
                    Intent intent = new Intent(Select_Lop.this, Select_Sinhvien.class);
                    intent.putExtra("MALOP", selectedLop.getMaLop());
                    // Add more data if needed

                    // Start the Makhoa activity
                    startActivity(intent);
                }
            });
        }
    }
}
