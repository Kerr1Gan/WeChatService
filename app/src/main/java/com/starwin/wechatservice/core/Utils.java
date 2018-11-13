package com.starwin.wechatservice.core;

import java.util.Date;

public class Utils {
    public static int getUnixTimeByDate(Date date) {
        return (int) (date.getTime() / 1000L);
    }
}
