package com.example.quanlysinhvien_app.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.quanlysinhvien_app.Database.Hienthidiem_Custom;
import com.example.quanlysinhvien_app.R;

import java.util.List;

public class Hienthidiem_CustomAdapter extends ArrayAdapter<Hienthidiem_Custom> {
    private Context mContext;
    private int mResource;

    public Hienthidiem_CustomAdapter(@NonNull Context context, int resource, @NonNull List<Hienthidiem_Custom> objects) {
        super(context, resource, objects);
        mContext = context;
        mResource = resource;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(mResource, parent, false);
        }

        Hienthidiem_Custom hienthidiem = getItem(position);

        // Ánh xạ các TextView từ layout
        TextView tenMonHocTextView = convertView.findViewById(R.id.text_tenmon_indiem2);
        TextView diemTrungBinhTextView = convertView.findViewById(R.id.text_diem_trung_binh1);
        TextView danhSachDiemTextView = convertView.findViewById(R.id.text_danh_sach_diem1);

        // Hiển thị thông tin từ đối tượng Hienthidiem_Custom vào các TextView
        tenMonHocTextView.setText(hienthidiem.getTenMonHoc());
        diemTrungBinhTextView.setText("Điểm trung bình: " + hienthidiem.getDiemTrungBinh());
        danhSachDiemTextView.setText("Danh sách điểm: " + hienthidiem.getDanhSachDiem());

        return convertView;
    }
}

