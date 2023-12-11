package com.example.quanlysinhvien_app;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.quanlysinhvien_app.User.LoginActivity;

public class PinEntryActivity extends AppCompatActivity {
    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mapindau);

        EditText pinEntryEditText = findViewById(R.id.pinEntryEditText);
        Button submitPinButton = findViewById(R.id.submitPinButton);

        sharedPreferences = getSharedPreferences("PIN_PREFERENCES", MODE_PRIVATE);

        submitPinButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String enteredPin = pinEntryEditText.getText().toString();
                String savedPin = sharedPreferences.getString("pin_key", "");


                if (enteredPin.equals(savedPin)) {
                    // Mã PIN đúng, chuyển sang màn hình chính của ứng dụng
                    Intent intent = new Intent(PinEntryActivity.this, LockScreenActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                    startActivity(intent);

                    finish(); // Đóng màn hình nhập mã PIN
                } else {
                    Toast.makeText(PinEntryActivity.this, "Mã PIN không đúng. Vui lòng thử lại.", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}



