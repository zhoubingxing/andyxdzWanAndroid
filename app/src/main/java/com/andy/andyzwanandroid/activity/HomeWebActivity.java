package com.andy.andyzwanandroid.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.webkit.WebSettings;
import android.webkit.WebView;

import com.andy.andyzwanandroid.R;

public class HomeWebActivity extends AppCompatActivity {

    WebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_web);
        webView = findViewById(R.id.home_web_view);
        WebSettings webSettings = webView.getSettings();

        // 设置与Js交互的权限
        webSettings.setJavaScriptEnabled(true);
        // 设置允许JS弹窗
        webSettings.setJavaScriptCanOpenWindowsAutomatically(true);

        Intent intent = getIntent();
        if (intent != null) {
            webView.loadUrl(intent.getStringExtra("url"));
        }
    }

    /**
     * 销毁时注意需要对WebView进行销毁，防止内存泄露
     */
    @Override
    protected void onDestroy() {
        ViewParent parent = webView.getParent();
        if (parent instanceof ViewGroup) {
            ((ViewGroup) parent).removeView(webView);
        }
        webView.destroy();
        super.onDestroy();
    }
}