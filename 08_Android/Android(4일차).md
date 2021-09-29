## 안드로이드 4일차 P362~



### Broadcast Receiver (브로드캐이스 수신자 이해하기)

> 문자보내기/문자받기/전화걸기/네트워크연결여부 (와이파이 이미지바꾸기) 확인하기
>
> Manifest 에 보안설정을 해주어야 한다.
>
> 교재 P377 위험권한 부여하기 포함(permission)



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

![KakaoTalk_20201015_182037623](Android(4%EC%9D%BC%EC%B0%A8)/KakaoTalk_20201015_182037623.jpg)



## 선택 위젯 만들기  P394~



### 나인패치 이미지 P395 (따로 실습안함)

> 이미지가 늘어남에 따라 글씨가 깨지거나 왜곡되는것을 방지하기 위해.



### ListView 만들기 (리싸이클러뷰 전단계) P426

> ListView 사용 ListView 안에 list는 java 파일에서 구현한다.

> MainActivity.java

```java
package com.example.p426;


import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    ListView listView;
    ArrayList<String> datas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // listView id 세팅
        listView = findViewById(R.id.listView);
    } // onCreate end

    // onItemClick 에서 datas 배열을 사용했기 때문에 final 로 바뀌었다.
    public void setList(final ArrayList<String> datas){
        // 그냥 데이터를 넣는 것이 아니라 ArrayAdapter 를 만든 뒤 list에 넣어야 한다.
        final ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                this,
                android.R.layout.simple_list_item_1,  // 화면 정보를 넣어줌
                datas
        );
        listView.setAdapter(adapter);

        // listView에 출력된 list를 클릭했을 경우
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override //int position 은 몇번째 list인지에 대한 정보 0 부터 시작
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setTitle("Hello");
                // datas 정보 가져오기 는 datas.get(position) 사용 datas가 배열이기 때문에 순서로 가져올 수 있다.
                builder.setMessage("Are you deleted this Item:"+ datas.get(position));
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        datas.remove(position);
                        //데이터 삭제 후 화면을 재세팅해야 반영된 것이 보여진다.
                        adapter.notifyDataSetChanged();
                    }
                });
                builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                builder.show();
            } //onItemClick end
        }); // setOnItemClickListener end
    } // setList end
    // 데이터 생성 , 네트워크에서 데이터 가져오기로 사용하면 된다.
    public void getData(){
        datas = new ArrayList<>();
        for(int i = 1; i <= 20; i++){
            datas.add("Item:"+i);
        }
        setList(datas);
    }

    public void ckbt(View v){
        getData();
    }


} // Class end
```



### 리싸이클러뷰 만들기 P427

> P426와 양식은 비슷하지만 안드로이드 기본 제공 list 모양이 아닌 listView 에 들어갈 list UI 만들기
>
> Person.java - ArrayList 데이터 관리 
>
> person.xml - layout에 UI 만들기
>
> ```
> UI 를 만들었을 경우 나만의 Adapter를 만들어야 한다. 기본 제공 List 이미를 썼을떄는 그냥 써도된다.
> class PersonAadpter extends BaseAdapter{}  implment 해야한다 빨간 글씨
> ```



> person.java

```java
package com.example.p427;

public class Person {
    int id;
    String name;
    String phone;

    public Person() {
    }

    public Person(int id, String name, String phone) {
        this.id = id;
        this.name = name;
        this.phone = phone;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
```



> MainActivity.java

