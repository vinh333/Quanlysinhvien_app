package com.example.quanlysinhvien_app.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.quanlysinhvien_app.Database.Khoa;
import com.example.quanlysinhvien_app.Database.NganhHoc;
import com.example.quanlysinhvien_app.R;

import java.util.List;

public class KhoaAdapter extends ArrayAdapter<Khoa> {
    private Context mContext;
    private int mResource;
    private List<NganhHoc> nganhList;

    public KhoaAdapter(@NonNull Context context, int resource, @NonNull List<Khoa> khoaList, List<NganhHoc> nganhList) {
        super(context, resource, khoaList);
        mContext = context;
        mResource = resource;
        this.nganhList = nganhList;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(mResource, parent, false);
        }

        TextView tenKhoaTextView = convertView.findViewById(R.id.tenkhoa);
        tenKhoaTextView.setText(getItem(position).getTenkhoa());

        // Hiển thị danh sách ngành trong mỗi item khoa
        ListView nganhListView = convertView.findViewById(R.id.list_nganh);
        NganhAdapter nganhAdapter = new NganhAdapter(mContext, R.layout.item_nganh, nganhList);
        nganhListView.setAdapter(nganhAdapter);

        return convertView;
    }
}

