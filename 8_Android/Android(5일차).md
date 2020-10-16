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
            webView.loadUrl("http://192.168.1.107:8000/android");
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
package com.example.p474;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    ProgressBar progressBar, progressBar2;
    TextView textView,textView2;
    Button button,button2,button3;
    MyHandler myHandler;
    MyHandler2 myHandler2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        progressBar = findViewById(R.id.progressBar);
        progressBar2 = findViewById(R.id.progressBar2);
        progressBar.setMax(50);
        progressBar2.setMax(50);
        textView = findViewById(R.id.textView);
        textView2 = findViewById(R.id.textView2);
        button = findViewById(R.id.button);
        button2 = findViewById(R.id.button2);
        button3 = findViewById(R.id.button3);
        myHandler = new MyHandler();
        myHandler2 = new MyHandler2();
     }

     public void ckbt(View v) {
        if(v.getId() == R.id.button){
            // 서브 스레드 시작
            MyThread t = new MyThread();
            t.start();
            // 스레드가 돌아가는 동안 버튼이 중복으로 클릭이 안되도록
            button.setEnabled(false);

        }else if(v.getId() == R.id.button2){
            // implement 받은 스레드는 객체를 안에 넣어주어야 한다.
            Thread t = new Thread(new MyThread2());
            t.start();
            button2.setEnabled(false);
        }else if(v.getId() == R.id.button3){
            progress();
        }
     } // ckbt end

    public void progress(){
        final ProgressDialog progressDialog = new ProgressDialog(this);

        AlertDialog.Builder dailog = new AlertDialog.Builder(this);
        dailog.setTitle("progress");
        dailog.setMessage("5 seconds");
        final Handler handler = new Handler();
        dailog.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // seTCancelable false 를 하면 그냥 꺼지지 않는다.
                progressDialog.setCancelable(false);
                progressDialog.setTitle("Downloading...");
                progressDialog.show();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        //핸들러를 이용하여 5초후에 다이어로그 자동으로 꺼진다.
                     progressDialog.dismiss();
                    }
                },5000);
            }
        });
        dailog.show();
       }


        // Thread t = new Thread(){};      이렇게도 생성가능
     class MyThread extends Thread{
        // 모든 Thread 의 문맥은 run 함수안에서 진행
         @Override
         public void run() {
             for(int i = 1; i <= 50; i++){
                 progressBar.setProgress(i);
                 textView.setText(i+"");
                 try {
                     Thread.sleep(200);
                 } catch (InterruptedException e) {
                     e.printStackTrace();
                 }
             }
             // 서브스레드에서 메인스레드 위젯을 변경하기 위해서는 runOnUiThread 를 추가해야한다.
             runOnUiThread(new Runnable() {
                 @Override
                 public void run() {
                     // 중지시켰던 버튼을 스레드가 끝나면 다시 동작시킨다.
                     button.setEnabled(true);
                 }
             });

         }
     }; // Thread t end


    // Thread 만드는 2번째 방법 핸들러로 메시지를 던진 후 처리
    class MyHandler extends Handler{
        @Override
        public void handleMessage(@NonNull Message msg) {
            Bundle bundle = msg.getData();
            int data = bundle.getInt("tdata",0);
            textView.setText("Handler1:"+data);
            progressBar.setProgress(data);
        }
    }
    class MyHandler2 extends Handler{
        @Override
        public void handleMessage(@NonNull Message msg) {
            Bundle bundle = msg.getData();
            int data = bundle.getInt("tdata",0);
            textView2.setText("Handler2:"+data);
            progressBar2.setProgress(data);

            // 중지시켰던 버튼 다시 활성화 하는 2번째 방법
            if(data == 50){
                button2.setEnabled(true);
            }
        }
    }
    // Thread 만드는 2번째 방법 Runnable 인터페이스에서 받음
    class MyThread2 implements Runnable{

        @Override
        public void run() {
            for(int i = 1; i <= 50; i++){
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
               Message message = myHandler.obtainMessage();
               Message message2 = myHandler2.obtainMessage();
               //bundle 은 데이터를 담는 바구니 역할
               Bundle bundle = new Bundle();
               bundle.putInt("tdata",i);
               message.setData(bundle);
               message2.setData(bundle);
              // myHandler.sendMessage(message);
               myHandler2.sendMessage(message2);

            }
        }
    }


} // class end

