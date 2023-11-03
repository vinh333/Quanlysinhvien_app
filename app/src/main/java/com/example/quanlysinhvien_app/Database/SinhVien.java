package com.example.quanlysinhvien_app.Database;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class SinhVien {
    private String masv;
    private String hotensv;
    private boolean gioitinh;
    private String diachi;
    private int hocbong;
    private String malop;
    private String matinh;
    private String ngaysinh;
    private String noisinh;
    private String quan;
    private String avatarUrl; // Thêm trường để lưu đường dẫn của hình ảnh avatar

    public SinhVien(String masv, String hotensv, boolean gioitinh, String diaChi, int hocBong,
                    String maLop, String maTinh, String ngaySinh, String noiSinh, String quan, String avatarUrl) {
        this.masv = masv;
        this.hotensv = hotensv;
        this.gioitinh = gioitinh;
        this.diachi = diaChi;
        this.hocbong = hocBong;
        this.malop = maLop;
        this.matinh = maTinh;
        this.ngaysinh = ngaySinh;
        this.noisinh = noiSinh;
        this.quan = quan;
        this.avatarUrl = avatarUrl; // Khởi tạo giá trị cho trường avatarUrl
    }

    public SinhVien() {

    }

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

    public boolean getGioitinh() {
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

    public String getNgaysinh() {
        return ngaysinh;
    }

    public void setNgaysinh(String ngaysinh) {
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

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }
}

