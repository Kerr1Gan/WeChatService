package com.starwin.wechatservice.core.plugin;

import com.blade.kit.json.JSONObject;
import com.starwin.wechatservice.core.WeChatMeta;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;
import java.util.Random;

public class FileHandler {
    public static Logger LOGGER = LoggerFactory.getLogger(FileHandler.class);
    private WeChatMeta meta;

    public FileHandler(WeChatMeta meta) {
        super();
        this.meta = meta;
    }

    public String uploadFile(String filePath, String toUserName) {

//		Map<String, Object> params = new HashMap<String, Object>();
//
//		Path path = Paths.get(filePath);
//		String contentType = FileUtil.getContentType(path, "text/plain");
//		File file = path.toFile();
//		params.put("id", "WU_FILE_0");
//		params.put("name", file.getName());
//		params.put("type", contentType);
//		params.put("size", file.length());
//		params.put("mediatype", FileUtil.isDoc(contentType) ? "doc" : FileUtil.isPicture(contentType) ? "pic" : "video"); // doc,video,pic
//		String fileMD5 = FileUtil.getMD5(file);
//		params.put("uploadmediarequest", getUploadmediaRequest(fileMD5, file.length(), 0, file.length(), toUserName));
//		params.put("webwx_data_ticket", CookieUtil.getValueFromCookieStr(meta.getCookie(), "webwx_data_ticket"));
//		params.put("pass_ticket", meta.getPass_ticket());
//		HttpRequest request = HttpRequest.post("https://file.wx2.qq.com/cgi-bin/mmwebwx-bin/webwxuploadmedia?f=json")
//				.contentType("multipart/form-data").part("filename", file.getName(), contentType, file).send(params.toString().getBytes());
//
//		String res = request.body();
//		LOGGER.debug("response from  wechat:{}", res);
//		request.disconnect();
//		if (StringKit.isBlank(res)) {
//			throw new WechatException(res.toString());
//		}
        return null;
    }

    private JSONObject getUploadmediaRequest(String fileMD5, long totalLen, long startPos, long dataLen, String toUserName) {
        String clientMediaId = String.valueOf(new Date().getTime()) + String.valueOf(new Random().nextLong()).substring(0, 4);
        JSONObject mediaRequest = new JSONObject();
        // TODO unkonw the meaning of uploadType
        mediaRequest.put("UploadType", 2);
        mediaRequest.put("BaseRequest", this.meta.getBaseRequest());
        mediaRequest.put("ClientMediaId", clientMediaId);
        mediaRequest.put("TotalLen", totalLen);
        mediaRequest.put("StartPos", startPos);
        mediaRequest.put("DataLen", dataLen);
        // TODO unkonw the meaning of MediaType
        mediaRequest.put("MediaType", 4);
        mediaRequest.put("FromUserName", meta.getUser().getString("UserName"));

        mediaRequest.put("ToUserName", toUserName == null ? "filehelper" : toUserName);
        mediaRequest.put("FileMd5", fileMD5);
        return mediaRequest;

    }
}
