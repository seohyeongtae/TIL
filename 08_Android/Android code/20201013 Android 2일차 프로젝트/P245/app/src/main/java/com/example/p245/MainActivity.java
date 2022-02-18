package com.example.p245;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.res.Resources;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    LinearLayout container;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        container = findViewById(R.id.container);
    }

    public void bt(View view){
        if(view.getId() == R.id.button){
            container.removeAllViews();
            LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            inflater.inflate(R.layout.login,container,true);

        }else if(view.getId() == R.id.button2){
            container.removeAllViews();
            LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            inflater.inflate(R.layout.admin,container,true);

        }else if(view.getId() == R.id.button3){
            container.removeAllViews();
            LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            inflater.inflate(R.layout.admininfo,container,true);

        }else if(view.getId() == R.id.button4){
            container.removeAllViews();
            LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            inflater.inflate(R.layout.adminlist,container,true);
            adminlist();
        }
    }

    // 로그인 화면 함수 프로그래스 다이로그 사용하기
    public void clicklogin (View view){
        ProgressDialog progressDialog = null;
        progressDialog = new ProgressDialog(this);
        progressDialog.setProgressStyle(progressDialog.STYLE_SPINNER);
        progressDialog.setTitle("Loging...");
        progressDialog.show();
    }

    // 회원가입 알림 다이어로그
    public void clickadmin (View view){
        LayoutInflater inflater = getLayoutInflater();
        View v = inflater.inflate(R.layout.dialog,
                (ViewGroup) findViewById(R.id.dialog_layout));
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("New admin");
        builder.setMessage("회원가입하시겠습니까?");
        builder.setView(v);

        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(MainActivity.this, "회원가입이 완료되었습니다.", Toast.LENGTH_SHORT).show();
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

    // 회원 정보 변경
    public  void adiminif (View view){

        String in,ipa,iph;
        TextView ifview1, ifview2, ifview3,ifname,ifpassword,ifphone;
        ifview1 = findViewById(R.id.adifview1);
        ifview2 = findViewById(R.id.adifview2);
        ifview3 = findViewById(R.id.adifview3);

        ifname = findViewById(R.id.adifname);
        ifpassword = findViewById(R.id.adifpassword);
        ifphone = findViewById(R.id.adifphone);

        in = ifname.getText().toString();
        ipa = ifpassword.getText().toString();
        iph = ifphone.getText().toString();

        ifview1.setText(in);
        ifview2.setText(ipa);
        ifview3.setText(iph);
    }


    // 회원정보 리스트 - Scroll 사용
    // 교재와 다른 점은 회원리스트를 클릭했을때 함수가 실행되기 위해 private로 설정함.
    // public 은 MainActivity의 함수로 xml onclick에 사용 가능하며 () 안에 View v 가 있어야한다.
    private void adminlist(){
        ScrollView scrollView;
        ImageView imageView;
        BitmapDrawable bitmap;  // 전역 변수에 선언해도 되지만 헷갈릴까봐 여기다가 씀

        scrollView = findViewById(R.id.scrollView);
        imageView = findViewById(R.id.listimage);
        // 수평 스크롤바 사용 기능 설정
        scrollView.setHorizontalScrollBarEnabled(true);

        // 리소스의 이미지 참고 이미지 이름은 소문자만  대문자 or 숫자로만 된 이름은 인식이 안된다.
        // gieIntrinsic ~ 는 이미지의 크기를 가져오는 것 아래 Params 로 이미지를 크기를 지정해주지 않으면
        // 자동으로 화면 크기에 맞게 이미지 크기가 조정 된다.
        Resources res = getResources();
        bitmap = (BitmapDrawable) res.getDrawable(R.drawable.a1);
        int bitmapWidth = bitmap.getIntrinsicWidth();
        int bitmapHeight = bitmap.getIntrinsicHeight();

        // 이미지 리소스와 이미지 크기 설정
        imageView.setImageDrawable(bitmap);
        imageView.getLayoutParams().width = bitmapWidth;
        imageView.getLayoutParams().height = bitmapHeight;

    }
    // 회원리스트 이미지 변경 - 회원리스트 안에 이미지변경 버튼 을 클릭하면 바꾸게 하기 위해 public 으로 하였다
    public void changeImage(View view){
        ImageView imageView;
       BitmapDrawable bitmap;
       imageView = findViewById(R.id.listimage);
       Resources res = getResources();
       bitmap = (BitmapDrawable) res.getDrawable(R.drawable.a2);
       int bitmapWidth = bitmap.getIntrinsicWidth();
       int bitmapHeight = bitmap.getIntrinsicHeight();

        imageView.setImageDrawable(bitmap);
        imageView.getLayoutParams().width = bitmapWidth;
        imageView.getLayoutParams().height = bitmapHeight;
    }


} // end class