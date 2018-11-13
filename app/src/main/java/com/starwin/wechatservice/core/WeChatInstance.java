package com.starwin.wechatservice.core;

import android.content.Context;
import android.content.Intent;

import com.blade.kit.json.JSONArray;
import com.blade.kit.json.JSONObject;

public class WeChatInstance {

    private WeChatMeta weChatMeta;

    public void doLogin(Context context) throws InterruptedException {
        String uuid = WeChatApiUtil.getUUID();
        final String path = WeChatApiUtil.getQrCodeUrl(uuid);
        Intent intent = WeChatLoginActivity.newIntent(context, uuid, path);
        context.startActivity(intent);
        while (!Thread.currentThread().isInterrupted()) {
            String res = WeChatApiUtil.waitLogin(0, uuid);
            String code = Matchers.match("window.code=(\\d+);", res);
            if (!Constant.HTTP_OK.equals(code)) {
                Thread.sleep(2000);
                continue;
            }
            WeChatMeta meta = WeChatApiUtil.newWechatMeta(res);
            WeChatApiUtil.login(meta);
            JSONObject wxInitObj = WeChatApiUtil.wxInit(meta);
            WeChatApiUtil.openStatusNotify(meta);
            this.weChatMeta = meta;
            JSONArray contactList = wxInitObj.get("ContactList").asArray();
            //todo
            //WxLocalCache.init(this.meta).setLatestContactList(contactList);
            break;
        }
    }
}
