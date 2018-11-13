package com.starwin.wechatservice.core;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.starwin.wechatservice.R;

import java.io.File;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class WeChatLoginActivity extends AppCompatActivity {

    private static final String EXTRA_QR_CODE_PATH = "qr_code_path_extra";
    private static final String EXTRA_UUID = "uuid_extra";

    private static final String[] PERMISSIONS = new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE};
    private static final int REQUEST_CODE = 1000;

    private String mUuid;
    private ExecutorService mService = Executors.newFixedThreadPool(2);

    public static Intent newIntent(Context context, String uuid, String path) {
        Intent intent = new Intent(context, WeChatLoginActivity.class);
        intent.putExtra(EXTRA_QR_CODE_PATH, path);
        intent.putExtra(EXTRA_UUID, uuid);
        return intent;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wechat_login);
        Intent intent = getIntent();
        if (intent != null) {
            String path = intent.getStringExtra(EXTRA_QR_CODE_PATH);
            mUuid = intent.getStringExtra(EXTRA_UUID);
            if (!TextUtils.isEmpty(path)) {
                ImageView imageView = findViewById(R.id.image);
                Glide.with(this).asBitmap().load(path).into(imageView);
                findViewById(R.id.btn).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        for (String permission : PERMISSIONS) {
                            int result = ActivityCompat.checkSelfPermission(WeChatLoginActivity.this, permission);
                            if (result != PackageManager.PERMISSION_GRANTED) {
                                ActivityCompat.requestPermissions(WeChatLoginActivity.this, PERMISSIONS, REQUEST_CODE);
                                return;
                            }
                        }
                        mService.submit(new Runnable() {
                            @Override
                            public void run() {
                                downloadQRCode(mUuid);
                            }
                        });
                    }
                });
                return;
            }
        }
        finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mService.shutdown();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_CODE) {
            for (String permission : PERMISSIONS) {
                int result = ActivityCompat.checkSelfPermission(WeChatLoginActivity.this, permission);
                if (result != PackageManager.PERMISSION_GRANTED) {
                    return;
                }
            }
            mService.submit(new Runnable() {
                @Override
                public void run() {
                    downloadQRCode(mUuid);
                }
            });
        }
    }

    void downloadQRCode(String uuid) {
        File dcim = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM);
        if (dcim != null) {
            final String path = WeChatApiUtil.getQrCode(uuid, dcim.getAbsolutePath());
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(WeChatLoginActivity.this, "二维码下载成功，路径：" + path, Toast.LENGTH_SHORT).show();
                }
            });
        } else {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(WeChatLoginActivity.this, "二维码下载失败", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }
}
