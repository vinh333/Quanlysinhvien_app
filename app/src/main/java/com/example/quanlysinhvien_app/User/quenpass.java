package com.example.quanlysinhvien_app.User;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import com.example.quanlysinhvien_app.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;

public class quenpass extends AppCompatActivity {

    private EditText emailEditText;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.quenmatkhau);

        // Liên kết các thành phần XML với mã Java
        emailEditText = findViewById(R.id.editTextText3);
        Button loginButton = findViewById(R.id.btnDN);

        // Khởi tạo Firebase Authentication
        mAuth = FirebaseAuth.getInstance();

        // Gắn sự kiện nhấn nút "Đăng nhập"
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Xử lý đăng nhập ở đây
                String email = emailEditText.getText().toString();
                if (!email.isEmpty()) {
                    checkEmailInFirebaseAuth(email);
                } else {
                    // Xử lý đăng nhập thất bại
                    Toast.makeText(quenpass.this, "Đăng nhập thất bại. Vui lòng nhập email.", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void checkEmailInFirebaseAuth(String email) {
        mAuth.fetchSignInMethodsForEmail(email)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        // Email hợp lệ, chuyển đến màn hình tiếp theo
                        if (task.getResult().getSignInMethods().isEmpty()) {
                            // Email không tồn tại trong Firebase Authentication
                            Toast.makeText(quenpass.this, "Email không tồn tại trong hệ thống.", Toast.LENGTH_SHORT).show();
                        } else {
                            // Email tồn tại trong Firebase Authentication
                            Intent intent = new Intent(quenpass.this, quenpass_nhaplai.class);
                            startActivity(intent);
                        }
                    } else {
                        // Xử lý lỗi truy vấn
                        String errorMessage = "Lỗi truy vấn: " + task.getException().getMessage();
                        Toast.makeText(quenpass.this, errorMessage, Toast.LENGTH_SHORT).show();
                    }
                });
    }
}
