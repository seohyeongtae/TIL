package com.example.p500;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    TextView tx_id, tx_pwd;
    HttpAsync httpAsync;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tx_id = findViewById(R.id.tx_id);
        tx_pwd = findViewById(R.id.tx_pwd);

    }
    public void ckbt(View v){
        String id = tx_id.getText().toString();
        String pwd = tx_pwd.getText().toString();
        String url ="http://192.168.1.107:8000/android/login.jsp";
        url += "?id="+id+"&pwd="+pwd;
        httpAsync = new HttpAsync();
        httpAsync.execute(url);

    }

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
            // trim 으로 받은 이유는 혹시모르는 빈 스페이스 공간ㅇ도 없애기 위해
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
                dialog.setMessage("Try Again...");
                dialog.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        return;
                    }
                });
                dialog.show();
            };
            //Toast.makeText(MainActivity.this, s, Toast.LENGTH_SHORT).show();
        }
     }
   }

