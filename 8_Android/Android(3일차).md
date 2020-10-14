## 안드로이드 3일차 P246~

1.  Android Studio
2.  Widget, Layout, Event
3.  Android 4 Component
   * Activity : Base, UI 역할
   * Service : Background Process
   * Broadcast Receiver : Broadcast 내용을 받는다.
   * Content Provider : 특정 App Data 공유

### Activity 간의 이동(화면전환, 데이터 전송, 퍼미션 체크) P251

> SecondActivity 생성  activity_second 자동 생성  (파일생성할 때 activity를 만들어야 한다.) 앱 위에 새로운 앱이 올라오는 것이기 때문에 뒤로가기로 누르면 메인엑티비티로 돌아 간다.  - finish(); 로 현재 엑티비티를 없앨 수 있다.



> MainActivity

```java
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
```



> SecondActivity

```java
package com.example.p251;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class SecondActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
        Intent intent = getIntent();

        // int result = intent.getIntExtra("data",0);

        Bundle bundle = intent.getExtras();
        int result = bundle.getInt("data",0);
        String str_result = bundle.getString("str","");
        Toast.makeText(this, ""+str_result+":"+result, Toast.LENGTH_SHORT).show();
    }

    public void second_clickbt(View v){
        Intent intent = new Intent(getApplicationContext(),ThirdActivity.class);

        startActivity(intent);
    }

}
```



> ThirdActivity

```java
package com.example.p251;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.PermissionChecker;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class ThirdActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_third);

    }

    public void ckbt(View v){
        Intent intent = null;
        if(v.getId()== R.id.button4){
            intent = new Intent(Intent.ACTION_VIEW,
                    Uri.parse("tel:010-9879-2039"));
            startActivity(intent);
        }else if(v.getId()== R.id.button3){
            intent = new Intent(Intent.ACTION_VIEW,
                    Uri.parse("content://contacts/people"));  // 주소록 가지고오기
            startActivity(intent);
        }else if(v.getId()== R.id.button5){
            // Permission 여부 확인
            int check = PermissionChecker.checkSelfPermission(
                    this, Manifest.permission.CALL_PHONE
            );
            if(check == PackageManager.PERMISSION_GRANTED){
                intent = new Intent(Intent.ACTION_CALL,
                        Uri.parse("tel:010-9878-8989"));
                startActivity(intent);
            }else {
                Toast.makeText(this, "Not Allowed", Toast.LENGTH_SHORT).show();
            }
        }
    }

}
```



### premission 체크 251p 위 코드 참고

> AndroidManifest  내용 추가 후 -> MainActivity 퍼미션 체크 추가 -> Permission 여부 확인 후 함수 실행

> AndroidManifest  사용할 permission 추가
>
>  <uses-permission android:name="android.permission.CALL_PHONE"/> 

```java
<?xml version="1.0" encoding="utf-8"?>
<manifest xmlns:android="http://schemas.android.com/apk/res/android"
    package="com.example.p251">


    <uses-permission android:name="android.permission.CALL_PHONE"/>


    <application
        android:allowBackup="true"
        android:icon="@mipmap/ic_launcher"
        android:label="@string/app_name"
        android:roundIcon="@mipmap/ic_launcher_round"
        android:supportsRtl="true"
        android:theme="@style/AppTheme">
        <activity android:name=".ThirdActivity"></activity>
        <activity android:name=".SecondActivity" />
        <activity android:name=".MainActivity">
            <intent-filter>
                <action android:name="android.intent.action.MAIN" />

                <category android:name="android.intent.category.LAUNCHER" />
            </intent-filter>
        </activity>
    </application>                             

</manifest>
```



![KakaoTalk_20201014_175612859_06](Android(3%EC%9D%BC%EC%B0%A8)/KakaoTalk_20201014_175612859_06.jpg)



### 액티비티 수명주기 , 데이터 임시 저장 P275

> 세로 -> 가로로 돌리면 App 이 완전히 죽었다가 다시 켜지는 형식이다.
>
> 임시저장은 앱이 실행되어 있을 때만 저장이 된다.

```java
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
```



![KakaoTalk_20201014_175612859_05](Android(3%EC%9D%BC%EC%B0%A8)/KakaoTalk_20201014_175612859_05.jpg)

![KakaoTalk_20201014_175612859_04](Android(3%EC%9D%BC%EC%B0%A8)/KakaoTalk_20201014_175612859_04.jpg)



### 프래그먼트 , 메뉴 넣기(ActionBar 관리하기) P287~

> app -> java 에 fragment 를 먼저 코딩해야 한다.
>
> 프래그먼트는 MainActivity의 코드를 간소화 하기 위해 



