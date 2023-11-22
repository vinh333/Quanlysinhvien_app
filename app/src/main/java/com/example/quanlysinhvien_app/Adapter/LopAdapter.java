package com.example.quanlysinhvien_app.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.example.quanlysinhvien_app.Database.LOP;
import com.example.quanlysinhvien_app.R;

import java.util.List;

public class LopAdapter extends ArrayAdapter<LOP> {

    public LopAdapter(Context context, List<LOP> classes) {
        super(context, 0, classes);
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, @NonNull ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_item_class, parent, false);
        }

        TextView textViewClass = convertView.findViewById(R.id.textViewClass);

        // Lấy đối tượng LOP từ danh sách tại vị trí hiện tại
        LOP lop = getItem(position);

        // Hiển thị thông tin lớp trong TextView
        if (lop != null) {
            textViewClass.setText(lop.toString());
        }

        return convertView;
    }
}

