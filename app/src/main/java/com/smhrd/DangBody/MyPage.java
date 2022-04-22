package com.smhrd.DangBody;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MyPage extends AppCompatActivity {
    TextView tv_pet, tv_family, tv_friend, tv_Member;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_page);

        tv_pet = findViewById(R.id.tv_pet);
        tv_family = findViewById(R.id.tv_family);
        tv_friend = findViewById(R.id.tv_friend);
        tv_Member = findViewById(R.id.tv_Member);


        tv_pet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent petJoinIntent = new Intent(MyPage.this, JoinPetActivity.class);
                startActivity(petJoinIntent);
            }
        });

        tv_friend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MyPage.this, JoinFriend.class);
                startActivity(intent);
            }
        });

        tv_Member.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent2 = new Intent(MyPage.this, Dangbody_Service.class);
                startActivity(intent2);
            }
        });

        tv_family.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent3 = new Intent(MyPage.this, JoinFamily.class);
                startActivity(intent3);
            }
        });

    }
}