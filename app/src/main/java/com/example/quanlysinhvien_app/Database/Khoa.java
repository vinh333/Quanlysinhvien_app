package com.example.quanlysinhvien_app.Database;

public class Khoa {
    private String makhoa;
    private String tenkhoa;

    // Constructors
    public Khoa() {
    }

    public Khoa(String makhoa, String tenkhoa) {
        this.makhoa = makhoa;
        this.tenkhoa = tenkhoa;
    }

    // Getter and Setter methods
    public String getMakhoa() {
        return makhoa;
    }

    public void setMakhoa(String makhoa) {
        this.makhoa = makhoa;
    }

    public String getTenkhoa() {
        return tenkhoa;
    }

    public void setTenkhoa(String tenkhoa) {
        this.tenkhoa = tenkhoa;
    }
}
