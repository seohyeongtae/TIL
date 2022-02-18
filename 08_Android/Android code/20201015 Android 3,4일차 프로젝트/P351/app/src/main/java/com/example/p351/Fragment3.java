package com.example.p351;

import android.app.ProgressDialog;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

public class Fragment3 extends Fragment {

    Button button3;
    ProgressDialog progressDialog;
    MainActivity m;

    public Fragment3(MainActivity m) {
        this.m = m;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ViewGroup viewGroup = null;
        viewGroup = (ViewGroup) inflater.inflate(R.layout.fragment_3,container,false);
        button3 = viewGroup.findViewById(R.id.button3);
        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                progressDialog = new ProgressDialog(m);
                progressDialog.setProgressStyle(progressDialog.STYLE_SPINNER);
                progressDialog.setTitle("Fragment3 Loading..");
                progressDialog.show();

              }
        });
        // 이거 꼭 viewGroup 으로 바꿔야 한다..........
        return viewGroup;
    } // onCreateView end
} // Class end