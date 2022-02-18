package com.example.p458;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class MainActivity extends AppCompatActivity {

    WebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        webView = findViewById(R.id.webView);

        // 안드로이드가 제공하는 기본적인 브라우져 기능 (Html 랜더링해주는 기능)
        webView.setWebViewClient(new WebViewClient());

        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);

    }

    public void ckbt(View vi){
        if (vi.getId() == R.id.button){
            webView.loadUrl("http://m.naver.com");
        }else if (vi.getId() == R.id.button2){
            webView.loadUrl("http://m.daum.net");
        }else if (vi.getId() == R.id.button3){
            webView.loadUrl("http://192.168.1.107/android");
        }

    }

}