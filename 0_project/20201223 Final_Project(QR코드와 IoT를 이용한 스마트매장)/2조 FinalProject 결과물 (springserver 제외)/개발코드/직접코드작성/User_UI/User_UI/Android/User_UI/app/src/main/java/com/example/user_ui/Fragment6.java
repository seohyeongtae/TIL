package com.example.user_ui;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.graphics.Paint;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Fragment6 extends Fragment {

    SecondActivity s1;
    String useridinfo;

    TextView infoidtx;
    TextView infonametx;
    TextView infopwdtx;
    TextView infonewpwdtx;
    TextView infochecktx;
    TextView infoagetx;
    TextView infogendertx;
    TextView balancetx;
    TextView chargetx;
    TextView infocatetx;

    Button registerbt;
    Button chargebt;

    RadioGroup radioGroup;
    RadioButton man;
    RadioButton woman;

    RadioGroup cateradiogroup;
    RadioButton cate1;
    RadioButton cate2;
    RadioButton cate3;
    RadioButton cate4;

    HttpAsync httpAsync;
    USERS user;

    JSONObject jsonObjectuser;
    JSONObject jsonObjectcharge;


    public Fragment6(SecondActivity s1,String useridinfo) {
        this.s1 =s1;
        this.useridinfo = useridinfo;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_6, container, false);

        infoidtx = v.findViewById(R.id.infoidtx);
        infonametx = v.findViewById(R.id.infonametx);
        infopwdtx = v.findViewById(R.id.infopwdtx);
        infonewpwdtx = v.findViewById(R.id.infonewpwdtx);
        infochecktx = v.findViewById(R.id.infochecktx);
        infoagetx = v.findViewById(R.id.infoagetx);
        infogendertx = v.findViewById(R.id.infogendertx);
        infocatetx = v.findViewById(R.id.infocatertx);

        balancetx = v.findViewById(R.id.balancetx);
        chargetx = v.findViewById(R.id.chargetx);

        registerbt = v.findViewById(R.id.registerbt);
        chargebt = v.findViewById(R.id.chargebt);

        radioGroup = v.findViewById(R.id.radiogroup);
        man = v.findViewById(R.id.man);
        woman = v.findViewById(R.id.woman);


        cateradiogroup = v.findViewById(R.id.cateradiogroup);
        cate1 = v.findViewById(R.id.cate1);
        cate2 = v.findViewById(R.id.cate2);
        cate3 = v.findViewById(R.id.cate3);
        cate4 = v.findViewById(R.id.cate4);

        jsonObjectuser = new JSONObject();
        jsonObjectcharge = new JSONObject();

        String url = "http://ec2-3-35-11-144.ap-northeast-2.compute.amazonaws.com:8080/demo/users/"+useridinfo;
        // Data 불러오기 및 로그인은 이후 구성
        // Toast.makeText(this,url,Toast.LENGTH_SHORT).show();
        httpAsync = new HttpAsync();
        httpAsync.execute(url);

        // newpwd 체크
        infochecktx.addTextChangedListener(new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            if(! infonewpwdtx.getText().toString().trim().equals(infochecktx.getText().toString().trim())){
                infochecktx.setTextColor(getResources().getColor(R.color.colorRed));
            }else if(infonewpwdtx.getText().toString().trim().equals(infochecktx.getText().toString().trim())){
                infochecktx.setTextColor(getResources().getColor(R.color.colorPrimary));
            }
        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    }); //addTextChangedListener end

        // 현재 PWD 확인
        infopwdtx.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(! infonewpwdtx.getText().toString().trim().equals(infochecktx.getText().toString().trim())){
                    infochecktx.setTextColor(getResources().getColor(R.color.colorRed));
                }else if(infonewpwdtx.getText().toString().trim().equals(infochecktx.getText().toString().trim())){
                    infochecktx.setTextColor(getResources().getColor(R.color.colorPrimary));
                }else if(infochecktx.getText().toString().trim().equals("")){
                    infochecktx.setTextColor(getResources().getColor(R.color.colorBlack));
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
                    infogendertx.setText("성별 : 남");
                }else  if(checkedId == R.id.woman){
                    infogendertx.setText("성별 : 여");
                }
            }
        };
        radioGroup.setOnCheckedChangeListener(radioGroupButtonChangeListener);

        RadioGroup.OnCheckedChangeListener radioGroupButtonChangeListener2 = new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if(checkedId == R.id.cate1){
                    infocatetx.setText("관심사 : 과자");
                }else  if(checkedId == R.id.cate2){
                    infocatetx.setText("관심사 : 음료수");
                }else  if(checkedId == R.id.cate3){
                    infocatetx.setText("관심사 : 생필품");
                }else  if(checkedId == R.id.cate4){
                    infocatetx.setText("관심사 : 신선식품");
                }
            }
        };
        cateradiogroup.setOnCheckedChangeListener(radioGroupButtonChangeListener2);


        // 회원정보 수정 버튼 클릭시
        registerbt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String id = infoidtx.getText().toString().trim();
                String name = infonametx.getText().toString().trim();
                String pwd = infonewpwdtx.getText().toString().trim();
                int age = Integer.parseInt(infoagetx.getText().toString().trim());
                String gender = infogendertx.getText().toString().trim();
                String category = infocatetx.getText().toString().trim();

                if(infoidtx.getText().toString().replace(" ","").equals("")){
                    Toast.makeText(s1,"ID를 입력해 주세요.",Toast.LENGTH_SHORT).show();
                    return;
                } else if(infonewpwdtx.getText().toString().replace(" ","").equals("")){
                    Toast.makeText(s1,"비밀번호를 입력해 주세요.",Toast.LENGTH_SHORT).show();
                    return;
                } else if(infonametx.getText().toString().replace(" ","").equals("")){
                    Toast.makeText(s1,"이름을 입력해 주세요.",Toast.LENGTH_SHORT).show();
                    return;
                } else if(infoagetx.getText().toString().replace(" ","").equals("")){
                    Toast.makeText(s1,"연령을 입력해 주세요.",Toast.LENGTH_SHORT).show();
                    return;
                } else if(gender==null){
                    Toast.makeText(s1,"성별을 체크해 주세요.",Toast.LENGTH_SHORT).show();
                    return;
                } else if(! infopwdtx.getText().toString().trim().equals(user.getPwd().toString().trim())){
                    Toast.makeText(s1,"비밀번호를 확인해 주세요.",Toast.LENGTH_SHORT).show();
                    return;
                } else if(! infopwdtx.getText().toString().trim().equals(infochecktx.getText().toString().trim())){
                    Toast.makeText(s1,"변경 비밀번호를 확인해 주세요.",Toast.LENGTH_SHORT).show();
                    return;
                }

                try{
                    jsonObjectuser.put("pwd",pwd);
                    jsonObjectuser.put("name",name);
                    jsonObjectuser.put("gender",gender);
                    jsonObjectuser.put("age",age);
                    jsonObjectuser.put("interest",category);
                }catch (JSONException e){
                    e.printStackTrace();
                }
                String url = "http://ec2-3-35-11-144.ap-northeast-2.compute.amazonaws.com:8080/demo/users/"+useridinfo;
                new InsertTask().execute(url, jsonObjectuser.toString());
                httpAsync = new HttpAsync();
                httpAsync.execute(url);
               // Toast.makeText(s1,jsonObjectuser.toString(),Toast.LENGTH_SHORT).show();
                System.out.println(jsonObjectuser.toString()+"------------------------------------");
                // Toast.makeText(s1,"정보수정이 완료되었습니다.",Toast.LENGTH_SHORT).show();
            }
        }); // 회원정보 수정 버튼 end



        //   잔액 충전 버튼
        chargebt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(chargetx.getText().toString().replace(" ","").equals("")){
                    Toast.makeText(s1,"충전 금액을 입력해 주세요.",Toast.LENGTH_SHORT).show();
                    return;
                }
                final int balance = Integer.parseInt(balancetx.getText().toString().trim());
                final int charge = Integer.parseInt(chargetx.getText().toString().trim());

                JSONArray jsonArrayitem = new JSONArray();

                android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(s1);
                builder.setTitle("충전하기");
                builder.setMessage(charge+" 원을 충전 하시겠습니까?");

                builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(s1,"충전이 완료되었습니다",Toast.LENGTH_LONG).show();
                        try{
                            jsonObjectcharge.put("balance",charge);

                        }catch (JSONException e){
                            e.printStackTrace();
                        }
                        String url = "http://ec2-3-35-11-144.ap-northeast-2.compute.amazonaws.com:8080/demo/users/"+useridinfo;
                        new InsertTask().execute(url, jsonObjectcharge.toString());
                        httpAsync = new HttpAsync();
                        httpAsync.execute(url);
                      //  Toast.makeText(s1,jsonObjectcharge.toString(),Toast.LENGTH_SHORT).show();

                    }
                });
                builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                AlertDialog dialog = builder.create();
                dialog.show();

            }
        }); // 잔액 충전 버튼 end

        return v;
    } // onCreateView end

    // 회원 정보 수정
    private class InsertTask extends AsyncTask<String, Void, String> {
        protected String doInBackground(String... urls) {

            String resp = HttpConnectPut.postRequest(urls[0], urls[1]);

            return resp;
        }

        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            // Toast.makeText(this, result, Toast.LENGTH_LONG).show();
        }
    }


    // 회원 정보 가지고 오기
    class HttpAsync extends AsyncTask<String,String,String> {
        ProgressDialog progressDialog;

        @Override
        protected void onPreExecute() {
            // 데이터가 받는 동안 progressDialog 띄운다움 데이터가 다내려오면 꺼지게 만든다. onPostExecute 에서
            progressDialog = new ProgressDialog(s1);
            progressDialog.setTitle("Login...");
            progressDialog.setCancelable(false);
            progressDialog.show();
        }

        @Override
        protected String doInBackground(String... strings) {
            String url = strings[0].toString();
            String result = HttpConnect.getString(url);
            return result;
        }

        @Override
        protected void onProgressUpdate(String... values) {
            super.onProgressUpdate(values);
        }

        // return 되는 값이 오는 곳 이클립스에서 out.print 한 값이 들어온다.
        @Override
        protected void onPostExecute(String s) {
            // 데이터가 다 내려오면 progressDialog 종료 후 Login 여부 확인 및 각 코드 실행
            // trim 으로 받은 이유는 혹시모르는 빈 스페이스 공간도 없애기 위해
            progressDialog.dismiss();
            JSONObject jo = null;
            try {
                jo = new JSONObject(s);
                String userid = jo.getString("userid");
                String pwd = jo.getString("pwd");
                int balance = jo.getInt("balance");
                String name = jo.getString("name");
                String gender = jo.getString("gender");
                int age = jo.getInt("age");
                String interest = jo.getString("interest");
                user = new USERS(userid,pwd,balance,name,gender,age,interest);
            } catch (JSONException e) {
                e.printStackTrace();
            }

            infoidtx.setText(user.getUserid());
            infonametx.setText(user.getName());
            infoagetx.setText(user.getAge()+"");
            infogendertx.setText("성별 : "+user.getGender());
            balancetx.setText(user.getBalance()+"");
            infocatetx.setText("관심사 : "+user.getInterest());
            infopwdtx.setText("");
            infopwdtx.setPaintFlags(infopwdtx.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
            infonewpwdtx.setText("");
            infochecktx.setText("");
            infochecktx.setPaintFlags(infochecktx.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
            chargetx.setText("");

            }
        }// HttpAsync end


    }  // class end

