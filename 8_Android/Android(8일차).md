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

    // action bar 메뉴 클릭시 fragment 이동
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
            }
            
              builder = new NotificationCompat.Builder(this,"ch2");
        }else{
            builder = new NotificationCompat.Builder(this);
        }
        // 어플 재실행 Intent와 Pending 사용
        Intent intent = new Intent(this, MainActivity.class);
       
        PendingIntent pendingIntent = PendingIntent.getActivity(this,101, Intent, PendingIntent.FLAG_UPDATE_CURRENT);
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

### 푸시 메시지가 어플 안으로 들어오면 그 데이터를 받아서 앱 내부에서 진동, 알람 띄우기

> FCM(Firebase Cloud Messaging) 구글의 푸시 서비스 - 구글 클라우드 서버를 사용
>
> https://console.firebase.google.com/
>
> firbase 세팅 순서 따라 파일 수정 (build.gradle 2가지 모두 수정, google json  파일 app폴더에 복사 등)
>
> Manifest  <uses-permission android:name="android.permission.INTERNET"/> 추가
>
> new - service 생성 MyFService.java ( MainActivity 밑에서 푸시 메세지를 받는 역할)
>
> 자바 이클립스 servelet 생성 / json lib 추가



> build.gradle 에 firebase.google 의 설명과는 다르게 추가했다.

```java
	apply plugin: 'com.google.gms.google-services'	


	implementation 'com.google.firebase:firebase-core:17.2.0'
    implementation 'com.google.firebase:firebase-messaging:20.0.0'
    implementation 'com.google.firebase:firebase-analytics'  // 없어도 된다.
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



> MainActivity

```java
package com.example.p711;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Build;
import android.os.Bundle;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.messaging.FirebaseMessaging;

public class MainActivity extends AppCompatActivity {

    TextView tx;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tx = findViewById(R.id.tx);

        FirebaseMessaging.getInstance().subscribeToTopic("car").addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                String msg = "FCM Complete...";
                if(!task.isSuccessful()){
                    msg = "FCM Fail...";
                }
                Log.d("[TAG]:",msg);
            }
        });

        // data 받을 준비
        LocalBroadcastManager lbm = LocalBroadcastManager.getInstance(this);
        lbm.registerReceiver(receiver,new IntentFilter("notification"));

    } // onCreate end

    // MyFService에서 보낸 data 받기
    public BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if(intent != null){
                String title = intent.getStringExtra("title");
                String control = intent.getStringExtra("control");
                String data = intent.getStringExtra("data");
                tx.setText(control+" "+data);
                Toast.makeText(MainActivity.this, title+ " "+ control+" "+data, Toast.LENGTH_SHORT).show();
                notification(title,control,data);
            }
        }
    };

    // data 가 들어오면 어플 내부에서 진동 , 상단 알람창 띄움 , 알람 클릭하면 어플 재실행
    public void notification(String title, String control, String data){
        NotificationManager manager;
        Vibrator vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);

        if(data != null) {
            // 진동
            if (Build.VERSION.SDK_INT >= 26) {
                vibrator.vibrate(VibrationEffect.createOneShot(1000, 10));
            } else {
                vibrator.vibrate(1000);
            }


            // 알람
            manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
            NotificationCompat.Builder builder = null;
            if (Build.VERSION.SDK_INT >= 26) {
                if(manager.getNotificationChannel("ch1") == null) {
                    manager.createNotificationChannel(new NotificationChannel("ch1", "chname1", NotificationManager.IMPORTANCE_DEFAULT));
                }

                builder = new NotificationCompat.Builder(this, "ch1");
            } else {                                                                                            
                builder = new NotificationCompat.Builder(this);
            }
            // 알람 클릭시 어플 재실행 Pending 사용
            Intent intent = new Intent(this,MainActivity.class);

            PendingIntent pendingIntent = PendingIntent.getActivity(this, 101, intent, PendingIntent.FLAG_UPDATE_CURRENT);
            builder.setAutoCancel(true);
            builder.setContentIntent(pendingIntent);

            builder.setContentTitle(title);
            builder.setContentText(control + data);
            builder.setSmallIcon(R.drawable.d2);
            Notification noti = builder.build();
            manager.notify(1, noti);
        }
    } // notification end


} // class end
```



> MyFService.java

```java
package com.example.p711;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

