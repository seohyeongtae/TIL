package com.example.user_ui;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;

import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class ThirdActivity extends AppCompatActivity {

    TextView newid;
    TextView newpwd;
    TextView checkpwd;
    TextView name;
    TextView age;
    RadioGroup radioGroup;
    RadioButton man;
    RadioButton woman;

    RadioGroup cateradiogroup;
    RadioButton cate1;
    RadioButton cate2;
    RadioButton cate3;
    RadioButton cate4;
    String category;

    Button idbt;
    Button registerbt;

    ArrayList<USERS> users;

    String putid;
    String putpwd;
    String putcheckpwd;
    String putname;
    String putage;
    String gender;

    JSONObject jsonObject;

    ActionBar actionBar;

    @SuppressLint("ResourceAsColor")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_third);

        actionBar = getSupportActionBar();
        actionBar.hide();

        newid = findViewById(R.id.newid);
        newpwd = findViewById(R.id.newpwd);
        checkpwd = findViewById(R.id.checkpwd);
        name = findViewById(R.id.name);
        age = findViewById(R.id.age);
        radioGroup = findViewById(R.id.radiogroup);
        man = findViewById(R.id.man);
        woman = findViewById(R.id.woman);

        idbt = findViewById(R.id.idbt);
        registerbt = findViewById(R.id.registerbt);

        cateradiogroup = findViewById(R.id.cateradiogroup);
        cate1 = findViewById(R.id.cate1);
        cate2 = findViewById(R.id.cate2);
        cate3 = findViewById(R.id.cate3);
        cate4 = findViewById(R.id.cate4);

        users = new ArrayList<>();
        jsonObject = new JSONObject();

        getData();

        checkpwd.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(! newpwd.getText().toString().trim().equals(checkpwd.getText().toString().trim())){
                    checkpwd.setTextColor(getResources().getColor(R.color.colorRed));
                }else if(newpwd.getText().toString().trim().equals(checkpwd.getText().toString().trim())){
                    checkpwd.setTextColor(getResources().getColor(R.color.colorPrimary));
                }else if(checkpwd.getText().toString().trim().equals("")){
                    checkpwd.setTextColor(getResources().getColor(R.color.colorBlack));
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        }); //addTextChangedListener end


    //라디오 그룹 클릭 리스너

        RadioGroup.OnCheckedChangeListener radioGroupButtonChangeListener = new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if(checkedId == R.id.man){
                    gender = "남";
               }else  if(checkedId == R.id.woman){
                    gender = "여";
                }
            }
        };
        radioGroup.setOnCheckedChangeListener(radioGroupButtonChangeListener);

        RadioGroup.OnCheckedChangeListener radioGroupButtonChangeListener2 = new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if(checkedId == R.id.cate1){
                    category = "과자";
                }else  if(checkedId == R.id.cate2){
                    category = "음료수";
                }else  if(checkedId == R.id.cate3){
                    category = "생필품";
                }else  if(checkedId == R.id.cate4){
                    category = "신선식품";
                }
            }
        };
        cateradiogroup.setOnCheckedChangeListener(radioGroupButtonChangeListener2);


    } //onCreate end

    // 아이디 중복 체크
    public void idclick(View v){
        if(newid.getText().toString().replace(" ","").equals("")){
            Toast.makeText(this,"ID를 입력해 주세요.",Toast.LENGTH_SHORT).show();
            return;
        }
        for(int i = 0; i < users.size(); i++){
            if(newid.getText().toString().trim().equals(users.get(i).userid)){
                Toast.makeText(this,"중복된 ID 입니다.",Toast.LENGTH_SHORT).show();
                return;
            }
            Toast.makeText(this,"사용 가능 한 ID 입니다.",Toast.LENGTH_SHORT).show();
        }
    } // idclick end

    // 회원가입 버튼
    public void registerclick(View v){
        if(newid.getText().toString().replace(" ","").equals("")){
            Toast.makeText(this,"ID를 입력해 주세요.",Toast.LENGTH_SHORT).show();
            return;
        } else if(newpwd.getText().toString().replace(" ","").equals("")){
            Toast.makeText(this,"비밀번호를 입력해 주세요.",Toast.LENGTH_SHORT).show();
            return;
        } else if(name.getText().toString().replace(" ","").equals("")){
            Toast.makeText(this,"이름을 입력해 주세요.",Toast.LENGTH_SHORT).show();
            return;
        } else if(age.getText().toString().replace(" ","").equals("")){
            Toast.makeText(this,"연령을 입력해 주세요.",Toast.LENGTH_SHORT).show();
            return;
        } else if(gender==null){
            Toast.makeText(this,"성별을 체크해 주세요.",Toast.LENGTH_SHORT).show();
            return;
        } else if(! newpwd.getText().toString().trim().equals(checkpwd.getText().toString().trim())){
            Toast.makeText(this,"비밀번호를 확인해 주세요.",Toast.LENGTH_SHORT).show();
            return;
        }

        insertid();
        Toast.makeText(this,"회원가입이 완료되었습니다.",Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(this,MainActivity.class);
        startActivity(intent);

    } // registerclick end

    public void insertid(){
        putid = newid.getText().toString().trim();
        putpwd = newpwd.getText().toString().trim();
        putcheckpwd = checkpwd.getText().toString().trim();
        putname = name.getText().toString().trim();
        putage = age.getText().toString().trim();

        try{
            jsonObject.put("userid",putid);
            jsonObject.put("pwd",putpwd);
            jsonObject.put("balance",0);
            jsonObject.put("name",putname);
            jsonObject.put("gender",gender);
            jsonObject.put("age",Integer.parseInt(putage));
            jsonObject.put("interest",category);
        }catch (JSONException e){
            e.printStackTrace();
        }

        String url = "http://ec2-3-35-11-144.ap-northeast-2.compute.amazonaws.com:8080/demo/users";
        new InsertTask().execute(url, jsonObject.toString());
    }
    private class InsertTask extends AsyncTask<String, Void, String> {
        protected String doInBackground(String... urls) {

            String resp = HttpConnectUtil.postRequest(urls[0], urls[1]);

            return resp;
        }

        protected void onPostExecute(String result) {
            super.onPostExecute(result);

           // Toast.makeText(this, result, Toast.LENGTH_LONG).show();
        }
    }


    // 기존 회원 List 불러오기
    public void getData() {
        String url = "http://ec2-3-35-11-144.ap-northeast-2.compute.amazonaws.com:8080/demo/users";
        UserAsync userAsync = new UserAsync();
        userAsync.execute(url);
        //Log.d("[Log]:", "ddde" + "  " + url);
    }

    class UserAsync extends AsyncTask<String, Void, String> {
        ProgressDialog progressDialog;


        @Override
        protected void onPreExecute() {

        }

        @Override
        protected String doInBackground(String... strings) {
            String url = strings[0].toString();
            String result = HttpConnect.getString(url);
            return result;
        }

        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
        }

        @Override
        protected void onPostExecute(final String s) {

            // 현재는 items Table 에서 데이터를 가져오기 때문에 그 형식에 맞게 JSon 생성
            JSONArray ja = null;
            try {
                ja = new JSONArray(s);
                for (int i = 0; i < ja.length(); i++) {
                    JSONObject jo = ja.getJSONObject(i);
                    String userid = jo.getString("userid");
                    String pwd = jo.getString("pwd");
                    String name = jo.getString("name");
                    String gender = jo.getString("gender");
                    int age = jo.getInt("age");
                    USERS user = new USERS(userid, pwd, name, gender, age);
                    users.add(user);
                }
            } catch (JSONException e) {
                e.printStackTrace();


            } //onPostExecute end
        } // ItemAsync end


    } // class end
}