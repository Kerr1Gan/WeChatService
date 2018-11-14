package com.starwin.wechatservice.core.plugin.storage;

import java.util.ArrayList;
import java.util.List;

import com.starwin.wechatservice.core.config.Enums;
import com.starwin.wechatservice.core.WeChatMeta;

public class PipeLineManager {
	private List<MessagePipeLine> pipelines;

	static class Holder {
		static PipeLineManager instance = new PipeLineManager();
	}

	public static PipeLineManager instance() {
		return Holder.instance;
	}

	private PipeLineManager() {
		pipelines = new ArrayList<MessagePipeLine>();
	}

	public void process(WeChatMeta meta, String line, Enums.MsgType msgType) {
		for (MessagePipeLine pipeLine : pipelines) {
			if (pipeLine != null) {
				pipeLine.process(meta, line, msgType);
			}

		}
	}
}
