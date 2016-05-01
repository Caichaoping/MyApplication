package com.cc.myapplication.utils;

import java.util.Date;
import java.util.TimeZone;

/**
 * 注释：时区工具
 * 作者：菠菜 on 2016/5/1 10:11
 * 邮箱：971859818@qq.com
 */
public class TimeZoneUtil {

    /**
     * 判断用户的设备时区是否为东八区（中国） 2014年7月31日
     * @return
     */
    public static boolean isInEasternEightZones() {
        boolean defaultVaule = true;
        if (TimeZone.getDefault() == TimeZone.getTimeZone("GMT+08"))
            defaultVaule = true;
        else
            defaultVaule = false;
        return defaultVaule;
    }

    /**
     * 根据不同时区，转换时间 2014年7月31日
     * @param
     * @return
     */
    public static Date transformTime(Date date, TimeZone oldZone, TimeZone newZone) {
        Date finalDate = null;
        if (date != null) {
            int timeOffset = oldZone.getOffset(date.getTime())
                    - newZone.getOffset(date.getTime());
            finalDate = new Date(date.getTime() - timeOffset);
        }
        return finalDate;
    }
}

