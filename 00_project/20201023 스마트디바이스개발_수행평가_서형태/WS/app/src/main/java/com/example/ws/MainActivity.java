package com.example.ws;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import android.Manifest;
import android.app.AlertDialog;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.util.Log;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.messaging.FirebaseMessaging;

public class MainActivity extends AppCompatActivity {
    WebView webView;
    TextView status_wifi,login_id, login_pwd;
    Button login_button;
    IntentFilter intentFilter;
    BroadcastReceiver broadcastReceiver;
    ActionBar actionBar;

    HttpAsync httpAsync;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        webView = findViewById(R.id.login_webView);
        status_wifi = findViewById(R.id.status_wifi);
        login_id = findViewById(R.id.login_id);
        login_pwd = findViewById(R.id.login_pwd);
        login_button = findViewById(R.id.login_button);
        actionBar = getSupportActionBar();
        actionBar.setTitle("Android 수행평가");
        actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_HOME | ActionBar.DISPLAY_SHOW_TITLE);

        String permission [] = {
                Manifest.permission.INTERNET,
                Manifest.permission.ACCESS_NETWORK_STATE,
                Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.ACCESS_FINE_LOCATION
        };
        ActivityCompat.requestPermissions(this,permission,101);

       // wifi 연결유무 확인
        intentFilter = new IntentFilter();
        intentFilter.addAction("android.net.conn.CONNECTIVITY_CHANGE");
        broadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                String cmd = intent.getAction();
                ConnectivityManager cm = null;
                NetworkInfo wifi = null;
                NetworkInfo mobile = null;
                if(cmd.equals("android.net.conn.CONNECTIVITY_CHANGE")){
                    // cm = 현재 네트워크 상태
                    cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
                    mobile = cm.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
                    wifi = cm.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
                    if(mobile != null && mobile.isConnected()){

                    }else if(wifi != null && wifi.isConnected()){
                        status_wifi.setText("인터넷에 연결중입니다.");
                    }else{
                        status_wifi.setText("인터넷에 연결되어있지 않습니다.");
                    }
                }
            }
        };  // BrodcastReciver end
        registerReceiver(broadcastReceiver,intentFilter);

        // WebView
        webView.setWebViewClient(new WebViewClient());
        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webView.loadUrl("http://m.cgv.co.kr/");

        //Firebase 메시지
        FirebaseMessaging.getInstance().subscribeToTopic("movie").addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                String msg = "FCM Complete...";
                if(!task.isSuccessful()){
                    msg = "FCM Fail...";
                }
                Log.d("[LOG]:",msg);
            }
        });

        LocalBroadcastManager lbm = LocalBroadcastManager.getInstance(this);
        lbm.registerReceiver(receiver,new IntentFilter("notification"));

    } // onCreate end

    // Firebase 메시지
    public BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if(intent != null){
                String title = intent.getStringExtra("title");
                String control = intent.getStringExtra("control");
                String data = intent.getStringExtra("data");
                Toast.makeText(MainActivity.this, title+ "  "+ control+" " ,Toast.LENGTH_SHORT).show();
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
            Intent intent = new Intent(this, Fragment3.class);

            PendingIntent pendingIntent = PendingIntent.getActivity(this, 101, intent, PendingIntent.FLAG_UPDATE_CURRENT);
            builder.setAutoCancel(true);
            builder.setContentIntent(pendingIntent);

            builder.setContentTitle(title);
            builder.setContentText(control);
            builder.setSmallIcon(R.drawable.cgv);
            Notification noti = builder.build();
            manager.notify(1, noti);
        }
    } // notification end


    // Login 버튼
    public  void login_bt(View v){
        String id = login_id.getText().toString();
        String pwd = login_pwd.getText().toString();
        String url = "http://192.168.1.107:8000/android/login.jsp";
        url += "?id="+id+"&pwd="+pwd;
        httpAsync = new HttpAsync();
        httpAsync.execute(url);
      //  Log.d("[LOG]:",url);
    }

    // 회원정보 데이터 받기
    class HttpAsync extends AsyncTask<String,String,String> {
        ProgressDialog progressDialog;

        @Override
        protected void onPreExecute() {
            // 데이터가 받는 동안 progressDialog 띄운다움 데이터가 다내려오면 꺼지게 만든다. onPostExecute 에서
            progressDialog = new ProgressDialog(MainActivity.this);
            progressDialog.setTitle("Login...");
            progressDialog.setCancelable(false);
            progressDialog.show();
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

        // return 되는 값이 오는 곳 이클립스에서 out.print 한 값이 들어온다.
        @Override
        protected void onPostExecute(String s) {
            // 데이터가 다 내려오면 progressDialog 종료 후 Login 여부 확인 및 각 코드 실행
            // trim 으로 받은 이유는 혹시모르는 빈 스페이스 공간도 없애기 위해
            String result = s.trim();

            if (result.equals("1")){
                progressDialog.dismiss();
                // Login COMPLETE SecondActivity 로 전환
                Intent intent = new Intent(MainActivity.this,SecondActivity.class);
                startActivity(intent);

            }else if (result.equals("2")){
                // Login Fail
                progressDialog.dismiss();
                AlertDialog.Builder dialog = new AlertDialog.Builder(MainActivity.this);
                dialog.setTitle("Login Fail");
                dialog.setMessage("아이디,비밀번호를 확인해 주세요");
                dialog.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        return;
                    }
                });
                dialog.show();
            };
        }
    } // HttpAsync end

} // class end