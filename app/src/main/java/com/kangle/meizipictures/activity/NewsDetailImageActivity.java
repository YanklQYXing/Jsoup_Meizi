package com.kangle.meizipictures.activity;

import android.Manifest;
import android.app.Activity;
import android.app.WallpaperManager;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.model.GlideUrl;
import com.bumptech.glide.load.model.LazyHeaders;
import com.kangle.firstarticle.imageUtil.DownLoadImageService;
import com.kangle.firstarticle.imageUtil.ImageDownLoadCallBack;
import com.kangle.firstarticle.utils.MyGlide;
import com.kangle.meizipictures.R;
import com.kangle.meizipictures.adapter.ViewPagerAdapter;
import com.kangle.firstarticle.utils.MyLog;
import com.kangle.meizipictures.base.MyApplication;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import uk.co.senab.photoview.PhotoView;
import uk.co.senab.photoview.PhotoViewAttacher;

/**
 * 展示照片的activity
 */
public class NewsDetailImageActivity extends AppCompatActivity implements View.OnClickListener {

    @BindView(R.id.back_iv)
    ImageView backIv;
    @BindView(R.id.photo_view_pager)
    ViewPager photoViewPager;
    @BindView(R.id.current_t)
    TextView currentT;
    @BindView(R.id.total_t)
    TextView totalT;
    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what==0){
                Toast.makeText(NewsDetailImageActivity.this, "保存成功", Toast.LENGTH_SHORT).show();
            }else if (msg.what==1){
                Toast.makeText(NewsDetailImageActivity.this, "保存失败", Toast.LENGTH_SHORT).show();
            }else if (msg.what==2){
                Toast.makeText(NewsDetailImageActivity.this, "设置成功", Toast.LENGTH_SHORT).show();
            }else if (msg.what==3){
                Toast.makeText(NewsDetailImageActivity.this, "设置失败", Toast.LENGTH_SHORT).show();
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_detail_image);
        ButterKnife.bind(this);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        initView();
    }

    /**
     * 初始化view
     */
    private void initView() {
        Intent intent = getIntent();
        String imageUrl = intent.getStringExtra("imageUrl");
        Bundle extras = intent.getExtras();
        final String[] imageUrlses = extras.getStringArray("imageUrls");
        totalT.setText("/"+imageUrlses.length);
        int po = 0;
        List<View> views = new ArrayList<>();
        for (int i = 0; i < imageUrlses.length; i++) {
            View view = LayoutInflater.from(this).inflate(R.layout.item_photo_layout, null, false);
            PhotoView photoView = (PhotoView) view.findViewById(R.id.photo_view);
            Button save = (Button) view.findViewById(R.id.save);
            Button set_wallpaper = (Button) view.findViewById(R.id.set_wallpaper);
            final String imageUrlse = imageUrlses[i];
            save.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (ContextCompat.checkSelfPermission(NewsDetailImageActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE)
                            == PackageManager.PERMISSION_GRANTED
                            || ContextCompat.checkSelfPermission(NewsDetailImageActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                            == PackageManager.PERMISSION_GRANTED) {
                        DownLoadImageService downLoadImageService = new DownLoadImageService(NewsDetailImageActivity.this, imageUrlse, new ImageDownLoadCallBack() {
                            @Override
                            public void onDownLoadSuccess(File file) {
                            }

                            @Override
                            public void onDownLoadSuccess(Bitmap bitmap) {

                            }

                            @Override
                            public void onDownLoadSuccess(String filePath) {
                                handler.sendEmptyMessage(0);
                            }

                            @Override
                            public void onDownLoadFailed() {
                                handler.sendEmptyMessage(1);
                            }
                        });
                        new Thread(downLoadImageService).start();
                    }else {
                        Toast.makeText(NewsDetailImageActivity.this, "未开启权限，不能保存", Toast.LENGTH_SHORT).show();
                    }

                }
            });
            set_wallpaper.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (ContextCompat.checkSelfPermission(NewsDetailImageActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE)
                            == PackageManager.PERMISSION_GRANTED
                            || ContextCompat.checkSelfPermission(NewsDetailImageActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                            == PackageManager.PERMISSION_GRANTED) {
                        DownLoadImageService downLoadImageService = new DownLoadImageService(NewsDetailImageActivity.this, imageUrlse, new ImageDownLoadCallBack() {
                            @Override
                            public void onDownLoadSuccess(File file) {
                            }

                            @Override
                            public void onDownLoadSuccess(Bitmap bitmap) {

                                // 设置壁纸
                                WallpaperManager wallpaperManager = WallpaperManager.getInstance(getApplicationContext());
                                try {
                                    wallpaperManager.setBitmap(bitmap);
                                    handler.sendEmptyMessage(2);
                                } catch (IOException e) {
                                    e.printStackTrace();
                                    handler.sendEmptyMessage(3);
                                }
                            }

                            @Override
                            public void onDownLoadSuccess(String filePath) {
                            }

                            @Override
                            public void onDownLoadFailed() {
                                handler.sendEmptyMessage(3);
                            }
                        });
                        new Thread(downLoadImageService).start();
                    }else {
                        Toast.makeText(NewsDetailImageActivity.this, "未开启权限，不能保存", Toast.LENGTH_SHORT).show();
                    }

                }
            });
            String imgUrl = imageUrlses[i];
            int gang = imgUrl.lastIndexOf("/");
            String substring1 = imgUrl.substring(0, gang); // 截取 妹子号后剩余下的
            int gang2 = substring1.lastIndexOf("/");
            String substring2 = substring1.substring((gang2+1), gang); // 截取 妹子号后剩余下的
            MyApplication.wMeizi = substring2;
            MyGlide.GlideDetail(this,imgUrl,"http://www.mmjpg.com/mm/"+substring2,photoView);
            photoView.setOnViewTapListener(new PhotoViewAttacher.OnViewTapListener() {
                @Override
                public void onViewTap(View view, float x, float y) {
                    onBackPressed();
                }
            });
                MyLog.log("i : "+imageUrlses[i]);
                MyLog.log("imageUrl : "+imageUrl);
            if (imageUrlses[i].equals(imageUrl)) {
                MyLog.log("相等了 ---------");
                po = i;
            }
            views.add(view);
        }
        ViewPagerAdapter adapter = new ViewPagerAdapter(views);
        photoViewPager.setAdapter(adapter);
        photoViewPager.setCurrentItem(po);
        backIv.setOnClickListener(this);
        currentT.setText(""+(po+1));
        photoViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                currentT.setText(""+(position+1));
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    @Override
    public void onClick(View v) {
        if (v != null) {
            switch (v.getId()) {
                case R.id.back_iv:
                    onBackPressed();
                    break;
            }
        }
    }


}
