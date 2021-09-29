package com.example.user_ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

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
import android.content.SharedPreferences;
import android.graphics.Paint;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.messaging.FirebaseMessaging;

import org.json.JSONException;
import org.json.JSONObject;


public class MainActivity extends AppCompatActivity {

    TextView inputid;
    TextView inputpwd;
    Button loginbt;
    Button newbt;
    HttpAsync httpAsync;

    USERS user;

    SharedPreferences usersp;
    String gender;

    ActionBar actionBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        actionBar = getSupportActionBar();
        actionBar.hide();

        inputid = findViewById(R.id.inputid);
        inputpwd = findViewById(R.id.inputpwd);

        inputid.setPaintFlags(inputid.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
        inputpwd.setPaintFlags(inputpwd.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);

        loginbt = findViewById(R.id.loginbt);
        newbt = findViewById(R.id.newbt);

        // 회원정보 SharedPreferences 저장  - Login 되었을 경우
        // MainActivity 가 종료되면 SharedPreferences 데이터 삭제되는 부분은 구현 안했습니다.
        usersp = getSharedPreferences("login",MODE_PRIVATE);
        gender = usersp.getString("gender","0");
        //Toast.makeText(MainActivity.this,gender.toString().trim(),Toast.LENGTH_SHORT).show();
        if(gender.equals("남")) {
            FirebaseMessaging.getInstance().unsubscribeFromTopic("woman");
            FirebaseMessaging.getInstance().subscribeToTopic("man").addOnCompleteListener(new OnCompleteListener<Void>() {

                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    String msg = "FCM Complete...";
                    if (!task.isSuccessful()) {
                        msg = "FCM Fail...";
                    }
                    Log.d("[TAG]:", msg);
                }
            });
            // data 받을 준비
            LocalBroadcastManager lbm = LocalBroadcastManager.getInstance(this);
            lbm.registerReceiver(receiver, new IntentFilter("notification"));

        } else if(gender.equals("여")) {
            FirebaseMessaging.getInstance().unsubscribeFromTopic("man");
            FirebaseMessaging.getInstance().subscribeToTopic("woman").addOnCompleteListener(new OnCompleteListener<Void>() {

                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    String msg = "FCM Complete...";
                    if (!task.isSuccessful()) {
                        msg = "FCM Fail...";
                    }
                    Log.d("[TAG]:", msg);
                }
            });
            // data 받을 준비
            LocalBroadcastManager lbm = LocalBroadcastManager.getInstance(this);
            lbm.registerReceiver(receiver, new IntentFilter("notification"));
        }

    } // onCreate end
    // MyFService에서 보낸 data 받기
    public BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if(intent != null){
                String title = intent.getStringExtra("title");
                String control = intent.getStringExtra("control");
                String data = intent.getStringExtra("data");
               // tx.setText(control+" "+data);
              //  Toast.makeText(MainActivity.this, title+ " "+ control+" "+data, Toast.LENGTH_SHORT).show();
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



    public void loginbt (View v){
        String id =inputid.getText().toString().trim();
        String pwd = inputpwd.getText().toString();
        String url = "http://ec2-3-35-11-144.ap-northeast-2.compute.amazonaws.com:8080/demo/users/"+id;
        // Data 불러오기 및 로그인은 이후 구성
       // Toast.makeText(this,url,Toast.LENGTH_SHORT).show();
        httpAsync = new HttpAsync();
        httpAsync.execute(url);

    } // loginbt end


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

            JSONObject jo = null;
            try {
                jo = new JSONObject(s);
                String userid = jo.getString("userid");
                String pwd = jo.getString("pwd");
                int balance = jo.getInt("balance");
                String name = jo.getString("name");
                String gender = jo.getString("gender");
                int age = jo.getInt("age");
                user = new USERS(userid,pwd,balance,name,gender,age);
            } catch (JSONException e) {
                e.printStackTrace();
            }

            if(jo == null){
                // Login Fail 아이디가 없을 경우
                progressDialog.dismiss();
                AlertDialog.Builder dialog = new AlertDialog.Builder(MainActivity.this);
                dialog.setTitle("Login Fail");
                dialog.setMessage("아이디를 확인해 주세요");
                dialog.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        return;
                    }
                });
                dialog.show();
                return;
            } else  {
                if (user.getPwd().toString().trim().equals(inputpwd.getText().toString().trim())){
                    progressDialog.dismiss();

                    // Login 성공시 SharedPreferences 저장 (user 정보)
                    SharedPreferences.Editor edit = usersp.edit();
                    edit.clear();
                    edit.putString("login",user.getUserid().toString().trim());
                    edit.putString("gender",user.getGender().toString().trim());
                    edit.commit();
                    String useridinfo = user.getUserid().toString().trim();
                    String userename = user.getName().toString().trim();
                    String usergender = user.getGender().toString().trim();

                    // Login COMPLETE SecondActivity 로 전환
                    Intent intent = new Intent(MainActivity.this,SecondActivity.class);
                    intent.putExtra("useridinfo",useridinfo);
                    intent.putExtra("username",userename);
                    intent.putExtra("usergender",usergender);
                    startActivity(intent);

                }else{
                    // Login Fail 비밀번호가 틀렸을 경우
                    progressDialog.dismiss();
                    AlertDialog.Builder dialog = new AlertDialog.Builder(MainActivity.this);
                    dialog.setTitle("Login Fail");
                    dialog.setMessage("비밀번호를 확인해 주세요");
                    dialog.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            return;
                        }
                    });
                    dialog.show();
                };
            }
        }
    } // HttpAsync end

    // 회원가입 버튼
    public void newbt(View V){
        Intent intent = new Intent(MainActivity.this,ThirdActivity.class);
        startActivity(intent);
    }



} // class end