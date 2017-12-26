package com.kangle.firstarticle.imageUtil;

import android.app.WallpaperManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.TextUtils;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.model.GlideUrl;
import com.bumptech.glide.load.model.LazyHeaders;
import com.bumptech.glide.request.target.Target;
import com.kangle.firstarticle.utils.MyLog;
import com.kangle.meizipictures.base.MyApplication;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * 图片下载
 *
 */
public class DownLoadImageService implements Runnable {
    private String url;
    private Context context;
    private ImageDownLoadCallBack callBack;
    private File currentFile;
    private String myFilePath;
    public DownLoadImageService(Context context, String url, ImageDownLoadCallBack callBack) {
        this.url = url;
        this.callBack = callBack;
        this.context = context;
    }

    @Override
    public void run() {
        File file = null;
        Bitmap bitmap = null;
        try {
            String imgUrl = url;
            int dian = imgUrl.lastIndexOf("."); // 截取到最后一个点
            int gang = imgUrl.lastIndexOf("/");
            String substring1 = imgUrl.substring(0, gang); // 截取 妹子号后剩余下的
            int gang2 = substring1.lastIndexOf("/");
            String substring2 = substring1.substring((gang2+1), gang); // 截取 妹子号后剩余下的
            LazyHeaders.Builder builder = new LazyHeaders.Builder();
            builder.addHeader("Accept","image/webp,image/apng,image/*,*/*;q=0.8");
            builder.addHeader("Accept-Encoding","gzip, deflate");
            builder.addHeader("Accept-Language","zh-CN,zh;q=0.9");
            builder.addHeader("Cache-Control","no-cache");
            builder.addHeader("Connection","keep-alive");
            builder.addHeader("Host","img.mmjpg.com");
            builder.addHeader("Pragma","no-cache");
            builder.addHeader("Referer","http://www.mmjpg.com/mm/"+substring2);
            builder.addHeader("User-Agent","Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/62.0.3202.75 Safari/537.36");
            GlideUrl cookie1 = new GlideUrl(url, builder.build());
            bitmap = Glide.with(context)
                    .load(cookie1)
                    .asBitmap()
                    .into(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL)
                    .get();
            if (bitmap != null){
                // 在这里执行图片保存方法
                myFilePath = saveImageToGallery(context,bitmap,url);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
//            if (file != null) {
//                callBack.onDownLoadSuccess(file);
//            } else {
//                callBack.onDownLoadFailed();
//            }
            if (bitmap != null && currentFile.exists() && !TextUtils.isEmpty(myFilePath)) {
                callBack.onDownLoadSuccess(bitmap);
                callBack.onDownLoadSuccess(myFilePath);
            } else {
                callBack.onDownLoadFailed();
            }
        }
    }

    public String saveImageToGallery(Context context, Bitmap bmp,String url) {
        // 首先保存图片
        File file = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES).getAbsoluteFile();//注意小米手机必须这样获得public绝对路径
        String fileName = "meizi";
        String meiziNum = MyApplication.wMeizi!=""?MyApplication.wMeizi+"-":"000";
        File appDir = new File(file ,fileName);
        if (!appDir.exists()) {
            appDir.mkdirs();
        }
        int i = url.lastIndexOf("/");
        String image_name = url.substring(i+1);
        String imageFileName = meiziNum+image_name;
        currentFile = new File(appDir, imageFileName);
        FileOutputStream fos = null;
        if (!currentFile.exists()){
            try {
                fos = new FileOutputStream(currentFile);
                bmp.compress(Bitmap.CompressFormat.JPEG, 100, fos);
                fos.flush();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                try {
                    if (fos != null) {
                        fos.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }


        // 其次把文件插入到系统图库
        try {
            MediaStore.Images.Media.insertImage(context.getContentResolver(),
                    currentFile.getAbsolutePath(), fileName, null);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        // 最后通知图库更新
        if (currentFile.exists()){
            context.sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.fromFile(new File(currentFile.getPath()))));
            return currentFile.getPath();
        }
        return null;
    }



}