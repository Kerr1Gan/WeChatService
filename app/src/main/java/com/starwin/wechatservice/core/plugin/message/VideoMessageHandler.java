package com.starwin.wechatservice.core.plugin.message;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.blade.kit.json.JSONObject;
import com.starwin.wechatservice.core.config.Enums;
import com.starwin.wechatservice.core.WeChatMeta;

public class VideoMessageHandler extends AbstractMessageHandler {

	private static final Logger LOGGER = LoggerFactory.getLogger(VideoMessageHandler.class);

	public VideoMessageHandler(WeChatMeta meta) {
		super(meta);
		this.meta = meta;
	}

	@Override
	public void process(JSONObject msg) {
		LOGGER.info("开始处理视频消息");
		download(msg, Enums.MsgType.VIDEO);
	}



}
