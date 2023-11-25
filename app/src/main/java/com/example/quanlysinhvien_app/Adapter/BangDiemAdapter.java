package com.example.quanlysinhvien_app.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.quanlysinhvien_app.Database.BANGDIEMTHI;
import com.example.quanlysinhvien_app.R;

import java.util.List;

public class BangDiemAdapter extends ArrayAdapter<BANGDIEMTHI> {

    public BangDiemAdapter(Context context, List<BANGDIEMTHI> bangDiemList) {
        super(context, R.layout.item_bang_diem, bangDiemList);
    }
    // Thêm phương thức này để lấy BANGDIEMTHI tại vị trí position
    public BANGDIEMTHI getItemAtPosition(int position) {
        return getItem(position);
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Tạo và cấu hình view item từ layout (item_bang_diem.xml)
        View itemView = convertView;
        if (itemView == null) {
            itemView = LayoutInflater.from(getContext()).inflate(R.layout.item_bang_diem, parent, false);
        }

        // Ghi đè phương thức này để hiển thị dữ liệu từ danh sách vào view item
        BANGDIEMTHI bangDiem = getItem(position);

        // Cập nhật các thành phần giao diện với dữ liệu từ bangDiem
        TextView maSVTextView = itemView.findViewById(R.id.text_view_masv);
        maSVTextView.setText(bangDiem.getMaSV()); // Giả sử getMaSV() là phương thức để lấy mã sinh viên

        // Cập nhật các thành phần giao diện với dữ liệu từ bangDiem
        TextView monHocTextView = itemView.findViewById(R.id.text_view_mamonhoc);
        monHocTextView.setText(bangDiem.getMaMonHoc()); // Giả sử getMaMonHoc() là phương thức để lấy mã môn học

        // Cập nhật các thành phần giao diện với dữ liệu từ bangDiem
        TextView lanThiTextView = itemView.findViewById(R.id.text_view_lanthi);
        lanThiTextView.setText(bangDiem.getLanThi()); // Giả sử getLanThi() là phương thức để lấy số lần thi

        // Cập nhật các thành phần giao diện với dữ liệu từ bangDiem
        TextView hocKyTextView = itemView.findViewById(R.id.text_view_hocky);
        hocKyTextView.setText(bangDiem.getHocKy()); // Giả sử getHocKy() là phương thức để lấy học kỳ

        // Giả sử có thêm các TextView khác trong layout item, cập nhật chúng tương tự

        // Cập nhật TextView cho điểm
        TextView diemTextView = itemView.findViewById(R.id.text_view_diem);
        diemTextView.setText(String.valueOf(bangDiem.getDiem())); // Giả sử getDiem() là phương thức để lấy điểm

        return itemView;
    }
}