> 교재는 P312~
>
> ActionVar 는 androidx.appcompat 에서 import (메뉴 넣기) 
>
> Menu 넣기 위해서는 res에 menu 디렉토리를 만들어야한다 .(type도 메뉴로 지정)
>
> menu.xml 에서 icon을 그림으로 넣을 수 있다.
>
> showAsAction 을 always를 보여주면 action bar 에 항상 표시가 된다.



> MainActivity

```java
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
    // menu 항목별 함수 지정하기
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
```



> Fragment1.java

```java
package com.example.p287;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class Fragment1 extends Fragment {
    TextView textView;
    EditText editText;
    ImageView imageView;

    public Fragment1(){

    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup viewGroup = null;
        // Fragment 는 직접 viewGroup을 만들어서 findView를 써야 한다.
        viewGroup = (ViewGroup)inflater.inflate(R.layout.fragment_1,container,false);
        textView = viewGroup.findViewById(R.id.textView);
        editText = viewGroup.findViewById(R.id.editText);
        imageView = viewGroup.findViewById(R.id.imageView);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String str = editText.getText().toString();
                textView.setText(str);
            }
        });
        return viewGroup;
    }
  
}

```

> Fragment2.java , Fragment3.java  아무 함수 안넣었음

```java
package com.example.p287;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class Fragment2 extends Fragment {
    public Fragment2(){

    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_2,container,false);
    }
}
```

![KakaoTalk_20201014_175612859_03](Android(3%EC%9D%BC%EC%B0%A8)/KakaoTalk_20201014_175612859_03.jpg)

![KakaoTalk_20201014_175612859_03](Android(3%EC%9D%BC%EC%B0%A8)/KakaoTalk_20201014_175612859_03-1602665966075.jpg)

![KakaoTalk_20201014_175612859_01](Android(3%EC%9D%BC%EC%B0%A8)/KakaoTalk_20201014_175612859_01.jpg)

### 상단탭 만들기 P323

> new project를 Tabbed Activity로 생성 앱을 실행하면 자동으로 tab1,2 가 생긴다.





### 하단탭 만들기, Fragment에서 Toast 사용하기(MainActivity Resouse 받기) P331

> menu와 동일하게 res -> android directory menu 만들기
>
> menu item -> icon 넣고,  
>
> showasaction : ifRoom/withText , 
>
> enabled : true  (첫번째만)
>
> activity_main.xlml 에 컨테이너,FrameLayout, - weight 1
>
> bottomNavigationView 받은뒤 탑재 - weight 12
>
> bottomNavigationView 우측 menu에 만든 bottom_menu.xml 입력



>  MainActivity

```java
package com.example.p331;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

    BottomNavigationView bottomNavigationView;
    ActionBar actionBar;

    Fragment1 fragment1;
    Fragment2 fragment2;
    Fragment3 fragment3;

    FragmentManager fragmentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        actionBar = getSupportActionBar();
        actionBar.hide();

        // fragment1 에서 Toast 를 사용하기 위해 MainActicity를 던져줌
        fragment1 = new Fragment1(this);
        fragment2 = new Fragment2();
        fragment3 = new Fragment3();

        // 최초의 화면 만들기 framelayout에 fragment1 을 넣겠다
        fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.framelayout,fragment1).commit();

        bottomNavigationView = findViewById(R.id.bottom_nav);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                if(item.getItemId() == R.id.tab1){
                    fragmentManager = getSupportFragmentManager();
                    fragmentManager.beginTransaction().replace(R.id.framelayout,fragment1).commit();
                    Toast.makeText(MainActivity.this, "tab1", Toast.LENGTH_SHORT).show();
                }else if(item.getItemId() == R.id.tab2){
                    fragmentManager = getSupportFragmentManager();
                    fragmentManager.beginTransaction().replace(R.id.framelayout,fragment2).commit();
                    Toast.makeText(MainActivity.this, "tab2", Toast.LENGTH_SHORT).show();
                }else if(item.getItemId() == R.id.tab3){
                    fragmentManager = getSupportFragmentManager();
                    fragmentManager.beginTransaction().replace(R.id.framelayout,fragment3).commit();
                    Toast.makeText(MainActivity.this, "tab3", Toast.LENGTH_SHORT).show();
                }
                return false;
            }
        }); // end function
    } //end onCreat


}
```



> fragment1

```java
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
```



> fragment2, 3  아무 함수 없음

```java
package com.example.p331;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


public class Fragment2 extends Fragment {

    public Fragment2() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_2, container, false);
    }
}
```





###  바로가기 메뉴 만들기 P342

>  Navigation Drawer Activity 로 new project 생성
>
> naver 와 같은 바로가기 메뉴 Android 에서 기본 틀을 제공해준다.



### 

