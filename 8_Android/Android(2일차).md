## 안드로이드 2일차 P171~ (위젯 상세히 설명, 코드)

>  다국어 지원 strings.xml



> 이건 1일차에도 있는 사진 한번 더 보자

![KakaoTalk_20201013_104657244](Android(2%EC%9D%BC%EC%B0%A8)/KakaoTalk_20201013_104657244.jpg)



res 폴더 밑에 values-en/   values-ko  폴더를 만들고 그안에 strings.xml 를 만들면 지원이 된다.

![KakaoTalk_20201013_104657244_01](Android(2%EC%9D%BC%EC%B0%A8)/KakaoTalk_20201013_104657244_01.jpg)

> text 리스너

![KakaoTalk_20201013_104657244_03](Android(2%EC%9D%BC%EC%B0%A8)/KakaoTalk_20201013_104657244_03.jpg)

### 레이아웃 클릭 시 함수 실행 P200 

> 레이아웃은 id가 안정해져 있기 때문에 따로 지정해주어야 한다.
>
> 터치 이벤트

```java
package com.example.p200;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    TextView textView;
    LinearLayout view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textView = findViewById(R.id.textView);
        view = findViewById(R.id.view);
        
        // ctrl + space를 잘 이용하자. 자동으로 기본값 세팅해준다. 
        view.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                int action = event.getAction();
                
                // float 은 좌표값
                float x = event.getX();
                float y = event.getY();
                if (action == MotionEvent.ACTION_DOWN){
                    print("DOWN:"+x+","+y);
                }else if(action == MotionEvent.ACTION_MOVE){
                    print("MOVE:"+x+","+y);
                }else if(action == MotionEvent.ACTION_UP){
                    print("UP:"+x+","+y);
                }
                return true;
            }
        });
    } // onCreate end
    
    public void print(String str){
        textView.setText(str);
    }
    
}
```

![KakaoTalk_20201013_104657244_02](Android(2%EC%9D%BC%EC%B0%A8)/KakaoTalk_20201013_104657244_02.jpg)

> 제스처이벤트 P202



### 키이벤트 처리 P205

> 오른쪽 클릭, generate , Override method 를 이용하자.

```java
   
// 뒤로가기를 눌렀을 때 Toast 출력
	@Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode == KeyEvent.KEYCODE_BACK){
            Toast.makeText(this, ""+"BACK KEY PRESSED",
                           Toast.LENGTH_SHORT).show();
        }

        return false;
    }

// 같은 기능
	   @Override
    public void onBackPressed() {
        //super.onBackPressed();
        Toast.makeText(this, ""+"BACK KEY PRESSED",
                       Toast.LENGTH_SHORT).show();
        finish();  // 앱종료시키기
    }
```

![KakaoTalk_20201013_104657244_04](Android(2%EC%9D%BC%EC%B0%A8)/KakaoTalk_20201013_104657244_04.jpg)



### 단일 방향 전환 이벤트 처리 P207

> res 밑에 Android Resouse Directory 를 만들어야 한다. 이름 = activity_main 으로 (가로화면) 폴더 밑에 layout resouse 생성

AndroidManifest.xml  을 수정해주어야 한다.

 <activity android:name=".MainActivity" android:configChanges="orientation|screenSize|keyboardHidden">

```java
// 화면을 항상 고정시켜주고 싶을때 추가
android:screenOrientation="landscape"
```

```html
<?xml version="1.0" encoding="utf-8"?>
<manifest xmlns:android="http://schemas.android.com/apk/res/android"
    package="com.example.p207">

    <application
        android:allowBackup="true"
        android:icon="@mipmap/ic_launcher"
        android:label="@string/app_name"
        android:roundIcon="@mipmap/ic_launcher_round"
        android:supportsRtl="true"
        android:theme="@style/AppTheme">
        <activity android:name=".MainActivity" 
                  
            <!--            android:screenOrientation="landscape"-->
        
            android:configChanges="orientation|screenSize|keyboardHidden">
            <intent-filter>
                <action android:name="android.intent.action.MAIN" />

                <category android:name="android.intent.category.LAUNCHER" />
            </intent-filter>
        </activity>
    </application>

</manifest>
```



```java
package com.example.p207;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.res.Configuration;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    Button button;
    EditText et;
    TextView textView;

    String str;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        show("onCreate");
        
        button = findViewById(R.id.button);
        et = findViewById(R.id.et);
        textView = findViewById(R.id.textView2);


        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                str = et.getText().toString();
                Toast.makeText(MainActivity.this, str,
                               Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onConfigurationChanged(@NonNull Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        if(newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE){
            setContentView(R.layout.activity_main);
            Toast.makeText(this, "LANDSCAPE", Toast.LENGTH_SHORT).show();
        }else if(newConfig.orientation == 
                 Configuration.ORIENTATION_PORTRAIT){
            setContentView(R.layout.activity_main);
            Toast.makeText(this, "PORTRAIT", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        show("onStart");
    }

    // 가운데 버튼으로 최소화 시켰을 경우 onStop onStop을 제대로 처리를 안했을 경우 백그라운드에서 계속 도는 경우가 있기 때문에 배터리가 많이달거나 핸드폰이 느려짐 따라서 여기에서 커넥션을 잠시 중지시키는 등 코드를 넣어주어야 한다.
    @Override
    protected void onStop() {
        super.onStop();
        show("onStop");
    }

    // Destroy 가 되기 전에 stop이 먼저 실행된다. start도 creat 이후 실행
    @Override
    protected void onDestroy() {
        super.onDestroy();
        show("onDestroy");
    }

    public void show(String str){
        Toast.makeText(this, str, Toast.LENGTH_SHORT).show();
    }


}
```



