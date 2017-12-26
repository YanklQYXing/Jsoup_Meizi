package com.kangle.meizipictures.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.kangle.meizipictures.fragment.PindaoFragment;

import java.util.List;

/**
 * Created by Administrator on 2017/5/29.
 */

public class VideoFragmentAdapter extends FragmentPagerAdapter {
    List<Fragment> fragments;

    public VideoFragmentAdapter(FragmentManager fm, List<Fragment> fragments) {
        super(fm);
        this.fragments = fragments;
    }

    @Override
    public Fragment getItem(int position) {
        return fragments.get(position);
    }

    @Override
    public int getCount() {
        return fragments.size();
    }
}
