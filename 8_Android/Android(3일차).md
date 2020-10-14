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



### 프래그먼트 P287~

> app -> java 에 fragment 를 먼저 코딩해야 한다.
>
> 프래그먼트는 MainActivity의 코드를 간소화 하기 위해 

