package com.kangle.firstarticle.utils;


import com.kangle.meizipictures.netconfig.NetConfig;

/**
 * Created by Administrator on 2016/9/21.
 * 为以后上传的是图片而不是网址来获取图片地址
 * TODO
 */
public class CommentUtil {
    public static String imageUrl(String u) {
        String url = NetConfig.URL_SOURCE+u;
        return url;
    }
    public static String imageUrl01(String u) {
//        String url = NetConfig.URL_SOURCE+u;
//        String url = "http://n1.itc.cn/img8/wb/recom/2016/01/02/145170542604057733.JPEG";
        String url = "http://img1.3lian.com/2016/w1/51/88.jpg";
        return url;
    }
    public static String price(long u) {
//        String url = u/100+"元";
        String url = u + "元";
        return url;
    }
    public static String price01(long u) {
//        String url = u/100+"元";
        String url = "￥"+u;
        return url;
    }
}
