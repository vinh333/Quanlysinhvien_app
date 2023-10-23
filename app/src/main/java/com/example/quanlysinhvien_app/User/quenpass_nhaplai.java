package com.example.quanlysinhvien_app.User;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.quanlysinhvien_app.R;

public class quenpass_nhaplai extends AppCompatActivity {

    private EditText newPasswordEditText;
    private EditText confirmPasswordEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.quenmatkhau_nhaplai);

        newPasswordEditText = findViewById(R.id.editTextText3);
        confirmPasswordEditText = findViewById(R.id.editTextText4);
        TextView loginButton = findViewById(R.id.textView);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String newPassword = newPasswordEditText.getText().toString();
                String confirmPassword = confirmPasswordEditText.getText().toString();

                if (newPassword.isEmpty() || confirmPassword.isEmpty()) {
                    Toast.makeText(quenpass_nhaplai.this, "Vui lòng nhập mật khẩu mới và xác nhận mật khẩu.", Toast.LENGTH_SHORT).show();
                } else if (!newPassword.equals(confirmPassword)) {
                    Toast.makeText(quenpass_nhaplai.this, "Mật khẩu và xác nhận mật khẩu không khớp.", Toast.LENGTH_SHORT).show();
                } else {
                    // Xử lý đổi mật khẩu ở đây
                    Toast.makeText(quenpass_nhaplai.this, "Đổi mật khẩu thành công", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}

