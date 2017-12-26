package com.kangle.meizipictures.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TableLayout;
import android.widget.TextView;

import com.kangle.meizipictures.R;
import com.kangle.meizipictures.adapter.MyFragmentAdapter;
import com.kangle.meizipictures.base.BaseFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by Administrator on 2017/7/8.
 */

public class HomeFragment extends BaseFragment {

    @BindView(R.id.title_rl)
    RelativeLayout titleRl;
    @BindView(R.id.home_tab_layout)
    TabLayout homeTabLayout;
    @BindView(R.id.home_vp)
    ViewPager homeVp;
    Unbinder unbinder;
    private String[] title = {"所有","排行榜","推荐","性感","小清新","刘飞儿","可儿","美胸","美臀","萌妹","ROSI","推女神","内衣","秀人网","尤果网","DISI","绮里嘉","第四印象"};
    private String[] url = {"home/","hot/","top/","tag/xinggan/","tag/xiaoqingxin/","tag/liufeier/","tag/barbie/","tag/meixiong/","tag/meitun/","tag/mengmei/","tag/rosi/","tag/tgod/","tag/neiyi/","tag/xiuren/","tag/ugirls/","tag/disi/","tag/qilijia/","tag/disi/"};

//    private String[] title = {"所有","性感妹子","日本妹子","台湾妹子","清纯妹子","刘飞儿","可儿","美胸","美臀","萌妹","ROSI","推女神","内衣","秀人网","尤果网","DISI","绮里嘉","第四印象"};
//    private String[] url = {"home/","xinggan/","japan/","taiwan/","mm/","tag/liufeier/","tag/barbie/","tag/meixiong/","tag/meitun/","tag/mengmei/","tag/rosi/","tag/tgod/","tag/neiyi/","tag/xiuren/","tag/ugirls/","tag/disi/","tag/qilijia/","tag/disi/"};

    private int current = 1;
    private List<PindaoFragment> fragments;
    private MyFragmentAdapter fragmentAdapter;

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
            PindaoFragment pindaoFragment = new PindaoFragment(url[i],i);
            fragments.add(pindaoFragment);
        }
        fragmentAdapter = new MyFragmentAdapter(getChildFragmentManager(),fragments);
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
            final PindaoFragment pindaoFragment = fragments.get(position);
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    pindaoFragment.init();
                }
            },200);
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
