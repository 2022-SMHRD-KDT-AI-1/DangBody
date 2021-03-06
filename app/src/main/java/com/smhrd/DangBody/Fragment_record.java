package com.smhrd.DangBody;

import static com.smhrd.DangBody.CalendarUtils.daysInMonthArray;
import static com.smhrd.DangBody.CalendarUtils.monthYearFromDate;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TableLayout;
import android.widget.TextView;
import android.widget.Toast;

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

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class Fragment_record extends Fragment {

    public Fragment_record() {

    }
    TextView monthYearText;
    RecyclerView calendarRecyclerView;
    Button btnWeekly, btnPrevMonth, btnNextMonth;
    View view;
    CheckBox checkbox_todo;
    boolean check= false;


//    @RequiresApi(api = Build.VERSION_CODES.O)
    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

         view = inflater.inflate(R.layout.fragment_record, container, false);

        btnWeekly = view.findViewById(R.id.btnWeekly);
        btnPrevMonth = view.findViewById(R.id.btnPrevMonth);
        btnNextMonth = view.findViewById(R.id.btnNextMonth);
        //체크박스
        checkbox_todo = view.findViewById(R.id.checkbox_todo);



//        checkbox_todo.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                onCheckboxClicked(v);
//            }
//        });

//        if (checkbox_todo.isChecked()) {
//            // TODO : CheckBox is checked.
//            Toast.makeText(getActivity(), "체크된당!!!", Toast.LENGTH_LONG).show();
//        } else {
//            // TODO : CheckBox is unchecked.
//        }
//
//        checkbox_todo.setChecked(true) ;

        initWidgets();
        CalendarUtils.selectedDate = LocalDate.now();
        setMonthView();

//
//        checkbox_todo.setOnClickListener(new CheckBox.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (((CheckBox)v).isChecked()) {
//                    // TODO : CheckBox is checked.
//                    check = true;
//                } else {
//                    // TODO : CheckBox is unchecked.
//                    check=false;
//                }
//            }
//        }) ;


        // 작은 양의 데이터를 저장할 때 사용하는 객체
        // 객체를 통해 데이터를 저장 > 파일 형태로 안드로이드에 저장
        // getSharedPreferences(파일명, Context.MODE_PRIVATE)
        // 파일명 : 안드로이드에 값이 저장된 파일이름
        // context.MODE_PRIVATE : 외부에서 파일 접근 금지 권한
//        SharedPreferences spf = getActivity().getSharedPreferences("mySDF", Context.MODE_PRIVATE);
//        SharedPreferences.Editor editor = spf.edit();

        btnWeekly.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                weeklyAction(view);
            }
        });

        btnNextMonth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                nextMonthAction(view);
            }
        });

        btnPrevMonth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                previousMonthAction(view);
            }
        });


        return view;


    }

    private void initWidgets() {
        calendarRecyclerView = view.findViewById(R.id.calendarRecyclerView);
        monthYearText = view.findViewById(R.id.monthYearTV);
    }


    @RequiresApi(api = Build.VERSION_CODES.O)
    private void setMonthView()
    {
        monthYearText.setText(monthYearFromDate(CalendarUtils.selectedDate));
        ArrayList<LocalDate> daysInMonth = daysInMonthArray(CalendarUtils.selectedDate);

        CalendarAdapter calendarAdapter = new com.smhrd.DangBody.CalendarAdapter(daysInMonth,this::onItemClick );
        //수정 : this -> Calendar Adapter의 온아이템리스너로 바꿈
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getContext(), 7);
        //수정 : getApplicationContext() -> getContext()
        calendarRecyclerView.setLayoutManager(layoutManager);
        calendarRecyclerView.setAdapter(calendarAdapter);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void previousMonthAction(View view)
    {
        CalendarUtils.selectedDate = CalendarUtils.selectedDate.minusMonths(1);
        setMonthView();
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void nextMonthAction(View view)
    {
        CalendarUtils.selectedDate = CalendarUtils.selectedDate.plusMonths(1);
        setMonthView();
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    //@Override  나중에 보러오기 Calendar Adapter 관련
    public void onItemClick(int position, LocalDate date)
    {
        if(date != null)
        {
            CalendarUtils.selectedDate = date;
            setMonthView();
        }
    }
    public void weeklyAction(View view)
    {
        startActivity(new Intent(getActivity(), WeekViewActivity.class));
    }

//    public void onCheckboxClicked(View view){
//        if (checkbox_todo.isChecked()) {
//            // TODO : CheckBox is checked.
//            Toast.makeText(getContext(), "체크된당!!!", Toast.LENGTH_LONG).show();
//        } else {
//            // TODO : CheckBox is unchecked.
//        }
//
//
//        checkbox_todo.setChecked(true);
//    }
}
//
