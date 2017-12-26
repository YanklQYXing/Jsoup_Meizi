package com.kangle.meizipictures.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.kangle.firstarticle.refreshView.PullToRefreshBase;
import com.kangle.firstarticle.refreshView.PullToRefreshListView;
import com.kangle.firstarticle.utils.MyLog;
import com.kangle.firstarticle.xutil.MyCallBack;
import com.kangle.firstarticle.xutil.MyGson;
import com.kangle.meizipictures.R;
import com.kangle.meizipictures.activity.MeiziDetialActivity;
import com.kangle.meizipictures.adapter.SmallMeiziAdapter;
import com.kangle.meizipictures.base.BaseFragment;
import com.kangle.meizipictures.model.MeiZiModel;
import com.kangle.meizipictures.model.VideoModel;
import com.kangle.meizipictures.netconfig.NetConfig;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by Administrator on 2017/7/8.
 */

public class VideoTuijianFragment extends BaseFragment {

    @BindView(R.id.list_view)
    PullToRefreshListView listView;
    @BindView(R.id.empty)
    TextView empty;
    Unbinder unbinder;
    private String url = "";

    private int current = 1;
    private SmallMeiziAdapter adapter;
    private int po = 0;
    private boolean isRefresh = false;

    private String[] urls = {"http://www.f8dy.tv",};
    private Elements img;

    public VideoTuijianFragment(int i){
        po = i;
    }


    List<MeiZiModel> meiZiModels;
    private boolean isVisible = false;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.pindao_fragment_layout, container, false);
        unbinder = ButterKnife.bind(this, view);
        MyLog.log("onCreateView");
        initView(view);
        return view;
    }


    private void initView(View view) {
        meiZiModels = new ArrayList();
        adapter = new SmallMeiziAdapter(meiZiModels,this);
        listView.setAdapter(adapter);
        listView.setEmptyView(empty);
        listView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
                isRefresh = true;
                current = 1;
                iniData();
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
                iniData();
            }
        });
        init();
//        iniData();
    }

    public void init(){
        MyLog.log("频道"+po);
        if (meiZiModels==null){
            meiZiModels = new ArrayList<>();
        }
        if (meiZiModels.size()==0){
            iniData();
        }
    };

    private void iniData() {
        String u = NetConfig.VIDEO;
        RequestParams params = new RequestParams(u);
        params.addHeader("User-Agent","Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/62.0.3202.75 Safari/537.36");
        params.addHeader("Referer","http://i.meizitu.net");
        x.http().get(params,new MyCallBack<String>(getContext()){
            @Override
            public void onSuccess(String result) {
                super.onSuccess(result);
                MyLog.log("成功 ： "+result);
                initHtml(result);
                current ++;
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                super.onError(ex, isOnCallback);
                MyLog.log("报错 ： "+ "   --- "+ex);
            }

            @Override
            public void onFinished() {
                super.onFinished();
                listView.onRefreshComplete();
            }
        });
    }

    /**
     * 处理获取的html数据
     */
    private void initHtml(String html) {
        Document doc = Jsoup.parse(html);
        Elements pic = doc.getElementsByClass("channel-silder-nav");
        String s = pic.toString();
        Document doc1 = Jsoup.parse(s);
        Elements lis = doc1.getElementsByTag("li"); // 轮播图的数量

        for (Element e : lis) {
            Elements a = e.getElementsByTag("a");
            Elements img = e.getElementsByTag("img");
            if (a.size()!=0&&img.size()!=0){
                Element elementImg = img.get(0);
                String imgUrl = elementImg.attr("data-original");
                String name = elementImg.attr("alt");
                String href = NetConfig.VIDEO+a.get(0).attr("href");
                MyLog.log("imgUrl---"+imgUrl+"  name---"+name+"  href---"+href);
                VideoModel videoModel = new VideoModel();
                videoModel.setName(name);
                videoModel.setImgUrl(imgUrl);
                videoModel.setPath(href);
            }
        }
//        adapter.notifyDataSetChanged();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.click_ll:
                int po = (int)v.getTag();
                MeiZiModel meiZiModel = meiZiModels.get(po);
                Intent intent = new Intent(getContext(), MeiziDetialActivity.class);
                intent.putExtra("meiZiModel", MyGson.gson.toJson(meiZiModel));

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
