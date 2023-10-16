package com.example.quanlysinhvien_app.Database;

public class DiemThiHocKy {
    private String masv;
    private String hocky;
    private String monhoc;
    private String tinchi;
    private String diem;

    public DiemThiHocKy() {
        // Default constructor required for calls to DataSnapshot.getValue(DiemThiHocKy.class)
    }

    public DiemThiHocKy(String masv, String hocky, String monhoc, String tinchi, String diem) {
        this.masv = masv;
        this.hocky = hocky;
        this.monhoc = monhoc;
        this.tinchi = tinchi;
        this.diem = diem;
    }

    public String getMasv() {
        return masv;
    }

    public String getHocky() {
        return hocky;
    }

    public String getMonhoc() {
        return monhoc;
    }

    public String getTinchi() {
        return tinchi;
    }

    public String getDiem() {
        return diem;
    }
}

