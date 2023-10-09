package com.example.quanlysinhvien_app;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.drawable.RoundedBitmapDrawable;
import androidx.core.graphics.drawable.RoundedBitmapDrawableFactory;
import android.widget.ImageView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ImageView imageView = findViewById(R.id.imageView2);
        Bitmap originalBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.avt2); // Thay your_image bằng ID của ảnh

        // Tạo RoundedBitmapDrawable từ ảnh gốc
        RoundedBitmapDrawable roundedDrawable = RoundedBitmapDrawableFactory.create(getResources(), originalBitmap);
        roundedDrawable.setCircular(true);

        // Thiết lập RoundedBitmapDrawable cho ImageView
        imageView.setImageDrawable(roundedDrawable);

        // Write a message to the database
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("message");

        myRef.setValue("Hello, dasdsWasdasdadsfsddfdsorld!");
        myRef.setValue("Hello, dasdsWasdasdadsfsddfdsorld!");

    }
}
