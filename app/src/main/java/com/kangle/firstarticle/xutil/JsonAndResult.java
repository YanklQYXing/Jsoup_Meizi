package com.kangle.firstarticle.xutil;


/**
 * Created by Administrator on 2016/8/31.
 */
public class JsonAndResult {
    public static Object parse(String result, Class<?> resultClass){
        Object o = MyGson.gson.fromJson(result, resultClass);
        return o;
    }
}
