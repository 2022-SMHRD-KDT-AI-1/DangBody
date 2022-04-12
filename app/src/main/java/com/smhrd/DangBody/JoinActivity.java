package com.smhrd.DangBody;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

public class JoinActivity extends AppCompatActivity {

    EditText edt_joinEmail,edt_joinPw,edt_joinNick;
    Button btn_nickCheck, btn_joinApp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_join);

        edt_joinEmail = findViewById(R.id.edt_email);
        edt_joinPw = findViewById(R.id.edt_pw);
        edt_joinNick = findViewById(R.id.edt_joinNick);

        btn_nickCheck = findViewById(R.id.btn_nickCheck);
        btn_joinApp = findViewById(R.id.btn_joinApp);
    }
}