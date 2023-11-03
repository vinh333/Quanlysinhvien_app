//package com.example.quanlysinhvien_app.Adapter;
//
//import android.content.Context;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.BaseAdapter;
//import android.widget.TextView;
//
//import com.example.quanlysinhvien_app.Database.BangDiemHocKy;
//import com.example.quanlysinhvien_app.R;
//
//import java.util.List;
//
//public class BangDiemHocKyAdapter extends BaseAdapter {
//    private List<BangDiemHocKy> bangDiemList;
//    private LayoutInflater inflater;
//
//    public BangDiemHocKyAdapter(Context context, List<BangDiemHocKy> bangDiemList) {
//        this.bangDiemList = bangDiemList;
//        inflater = LayoutInflater.from(context);
//    }
//
//    @Override
//    public int getCount() {
//        return bangDiemList.size();
//    }
//
//    @Override
//    public Object getItem(int position) {
//        return bangDiemList.get(position);
//    }
//
//    @Override
//    public long getItemId(int position) {
//        return position;
//    }
//
//    @Override
//    public View getView(int position, View convertView, ViewGroup parent) {
//        View view = convertView;
//        if (view == null) {
//            view = inflater.inflate(R.layout.list_item_hienthidiem, parent, false);
//        }
//
//        TextView masvTextView = view.findViewById(R.id.masvTextView);
//        TextView monhocTextView = view.findViewById(R.id.monhocTextView);
//        TextView hockyTextView = view.findViewById(R.id.hockyTextView);
//        TextView diemTextView = view.findViewById(R.id.diemTextView);
//        TextView tinchiTextView = view.findViewById(R.id.tinchiTextView);
//
//        BangDiemHocKy bangDiem = bangDiemList.get(position);
//        masvTextView.setText("Mã SV: " + bangDiem.getMasv());
//        monhocTextView.setText("Môn học: " + bangDiem.getMonhoc());
//        hockyTextView.setText("Học kỳ: " + bangDiem.getHocky());
//        diemTextView.setText("Điểm: " + bangDiem.getDiem());
//        tinchiTextView.setText("Số tín chỉ: " + bangDiem.getTinchi());
//
//        return view;
//    }
//}
//
