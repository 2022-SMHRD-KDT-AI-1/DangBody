package com.smhrd.DangBody;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MyPage extends AppCompatActivity {
    TextView tv_pet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_page);

        tv_pet = findViewById(R.id.tv_pet);

        tv_pet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent petJoinIntent = new Intent(MyPage.this, JoinPetActivity.class);
                startActivity(petJoinIntent);
            }
        });
    }
}