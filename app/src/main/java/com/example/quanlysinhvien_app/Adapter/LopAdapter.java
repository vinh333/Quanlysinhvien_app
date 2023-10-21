package com.example.quanlysinhvien_app.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import com.example.quanlysinhvien_app.Database.Lop;
import com.example.quanlysinhvien_app.R;
import java.util.List;

public class LopAdapter extends ArrayAdapter<Lop> {

    private List<Lop> lopList;
    private Context context;

    public LopAdapter(Context context, List<Lop> lopList) {
        super(context, R.layout.list_item_lop, lopList);
        this.lopList = lopList;
        this.context = context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater inflater = LayoutInflater.from(context);
            convertView = inflater.inflate(R.layout.list_item_lop, parent, false);
        }

        Lop lop = lopList.get(position);

        TextView tvMaLop = convertView.findViewById(R.id.tvMaLop);
        tvMaLop.setText(lop.getMaLop());

        TextView tvTenLop = convertView.findViewById(R.id.tvTenLop);
        tvTenLop.setText(lop.getTenLop());

        return convertView;
    }
}
