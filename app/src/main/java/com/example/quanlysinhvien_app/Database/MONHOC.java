package com.example.quanlysinhvien_app.Database;

public class MONHOC {
    private String maMonHoc;
    private String tenMonHoc;
    private int lyThuyet;
    private int thucHanh;

    public MONHOC(String maMonHoc, String tenMonHoc, int lyThuyet, int thucHanh) {
        this.maMonHoc = maMonHoc;
        this.tenMonHoc = tenMonHoc;
        this.lyThuyet = lyThuyet;
        this.thucHanh = thucHanh;
    }

    public String getMaMonHoc() {
        return maMonHoc;
    }

    public void setMaMonHoc(String maMonHoc) {
        this.maMonHoc = maMonHoc;
    }

    public String getTenMonHoc() {
        return tenMonHoc;
    }

    public void setTenMonHoc(String tenMonHoc) {
        this.tenMonHoc = tenMonHoc;
    }

    public int getLyThuyet() {
        return lyThuyet;
    }

    public void setLyThuyet(int lyThuyet) {
        this.lyThuyet = lyThuyet;
    }

    public int getThucHanh() {
        return thucHanh;
    }

    public void setThucHanh(int thucHanh) {
        this.thucHanh = thucHanh;
    }

    @Override
    public String toString() {
        return "MonHoc{" +
                "maMonHoc='" + maMonHoc + '\'' +
                ", tenMonHoc='" + tenMonHoc + '\'' +
                ", lyThuyet=" + lyThuyet +
                ", thucHanh=" + thucHanh +
                '}';
    }
}

