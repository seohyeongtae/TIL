package com.example.p474;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    ProgressBar progressBar, progressBar2;
    TextView textView,textView2;
    Button button,button2,button3;
    MyHandler myHandler;
    MyHandler2 myHandler2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        progressBar = findViewById(R.id.progressBar);
        progressBar2 = findViewById(R.id.progressBar2);
        progressBar.setMax(50);
        progressBar2.setMax(50);
        textView = findViewById(R.id.textView);
        textView2 = findViewById(R.id.textView2);
        button = findViewById(R.id.button);
        button2 = findViewById(R.id.button2);
        button3 = findViewById(R.id.button3);
        myHandler = new MyHandler();
        myHandler2 = new MyHandler2();
     }

     public void ckbt(View v) {
        if(v.getId() == R.id.button){
            // 서브 스레드 시작
            MyThread t = new MyThread();
            t.start();
            // 스레드가 돌아가는 동안 버튼이 중복으로 클릭이 안되도록
            button.setEnabled(false);

        }else if(v.getId() == R.id.button2){
            // implement 받은 스레드는 객체를 안에 넣어주어야 한다.
            Thread t = new Thread(new MyThread2());
            t.start();
            button2.setEnabled(false);
        }else if(v.getId() == R.id.button3){
            progress();
        }
     } // ckbt end

    public void progress(){
        final ProgressDialog progressDialog = new ProgressDialog(this);

        AlertDialog.Builder dailog = new AlertDialog.Builder(this);
        dailog.setTitle("progress");
        dailog.setMessage("5 seconds");
        final Handler handler = new Handler();
        dailog.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // seTCancelable false 를 하면 그냥 꺼지지 않는다.
                progressDialog.setCancelable(false);
                progressDialog.setTitle("Downloading...");
                progressDialog.show();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        //핸들러를 이용하여 5초후에 다이어로그 자동으로 꺼진다.
                     progressDialog.dismiss();
                    }
                },5000);
            }
        });
        dailog.show();
       }


        // Thread t = new Thread(){};      이렇게도 생성가능
     class MyThread extends Thread{
        // 모든 Thread 의 문맥은 run 함수안에서 진행
         @Override
         public void run() {
             for(int i = 1; i <= 50; i++){
                 progressBar.setProgress(i);
                 textView.setText(i+"");
                 try {
                     Thread.sleep(200);
                 } catch (InterruptedException e) {
                     e.printStackTrace();
                 }
             }
             // 서브스레드에서 메인스레드 위젯을 변경하기 위해서는 runOnUiThread 를 추가해야한다.
             runOnUiThread(new Runnable() {
                 @Override
                 public void run() {
                     // 중지시켰던 버튼을 스레드가 끝나면 다시 동작시킨다.
                     button.setEnabled(true);
                 }
             });

         }
     }; // Thread t end


    // Thread 만드는 2번째 방법 핸들러로 메시지를 던진 후 처리
    class MyHandler extends Handler{
        @Override
        public void handleMessage(@NonNull Message msg) {
            Bundle bundle = msg.getData();
            int data = bundle.getInt("tdata",0);
            textView.setText("Handler1:"+data);
            progressBar.setProgress(data);
        }
    }
    class MyHandler2 extends Handler{
        @Override
        public void handleMessage(@NonNull Message msg) {
            Bundle bundle = msg.getData();
            int data = bundle.getInt("tdata",0);
            textView2.setText("Handler2:"+data);
            progressBar2.setProgress(data);

            // 중지시켰던 버튼 다시 활성화 하는 2번째 방법
            if(data == 50){
                button2.setEnabled(true);
            }
        }
    }
    // Thread 만드는 2번째 방법 Runnable 인터페이스에서 받음
    class MyThread2 implements Runnable{

        @Override
        public void run() {
            for(int i = 1; i <= 50; i++){
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
               Message message = myHandler.obtainMessage();
               Message message2 = myHandler2.obtainMessage();
               //bundle 은 데이터를 담는 바구니 역할
               Bundle bundle = new Bundle();
               bundle.putInt("tdata",i);
               message.setData(bundle);
               message2.setData(bundle);
              // myHandler.sendMessage(message);
               myHandler2.sendMessage(message2);

            }
        }
    }


} // class end
