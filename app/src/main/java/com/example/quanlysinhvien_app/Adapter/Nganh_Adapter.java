package com.example.quanlysinhvien_app.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.quanlysinhvien_app.R;
import com.github.ivbaranov.mli.MaterialLetterIcon;

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

        TextView nganhTextView = listItemView.findViewById(R.id.nganhTextView);
        nganhTextView.setText(nganh);

        // icon
        // Mảng chứa các mã màu
        String[] colors = {
                "#FF5F5D", // Đỏ hồng
                "#3F7C85", // Xanh navy
                "#00CCBF", // Cyan đậm
                "#72F2EB", // Xanh nước biển nhạt
                "#747E7E",  // Xám xanh
                "#F2CA52",
                "#BBCDF2",
                "#024873"
        };

        if (nganh != null && nganh.length() > 0) {
            // Lấy ký tự đầu tiên của chuỗi
            char chuDau = nganh.charAt(0);

            // Chuyển đổi ký tự đầu tiên thành chuỗi
            String chuoiChuDau = String.valueOf(chuDau);

            // Đặt ký tự đầu tiên vào MaterialLetterIcon
            MaterialLetterIcon mIcon = listItemView.findViewById(R.id.m_icon);

            // Chọn màu ngẫu nhiên từ mảng colors
            String randomColor = getRandomColorFromList(colors);

            // Đặt màu và chữ cái cho MaterialLetterIcon
            mIcon.setShapeColor(android.graphics.Color.parseColor(randomColor));
            mIcon.setLetter(chuoiChuDau);
        } else {
            // Xử lý khi chuỗi rỗng hoặc null
            MaterialLetterIcon mIcon = listItemView.findViewById(R.id.m_icon);

            // Đặt màu và chữ cái cho MaterialLetterIcon
            mIcon.setShapeColor(android.graphics.Color.GRAY); // Màu mặc định nếu chuỗi rỗng hoặc null
            mIcon.setLetter("?");
        }

        return listItemView;
    }

    // Hàm để chọn một màu ngẫu nhiên từ mảng
    private String getRandomColorFromList(String[] colors) {
        int randomIndex = (int) (Math.random() * colors.length);
        return colors[randomIndex];
    }
}
