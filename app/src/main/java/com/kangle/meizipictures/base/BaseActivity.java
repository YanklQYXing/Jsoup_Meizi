package com.kangle.meizipictures.base;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.telephony.TelephonyManager;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.kangle.meizipictures.R;
import com.kangle.firstarticle.utils.PreferenceManager;

/**
 * Created by Administrator on 2016/10/27.
 */
public class BaseActivity extends AppCompatActivity implements View.OnClickListener {

    public TelephonyManager tm;
    private boolean isReduce = false;

   /* @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
    }*/

    /*@Override
    protected void onStart() {
        super.onStart();
        if (isReduce==true){
            RelativeLayout relativeLayout = (RelativeLayout) findViewById(R.id.title_rl);
            Bar(relativeLayout,1);
            isReduce = true;
        }
    }*/

    private void bar01(RelativeLayout relativeLayout) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }else {
            relativeLayout.setVisibility(View.GONE);
        }
    }

    public void Bar(RelativeLayout group , int i) {
        /**
         * 进行SDK判断
         */
        int b = i;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            // 透明状态栏
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            // // 透明导航栏
            // getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
//            StatusBarUtil.transparencyBar(this);
//            int i1 = StatusBarUtil.StatusBarLightMode(this);
//            StatusBarUtil.StatusBarLightMode(this,i1);
        } else {
            if(group==null){
                return;
            }
            // 判读不同的 父控件类型，进行不同的适配
            int reduce = Math.round(getResources().getDisplayMetrics().density * 28);
            if(i==1){
                RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) group.getLayoutParams();
                layoutParams.height = layoutParams.height - reduce;
                layoutParams.setMargins(0, 0, 0, 0);
            }else if(i==2){
                LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) group.getLayoutParams();
                layoutParams.height = layoutParams.height - reduce;
                layoutParams.setMargins(0, 0, 0, 0);
            }else if(i==3){
                RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) group.getLayoutParams();
                layoutParams.height = layoutParams.height - reduce;
                layoutParams.setMargins(0, 0, 0, 0);
            }
        }
    }
    /**
     * IMEI号
     */
    public String getImei() {
        tm = (TelephonyManager) getSystemService(TELEPHONY_SERVICE);
        try {
            if (tm != null) {
                String imei = tm.getDeviceId();
                return imei;
            }
        }catch (Exception e){
            return "123456789";
        }
        return null;
    }

    /**
     * 设置标题
     *
     * @param s
     */
    public void titleText(String s) {
        TextView title = (TextView) findViewById(R.id.title);
        title.setText(s);
        ImageView backIV = (ImageView) findViewById(R.id.back_iv);
        backIV.setOnClickListener(this);
        RelativeLayout relativeLayout = (RelativeLayout) findViewById(R.id.title_rl);
//        Bar(relativeLayout,1);
        bar01(relativeLayout);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    /**
     * 显示分享
     */
    public void showShare(){ 
        ImageView shareIv = (ImageView) findViewById(R.id.share_iv);
        shareIv.setVisibility(View.VISIBLE);
        shareIv.setOnClickListener(this);
    }
    
    /**
     * 获取网络信息
     *
     * @return
     */
    public int getNetworkType() {

        // 获得连接管理者
        ConnectivityManager connectivityManager = (ConnectivityManager) getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        // 通过连接管理者获取到网络连接状态的信息
        NetworkInfo info = connectivityManager.getActiveNetworkInfo();

        if (info == null) {
            return 0;
        } else if (info.getType() == ConnectivityManager.TYPE_WIFI) {
            return 1;
        } else if (info.getType() == ConnectivityManager.TYPE_MOBILE) {
            return 2;
        }
        return 0;
    }

    @Override
    public void onClick(View v) {
        if (v != null) {
            switch (v.getId()) {
                // 返回按钮
                case R.id.back_iv:
                    onBackPressed();
                    break;
                case R.id.share_iv: // TODO 分享按钮
                    Toast.makeText(getApplicationContext(), "分享按钮", Toast.LENGTH_SHORT).show();
                    break;
            }
        }
    }

    /**
     * 初始化 ScrollView
     */
//    public void initScrollView() {
//        final PullToRefreshScrollView scrollView = (PullToRefreshScrollView) findViewById(R.id.scroll_view);
//        scrollView.setMode(PullToRefreshBase.Mode.BOTH);
//        ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
////        params.height = 0;
//        params.width = 0;
//        LoadingLayout loadingLayout = scrollView.getmHeaderLayout();
//        ImageView imageView = loadingLayout.getmHeaderImage();
//        TextView textView = loadingLayout.getmHeaderText();
//        imageView.setVisibility(View.GONE);
//        textView.setVisibility(View.GONE);
//        LoadingLayout loadingLayout1 = scrollView.getmFooterLayout();
//        ImageView imageView1 = loadingLayout1.getmHeaderImage();
//        TextView textView1 = loadingLayout1.getmHeaderText();
//        imageView1.setVisibility(View.GONE);
//        textView1.setVisibility(View.GONE);
//        imageView.getLayoutParams().width = 0;
//        imageView1.getLayoutParams().width = 0;
//    }
    
}
