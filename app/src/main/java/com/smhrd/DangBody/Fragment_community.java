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
import android.widget.ImageView;
import android.widget.TextView;

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

    TextView tvNick, tvContent, tvlikes;
    ImageView imgPost;
    RecyclerView rcv;
    Button btnWrite;

    ArrayList<CommunityVO> list = new ArrayList<CommunityVO>();
    RequestQueue requestQueue;
    StringRequest request;
    private int article_seq;
    private String article_content;
    private String article_file;
    private String article_date;
    private int like;
    private String user_nick;
    String content, date;
    int likes;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_community, container, false);

        //ViewGroup rootView = (ViewGroup)inflater.inflate(R.layout.fragment_community, container, false );
        tvNick = view.findViewById(R.id.tvNick);
        tvContent = view.findViewById(R.id.tvContent);
        tvlikes = view.findViewById(R.id.tvlikes);
        imgPost = view.findViewById(R.id.imgPost);

        rcv = view.findViewById(R.id.rcv);
        btnWrite = view.findViewById(R.id.btnWrite);
        CommunityAdapter adapter = new CommunityAdapter();
        if(requestQueue == null){
            requestQueue = Volley.newRequestQueue(getActivity());
        }
        Log.d("Fragment_community","들어옴");

        String url = "http://220.71.97.178:8082/dangbody/showCommunityService";
//        String imgUrl = "http://"
        request = new StringRequest(
                Request.Method.POST,
                url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("Fragment_community", response);

                        try {
                            JSONArray array = new JSONArray(response);
                            StringBuffer sb = new StringBuffer();
                            for (int i = 0; i<array.length(); i++){
                                JSONObject obj = (JSONObject) array.get(i);

                            article_seq = obj.getInt("article_seq");
                            article_content = obj.getString("article_content");
                            article_file = obj.getString("article_file");
                            article_date = obj.getString("article_date");
                            like = obj.getInt("like");
                            user_nick = obj.getString("user_nick");

                            sb.append(article_seq);
                            sb.append("/");
                            sb.append(article_content);
                            sb.append("/");
                            sb.append(article_file);
                            sb.append("/");
                            sb.append(article_date);
                            sb.append("/");
                            sb.append(like);
                            sb.append("/");
                            sb.append(user_nick);
                            sb.append("\n");

//                            tvNick.setText(user_nick);
//                            tvContent.setText(article_content);
//                            tvlikes.setText(String.valueOf(like));
                            adapter.addItem(new CommunityVO(like,user_nick,article_content,article_file));

                            }// end

                            LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity(),LinearLayoutManager.VERTICAL,false);
                            rcv.setLayoutManager(layoutManager);
                            rcv.setAdapter(adapter);

                            Log.d("yummy","파일경로"+article_file);
                            Log.d("yummy","user_nick"+user_nick);
                            Log.d("yummy","article_seq"+article_seq);

//                            tvNick.setText(user_nick);
//                            tvContent.setText(article_content);
//                            tvlikes.setText(String.valueOf(like));



                            // 고민해볼 부분
//                            JSONObject user = array.getJSONObject(1);
//                            JSONObject com = array.getJSONObject(0);
                            //JSONArray com = (JSONArray) array.get(0);

//                            Log.d("확인해보자", user.getString("user_nick"));
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

//        rcv.setLayoutManager(new LinearLayoutManager(getContext()));
//        rcv.setAdapter(adapter);

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