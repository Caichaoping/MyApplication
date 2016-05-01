package com.cc.myapplication.net;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestListener;
import com.cc.myapplication.R;

/**
 * 注释：
 * 作者：菠菜 on 2016/5/1 14:49
 * 邮箱：971859818@qq.com
 */
public class GlideUtils {

    public static void display(Context context, ImageView imageView, String url, int placeholder, int error) {
        if(imageView == null) {
            throw new IllegalArgumentException("argument view error");
        }
        Glide.with(context).load(url).placeholder(placeholder)
                .error(error).crossFade().into(imageView);
    }

    public static void display(Context context, ImageView imageView, String url) {
        if(imageView == null) {
            throw new IllegalArgumentException("argument view error");
        }
        Glide.with(context).load(url).placeholder(R.drawable.ic_image_loading)
                .error(R.drawable.ic_image_loadfail).crossFade().into(imageView);
    }

    public static void display(Context context,ImageView imageView,String url,RequestListener listener){
        if(imageView == null) {
            throw new IllegalArgumentException("argument view error");
        }
        Glide.with(context).load(url).placeholder(R.drawable.ic_image_loading).listener(listener)
                .error(R.drawable.ic_image_loadfail).crossFade().into(imageView);
    }

}

