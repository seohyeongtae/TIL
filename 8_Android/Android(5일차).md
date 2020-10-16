## 안드로이드 5일차 P458~



### 앱 화면에 웹브라우저 넣기 P458

> widgets -> WebView 사용 (id 지정)
>
> Manifest에 permission 줘야한다.



>AndroidManifest.xml

```java
<?xml version="1.0" encoding="utf-8"?>
<manifest xmlns:android="http://schemas.android.com/apk/res/android"
    package="com.example.p458">
    
    <uses-permission android:name="android.permission.INTERNET"/> // 추가함

    <application
        
        android:usesCleartextTraffic="true" // 추가해야 한다.
            
        android:allowBackup="true"
        android:icon="@mipmap/ic_launcher"
        android:label="@string/app_name"
        android:roundIcon="@mipmap/ic_launcher_round"
        android:supportsRtl="true"
        android:theme="@style/AppTheme">
        <activity android:name=".MainActivity">
            <intent-filter>
                <action android:name="android.intent.action.MAIN" />

                <category android:name="android.intent.category.LAUNCHER" />
            </intent-filter>
        </activity>
    </application>

</manifest>
```



> MainActivity.java

```java
package com.example.p458;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class MainActivity extends AppCompatActivity {

    WebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        webView = findViewById(R.id.webView);

        // 안드로이드가 제공하는 기본적인 브라우져 기능 (Html 랜더링해주는 기능)
        webView.setWebViewClient(new WebViewClient());

        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);

    }

    public void ckbt(View vi){
        if (vi.getId() == R.id.button){
            webView.loadUrl("http://m.naver.com");
        }else if (vi.getId() == R.id.button2){
            webView.loadUrl("http://m.daum.net");
        }else if (vi.getId() == R.id.button3){
            // 이클립스 연동
            webView.loadUrl("http://192.168.1.107/android");
        }

    }

}
```



### 시크바 사용하기 p464 (시크바에 따라 화면 밝기 조정)

> MainActivity

```java
package com.example.p464;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.WindowManager;
import android.widget.SeekBar;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    SeekBar seekBar;
    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        seekBar = findViewById(R.id.seekBar);
        textView = findViewById(R.id.textView);
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                textView.setText(progress + "");
                setBtight(progress);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }
    // 밝기조정 함수
    public void setBtight(int value){
        WindowManager.LayoutParams params = getWindow().getAttributes();
        params.screenBrightness = (float)value/100;
        getWindow().setAttributes(params);
    }
}
```



### 스레드와 핸들러 이해하기 P474

1. Thread & Runnable - 모든 애플리케이션에서 구현할 때 정교하게 컨트롤 하려면 Runnable , Handler 사용



 안드로이드에서만 구현할 때

1. Handler  
2. AsyncTask



> MainActivity

```java

```

