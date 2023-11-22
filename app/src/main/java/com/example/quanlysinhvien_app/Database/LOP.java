package com.example.quanlysinhvien_app.Database;

public class LOP {
    private String maLop;
    private String tenLop;
    private String maKhoa;

    public LOP(String maLop, String tenLop, String maKhoa) {
        this.maLop = maLop;
        this.tenLop = tenLop;
        this.maKhoa = maKhoa;
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

    public String getMaKhoa() {
        return maKhoa;
    }

    public void setMaKhoa(String maKhoa) {
        this.maKhoa = maKhoa;
    }

    @Override
    public String toString() {
        // Hiển thị theo định dạng "MaLop: TenLop"
        return maLop + ": " + tenLop;
    }
}
