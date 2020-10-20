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

    // 버튼 눌렀을 때 위치정보 받아오기
    public void ck(View v){
//        startMyLocation();
    }

//    private void startMyLocation() {
//        Location location = null;
//        if(checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_DENIED){
//            Toast.makeText(this, "Finish", Toast.LENGTH_SHORT).show();
//            finish();
//        }
//        location = locationManager.getLastKnownLocation(
//                LocationManager.GPS_PROVIDER
//        );
//        if(location != null) {
//            double lat = location.getLatitude();
//            double lon = location.getLongitude();
//            textView.setText(lat + " " + lon);
//        }
//    }

    // 자동으로 위치데이터 받기 onCreate 에 locationmanager 에 정보 넣었다.
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

### 현재 위치의 지도 보여주기 P674 google Map API 사용하기

> empty 가 아닌 google map activity 로 새로 만들었다.
>
> console.cloud.google.com  들어가서 API 받기

**안드로이드 스튜디오 내부에 SHA-1 값이 자동을 나오지만 아래 찾는 방법도 참고해놓자**

**발급받은 API 값을 googl_maps_api.xml 에 넣기**

본인 컴퓨터 cmd 에서 SHA-1 인증서 디지털 지문 값을 찾아야 한다. 

(복사 붙여넣기 활용 처음 cd 로 자바파일 bin 으로 들어가야 한다.

cmd 내부에서 복사 , 붙여넣기는 오른쪽 클릭으로 사용 가능.  오른쪽클릭 붙여넣기 / 드래그 해서 오른쪽 클릭)

```cmd
C:\Users\i>cd C:\Program Files\Java\jdk1.8.0_251\bin

C:\Program Files\Java\jdk1.8.0_251\bin>keytool -list -v -keystore "C:\Users\i\.android\debug.keystore" -alias androiddebugkey -storepass android -keypass android
별칭 이름: androiddebugkey
생성 날짜: 2020. 10. 12
항목 유형: PrivateKeyEntry
인증서 체인 길이: 1
인증서[1]:
소유자: C=US, O=Android, CN=Android Debug
발행자: C=US, O=Android, CN=Android Debug
일련 번호: 1
적합한 시작 날짜: Mon Oct 12 11:24:36 KST 2020 종료 날짜: Wed Oct 05 11:24:36 KST 2050
인증서 지문:
         MD5:  22:D3:89:1F:22:C9:5B:8B:ED:98:31:9B:60:3A:8A:DC
         SHA1: 18:19:C0:64:F2:D8:B0:93:D0:90:0A:57:79:A1:4C:1C:B2:78:22:79
         SHA256: C0:77:95:28:2F:68:08:F0:CB:31:30:77:82:64:C5:66:6D:92:1C:07:60:E5:79:6B:8E:AD:FD:BD:C0:09:0A:8A
서명 알고리즘 이름: SHA1withRSA
주체 공용 키 알고리즘: 2048비트 RSA 키
버전: 1

Warning:
JKS 키 저장소는 고유 형식을 사용합니다. "keytool -importkeystore -srckeystore C:\Users\i\.android\debug.keystore -destkeystore C:\Users\i\.android\debug.keystore -deststoretype pkcs12"를 사용하는 산업 표준 형식인 PKCS12로 이전하는 것이 좋습니다.

C:\Program Files\Java\jdk1.8.0_251\bin>
```



### P675 직접 google maps fragment 만들기 (empty 양식사용)

* Gradle Scripts -> build.gradle(module:app) 에 implementation 'com.google.android.gms:play-services-maps:17.0.0' 를 추가해야 한다. - 이후 sync now

* AndroidMainfest  안에 activity 위에 아래 코드를 추가해야 한다. @string/google_maps_key에 API 키 값 넣기

  ```
  <meta-data
      android:name="com.google.android.geo.API_KEY"
      android:value="@string/google_maps_key" />
  ```

> build.gradle(Module:app)

```java
(생략)...
    
    dependencies {
    implementation fileTree(dir: "libs", include: ["*.jar"])
    implementation 'androidx.appcompat:appcompat:1.2.0'
    implementation 'com.google.android.gms:play-services-maps:17.0.0'
    implementation 'androidx.constraintlayout:constraintlayout:2.0.2'
    testImplementation 'junit:junit:4.12'
    androidTestImplementation 'androidx.test.ext:junit:1.1.2'
    androidTestImplementation 'androidx.test.espresso:espresso-core:3.3.0'

}

```

> AndroidManifest.xml

```java
<?xml version="1.0" encoding="utf-8"?>
<manifest xmlns:android="http://schemas.android.com/apk/res/android"
    package="com.example.p675">

    <application
        android:allowBackup="true"
        android:icon="@mipmap/ic_launcher"
        android:label="@string/app_name"
        android:roundIcon="@mipmap/ic_launcher_round"
        android:supportsRtl="true"
        android:theme="@style/AppTheme">

        <meta-data
            android:name="com.google.android.geo.API_KEY"
            android:value="AIzaSyDPjf_dk_A3CPBoD1P-xZeZ2F7ctTqn95A" />


        <activity android:name=".MainActivity">
            <intent-filter>
                <action android:name="android.intent.action.MAIN" />

                <category android:name="android.intent.category.LAUNCHER" />
            </intent-filter>
        </activity>
    </application>

</manifest>
```





