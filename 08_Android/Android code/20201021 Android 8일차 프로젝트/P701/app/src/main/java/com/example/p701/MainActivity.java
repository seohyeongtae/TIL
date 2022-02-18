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