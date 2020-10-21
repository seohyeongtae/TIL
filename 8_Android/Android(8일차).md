## 안드로이드 8일차



## Fragment 에 google maps 뿌리기 P287_map



> mapview 사용
>
> gradle, manifest 구성 동일 
>
> 아예 secondactvity 를 만들어 지도를 띄우는 방법도 있다.



> MainActivity

```java
package com.example.p287;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class MainActivity extends AppCompatActivity implements Fragment1.View1Manager {
    Fragment1 fragment1;
    Fragment2 fragment2;
    Fragment3 fragment3;

    ActionBar actionBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        String [] permission = {
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION
        };
        ActivityCompat.requestPermissions(this,
                permission, 101);



//        fragment1 = new Fragment1();
        fragment1 =
                (Fragment1)getSupportFragmentManager().findFragmentById(
                        R.id.fragment
                );
        fragment2 = new Fragment2();
        fragment3 = new Fragment3();
        actionBar = getSupportActionBar();
        actionBar.setTitle("Fragment");
        actionBar.setLogo(R.drawable.d1);
        actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_HOME |
                ActionBar.DISPLAY_USE_LOGO);
        //actionBar.hide();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.mymenu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() == R.id.m1){
            Log.d("-----------------------","--------------------------------");
            changeTx("dd");
            getSupportFragmentManager().beginTransaction().replace(
                    R.id.fragment,fragment1
            ).commit();
        }else if(item.getItemId() == R.id.m2){
            getSupportFragmentManager().beginTransaction().replace(
                    R.id.fragment,fragment2
            ).commit();
        }else if(item.getItemId() == R.id.m3){
            getSupportFragmentManager().beginTransaction().replace(
                    R.id.fragment,fragment3
            ).commit();
        }
        return super.onOptionsItemSelected(item);
    }

    public void ckbt(View v){
        if(v.getId() == R.id.button){
            getSupportFragmentManager().beginTransaction().replace(
                    R.id.fragment,fragment1
            ).commit();
        }else if(v.getId() == R.id.button2){
            getSupportFragmentManager().beginTransaction().replace(
                    R.id.fragment,fragment2
            ).commit();
        }else if(v.getId() == R.id.button3){
            getSupportFragmentManager().beginTransaction().replace(
                    R.id.fragment,fragment3
            ).commit();
        }
    }

    @Override
    public void changeTx(String str) {
        fragment1.setTx(str);

    }
}

```



> fragment3.java

```java
package com.example.p287;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class Fragment3 extends Fragment {

    MapView gmap;

    public Fragment3() {

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_3, container, false);
        gmap = (MapView) v.findViewById(R.id.map);
        gmap.onCreate(savedInstanceState);
        gmap.onResume();

        MapsInitializer.initialize(getActivity().getApplicationContext());

        gmap.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap googleMap) {
                LatLng latlng = new LatLng(34.1742, -118.4580);

                if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                     return;
                }
                googleMap.setMyLocationEnabled(true);

                googleMap.addMarker(
                        new MarkerOptions().position(latlng).
                                title("공항").snippet("xxx")
                );
                googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latlng,10));
            }
        });

        return v;


    }



}
```



### 푸시 서비스와 센서 및 단말 기능 사용하기 (SMS, 푸시 메시지 등) P701

> 쿠팡 등 회사들은 푸시 서비스를 사용하기 위해 어플리케이션 화면을 웹, 웹서버로 구성함.
>
> mp3 파일은 res -> android resourse directory 에서 raw 폴더 만들어서 넣기
>
> raw 폴더는 영상, 소리등의 파일을 담당
>
> 진동은 permission 등록 필요



> MainActivity

