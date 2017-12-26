package com.kangle.firstarticle.xutil;

import android.content.Context;
import android.util.Log;

import org.xutils.common.Callback;

/**
 * Created by Administrator on 2016/8/31.
 */
public class MyCallBack<ResultType> implements Callback.CommonCallback<ResultType> {

    Context context;

    public MyCallBack(Context context) {
        this.context = context;
    }

    @Override
    public void onSuccess(ResultType result) {
        //可以根据公司的需求进行统一的请求成功的逻辑处理
    }

    @Override
    public void onError(Throwable ex, boolean isOnCallback) {
        //可以根据公司的需求进行统一的请求网络失败的逻辑处理
        Log.d("lele", "XUtils 请求发出的异常 ：" + ex);
        // TODO: 2016/8/31 错误码设置
//        if (ex instanceof HttpException) { // 网络错误
//            HttpException httpEx = (HttpException) ex;
//            int responseCode = httpEx.getCode();
//            String responseMsg = httpEx.getMessage();
//            String errorResult = httpEx.getResult();
//        } else { // 其他错误
//        }
        /*LayoutInflater layout = LayoutInflater.from(context);
        View view1 = layout.inflate(R.layout.dialog_loading_err, null);
        final Dialog dialogClare = new Dialog(context, R.style.MyDialogStyleTop);
//        dialogClare.show(); // // TODO: 2016/10/10
        WindowManager.LayoutParams lp = dialogClare.getWindow().getAttributes();
        lp.alpha = 0.7f;
        dialogClare.getWindow().setAttributes(lp);
        dialogClare.getWindow().setContentView(view1);
        Button cancel = (Button) view1.findViewById(R.id.cancel_btn);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogClare.dismiss();
            }
        });*/
    }

    @Override
    public void onCancelled(CancelledException cex) {

    }

    @Override
    public void onFinished() {

    }


}

