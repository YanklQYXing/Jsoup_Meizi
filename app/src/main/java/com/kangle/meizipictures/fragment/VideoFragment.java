package com.kangle.meizipictures.fragment;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;

import com.kangle.firstarticle.utils.MyLog;
import com.kangle.meizipictures.Listener.PindaoListener;
import com.kangle.meizipictures.R;
import com.kangle.meizipictures.base.BaseFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;

/**
 * Created by qian on 2017/12/18.
 */

public class VideoFragment extends BaseFragment{

    private List<Button> buttons;
    private VideoPindaoFragment videoPindaoFragment;

    private PindaoListener pindaoListener = null;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.video_fragment_layout, container, false);
        initView(view);
        return view;
    }

    private void initView(View view) {
        RelativeLayout title_rl = (RelativeLayout) view.findViewById(R.id.title_rl);
        Bar(title_rl, 1);
        buttons = new ArrayList<>();
        Button tuijian = (Button) view.findViewById(R.id.tuijian);
        Button video_btn = (Button) view.findViewById(R.id.video_btn);
        Button dianshi = (Button) view.findViewById(R.id.dianshi);
        Button zongyi = (Button) view.findViewById(R.id.zongyi);
        Button dongman = (Button) view.findViewById(R.id.dongman);
        Button weidianying = (Button) view.findViewById(R.id.weidianying);
        tuijian.setOnClickListener(this);
        video_btn.setOnClickListener(this);
        dianshi.setOnClickListener(this);
        zongyi.setOnClickListener(this);
        dongman.setOnClickListener(this);
        weidianying.setOnClickListener(this);
        buttons.add(tuijian);
        buttons.add(video_btn);
        buttons.add(dianshi);
        buttons.add(zongyi);
        buttons.add(dongman);
        buttons.add(weidianying);
        tuijian.setSelected(true);

        videoPindaoFragment = new VideoPindaoFragment(0);
        getChildFragmentManager().beginTransaction().replace(R.id.video_frame_layout, videoPindaoFragment).commit();
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()){
            case R.id.tuijian:
                setSelectorBtn(0);
                break;
            case R.id.video_btn:
                setSelectorBtn(1);
                break;
            case R.id.dianshi:
                setSelectorBtn(2);
                break;
            case R.id.zongyi:
                setSelectorBtn(3);
                break;
            case R.id.dongman:
                setSelectorBtn(4);
                break;
            case R.id.weidianying:
                setSelectorBtn(5);
                break;
        }
    }

    // 选择的btn
    private void setSelectorBtn(int which) {
        for (int i = 0; i < buttons.size(); i++) {
            buttons.get(i).setSelected(i==which);
        }
        if(which!=1||which!=2){
            MyLog.log("==========="+which);
            getChildFragmentManager().beginTransaction().replace(R.id.video_frame_layout,new VideoPindaoFragment(which)).commit();
        }else {

        }
    }

}
