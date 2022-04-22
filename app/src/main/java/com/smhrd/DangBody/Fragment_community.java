package com.smhrd.DangBody;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Map;


public class Fragment_community extends Fragment {

    public Fragment_community() { }

    RecyclerView recyclerView;
    Button btnWrite;
    CommnityAdapter adapter;
    ArrayList<CommunityVO> list = new ArrayList<CommunityVO>();
    RequestQueue requestQueue;
    StringRequest request;

    String content, date;
    int likes;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_community, container, false);

        //ViewGroup rootView = (ViewGroup)inflater.inflate(R.layout.fragment_community, container, false );

        recyclerView = view.findViewById(R.id.rcv);
        btnWrite = view.findViewById(R.id.btnWrite);
        adapter = new CommnityAdapter();
        if(requestQueue == null){
            requestQueue = Volley.newRequestQueue(getActivity());
        }
        Log.d("Fragment_community","들어옴");

        String url = "http://220.71.97.178:8082/dangbody/showCommunityService";

        request = new StringRequest(
                Request.Method.POST,
                url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("Fragment_community", response);

                        try {
                            JSONArray array = new JSONArray(response);
                            // 고민해볼 부분
                            JSONObject user = array.getJSONObject(1);
                            JSONObject com = array.getJSONObject(0);
                            //JSONArray com = (JSONArray) array.get(0);

                            Log.d("확인해보자", user.getString("user_nick"));
                            //Log.d("확인해보자", com.get(0).toString());
                           /*
                            StringBuffer sb = new StringBuffer();

                            for(int i = 0 ; i<array.length(); i++){
                                JSONObject obj = (JSONObject) array.get(i);


                            }*/
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                }
        ){
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                return super.getParams();
            }
        };
        requestQueue.add(request);

/*
        adapter.addItem(new CommunityVO(R.drawable.base, R.drawable.ay, "DangBody_USER1", "안녕"));
        adapter.addItem(new CommunityVO(R.drawable.base, R.drawable.tori1,"DangBody_USER2", "안녕"));
        adapter.addItem(new CommunityVO(R.drawable.base, R.drawable.tori2, "DangBody_USER3", "안녕"));
        adapter.addItem(new CommunityVO(R.drawable.base, R.drawable.tori3, "DangBody_USER4", "안녕"));
        adapter.addItem(new CommunityVO(R.drawable.base, R.drawable.jy, "DangBody_USER5", "안녕"));*/

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