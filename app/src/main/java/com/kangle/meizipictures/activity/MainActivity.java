package com.kangle.meizipictures.activity;

import android.Manifest;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.kangle.firstarticle.utils.MyLog;
import com.kangle.firstarticle.utils.PreferenceManager;
import com.kangle.meizipictures.R;
import com.kangle.meizipictures.base.BaseActivity;
import com.kangle.meizipictures.fragment.CollectFragment;
import com.kangle.meizipictures.fragment.HomeFragment;
import com.kangle.meizipictures.fragment.UserCenterFragment;
import com.kangle.meizipictures.fragment.VideoFragment;
import com.kangle.meizipictures.fragment.VideoFragment02;
import com.kangle.meizipictures.fragment.VideoPindaoFragment;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends BaseActivity {

    @BindView(R.id.home_rl)
    RelativeLayout homeRl;
    @BindView(R.id.collect_rl)
    RelativeLayout collectRl;
    @BindView(R.id.user_center_rl)
    RelativeLayout user_center_rl;
    @BindView(R.id.bottom_ll)
    LinearLayout bottomLl;
    @BindView(R.id.content_f)
    FrameLayout contentF;
    private HomeFragment homeFragment;
    private VideoFragment02 videoFragment;
    private UserCenterFragment userCenterFragment;
    // 点击两次退出程序
    // 定义一个变量，来标识是否退出
    private static boolean isExit = false;

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            isExit = false;
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        initView();
        permission();
    }

    /**
     * 对于安卓6.0以上需要手动设置权限
     */
    private void permission() {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

            if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)
                    != PackageManager.PERMISSION_GRANTED
                    || ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    != PackageManager.PERMISSION_GRANTED) {
                // 没有权限，申请权限。
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 1);
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 2);
            } else {
                // 有权限然后保存付款码
//                saveCode();
            }
        } else {
//            saveCode();
        }


    }

    // 保存付款码
    private void saveCode() {
        BitmapDrawable drawable = (BitmapDrawable) getResources().getDrawable(R.drawable.fukuan_code);
        saveBitmap(drawable.getBitmap(), "fukuan_code.png", Bitmap.CompressFormat.PNG);
    }
    /**
     * 将Bitmap以指定格式保存到指定路径
     */
    public void saveBitmap(Bitmap bitmap, String name, Bitmap.CompressFormat format) {
        // 创建一个位于SD卡上的文件
        File file = new File(Environment.getExternalStorageDirectory(),name);
        FileOutputStream out = null;
        try{
            // 打开指定文件输出流
            out = new FileOutputStream(file);
            // 将位图输出到指定文件
            bitmap.compress(format, 100,
                    out);
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        String path = file.getPath();
        MyLog.log("付款吗保存路径"+path);
        PreferenceManager.getInstance().setFukuanCode(path);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode==KeyEvent.KEYCODE_BACK){
            exit();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    private void exit() {
        if (!isExit) {
            isExit = true;
            Toast.makeText(MainActivity.this, "再按一次退出", Toast.LENGTH_SHORT).show();
            mHandler.sendEmptyMessageDelayed(1, 2000);
        } else {
            finish();
        }
    }

    private void initView() {
        homeFragment = new HomeFragment();
        videoFragment = new VideoFragment02();
        userCenterFragment = new UserCenterFragment();
        getSupportFragmentManager().beginTransaction().replace(R.id.content_f,homeFragment).commit();
        homeRl.setOnClickListener(this);
        collectRl.setOnClickListener(this);
        user_center_rl.setOnClickListener(this);
        homeRl.setSelected(true);
        collectRl.setSelected(false);
        user_center_rl.setSelected(false);
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        if (v!=null){
            switch (v.getId()){
                case R.id.home_rl:
                    getSupportFragmentManager().beginTransaction().replace(R.id.content_f,homeFragment).commit();
                    homeRl.setSelected(true);
                    collectRl.setSelected(false);
                    user_center_rl.setSelected(false);
                    break;
                case R.id.collect_rl:
                    getSupportFragmentManager().beginTransaction().replace(R.id.content_f,videoFragment).commit();
                    homeRl.setSelected(false);
                    collectRl.setSelected(true);
                    user_center_rl.setSelected(false);
                    break;
                case R.id.user_center_rl:
                    getSupportFragmentManager().beginTransaction().replace(R.id.content_f,userCenterFragment).commit();
                    homeRl.setSelected(false);
                    collectRl.setSelected(false);
                    user_center_rl.setSelected(true);
                    break;
            }
        }
    }
}
