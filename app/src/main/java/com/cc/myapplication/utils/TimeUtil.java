package com.cc.myapplication.utils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * 注释：时间相关工具类
 * 作者：菠菜 on 2016/4/30 16:57
 * 邮箱：971859818@qq.com
 */
public class TimeUtil {


    //　由生日计算出年龄
    public static String getAgeFromBirth(String birth) {
        if (birth == null || "".equals(birth)) {
            return "";
        }
        String pattern = "yyyy-MM-dd";
        SimpleDateFormat dateFormat = new SimpleDateFormat(pattern);
        try {
            Date date = dateFormat.parse(birth);

            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date);

            int birthYear = calendar.get(Calendar.YEAR);

            calendar.clear();
            calendar.setTime(new Date());
            int nowYear = calendar.get(Calendar.YEAR);

            return (nowYear - birthYear) + "";

        } catch (Exception e) {
            e.printStackTrace();
        }
        return "0";
    }

}
