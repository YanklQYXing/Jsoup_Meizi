package com.kangle.firstarticle.utils;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.os.SystemClock;
import android.provider.MediaStore;

import java.io.File;

/**
 * Created by Administrator on 2016/11/22.
 */

public class ChosePhoto {
    /**
     * 调取相机拍照
     */
    public static Uri startCamera(Activity context) {
        Intent intent = new Intent();
        intent.setAction("android.media.action.IMAGE_CAPTURE");
        intent.addCategory("android.intent.category.DEFAULT");
        String uriFilePath = "/news_" + SystemClock.currentThreadTimeMillis() + ".jpg";
        File file = new File(Environment.getExternalStorageDirectory() + uriFilePath);
        Uri uri = Uri.fromFile(file);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
        context.startActivityForResult(intent, 0);
        return uri;
    }

    /**
     * 从图库获取图片
     */
    public static  void startPhoto(Activity context) {
        Intent intent;
        if (Build.VERSION.SDK_INT < 19) {
            intent = new Intent(Intent.ACTION_GET_CONTENT);
            intent.setType("image/*");
        } else {
            intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        }
        context.startActivityForResult(intent, 1);
    }

    /**
     * 调取相机拍照
     * @param context
     * @param code 返回码
     * @return
     */
    public static Uri startCamera01(Activity context, int code) {
        Intent intent = new Intent();
        intent.setAction("android.media.action.IMAGE_CAPTURE");
        intent.addCategory("android.intent.category.DEFAULT");
        String uriFilePath = "/news_" + SystemClock.currentThreadTimeMillis() + ".jpg";
        File file = new File(Environment.getExternalStorageDirectory() + uriFilePath);
        Uri uri = Uri.fromFile(file);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
        context.startActivityForResult(intent, code);
        return uri;
    }

    /**
     * 从图库获取图片
     * @param context
     * @param code 返回码
     * @return
     */
    public static  void startPhoto01(Activity context, int code) {
        Intent intent;
        if (Build.VERSION.SDK_INT < 19) {
            intent = new Intent(Intent.ACTION_GET_CONTENT);
            intent.setType("image/*");
        } else {
            intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        }
        context.startActivityForResult(intent, code);
    }
}
