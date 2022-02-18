package com.example.p168;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.TextureView;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    String input = null;
    int in1;
    TextView textView= (TextView)findViewById(R.id.editTextTextPersonName);

    @SuppressLint("ResourceType")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        input =getString(R.id.editTextTextPersonName);
        in1 = input.length();
        if(in1 <= 80){
        textView.setText(in1 + "/80바이트");
        }
    }

    public void input1(View view) {

    };

}