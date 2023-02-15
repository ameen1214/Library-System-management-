package com.example.librarysystemmanagement;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class Webview extends AppCompatActivity {
private WebView webview;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_webview);
        webview = (WebView)findViewById(R.id.webview);
        webview.setWebViewClient(new WebViewClient());
        webview.loadUrl("https://www.vaniercollege.qc.ca/");

        WebSettings webSettings= webview.getSettings();
        webSettings.setJavaScriptEnabled(true);

    }

    @Override
    public void onBackPressed() {
        if (webview.canGoBack()) {
            webview.goBack();
        } else {
        super.onBackPressed(); }
    }
}