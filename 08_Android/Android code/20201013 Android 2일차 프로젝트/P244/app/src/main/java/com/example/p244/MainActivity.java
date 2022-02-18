package com.example.p244;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    LinearLayout container;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        container = findViewById(R.id.container);
    }

    public void bt(View v){
        if(v.getId() == R.id.button){
            container.removeAllViews();  // 이걸 안써주면 변하지 버튼2를 눌렀을때 변하지 않음 유용하게 쓰임
            LayoutInflater inflater = (LayoutInflater)getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                    inflater.inflate(R.layout.sub1,container,true);
            TextView tv = container.findViewById(R.id.textView);
            tv.setText("Sub1 Page");

        }else if(v.getId() == R.id.button2){
            container.removeAllViews();  // 이걸 안써주면 변하지 버튼1을 눌렀을때 변하지 않음 유용하게 쓰임
            LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                inflater.inflate(R.layout.sub2,container,true);
                TextView tv = container.findViewById(R.id.textView2);
                tv.setText("Hello sub2 page");
        }
    }

}