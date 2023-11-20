package com.example.quanlysinhvien_app.Database;

public class KHOA {
    private String maKhoa;
    private String tenKhoa;

    public KHOA(String maKhoa, String tenKhoa) {
        this.maKhoa = maKhoa;
        this.tenKhoa = tenKhoa;
    }

    public String getMaKhoa() {
        return maKhoa;
    }

    public void setMaKhoa(String maKhoa) {
        this.maKhoa = maKhoa;
    }

    public String getTenKhoa() {
        return tenKhoa;
    }

    public void setTenKhoa(String tenKhoa) {
        this.tenKhoa = tenKhoa;
    }

    @Override
    public String toString() {
        // Hiển thị theo định dạng "Makhoa: TenKhoa"
        return maKhoa + ": " + tenKhoa;
    }
}

