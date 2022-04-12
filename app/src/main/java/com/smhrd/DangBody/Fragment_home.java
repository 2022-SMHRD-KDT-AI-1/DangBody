package com.smhrd.DangBody;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;

import com.smhrd.DangBody.MyPage;


public class Fragment_home extends Fragment {

    ImageView img_dangBody, img_activity, img_record, img_myPage;

    public Fragment_home() { }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        // 프래그먼트에 쓰일 뷰들을 정의하고 초기화 (id랑 이미지파일을 매칭시켜주는 것)
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        img_dangBody= (ImageView) view.findViewById(R.id.img_dangBody);
        img_activity= (ImageView) view.findViewById(R.id.img_activity);
        img_record=(ImageView) view.findViewById(R.id.img_record);
        img_myPage=(ImageView) view.findViewById(R.id.img_myPage);

        // 댕바디 이미지 띄워주기
        img_dangBody.setImageResource(R.drawable.dang1);
        img_activity.setImageResource(R.drawable.dang1);
        img_record.setImageResource(R.drawable.dang1);

        // 카메라 액티비티로 이동하기
        img_dangBody.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent i = new Intent(getActivity(), CameraActivity.class);
                startActivity(i);

            }
        });

        img_myPage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent i = new Intent(getActivity(), MyPage.class);
                startActivity(i);
            }
        });


        return view;



    }
}