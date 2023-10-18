package com.example.quanlysinhvien_app.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.quanlysinhvien_app.Database.Diemdanh;
import com.example.quanlysinhvien_app.R;

import java.util.List;

public class DiemDanhAdapter extends ArrayAdapter<Diemdanh> {

    private List<Diemdanh> diemdanhList;
    private Context context;

    public DiemDanhAdapter(Context context, List<Diemdanh> diemdanhList) {
        super(context, R.layout.list_item_diemdanh, diemdanhList);
        this.diemdanhList = diemdanhList;
        this.context = context;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater inflater = LayoutInflater.from(context);
            convertView = inflater.inflate(R.layout.list_item_diemdanh, parent, false);
        }

        Diemdanh diemdanh = diemdanhList.get(position);

        CheckBox checkBoxDiemDanh = convertView.findViewById(R.id.checkbox_diemdanh);
        TextView textViewTenSV = convertView.findViewById(R.id.textView_diemdanh_tensv);
        EditText editTextGhiChu = convertView.findViewById(R.id.textView_diemdanh_note);

        // Đặt dữ liệu cho các thành phần trong layout
        textViewTenSV.setText(diemdanh.getHotensv()); // Thay thế bằng phương thức lấy tên sinh viên từ Diemdanh
        checkBoxDiemDanh.setChecked(diemdanh.isTinhtrangdiemdanh());
        editTextGhiChu.setText(diemdanh.getGhiChu()); // Thay thế bằng phương thức lấy ghi chú từ Diemdanh

        // Các xử lý khác tại đây nếu cần

        return convertView;
    }
}