>avtivity_main.xml
>
>fragment 코드를 입력하고 아래 코드
>
>​        android:name="com.google.android.gms.maps.SupportMapFragment"
>
>추가 후 위에 단계를 진행  

```java
<?xml version="1.0" encoding="utf-8"?>
<LinearLayout xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    xmlns:tools="http://schemas.android.com/tools"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    android:orientation="vertical"
    tools:context=".MainActivity" >

    <Button
        android:id="@+id/button"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:text="Button" />

    <Button
        android:id="@+id/button2"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:text="Button" />

    <fragment
        android:id="@+id/map"
        android:layout_width="match_parent"
        android:layout_height="match_parent"
        android:name="com.google.android.gms.maps.SupportMapFragment"
        />

</LinearLayout>
```



> MainActivity

```java
package com.example.p675;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class MainActivity extends AppCompatActivity {

    SupportMapFragment supportMapFragment;
    GoogleMap gmap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // activity main 에 만든 map fragment 를 통해 가지고 오기
        supportMapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        supportMapFragment.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap googleMap) {
                gmap = googleMap;
                LatLng latLng = new LatLng(37.517309, 126.958814);
                gmap.addMarker(
                        new MarkerOptions().position(latLng).title("노들섬")
                );
                // 숫자는 zoom level
                gmap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng,10));
            }
        });
    } // onCreate end
    public void ck1(View v){
        LatLng latLng = new LatLng(37.400991, 126.920311);
        gmap.addMarker(
                new MarkerOptions().position(latLng).title("안양")
        );
        gmap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng,15));
    }

    public void ck2(View v){
        LatLng latLng = new LatLng(37.535674, 126.995272);
        // icon bitmap 일 경우에는 v24로 저장해야 한다.
        gmap.addMarker(
                new MarkerOptions().position(latLng).title("이태원").snippet("상세정보").icon(BitmapDescriptorFactory.fromResource(R.drawable.d3))
        );
        gmap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng,10));
    }

}
```



### 나의 실시간 위치를 나타내는 네비게이션 만들기 P676

> 기본적인 구성은 위 P675 와 비슷하다. 현재 내 위치정보 가져오는 것은 P667
>
> 내 위치 변화에 따라 Marker 찍기
>
> 추가로 gmap.setMyLocationEnabled(true); 를 사용하기 위해 아래 퍼미션 추가

```
<uses-permission android:name="android.permission.ACCESS_COARSE_LOCATION"/>
```



> MainActivity

```java
package com.example.p676;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class MainActivity extends AppCompatActivity {

    SupportMapFragment supportMapFragment;
    GoogleMap gmap;

    TextView textView;
    LocationManager locationManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().hide();

        String [] permission = {
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION
        };
        // permission 권한 물어보기
        ActivityCompat.requestPermissions(this,permission,101);

        supportMapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        supportMapFragment.getMapAsync(new OnMapReadyCallback() {

            // gmap 이 만들어지는 곳
            @Override
            public void onMapReady(GoogleMap googleMap) {
                gmap = googleMap;

                // 권한 부여가 안되어있을 시 앱 종료
                if(checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_DENIED
                        || checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_DENIED ){
                    return;
                }

                // gmap.setMyLocationEnabled(true); 사용하려면
                // 퍼미션을 추가해야 한다. Manifest.permission.ACCESS_COARSE_LOCATION
                // 파란색으로 내위치 표시 마커 대신 사용 가능
                gmap.setMyLocationEnabled(true);

                LatLng latLng = new LatLng(37.400991, 126.920311);
                gmap.addMarker(
                        new MarkerOptions().position(latLng).title("안양").snippet("집가야지")
                );
                gmap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng,15));
            }
        });

        // Location
        textView = findViewById(R.id.textView);

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

    // 내 위치에 따라 포인터 변경
    class  MyLocation implements LocationListener {

        @Override
        public void onLocationChanged(@NonNull Location location) {
            double lat = location.getLatitude();
            double lon = location.getLongitude();
            textView.setText(lat+" "+lon);

            LatLng latLng = new LatLng(lat, lon);

            // 위에 gmap.setMyLocationEnabled(true); 을 마커대신 사용함
//            gmap.addMarker(
//                    new MarkerOptions().position(latLng).title("나는누구").snippet("여긴어디")
//            );
            gmap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng,15));
        }
    }

    // 백그라운드에서 계속 동작하기 때문에 MissingPerMission 은 이미 위에서 검사를 했기 때문에 생략하겠다는 뜻
    @SuppressLint("MissingPermission")
    @Override
    protected void onResume() {
        super.onResume();
        if(gmap != null){
            gmap.setMyLocationEnabled(true);  // 다시시작했을때
        }
    }
    @SuppressLint("MissingPermission")
    @Override
    protected void onPause() {
        super.onPause();
        if(gmap != null){
            gmap.setMyLocationEnabled(false);
        }
    }
} // class end
```

