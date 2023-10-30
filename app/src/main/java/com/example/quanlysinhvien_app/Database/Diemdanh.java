package com.example.quanlysinhvien_app.Database;

public class Diemdanh {

    static String masv;
    static String malop;
    static String mamonhoc;
    static String hocky;
    static String ngaydiemdanh; // Sửa đổi kiểu dữ liệu thành String
    static boolean tinhtrangdiemdanh;
    static String ghiChu;
    static String hotensv;

    public Diemdanh() {
        // Empty constructor required for Firebase
    }

    public Diemdanh(String masv, String malop, String mamonhoc, String hocky, String ngaydiemdanh, boolean tinhtrangdiemdanh, String ghiChu, String hotensv) {
        this.masv = masv;
        this.malop = malop;
        this.mamonhoc = mamonhoc;
        this.hocky = hocky;
        this.ngaydiemdanh = ngaydiemdanh;
        this.tinhtrangdiemdanh = tinhtrangdiemdanh;
        this.ghiChu = ghiChu;
        this.hotensv = hotensv;
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

    public String getNgaydiemdanh() {
        return ngaydiemdanh;
    }

    public void setNgaydiemdanh(String ngaydiemdanh) {
        this.ngaydiemdanh = ngaydiemdanh;
    }

    public boolean getTinhtrangdiemdanh() {
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

    public String getHotensv() {
        return hotensv;
    }

    public void setHotensv(String hotensv) {
        this.hotensv = hotensv;
    }
}
