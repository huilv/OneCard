package com.datapay.onecard.activity;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.datapay.onecard.utils.StatusBarUtil;

public abstract class OneCardBaseActivity extends AppCompatActivity {

    abstract int getLayoutId();

    abstract void initOneCard();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        initStatusBar();
        super.onCreate(savedInstanceState);
        setContentView(getLayoutId());
        initOneCard();
    }

    protected  void initStatusBar(){
        StatusBarUtil.transparencyBar(this);
        StatusBarUtil.StatusBarLightMode(this);
    }
}
