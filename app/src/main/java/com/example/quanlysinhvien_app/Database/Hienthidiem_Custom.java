package com.example.quanlysinhvien_app.Database;

public class Hienthidiem_Custom {
    private String tenMonHoc;
    private double diemTrungBinh;
    private String danhSachDiem;

    public Hienthidiem_Custom(String tenMonHoc, double diemTrungBinh, String danhSachDiem) {
        this.tenMonHoc = tenMonHoc;
        this.diemTrungBinh = diemTrungBinh;
        this.danhSachDiem = danhSachDiem;
    }

    public String getTenMonHoc() {
        return tenMonHoc;
    }

    public double getDiemTrungBinh() {
        return diemTrungBinh;
    }

    public String getDanhSachDiem() {
        return danhSachDiem;
    }
}

