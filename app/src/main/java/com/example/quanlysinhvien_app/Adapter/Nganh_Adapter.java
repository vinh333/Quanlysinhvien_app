package com.example.quanlysinhvien_app.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.quanlysinhvien_app.R;

import java.util.ArrayList;

public class Nganh_Adapter extends ArrayAdapter<String> {

    private Context context;
    private ArrayList<String> nganhList;

    public Nganh_Adapter(Context context, int resource, ArrayList<String> nganhList) {
        super(context, resource, nganhList);
        this.context = context;
        this.nganhList = nganhList;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View listItemView = convertView;
        if (listItemView == null) {
            LayoutInflater inflater = LayoutInflater.from(context);
            listItemView = inflater.inflate(R.layout.list_item_nganh, parent, false);
        }

        String nganh = nganhList.get(position);

        TextView nganhTextView = listItemView.findViewById(R.id.nganhTextView); // Thay R.id.nganhTextView bằng ID thật của TextView trong list_item_nganh.xml
        nganhTextView.setText(nganh);

        return listItemView;
    }
}
