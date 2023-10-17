package com.example.quanlysinhvien_app.Database;

import java.sql.Date;

public class Diemdanh {

    private String masv;
    private String malop;
    private String mamonhoc;
    private String hocky;
    private Date ngaydiemdanh;
    private boolean tinhtrangdiemdanh;
    private String ghiChu; // Thêm biến để lưu ghi chú

    public Diemdanh() {
        // Empty constructor required for Firebase
    }

    public Diemdanh(String masv, String malop, String mamonhoc, String hocky, Date ngaydiemdanh, boolean tinhtrangdiemdanh, String ghiChu) {
        this.masv = masv;
        this.malop = malop;
        this.mamonhoc = mamonhoc;
        this.hocky = hocky;
        this.ngaydiemdanh = ngaydiemdanh;
        this.tinhtrangdiemdanh = tinhtrangdiemdanh;
        this.ghiChu = ghiChu;
    }

    public String getMasv() {
        return masv;
    }

    public void setMasv(String masv) {
        this.masv = masv;
    }

    public String getMalop() {
        return malop;
    }

    public void setMalop(String malop) {
        this.malop = malop;
    }

    public String getMamonhoc() {
        return mamonhoc;
    }

    public void setMamonhoc(String mamonhoc) {
        this.mamonhoc = mamonhoc;
    }

    public String getHocky() {
        return hocky;
    }

    public void setHocky(String hocky) {
        this.hocky = hocky;
    }

    public Date getNgaydiemdanh() {
        return ngaydiemdanh;
    }

    public void setNgaydiemdanh(Date ngaydiemdanh) {
        this.ngaydiemdanh = ngaydiemdanh;
    }

    public boolean isTinhtrangdiemdanh() {
        return tinhtrangdiemdanh;
    }

    public void setTinhtrangdiemdanh(boolean tinhtrangdiemdanh) {
        this.tinhtrangdiemdanh = tinhtrangdiemdanh;
    }

    public String getGhiChu() {
        return ghiChu;
    }

    public void setGhiChu(String ghiChu) {
        this.ghiChu = ghiChu;
    }
}
