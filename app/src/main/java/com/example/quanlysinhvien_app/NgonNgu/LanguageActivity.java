package com.example.quanlysinhvien_app.NgonNgu;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;

import com.example.quanlysinhvien_app.MainActivity;
import com.example.quanlysinhvien_app.R;

import java.util.Locale;

public class LanguageActivity extends AppCompatActivity {

    private Spinner languageSpinner;
    private Button applyButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_language);

        languageSpinner = findViewById(R.id.languageSpinner);
        applyButton = findViewById(R.id.applyButton);

        // Thiết lập spinner với danh sách các ngôn ngữ
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                this,
                R.array.language_options,  // Tạo một mảng resource trong strings.xml cho ngôn ngữ của bạn
                android.R.layout.simple_spinner_item
        );
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        languageSpinner.setAdapter(adapter);

        applyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                applyLanguageChange();
            }
        });
    }

    private String getSelectedLanguageCode(int position) {
        // Lấy mã ngôn ngữ được chọn từ vị trí của spinner
        switch (position) {
            case 0:
                return "en";  // Mã ngôn ngữ cho tiếng Anh
            case 1:
                return "vi";  // Mã ngôn ngữ cho tiếng Việt
            case 2:
                return "zh";  // Mã ngôn ngữ cho tiếng Trung
            case 3:
                return "ja";  // Mã ngôn ngữ cho tiếng Nhật
            default:
                return "vi";
        }
    }

    private void setLocale(String languageCode) {
        // Thiết lập ngôn ngữ được chọn cho ứng dụng
        Locale locale = new Locale(languageCode);
        Locale.setDefault(locale);
        Resources resources = getResources();
        Configuration configuration = resources.getConfiguration();
        configuration.setLocale(locale);
        resources.updateConfiguration(configuration, resources.getDisplayMetrics());
    }

    private void saveSelectedLanguage(String languageCode) {
        // Lưu ngôn ngữ đã chọn vào SharedPreferences
        SharedPreferences.Editor editor = getSharedPreferences("LanguagePrefs", MODE_PRIVATE).edit();
        editor.putString("selected_language", languageCode);
        editor.apply();
    }

    private void applyLanguageChange() {
        int selectedPosition = languageSpinner.getSelectedItemPosition();
        String selectedLanguageCode = getSelectedLanguageCode(selectedPosition);

        // Lưu ngôn ngữ đã chọn
        saveSelectedLanguage(selectedLanguageCode);

        // Thiết lập ngôn ngữ và chuyển sang MainActivity
        setLocale(selectedLanguageCode);
        Intent intent = new Intent(LanguageActivity.this, MainActivity.class);
        startActivity(intent);

        // Kết thúc LanguageChangeActivity để không thể quay lại từ MainActivity
        finish();
    }
}