```java
package com.example.p427;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    ListView listView;
    ArrayList<Person> persons;
    ConstraintLayout container;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        listView = findViewById(R.id.listView);
        container = findViewById(R.id.container);
    } // onCreate end

    // UI 를 만들었을 경우 나만의 Adapter를 만들어야 한다. 기본 제공 List 이미를 썼을떄는 그냥 써도된다.
    class PersonAdapter extends BaseAdapter{

        ArrayList<Person> datas;

        public PersonAdapter(ArrayList<Person> datas){
            this.datas = datas;
        }

        @Override
        public int getCount() {
            // 데이터의 갯수 만큼 뿌리겠다.
            return datas.size();
        }

        @Override
        public Object getItem(int position) {
            return datas.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            // position 에 따라 화면을 달라
            View view = null;
            LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            // person.xml 레이아웃에 넣겠다. id container 로 하여 받아왔다.
            view = inflater.inflate(R.layout.person, container, true);

            ImageView im = view.findViewById(R.id.imageView);
            TextView tx_name = view.findViewById(R.id.tx_name);
            TextView tx_phone = view.findViewById(R.id.tx_phone);
            Person p = datas.get(position);

            // setImgeResource 안에는 int가 들어가야 하므로 int 형식으로 data를 만들자.
            im.setImageResource(p.getId());
            tx_name.setText(p.getName());
            tx_phone.setText(p.getPhone());
            return view;
        }
    } // class PersonAadpter end

    public void setList(ArrayList<Person> persons){
        PersonAdapter personAadpter = new PersonAdapter(persons);
        listView.setAdapter(personAadpter);

    } // setList end

    public void getData(){
        // id 값은 int 이미지이기 떄문에
    persons = new ArrayList<>();
        persons.add(new Person(R.drawable.p1,"Lee Malsook","010-1253-2983"));
        persons.add(new Person(R.drawable.p2,"dsa Malsook","010-4567-2983"));
        persons.add(new Person(R.drawable.p3,"Kim Malsook","010-7545-2983"));
        persons.add(new Person(R.drawable.p4,"Jee Malsook","010-8987-2983"));
        persons.add(new Person(R.drawable.p5,"Seo Malsook","010-1238-2983"));
        persons.add(new Person(R.drawable.p6,"MAl Malsook","010-9878-2983"));
        persons.add(new Person(R.drawable.p7,"Son Malsook","010-5458-2983"));
        persons.add(new Person(R.drawable.p8,"Rim Malsook","010-4687-2983"));
        persons.add(new Person(R.drawable.p9,"Pim Malsook","010-3547-2983"));
        setList(persons);
    } // getData end

    public void ckbt(View v){
        getData();
    }
} // Class end
```



### WorkShop 426,427 종합개념

> person.xml 은 linearlayout 을 사용하여 좀 더 깔끔하게 했으며 alertdialog 도 사용
>
> alertdialog 에 view 로 이미지를 크게 넣기 위해 dialog.xml 를 추가로 만듬

> person.java

```java
package com.example.p428;

public class Person {
    int img;
    String id;
    String name;
    int age;

    public Person() {
    }

    public Person(int img, String id, String name, int age) {
        this.img = img;
        this.id = id;
        this.name = name;
        this.age = age;
    }

    public int getImg() {
        return img;
    }

    public void setImg(int img) {
        this.img = img;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }
}
```



> MainActivity.java

```java
package com.example.p428;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    ArrayList<Person> persons;
    ListView listView;
    LinearLayout person_container;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        listView = findViewById(R.id.listView);
        person_container = findViewById(R.id.person_container);

        // list 목록 클릭했을 경우 AlertDialog
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Person p = persons.get(position);
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);

                //alertdialog 에 들어갈 view 불러오기 및 생성 layout 자체에 id 생성후 불러와야 한다.
                LayoutInflater layoutInflater = getLayoutInflater();
                View dview = layoutInflater.inflate(R.layout.dialog,
                        (ViewGroup) findViewById(R.id.dlayout));
                
                // imageView2 세팅
                ImageView dimg = dview.findViewById(R.id.imageView2);
                dimg.setImageResource(p.getImg());
                builder.setView(dview);

                builder.setTitle("Hi");
                builder.setMessage("NAME:"+p.getName());
                builder.setIcon(p.getImg());

                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                builder.show();
            }
        }); // setOnItemClickListener end

    } //onCreate end

    class PersonAdapter extends BaseAdapter{
        ArrayList<Person> datas;

        public PersonAdapter(ArrayList<Person> datas) {
            this.datas = datas;
        }

        @Override
        public int getCount() {
            return datas.size();
        }

        @Override
        public Object getItem(int position) {
            return datas.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View view = null;
            LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.person, person_container,true);

            ImageView im = view.findViewById(R.id.imageView);
            TextView tx_id = view.findViewById(R.id.tx_id);
            TextView tx_name = view.findViewById(R.id.tx_name);
            TextView tx_age = view.findViewById(R.id.tx_age);
            Person p = datas.get(position);

            im.setImageResource(p.getImg());
            tx_id.setText(p.getId());
            tx_name.setText(p.getName());
            // int 를 setText 하려면 +"" 를 해야 한다.
            tx_age.setText(p.getAge()+"");
            return view;
        } // getView end
    } // class personadapter end


    public void setList(final ArrayList<Person> persons){
        PersonAdapter personAdapter = new PersonAdapter(persons);
        listView.setAdapter(personAdapter);
    } // setList end

    public void getData(){
        persons = new ArrayList<>();
        persons.add(new Person(R.drawable.p1,"0001","이말숙",29));
        persons.add(new Person(R.drawable.p2,"0002","김말숙",21));
        persons.add(new Person(R.drawable.p3,"0003","정말숙",22));
        persons.add(new Person(R.drawable.p4,"0004","상말숙",23));
        persons.add(new Person(R.drawable.p5,"0005","최말숙",24));
        persons.add(new Person(R.drawable.p6,"0006","서말숙",25));
        persons.add(new Person(R.drawable.p7,"0007","오말숙",26));
        persons.add(new Person(R.drawable.p8,"0008","성말숙",27));
        persons.add(new Person(R.drawable.p9,"0009","강말숙",28));
        setList(persons);
    }

    public void ckbt(View v){
        getData();
    }

} // Class end
```



