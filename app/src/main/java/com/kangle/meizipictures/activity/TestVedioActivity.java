package com.kangle.meizipictures.activity;

import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.MediaController;
import android.widget.VideoView;

import com.kangle.meizipictures.R;

public class TestVedioActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_vedio);
        initView();
    }

    // 初始化view
    private void initView() {
        VideoView videoView = (VideoView) findViewById(R.id.video_view);
        Uri uri = Uri.parse("http://disp.titan.mgtv.com/vod.do?fmt=4&pno=2010&fid=23F6828F9E298145F4313D357A0D6B60&file=%2Fc1%2F2017%2F12%2F15_0%2F23F6828F9E298145F4313D357A0D6B60_20171215_1_1_814.mp4");
        videoView.setVideoURI(uri);
        videoView.setMediaController(new MediaController(this));
        //开始播放视频
        videoView.start();
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle presses on the action bar items
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
