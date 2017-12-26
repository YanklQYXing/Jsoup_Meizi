package com.kangle.firstarticle.utils;

import android.app.Activity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/11/4.
 * 事件处理类
 * 
 */

public class MyEvent {
    
    private List<Class<? extends Activity>> classList;

    public MyEvent(List<Class<? extends Activity>> classList) {
        classList = new ArrayList<>();
    }
    
    public void setFinish(){
        for (int i = 0; i < classList.size(); i++) {
            Class<? extends Activity> aClass = classList.get(i);
        }
    }
    
}
