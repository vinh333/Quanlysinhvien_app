package com.example.quanlysinhvien_app.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.example.quanlysinhvien_app.Database.KHOA;
import com.example.quanlysinhvien_app.R;

import java.util.List;

public class KhoaAdapter extends ArrayAdapter<KHOA> {

    public KhoaAdapter(Context context, List<KHOA> departments) {
        super(context, 0, departments);
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, @NonNull ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_item_department, parent, false);
        }

        TextView textViewDepartment = convertView.findViewById(R.id.textViewDepartment);

        // Lấy đối tượng KHOA từ danh sách tại vị trí hiện tại
        KHOA khoa = getItem(position);

        // Hiển thị thông tin khoa trong TextView
        if (khoa != null) {
            textViewDepartment.setText(khoa.toString());
        }

        return convertView;
    }
}

