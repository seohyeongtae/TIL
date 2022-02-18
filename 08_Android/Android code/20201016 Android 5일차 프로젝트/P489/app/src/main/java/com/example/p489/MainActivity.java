package com.example.p489;

import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    Button button,button2;
    SeekBar seekBar;
    TextView textView;
    ImageView imageView;
    MyAsynch myAsynch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        button = findViewById(R.id.button);
        button2 = findViewById(R.id.button2);
        // button enabled 세팅은 ckbt 에 하는 것이 아니라 asynch 안에 해야 한다.
        // 스레드의 진행 방식은 기존 위에 함수가 끝나고 아래 함수가 실행되는 것이 아니라
        // 실행시키고 바로 넘어가는 형식이기 때문에 스레드 execute 상에 문제가 생겼어도 button이 enable(false) 될 수 있기 때문에.
        button.setEnabled(true);
        button2.setEnabled(false);
        seekBar = findViewById(R.id.seekBar);
        seekBar.setMax(100);
        textView = findViewById(R.id.textView);
        imageView = findViewById(R.id.imageView);
    }

    public void ckbt1(View v){
        myAsynch = new MyAsynch();
        myAsynch.execute(100);

    }
    public void ckbt2(View v){
        //thread 중지
        myAsynch.cancel(true);
        // AsyncTask 안에 함수 실행
        myAsynch.onCancelled();

    }

    // 스레드를 제어한다. string 은 스레드가 끝날때 리턴 되는 값 <> 안에 설정은 임의로 작성 가능하다. argument 가 아니다.
    class MyAsynch extends AsyncTask<Integer,Integer,String>{
        @Override
        protected void onPreExecute() {
            button.setEnabled(false);
            button2.setEnabled(true);
        }

        // 스레드가 끝나면 string 으로 응답한다.
        @Override
        protected String doInBackground(Integer... integers) {
            int a = integers[0].intValue();
            // myAsynch.execute(); 스레드 시작하는 ()안에 숫자를 넣으면 위에 함수처럼 그안의 값을 받아올 수 있다.
            // 받아온 a 는 for 문 안에 i<=a 형식으로 활용할 수 있다.
            int sum = 0;
            for(int i =0; i<=a; i++){

                // Async가 중지인지 확인 후 중지된 상태이면 break
                if(isCancelled() == true){
                    break;
                }
                sum += i;
                // onProgressUpdate 로 데이터를 던진다.
                publishProgress(i);

                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            return "result: "+sum;
        }

        // integer ...   ... 이 들어가면 여러개의 값을 한번에 보낼 수 있으며 배열로 받기 때문에 데이터가 1개 여도
        // values[0] 배열 형태로 데이터를 끄집어낸다. 스레드가 진행되는 도중의 값을 볼 수 있다.
        @Override
        protected void onProgressUpdate(Integer... values) {
            int i = values[0].intValue();
            seekBar.setProgress(i);
            if(i <= 30){
                imageView.setImageResource(R.drawable.down);
            }else if(i <= 70){
                imageView.setImageResource(R.drawable.middle);
            }else if(i <= 100){
                imageView.setImageResource(R.drawable.up);
            }
        }

        // return 되는 값이 오는 곳
        @Override
        protected void onPostExecute(String s) {
            textView.setText(s);
            button.setEnabled(true);
            button2.setEnabled(false);
        }

        // Thread 중지 되었을 때
        @Override
        protected void onCancelled() {
            seekBar.setProgress(0);
            textView.setText("");
            imageView.setImageResource(R.drawable.ic_launcher_background);
            button.setEnabled(true);
            button2.setEnabled(false);
        }
    }

}