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
    private Date ngaysinh;
    private String noisinh;
    private String quan;

    public SinhVien(String masv, String hotensv, boolean gioitinh, String diaChi, int hocBong,
                    String maLop, String maTinh, String ngaySinh, String noiSinh, String quan) {
        this.masv = masv;
        this.hotensv = hotensv;
        this.gioitinh = gioitinh;
        this.diachi = diaChi;
        this.hocbong = hocBong;
        this.malop = maLop;
        this.matinh = maTinh;
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            this.ngaysinh = sdf.parse(ngaySinh);
        } catch (ParseException e) {
            e.printStackTrace();
            this.ngaysinh = new Date(); // Đặt ngày sinh mặc định nếu có lỗi khi chuyển đổi ngày
        }
        this.noisinh = noiSinh;
        this.quan = quan;
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

