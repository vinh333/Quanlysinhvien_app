package com.example.quanlysinhvien_app.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.quanlysinhvien_app.Database.Diemdanh;
import com.example.quanlysinhvien_app.R;

import java.util.List;

// Tạo một lớp Adapter tùy chỉnh
public class HienThiDiemDanhAdapter extends ArrayAdapter<Diemdanh> {
    private Context context;
    private List<Diemdanh> diemDanhList;

    public HienThiDiemDanhAdapter(Context context, List<Diemdanh> diemDanhList) {
        super(context, 0, diemDanhList);
        this.context = context;
        this.diemDanhList = diemDanhList;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        // Kiểm tra nếu convertView là null, tạo mới nếu cần
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.list_hienthidiemdanh, parent, false);
        }

        // Lấy thông tin điểm danh tại vị trí hiện tại
        Diemdanh diemDanh = diemDanhList.get(position);

        // Ánh xạ các TextView trong item layout
        TextView maTenTextView = convertView.findViewById(R.id.listItem_HoTen);
        TextView ngayDiemDanhTextView = convertView.findViewById(R.id.listItem_NgayDiemDanh);
        TextView monHocTextView = convertView.findViewById(R.id.listItem_MonHoc);
        TextView lopTextView = convertView.findViewById(R.id.listItem_Lop);
        TextView tinhTrangDiemDanhTextView = convertView.findViewById(R.id.listItem_TinhTrangDiemDanh);
        TextView ghiChuTextView = convertView.findViewById(R.id.listItem_GhiChu);

        // Đặt giá trị cho các TextView
        maTenTextView.setText(diemDanh.getHotensv());
        ngayDiemDanhTextView.setText(diemDanh.getNgaydiemdanh());
        monHocTextView.setText(diemDanh.getMamonhoc());
        lopTextView.setText( diemDanh.getMalop());

        // Hiển thị tình trạng điểm danh dưới dạng "Có" hoặc "Vắng"
        String tinhTrang = diemDanh.getTinhtrangdiemdanh() ? "Có" : "Vắng";
        tinhTrangDiemDanhTextView.setText( tinhTrang);

        ghiChuTextView.setText( diemDanh.getGhiChu());

        return convertView;
    }
}

