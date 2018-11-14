package com.starwin.wechatservice.core.plugin.storage;

import com.starwin.wechatservice.core.config.Enums;
import com.starwin.wechatservice.core.WeChatMeta;

public interface MessagePipeLine {
	public void process(WeChatMeta meata, String line, Enums.MsgType msgType);

}
