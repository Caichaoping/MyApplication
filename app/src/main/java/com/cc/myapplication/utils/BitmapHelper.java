package com.cc.myapplication.utils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;

import java.io.ByteArrayOutputStream;

/**
 * 注释：Bitmap工具类
 * 作者：菠菜 on 2016/5/1 10:17
 * 邮箱：971859818@qq.com
 */
public class BitmapHelper {
    public BitmapHelper() {
    }

    public static BitmapFactory.Options calculateInSampleSize(BitmapFactory.Options options, int
            reqWidth, int reqHeight) {
        int height = options.outHeight;
        int width = options.outWidth;
        int inSampleSize = 1;
        if (height > reqHeight || width > reqWidth) {
            int heightRatio = Math.round((float) height / (float) reqHeight);
            int widthRatio = Math.round((float) width / (float) reqWidth);
            inSampleSize = heightRatio < widthRatio ? heightRatio : widthRatio;
        }

        options.inSampleSize = inSampleSize;
        options.inJustDecodeBounds = false;
        return options;
    }

    //　bitmap 缩放
    public static Bitmap imageZoom(Bitmap bitmap, double maxSize) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 70, baos);
        byte[] b = baos.toByteArray();
        double mid = (double) (b.length / 1024);
        double i = mid / maxSize;
        if (i > 1.0D) {
            bitmap = scaleWithWH(bitmap, (double) bitmap.getWidth() / Math.sqrt(i), (double)
                    bitmap.getHeight() / Math.sqrt(i));
        }

        return bitmap;
    }

    //　按宽高缩放
    public static Bitmap scaleWithWH(Bitmap src, double w, double h) {
        if (w != 0.0D && h != 0.0D && src != null) {
            int width = src.getWidth();
            int height = src.getHeight();
            Matrix matrix = new Matrix();
            float scaleWidth = (float) (w / (double) width);
            float scaleHeight = (float) (h / (double) height);
            matrix.postScale(scaleWidth, scaleHeight);
            return Bitmap.createBitmap(src, 0, 0, width, height, matrix, true);
        } else {
            return src;
        }
    }

    //　按宽高缩放
    public static Bitmap scaleWithMatrix(Bitmap src, Matrix scaleMatrix) {
        return Bitmap.createBitmap(src, 0, 0, src.getWidth(), src.getHeight(), scaleMatrix, true);
    }

    //　按XY缩放
    public static Bitmap scaleWithXY(Bitmap src, float scaleX, float scaleY) {
        Matrix matrix = new Matrix();
        matrix.postScale(scaleX, scaleY);
        return Bitmap.createBitmap(src, 0, 0, src.getWidth(), src.getHeight(), matrix, true);
    }

    //　按XY缩放
    public static Bitmap scaleWithXY(Bitmap src, float scaleXY) {
        return scaleWithXY(src, scaleXY, scaleXY);
    }

    //　旋转
    public static Bitmap rotate(int angle, Bitmap bitmap) {
        Matrix matrix = new Matrix();
        matrix.postRotate((float) angle);
        return Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix,
                true);
    }

    //　回收资源 防止内存泄漏
    public static void doRecycledIfNot(Bitmap bitmap) {
        if (!bitmap.isRecycled()) {
            bitmap.recycle();
        }

    }
}

