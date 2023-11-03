package com.example.quanlysinhvien_app.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.quanlysinhvien_app.Database.SinhVien;
import com.example.quanlysinhvien_app.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class SinhVienAdapter extends ArrayAdapter<SinhVien> {

    private List<SinhVien> sinhVienList;
    private Context context;

    public SinhVienAdapter(Context context, List<SinhVien> sinhVienList) {
        super(context, R.layout.list_item_sinhvien, sinhVienList);
        this.sinhVienList = sinhVienList;
        this.context = context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater inflater = LayoutInflater.from(context);
            convertView = inflater.inflate(R.layout.list_item_sinhvien, parent, false);
        }

        SinhVien sinhVien = sinhVienList.get(position);

        TextView tvMaSV = convertView.findViewById(R.id.tvMaLop);
        tvMaSV.setText(sinhVien.getMasv());

        TextView tvHoTen = convertView.findViewById(R.id.tvTenLop);
        tvHoTen.setText(sinhVien.getHotensv());

        ImageView imageViewAvatar = convertView.findViewById(R.id.imageavt);

        // Tải và hiển thị avatar sử dụng Picasso
        Picasso.get().load(sinhVien.getAvatarUrl()).into(imageViewAvatar);


        return convertView;
    }
}

