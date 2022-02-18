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