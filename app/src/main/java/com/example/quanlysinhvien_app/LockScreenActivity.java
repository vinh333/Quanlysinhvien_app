package com.example.quanlysinhvien_app;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class LockScreenActivity extends AppCompatActivity {

    private StringBuilder passwordBuilder = new StringBuilder(); // Dùng để xây dựng mật khẩu
    private TextView[] passwordTextViews = new TextView[4]; // Mảng chứa TextViews của mật khẩu

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_passcode_view);

        // Khởi tạo TextViews của mật khẩu
        passwordTextViews[0] = findViewById(R.id.textView18);
        passwordTextViews[1] = findViewById(R.id.textView34);
        passwordTextViews[2] = findViewById(R.id.textView35);
        passwordTextViews[3] = findViewById(R.id.textView36);
    }

    // Phương thức xử lý khi một số được chọn
    public void onNumberClick(View view) {
        TextView clickedTextView = (TextView) view;
        String number = clickedTextView.getText().toString();

        // Thêm số vào mật khẩu nếu mật khẩu chưa đủ 4 ký tự
        if (passwordBuilder.length() < 4) {
            passwordBuilder.append(number);
            updatePasswordViews();
        }

        // Kiểm tra mật khẩu sau khi nhập và thực hiện các hành động cần thiết
        if (passwordBuilder.length() == 4) {
            checkPassword();
        }
    }

    // Phương thức xử lý khi nút Backspace được nhấn
    public void onBackspaceClick(View view) {
        if (passwordBuilder.length() > 0) {
            passwordBuilder.deleteCharAt(passwordBuilder.length() - 1);
            updatePasswordViews();
        }
    }

    // Phương thức cập nhật hiển thị của TextViews của mật khẩu
    private void updatePasswordViews() {
        for (int i = 0; i < passwordTextViews.length; i++) {
            if (i < passwordBuilder.length()) {
                passwordTextViews[i].setBackgroundResource(R.drawable.bg_circle); // Đặt hình ảnh nền
            } else {
                passwordTextViews[i].setText(""); // Xóa nếu không có số
            }
        }
    }

    // Phương thức kiểm tra mật khẩu và thực hiện các hành động cần thiết
    private void checkPassword() {
        String enteredPassword = passwordBuilder.toString();
        // Thực hiện kiểm tra mật khẩu ở đây và thực hiện các hành động cần thiết

        // Ví dụ: Hiển thị một thông báo (cần thay đổi theo nhu cầu của bạn)
        if ("1234".equals(enteredPassword)) {
            // Mã PIN đúng, chuyển sang màn hình chính của ứng dụng
            Intent intent = new Intent(LockScreenActivity.this, MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
            startActivity(intent);

            finish(); // Đóng màn hình nhập mã PIN
        } else {
            // Mật khẩu sai, thực hiện các hành động cần thiết (ví dụ: thông báo)
            // Toast.makeText(this, "Mật khẩu không đúng", Toast.LENGTH_SHORT).show();
        }

        // Sau khi kiểm tra mật khẩu, bạn có thể reset mật khẩu để người dùng nhập lại
        passwordBuilder.setLength(0);
        updatePasswordViews();
    }
}

