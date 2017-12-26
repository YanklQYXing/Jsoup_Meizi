package com.kangle.firstarticle.utils;

import android.content.Context;

/**
 * 帮助管理的类
 */
public class NewsHelper {
    public static NewsHelper instance = null;
    private Context appContext;

    public static synchronized NewsHelper getInstance() {
        if (instance == null) {
            instance = new NewsHelper();
        }
        return instance;
    }


    public void init(Context context){
        appContext = context;
        PreferenceManager.init(context);
    }
}
