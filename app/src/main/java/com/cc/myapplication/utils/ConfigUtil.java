package com.cc.myapplication.utils;

import android.content.Context;

import com.cc.myapplication.R;

import java.io.IOException;
import java.util.Properties;

/**
 * 注释：配置文件读取
 * 作者：菠菜 on 2016/4/30 17:20
 * 邮箱：971859818@qq.com
 */
public class ConfigUtil {

    public static String getConfigContent(Context context,String key){
        String result;
        Properties props = new Properties();
        try {
            props.load(context.getResources().openRawResource(R.raw.config));
            result = props.getProperty(key);
            return  result;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "";
    }

}
