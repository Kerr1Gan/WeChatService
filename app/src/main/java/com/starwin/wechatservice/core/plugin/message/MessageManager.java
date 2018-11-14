package com.starwin.wechatservice.core.plugin.message;

import com.blade.kit.json.JSONArray;
import com.blade.kit.json.JSONObject;
import com.starwin.wechatservice.core.config.Enums;
import com.starwin.wechatservice.core.WeChatMeta;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

public class MessageManager implements IMessageHandler {
    protected Logger logger = LoggerFactory.getLogger(MessageManager.class);

    private WeChatMeta meta;
    private Map<Enums.MsgType, IMessageHandler> handlers = new HashMap<Enums.MsgType, IMessageHandler>();

    public MessageManager(WeChatMeta meta) {
        this.meta = meta;
        register();
    }

    private IMessageHandler getMessageHandler(int type) {
        Enums.MsgType msgType = Enums.MsgType.getType(type);
        if (msgType == null)
            return handlers.get(Enums.MsgType.TXT);
        return handlers.get(msgType);

    }

    private void register() {
        handlers.put(Enums.MsgType.TXT, new TxtMessageHandler(meta));
        handlers.put(Enums.MsgType.VOICE, new VoiceMessageHandler(meta));
        handlers.put(Enums.MsgType.PICTURE, new PictureMessageHandler(meta));
        handlers.put(Enums.MsgType.SHARE_MP, new MpMessageHandler(meta));
        VideoMessageHandler videoMessageHandler = new VideoMessageHandler(meta);
        handlers.put(Enums.MsgType.SMALL_VIDEO, videoMessageHandler);
        handlers.put(Enums.MsgType.VIDEO, videoMessageHandler);
    }

    @Override
    public void process(JSONObject data) {
        if (null == data) {
            logger.warn("data不能为空");
        }
        JSONArray AddMsgList = data.get("AddMsgList").asArray();
        for (int i = 0, len = AddMsgList.size(); i < len; i++) {
            JSONObject msg = AddMsgList.get(i).asJSONObject();
            processSingleMsg(msg);
        }

    }

    private void processSingleMsg(JSONObject msg) {
        int msgType = msg.getInt("MsgType", 0);
        if (msgType == 51) {
            logger.info("msgType:{},do nothing", msgType);
            return;
        }
        IMessageHandler messageHandler = getMessageHandler(msgType);
        if (messageHandler == null) {
            logger.error("msgType:{},无法找到对应的消息处理器", msgType);
            return;
        }
        messageHandler.process(msg);

    }

}
