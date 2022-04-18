package com.smhrd.DangBody;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class LoginActivity extends AppCompatActivity {

    EditText edtId,edtPw;
    Button btnLogin, btnJoin;
    RequestQueue requestQueue;
    StringRequest request;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        init();


        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String id = edtId.getText().toString();
                String pw = edtPw.getText().toString();

                String url = "http://220.71.97.178:8082/dangbody/LoginService";
                //Log.d("확인","클릭완");
                request = new StringRequest(
                        Request.Method.POST,
                        url,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                //Log.d("Test", response);

                                if(response.equals("0")){
                                    Toast.makeText(LoginActivity.this, "로그인 실패", Toast.LENGTH_SHORT).show();
                                    //Log.d("확인","로그인실패완");
                                }else{
                                    Log.d("응답",response);
                                    Toast.makeText(LoginActivity.this, "로그인 성공", Toast.LENGTH_SHORT).show();
                                    //Log.d("확인","로그인성공완");

                                    try {



                                        JSONObject obj = new JSONObject(response);

                                        String userId = obj.getString("user_id");
                                        String userNick = obj.getString("user_nick");

                                        Log.d("사용자",userId+"/"+userNick);

                                        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                                        intent.putExtra("user_nick",userNick);

                                        startActivity(intent);

                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
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
                        return param;
                    }
                };
                requestQueue.add(request);
            }
        });

        btnJoin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, JoinActivity.class);
                startActivity(intent);
            }
        });

    }

    // xml view 초기화
    private void init() {
        edtId=findViewById(R.id.edtId);
        edtPw=findViewById(R.id.edtPw);

        btnLogin=findViewById(R.id.btnLogin);
        btnJoin=findViewById(R.id.btnJoin);

        if(requestQueue == null){
            requestQueue = Volley.newRequestQueue(getApplicationContext());
        }
    }
}