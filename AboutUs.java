package com.example.flash.superkatale;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.webkit.WebView;

public class AboutUs extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_us);
        WebView webview = (WebView)findViewById(R.id.webView);
        webview.loadUrl("http://192.168.107.2/quamax-web/web/");
    }
}
