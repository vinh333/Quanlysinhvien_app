package com.example.quanlysinhvien_app.Adapter;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CursorAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.quanlysinhvien_app.Database.SINHVIEN;
import com.example.quanlysinhvien_app.DatabaseHelper;
import com.example.quanlysinhvien_app.R;

import java.util.List;
import java.util.Random;

public class SinhVienAdapter extends ArrayAdapter<SINHVIEN> {

    private List<SINHVIEN> sinhVienList;
    private Context context;
    private int[] randomDrawables_Nam = {R.drawable.nam__1_, R.drawable.nam__2_, R.drawable.nam__3_};
    private int[] randomDrawables_Nu = { R.drawable.nu__1_, R.drawable.nu__2_,R.drawable.nu__3_};

    public SinhVienAdapter(Context context, List<SINHVIEN> sinhVienList) {
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

        SINHVIEN sinhVien = sinhVienList.get(position);

        TextView tvMaSV = convertView.findViewById(R.id.tvMaSV);
        tvMaSV.setText(sinhVien.getMaSV());

        TextView tvHoTen = convertView.findViewById(R.id.tvTenSV);
        tvHoTen.setText(sinhVien.getHoTen());

        ImageView imageView = convertView.findViewById(R.id.imageView5);
        // Chọn ngẫu nhiên một drawable từ mảng randomDrawables
        boolean gioitinh = sinhVien.isGioiTinh();
        if (gioitinh){
            int randomIndex = new Random().nextInt(randomDrawables_Nam.length);
            imageView.setImageResource(randomDrawables_Nam[randomIndex]);

            return convertView;
        }else {
            int randomIndex = new Random().nextInt(randomDrawables_Nu.length);
            imageView.setImageResource(randomDrawables_Nu[randomIndex]);

            return convertView;
        }

    }
}
