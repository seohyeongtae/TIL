package com.example.ws;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;

public class Fragment3 extends Fragment {

    TextView eventtext1, eventtext2;
    SecondActivity s;
    WebView webView;

    public Fragment3(SecondActivity s) {
        this.s = s;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        }

        // Firebase 로 데이터를 받아 TextView, webView 뿌리기
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_3, container,false);
        eventtext1 = v.findViewById(R.id.eventtextView);
        eventtext2 = v.findViewById(R.id.eventtextView2);
        webView = v.findViewById(R.id.eventWeb);

        webView.setWebViewClient(new WebViewClient());

        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);

        LocalBroadcastManager lbm = LocalBroadcastManager.getInstance(s);
        lbm.registerReceiver(receiver,new IntentFilter("notification"));
        return v;

    }
    public BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if(intent != null){
                String title = intent.getStringExtra("title");
                String control = intent.getStringExtra("control");
                String data = intent.getStringExtra("data");
                eventtext2.setText(control);
                webView.loadUrl(data);
            }
        }
    };

} // class end