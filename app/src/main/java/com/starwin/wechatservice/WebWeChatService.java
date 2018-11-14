package com.starwin.wechatservice;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;

import com.starwin.wechatservice.core.WeChatInstance;
import com.starwin.wechatservice.core.plugin.MessageListener;

public class WebWeChatService extends Service {

    private WeChatInstance mWeChat;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mWeChat = new WeChatInstance();
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    mWeChat.doLogin(WebWeChatService.this);
                    MessageListener listener = new MessageListener(mWeChat.getWeChatMeta());
                    listener.listen();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
