package com.example.quanlysinhvien_app;

import android.content.Context;
import android.content.SharedPreferences;

public class PinManager {
    private static final String PIN_PREFS_NAME = "PIN_PREFERENCES";
    private static final String PIN_KEY = "pin_key";

    private SharedPreferences sharedPreferences;

    public PinManager(Context context) {
        sharedPreferences = context.getSharedPreferences(PIN_PREFS_NAME, Context.MODE_PRIVATE);
    }

    public void setPin(String pin) {
        sharedPreferences.edit().putString(PIN_KEY, pin).apply();
    }

    public String getPin() {
        return sharedPreferences.getString(PIN_KEY, null);
    }

    public void clearPin() {
        sharedPreferences.edit().remove(PIN_KEY).apply();
    }
}
