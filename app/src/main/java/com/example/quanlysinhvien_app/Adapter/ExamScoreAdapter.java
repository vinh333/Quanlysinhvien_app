//package com.example.quanlysinhvien_app.Adapter;
//
//import android.content.Context;
//import android.database.Cursor;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.CursorAdapter;
//import android.widget.TextView;
//import com.example.quanlysinhvien_app.DatabaseHelper;
//import com.example.quanlysinhvien_app.R;
//
//public class ExamScoreAdapter extends CursorAdapter {
//
//    public ExamScoreAdapter(Context context, Cursor cursor) {
//        super(context, cursor, 0);
//    }
//
//    @Override
//    public View newView(Context context, Cursor cursor, ViewGroup parent) {
//        return LayoutInflater.from(context).inflate(R.layout.list_item_exam_score, parent, false);
//    }
//
//    @Override
//    public void bindView(View view, Context context, Cursor cursor) {
//        TextView textViewScore = view.findViewById(R.id.textViewScore);
//        double score = cursor.getDouble(cursor.getColumnIndex(DatabaseHelper.EXAM_SCORE_SCORE_COLUMN_NAME));
//        textViewScore.setText(String.valueOf(score));
//
//        // Đọc các trường dữ liệu khác và hiển thị chúng nếu cần thiết
//    }
//}
