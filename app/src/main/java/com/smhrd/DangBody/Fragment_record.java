package com.smhrd.DangBody;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


public class Fragment_record extends Fragment {

    public Fragment_record() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_record, container, false);


        // 작은 양의 데이터를 저장할 때 사용하는 객체
        // 객체를 통해 데이터를 저장 > 파일 형태로 안드로이드에 저장
        // getSharedPreferences(파일명, Context.MODE_PRIVATE)
        // 파일명 : 안드로이드에 값이 저장된 파일이름
        // context.MODE_PRIVATE : 외부에서 파일 접근 금지 권한
        SharedPreferences spf = getActivity().getSharedPreferences("mySDF", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = spf.edit();


        return view;
    }
}