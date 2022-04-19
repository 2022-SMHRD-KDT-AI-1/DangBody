package com.smhrd.DangBody;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
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

import java.util.HashMap;
import java.util.Map;

public class WalkRecordActivity extends AppCompatActivity {

    RequestQueue queue;
    StringRequest request;
    TextView time, meters, date;
    SharedPreferences loginData;
    String userID;

    String walk_time, walk_distance, walk_date;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_walk_record);
        //로그인 정보
        loginData = getSharedPreferences("loginData",MODE_PRIVATE);

        time = findViewById(R.id.time);
        meters = findViewById(R.id.meters);
        date = findViewById(R.id.textView);


        userID = loginData.getString("user_id","없음");


        RecyclerView rvWalkRecord = findViewById(R.id.rvWalkRecord);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false );
        rvWalkRecord.setLayoutManager(layoutManager);

        WalkRecordAdapter adapter = new WalkRecordAdapter();

//        adapter.addItem(new WalkRecord("123","123","123"));
//        adapter.addItem(new WalkRecord("456","456","456"));
//
//
//
//        rvWalkRecord.setAdapter(adapter);

        if(queue == null){
            queue = Volley.newRequestQueue(WalkRecordActivity.this);
        }

        String serverUrl = "http://220.71.97.178:8082/dangbody/showRecord";

        // 서버에 요청한 후 응답데이터를 Log.d()로 출력하시오.
        request = new StringRequest(
                Request.Method.POST,
                serverUrl,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("WalkRecordActivity", response);

                        try {
                            JSONArray array = new JSONArray(response);

                            StringBuffer sb = new StringBuffer();

                            for (int i = 0; i<array.length(); i++){
                                JSONObject obj = (JSONObject) array.get(i);

                                walk_time = obj.getString("walk_time");
                                walk_distance = obj.getString("walk_distance");
                                walk_date = obj.getString("walk_date");

                                sb.append(walk_time);
                                sb.append("/");
                                sb.append(walk_distance);
                                sb.append("/");
                                sb.append(walk_date);
                                sb.append("\n");


                            }//end for
                            time.setText(sb.toString());
                            meters.setText(sb.toString());
                            date.setText(sb.toString());

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
                Map<String, String> params = new HashMap<>();
                params.put("user_id",userID);
                return params;
            }
        };

        // 서버요청하기
        queue.add(request);


        adapter.addItem(new WalkRecord(walk_time,walk_distance,walk_date));
        adapter.addItem(new WalkRecord("456","456","456"));



        rvWalkRecord.setAdapter(adapter);

    }


    }

