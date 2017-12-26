package com.kangle.meizipictures.base;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.support.v4.app.Fragment;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.kangle.meizipictures.R;
import com.kangle.firstarticle.utils.PreferenceManager;

/**
 * Created by Administrator on 2016/10/27.
 */
public class BaseFragment extends Fragment implements View.OnClickListener {


    public int getNetworkType() {
        // 获得连接管理者
        ConnectivityManager connectivityManager = (ConnectivityManager) MyApplication.applicationContext.getSystemService(Context.CONNECTIVITY_SERVICE);
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
    public void onResume() {
        super.onResume();
    }

    /**
     * 设置标题
     *
     * @param s
     */
    public void titleText(View view, String s) {
        TextView title = (TextView) view.findViewById(R.id.title);
        title.setText(s);
        ImageView backIV = (ImageView) view.findViewById(R.id.back_iv);
        backIV.setOnClickListener(this);
    }

    private void bar01(RelativeLayout relativeLayout) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            getActivity().getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
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
            getActivity().getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            // // 透明导航栏
            // getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
//            StatusBarUtil.transparencyBar(getActivity());
//            int i1 = StatusBarUtil.StatusBarLightMode(getActivity());
//            StatusBarUtil.StatusBarLightMode(getActivity(),i1);
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

    @Override
    public void onClick(View v) {

    }
}
