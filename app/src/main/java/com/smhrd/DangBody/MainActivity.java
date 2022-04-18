package com.smhrd.DangBody;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.app.Activity;
import android.app.FragmentManager;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebViewClient;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

public class MainActivity extends AppCompatActivity {

    BottomNavigationView bottomNav;
    Fragment_home fragmentHome;
    Fragment_activity fragmentActivity;
    Fragment_record fragmentRecord;
    Fragment_community fragmentCommunity;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        bottomNav = findViewById(R.id.bottomNav);
        fragmentHome = new Fragment_home();
        fragmentActivity = new Fragment_activity();
        fragmentRecord = new Fragment_record();
        fragmentCommunity = new Fragment_community();

        // 액티비티에 보여질 첫 프래그먼트 화면 설정
        //getSupportFragmentManager().beginTransaction().replace(R.id.frame, fragmentHome).commit();
        getSupportFragmentManager().beginTransaction().replace(R.id.frame, fragmentHome).commit();

        bottomNav.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                int pos = item.getItemId();

                if(pos == R.id.item1){
                    getSupportFragmentManager().beginTransaction().replace(R.id.frame, fragmentHome).commit();

                }else if(pos == R.id.item2){
                    getSupportFragmentManager().beginTransaction().replace(R.id.frame, fragmentActivity).commit();

                }else if (pos == R.id.item3){
                    getSupportFragmentManager().beginTransaction().replace(R.id.frame, fragmentRecord).commit();

                }else {
                    getSupportFragmentManager().beginTransaction().replace(R.id.frame, fragmentCommunity).commit();
                }
                return true; // 선택한 메뉴 활성화
            }
        });

    }

}