package com.smhrd.DangBody;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

public class LoginActivity extends AppCompatActivity {

    EditText edt_email,edt_pw;
    Button btn_login, btn_join;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        edt_email=findViewById(R.id.edt_email);
        edt_pw=findViewById(R.id.edt_pw);

        btn_login=findViewById(R.id.btn_login);
        btn_join=findViewById(R.id.btn_join);
    }
}