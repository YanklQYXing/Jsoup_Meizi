package com.kangle.meizipictures.fragment;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.kangle.firstarticle.xutil.MyGson;
import com.kangle.meizipictures.R;
import com.kangle.meizipictures.activity.MeiziDetialActivity;
import com.kangle.meizipictures.adapter.SmallMeiziAdapter;
import com.kangle.meizipictures.base.BaseFragment;
import com.kangle.meizipictures.base.MyApplication;
import com.kangle.meizipictures.model.MeiZiModel;

import org.xutils.ex.DbException;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by Administrator on 2017/7/8.
 */

public class CollectFragment extends BaseFragment {

    @BindView(R.id.title_rl)
    RelativeLayout titleRl;
    @BindView(R.id.back_iv)
    ImageView backIv;
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.share_iv)
    ImageView shareIv;
    Unbinder unbinder;
    List<MeiZiModel> meiZiModels;
    @BindView(R.id.collect_ls)
    ListView collectLs;
    @BindView(R.id.empty)
    TextView empty;

    int type = 0; // 数据类型，0浏览记录，1 收藏

    public CollectFragment(){

    }

    @SuppressLint("ValidFragment")
    public CollectFragment(int type){
        this.type = type;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.collect_fragment_layout, container, false);
        unbinder = ButterKnife.bind(this, view);
        initView(view);
        return view;
    }

    private void initView(View view) {
        backIv.setVisibility(View.GONE);
        titleText(view, "收藏夹");
        LinearLayout title_in = (LinearLayout) view.findViewById(R.id.title_in);
        title_in.setVisibility(View.GONE);
        meiZiModels = new ArrayList<>();
        try {
            List<MeiZiModel> all = MyApplication.dbManager.findAll(MeiZiModel.class);
            if (all != null) {
                if(type==1){
                    for (int i = all.size()-1; i > 0; i--) {
                        if(all.get(i).getType()==1){
                            meiZiModels.add(all.get(i));
                        }
                    }
                }else if(type==0){
                    for (int i = all.size()-1; i > 0; i--) {
                        meiZiModels.add(all.get(i));
                    }
                }
            }
        } catch (DbException e) {
            e.printStackTrace();
        }

        SmallMeiziAdapter adapter = new SmallMeiziAdapter(meiZiModels, this);
        collectLs.setAdapter(adapter);
        collectLs.setEmptyView(empty);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.click_ll:
                int po = (int)v.getTag();
                MeiZiModel meiZiModel = meiZiModels.get(po);
                Intent intent = new Intent(getContext(), MeiziDetialActivity.class);
                intent.putExtra("meiZiModel", MyGson.gson.toJson(meiZiModel));

//                View transitionView = view.findViewById(R.id.ivNews);
                ActivityOptionsCompat options =
                        ActivityOptionsCompat.makeSceneTransitionAnimation(getActivity(),
                                v, getString(R.string.transition_news_img));

                ActivityCompat.startActivity(getActivity(), intent, options.toBundle());
                break;
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
