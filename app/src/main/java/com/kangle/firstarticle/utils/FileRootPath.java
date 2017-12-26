package com.kangle.firstarticle.utils;

import android.os.Environment;

import java.io.File;

/**
 * Created by Administrator on 2017/5/7.
 * 图片文件夹根目录
 */

public class FileRootPath {
    public static String getFileRootPth(){
        File file = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES).getAbsoluteFile();//注意小米手机必须这样获得public绝对路径
        String fileName = ".RV"; // 设置图片存放的文件夹为  .RV
        File appDir = new File(file ,fileName);
        if (!appDir.exists()) {
            appDir.mkdirs();
        }
        return appDir.getPath();
    }
}
