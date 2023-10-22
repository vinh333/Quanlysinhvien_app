package com.example.quanlysinhvien_app.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.quanlysinhvien_app.Database.Khoa;
import com.example.quanlysinhvien_app.R;

import java.util.List;

public class KhoaAdapter extends ArrayAdapter<Khoa> {
    private Context mContext;
    private int mResource;

    public KhoaAdapter(@NonNull Context context, int resource, @NonNull List<Khoa> khoaList) {
        super(context, resource, khoaList);
        mContext = context;
        mResource = resource;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(mResource, parent, false);
        }

        TextView tenKhoaTextView = convertView.findViewById(R.id.tenkhoa);
        tenKhoaTextView.setText(getItem(position).getTenkhoa());

        // Nếu bạn muốn hiển thị thông tin khác của Khoa, bạn có thể làm ở đây.

        return convertView;
    }
}
