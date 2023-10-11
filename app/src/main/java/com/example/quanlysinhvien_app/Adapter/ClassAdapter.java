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
//
//public class ClassAdapter extends CursorAdapter {
//
//    public ClassAdapter(Context context, Cursor cursor) {
//        super(context, cursor, 0);
//    }
//
//    @Override
//    public View newView(Context context, Cursor cursor, ViewGroup parent) {
//        return LayoutInflater.from(context).inflate(android.R.layout.simple_spinner_dropdown_item, parent, false);
//    }
//
//    @Override
//    public void bindView(View view, Context context, Cursor cursor) {
//        TextView textViewClassCode = view.findViewById(android.R.id.text1);
//        String classCode = cursor.getString(cursor.getColumnIndex(DatabaseHelper.CLASS_CODE_COLUMN_NAME));
//        textViewClassCode.setText(classCode);
//    }
//}
