package com.example.p331;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

public class Fragment1 extends Fragment {

    Button button;

    // Toast 는 MainActivity에서만 사용가능하기 때문에 메인에서 던져 정보를 받을 준비
    MainActivity m;
    public Fragment1(MainActivity m) {
        this.m = m;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        ViewGroup viewGroup = null;
        // Fragment 는 직접 viewGroup을 만들어서 findView를 써야 한다.
        viewGroup = (ViewGroup)inflater.inflate(R.layout.fragment_1,container,false);
        button = viewGroup.findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(m, "Fragment1...", Toast.LENGTH_SHORT).show();
            }
        });
        
        return viewGroup;
    }



}