```java
package com.example.p701;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.view.View;

public class MainActivity extends AppCompatActivity {
    NotificationManager manager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    // 진동 울리기
    public void ck1(View v){
        Vibrator vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        // 안드로이드 버젼 체크를 해야한다.
        if(Build.VERSION.SDK_INT >= 26){
            vibrator.vibrate(VibrationEffect.createOneShot(1000,10));
        }else{
            vibrator.vibrate(1000);
        }
    }
    // 안드로이드가 제공해주는 소리 띄우기
    public void ck2(View v){
        Uri uri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        Ringtone ringtone = RingtoneManager.getRingtone(getApplicationContext(), uri);
        ringtone.play();

    }
    // 다운받은 소리 띄우기
    public void ck3(View v){
        MediaPlayer player = MediaPlayer.create(getApplicationContext(), R.raw.mp);
        player.start();
    }
    // 알람 띄우기
    public void ck4(View v){
        manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        NotificationCompat.Builder builder = null;
        if(Build.VERSION.SDK_INT >= 26){
           if(manager.getNotificationChannel("ch1") == null){
               manager.createNotificationChannel(new NotificationChannel("ch1","chname",NotificationManager.IMPORTANCE_DEFAULT));
               builder = new NotificationCompat.Builder(this,"ch1");
           }
        }else{
           builder = new NotificationCompat.Builder(this);
        }
        builder.setContentTitle("Noti Test");
        builder.setContentText("Content Text");
        builder.setSmallIcon(R.drawable.d1);
        Notification noti = builder.build();
        manager.notify(1,noti);

    }

    // 알람 클릭하면 어플이 다시 켜짐
    public void ck5(View v){
        manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        NotificationCompat.Builder builder = null;
        if(Build.VERSION.SDK_INT >= 26){
            if(manager.getNotificationChannel("ch2") == null){
                manager.createNotificationChannel(new NotificationChannel("ch2","chname2",NotificationManager.IMPORTANCE_DEFAULT));
                builder = new NotificationCompat.Builder(this,"ch2");
            }
        }else{
            builder = new NotificationCompat.Builder(this);
        }
        Intent intent = new Intent(this, MainActivity.class);
        
        // new Intent[]{intent} 는 intent 로 원래 써야함.
        PendingIntent pendingIntent = PendingIntent.getActivities(this,101, new Intent[]{intent}, PendingIntent.FLAG_UPDATE_CURRENT);
        builder.setAutoCancel(true);
        builder.setContentIntent(pendingIntent);

        builder.setContentTitle("Noti Test");
        builder.setContentText("Content Text");
        builder.setSmallIcon(R.drawable.d1);
        Notification noti = builder.build();
        manager.notify(1,noti);

    }

} //class end
```



### 푸시 서비스 사용하기 (푸시메시지) (FCM 푸시 메시지) P711

> FCM(Firebase Cloud Messaging) 구글의 푸시 서비스 - 구글 클라우드 서버를 사용
>
> https://console.firebase.google.com/
>
> firbase 세팅 순서 따라 파일 수정
>
> Manifest  <uses-permission android:name="android.permission.INTERNET"/> 추가
>
> new - service 생성 MyFService.java ( MainActivity 밑에서 푸시 메세지를 받는 역할)



> build.gradle 에 firebase.google 의 설명과는 다르게 추가했다.

```java
	implementation 'com.google.firebase:firebase-core:17.2.0'
    implementation 'com.google.firebase:firebase-messaging:20.0.0'
    implementation 'com.google.firebase:firebase-analytics'
```



> AndroidManifest.xml  수정
>
> service.java 생성하면 자동으로 입력되는 <service></service>  내용 수정

```java
<?xml version="1.0" encoding="utf-8"?>
<manifest xmlns:android="http://schemas.android.com/apk/res/android"
    package="com.example.p711">

    <uses-permission android:name="android.permission.INTERNET" />

    <application
        android:allowBackup="true"
        android:icon="@mipmap/ic_launcher"
        android:label="@string/app_name"
        android:roundIcon="@mipmap/ic_launcher_round"
        android:supportsRtl="true"
        android:theme="@style/AppTheme">

        <service android:name=".MyFService">
            <intent-filter>
            <action android:name="com.google.firebase.MESSAGING_EVENT"/>
            </intent-filter>
        </service>

        <activity android:name=".MainActivity">
            <intent-filter>
                <action android:name="android.intent.action.MAIN" />

                <category android:name="android.intent.category.LAUNCHER" />
            </intent-filter>
        </activity>
    </application>

</manifest>
```

