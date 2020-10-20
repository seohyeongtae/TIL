## 안드로이드 7일차 P537

> 앱을 만들면서 데이터를 간단하게 저장하고 싶을 때는 SharedPreferences 사용
>
> 안드로이드는 임베디드 데이터베이스로 개별된 경량급 관계형 데이터베이스인 SQLite를 가지고 있다.



###  SharedPreferences 앱안에 데이터 저장 P552

> 앱이 삭제가 되지 않는 한 SharedPreferences 안에 저장이 된다. 
>
> ex) 자동 로그인 기능 등



> MainActivity.java

```java
package com.example.p552;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    SharedPreferences sp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        sp = getSharedPreferences("login",MODE_PRIVATE);
        // status 라는 이름의 sharedpreferences 를 불러옴
        String status = sp.getString("status","");
        Toast.makeText(this, status, Toast.LENGTH_LONG).show();
    }

    public void ck(View v){
        SharedPreferences.Editor edit = sp.edit();

        // status 라는 이름 으로 저장
        edit.putString("status","ok");
        edit.commit();
    }

    public void ck2(View v){
        // SharedPreferences 에 저장된 데이터 삭제
        SharedPreferences.Editor edit = sp.edit();

        // status 라는 이름 으로 저장 된 데이터 삭제
        edit.remove("status");
        edit.commit();
    }

}
```



### (교재)P567 앨범과 연락처 조회하기 

### (교재)P579 뷰에 그래프, 그림그리기

### (교재)P623 멀티미디어 다루기 (카메라,오디오,유튜브 영상 등)



### 위치기반 서비스와 앱 위젯 사용하기 P667 - GPS로 나의 위치 확인하기

>  Mainfest  에 권한 부여해야 한다.
>
> <uses-permission android:name="android.permission.ACCESS_FINE_LOCATION"/>



> MainActivity

```java
package com.example.p667;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    TextView textView;
    LocationManager locationManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textView = findViewById(R.id.textView);

        String [] permission = {
                Manifest.permission.ACCESS_FINE_LOCATION
        };
        // permission 권한 물어보기
        ActivityCompat.requestPermissions(this,permission,101);
        // 권한 부여가 안되어있을 시 앱 종료
        if(checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_DENIED){
            Toast.makeText(this, "Finish", Toast.LENGTH_SHORT).show();
            finish();
        }
        locationManager = (LocationManager)getSystemService(Context.LOCATION_SERVICE);

        // 앱이 켜지자마자 위치정보 지속적으로 받기 MyLocation 클래스 이용
        MyLocation myLocation = new MyLocation();
        locationManager.requestLocationUpdates(
                LocationManager.GPS_PROVIDER,
                1,
                0,  // 조금만 이동해도 데이터를 받겠다
                myLocation
        );

    } // onCreate end

    public void ck(View v){
        startMyLocation();
    }

    private void startMyLocation() {
        Location location = null;
        if(checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_DENIED){
            Toast.makeText(this, "Finish", Toast.LENGTH_SHORT).show();
            finish();
        }
        location = locationManager.getLastKnownLocation(
                LocationManager.GPS_PROVIDER
        );
        if(location != null) {
            double lat = location.getLatitude();
            double lon = location.getLongitude();
            textView.setText(lat + " " + lon);
        }
    }

    // 자동으로 위치데이터 전송 onCreate 에 locationmanager 에 정보 넣었다.
    class  MyLocation implements LocationListener{

        @Override
        public void onLocationChanged(@NonNull Location location) {
            double lat = location.getLatitude();
            double lon = location.getLongitude();
            textView.setText(lat+" "+lon);
        }
    }

} //  class end
```



### 

