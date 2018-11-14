package com.starwin.wechatservice.core.plugin.message;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.blade.kit.json.JSONObject;
import com.starwin.wechatservice.core.config.Enums;
import com.starwin.wechatservice.core.WeChatMeta;

public class PictureMessageHandler extends AbstractMessageHandler {

	private static final Logger LOGGER = LoggerFactory.getLogger(PictureMessageHandler.class);

	public PictureMessageHandler(WeChatMeta meta) {
		super(meta);
		this.meta = meta;
	}

	@Override
	public void process(JSONObject msg) {
		LOGGER.info("开始处理图片消息");
		download(msg, Enums.MsgType.PICTURE);

	}
}
