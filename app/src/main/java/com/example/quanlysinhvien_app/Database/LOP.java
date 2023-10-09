package com.example.quanlysinhvien_app.Database;



public class LOP {
    private String maLop;
    private String tenLop;
    private String maKhoaHoc;
    private String maHeDaoTao;
    private String maNganhHoc;

    public LOP(String maLop, String tenLop, String maKhoaHoc, String maHeDaoTao, String maNganhHoc) {
        this.maLop = maLop;
        this.tenLop = tenLop;
        this.maKhoaHoc = maKhoaHoc;
        this.maHeDaoTao = maHeDaoTao;
        this.maNganhHoc = maNganhHoc;
    }

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

    public String getMaNganhHoc() {
        return maNganhHoc;
    }

    public void setMaNganhHoc(String maNganhHoc) {
        this.maNganhHoc = maNganhHoc;
    }
}

