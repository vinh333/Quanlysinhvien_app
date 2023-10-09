package com.example.quanlysinhvien_app;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "STUDENT_DATABASE";
    private static final int DATABASE_VERSION = 1;

    // Tạo bảng BANGDIEMTHI
    private static final String CREATE_TABLE_BANGDIEMTHI = "CREATE TABLE BANGDIEMTHI " +
            "(MASV CHAR(10) PRIMARY KEY, " +
            "MAMONHOC CHAR(10), " +
            "LANTHI VARCHAR(20), " +
            "HOCKY VARCHAR(40), " +
            "DIEM DOUBLE, " +
            "FOREIGN KEY (MASV) REFERENCES SINHVIEN(MASV), " +
            "FOREIGN KEY (MAMONHOC) REFERENCES MONHOC(MAMONHOC));";

    // Tạo bảng KHOA
    private static final String CREATE_TABLE_KHOA = "CREATE TABLE KHOA " +
            "(MAKHOA CHAR(10) PRIMARY KEY, " +
            "TENKHOA VARCHAR(40));";

    // Tạo bảng HEDAOTAO
    private static final String CREATE_TABLE_HEDAOTAO = "CREATE TABLE HEDAOTAO " +
            "(MAHE CHAR(4) PRIMARY KEY, " +
            "TENHE VARCHAR(50));";

    // Tạo bảng KHOAHOC
    private static final String CREATE_TABLE_KHOAHOC = "CREATE TABLE KHOAHOC " +
            "(MAKHOAHOC CHAR(10) PRIMARY KEY, " +
            "TENKHOAHOC VARCHAR(40));";

    // Tạo bảng NGANHHOC
    private static final String CREATE_TABLE_NGANHHOC = "CREATE TABLE NGANHHOC " +
            "(MANGANH CHAR(10) PRIMARY KEY, " +
            "TENNGANH VARCHAR(60), " +
            "MAKHOA CHAR(10), " +
            "FOREIGN KEY (MAKHOA) REFERENCES KHOA(MAKHOA));";

    // Tạo bảng LOP
    private static final String CREATE_TABLE_LOP = "CREATE TABLE LOP " +
            "(MALOP CHAR(8) PRIMARY KEY, " +
            "TENLOP VARCHAR(50), " +
            "MAKHOAHOC CHAR(10), " +
            "MAHE CHAR(2), " +
            "MANGANH CHAR(10), " +
            "FOREIGN KEY (MAKHOAHOC) REFERENCES KHOAHOC(MAKHOAHOC), " +
            "FOREIGN KEY (MAHE) REFERENCES HEDAOTAO(MAHE), " +
            "FOREIGN KEY (MANGANH) REFERENCES NGANHHOC(MANGANH));";

    // Tạo bảng MONHOC
    private static final String CREATE_TABLE_MONHOC = "CREATE TABLE MONHOC " +
            "(MAMONHOC CHAR(10) PRIMARY KEY, " +
            "TENMONHOC VARCHAR(40), " +
            "LYTHUYET TINYINT, " +
            "THUCHANH TINYINT);";

    // Tạo bảng SINHVIEN
    private static final String CREATE_TABLE_SINHVIEN = "CREATE TABLE SINHVIEN " +
            "(MASV CHAR(10) PRIMARY KEY, " +
            "HOSV VARCHAR(20), " +
            "TENSV VARCHAR(10), " +
            "GIOITINH BOOLEAN, " +
            "NGAYSINH DATE, " +
            "NOISINH VARCHAR(30), " +
            "DIACHI VARCHAR(40), " +
            "MATINH CHAR(6), " +
            "QUAN CHAR(2), " +
            "MALOP CHAR(8), " +
            "HOCBONG FLOAT, " +
            "FOREIGN KEY (MALOP) REFERENCES LOP(MALOP));";

    // Tạo bảng BANGDIEMRENLUYEN
    private static final String CREATE_TABLE_BANGDIEMRENLUYEN = "CREATE TABLE BANGDIEMRENLUYEN " +
            "(MASV CHAR(10), " +
            "HOCKY VARCHAR(40), " +
            "DIEM DOUBLE, " +
            "PRIMARY KEY (MASV, HOCKY), " +
            "FOREIGN KEY (MASV) REFERENCES SINHVIEN(MASV));";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_BANGDIEMTHI);
        db.execSQL(CREATE_TABLE_KHOA);
        db.execSQL(CREATE_TABLE_HEDAOTAO);
        db.execSQL(CREATE_TABLE_KHOAHOC);
        db.execSQL(CREATE_TABLE_NGANHHOC);
        db.execSQL(CREATE_TABLE_LOP);
        db.execSQL(CREATE_TABLE_MONHOC);
        db.execSQL(CREATE_TABLE_SINHVIEN);
        db.execSQL(CREATE_TABLE_BANGDIEMRENLUYEN);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Xóa các bảng cũ nếu tồn tại và tạo lại
        db.execSQL("DROP TABLE IF EXISTS BANGDIEMTHI");
        db.execSQL("DROP TABLE IF EXISTS KHOA");
        db.execSQL("DROP TABLE IF EXISTS HEDAOTAO");
        db.execSQL("DROP TABLE IF EXISTS KHOAHOC");
        db.execSQL("DROP TABLE IF EXISTS NGANHHOC");
        db.execSQL("DROP TABLE IF EXISTS LOP");
        db.execSQL("DROP TABLE IF EXISTS MONHOC");
        db.execSQL("DROP TABLE IF EXISTS SINHVIEN");
        db.execSQL("DROP TABLE IF EXISTS BANGDIEMRENLUYEN");

        // Tạo lại các bảng
        onCreate(db);
    }
}
