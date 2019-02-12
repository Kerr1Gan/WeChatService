package com.starwin.wechatservice;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById(R.id.button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startService(new Intent(MainActivity.this, WebWeChatService.class));
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        //ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.INTERNET}, 100);
    }
}
