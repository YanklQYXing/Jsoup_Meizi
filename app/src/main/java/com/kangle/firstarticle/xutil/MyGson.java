package com.kangle.firstarticle.xutil;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.kangle.firstarticle.utils.Comment;

/**
 * Created by Administrator on 2016/8/30.
 */
public interface MyGson {
    Gson gson = new GsonBuilder()
            .setPrettyPrinting()
            .disableHtmlEscaping()
            .setDateFormat(Comment.DATA_TIME_MODEL)
            .create();
}
