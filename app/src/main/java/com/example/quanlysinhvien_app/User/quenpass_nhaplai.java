package com.example.quanlysinhvien_app.User;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.quanlysinhvien_app.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class quenpass_nhaplai extends AppCompatActivity {

    EditText emailEditText;
    EditText newPasswordEditText;
    EditText confirmPasswordEditText;
    Button changePasswordButton;

    FirebaseAuth mAuth;
    DatabaseReference databaseReference;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.quenmatkhau_nhaplai);

        mAuth = FirebaseAuth.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference("users");

        emailEditText = findViewById(R.id.emailEditText);
        newPasswordEditText = findViewById(R.id.editTextText3);
        confirmPasswordEditText = findViewById(R.id.editTextText4);
        changePasswordButton = findViewById(R.id.changePasswordButton);

        changePasswordButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String email = emailEditText.getText().toString();
                final String newPassword = newPasswordEditText.getText().toString();
                final String confirmPassword = confirmPasswordEditText.getText().toString();

                if (newPassword.equals(confirmPassword)) {
                    // Kiểm tra email có hợp lệ
                    if (isValidEmail(email)) {
                        mAuth.sendPasswordResetEmail(email)
                                .addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if (task.isSuccessful()) {
                                            // Thay đổi mật khẩu trong Firebase Authentication
                                            FirebaseUser user = mAuth.getCurrentUser();
                                            if (user != null) {
                                                user.updatePassword(newPassword)
                                                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                            @Override
                                                            public void onComplete(@NonNull Task<Void> task) {
                                                                if (task.isSuccessful()) {
                                                                    // Cập nhật mật khẩu mới lên cơ sở dữ liệu
                                                                    FirebaseUser currentUser = mAuth.getCurrentUser();
                                                                    if (currentUser != null) {
                                                                        String userId = currentUser.getUid();
                                                                        databaseReference.child(userId).child("password").setValue(newPassword);

                                                                        Toast.makeText(quenpass_nhaplai.this, "Mật khẩu đã được thay đổi thành công", Toast.LENGTH_SHORT).show();
                                                                    }
                                                                } else {
                                                                    Toast.makeText(quenpass_nhaplai.this, "Đã xảy ra lỗi trong quá trình thay đổi mật khẩu", Toast.LENGTH_SHORT).show();
                                                                }
                                                            }
                                                        });
                                            }
                                        } else {
                                            Toast.makeText(quenpass_nhaplai.this, "Email không hợp lệ hoặc không tồn tại", Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                });
                    } else {
                        Toast.makeText(quenpass_nhaplai.this, "Email không hợp lệ", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(quenpass_nhaplai.this, "Mật khẩu không khớp, vui lòng kiểm tra lại", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private boolean isValidEmail(String email) {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }
}
