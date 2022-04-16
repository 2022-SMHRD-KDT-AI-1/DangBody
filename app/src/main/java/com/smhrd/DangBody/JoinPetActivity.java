package com.smhrd.DangBody;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;
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

public class JoinPetActivity extends AppCompatActivity {
    SharedPreferences sp;
    /* Pet 등록 */

    TextView edtPetName, edtPetWeight;
    Button btnJoinPet;
    RadioButton genderFemale, genderMale, neutralYes, neutralNo;
    Spinner spinnerPetBirthYear, spinnerPetBirthMonth, spinnerPetBirthDay, spinnerPetYear, spinnerPetMonth, spinnerPetDay;
    String petGender;
    String petNeutral;

    String name,weight, petBYear, petBMonth, petBDay,petYear,petMonth,petDay,user_id;

    RequestQueue requestQueue;
    StringRequest request;

    final String TEST="testtest";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_join_pet);

        init();

        spinner();

        btnJoinPet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                getText();


                Log.d(TEST, weight);
                Log.d(TEST, petBDay);
                Log.d(TEST, petYear);
                Log.d(TEST, petGender);

                String url = "http://220.71.97.178:8082/TEST/JoinPetService";
                Log.d("확인","클릭완");
                Intent idIntent = getIntent();

                request = new StringRequest(
                        Request.Method.POST,
                        url,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                if(response.equals("0")){
                                    Toast.makeText(JoinPetActivity.this, "회원가입 실패",Toast.LENGTH_SHORT).show();
                                }else{
                                    Toast.makeText(JoinPetActivity.this,"회원가입 성공",Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(JoinPetActivity.this, MainActivity.class);
                                    startActivity(intent);

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
                        param.put("pet_name", name);
                        param.put("pet_birthy", petBYear);
                        param.put("pet_birthm", petBMonth);
                        param.put("pet_birthd", petBDay);
                        param.put("pet_year", petYear);
                        param.put("pet_month", petMonth);
                        param.put("pet_day", petDay);
                        param.put("pet_gender", petGender);
                        param.put("pet_neutral", petNeutral);
                        param.put("pet_weight", weight);
                        param.put("user_id",user_id);
                        return param;
                    }
                };
                requestQueue.add(request);


            }
        });
    }
    // 입력받은 값 가져오기
    private void getText() {
        Log.d(TEST, "getText()");
        name = edtPetName.getText().toString();
        weight = edtPetWeight.getText().toString();

        petBYear = spinnerPetBirthYear.getSelectedItem().toString();
        petBMonth = spinnerPetBirthMonth.getSelectedItem().toString();
        petBDay = spinnerPetBirthDay.getSelectedItem().toString();

        petYear = spinnerPetYear.getSelectedItem().toString();
        petMonth = spinnerPetMonth.getSelectedItem().toString();
        petDay = spinnerPetDay.getSelectedItem().toString();

        petGender = getGender();

        petNeutral = getPetNeutral();

        sp = getSharedPreferences("autoLogin",0);
        user_id = sp.getString("userId","idnotfound").toString();
    }

    private String getGender() {
        String gender = "";
        if(genderFemale.isChecked()){
            gender = "F";
        }else if(genderMale.isChecked()) {
            gender = "M";
        }return gender;
    }

    private String getPetNeutral() {
        String n = "";
        if(neutralYes.isChecked()){
            n = "Y";
        }else if(neutralNo.isChecked()) {
            n = "N";
        }return n;
    }

    private void init() {
        edtPetName = findViewById(R.id.edtPetName);
        edtPetWeight = findViewById(R.id.edtPetWeight);
        btnJoinPet = findViewById(R.id.btnJoinPet);

        genderFemale = findViewById(R.id.genderFemale);
        genderMale = findViewById(R.id.genderMale);

        neutralNo = findViewById(R.id.neutralNo);
        neutralYes = findViewById(R.id.neutralYes);

        spinnerPetBirthYear = findViewById(R.id.spinner_petbirth_year);
        spinnerPetBirthMonth = findViewById(R.id.spinner_petbirth_month);
        spinnerPetBirthDay= findViewById(R.id.spinner_petbirth_day);
        spinnerPetYear = findViewById(R.id.spinner_pet_year);
        spinnerPetMonth = findViewById(R.id.spinner_pet_month);
        spinnerPetDay = findViewById(R.id.spinner_pet_day);

        if(requestQueue == null){
            requestQueue = Volley.newRequestQueue(getApplicationContext());
        }
    }

    private void spinner(){
        ArrayAdapter yearAdapter = ArrayAdapter.createFromResource(this,
                R.array.date_year, android.R.layout.simple_spinner_item);
        yearAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerPetBirthYear.setAdapter(yearAdapter);
        spinnerPetYear.setAdapter(yearAdapter);

        ArrayAdapter monthAdapter = ArrayAdapter.createFromResource(this,
                R.array.date_month, android.R.layout.simple_spinner_item);
        monthAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerPetBirthMonth.setAdapter(monthAdapter);
        spinnerPetMonth.setAdapter(monthAdapter);

        ArrayAdapter dayAdapter = ArrayAdapter.createFromResource(this,
                R.array.date_day, android.R.layout.simple_spinner_item);
        dayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerPetBirthDay.setAdapter(dayAdapter);
        spinnerPetDay.setAdapter(dayAdapter);


    }
}