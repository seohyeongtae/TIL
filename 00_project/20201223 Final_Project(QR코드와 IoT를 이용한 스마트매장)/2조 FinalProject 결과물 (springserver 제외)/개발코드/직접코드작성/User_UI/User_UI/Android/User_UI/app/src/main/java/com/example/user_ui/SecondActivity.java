package com.example.user_ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import org.json.JSONException;
import org.json.JSONObject;

public class SecondActivity extends AppCompatActivity {

    Fragment1 fragment1;
    Fragment2 fragment2;
    Fragment3 fragment3;
    Fragment4 fragment4;
    Fragment5 fragment5;
    Fragment6 fragment6;
    FragmentManager fragmentManager;
    BottomNavigationView bottomNavigationView;
    ActionBar actionBar;

    Menu menu;

    String useridinfo;
    String username;
    String usergender;

    // 뒤로가기 버튼 1번 누르면 fragment 1 로, 한번 더 누르면 MainActivity로 이동
    private BackPressCloseHandler backPressCloseHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        Intent main_intent =getIntent();
        useridinfo =main_intent.getStringExtra("useridinfo");
        username =main_intent.getStringExtra("username");
        usergender =main_intent.getStringExtra("usergender");
        //Toast.makeText(this,"info = "+useridinfo,Toast.LENGTH_SHORT).show();
        // 상단 actionBar 숨기기 (아직 어플 명도 안나와서)
        actionBar = getSupportActionBar();
        actionBar.hide();

        // fragment 1 -> 전체 목록 Home (이 목록에서도 각 fragment로 이동가능
        // fragment 2 -> 회원정보
        // fragment 3 -> 주문하기
        // fragment 4 -> 장바구니
        // fragment 5 -> 제품안내
        // fragment 6 -> 회원정보수정
        fragment1 = new Fragment1(this,usergender);
        fragment2 = new Fragment2(this,useridinfo,username);
        fragment3 = new Fragment3(this,useridinfo);
        fragment4 = new Fragment4(this,useridinfo);
        fragment5 = new Fragment5(this,useridinfo);
        fragment6 = new Fragment6(this,useridinfo);