## WorkShop 파일 p 440

![KakaoTalk_20201015_152147633](Android(4%EC%9D%BC%EC%B0%A8)/KakaoTalk_20201015_152147633.jpg)

- 이미지는 빼고 작업하시오.
- 추가 및 선택 후 삭제 가능
- ActionBar에 네트워크 상태 표시 (이미지로) Logo 이미지 변환

> MainActivity.java

```java
package com.example.p440;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;


import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    ArrayList<Person> persons;
    ListView listView;
    LinearLayout listlayout;
    TextView tx_info,edit_name,edit_date,edit_phone;

    // 네트워크 환경 연결결 여부확인
    BroadcastReceiver broadcastReceiver;
    IntentFilter intentFilter;

    // ActionBar 생성
   ActionBar actionBar;;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        listView = findViewById(R.id.listView);
        listlayout = findViewById(R.id.listLayout);
        tx_info = findViewById(R.id.tx_info);
        edit_name = findViewById(R.id.edit_name);
        edit_date = findViewById(R.id.edit_date);
        edit_phone = findViewById(R.id.edit_phone);

        persons = new ArrayList<Person>();

        //action bar 생성
        actionBar = getSupportActionBar();


        //브로드캐스트

        intentFilter = new IntentFilter();
        intentFilter.addAction("android.net.conn.CONNECTIVITY_CHANGE");

        broadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                String cmd = intent.getAction();
                ConnectivityManager cm = null;
                NetworkInfo wifi = null;

                if(cmd.equals("android.net.conn.CONNECTIVITY_CHANGE")){
                    cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
                    wifi = cm.getNetworkInfo(ConnectivityManager.TYPE_WIFI);

                    if(wifi != null && wifi.isConnected()){
                      Toast.makeText(context, "hi", Toast.LENGTH_SHORT).show();
                        actionBar.setLogo(R.drawable.wifi);
                        actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_HOME | ActionBar.DISPLAY_USE_LOGO);

                    }else {
                      Toast.makeText(context, "bye", Toast.LENGTH_SHORT).show();
                        actionBar.setLogo(R.drawable.nwifi);
                        actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_HOME | ActionBar.DISPLAY_USE_LOGO);

                    }
                }

            } // onReceive end
        };  // broadcastReceiver end
        registerReceiver(broadcastReceiver,intentFilter);
     } // onCreate end
    // App 이 꺼졌을 때 연결을 끊는다는 것을 반드시 넣어주어야 한다.
    @Override
    protected void onDestroy() {
        super.onDestroy();
        // 반드시 넣어주어야 함.~~~~~~~~~~~~~~~~~~~~
        unregisterReceiver(broadcastReceiver);
    } // onDestroy end


    class PersonAdapter extends BaseAdapter{
        ArrayList<Person> datas;

        public PersonAdapter(ArrayList<Person> datas){
            this.datas = datas;
        }

        @Override
        public int getCount() {
            return datas.size();
        }

        @Override
        public Object getItem(int position) {
            return datas.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View view = null;

            Person p = datas.get(position);
            LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.person, listlayout, true);

            TextView listname = view.findViewById(R.id.list_name);
            TextView listdate = view.findViewById(R.id.list_date);
            TextView listphone = view.findViewById(R.id.list_phone);


            listname.setText(p.getName());
            listdate.setText(p.getDate());
            listphone.setText(p.getPhone());


            return view;
        }
    }; // class PersonAdapter end


    public void setList(final ArrayList<Person> persons){
        final PersonAdapter personAdapter = new PersonAdapter(persons);
        listView.setAdapter(personAdapter);

        //list목록 클릭했을 때
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setTitle("Delete");
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                   persons.remove(position);
                   personAdapter.notifyDataSetChanged();
                    }
                });
                builder.show();
            }
        });

    } //setlist end

    public void getData(){
        String name = edit_name.getText().toString();
        String date = edit_date.getText().toString();
        String phone = edit_phone.getText().toString();
        persons.add(new Person(name,date,phone));

        setList(persons);
    }

    public void ckbt(View view){
        getData();
    }
} // Class end
```

