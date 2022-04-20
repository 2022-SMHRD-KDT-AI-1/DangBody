package com.smhrd.DangBody;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.time.LocalTime;
import java.util.HashMap;
import java.util.Map;

public class EventEditActivity extends AppCompatActivity
{
    private EditText eventNameET;
    private TextView eventDateTV, eventTimeTV;
    private LocalTime time;
    private Button plusButton;
    RequestQueue requestQueue;
    StringRequest request;
    String reqTitle, reqDate, reqTime;
    //SharedPreferences sp = getSharedPreferences("loginData",MODE_PRIVATE);
    String user_id = "test";

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_edit);
        initWidgets();
        time = LocalTime.now();
        eventDateTV.setText("Date: " + CalendarUtils.formattedDate(CalendarUtils.selectedDate));
        eventTimeTV.setText("Time: " + CalendarUtils.formattedTime(time));
        //Log.d("사용자",sp.getString("user_id","널오지마"));

        reqDate = CalendarUtils.formattedDate(CalendarUtils.selectedDate);
        reqTime = CalendarUtils.formattedTime(time);
        reqTitle = eventNameET.getText().toString();

        plusButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String url = "http://220.71.97.178:8082/dangbody/RecordNoticeService";
                Log.d("버튼 클릭","완완!");
                request = new StringRequest(
                        Request.Method.POST,
                        url,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                Log.d("확인",response);
                                if(response.equals("0")){

                                    Toast.makeText(EventEditActivity.this, "일정 추가 실패", Toast.LENGTH_SHORT).show();
                                    Log.d("확인","로그인실패완");
                                }else{
                                    Log.d("응답",response);
                                    Toast.makeText(EventEditActivity.this, "일정 추가 성공", Toast.LENGTH_SHORT).show();
                                    //Log.d("확인","로그인성공완");
                                }
                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {

                            }
                        }
                )//end of request
                {
                    @Nullable
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        Map<String,String> param = new HashMap<>();
                        param.put("notice_title",reqTitle);
                        param.put("notice_date",reqDate);
                        param.put("notice_time",reqTime);
                        param.put("user_id", user_id);
                        return param;
                    }
                };
                requestQueue.add(request);
            }
        });
    }

    private void initWidgets()
    {
        eventNameET = findViewById(R.id.eventNameET);
        eventDateTV = findViewById(R.id.eventDateTV);
        eventTimeTV = findViewById(R.id.eventTimeTV);
        plusButton = findViewById(R.id.plusButton);

        if(requestQueue == null){
            requestQueue = Volley.newRequestQueue(getApplicationContext());
        }
    }

    /*public void saveEventAction(View view)
    {
        String eventName = eventNameET.getText().toString();
        com.smhrd.DangBody.Event newEvent = new com.smhrd.DangBody.Event(eventName, CalendarUtils.selectedDate, time);
        com.smhrd.DangBody.Event.eventsList.add(newEvent);



        finish();
    }*/

//    public void onCheckboxClicked(View view) {
//        // Is the view now checked?
//        boolean checked = ((CheckBox) view).isChecked();
//
//        // Check which checkbox was clicked

    }

