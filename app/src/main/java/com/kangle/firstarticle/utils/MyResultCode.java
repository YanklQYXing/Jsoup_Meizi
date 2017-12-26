package com.kangle.firstarticle.utils;

import android.content.Intent;
import android.widget.Toast;

//import com.kangle.firstarticle.activity.LoginActivity;
//import com.kangle.firstarticle.base.MyApplication;

/**
 * Created by Administrator on 2016/8/30.
 * 请求返回码封装
 */
public class MyResultCode {
    /*
    0：成功
    100：错误
    101：方法错误
    102：参数类型错误
    103：参数验证错误
    104：用户未登陆
    105：权限错误
    106：已存在
    107：不存在
    108：不匹配
    109：验证码错误
    110：执行超时
    111：编码错误
    112：服务端运行错误
    113：调用第三方服务错误
    114：操作太频繁
    115：用户已登录
     */
    public static boolean codeBack(int code) {

//        MyApplication instance = MyApplication.getInstance();

        String backString = "";
        switch (code) {
            case 0:
                backString = "成功";
                break;
            case 100:
                backString = "错误";
                break;
            case 101:
                backString = "方法错误";
                break;
            case 102:
                backString = "参数类型错误";
                break;
            case 103:
                backString = "参数验证错误";
                break;
            case 104:
                backString = "用户未登陆";
//                instance.startActivity(new Intent(instance, LoginActivity.class));
                break;
            case 105:
                backString = "权限错误";
                break;
            case 106:
                backString = "已存在";
                break;
            case 107:
                backString = "不存在";
                break;
            case 108:
                backString = "不匹配";
                break;
            case 109:
                backString = "验证码错误";
                break;
            case 110:
                backString = "执行超时";
                break;
            case 111:
                backString = "错误";
                break;
            case 112:
                backString = "服务端运行错误";
                break;
            case 113:
                backString = "调用第三方服务错误";
                break;
            case 114:
                backString = "操作太频繁";
                break;
            case 115:
                backString = "用户已登录";
                break;
            case 404:
                backString = "服务器错误";
                break;
        }

        if ("成功".equals(backString)){
            return true;
        }else {
//            Toast.makeText(instance, backString, Toast.LENGTH_SHORT).show();
            return false;
        }
    }
}