public class MyFService extends FirebaseMessagingService {

    public MyFService() {
    }

    @Override
    public void onMessageReceived(@NonNull RemoteMessage remoteMessage) {
        String title = remoteMessage.getNotification().getTitle();
        String control = remoteMessage.getData().get("control");
        String data = remoteMessage.getData().get("data");
        Log.d("[TAG]:",title+" "+control+" "+data);

        // 받은 data를 MainActivity로 notification 이라는 이름으로 보내기
        Intent intent = new Intent("notification");
        intent.putExtra("title",title);
        intent.putExtra("control",control);
        intent.putExtra("data",data);
        LocalBroadcastManager.getInstance(this).sendBroadcast(intent);

    }
}

```



> 이클립스 src에 servlet 생성 / json lib 추가
>
> 구글 클라우드에 메시지를 전송한 뒤 앱으로 전송

```java
package ftest;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONObject;

@WebServlet({ "/Ftest", "/ftest" })
public class Ftest extends HttpServlet {
	private static final long serialVersionUID = 1L;

    public Ftest() {
        super();
    }


	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
			URL url = null;
			try {
				url = new URL("https://fcm.googleapis.com/fcm/send");
			} catch (MalformedURLException e) {
				System.out.println("Error while creating Firebase URL | MalformedURLException");
				e.printStackTrace();
			}
			HttpURLConnection conn = null;
			try {
				conn = (HttpURLConnection) url.openConnection();
			} catch (IOException e) {
				System.out.println("Error while createing connection with Firebase URL | IOException");
				e.printStackTrace();
			}
			conn.setUseCaches(false);
			conn.setDoInput(true);
			conn.setDoOutput(true);
			conn.setRequestProperty("Content-Type", "application/json");

			// set my firebase server key https://console.firebase.google.com/ 설정 클라우드 메시징에 키값이 있다.
			conn.setRequestProperty("Authorization", "key="
					+ "AAAAW_wGDZs:APA91bHA7IHCWbj6P42H3hLnk-IzYkPpWSYY60XCSvx4fdNgE1uEpiMF9VxuqN_HmzmGQFzOezECXVQxCdRC-EK1fQgm3T_uZamJn0x3NEsFXDquqDhCD61_UNyh4MCuVoO9xFUguBbt");

			// create notification message into JSON format /topics/  옆에는 Android 양식에 맞게 수정
			JSONObject message = new JSONObject();
			message.put("to", "/topics/car");
			message.put("priority", "high");
			
			// 앱이 꺼져있는 상태에 알림가는 내용 (데이터안감)
			JSONObject notification = new JSONObject();
			notification.put("title", "title1");
			notification.put("body", "body1");
			message.put("notification", notification);
			
			// 앱이 켜져있는 상태에 데이터가 전송됨 (알림 안감)
			JSONObject data = new JSONObject();
			data.put("control", "control1");
			data.put("data", 100);
			message.put("data", data);


			try {
				OutputStreamWriter out = new OutputStreamWriter(conn.getOutputStream(), "UTF-8");
				out.write(message.toString());
				out.flush();
				conn.getInputStream();
				System.out.println("OK...............");

			} catch (IOException e) {
				System.out.println("Error while writing outputstream to firebase sending to ManageApp | IOException");
				e.printStackTrace();
			}	
				
	}

}

```

![KakaoTalk_20201021_173900021_01](Android(8%EC%9D%BC%EC%B0%A8)/KakaoTalk_20201021_173900021_01.jpg)

![KakaoTalk_20201021_173900021](Android(8%EC%9D%BC%EC%B0%A8)/KakaoTalk_20201021_173900021.jpg)