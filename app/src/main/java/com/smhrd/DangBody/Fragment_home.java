package com.smhrd.DangBody;

import android.animation.LayoutTransition;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.transition.AutoTransition;
import android.transition.TransitionManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.ViewFlipper;

import com.smhrd.DangBody.MyPage;

import java.util.Random;


public class Fragment_home extends Fragment {

    ImageView img_dangBody, img_myPage;
    Button btn_dangbodyCamera, btn_dangbodyGallery, btn_testLogin;
    LinearLayout layout;
    TextView details;
    CardView cardView;
    ViewPager viewPager;


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

//         댕바디 테스트용 로그인 띄워주기
        btn_testLogin=view.findViewById(R.id.btn_testLogin);
        btn_testLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent i = new Intent(getActivity(), LoginActivity.class);
                startActivity(i);
            }
        });

        // 댕바디 정보 띄워주기
        layout = view.findViewById(R.id.layout);
        details = view.findViewById(R.id.details);
        cardView = view.findViewById(R.id.cardview);
        layout.getLayoutTransition().enableTransitionType(LayoutTransition.CHANGING);

        cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                expand();
            }
        });





//        String[]randomTxt = getResources().getStringArray(R.array.randomTxt);
//        Random random = new Random();
//        int n = random.nextInt(randomTxt.length - 1);
//
//        details.setText(randomTxt[n]);

     //   onCreate 함수 안에 drawable 안에 있는 이미지를 사용할 변수.



//        int images[] = {
//                R.drawable.image_slide,
//                R.drawable.image_slide2,
//                R.drawable.image_slide3
//        };


//        //뷰 페이저 초기화
        viewPager = (ViewPager) view.findViewById(R.id.viewpager);

        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(getActivity());
        viewPager.setAdapter(viewPagerAdapter);

//        for(int image : images) {
//            fllipperImages(image);
//        }



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

//    public void fllipperImages(int image) {
//        ImageView imageView = new ImageView(getActivity());
//        imageView.setBackgroundResource(image);
//
//        v_fllipper.addView(imageView);      // 이미지 추가
//        v_fllipper.setFlipInterval(4000);       // 자동 이미지 슬라이드 딜레이시간(1000 당 1초)
//        v_fllipper.setAutoStart(true);          // 자동 시작 유무 설정
//
//        // animation
//        v_fllipper.setInAnimation(getActivity(),android.R.anim.slide_in_left);
//        v_fllipper.setOutAnimation(getActivity(),android.R.anim.slide_out_right);




//    }

    private void expand() {
        int v = (details.getVisibility()==View.GONE)? View.VISIBLE:View.GONE;
        TransitionManager.beginDelayedTransition(layout, new AutoTransition());
        details.setVisibility(v);
    }


}
