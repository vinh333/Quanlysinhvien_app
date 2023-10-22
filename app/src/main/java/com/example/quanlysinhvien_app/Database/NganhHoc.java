package com.example.quanlysinhvien_app.Database;

public class NganhHoc {
    private String makhoa;
    private String manganh;
    private String tennganh;

    // Constructors
    public NganhHoc() {
    }

    public NganhHoc(String makhoa, String manganh, String tennganh) {
        this.makhoa = makhoa;
        this.manganh = manganh;
        this.tennganh = tennganh;
    }

    // Getter and Setter methods
    public String getMakhoa() {
        return makhoa;
    }

    public void setMakhoa(String makhoa) {
        this.makhoa = makhoa;
    }

    public String getManganh() {
        return manganh;
    }

    public void setManganh(String manganh) {
        this.manganh = manganh;
    }

    public String getTennganh() {
        return tennganh;
    }

    public void setTennganh(String tennganh) {
        this.tennganh = tennganh;
    }
}

