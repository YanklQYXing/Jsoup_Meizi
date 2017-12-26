package com.kangle.meizipictures.netconfig;

/**
 * Created by Administrator on 2016/10/27.
 * 接口地址
 */
public interface NetConfig {
    // 图片
    String URL_ROOT = "http://www.mmjpg.com/"; // GET 大图 包括 网站
//    String URL_ROOT = "http://www.mzitu.com/"; // GET 大图 包括 网站
    // 资源根
    String URL_SOURCE = "http://i.meizitu.net/thumbs/"; // 小图
    String URL_SOURCE_BIG = "http://img.mmjpg.com/"; // 大图
    // 獲得有多少個
    String GET_LENGTH_GET = URL_ROOT+"mm/";

    // 视频
    String VIDEO = "http://www.f8dy.tv"; // 视频网站

}
