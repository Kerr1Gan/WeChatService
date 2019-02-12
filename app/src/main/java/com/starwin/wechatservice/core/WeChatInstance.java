package com.starwin.wechatservice.core;

import android.content.Context;
import android.content.Intent;

import com.blade.kit.json.JSONArray;
import com.blade.kit.json.JSONObject;

import java.net.HttpURLConnection;

import static android.content.Intent.FLAG_ACTIVITY_NEW_TASK;

public class WeChatInstance {

    private WeChatMeta weChatMeta;

    private JSONObject mWxInitJsonObj;

    public void doLogin(Context context) throws InterruptedException {
        String uuid = WeChatApiUtil.getUUID();
        final String path = WeChatApiUtil.getQrCodeUrl(uuid);
        Intent intent = WeChatLoginActivity.newIntent(context, uuid, path);
        intent.addFlags(FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
        while (!Thread.currentThread().isInterrupted()) {
            String res = WeChatApiUtil.waitLogin(0, uuid);
            String code = Matchers.match("window.code=(\\d+);", res);
            if (!String.valueOf(HttpURLConnection.HTTP_OK).equals(code)) {
                Thread.sleep(2000);
                continue;
            }
            WeChatMeta meta = WeChatApiUtil.newWechatMeta(res);
            WeChatApiUtil.login(meta);
            mWxInitJsonObj = WeChatApiUtil.wxInit(meta);
            WeChatApiUtil.openStatusNotify(meta);
            this.weChatMeta = meta;
            JSONArray contactList = mWxInitJsonObj.get("ContactList").asArray();
            //todo
            //WxLocalCache.init(this.meta).setLatestContactList(contactList);
            break;
        }
    }

    public JSONArray getContactList() {
        return mWxInitJsonObj.get("ContactList").asArray();
    }

    public WeChatMeta getWeChatMeta() {
        return weChatMeta;
    }
}
