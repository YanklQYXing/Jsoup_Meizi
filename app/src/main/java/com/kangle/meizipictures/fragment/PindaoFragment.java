package com.kangle.meizipictures.fragment;

import android.annotation.SuppressLint;
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
import com.kangle.firstarticle.xutil.XUtil;
import com.kangle.meizipictures.R;
import com.kangle.meizipictures.activity.MeiziDetialActivity;
import com.kangle.meizipictures.adapter.SmallMeiziAdapter;
import com.kangle.meizipictures.base.BaseFragment;
import com.kangle.meizipictures.model.MeiZiModel;
import com.kangle.meizipictures.netconfig.NetConfig;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.xutils.http.RequestParams;
import org.xutils.http.cookie.DbCookieStore;
import org.xutils.x;

import java.net.CookieManager;
import java.net.CookieStore;
import java.net.HttpCookie;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by Administrator on 2017/7/8.
 */

public class PindaoFragment extends BaseFragment {

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

    public PindaoFragment(){

    }

    @SuppressLint("ValidFragment")
    public PindaoFragment(String url, int i) {
        this.url = url;
        po = i;
    }

    List<MeiZiModel> meiZiModels;
    private boolean isVisible = false;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.pindao_fragment_layout, container, false);
        unbinder = ButterKnife.bind(this, view);
        initView(view);
        current = 1;
        if (po==0){
            init();
        }
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
    }

    public void init(){
        if (meiZiModels==null){
            meiZiModels = new ArrayList<>();
        }
        if (meiZiModels.size()==0){
            iniData();
        }
    };

    private void iniData() {
        String u = NetConfig.URL_ROOT;
        if (!TextUtils.isEmpty(url)){
            if (url.endsWith("top/")||url.endsWith("hot/")){
                u = NetConfig.URL_ROOT+url;
                listView.setMode(PullToRefreshBase.Mode.PULL_FROM_START);
            }else if (url.endsWith("home/")&&current==1){
                u = NetConfig.URL_ROOT;
                listView.setMode(PullToRefreshBase.Mode.BOTH);
            }else {
                listView.setMode(PullToRefreshBase.Mode.BOTH);
                u = NetConfig.URL_ROOT+url+current;
            }
        }
        MyLog.log("加载的网址----"+u);
        RequestParams params = new RequestParams(u);
        params.addHeader("User-Agent","Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1)");
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
                MyLog.log("报错 ： "+url + "   --- "+ex);
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
        Elements pic = doc.getElementsByClass("pic");
//        Element pins = doc.getElementById("pins");
        String s = pic.toString();
        Document doc1 = Jsoup.parse(s);
        Elements img = doc1.getElementsByTag("img");
        MyLog.log("pic ： " +img.size()+" ---- "+img);
        if ((img.size()!=0&&isRefresh)||meiZiModels.size()>=200){
            meiZiModels.clear();
            adapter.notifyDataSetChanged();
            isRefresh = false;
        }
        for (Element e : img) {
            String imgUrl = e.attr("src");
            MyLog.log("src : "+imgUrl);
            if (!TextUtils.isEmpty(imgUrl)) {
                String name = e.attr("alt");
                MeiZiModel meiZiModel = new MeiZiModel();
                meiZiModel.setPictureUrl(imgUrl);
                if (!TextUtils.isEmpty(name)) {
                    meiZiModel.setName(name);
                }
                // 截取地址
                if (imgUrl.length()>0){
                    int dian = imgUrl.lastIndexOf("."); // 截取到最后一个点
                    int gang = imgUrl.lastIndexOf("/");
                    String num = imgUrl.substring((gang+1), dian); // 妹子号
                    String substring1 = imgUrl.substring(0, gang); // 截取 妹子号后剩余下的
                    int gang2 = substring1.lastIndexOf("/");
                    String year = substring1.substring((gang2+1)); // 年份
//                    MyLog.log(imgUrl +" ==year== "+year+ " --num--" +num);
                    meiZiModel.setNum(num);
                    meiZiModel.setYare(year);
                    meiZiModels.add(meiZiModel);
                }
            }
        }
        adapter.notifyDataSetChanged();
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
