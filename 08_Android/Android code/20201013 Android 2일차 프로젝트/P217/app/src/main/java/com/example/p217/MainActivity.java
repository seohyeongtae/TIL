package com.example.p217;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;

public class MainActivity extends AppCompatActivity {

    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        progressBar = findViewById(R.id.progressBar);
        progressBar.setMax(10);
    }

    public void clickb1(View v){
        // this 는 이 MainActivity 에 띄우겠다 라는 뜻
        Toast t = Toast.makeText(this, "Toast1 ...",Toast.LENGTH_LONG);
        // Garvity 는 시작점
        t.setGravity(Gravity.CENTER,50,100);
        t.show();
    }
    public void clickb2(View v){
        //setView 화면 만들기 layout에 toast.xml 만들어놓고 가지고 오기
        LayoutInflater inflater = getLayoutInflater();
        View view = inflater.inflate(R.layout.toast,
                (ViewGroup) findViewById(R.id.toast_layout));  // 레이아웃 아이디
        // 레이아웃 전체를 가지고 오면 그 안에 내용도 가지고 올 수 있다.
        TextView tv = view.findViewById(R.id.textView);
        tv.setText("INPUT TEXT");

        Toast t = new Toast(this);
        t.setGravity(Gravity.CENTER,0,0);
        t.setDuration(Toast.LENGTH_LONG);
        t.setView(view);
        t.show();
    }

    // 스낵바 activity_main -> Containers -> AppBarLayout 옆 다운로드로 lib 받은 뒤 사용가능
    public void clickb3(View v){
        Snackbar.make(v,"Snack Bar",Snackbar.LENGTH_LONG).show();
    }
    public void clickb4(View v){
        // Dialog 생성
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("My Dialog");
        builder.setMessage("Exit now");
        builder.setIcon(R.drawable.d2);

        // OK 버튼을 누르면
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                finish();
            }
        });
        // No 버튼을 누르면
        builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });

        // Dialog 실행
        AlertDialog dialog = builder.create();
        dialog.show();
    }

        // Progress Bar 작성 XML에서 progress bar 를 만들어야 한다.
    public void btprogress(View v){
        ProgressDialog progressDialog = null;
        
        if(v.getId() == R.id.button5){
            int pdata = progressBar.getProgress();
            if(pdata < 10){
                progressBar.setProgress(pdata+1);
            }else {
                Toast.makeText(this, "MaxValue", Toast.LENGTH_SHORT).show();
            }


        }else if(v.getId() == R.id.button6){
            int pdata = progressBar.getProgress();
            progressBar.setProgress(pdata-1);
            
            // Progress Bar 다른 예시 progressDialog 올리기
        }else if(v.getId() == R.id.button7){
            progressDialog = new ProgressDialog(this);
            progressDialog.setProgressStyle(progressDialog.STYLE_SPINNER);
            progressDialog.setTitle("Downloading...");
            // progressDialog.setCancelable(false); // progressDialog 중 다른곳 클릭이 안되게 하는 함수 응용 -> 아작스로 데이터를 받아올떄
            // 다 받고 난뒤 dismiss 처리를 하면 된다. 하지만 잘못 할 경우 화면이 계속 꺼지않고 버튼이 안먹을 수 도 있다.
            progressDialog.show();
        }else if(v.getId() == R.id.button8){
            progressDialog.dismiss();  // dialog 끝내기  응용 = 다운로드가 끝났을때 넣으면 된다.
        }
    }



        // 뒤로가기 눌렀을 때 Dialog 생성 내가 만든 화면을 띄우기 넣기
    @Override
    public void onBackPressed() {
        // super.onBackPressed();  이게 있으면 아래 코드로 넘어가지 않는다.
        LayoutInflater layoutInflater = getLayoutInflater();
        View view = layoutInflater.inflate(R.layout.dialog,
                (ViewGroup) findViewById(R.id.dialog_layout));

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("My Dialog1");
        builder.setMessage("Message");
        builder.setView(view);
        // builder.setIcon(R.drawable.d1);

        builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                finish();
            }
        });
        builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();

    }
} // end class