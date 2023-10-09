package com.example.quanlysinhvien_app.Database;

public class SINHVIEN {
    private String maSV;
    private String hoTen;
    private String ngaySinh;
    private String gioiTinh;
    private String lop;
    private String noiSinh;
    private String diaChi;
    private String maTinh;
    private String quan;
    private String maLop;
    private float hocBong;

    public SINHVIEN(String maSV, String hoTen, String ngaySinh, String gioiTinh, String lop,
                    String noiSinh, String diaChi, String maTinh, String quan, String maLop, float hocBong) {
        this.maSV = maSV;
        this.hoTen = hoTen;
        this.ngaySinh = ngaySinh;
        this.gioiTinh = gioiTinh;
        this.lop = lop;
        this.noiSinh = noiSinh;
        this.diaChi = diaChi;
        this.maTinh = maTinh;
        this.quan = quan;
        this.maLop = maLop;
        this.hocBong = hocBong;
    }

    public String getMaSV() {
        return maSV;
    }

    public void setMaSV(String maSV) {
        this.maSV = maSV;
    }

    public String getHoTen() {
        return hoTen;
    }

    public void setHoTen(String hoTen) {
        this.hoTen = hoTen;
    }

    public String getNgaySinh() {
        return ngaySinh;
    }

    public void setNgaySinh(String ngaySinh) {
        this.ngaySinh = ngaySinh;
    }

    public String getGioiTinh() {
        return gioiTinh;
    }

    public void setGioiTinh(String gioiTinh) {
        this.gioiTinh = gioiTinh;
    }

    public String getLop() {
        return lop;
    }

    public void setLop(String lop) {
        this.lop = lop;
    }

    public String getNoiSinh() {
        return noiSinh;
    }

    public void setNoiSinh(String noiSinh) {
        this.noiSinh = noiSinh;
    }

    public String getDiaChi() {
        return diaChi;
    }

    public void setDiaChi(String diaChi) {
        this.diaChi = diaChi;
    }

    public String getMaTinh() {
        return maTinh;
    }

    public void setMaTinh(String maTinh) {
        this.maTinh = maTinh;
    }

    public String getQuan() {
        return quan;
    }

    public void setQuan(String quan) {
        this.quan = quan;
    }

    public String getMaLop() {
        return maLop;
    }

    public void setMaLop(String maLop) {
        this.maLop = maLop;
    }

    public float getHocBong() {
        return hocBong;
    }

    public void setHocBong(float hocBong) {
        this.hocBong = hocBong;
    }
}
