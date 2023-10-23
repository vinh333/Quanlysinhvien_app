package com.example.quanlysinhvien_app.Adapter;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.quanlysinhvien_app.Database.NganhHoc;
import com.example.quanlysinhvien_app.R;

import java.util.List;
public class NganhAdapter extends ArrayAdapter<NganhHoc> {
    private Context mContext;
    private int mResource;

    public NganhAdapter(@NonNull Context context, int resource, @NonNull List<NganhHoc> nganhList) {
        super(context, resource, nganhList);
        mContext = context;
        mResource = resource;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(mResource, parent, false);
        }

        // Lấy đối tượng NganhHoc tại vị trí position
        NganhHoc nganhHoc = getItem(position);

        // Hiển thị thông tin ngành học trong TextView của mỗi item
        TextView tenNganhTextView = convertView.findViewById(R.id.ten_nienkhoa);
        if (nganhHoc != null) {
            tenNganhTextView.setText(nganhHoc.getTennganh());
        }



        return convertView;
    }
}