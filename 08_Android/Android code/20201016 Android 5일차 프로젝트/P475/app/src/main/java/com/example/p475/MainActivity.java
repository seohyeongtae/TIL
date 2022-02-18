package com.example.p475;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.Random;

public class MainActivity extends AppCompatActivity {

    TextView textView,textView2;
    ProgressBar progressBar,progressBar2;
    MyHandler myHandler;
    MyHandler2 myHandler2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textView = findViewById(R.id.textView);
        textView2 = findViewById(R.id.textView2);
        progressBar = findViewById(R.id.progressBar);
        progressBar2 = findViewById(R.id.progressBar2);
        myHandler = new MyHandler();
        myHandler2 = new MyHandler2();
        Thread1 t = new Thread1();
        Thread2 t2 = new Thread2();
        t.start();
        t2.start();
    }


    class MyHandler extends Handler{
        @Override
        public void handleMessage(@NonNull Message msg) {
            Bundle bundle = msg.getData();
            int data = bundle.getInt("kdata",0);
            textView.setText(data+"Km");
            progressBar.setProgress(data);
        }
    }
    class MyHandler2 extends Handler{
        @Override
        public void handleMessage(@NonNull Message msg) {
            Bundle bundle = msg.getData();
            int data = bundle.getInt("rdata",0);
            textView2.setText(data+"rpm");
            progressBar.setProgress(data);
        }
    }



    class Thread1 extends Thread{
        @Override
        public void run() {
            for(int i = 0; i<= 30; i++){
                Random r = new Random();
                int kdata = r.nextInt(200)+1;
                Message message = myHandler.obtainMessage();
                Bundle bundle = new Bundle();
                bundle.putInt("kdata",kdata);
                message.setData(bundle);
                myHandler.sendMessage(message);
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }

    }
}
    class Thread2 extends Thread{
        @Override
        public void run() {
            for(int i = 0; i<= 30; i++){
                Random r = new Random();
                int rdata = r.nextInt(1500)+1;
                Message message = myHandler2.obtainMessage();
                Bundle bundle = new Bundle();
                bundle.putInt("rdata",rdata);
                message.setData(bundle);
                myHandler2.sendMessage(message);
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }

        }
    }
            
            
} // class end