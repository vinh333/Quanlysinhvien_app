package com.example.quanlysinhvien_app;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

import com.example.quanlysinhvien_app.Database.BANGDIEMTHI;
import com.example.quanlysinhvien_app.Database.KHOA;
import com.example.quanlysinhvien_app.Database.LOP;
import com.example.quanlysinhvien_app.Database.MONHOC;
import com.example.quanlysinhvien_app.Database.SINHVIEN;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {


    private static final String DATABASE_NAME = "STUDENT_DATABASE";

    // Tên cột cho bảng LOP
    public static final String CLASS_CODE_COLUMN_NAME = "MALOP";

    // Tên cột cho bảng SINHVIEN
    public static final String STUDENT_ID_COLUMN_NAME = "MASV";
    public static final String STUDENT_NAME_COLUMN_NAME = "HOTENSV";
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
    // Tên cột cho bảng MONHOC
    public static final String SUBJECT_CODE_COLUMN_NAME = "MAMONHOC";
    public static final String SUBJECT_NAME_COLUMN_NAME = "TENMONHOC";
    public static final String SUBJECT_THEORY_COLUMN_NAME = "LYTHUYET";
    public static final String SUBJECT_PRACTICE_COLUMN_NAME = "THUCHANH";
    // Tên cột cảu BANGDIEMHOCKY
    public static final String TABLE_NAME = "BANGDIEMHOCKY";
    public static final String COLUMN_ID = "ID";
    public static final String COLUMN_MASV = "MASV";
    public static final String COLUMN_HOCKY = "HOCKY";
    public static final String COLUMN_LANTHI = "LANTHI";
    public static final String COLUMN_DIEM = "DIEM";

    private static final int DATABASE_VERSION = 6;

    // Tạo bảng BANGDIEMTHI
    private static final String CREATE_TABLE_BANGDIEMTHI =  "CREATE TABLE BANGDIEMTHI " +
            "(MASV CHAR(10), " +
            "MAMONHOC CHAR(10), " +
            "LANTHI VARCHAR(20), " +
            "HOCKY VARCHAR(40), " +
            "DIEM DOUBLE, " +
            "PRIMARY KEY (MASV, MAMONHOC,LANTHI,HOCKY), " +
            "FOREIGN KEY (MASV) REFERENCES SINHVIEN(MASV), " +
            "FOREIGN KEY (MAMONHOC) REFERENCES MONHOC(MAMONHOC))";

    // Tạo bảng KHOA
    private static final String CREATE_TABLE_KHOA = "CREATE TABLE KHOA " +
            "(MAKHOA CHAR(10) PRIMARY KEY, " +
            "TENKHOA VARCHAR(40));";


    // Tạo bảng LOP
    private static final String CREATE_TABLE_LOP = "CREATE TABLE LOP " +
            "(MALOP CHAR(8) PRIMARY KEY, " +
            "TENLOP VARCHAR(50), " +
            "MAKHOA CHAR(10), " +
            "FOREIGN KEY (MAKHOA) REFERENCES KHOA(MAKHOA));" ;

    // Tạo bảng MONHOC
    private static final String CREATE_TABLE_MONHOC = "CREATE TABLE MONHOC " +
            "(MAMONHOC CHAR(10) PRIMARY KEY, " +
            "TENMONHOC VARCHAR(40), " +
            "LYTHUYET TINYINT, " +
            "THUCHANH TINYINT);";

    // Tạo bảng SINHVIEN
    private static final String CREATE_TABLE_SINHVIEN = "CREATE TABLE SINHVIEN " +
            "(MASV CHAR(10) PRIMARY KEY, " +
            "HOTENSV VARCHAR(40), " +
            "GIOITINH BOOLEAN, " +
            "NGAYSINH DATE, " +
            "NOISINH VARCHAR(30), " +
            "DIACHI VARCHAR(40), " +
            "MALOP CHAR(8), " +
            "HOCBONG FLOAT, " +
            "FOREIGN KEY (MALOP) REFERENCES LOP(MALOP));";



    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_BANGDIEMTHI);
        db.execSQL(CREATE_TABLE_KHOA);

        db.execSQL(CREATE_TABLE_LOP);
        db.execSQL(CREATE_TABLE_MONHOC);
        db.execSQL(CREATE_TABLE_SINHVIEN);


    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Xóa các bảng cũ nếu tồn tại và tạo lại
        db.execSQL("DROP TABLE IF EXISTS BANGDIEMTHI");


        db.execSQL("DROP TABLE IF EXISTS LOP");
        db.execSQL("DROP TABLE IF EXISTS MONHOC");
        db.execSQL("DROP TABLE IF EXISTS SINHVIEN");

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
        insertSampleDataLop(db);
        insertSampleDataMonHoc(db);
        insertSampleDataSinhVien(db);
        insertSampleDataBangDiemThi(db);

        db.close();
    }



    // Phương thức thêm dữ liệu mẫu cho bảng MONHOC
    private void insertSampleDataMonHoc(SQLiteDatabase db) {
        db.execSQL("INSERT INTO MONHOC (MAMONHOC, TENMONHOC, LYTHUYET, THUCHANH) VALUES ('M01', 'Lập Trình Java', 4, 2)");
        db.execSQL("INSERT INTO MONHOC (MAMONHOC, TENMONHOC, LYTHUYET, THUCHANH) VALUES ('M02', 'Kế Toán Căn Bản', 3, 1)");
        // Thêm dữ liệu cho các môn học khác nếu cần thiết
    }

    // Phương thức thêm dữ liệu mẫu cho bảng KHOA
    private void insertSampleDataKhoa(SQLiteDatabase db) {
        db.execSQL("INSERT INTO KHOA (MAKHOA, TENKHOA) VALUES ('K001', 'Khoa Công Nghệ Thông Tin')");
        db.execSQL("INSERT INTO KHOA (MAKHOA, TENKHOA) VALUES ('K002', 'Khoa Kinh Tế')");
        // Thêm dữ liệu cho các khoa khác nếu cần thiết
    }

    // Phương thức thêm dữ liệu mẫu cho bảng LOP
    private void insertSampleDataLop(SQLiteDatabase db) {
        db.execSQL("INSERT INTO LOP (MALOP, TENLOP, MAKHOA) VALUES ('L001', 'Lớp 1A', 'K001')");
        db.execSQL("INSERT INTO LOP (MALOP, TENLOP, MAKHOA) VALUES ('L002', 'Lớp 2A', 'K002')");
        // Thêm dữ liệu cho các lớp khác nếu cần thiết
    }

    // Phương thức thêm dữ liệu mẫu cho bảng SINHVIEN
    private void insertSampleDataSinhVien(SQLiteDatabase db) {
        db.execSQL("INSERT INTO SINHVIEN (MASV, HOTENSV, GIOITINH, NGAYSINH, NOISINH, DIACHI, MALOP, HOCBONG) VALUES " +
                "('SV001', 'Nguyen Van A', 1, '2000-01-01', 'Hanoi', '123 Main St', 'L001', 500000)");
        db.execSQL("INSERT INTO SINHVIEN (MASV, HOTENSV, GIOITINH, NGAYSINH, NOISINH, DIACHI, MALOP, HOCBONG) VALUES " +
                "('SV002', 'Tran Thi B', 0, '2001-02-02', 'Hanoi', '456 Main St', 'L002', 400000)");
        // Add data for other students if necessary
    }


    // Phương thức thêm dữ liệu mẫu cho bảng BANGDIEMTHI
    private void insertSampleDataBangDiemThi(SQLiteDatabase db) {
        db.execSQL("INSERT INTO BANGDIEMTHI (MASV, MAMONHOC, LANTHI, HOCKY, DIEM) VALUES ('SV001', 'M01', 'Lan 1', 'Hoc Ky 1', 8.5)");
        db.execSQL("INSERT INTO BANGDIEMTHI (MASV, MAMONHOC, LANTHI, HOCKY, DIEM) VALUES ('SV002', 'M02', 'Lan 1', 'Hoc Ky 1', 7.5)");
        // Thêm dữ liệu cho các bảng điểm thi khác nếu cần thiết
    }



    //******** Các hàm add table
    // Phương thức thêm Môn Học vào cơ sở dữ liệu
    public void addMonHoc(MONHOC monHoc) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(SUBJECT_CODE_COLUMN_NAME, monHoc.getMaMonHoc());
        values.put(SUBJECT_NAME_COLUMN_NAME, monHoc.getTenMonHoc());
        values.put(SUBJECT_THEORY_COLUMN_NAME, monHoc.getLyThuyet());
        values.put(SUBJECT_PRACTICE_COLUMN_NAME, monHoc.getThucHanh());

        // Thêm dữ liệu vào bảng MONHOC
        db.insert("MONHOC", null, values);

        db.close();
    }
    public void addMonHocFromUI(String maMonHoc, String tenMonHoc, int lyThuyet, int thucHanh) {
        MONHOC monHoc = new MONHOC(maMonHoc, tenMonHoc, lyThuyet, thucHanh);
        addMonHoc(monHoc);
    }
    // Phương thức thêm lớp học từ giao diện người dùng
    public void addLopHocFromUI(String maLop, String tenLop, String maKhoa) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(CLASS_CODE_COLUMN_NAME, maLop);
        values.put("TENLOP", tenLop);
        values.put("MAKHOA", maKhoa);

        // Thêm dữ liệu vào bảng LOP
        long result = db.insert("LOP", null, values);

        db.close();
    }
    public void addDiemThiFromUI(String maSV, String maMonHoc, String lanThi, String hocKy, double diem) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(EXAM_SCORE_STUDENT_ID_COLUMN_NAME, maSV);
        values.put(EXAM_SCORE_SUBJECT_CODE_COLUMN_NAME, maMonHoc);
        values.put(EXAM_SCORE_SESSION_COLUMN_NAME, lanThi);
        values.put(EXAM_SCORE_SEMESTER_COLUMN_NAME, hocKy);
        values.put(EXAM_SCORE_SCORE_COLUMN_NAME, diem);

        // Thêm dữ liệu vào bảng BANGDIEMTHI
        db.insert("BANGDIEMTHI", null, values);

        db.close();
    }

    //******** các hàm select UPDATE
    public boolean updateSinhVien(String maSV, String hoTen, boolean gioiTinh, String ngaySinh, String noiSinh, String diaChi, String maLop, float hocBong) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(STUDENT_NAME_COLUMN_NAME, hoTen);
        values.put(STUDENT_GENDER_COLUMN_NAME, gioiTinh ? 1 : 0);
        values.put(STUDENT_BIRTHDATE_COLUMN_NAME, ngaySinh);
        values.put(STUDENT_BIRTHPLACE_COLUMN_NAME, noiSinh);
        values.put(STUDENT_ADDRESS_COLUMN_NAME, diaChi);
        values.put(STUDENT_CLASS_CODE_COLUMN_NAME, maLop);
        values.put(STUDENT_SCHOLARSHIP_COLUMN_NAME, hocBong);

        String whereClause = STUDENT_ID_COLUMN_NAME + " = ?";
        String[] whereArgs = {maSV};

        // Cập nhật thông tin sinh viên trong bảng SINHVIEN
        int numRowsAffected = db.update("SINHVIEN", values, whereClause, whereArgs);

        db.close();

        // Trả về true nếu có ít nhất một hàng được cập nhật, ngược lại trả về false
        return numRowsAffected > 0;
    }


    //******** các hàm select All table
    public List<SINHVIEN> getAllSinhVien() {
        List<SINHVIEN> sinhVienList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery("SELECT * FROM SINHVIEN", null);
        if (cursor.moveToFirst()) {
            do {
                // Lấy thông tin từ cursor và tạo đối tượng SINHVIEN
                String maSV = cursor.getString(cursor.getColumnIndex(STUDENT_ID_COLUMN_NAME));
                String hoTen = cursor.getString(cursor.getColumnIndex(STUDENT_NAME_COLUMN_NAME));
                boolean gioiTinh = cursor.getInt(cursor.getColumnIndex(STUDENT_GENDER_COLUMN_NAME)) == 1;
                String noiSinh = cursor.getString(cursor.getColumnIndex(STUDENT_BIRTHPLACE_COLUMN_NAME));
                String ngaySinh = cursor.getString(cursor.getColumnIndex(STUDENT_BIRTHDATE_COLUMN_NAME));
                String diaChi = cursor.getString(cursor.getColumnIndex(STUDENT_ADDRESS_COLUMN_NAME));
                String maLop = cursor.getString(cursor.getColumnIndex(STUDENT_CLASS_CODE_COLUMN_NAME));
                float hocBong = cursor.getFloat(cursor.getColumnIndex(STUDENT_SCHOLARSHIP_COLUMN_NAME));

                // Tạo đối tượng SINHVIEN và thêm vào danh sách
                SINHVIEN sinhVien = new SINHVIEN(maSV, hoTen, ngaySinh, gioiTinh, noiSinh, diaChi, maLop, hocBong);
                sinhVienList.add(sinhVien);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return sinhVienList;
    }
    public List<KHOA> getAllDepartments() {
        List<KHOA> departmentList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery("SELECT * FROM KHOA", null);
        if (cursor.moveToFirst()) {
            do {
                // Lấy thông tin từ cursor và tạo đối tượng Khoa
                String maKhoa = cursor.getString(cursor.getColumnIndex("MAKHOA"));
                String tenKhoa = cursor.getString(cursor.getColumnIndex("TENKHOA"));

                // Tạo đối tượng Khoa và thêm vào danh sách
                KHOA khoa = new KHOA(maKhoa, tenKhoa);
                departmentList.add(khoa);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return departmentList;
    }

    public List<String> getAllMaMonHoc() {
        List<String> maMonHocList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery("SELECT " + SUBJECT_CODE_COLUMN_NAME + " FROM MONHOC", null);
        if (cursor.moveToFirst()) {
            do {
                // Lấy mã môn học từ cursor và thêm vào danh sách
                String maMonHoc = cursor.getString(cursor.getColumnIndex(SUBJECT_CODE_COLUMN_NAME));
                maMonHocList.add(maMonHoc);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return maMonHocList;
    }
    public List<String> getAllMaSinhVien() {
        List<String> maSinhVienList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery("SELECT " + STUDENT_ID_COLUMN_NAME + " FROM SINHVIEN", null);
        if (cursor.moveToFirst()) {
            do {
                // Lấy mã sinh viên từ cursor và thêm vào danh sách
                String maSV = cursor.getString(cursor.getColumnIndex(STUDENT_ID_COLUMN_NAME));
                maSinhVienList.add(maSV);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return maSinhVienList;
    }
    // Trong lớp DatabaseHelper
    public SINHVIEN getSinhVienByMaSV(String maSV) {
        SQLiteDatabase db = this.getReadableDatabase();

        String[] columns = {
                STUDENT_ID_COLUMN_NAME,
                STUDENT_NAME_COLUMN_NAME,
                STUDENT_GENDER_COLUMN_NAME,
                STUDENT_BIRTHDATE_COLUMN_NAME,
                STUDENT_BIRTHPLACE_COLUMN_NAME,
                STUDENT_ADDRESS_COLUMN_NAME,
                STUDENT_CLASS_CODE_COLUMN_NAME,
                STUDENT_SCHOLARSHIP_COLUMN_NAME
        };

        String selection = STUDENT_ID_COLUMN_NAME + " = ?";
        String[] selectionArgs = {maSV};

        Cursor cursor = db.query("SINHVIEN", columns, selection, selectionArgs, null, null, null);

        SINHVIEN sinhVien = null;

        if (cursor != null && cursor.moveToFirst()) {
            String hoTen = cursor.getString(cursor.getColumnIndex(STUDENT_NAME_COLUMN_NAME));
            boolean gioiTinh = cursor.getInt(cursor.getColumnIndex(STUDENT_GENDER_COLUMN_NAME)) == 1;
            String ngaySinh = cursor.getString(cursor.getColumnIndex(STUDENT_BIRTHDATE_COLUMN_NAME));
            String noiSinh = cursor.getString(cursor.getColumnIndex(STUDENT_BIRTHPLACE_COLUMN_NAME));
            String diaChi = cursor.getString(cursor.getColumnIndex(STUDENT_ADDRESS_COLUMN_NAME));
            String maLop = cursor.getString(cursor.getColumnIndex(STUDENT_CLASS_CODE_COLUMN_NAME));
            float hocBong = cursor.getFloat(cursor.getColumnIndex(STUDENT_SCHOLARSHIP_COLUMN_NAME));

            sinhVien = new SINHVIEN(maSV, hoTen, ngaySinh, gioiTinh, noiSinh, diaChi, maLop, hocBong);
        }

        if (cursor != null) {
            cursor.close();
        }

        db.close();

        return sinhVien;
    }
    public List<String> getAllLopNames() {
        List<String> lopNames = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        // Câu lệnh truy vấn
        String query = "SELECT MALOP FROM LOP";

        // Thực hiện truy vấn
        Cursor cursor = db.rawQuery(query, null);

        // Xử lý dữ liệu từ con trỏ
        if (cursor.moveToFirst()) {
            do {
                String tenLop = cursor.getString(cursor.getColumnIndex("MALOP"));
                lopNames.add(tenLop);
            } while (cursor.moveToNext());
        }

        // Đóng con trỏ và cơ sở dữ liệu
        cursor.close();
        db.close();

        return lopNames;
    }

    // Trong lớp DatabaseHelper
    public List<LOP> getAllClasses() {
        List<LOP> classList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery("SELECT * FROM LOP", null);
        if (cursor.moveToFirst()) {
            do {
                // Lấy thông tin từ cursor và tạo đối tượng LOP
                String maLop = cursor.getString(cursor.getColumnIndex(CLASS_CODE_COLUMN_NAME));
                String tenLop = cursor.getString(cursor.getColumnIndex("TENLOP"));
                String maKhoa = cursor.getString(cursor.getColumnIndex("MAKHOA"));

                // Tạo đối tượng LOP và thêm vào danh sách
                LOP lop = new LOP(maLop, tenLop, maKhoa);
                classList.add(lop);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return classList;
    }
    public List<BANGDIEMTHI> getAllBangDiem() {
        List<BANGDIEMTHI> bangDiemList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery("SELECT * FROM BANGDIEMTHI", null);
        if (cursor.moveToFirst()) {
            do {
                // Lấy thông tin từ cursor và tạo đối tượng BangDiem
                String maSV = cursor.getString(cursor.getColumnIndex("MASV"));
                String maMonHoc = cursor.getString(cursor.getColumnIndex("MAMONHOC"));
                String lanThi = cursor.getString(cursor.getColumnIndex("LANTHI"));
                String hocKy = cursor.getString(cursor.getColumnIndex("HOCKY"));
                double diem = cursor.getDouble(cursor.getColumnIndex("DIEM"));

                // Tạo đối tượng BangDiem và thêm vào danh sách
                BANGDIEMTHI bangDiem = new BANGDIEMTHI(maSV, maMonHoc, lanThi, hocKy, diem);
                bangDiemList.add(bangDiem);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return bangDiemList;
    }
    public List<BANGDIEMTHI> executeCustomQuery(String query) {
        List<BANGDIEMTHI> result = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery(query, null);
        if (cursor.moveToFirst()) {
            do {
                // Đọc dữ liệu từ Cursor và thêm vào danh sách kết quả
                BANGDIEMTHI bangDiem = new BANGDIEMTHI();

                bangDiem.setMaSV(cursor.getString(cursor.getColumnIndex("MASV")));
                bangDiem.setMaMonHoc(cursor.getString(cursor.getColumnIndex("MAMONHOC")));
                bangDiem.setLanThi(cursor.getString(cursor.getColumnIndex("LANTHI")));
                bangDiem.setHocKy(cursor.getString(cursor.getColumnIndex("HOCKY")));
                bangDiem.setDiem(cursor.getDouble(cursor.getColumnIndex("DIEM")));

                result.add(bangDiem);
            } while (cursor.moveToNext());
        }

        // Đóng cursor và kết nối đến cơ sở dữ liệu
        cursor.close();
        db.close();

        return result;
    }

    public List<String> getAllHocKy() {
        List<String> hocKyList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        // Câu lệnh truy vấn
        String query = "SELECT DISTINCT " + COLUMN_HOCKY + " FROM BANGDIEMTHI";

        // Thực hiện truy vấn
        Cursor cursor = db.rawQuery(query, null);

        // Xử lý dữ liệu từ con trỏ
        try {
            while (cursor.moveToNext()) {
                String hocKy = cursor.getString(cursor.getColumnIndex(COLUMN_HOCKY));
                hocKyList.add(hocKy);
            }
        } finally {
            // Đảm bảo con trỏ được đóng ngay cả khi có lỗi xảy ra
            cursor.close();
        }

        // Đóng cơ sở dữ liệu
        db.close();

        return hocKyList;
    }

    public List<String> getAllLanThi() {
        List<String> lanThiList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        // Câu lệnh truy vấn
        String query = "SELECT DISTINCT " + COLUMN_LANTHI + " FROM BANGDIEMTHI";

        // Thực hiện truy vấn
        Cursor cursor = db.rawQuery(query, null);

        // Xử lý dữ liệu từ con trỏ
        try {
            while (cursor.moveToNext()) {
                String lanThi = cursor.getString(cursor.getColumnIndex(COLUMN_LANTHI));
                lanThiList.add(lanThi);
            }
        } finally {
            // Đảm bảo con trỏ được đóng ngay cả khi có lỗi xảy ra
            cursor.close();
        }

        // Đóng cơ sở dữ liệu
        db.close();

        return lanThiList;
    }


    // Các hàm xoá
    // Hàm xóa sinh viên dựa trên mã sinh viên
    public boolean deleteSinhVien(String maSV) {
        SQLiteDatabase db = this.getWritableDatabase();

        // Bắt đầu một giao dịch
        db.beginTransaction();

        try {
            // Xóa sinh viên từ bảng SINHVIEN
            int affectedRows = db.delete("SINHVIEN", STUDENT_ID_COLUMN_NAME + "=?", new String[]{maSV});

            if (affectedRows > 0) {
                // Nếu xóa thành công, cập nhật bảng LOP
                ContentValues contentValues = new ContentValues();
                contentValues.put(CLASS_CODE_COLUMN_NAME, (String) null); // Đặt giá trị của cột MALOP thành null
                db.update("LOP", contentValues, CLASS_CODE_COLUMN_NAME + "=?", new String[]{maSV});
            }

            // Đánh dấu giao dịch là thành công
            db.setTransactionSuccessful();
        } catch (Exception e) {
            // In thông điệp lỗi nếu có lỗi xảy ra
            Log.e("DatabaseHelper", "Error deleting student with ID " + maSV, e);
        } finally {
            // Kết thúc giao dịch
            db.endTransaction();
        }

        // Kiểm tra xem có sinh viên nào bị xóa hay không
        int deleteResult = db.delete("SINHVIEN", STUDENT_ID_COLUMN_NAME + "=?", new String[]{maSV});

        // Đóng kết nối đến cơ sở dữ liệu
        db.close();

        return deleteResult > 0;
    }




}
