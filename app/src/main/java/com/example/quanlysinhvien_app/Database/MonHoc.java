package com.example.quanlysinhvien_app.Database;

public class MonHoc {
    private String maMonHoc;
    private String tenMonHoc;
    private int tongTinChi;

    public MonHoc() {
        // Empty constructor required for Firebase
    }

    public MonHoc(String maMonHoc, String tenMonHoc, int tongTinChi) {
        this.maMonHoc = maMonHoc;
        this.tenMonHoc = tenMonHoc;
        this.tongTinChi = tongTinChi;
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

    public int getTongTinChi() {
        return tongTinChi;
    }

    public void setTongTinChi(int tongTinChi) {
        this.tongTinChi = tongTinChi;
    }
}

