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
    TextView time, meters, date, tvtop, tvCount, tvCountd, tvCountt;
    SharedPreferences loginData;
    String userID;

    String walk_time, walk_distance, walk_date, SumD2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_walk_record);
        //로그인 정보
        tvtop = findViewById(R.id.tvtop);
        tvCount = findViewById(R.id.tvCount);
        tvCountd = findViewById(R.id.tvCountd);
        tvCountt = findViewById(R.id.tvCountt);
        loginData = getSharedPreferences("loginData",MODE_PRIVATE);

//        time = findViewById(R.id.time);
//        meters = findViewById(R.id.meters);
//        date = findViewById(R.id.textView);


        userID = loginData.getString("user_id","없음");

        RecyclerView rvWalkRecord = findViewById(R.id.rvWalkRecord);
        WalkRecordAdapter adapter = new WalkRecordAdapter();
//        adapter.addItem(new WalkRecord("123","123","123"));
//        adapter.addItem(new WalkRecord("456","456","456"));
//        rvWalkRecord.setAdapter(adapter);
        tvtop.setText(loginData.getString("pet_name","멍멍이") + "의 산책 기록입니다.");
        if(queue == null){
            queue = Volley.newRequestQueue(WalkRecordActivity.this);
        }

        String serverUrl = "http://dangbody.ddns.net:8080/dangbody/showRecord";

        // 서버에 요청한 후 응답데이터를 Log.d()로 출력하시오.
        request = new StringRequest(
                Request.Method.POST,
                serverUrl,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("WalkRecordActivity", response);

                        try {
                            double SumD = 0;
                            int count = 0;
                            int hour = 0;
                            int minute = 0;
                            int sumTime = 0;
                            JSONArray array = new JSONArray(response);

                            StringBuffer sb = new StringBuffer();

                            for (int i = 0; i<array.length(); i++){
                                JSONObject obj = (JSONObject) array.get(i);

                                walk_time = obj.getString("walk_time");
                                walk_distance = obj.getString("walk_distance");
                                double wd = Double.parseDouble(walk_distance);

                                String wth = walk_time.substring(0,2);
                                String wtm = walk_time.substring(walk_time.length()-2,walk_time.length());
                                hour = Integer.parseInt(wth);
                                minute = Integer.parseInt(wtm);
                                sumTime += hour*60 + minute;
                                walk_date = obj.getString("walk_date");



                                SumD += wd;
                                count ++;

                                sb.append(walk_time);
                                sb.append("/");
                                sb.append(walk_distance);
                                sb.append("/");
                                sb.append(walk_date);
                                sb.append("\n");

                                Log.d("main","walk_time" + walk_time);
                                Log.d("main","walk_time" + walk_date);
                                Log.d("main","walk_time" + walk_distance);

                                if(wd < 1 && wd!=0) {
                                    adapter.addItem(new WalkRecord(walk_time, "0"+walk_distance + "km", walk_date));
                                }
                                else{
                                    adapter.addItem(new WalkRecord(walk_time, walk_distance + "km", walk_date));
                                }


                            }//end for

                            hour = sumTime/60;
                            minute = sumTime%60;
//                            time.setText(sb.toString());
//                            meters.setText(sb.toString());
//                            date.setText(sb.toString());

//                            adapter.addItem(new WalkRecord("456","456","456"));
//                            adapter.addItem(new WalkRecord("454","454","454"));

                            LinearLayoutManager layoutManager = new LinearLayoutManager(WalkRecordActivity.this,LinearLayoutManager.VERTICAL,false );
                            rvWalkRecord.setLayoutManager(layoutManager);
                            rvWalkRecord.setAdapter(adapter);
                            SumD2 = String.format("%.1f",SumD);

                            tvCount.setText(String.valueOf(count));
//                            tvCountd.setText(String.valueOf(SumD));
                            tvCountd.setText(SumD2);

                            if(minute<10){
                                tvCountt.setText("0"+String.valueOf(hour) + ":" + "0"+String.valueOf(minute));
                            }
                            else {
                                tvCountt.setText(String.valueOf(hour) + ":" + String.valueOf(minute));
                            }
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
    }


    }

