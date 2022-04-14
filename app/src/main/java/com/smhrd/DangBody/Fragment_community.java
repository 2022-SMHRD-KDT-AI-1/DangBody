package com.smhrd.DangBody;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import java.util.ArrayList;


public class Fragment_community extends Fragment {

    public Fragment_community() { }

    RecyclerView recyclerView;
    Button btnWrite;
    CommnityAdapter adapter;
    ArrayList<CommunityVO> list = new ArrayList<CommunityVO>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_community, container, false);

        //ViewGroup rootView = (ViewGroup)inflater.inflate(R.layout.fragment_community, container, false );

        recyclerView = view.findViewById(R.id.rcv);
        btnWrite = view.findViewById(R.id.btnWrite);
        adapter = new CommnityAdapter();

        adapter.addItem(new CommunityVO("js", "안녕"));
        adapter.addItem(new CommunityVO("br", "안녕"));
        adapter.addItem(new CommunityVO("hj", "안녕"));
        adapter.addItem(new CommunityVO("jw", "안녕"));
        adapter.addItem(new CommunityVO("sr", "안녕"));

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter);

        btnWrite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getActivity(), WriteActivity.class);
                startActivity(i);
            }
        });


        return view;

    }
}