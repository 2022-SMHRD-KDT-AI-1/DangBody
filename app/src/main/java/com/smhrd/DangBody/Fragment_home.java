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
import android.widget.Button;
import android.widget.ImageView;

import com.smhrd.DangBody.MyPage;


public class Fragment_home extends Fragment {

    ImageView img_dangBody, img_myPage;
    Button btn_dangbodyCamera, btn_dangbodyGallery;


    public Fragment_home() { }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        // 프래그먼트에 쓰일 뷰들을 정의하고 초기화 (id랑 이미지파일을 매칭시켜주는 것)
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        img_dangBody= (ImageView) view.findViewById(R.id.img_dangBody);
        img_myPage=(ImageView) view.findViewById(R.id.img_myPage);

        btn_dangbodyCamera=view.findViewById(R.id.btn_dangbodyCamera);
        btn_dangbodyGallery=view.findViewById(R.id.btn_dangbodyGallery);

        // 댕바디 이미지 띄워주기
        img_dangBody.setImageResource(R.drawable.img_dangbody);




        // 카메라 액티비티로 이동하기(이미지클릭으로)
        img_dangBody.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent i = new Intent(getActivity(), CameraActivity.class);
                startActivity(i);

            }
        });

        // 카메라 액티비티로 이동하기(버튼클릭으로)
        btn_dangbodyCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent i = new Intent(getActivity(), CameraActivity.class);
                startActivity(i);
            }
        });

        // 댕바디 갤러리로 이동하기(버튼클릭으로)
        btn_dangbodyGallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent i = new Intent(getActivity(), DangbodyGallery.class);
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