```

### 랜덤숫자 handler , Thread 이용하여 만들기 (시속, Rpm)

> MainActivity

```java
package com.example.p475;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.Random;

public class MainActivity extends AppCompatActivity {

    TextView textView,textView2;
    ProgressBar progressBar,progressBar2;
    MyHandler myHandler;
    MyHandler2 myHandler2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textView = findViewById(R.id.textView);
        textView2 = findViewById(R.id.textView2);
        progressBar = findViewById(R.id.progressBar);
        progressBar2 = findViewById(R.id.progressBar2);
        myHandler = new MyHandler();
        myHandler2 = new MyHandler2();
        Thread1 t = new Thread1();
        Thread2 t2 = new Thread2();
        t.start();
        t2.start();
    }


    class MyHandler extends Handler{
        @Override
        public void handleMessage(@NonNull Message msg) {
            Bundle bundle = msg.getData();
            int data = bundle.getInt("kdata",0);
            textView.setText(data+"Km");
            progressBar.setProgress(data);
        }
    }
    class MyHandler2 extends Handler{
        @Override
        public void handleMessage(@NonNull Message msg) {
            Bundle bundle = msg.getData();
            int data = bundle.getInt("rdata",0);
            textView2.setText(data+"rpm");
            progressBar.setProgress(data);
        }
    }



    class Thread1 extends Thread{
        @Override
        public void run() {
            for(int i = 0; i<= 30; i++){
                Random r = new Random();
                int kdata = r.nextInt(200)+1;
                Message message = myHandler.obtainMessage();
                Bundle bundle = new Bundle();
                bundle.putInt("kdata",kdata);
                message.setData(bundle);
                myHandler.sendMessage(message);
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }

    }
}
    class Thread2 extends Thread{
        @Override
        public void run() {
            for(int i = 0; i<= 30; i++){
                Random r = new Random();
                int rdata = r.nextInt(1500)+1;
                Message message = myHandler2.obtainMessage();
                Bundle bundle = new Bundle();
                bundle.putInt("rdata",rdata);
                message.setData(bundle);
                myHandler2.sendMessage(message);
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }

        }
    }
            
            
} // class end
```





### AsyncTask 사용하기 P489

> 스레드를 효과적으로 제어하기 위함 
>
> 스레드 진행 중 데이터 받기 / 결과값 받기 / 중지 시키기 등



> MainActivity

```java
package com.example.p489;

import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    Button button,button2;
    SeekBar seekBar;
    TextView textView;
    ImageView imageView;
    MyAsynch myAsynch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        button = findViewById(R.id.button);
        button2 = findViewById(R.id.button2);
        // button enabled 세팅은 ckbt 에 하는 것이 아니라 asynch 안에 해야 한다.
        // 스레드의 진행 방식은 기존 위에 함수가 끝나고 아래 함수가 실행되는 것이 아니라
        // 실행시키고 바로 넘어가는 형식이기 때문에 스레드 execute 상에 문제가 생겼어도 button이 enable(false) 될 수 있기 때문에.
        button.setEnabled(true);
        button2.setEnabled(false);
        seekBar = findViewById(R.id.seekBar);
        seekBar.setMax(100);
        textView = findViewById(R.id.textView);
        imageView = findViewById(R.id.imageView);
    }

    public void ckbt1(View v){
        myAsynch = new MyAsynch();
        myAsynch.execute(100);

    }
    public void ckbt2(View v){
        //thread 중지
        myAsynch.cancel(true);
        // AsyncTask 안에 함수 실행
        myAsynch.onCancelled();

    }

    // 스레드를 제어한다. string 은 스레드가 끝날때 리턴 되는 값 <> 안에 설정은 임의로 작성 가능하다. argument 가 아니다.
    class MyAsynch extends AsyncTask<Integer,Integer,String>{
        @Override
        protected void onPreExecute() {
            button.setEnabled(false);
            button2.setEnabled(true);
        }

        // 스레드가 끝나면 string 으로 응답한다.
        @Override
        protected String doInBackground(Integer... integers) {
            int a = integers[0].intValue();
            // myAsynch.execute(); 스레드 시작하는 ()안에 숫자를 넣으면 위에 함수처럼 그안의 값을 받아올 수 있다.
            // 받아온 a 는 for 문 안에 i<=a 형식으로 활용할 수 있다.
            int sum = 0;
            for(int i =0; i<=a; i++){

                // Async가 중지인지 확인 후 중지된 상태이면 break
                if(isCancelled() == true){
                    break;
                }
                sum += i;
                // onProgressUpdate 로 데이터를 던진다.
                publishProgress(i);

                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            return "result: "+sum;
        }

        // integer ...   ... 이 들어가면 여러개의 값을 한번에 보낼 수 있으며 배열로 받기 때문에 데이터가 1개 여도
        // values[0] 배열 형태로 데이터를 끄집어낸다. 스레드가 진행되는 도중의 값을 볼 수 있다.
        @Override
        protected void onProgressUpdate(Integer... values) {
            int i = values[0].intValue();
            seekBar.setProgress(i);
            if(i <= 30){
                imageView.setImageResource(R.drawable.down);
            }else if(i <= 70){
                imageView.setImageResource(R.drawable.middle);
            }else if(i <= 100){
                imageView.setImageResource(R.drawable.up);
            }
        }

        // return 되는 값이 오는 곳
        @Override
        protected void onPostExecute(String s) {
            textView.setText(s);
            button.setEnabled(true);
            button2.setEnabled(false);
        }

        // Thread 중지 되었을 때
        @Override
        protected void onCancelled() {
            seekBar.setProgress(0);
            textView.setText("");
            imageView.setImageResource(R.drawable.ic_launcher_background);
            button.setEnabled(true);
            button2.setEnabled(false);
        }
    }

}
```



### 서버에 데이터 요청하고 응답받기 P500

**이클립스, 톰캣으로 연동했다.**  이클립스 out.print 가  어플 Toast로 리턴된다.

> 이클립스 - web   login.jsp

```html
<%@ page language="java" contentType="text/html; charset=EUC-KR"
	pageEncoding="EUC-KR"%>
