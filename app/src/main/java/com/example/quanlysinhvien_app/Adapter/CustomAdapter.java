package com.example.quanlysinhvien_app.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.example.quanlysinhvien_app.R;

import java.util.List;

public class CustomAdapter extends ArrayAdapter<String> {
    private List<String> khoaHocList;

    public CustomAdapter(Context context, int resource, List<String> khoaHocList) {
        super(context, resource, khoaHocList);
        this.khoaHocList = khoaHocList;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, @NonNull ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.item_nienkhoa, parent, false);
        }

        String tenKhoaHoc = getItem(position);

        // Ánh xạ TextView trong mỗi dòng của ListView
        TextView textView = convertView.findViewById(R.id.ten_nienkhoa); // Thay "text_view_id" bằng ID thật của TextView

        if (tenKhoaHoc != null) {
            textView.setText(tenKhoaHoc);
        }

        return convertView;
    }
}

