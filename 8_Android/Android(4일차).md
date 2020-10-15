## 안드로이드 4일차 P362~



### Broadcast Receiver (브로드캐이스 수신자 이해하기)

> 문자보내기/문자받기/전화걸기/네트워크연결여부 (와이파이 이미지바꾸기) 확인하기
>
> Manifest 에 보안설정을 해주어야 한다.



> Manifest  - 사용할 퍼미션 등록

```java
<?xml version="1.0" encoding="utf-8"?>
<manifest xmlns:android="http://schemas.android.com/apk/res/android"
    package="com.example.p362">

    <uses-permission android:name="android.permission.ACCESS_NETWORK_STATE" />
    <uses-permission android:name="android.permission.RECEIVE_SMS" />
    <uses-permission android:name="android.permission.SEND_SMS" />
    <uses-permission android:name="android.permission.CALL_PHONE" />


    <application
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



> MainActivity

```java
package com.example.p362;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.PermissionChecker;

import android.Manifest;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.media.Image;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.telephony.SmsMessage;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Date;

public class MainActivity extends AppCompatActivity {
    ImageView imageView;
    TextView textView;

    BroadcastReceiver broadcastReceiver;

    // 어떤 종류의 브로드캐스트를 받을 것인지 등록하는 객체
    IntentFilter intentFilter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        imageView = findViewById(R.id.imageView);
        textView = findViewById(R.id.textView);

        String permissions [] = {
                Manifest.permission.CALL_PHONE,
                // SMS 관련된건 허가여부를 함께 물어본다. send,receive
                Manifest.permission.SEND_SMS,
                Manifest.permission.RECEIVE_SMS
        };
        // App이 시작할 때 허가를 물어보는 것 101은 그냥 코드
        ActivityCompat.requestPermissions(this,permissions,101);

        // 브로드캐스트 리시버 등록  네트워크 연결 변경여부, SMS 문자 받기
        intentFilter = new IntentFilter();
        intentFilter.addAction("android.net.conn.CONNECTIVITY_CHANGE");
        intentFilter.addAction("android.provider.Telephony.SMS_RECEIVED");

        // new 컨트롤 스페이스 누르면 자동완성
        broadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                String cmd = intent.getAction();
                ConnectivityManager cm = null;
                NetworkInfo mobile = null;
                NetworkInfo wifi = null;

                // 브로드캐스트 리시버가 보낸 내용이 네트워크가 변경된 경우일 경우
                if(cmd.equals("android.net.conn.CONNECTIVITY_CHANGE")){
                    // cm = 현재 네트워크 상태
                    cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
                    mobile = cm.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
                    wifi = cm.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
                    if(mobile != null && mobile.isConnected()){

                    }else if(wifi != null && wifi.isConnected()){
                        imageView.setImageResource(R.drawable.wifi);
                    }else{
                        imageView.setImageResource(R.drawable.nwifi);
                    }
                // 브로드캐스트 리시버가 보낸 내용이 문자일 경우
                } else if (cmd.equals("android.provider.Telephony.SMS_RECEIVED")){
                    Toast.makeText(context, "SMS_RECEIVED", Toast.LENGTH_SHORT).show();
                    Bundle bundle = intent.getExtras();
                    Object [] obj = (Object []) bundle.get("pdus");
                    SmsMessage [] messages = new SmsMessage[obj.length];
                    for(int i = 0; i<obj.length; i++){
                        String format = bundle.getString("format");
                        messages[i] = SmsMessage.createFromPdu((byte[]) obj[i],format);
                    };
                    String msg ="";
                    if(messages != null && messages.length >0){
                        msg += messages[0].getOriginatingAddress()+"\n" ;
                        msg += messages[0].getMessageBody().toLowerCase()+"\n";
                        msg += new Date(messages[0].getTimestampMillis()).toString();
                        textView.setText(msg);
                    }
                } // else if end (문자일 경우)

            } // onReceive end
        }; // new Broadcast Receiver end
        // 가동시키기 (데이터가 오면 받겠다)
        registerReceiver(broadcastReceiver,intentFilter);
    } // onCreate end

    // App 이 꺼졌을 때 연결을 끊는다는 것을 반드시 넣어주어야 한다.
    @Override
    protected void onDestroy() {
        super.onDestroy();
        // 반드시 넣어주어야 함.~~~~~~~~~~~~~~~~~~~~
        unregisterReceiver(broadcastReceiver);
    } // onDestroy end

    public void ckbt(View v){
        if(v.getId() == R.id.button){
            // permissions 체크여부 확인 전화걸기 GRANTED 반드시 확인해야한다.
            int check = PermissionChecker.checkSelfPermission(this,Manifest.permission.CALL_PHONE);
            if(check == PackageManager.PERMISSION_GRANTED){
                Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:010-9090-9898"));
                startActivity(intent);
            }else {
                Toast.makeText(this, "DENIED", Toast.LENGTH_SHORT).show();
            }

            // 문자 보내기 - 문자도 전화와 마찬가지로 permission 체크를 해야한다. if문 아래는 아직 안한상태 (반드시 GRANTED 확인해야 한다.)
        }else if(v.getId() == R.id.button2){
            SmsManager smsManager = SmsManager.getDefault();
            smsManager.sendTextMessage(
                    "tel:010-2323-0432",
                    null,
                    "hi, Man ...",
                    null,
                    null
            );
            Toast.makeText(this, "Send ..OK", Toast.LENGTH_SHORT).show();
        }

    } // ckbt end

} // Class end
```



