package com.example.p251;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // AndroidManifest 수정 후 permission 체크
        String [] permissions = {
                // 여러개 써도 된다.
                Manifest.permission.CALL_PHONE
        };
        // permission 요청 단계
        ActivityCompat.requestPermissions(this,permissions,101);
    }

    public void ckbt(View v){
        Intent intent = new Intent(getApplicationContext(),SecondActivity.class);
        intent.putExtra("data",100);
        intent.putExtra("str","String Data");

        startActivity(intent);
    }


}