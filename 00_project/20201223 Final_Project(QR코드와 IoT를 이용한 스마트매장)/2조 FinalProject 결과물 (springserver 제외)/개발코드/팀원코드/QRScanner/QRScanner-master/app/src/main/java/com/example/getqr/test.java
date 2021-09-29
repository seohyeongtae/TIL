//package com.example.getqr;
//
//import android.app.Activity;
//import android.content.Intent;
//import android.net.ConnectivityManager;
//import android.net.NetworkInfo;
//import android.os.Bundle;
//import android.widget.TextView;
//import android.widget.Toast;
//
//import androidx.appcompat.app.AppCompatActivity;
//
//import com.google.zxing.integration.android.IntentIntegrator;
//import com.google.zxing.integration.android.IntentResult;
//
//import java.io.IOException;
//import java.io.InputStream;
//import java.io.OutputStream;
//import java.net.HttpURLConnection;
//import java.net.URL;
//
//public class MainActivity extends AppCompatActivity {
//
//    TextView textView;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main);
//
//        textView = findViewById(R.id.textView);
//        if (isConnected()) {
//            textView.setText("connected");
//        } else {
//            textView.setText("not connected");
//        }
//        //new IntentIntegrator(this).initiateScan();
//    }
//
//    public boolean isConnected() {
//        ConnectivityManager connMgr = (ConnectivityManager) getSystemService(Activity.CONNECTIVITY_SERVICE);
//        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
//        if (networkInfo != null && networkInfo.isConnected())
//            return true;
//        else
//            return false;
//    }
//
//
//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
//        if (result != null) {
//            if (result.getContents() == null) {
//                Toast.makeText(this, "Cancelled", Toast.LENGTH_LONG).show();
//                // todo
//            } else {
//                Toast.makeText(this, "Scanned: " + result.getContents(), Toast.LENGTH_LONG).show();
//                System.out.println("POSTRESULT" + POST(result.getContents()));
//            }
//        } else {
//            super.onActivityResult(requestCode, resultCode, data);
//        }
//    }
//
//    public static String POST(String json) {
//        System.out.println("aa" + json);
//        InputStream is = null;
//
//        String result = "";
//
//        try {
//            URL urlCon = new URL("http://ec2-3-35-11-144.ap-northeast-2.compute.amazonaws.com:8080/demo/orderlist/");
////            URL urlCon = new URL("http://192.168.0.92/tcpip/getJson.mc");
//            HttpURLConnection httpCon = (HttpURLConnection) urlCon.openConnection();
//
//            // Set some headers to inform server about the type of the content
//            httpCon.setRequestProperty("Accept", "application/json");
//            httpCon.setRequestProperty("Content-type", "application/json");
//
//
//            // OutputStream으로 POST 데이터를 넘겨주겠다는 옵션.
//            httpCon.setDoOutput(true);
//            // InputStream으로 서버로 부터 응답을 받겠다는 옵션.
//            httpCon.setDoInput(true);
//
//            OutputStream os = httpCon.getOutputStream();
//            os.write(json.getBytes("euc-kr"));
//            os.flush();
//
//            // receive response as inputStream
//            try {
//                is = httpCon.getInputStream();
//                // convert inputstream to string
//                if (is != null)
//                    System.out.println("isisisisis" + is.toString());
//                    //result = convertInputStreamToString(is);
//                else
//                    result = "Did not work!";
//
//            } catch (IOException e) {
//                e.printStackTrace();
//            } finally {
//                httpCon.disconnect();
//            }
//        } catch (IOException e) {
//            e.printStackTrace();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//
//        return result;
//
//
//    }
//}