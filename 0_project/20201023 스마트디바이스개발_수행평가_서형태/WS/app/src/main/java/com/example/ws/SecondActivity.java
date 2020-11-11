package com.example.ws;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;
import com.google.android.material.bottomnavigation.BottomNavigationView;


public class SecondActivity extends AppCompatActivity {
    Fragment1 fragment1;
    Fragment2 fragment2;
    Fragment3 fragment3;
    FragmentManager fragmentManager;
    BottomNavigationView bottomNavigationView;
    ActionBar actionBar;

    Menu menu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        // 상단 actionbar 없애기
        actionBar = getSupportActionBar();
        actionBar.hide();

        fragment1 = new Fragment1(this);
        fragment2 = new Fragment2();
        fragment3 = new Fragment3(this);

        fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.framelayout,fragment1).commit();


        bottomNavigationView = findViewById(R.id.bottomNav);
        menu = bottomNavigationView.getMenu();

        // bottom nav 클릭시 이미지 변경, fragment 이동
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                if(item.getItemId() == R.id.navbt1){
                    item.setIcon(R.drawable.check);
                    menu.findItem(R.id.navbt2).setIcon(R.drawable.location);
                    menu.findItem(R.id.navbt3).setIcon(R.drawable.event);

                    fragmentManager = getSupportFragmentManager();
                    fragmentManager.beginTransaction().replace(R.id.framelayout,fragment1).commit();
                    Toast.makeText(SecondActivity.this, "상영순위", Toast.LENGTH_SHORT).show();
                }else if(item.getItemId() == R.id.navbt2){
                    item.setIcon(R.drawable.check);
                    menu.findItem(R.id.navbt1).setIcon(R.drawable.list);
                    menu.findItem(R.id.navbt3).setIcon(R.drawable.event);

                    fragmentManager = getSupportFragmentManager();
                    fragmentManager.beginTransaction().replace(R.id.framelayout,fragment2).commit();
                    Toast.makeText(SecondActivity.this, "상영관 위치", Toast.LENGTH_SHORT).show();
                }else if(item.getItemId() == R.id.navbt3){
                    item.setIcon(R.drawable.check);
                    menu.findItem(R.id.navbt1).setIcon(R.drawable.list);
                    menu.findItem(R.id.navbt2).setIcon(R.drawable.location);

                    fragmentManager = getSupportFragmentManager();
                    fragmentManager.beginTransaction().replace(R.id.framelayout,fragment3).commit();
                    Toast.makeText(SecondActivity.this, "이벤트 정보", Toast.LENGTH_SHORT).show();
                }
                switch (item.getItemId()){
                    case R.id.navbt1:
                }

             return false;
            }
        });


    } // onCreate end

} // class end