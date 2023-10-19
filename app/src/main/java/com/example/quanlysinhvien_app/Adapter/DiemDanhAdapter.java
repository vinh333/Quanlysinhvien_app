package com.example.quanlysinhvien_app.Adapter;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
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

    static class ViewHolder {
        CheckBox checkBoxDiemDanh;
        TextView textViewTenSV;
        EditText editTextGhiChu;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        ViewHolder holder;

        if (convertView == null) {
            LayoutInflater inflater = LayoutInflater.from(context);
            convertView = inflater.inflate(R.layout.list_item_diemdanh, parent, false);

            holder = new ViewHolder();
            holder.checkBoxDiemDanh = convertView.findViewById(R.id.checkbox_diemdanh);
            holder.textViewTenSV = convertView.findViewById(R.id.textView_diemdanh_tensv);
            holder.editTextGhiChu = convertView.findViewById(R.id.textView_diemdanh_note);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        Diemdanh diemdanh = diemdanhList.get(position);

        // Đặt dữ liệu cho các thành phần trong layout
        holder.textViewTenSV.setText(diemdanh.getHotensv());
        holder.checkBoxDiemDanh.setChecked(diemdanh.getTinhtrangdiemdanh());
        holder.editTextGhiChu.setText(diemdanh.getGhiChu());

        // Lưu trạng thái của CheckBox khi thay đổi
        holder.checkBoxDiemDanh.setOnCheckedChangeListener((buttonView, isChecked) -> {
            diemdanh.setTinhtrangdiemdanh(isChecked);
        });


        // Lưu trạng thái của CheckBox khi thay đổi
        holder.checkBoxDiemDanh.setOnCheckedChangeListener((buttonView, isChecked) -> {
            diemdanh.setTinhtrangdiemdanh(isChecked);
        });
        // Đặt trạng thái của CheckBox
        holder.checkBoxDiemDanh.setChecked(diemdanh.getTinhtrangdiemdanh());


        // Lưu nội dung của EditText khi thay đổi
        holder.editTextGhiChu.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                diemdanh.setGhiChu(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {
                // Lưu ngay cả khi EditText rỗng
                diemdanh.setGhiChu(s.toString());
            }
        });


        return convertView;
    }
}
