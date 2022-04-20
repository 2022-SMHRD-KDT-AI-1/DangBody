package com.smhrd.DangBody;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class EventAdapter extends ArrayAdapter<com.smhrd.DangBody.Event>
{
    CheckBox checkbox_todo;
    View view;
    SharedPreferences sp = getContext().getSharedPreferences("loginData",Context.MODE_PRIVATE);
    String user_id=sp.getString("user_id","이름없음34445");
    private RequestQueue requestQueue;
    private StringRequest request;
    String date, time, title;



    public EventAdapter(@NonNull Context context, List<com.smhrd.DangBody.Event> events)
    {

        super(context, 0, events);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent)
    {
        com.smhrd.DangBody.Event event = getItem(position);



        if (convertView == null)
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.event_cell, parent, false);

            TextView eventCellTV = convertView.findViewById(R.id.eventCellTV);

            String eventTitle = event.getName() +" "+ CalendarUtils.formattedTime(event.getTime());
            eventCellTV.setText(eventTitle);

//        체크박스
        checkbox_todo = convertView.findViewById(R.id.checkbox_todo);
        checkbox_todo.setChecked(false);
        checkbox_todo.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                onCheckboxClicked(view);
            }
        });

    //        Log.d("EventAdapter","사용자 아이디 가지고옵시다"+user_id);

        if(requestQueue == null){
            requestQueue = Volley.newRequestQueue(getContext());
        }


        return convertView;
    }

    public void onCheckboxClicked(View view){
        if (checkbox_todo.isChecked()) {
            // TODO : CheckBox is checked.
            Toast.makeText(getContext(), "체크된당!!!", Toast.LENGTH_LONG).show();
            checkbox_todo.setChecked(true);
        } else {
            // TODO : CheckBox is unchecked.
            checkbox_todo.setChecked(false);
        }



    }

}

