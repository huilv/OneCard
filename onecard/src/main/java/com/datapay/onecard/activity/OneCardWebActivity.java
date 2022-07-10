package com.datapay.onecard.activity;

import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.datapay.onecard.R;
import com.datapay.onecard.utils.StatusBarUtil;

public class OneCardWebActivity extends OneCardBaseActivity{
    @Override
    int getLayoutId() {
        return R.layout.activity_one_card_web;
    }

    @Override
    void initOneCard() {
        String htmlUrl=getIntent().getStringExtra("one_card_html_url");
        View one_card_top = findViewById(R.id.one_card_top);
        ViewGroup.LayoutParams layoutParams = one_card_top.getLayoutParams();
        layoutParams.height = StatusBarUtil.getStatusBarHeight(this);
        one_card_top.setLayoutParams(layoutParams);
        WebView mWebView= findViewById(R.id.one_card_webview);
        mWebView.setWebChromeClient(new WebChromeClient());
        mWebView.setWebViewClient(new WebViewClient());
        mWebView.loadUrl(htmlUrl);
    }

}