<%
	
	String id = request.getParameter("id");
	String pwd = request.getParameter("pwd");
	System.out.println(id + pwd);
	if (id.equals("id01") && pwd.equals("pwd01")) {
		out.print("1");
	} else {
		out.print("2");
	}
	
%>
```





>Mainfest : 
>
><uses-permission android:name="android.permission.INTERNET"/> 
>
> android:usesCleartextTraffic="true"    추가



> HttpConnect.java  (네이버 카페에 코드 있음)

```java
package com.example.p500;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class HttpConnect {
           public static String getString(String urlstr){
            String result = null;
            URL url = null;
            HttpURLConnection hcon = null;
            InputStream is = null;
            try{
                url = new URL(urlstr);
                hcon = (HttpURLConnection)url.openConnection();
                hcon.setConnectTimeout(2000);
                hcon.setRequestMethod("GET");
                is = new BufferedInputStream(hcon.getInputStream());
                result = convertStr(is);
            }catch(Exception e){
                e.printStackTrace();
            }
            return result;
        }

        // 받아온 것을 String 으로 바꿔준다.
        public static String convertStr(InputStream is){
            String result = null;
            BufferedReader bi = null;
            StringBuilder sb = new StringBuilder();
            try{
                bi = new BufferedReader(
                        new InputStreamReader(is)
                );
                String temp = "";
                while((temp =bi.readLine()) != null){
                    sb.append(temp);
                }
            }catch(Exception e){
                e.printStackTrace();
            }
            return sb.toString();
        }

}
```



> MainActivity

```java
package com.example.p500;

import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    TextView tx_id, tx_pwd1;
    HttpAsync httpAsync;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tx_id = findViewById(R.id.tx_id);
        tx_pwd1 = findViewById(R.id.tx_pwd);
    }
    public void ckbt(View v){
        String id = tx_id.getText().toString();
        String pwd = tx_pwd1.getText().toString();
        String url ="http://192.168.1.107:8000/android/login.jsp";
        url += "?id="+id+"&pwd="+pwd;
        httpAsync = new HttpAsync();
        httpAsync.execute(url);

    }

    class HttpAsync extends AsyncTask<String,String,String> {

        @Override
        protected void onPreExecute() {

        }

        @Override
        protected String doInBackground(String... strings) {
            String url = strings[0].toString();
            String result = HttpConnect.getString(url);
            return result;
        }

        @Override
        protected void onProgressUpdate(String... values) {
            super.onProgressUpdate(values);
        }

        // return 되는 값이 오는 곳
        @Override
        protected void onPostExecute(String s) {
            Toast.makeText(MainActivity.this, s, Toast.LENGTH_SHORT).show();
        }

    }

   }

```



