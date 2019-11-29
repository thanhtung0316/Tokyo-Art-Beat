package com.thanhtung.mockproject.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.thanhtung.mockproject.R;

public class WebViewActivity extends AppCompatActivity{
    private WebView webView;
    private ProgressDialog dialog;
    private String url;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.webview_activity);
        initViews();
        Intent intent = getIntent();
        url = intent.getStringExtra("url");
        webView.loadUrl(url);
        webView.setWebViewClient(new WebViewClient(){
            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                dialog.dismiss();
            }
        });
    }
    private void initViews() {
        webView = findViewById(R.id.wv_webview);
        webView.getSettings().setJavaScriptEnabled(true);
        dialog = new ProgressDialog(this);
        dialog.setMessage("Loading, please wait...");
        dialog.setCanceledOnTouchOutside(false);
        dialog.show();
    }

}

