package com.kangle.firstarticle.imageUtil;

import android.graphics.Bitmap;

import java.io.File;

/**
 * Created by Administrator on 2017/5/6.
 * 图片返回接口
 */

public interface ImageDownLoadCallBack {
    void onDownLoadSuccess(File file);

    void onDownLoadSuccess(Bitmap bitmap);

    void onDownLoadSuccess(String filePath);

    void onDownLoadFailed();
}