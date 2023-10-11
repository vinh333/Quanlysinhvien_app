package com.example.quanlysinhvien_app.Database;

import java.util.Date;

public class SinhVien {
    private String masv;
    private String hotensv;
    private boolean gioitinh;
    private String diachi;
    private int hocbong;
    private String malop;
    private String matinh;
    private Date ngaysinh;
    private String noisinh;
    private String quan;

    // Constructors
    public SinhVien() {
        // Default constructor required for calls to DataSnapshot.getValue(SinhVien.class)
    }

    public SinhVien(String masv, String hotensv, boolean gioitinh, String diachi, int hocbong,
                    String malop, String matinh, Date ngaysinh, String noisinh, String quan) {
        this.masv = masv;
        this.hotensv = hotensv;
        this.gioitinh = gioitinh;
        this.diachi = diachi;
        this.hocbong = hocbong;
        this.malop = malop;
        this.matinh = matinh;
        this.ngaysinh = ngaysinh;
        this.noisinh = noisinh;
        this.quan = quan;
    }

    // Getter and Setter methods
    public String getMasv() {
        return masv;
    }

    public void setMasv(String masv) {
        this.masv = masv;
    }

    public String getHotensv() {
        return hotensv;
    }

    public void setHotensv(String hotensv) {
        this.hotensv = hotensv;
    }

    public boolean isGioitinh() {
        return gioitinh;
    }

    public void setGioitinh(boolean gioitinh) {
        this.gioitinh = gioitinh;
    }

    public String getDiachi() {
        return diachi;
    }

    public void setDiachi(String diachi) {
        this.diachi = diachi;
    }

    public int getHocbong() {
        return hocbong;
    }

    public void setHocbong(int hocbong) {
        this.hocbong = hocbong;
    }

    public String getMalop() {
        return malop;
    }

    public void setMalop(String malop) {
        this.malop = malop;
    }

    public String getMatinh() {
        return matinh;
    }

    public void setMatinh(String matinh) {
        this.matinh = matinh;
    }

    public Date getNgaysinh() {
        return ngaysinh;
    }

    public void setNgaysinh(Date ngaysinh) {
        this.ngaysinh = ngaysinh;
    }

    public String getNoisinh() {
        return noisinh;
    }

    public void setNoisinh(String noisinh) {
        this.noisinh = noisinh;
    }

    public String getQuan() {
        return quan;
    }

    public void setQuan(String quan) {
        this.quan = quan;
    }
}
