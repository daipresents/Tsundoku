package com.daipresents.tsundoku;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.daipresents.tsundoku.book.BookParcelable;

public class WebViewActivity extends AppCompatActivity {

    private static final String TAG = WebViewActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_view);

        Intent receivedIntent = getIntent();
        String url = receivedIntent.getStringExtra("url");
        Log.v(TAG, "onCreate: url is " + url);

        WebView webView = (WebView) findViewById(R.id.webView);
        webView.setWebViewClient(new WebViewClient());
        webView.loadUrl(url);
        webView.getSettings().setJavaScriptEnabled(true);
    }
}
