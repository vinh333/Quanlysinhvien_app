package com.example.quanlysinhvien_app.Database;

public class Hienthidiem_Custom {
    private String tenMonHoc;
    private double diemTrungBinh;
    private StringBuilder danhSachDiem;

    public Hienthidiem_Custom(String tenMonHoc, double diemTrungBinh, StringBuilder danhSachDiem) {
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

    public StringBuilder getDanhSachDiem() {
        return danhSachDiem;
    }
}