### 토스트, 스낵바 그리고 대화상자 사용하기 P217~

> 직접 토스트 화면을 만들어서 사용할 수 있다. **clickb2 중요! 많이 사용함**

> Toast 만들기 clickb1,2

> 스낵바는 외부 라이바르러를 추가해야 한다.  많이 사용은 안한다. clickb3
>
> ```
> // 스낵바 activity_main -> Containers -> AppBarLayout 옆 다운로드로 lib 받은 뒤 사용가능
> ```

> AlertDialog 는 android app 에서 import 해야한다  MainActivity에서만 사용 가능 clickb4
>
> 프로그래스 바 p227~  btprogress  버튼 5/6/7/8



inflater 는 많이 쓰인다.

```
//setView 화면 만들기 layout에 toast.xml 만들어놓고 가지고 오기
LayoutInflater inflater = getLayoutInflater();
View view = inflater.inflate(R.layout.toast,
        (ViewGroup) findViewById(R.id.toast_layout));  // 레이아웃 아이디
// 레이아웃 전체를 가지고 오면 그 안에 내용도 가지고 올 수 있다.
TextView tv = view.findViewById(R.id.textView);
tv.setText("INPUT TEXT");
```





```java
package com.example.p217;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;

public class MainActivity extends AppCompatActivity {

    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        progressBar = findViewById(R.id.progressBar);
    }

    public void clickb1(View v){
        // this 는 이 MainActivity 에 띄우겠다 라는 뜻
        Toast t = Toast.makeText(this, "Toast1 ...",Toast.LENGTH_LONG);
        // Garvity 는 시작점
        t.setGravity(Gravity.CENTER,50,100);
        t.show();
    }
    public void clickb2(View v){
        //setView 화면 만들기 layout에 toast.xml 만들어놓고 가지고 오기
        LayoutInflater inflater = getLayoutInflater();
        View view = inflater.inflate(R.layout.toast,
                (ViewGroup) findViewById(R.id.toast_layout));  // 레이아웃 아이디
        // 레이아웃 전체를 가지고 오면 그 안에 내용도 가지고 올 수 있다.
        TextView tv = view.findViewById(R.id.textView);
        tv.setText("INPUT TEXT");

        Toast t = new Toast(this);
        t.setGravity(Gravity.CENTER,0,0);
        t.setDuration(Toast.LENGTH_LONG);
        t.setView(view);
        t.show();
    }

    // 스낵바 activity_main -> Containers -> AppBarLayout 옆 다운로드로 lib 받은 뒤 사용가능
    public void clickb3(View v){
        Snackbar.make(v,"Snack Bar",Snackbar.LENGTH_LONG).show();
    }
    public void clickb4(View v){
        // Dialog 생성
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("My Dialog");
        builder.setMessage("Exit now");
        builder.setIcon(R.drawable.d2);

        // OK 버튼을 누르면
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                finish();
            }
        });
        // No 버튼을 누르면
        builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });

        // Dialog 실행
        AlertDialog dialog = builder.create();
        dialog.show();
    }

        // Progress Bar 작성 XML에서 progress bar 를 만들어야 한다.
    public void btprogress(View v){
        ProgressDialog progressDialog = null;
        
        if(v.getId() == R.id.button5){
            int pdata = progressBar.getProgress();
            progressBar.setProgress(pdata+1);
        }else if(v.getId() == R.id.button6){
            int pdata = progressBar.getProgress();
            progressBar.setProgress(pdata-1);
            
            // Progress Bar 다른 예시 progressDialog 올리기
        }else if(v.getId() == R.id.button7){
            progressDialog = new ProgressDialog(this);
            progressDialog.setProgressStyle(progressDialog.STYLE_SPINNER);
            progressDialog.setTitle("Downloading...");
            // progressDialog.setCancelable(false); // progressDialog 중 다른곳 클릭이 안되게 하는 함수 응용 -> 아작스로 데이터를 받아올떄
            // 다 받고 난뒤 dismiss 처리를 하면 된다. 하지만 잘못 할 경우 화면이 계속 꺼지않고 버튼이 안먹을 수 도 있다.
            progressDialog.show();
        }else if(v.getId() == R.id.button8){
            progressDialog.dismiss();  // dialog 끝내기  응용 = 다운로드가 끝났을때 넣으면 된다.
        }
    }



        // 뒤로가기 눌렀을 때 Dialog 생성 내가 만든 화면을 띄우기 넣기
    @Override
    public void onBackPressed() {
        // super.onBackPressed();  이게 있으면 아래 코드로 넘어가지 않는다.
        LayoutInflater layoutInflater = getLayoutInflater();
        View view = layoutInflater.inflate(R.layout.dialog,
                (ViewGroup) findViewById(R.id.dialog_layout));

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("My Dialog1");
        builder.setMessage("Message");
        builder.setView(view);
        // builder.setIcon(R.drawable.d1);

        builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                finish();
            }
        });
        builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();

    }
} // end class
```



