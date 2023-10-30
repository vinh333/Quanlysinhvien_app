package com.example.quanlysinhvien_app.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.quanlysinhvien_app.R;

import java.util.ArrayList;

public class KhoaAdapter extends ArrayAdapter<String> {

    private ArrayList<String> khoaList;

    public KhoaAdapter(Context context, int resource, ArrayList<String> khoaList) {
        super(context, resource, khoaList);
        this.khoaList = khoaList;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_item_khoa, parent, false);
        }

        String tenKhoa = khoaList.get(position);

        TextView tenKhoaTextView = convertView.findViewById(R.id.khoaTextView);
        tenKhoaTextView.setText(tenKhoa);

        return convertView;
    }
}

