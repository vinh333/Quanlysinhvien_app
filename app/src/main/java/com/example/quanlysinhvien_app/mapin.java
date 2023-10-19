package com.example.quanlysinhvien_app;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class mapin extends AppCompatActivity {
    private EditText pinInput;
    private Button confirmButton;
    private Button deleteButton;
    private PinManager pinManager;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pin_setup_layout);

        pinManager = new PinManager(this);

        pinInput = findViewById(R.id.pinInput);
        confirmButton = findViewById(R.id.confirmButton);
        deleteButton = findViewById(R.id.deleteButton);

        confirmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String enteredPin = pinInput.getText().toString();
                if (enteredPin.length() == 6) {
                    // Lưu mã PIN
                    pinManager.setPin(enteredPin);
                    Toast.makeText(mapin.this, "Mã PIN đã được lưu.", Toast.LENGTH_SHORT).show();

                    // Chuyển đến trang chính của ứng dụng
                    Intent intent = new Intent(mapin.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                } else {
                    // Mã PIN không hợp lệ
                    Toast.makeText(mapin.this, "Mã PIN phải có 6 chữ số.", Toast.LENGTH_SHORT).show();
                }
            }
        });

        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Xóa mã PIN
                pinManager.clearPin();
                Toast.makeText(mapin.this, "Mã PIN đã bị xóa.", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
