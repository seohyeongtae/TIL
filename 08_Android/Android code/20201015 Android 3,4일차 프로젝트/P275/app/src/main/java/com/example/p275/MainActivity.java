package com.example.p275;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import java.util.Date;

public class MainActivity extends AppCompatActivity {

    SharedPreferences sp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.d("[TEST]","onCreate");
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d("[TEST]","onStart");
    }

    protected  void  onResume(){
        super.onResume();

        // 앱이 다시 시작했을때 데이터 불러오기
        restoreState();
        Log.d("[TEST]","onResume");
    }
    protected  void  onRestart(){
        super.onRestart();
        Log.d("[TEST]","onRestart");
    }
    protected void onPause(){
        super.onPause();

        // 앱이 잠시 멈췄을 때 저장
        saveState();
        Log.d("[TEST]","onPause");
    }
    protected void onStop(){
        super.onStop();
        Log.d("[TEST]","onStop");
    }
    protected void onDestroy(){
        super.onDestroy();
        Log.d("[TEST]","onDestroy");
    }

    // 앱이 실행되어 있을 때만 저장이 된다.
    // 데이터 끄집어 내기
    public void restoreState(){
        sp = getSharedPreferences("st",Activity.MODE_PRIVATE);
        if(sp != null && sp.contains("date")){
            String result = sp.getString("date","");
            Toast.makeText(this,result, Toast.LENGTH_SHORT).show();
        }
    }
    // 데이터 임시저장
    public void saveState(){
        sp = getSharedPreferences("st", Activity.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        Date d = new Date();
        editor.putString("date",d.toString());
        editor.commit();
    }

}