package com.example.quanlysinhvien_app.Database;

public class Lop {
    private String maLop;
    private String tenLop;
    private String maKhoaHoc;
    private String maHeDaoTao;
    private String maNganh;

    public Lop(String maLop, String tenLop, String maKhoaHoc, String maHeDaoTao, String maNganh) {
        this.maLop = maLop;
        this.tenLop = tenLop;
        this.maKhoaHoc = maKhoaHoc;
        this.maHeDaoTao = maHeDaoTao;
        this.maNganh = maNganh;
    }

    // Getters and setters
    public String getMaLop() {
        return maLop;
    }

    public void setMaLop(String maLop) {
        this.maLop = maLop;
    }

    public String getTenLop() {
        return tenLop;
    }

    public void setTenLop(String tenLop) {
        this.tenLop = tenLop;
    }

    public String getMaKhoaHoc() {
        return maKhoaHoc;
    }

    public void setMaKhoaHoc(String maKhoaHoc) {
        this.maKhoaHoc = maKhoaHoc;
    }

    public String getMaHeDaoTao() {
        return maHeDaoTao;
    }

    public void setMaHeDaoTao(String maHeDaoTao) {
        this.maHeDaoTao = maHeDaoTao;
    }

    public String getMaNganh() {
        return maNganh;
    }

    public void setMaNganh(String maNganh) {
        this.maNganh = maNganh;
    }

    @Override
    public String toString() {
        return "Lop{" +
                "maLop='" + maLop + '\'' +
                ", tenLop='" + tenLop + '\'' +
                ", maKhoaHoc='" + maKhoaHoc + '\'' +
                ", maHeDaoTao='" + maHeDaoTao + '\'' +
                ", maNganh='" + maNganh + '\'' +
                '}';
    }
}

