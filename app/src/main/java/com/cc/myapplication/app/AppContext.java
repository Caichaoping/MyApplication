package com.cc.myapplication.app;

import android.app.Application;
import android.content.Context;

import com.cc.myapplication.utils.ConfigUtil;

import cn.bmob.sms.BmobSMS;

/**
 * 注释：Application
 * 作者：菠菜 on 2016/4/30 17:10
 * 邮箱：971859818@qq.com
 */
public class AppContext extends Application {

    public static Context mContext;

    @Override
    public void onCreate() {
        super.onCreate();
        mContext = this.getApplicationContext();
        BmobSMS.initialize(mContext, ConfigUtil.getConfigContent(mContext, "bmob.sms.id"));
    }
}
