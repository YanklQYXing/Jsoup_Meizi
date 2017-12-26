package com.kangle.meizipictures.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.kangle.firstarticle.utils.GetFileSizeUtil;
import com.kangle.firstarticle.utils.MyLog;
import com.kangle.firstarticle.utils.PreferenceManager;
import com.kangle.meizipictures.R;
import com.kangle.meizipictures.activity.AboutActivity;
import com.kangle.meizipictures.activity.ForeverActivity;
import com.kangle.meizipictures.activity.HistoryActivity;
import com.kangle.meizipictures.activity.TimeActivity;
import com.kangle.meizipictures.base.BaseFragment;

import java.io.File;

/**
 * Created by qian on 2017/12/15.
 */

public class UserCenterFragment extends BaseFragment {

    private RelativeLayout historyRl;
    private RelativeLayout foreverRl;
    private RelativeLayout aboutRl;
    private Switch openCache;
    private TextView cache_tv;
    private RelativeLayout clear_rl;
    private String howGarbge = "0kb";
    private ImageView backIv;
    private RelativeLayout collect_rl;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.user_center_fragment_layout,container,false);
        initView(view);
        return view;
    }

    private void initView(View view) {
        backIv = (ImageView) view.findViewById(R.id.back_iv);
        backIv.setVisibility(View.GONE);
        titleText(view, "设置");
        historyRl = (RelativeLayout) view.findViewById(R.id.history_rl);
        collect_rl = (RelativeLayout) view.findViewById(R.id.collect_rl);
        foreverRl = (RelativeLayout) view.findViewById(R.id.forever_rl);
        aboutRl = (RelativeLayout) view.findViewById(R.id.about_rl);
        clear_rl = (RelativeLayout) view.findViewById(R.id.clear_rl);
        openCache = (Switch) view.findViewById(R.id.open_cache_switch);
        cache_tv = (TextView) view.findViewById(R.id.cache_tv);
        collect_rl.setOnClickListener(this);
        historyRl.setOnClickListener(this);
        clear_rl.setOnClickListener(this);
        foreverRl.setOnClickListener(this);
        aboutRl.setOnClickListener(this);
        openCache.setOnClickListener(this);
        boolean isOpenCache = PreferenceManager.getInstance().getOpenCache();
        openCache.setChecked(isOpenCache);

        aboutRl.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                startActivity(new Intent(getContext(), TimeActivity.class));
                return false;
            }
        });

        cache_tv.setText("");
//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                try {
//                    howGarbge = GetFileSizeUtil.getAutoFileOrFilesSize(getActivity().getCacheDir().getPath());
//                    new Handler().post(new Runnable() {
//                        @Override
//                        public void run() {
//                            cache_tv.setText("" + howGarbge);
//                        }
//                    });
//                }catch (Exception e){
//                    MyLog.log(e.toString());
//                }
//
//            }
//        }).start();
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()){
            case R.id.collect_rl:
                Intent intentC = new Intent(getActivity(), HistoryActivity.class);
                intentC.putExtra("type",1);
                startActivity(intentC);
                break;
            case R.id.history_rl:
                Intent intentH = new Intent(getActivity(), HistoryActivity.class);
                intentH.putExtra("type",0);
                startActivity(intentH);
                break;
            case R.id.forever_rl:
                startActivity(new Intent(getContext(), ForeverActivity.class));
                break;
            case R.id.about_rl:
                startActivity(new Intent(getContext(), AboutActivity.class));
                break;
            case R.id.clear_rl:
                try {
                    int cache = clearCacheFolder(getActivity().getCacheDir(), System.currentTimeMillis());
                    Toast.makeText(getActivity(), "清理成功", Toast.LENGTH_SHORT).show();
//                    howGarbge = GetFileSizeUtil.getAutoFileOrFilesSize(getActivity().getCacheDir().getPath());
//                    cache_tv.setText("" + howGarbge);
                }catch (Exception e){
                    MyLog.log(e.toString());
                    Toast.makeText(getActivity(), "清理成功", Toast.LENGTH_SHORT).show();
//                    cache_tv.setText("0kb");
                }
                break;
            case R.id.open_cache_switch:
                boolean isOpenCache = PreferenceManager.getInstance().getOpenCache();
                openCache.setChecked(!isOpenCache);
                PreferenceManager.getInstance().setOpenCache(!isOpenCache);
                break;
        }
    }

    /**
     * 清理缓存
     *
     * @param dir
     * @param numDays
     * @return
     */
    private int clearCacheFolder(File dir, long numDays) {
        int deletedFiles = 0;
        if (dir != null && dir.isDirectory()) {
            try {
                for (File child : dir.listFiles()) {
                    if (child.isDirectory()) {
                        deletedFiles += clearCacheFolder(child, numDays);
                    }
                    if (child.lastModified() < numDays) {
                        if (child.delete()) {
                            deletedFiles++;
                        }
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return deletedFiles;
    }

}
