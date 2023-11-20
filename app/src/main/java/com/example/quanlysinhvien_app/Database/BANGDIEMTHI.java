package com.example.quanlysinhvien_app.Database;

public class BANGDIEMTHI {
    private String maSV;
    private String maMonHoc;
    private String lanThi;
    private String hocKy;
    private double diem;

    public BANGDIEMTHI(String maSV, String maMonHoc, String lanThi, String hocKy, double diem) {
        this.maSV = maSV;
        this.maMonHoc = maMonHoc;
        this.lanThi = lanThi;
        this.hocKy = hocKy;
        this.diem = diem;
    }

    // Getter and Setter methods for maSV
    public String getMaSV() {
        return maSV;
    }

    public void setMaSV(String maSV) {
        this.maSV = maSV;
    }

    // Getter and Setter methods for maMonHoc
    public String getMaMonHoc() {
        return maMonHoc;
    }

    public void setMaMonHoc(String maMonHoc) {
        this.maMonHoc = maMonHoc;
    }

    // Getter and Setter methods for lanThi
    public String getLanThi() {
        return lanThi;
    }

    public void setLanThi(String lanThi) {
        this.lanThi = lanThi;
    }

    // Getter and Setter methods for hocKy
    public String getHocKy() {
        return hocKy;
    }

    public void setHocKy(String hocKy) {
        this.hocKy = hocKy;
    }

    // Getter and Setter methods for diem
    public double getDiem() {
        return diem;
    }

    public void setDiem(double diem) {
        this.diem = diem;
    }
}
