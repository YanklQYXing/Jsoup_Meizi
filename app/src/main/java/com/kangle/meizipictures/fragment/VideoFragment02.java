package com.kangle.meizipictures.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.kangle.meizipictures.R;
import com.kangle.meizipictures.adapter.MyFragmentAdapter;
import com.kangle.meizipictures.adapter.VideoFragmentAdapter;
import com.kangle.meizipictures.base.BaseFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by Administrator on 2017/7/8.
 */

public class VideoFragment02 extends BaseFragment {

    @BindView(R.id.title_rl)
    RelativeLayout titleRl;
    @BindView(R.id.home_tab_layout)
    TabLayout homeTabLayout;
    @BindView(R.id.home_vp)
    ViewPager homeVp;
    Unbinder unbinder;
    private String[] title = {"推荐","电影","电视剧","综艺","动漫","微电影"};
    private int current = 1;
    private List<Fragment> fragments;
    private VideoFragmentAdapter fragmentAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.heme_fragment_layout, container, false);
        unbinder = ButterKnife.bind(this, view);
        initView(view);
        return view;
    }

    private void initView(View view) {
        Bar(titleRl,1);
        fragments = new ArrayList<>();
        for (int i = 0; i < title.length; i++) {
            TabLayout.Tab tab = homeTabLayout.newTab();
            View view1 = LayoutInflater.from(getContext()).inflate(R.layout.tab_text,null);
            tab.setCustomView(view1);
            TextView textView = (TextView) view1.findViewById(R.id.tab_tv);
            textView.setText(title[i]);
            homeTabLayout.addTab(tab);
            if (i==0){
                VideoTuijianFragment videoTuijianFragment = new VideoTuijianFragment(0);
                fragments.add(videoTuijianFragment);
            }else {
                VideoPindaoFragment videoPindaoFragment = new VideoPindaoFragment(i);
                fragments.add(videoPindaoFragment);
            }
        }
        fragmentAdapter = new VideoFragmentAdapter(getChildFragmentManager(),fragments);
        homeVp.setAdapter(fragmentAdapter);
        homeTabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);
        homeTabLayout.setSelectedTabIndicatorHeight(1);
        homeTabLayout.addOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(homeVp));
        homeVp.addOnPageChangeListener(new MyListener(homeTabLayout));
        homeVp.setOffscreenPageLimit(0);
    }

    class MyListener extends TabLayout.TabLayoutOnPageChangeListener{

        public MyListener(TabLayout tabLayout) {
            super(tabLayout);
        }

        @Override
        public void onPageSelected(int position) {
            super.onPageSelected(position);
//            final Fragment pindaoFragment = fragments.get(position);
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
