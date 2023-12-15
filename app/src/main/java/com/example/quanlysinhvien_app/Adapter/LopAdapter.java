package com.example.quanlysinhvien_app.Adapter;


import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import com.example.quanlysinhvien_app.Database.Lop;
import com.example.quanlysinhvien_app.R;
import com.github.ivbaranov.mli.MaterialLetterIcon;

import java.util.List;

public class LopAdapter extends ArrayAdapter<Lop> {

    private List<Lop> lopList;
    private Context context;
    private View view;

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
        // icon
        // Mảng chứa các mã màu
        // Mảng chứa các mã màu
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

        String tenLop = lop.getTenLop();

        if (tenLop != null && tenLop.length() > 0) {
            // Lấy ký tự cuối cùng của chuỗi
            char chuCui = tenLop.charAt(tenLop.length() - 1);

            // Chuyển đổi ký tự cuối cùng thành chuỗi
            String chuoiChuCui = String.valueOf(chuCui);

            // Đặt ký tự cuối cùng vào MaterialLetterIcon
            MaterialLetterIcon mIcon = convertView.findViewById(R.id.m_icon);

            // Chọn màu ngẫu nhiên từ mảng colors
            String randomColor = getRandomColorFromList(colors);

            // Đặt màu và chữ cái cho MaterialLetterIcon
            mIcon.setShapeColor(Color.parseColor(randomColor));
            mIcon.setLetter(chuoiChuCui);
        } else {
            // Xử lý khi chuỗi rỗng hoặc null
            MaterialLetterIcon mIcon = convertView.findViewById(R.id.m_icon);

            // Đặt màu và chữ cái cho MaterialLetterIcon
            mIcon.setShapeColor(Color.GRAY); // Màu mặc định nếu chuỗi rỗng hoặc null
            mIcon.setLetter("?");
        }



        return convertView;
    }
    // Hàm để chọn một màu ngẫu nhiên từ mảng
    private String getRandomColorFromList(String[] colors) {
        int randomIndex = (int) (Math.random() * colors.length);
        return colors[randomIndex];
    }
}