        fragmentManager =getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.framelayout,fragment1).commit();

        bottomNavigationView = findViewById(R.id.botomnav);
        menu = bottomNavigationView.getMenu();

        // 하단 메뉴 누를시 각 fragment로 이동하며 Icon 변경
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                if(item.getItemId() == R.id.navbt1){
                    item.setIcon(R.drawable.ic_baseline_check_24);
                    menu.findItem(R.id.navbt2).setIcon(R.drawable.ic_baseline_info_24);
                    menu.findItem(R.id.navbt3).setIcon(R.drawable.ic_baseline_favorite_border_24);
                    menu.findItem(R.id.navbt4).setIcon(R.drawable.ic_baseline_shopping_cart_24);
                    menu.findItem(R.id.navbt5).setIcon(R.drawable.ic_baseline_list_alt_24);

                    fragmentManager = getSupportFragmentManager();
                    fragmentManager.beginTransaction().replace(R.id.framelayout,fragment1).commit();
                    Toast.makeText(SecondActivity.this, "Home", Toast.LENGTH_SHORT).show();
                }else if(item.getItemId() == R.id.navbt2){
                    item.setIcon(R.drawable.ic_baseline_check_24);
                    menu.findItem(R.id.navbt1).setIcon(R.drawable.ic_baseline_home_24);
                    menu.findItem(R.id.navbt3).setIcon(R.drawable.ic_baseline_favorite_border_24);
                    menu.findItem(R.id.navbt4).setIcon(R.drawable.ic_baseline_shopping_cart_24);
                    menu.findItem(R.id.navbt5).setIcon(R.drawable.ic_baseline_list_alt_24);

                    fragmentManager = getSupportFragmentManager();
                    fragmentManager.beginTransaction().replace(R.id.framelayout,fragment2).commit();
                    Toast.makeText(SecondActivity.this, "회원정보", Toast.LENGTH_SHORT).show();
                }else if(item.getItemId() == R.id.navbt3){
                    item.setIcon(R.drawable.ic_baseline_check_24);
                    menu.findItem(R.id.navbt1).setIcon(R.drawable.ic_baseline_home_24);
                    menu.findItem(R.id.navbt2).setIcon(R.drawable.ic_baseline_info_24);
                    menu.findItem(R.id.navbt4).setIcon(R.drawable.ic_baseline_shopping_cart_24);
                    menu.findItem(R.id.navbt5).setIcon(R.drawable.ic_baseline_list_alt_24);

                    fragmentManager = getSupportFragmentManager();
                    fragmentManager.beginTransaction().replace(R.id.framelayout,fragment3).commit();
                    Toast.makeText(SecondActivity.this, "주문하기", Toast.LENGTH_SHORT).show();
                }else if(item.getItemId() == R.id.navbt4) {
                    item.setIcon(R.drawable.ic_baseline_check_24);
                    menu.findItem(R.id.navbt1).setIcon(R.drawable.ic_baseline_home_24);
                    menu.findItem(R.id.navbt2).setIcon(R.drawable.ic_baseline_info_24);
                    menu.findItem(R.id.navbt3).setIcon(R.drawable.ic_baseline_favorite_border_24);
                    menu.findItem(R.id.navbt5).setIcon(R.drawable.ic_baseline_list_alt_24);

                    fragmentManager = getSupportFragmentManager();
                    fragmentManager.beginTransaction().replace(R.id.framelayout, fragment4).commit();
                    Toast.makeText(SecondActivity.this, "주문현황", Toast.LENGTH_SHORT).show();
                }else if(item.getItemId() == R.id.navbt5) {
                    item.setIcon(R.drawable.ic_baseline_check_24);
                    menu.findItem(R.id.navbt1).setIcon(R.drawable.ic_baseline_home_24);
                    menu.findItem(R.id.navbt2).setIcon(R.drawable.ic_baseline_info_24);
                    menu.findItem(R.id.navbt3).setIcon(R.drawable.ic_baseline_favorite_border_24);
                    menu.findItem(R.id.navbt4).setIcon(R.drawable.ic_baseline_shopping_cart_24);

                    fragmentManager = getSupportFragmentManager();
                    fragmentManager.beginTransaction().replace(R.id.framelayout, fragment5).commit();
                    Toast.makeText(SecondActivity.this, "제품안내", Toast.LENGTH_SHORT).show();
                }
                switch (item.getItemId()){
                    case R.id.navbt1:
                }

                return false;
            }
        });
        JSONObject jo = new JSONObject();
        // log 기록 저장
        if(usergender.equals("남")){
            try {
                jo.put("gender","남");
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }else if(usergender.equals("여")){
            try {
                jo.put("gender","여");
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        String url = "http://ec2-3-35-11-144.ap-northeast-2.compute.amazonaws.com:8080/demo/userlog/";
        new InsertTask().execute(url, jo.toString());



        backPressCloseHandler = new BackPressCloseHandler(this);
    } // onCreate end

    // 회원 정보 수정
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

    // 뒤로가기 버튼 이벤트
    @Override
    public void onBackPressed() {
        backPressCloseHandler.onBackPressed();
    }
    public class BackPressCloseHandler {
        private  long backkeyPressedTime = 0;
        private Toast toast;
        private SecondActivity secondActivity;
        public BackPressCloseHandler(SecondActivity context){
            this.secondActivity = context;
        }
        public void onBackPressed(){
            if (System.currentTimeMillis() > backkeyPressedTime + 2000) {
                menu.findItem(R.id.navbt1).setIcon(R.drawable.ic_baseline_check_24);
                menu.findItem(R.id.navbt2).setIcon(R.drawable.ic_baseline_info_24);
                menu.findItem(R.id.navbt3).setIcon(R.drawable.ic_baseline_favorite_border_24);
                menu.findItem(R.id.navbt4).setIcon(R.drawable.ic_baseline_shopping_cart_24);
                menu.findItem(R.id.navbt5).setIcon(R.drawable.ic_baseline_list_alt_24);

                fragmentManager = getSupportFragmentManager();
                fragmentManager.beginTransaction().replace(R.id.framelayout, fragment1).commit();
                backkeyPressedTime = System.currentTimeMillis();
                showGuide();
                return;
            }
            if(System.currentTimeMillis() <= backkeyPressedTime + 2000){
                secondActivity.finish();
                toast.cancel();
            }
        } //onBackPressed end
        public void showGuide() {
            toast = Toast.makeText(secondActivity,"뒤로가기를 한번 더 누르시면 로그인 페이지로 돌아갑니다.",Toast.LENGTH_SHORT);
            toast.show();
        }

    } // BackPressCloseHandler end


} // class end