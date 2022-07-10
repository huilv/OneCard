package com.datapay.distinguish;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.datapay.onecard.activity.OneCardActivity;


public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById(R.id.idScan).setOnClickListener(view -> startActivity(new Intent(MainActivity.this, OneCardActivity.class)));
    }

}