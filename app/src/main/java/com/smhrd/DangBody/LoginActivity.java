package com.smhrd.DangBody;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LoginActivity extends AppCompatActivity {

    EditText edtId,edtPw;
    Button btnLogin, btnJoin;
    RequestQueue requestQueue;
    StringRequest request;
    CheckBox checkBox;
    boolean saveLoginData;

    SharedPreferences loginData;

    String id,pw;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        loginData= getSharedPreferences("loginData", MODE_PRIVATE);
        load();

        init();


        if(saveLoginData){
            edtId.setText(id);
            edtPw.setText(pw);
            checkBox.setChecked(saveLoginData);
        }
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                id = edtId.getText().toString();
                pw = edtPw.getText().toString();

                loginConn();
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

    private void loginConn() {
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

                                String user = obj.getString("user"); // json형태의 String
                                JSONArray pet = obj.getJSONArray("list"); // list형태의 값을 JSONArray

                                JSONObject jsonUser = new JSONObject(user);// user는 jsonObj로 다시 변환


                                // user에 관한 정보 키를 통해 찾게 함
                                String userId = jsonUser.getString("user_id");
                                String userNick = jsonUser.getString("user_nick");
                                String userPw = jsonUser.getString("user_pw");
                                String petName = pet.get(0).toString();

                                Log.d("사용자","user_id는 "+userId+", user_nick은 "+userNick+", 첫 반려견 이름은 "+petName);


                                save(userId,userPw,userNick,petName);
                                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
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

    private void save(String userId, String userPw, String userNick, String petName){
        SharedPreferences.Editor editor = loginData.edit();

        editor.putBoolean("SAVE_LOGIN_DATA", checkBox.isChecked());
        editor.putString("user_id",userId);
        editor.putString("user_pw",userPw);
        editor.putString("user_nick",userNick);
        editor.putString("pet_name",petName);

        editor.commit();
    }

    private void load(){

    saveLoginData = loginData.getBoolean("SAVE_LOGIN_DATA",false);
    id = loginData.getString("user_id","");
    pw = loginData.getString("user_pw","");

    }

    // xml view 초기화
    private void init() {
        edtId=findViewById(R.id.edtId);
        edtPw=findViewById(R.id.edtPw);
        checkBox = findViewById(R.id.checkBox);
        btnLogin=findViewById(R.id.btnLogin);
        btnJoin=findViewById(R.id.btnJoin);

        if(requestQueue == null){
            requestQueue = Volley.newRequestQueue(getApplicationContext());
        }
    }
}