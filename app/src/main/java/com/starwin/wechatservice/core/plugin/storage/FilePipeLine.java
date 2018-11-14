package com.starwin.wechatservice.core.plugin.storage;

import com.starwin.wechatservice.core.WeChatMeta;
import com.starwin.wechatservice.core.config.Enums;


public class FilePipeLine implements MessagePipeLine {

    @Override
    public void process(WeChatMeta meta, String line, Enums.MsgType msgType) {
//
//		File f = new File(Constant.configReader.get("app.msg_location"));
//		if (!f.exists()) {
//			f.mkdirs();
//		}
//		try {
//			Files.write(Paths.get(Constant.configReader.get("app.msg_location")), line.toString().getBytes(), StandardOpenOption.CREATE,
//					StandardOpenOption.WRITE, StandardOpenOption.APPEND);
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
    }
}
