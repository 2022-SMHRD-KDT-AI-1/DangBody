package com.smhrd.DangBody;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.android.volley.RequestQueue;

import java.io.File;

public class WriteActivity extends AppCompatActivity {



    EditText contentsInput;
    private ImageView imageView;
    private Button saveButton;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_write);

        imageView = findViewById(R.id.imageView);
        saveButton=findViewById(R.id.saveButton);

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(WriteActivity.this,CameraActivity.class);

                startActivity(intent);
            }
        });

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Intent intent = new Intent(WriteActivity.this, );

                Intent intent = new Intent(WriteActivity.this,Fragment_community.class);
                startActivity(intent);
            }
        });
  }
}