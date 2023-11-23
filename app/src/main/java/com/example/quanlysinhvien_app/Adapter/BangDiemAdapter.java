package com.example.quanlysinhvien_app.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.quanlysinhvien_app.Database.BANGDIEMTHI;
import com.example.quanlysinhvien_app.R;

import java.util.List;

public class BangDiemAdapter extends RecyclerView.Adapter<BangDiemAdapter.ViewHolder> {

    private List<BANGDIEMTHI> bangDiemList; // Thay BangDiem bằng lớp dữ liệu thực tế

    public BangDiemAdapter(List<BANGDIEMTHI> bangDiemList) {
        this.bangDiemList = bangDiemList;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        // Khai báo các thành phần giao diện trong ViewHolder
        public TextView maSVTextView;
        public TextView maMonHocTextView;
        public TextView lanThiTextView;
        public TextView hocKyTextView;
        public TextView diemTextView;

        public ViewHolder(View itemView) {
            super(itemView);
            // Ánh xạ các thành phần giao diện
            maSVTextView = itemView.findViewById(R.id.text_view_masv);
            maMonHocTextView = itemView.findViewById(R.id.text_view_mamonhoc);
            lanThiTextView = itemView.findViewById(R.id.text_view_lanthi);
            hocKyTextView = itemView.findViewById(R.id.text_view_hocky);
            diemTextView = itemView.findViewById(R.id.text_view_diem);
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // Tạo ViewHolder từ layout của mỗi hàng trong RecyclerView
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_bang_diem, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        // Gán dữ liệu cho các thành phần giao diện trong ViewHolder từ danh sách bangDiemList
        BANGDIEMTHI bangDiem = bangDiemList.get(position);

        // Cập nhật các thành phần giao diện với dữ liệu từ bangDiem
        holder.maSVTextView.setText(bangDiem.getMaSV());
        holder.maMonHocTextView.setText(bangDiem.getMaMonHoc());
        holder.lanThiTextView.setText(bangDiem.getLanThi());
        holder.hocKyTextView.setText(bangDiem.getHocKy());
        holder.diemTextView.setText(String.valueOf(bangDiem.getDiem()));
    }

    @Override
    public int getItemCount() {
        return bangDiemList.size();
    }
}
