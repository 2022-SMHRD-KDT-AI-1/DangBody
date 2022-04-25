package com.smhrd.DangBody;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

public class JoinActivity extends AppCompatActivity {

    EditText edtJoinId, edtJoinPw, edtJoinPwcheck, edtJoinNick, edtJoinName, edtJoinBirt,edtJoinPhone;
    Button btnJoin2;
    RequestQueue requestQueue;
    StringRequest request;
    ImageView checkImg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_join);

        btnJoin2 = findViewById(R.id.btnJoin2);

        init();


        edtJoinPwcheck.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(edtJoinPw.getText().toString().equals(edtJoinPwcheck.getText().toString())){
                    checkImg.setImageResource(R.drawable.check);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        spinner();

        btnJoin2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String id = edtJoinId.getText().toString();
                String pw = edtJoinPw.getText().toString();
                String name = edtJoinName.getText().toString();
                String nick = edtJoinNick.getText().toString();
                String phone = edtJoinPhone.getText().toString();
                Spinner spy = findViewById(R.id.spinner_year);
                Spinner spm = findViewById(R.id.spinner_month);
                Spinner spd = findViewById(R.id.spinner_day);

                String year = spy.getSelectedItem().toString();
                String month = spm.getSelectedItem().toString();
                String day = spd.getSelectedItem().toString();
                Log.d("확인",year+month+day);

                String url = "http://3.19.217.154:8080/dangbody/JoinService";
                Log.d("확인","클릭완");

                request = new StringRequest(
                        Request.Method.POST,
                        url,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                if(response.equals("0")){
                                    Toast.makeText(JoinActivity.this, "회원가입 실패",Toast.LENGTH_SHORT).show();
                                }else{
                                    Toast.makeText(JoinActivity.this,"회원가입 성공",Toast.LENGTH_SHORT).show();
//                                    Intent intent = new Intent(JoinActivity.this, MainActivity.class);
//                                    startActivity(intent);

                                }
                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {

                            }
                        }){
                    @Nullable
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        Map<String, String> param = new HashMap<>();
                        param.put("user_id", id);
                        param.put("user_pw", pw);
                        param.put("user_name", name);
                        param.put("user_nick", nick);
                        param.put("user_birthy", year);
                        param.put("user_birthm", month);
                        param.put("user_birthd", day);
                        param.put("user_phone", phone);
                        return param;
                    }
                };
                requestQueue.add(request);
                Intent intent = new Intent(JoinActivity.this,JoinPetActivity.class);
                startActivity(intent);
            }
        });
    }

    private void init() {
        edtJoinId = findViewById(R.id.edtJoinId);
        edtJoinPw = findViewById(R.id.edtJoinPw);
        edtJoinPwcheck = findViewById(R.id.edtJoinPwcheck);
        edtJoinName = findViewById(R.id.edtJoinName);
        edtJoinNick = findViewById(R.id.edtJoinNick);
        edtJoinPhone = findViewById(R.id.edtJoinPhone);
        checkImg = findViewById(R.id.checkImg);

        btnJoin2 = findViewById(R.id.btnJoin2);

        if(requestQueue == null){
            requestQueue = Volley.newRequestQueue(getApplicationContext());
        }
    }


    private void spinner() {
        Spinner yearSpinner = (Spinner)findViewById(R.id.spinner_year);
        ArrayAdapter yearAdapter = ArrayAdapter.createFromResource(this,
                R.array.date_year, android.R.layout.simple_spinner_item);
        yearAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        yearSpinner.setAdapter(yearAdapter);

        Spinner monthSpinner = (Spinner)findViewById(R.id.spinner_month);
        ArrayAdapter monthAdapter = ArrayAdapter.createFromResource(this,
                R.array.date_month, android.R.layout.simple_spinner_item);
        monthAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        monthSpinner.setAdapter(monthAdapter);

        Spinner daySpinner = (Spinner)findViewById(R.id.spinner_day);
        ArrayAdapter dayAdapter = ArrayAdapter.createFromResource(this,
                R.array.date_day, android.R.layout.simple_spinner_item);
        dayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        daySpinner.setAdapter(dayAdapter);
    }

}