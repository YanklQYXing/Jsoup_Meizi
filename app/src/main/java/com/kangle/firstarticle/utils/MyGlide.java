package com.kangle.firstarticle.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.text.TextUtils;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.model.GlideUrl;
import com.bumptech.glide.load.model.LazyHeaders;
import com.bumptech.glide.request.target.Target;
import com.kangle.meizipictures.R;

/**
 * Created by qian on 2017/12/15.
 * 自定义Glide
 */

public class MyGlide {

    public static void GlideDetail(Context context, String url, String headerUrl, ImageView imageView){
        LazyHeaders.Builder builder = new LazyHeaders.Builder();
        builder.addHeader("Accept","image/webp,image/apng,image/*,*/*;q=0.8");
        builder.addHeader("Accept-Encoding","gzip, deflate");
        builder.addHeader("Accept-Language","zh-CN,zh;q=0.9");
        builder.addHeader("Cache-Control","no-cache");
        builder.addHeader("Connection","keep-alive");
        builder.addHeader("Host","img.mmjpg.com");
        builder.addHeader("Pragma","no-cache");
        builder.addHeader("Referer",headerUrl);
        builder.addHeader("User-Agent","Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/62.0.3202.75 Safari/537.36");
        GlideUrl cookie1 = new GlideUrl(url, builder.build());
        boolean openCache = PreferenceManager.getInstance().getOpenCache();
        if (openCache){
            Glide.with(context)
                    .load(cookie1)
                    .skipMemoryCache(false)
                    .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                    .into(imageView);
        }else {
            Glide.with(context)
                    .load(cookie1)
                    .into(imageView);
        }

    }

    public static Bitmap GlideBitmap(Context context, String url, String headerUrl, ImageView imageView){
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
                return bitmap;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return bitmap;
    }

}
