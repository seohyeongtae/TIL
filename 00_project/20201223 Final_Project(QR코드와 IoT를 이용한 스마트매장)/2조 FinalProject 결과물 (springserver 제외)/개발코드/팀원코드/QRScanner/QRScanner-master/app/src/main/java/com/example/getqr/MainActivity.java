package com.example.getqr;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        new IntentIntegrator(this).initiateScan();

    }
    public String sendIotJsonType() {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("itemname", "itemname");
            jsonObject.put("price", 100);
            jsonObject.put("stock", 100);
            jsonObject.put("category", "aaa");
            jsonObject.put("image", "cccc");

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return jsonObject.toString();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if (result != null) {
            if (result.getContents() == null) {
                Toast.makeText(this, "Cancelled", Toast.LENGTH_LONG).show();
                // todo
            } else {
                Toast.makeText(this, "Scanned: " + result.getContents(), Toast.LENGTH_LONG).show();

                String iotUrl = "http://ec2-3-35-11-144.ap-northeast-2.compute.amazonaws.com:8080/tcpipWebCon/getJson.mc";
                String orderlistUrl = "http://ec2-3-35-11-144.ap-northeast-2.compute.amazonaws.com:8080/demo/orderlist/";
                String jsonContent = result.getContents();

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {//3.0
                    new SampleTask().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, orderlistUrl, jsonContent);
                    new SampleTask().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, iotUrl, sendIotJsonType());

                }
                else {
                    new SampleTask().execute(orderlistUrl, jsonContent);
                    new SampleTask().execute(iotUrl, sendIotJsonType());
                }
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }


    private class SampleTask extends AsyncTask<String, Void, String> {
        protected String doInBackground(String... urls) {

            System.out.println("urls[0]: " + urls[0]);
            System.out.println("urls[1]: " + urls[1]);
            String resp = HttpConnectionUtil.postRequest(urls[0], urls[1]);

            return resp;
        }


        protected void onPostExecute(String result) {
            super.onPostExecute(result);

            Toast.makeText(MainActivity.this, result, Toast.LENGTH_LONG).show();
        }
    }

}