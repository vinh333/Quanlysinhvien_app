package com.example.quanlysinhvien_app;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.quanlysinhvien_app.Database.SINHVIEN;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {


    private static final String DATABASE_NAME = "STUDENT_DATABASE";

    // Tên cột cho bảng LOP
    public static final String CLASS_CODE_COLUMN_NAME = "MALOP";

    // Tên cột cho bảng SINHVIEN
    public static final String STUDENT_ID_COLUMN_NAME = "MASV";
    public static final String STUDENT_NAME_COLUMN_NAME = "HOSV";
    public static final String STUDENT_LAST_NAME_COLUMN_NAME = "TENSV";
    public static final String STUDENT_GENDER_COLUMN_NAME = "GIOITINH";
    public static final String STUDENT_BIRTHDATE_COLUMN_NAME = "NGAYSINH";
    public static final String STUDENT_BIRTHPLACE_COLUMN_NAME = "NOISINH";
    public static final String STUDENT_ADDRESS_COLUMN_NAME = "DIACHI";
    public static final String STUDENT_CITY_COLUMN_NAME = "MATINH";
    public static final String STUDENT_DISTRICT_COLUMN_NAME = "QUAN";
    public static final String STUDENT_CLASS_CODE_COLUMN_NAME = "MALOP";
    public static final String STUDENT_SCHOLARSHIP_COLUMN_NAME = "HOCBONG";

    // Tên cột cho bảng BANGDIEMTHI
    public static final String EXAM_SCORE_STUDENT_ID_COLUMN_NAME = "MASV";
    public static final String EXAM_SCORE_SUBJECT_CODE_COLUMN_NAME = "MAMONHOC";
    public static final String EXAM_SCORE_SESSION_COLUMN_NAME = "LANTHI";
    public static final String EXAM_SCORE_SEMESTER_COLUMN_NAME = "HOCKY";
    public static final String EXAM_SCORE_SCORE_COLUMN_NAME = "DIEM";

    // Tên cột cho bảng BANGDIEMRENLUYEN
    public static final String TRAINING_SCORE_STUDENT_ID_COLUMN_NAME = "MASV";
    public static final String TRAINING_SCORE_SEMESTER_COLUMN_NAME = "HOCKY";
    public static final String TRAINING_SCORE_SCORE_COLUMN_NAME = "DIEM";

    private static final int DATABASE_VERSION = 5;

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



        // Kiểm tra xem cơ sở dữ liệu có dữ liệu hay không
        if (isDatabaseEmpty(db)) {
            // Nếu cơ sở dữ liệu trống, thêm dữ liệu mẫu
            insertSampleData();
        }
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

    // Phương thức kiểm tra xem cơ sở dữ liệu có dữ liệu hay không
    private boolean isDatabaseEmpty(SQLiteDatabase db) {
        boolean isEmpty = true;
        Cursor cursor = db.rawQuery("SELECT * FROM " + DATABASE_NAME, null);
        if (cursor.getCount() > 0) {
            isEmpty = false;
        }
        cursor.close();
        return isEmpty;
    }
    // Phương thức thêm dữ liệu mẫu cho tất cả các bảng
    public void insertSampleData() {
        SQLiteDatabase db = this.getWritableDatabase();
        insertSampleDataKhoa(db);
        insertSampleDataHeDaoTao(db);
        insertSampleDataKhoaHoc(db);
        insertSampleDataNganhHoc(db);
        insertSampleDataLop(db);
        insertSampleDataMonHoc(db);
        insertSampleDataSinhVien(db);
        insertSampleDataBangDiemThi(db);
        insertSampleDataBangDiemRenLuyen(db);
        db.close();
    }

    // Phương thức thêm dữ liệu mẫu cho bảng KHOA
    private void insertSampleDataKhoa(SQLiteDatabase db) {
        db.execSQL("INSERT INTO KHOA (MAKHOA, TENKHOA) VALUES ('K001', 'Khoa Công Nghệ Thông Tin')");
        db.execSQL("INSERT INTO KHOA (MAKHOA, TENKHOA) VALUES ('K002', 'Khoa Kinh Tế')");
        // Thêm dữ liệu cho các khoa khác nếu cần thiết
    }

    // Phương thức thêm dữ liệu mẫu cho bảng HEDAOTAO
    private void insertSampleDataHeDaoTao(SQLiteDatabase db) {
        db.execSQL("INSERT INTO HEDAOTAO (MAHE, TENHE) VALUES ('H01', 'Đại Học')");
        db.execSQL("INSERT INTO HEDAOTAO (MAHE, TENHE) VALUES ('H02', 'Cao Đẳng')");
        // Thêm dữ liệu cho các hệ đào tạo khác nếu cần thiết
    }

    // Phương thức thêm dữ liệu mẫu cho bảng KHOAHOC
    private void insertSampleDataKhoaHoc(SQLiteDatabase db) {
        db.execSQL("INSERT INTO KHOAHOC (MAKHOAHOC, TENKHOAHOC) VALUES ('KH01', 'Khoa Học Máy Tính')");
        db.execSQL("INSERT INTO KHOAHOC (MAKHOAHOC, TENKHOAHOC) VALUES ('KH02', 'Khoa Học Điều Dưỡng')");
        // Thêm dữ liệu cho các khóa học khác nếu cần thiết
    }

    // Phương thức thêm dữ liệu mẫu cho bảng NGANHHOC
    private void insertSampleDataNganhHoc(SQLiteDatabase db) {
        db.execSQL("INSERT INTO NGANHHOC (MANGANH, TENNGANH, MAKHOA) VALUES ('N01', 'Công Nghệ Thông Tin', 'K001')");
        db.execSQL("INSERT INTO NGANHHOC (MANGANH, TENNGANH, MAKHOA) VALUES ('N02', 'Kế Toán', 'K002')");
        // Thêm dữ liệu cho các ngành học khác nếu cần thiết
    }

    // Phương thức thêm dữ liệu mẫu cho bảng LOP
    private void insertSampleDataLop(SQLiteDatabase db) {
        db.execSQL("INSERT INTO LOP (MALOP, TENLOP, MAKHOAHOC, MAHE, MANGANH) VALUES ('L001', 'Lớp 1A', 'KH01', 'H01', 'N01')");
        db.execSQL("INSERT INTO LOP (MALOP, TENLOP, MAKHOAHOC, MAHE, MANGANH) VALUES ('L002', 'Lớp 2A', 'KH02', 'H02', 'N02')");
        // Thêm dữ liệu cho các lớp khác nếu cần thiết
    }

    // Phương thức thêm dữ liệu mẫu cho bảng MONHOC
    private void insertSampleDataMonHoc(SQLiteDatabase db) {
        db.execSQL("INSERT INTO MONHOC (MAMONHOC, TENMONHOC, LYTHUYET, THUCHANH) VALUES ('M01', 'Lập Trình Java', 4, 2)");
        db.execSQL("INSERT INTO MONHOC (MAMONHOC, TENMONHOC, LYTHUYET, THUCHANH) VALUES ('M02', 'Kế Toán Căn Bản', 3, 1)");
        // Thêm dữ liệu cho các môn học khác nếu cần thiết
    }

    // Phương thức thêm dữ liệu mẫu cho bảng SINHVIEN
    private void insertSampleDataSinhVien(SQLiteDatabase db) {
        db.execSQL("INSERT INTO SINHVIEN (MASV, HOSV, TENSV, GIOITINH, NGAYSINH, NOISINH, DIACHI, MATINH, QUAN, MALOP, HOCBONG) VALUES ('SV001', 'Nguyen', 'Van A', 1, '2000-01-01', 'Hanoi', '123 Main St', 'HN', 'D1', 'L001', 500000)");
        db.execSQL("INSERT INTO SINHVIEN (MASV, HOSV, TENSV, GIOITINH, NGAYSINH, NOISINH, DIACHI, MATINH, QUAN, MALOP, HOCBONG) VALUES ('SV002', 'Tran', 'Thi B', 0, '2001-02-02', 'Hanoi', '456 Main St', 'HN', 'D2', 'L002', 400000)");
        // Thêm dữ liệu cho các sinh viên khác nếu cần thiết
    }

    // Phương thức thêm dữ liệu mẫu cho bảng BANGDIEMTHI
    private void insertSampleDataBangDiemThi(SQLiteDatabase db) {
        db.execSQL("INSERT INTO BANGDIEMTHI (MASV, MAMONHOC, LANTHI, HOCKY, DIEM) VALUES ('SV001', 'M01', 'Lan 1', 'Hoc Ky 1', 8.5)");
        db.execSQL("INSERT INTO BANGDIEMTHI (MASV, MAMONHOC, LANTHI, HOCKY, DIEM) VALUES ('SV002', 'M02', 'Lan 1', 'Hoc Ky 1', 7.5)");
        // Thêm dữ liệu cho các bảng điểm thi khác nếu cần thiết
    }

    // Phương thức thêm dữ liệu mẫu cho bảng BANGDIEMRENLUYEN
    private void insertSampleDataBangDiemRenLuyen(SQLiteDatabase db) {
        db.execSQL("INSERT INTO BANGDIEMRENLUYEN (MASV, HOCKY, DIEM) VALUES ('SV001', 'Hoc Ky 1', 9.0)");
        db.execSQL("INSERT INTO BANGDIEMRENLUYEN (MASV, HOCKY, DIEM) VALUES ('SV002', 'Hoc Ky 1', 8.5)");
        // Thêm dữ liệu cho các bảng điểm rèn luyện khác nếu cần thiết
    }

    public List<SINHVIEN> getAllSinhVien() {
        List<SINHVIEN> sinhVienList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery("SELECT * FROM SINHVIEN", null);
        if (cursor.moveToFirst()) {
            do {
                // Lấy thông tin từ cursor và tạo đối tượng SINHVIEN
                String maSV = cursor.getString(cursor.getColumnIndex(STUDENT_ID_COLUMN_NAME));
                String hoTen = cursor.getString(cursor.getColumnIndex(STUDENT_NAME_COLUMN_NAME));
                String ten = cursor.getString(cursor.getColumnIndex(STUDENT_LAST_NAME_COLUMN_NAME));
                boolean gioiTinh = cursor.getInt(cursor.getColumnIndex(STUDENT_GENDER_COLUMN_NAME)) == 1;
                String ngaySinh = cursor.getString(cursor.getColumnIndex(STUDENT_BIRTHDATE_COLUMN_NAME));
                String diaChi = cursor.getString(cursor.getColumnIndex(STUDENT_ADDRESS_COLUMN_NAME));
                String maTinh = cursor.getString(cursor.getColumnIndex(STUDENT_CITY_COLUMN_NAME));
                String maQuan = cursor.getString(cursor.getColumnIndex(STUDENT_DISTRICT_COLUMN_NAME));
                String maLop = cursor.getString(cursor.getColumnIndex(STUDENT_CLASS_CODE_COLUMN_NAME));
                float hocBong = cursor.getFloat(cursor.getColumnIndex(STUDENT_SCHOLARSHIP_COLUMN_NAME));

                // Tạo đối tượng SINHVIEN và thêm vào danh sách
                SINHVIEN sinhVien = new SINHVIEN(maSV, hoTen, ten, gioiTinh, ngaySinh, diaChi, maTinh, maQuan, maLop, hocBong);
                sinhVienList.add(sinhVien);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return sinhVienList;
    }


}
