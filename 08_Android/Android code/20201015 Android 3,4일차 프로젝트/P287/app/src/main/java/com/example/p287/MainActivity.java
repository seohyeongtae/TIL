package com.example.p287;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

public class MainActivity extends AppCompatActivity {
    Fragment1 fragment1;
    Fragment2 fragment2;
    Fragment3 fragment3;

    //Menu bar 넣기
    ActionBar actionBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        fragment1 = new Fragment1();
        // fragment1 = (Fragment1) getSupportFragmentManager().findFragmentById(R.id.fragment);
        fragment2 = new Fragment2();
        fragment3 = new Fragment3();

        actionBar = getSupportActionBar();
        actionBar.setTitle("Fragment");
        actionBar.setLogo(R.drawable.d3);
        //Title 을 보이게 할 경우
        // actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_HOME | ActionBar.DISPLAY_SHOW_TITLE);

        // Logo 를 보이게 할 경우
        actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_HOME | ActionBar.DISPLAY_USE_LOGO);
        // actionBar.hide();
    }
    // menu.xml 불러오기
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.mymenu,menu);
        return true;
    }
    // menu 항복별 함수 지정하기
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() == R.id.m1){
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment,fragment1).commit();
        }else if(item.getItemId() == R.id.m2){
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment,fragment2).commit();
        }else if(item.getItemId() == R.id.m3){
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment,fragment3).commit();
        }
        return super.onOptionsItemSelected(item);
    }

    // Fragment xml 각각 불러오기
    public void ckbt(View v){
        if(v.getId() == R.id.button){
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment,fragment1).commit();

        }else if(v.getId() == R.id.button2){
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment,fragment2).commit();

        }else if(v.getId() == R.id.button3){
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment,fragment3).commit();

        }
    }
}