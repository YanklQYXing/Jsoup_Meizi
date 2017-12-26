package com.kangle.meizipictures.activity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.model.GlideUrl;
import com.bumptech.glide.load.model.LazyHeaders;
import com.kangle.firstarticle.utils.MyGlide;
import com.kangle.firstarticle.utils.MyLog;
import com.kangle.firstarticle.utils.PreferenceManager;
import com.kangle.firstarticle.xutil.MyCallBack;
import com.kangle.firstarticle.xutil.MyGson;
import com.kangle.firstarticle.xutil.XUtil;
import com.kangle.meizipictures.R;
import com.kangle.meizipictures.adapter.GridViewAdapter;
import com.kangle.meizipictures.base.BaseActivity;
import com.kangle.meizipictures.base.MyApplication;
import com.kangle.meizipictures.model.MeiZiModel;
import com.kangle.meizipictures.netconfig.NetConfig;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.xutils.ex.DbException;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MeiziDetialActivity extends BaseActivity {

    @BindView(R.id.ivImage)
    ImageView ivImage;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.collapsing_toolbar)
    CollapsingToolbarLayout collapsingToolbar;
    @BindView(R.id.grid_view)
    GridView gridView;
    @BindView(R.id.collect_btn)
    FloatingActionButton collect_btn;
    private MeiZiModel meiZiModel;
    private List<String> urls;
    private GridViewAdapter adapter;
    private int payType;
    private RelativeLayout relativeLayout;
    private Button shareBtn;
    private boolean isGoPay = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meizi_detial);
        ButterKnife.bind(this);
        initView();
        initDate();
    }

    private void initView() {
        int meiziCishu = PreferenceManager.getInstance().getMeiziCishu();
        MyLog.log("meiziCishu---"+meiziCishu);
        PreferenceManager.getInstance().setMeiziCishu(meiziCishu+1);
        Intent intent = getIntent();
        String meiZiModels = intent.getStringExtra("meiZiModel");
        if (meiZiModels != null) {
            meiZiModel = MyGson.gson.fromJson(meiZiModels, MeiZiModel.class);
        }
        initShowPay();
        if (meiZiModel!=null){
            try {
                List<MeiZiModel> all = MyApplication.dbManager.findAll(MeiZiModel.class);
                if (all != null) {
                    boolean isHave = false;
                    int type = 0;
                    for (int i = 0; i < all.size(); i++) {
                        MeiZiModel meiZiModel1 = all.get(i);
                        if (meiZiModel.getYare().endsWith(meiZiModel1.getYare()) && meiZiModel.getNum().endsWith(meiZiModel1.getNum())) {
                            type = meiZiModel1.getType();
                            MyApplication.dbManager.deleteById(MeiZiModel.class, meiZiModel1.getId());
                        }
                    }
                    if(!isHave){
                        MyApplication.dbManager.save(meiZiModel);
                    }else {
                        MyApplication.dbManager.deleteById(MeiZiModel.class, meiZiModel.getId());
                        meiZiModel.setType(type);
                        MyApplication.dbManager.save(meiZiModel);
                    }
                }
            } catch (DbException e) {
                e.printStackTrace();
            }
        }
        urls = new ArrayList<>();
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        CollapsingToolbarLayout collapsingToolbar = (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
        collapsingToolbar.setTitle(meiZiModel.getName());
        String urlStr = meiZiModel.getPictureUrl();
        URL url = null;
        try {
            url = new URL(urlStr);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        URI uri = null;
        try {
            uri = new URI(url.getProtocol(), url.getUserInfo(), url.getHost(), url.getPort(), url.getPath(), url.getQuery(), url.getRef());
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        try {
            url = uri.toURL();
            MyGlide.GlideDetail(this,url.toString(),"http://www.mmjpg.com/",ivImage);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        adapter = new GridViewAdapter(urls, this);
        gridView.setAdapter(adapter);
        collect_btn.setOnClickListener(this);
        try {
            List<MeiZiModel> all = MyApplication.dbManager.findAll(MeiZiModel.class);
            if (all != null) {
                for (int i = 0; i < all.size(); i++) {
                    MeiZiModel meiZiModel1 = all.get(i);
                    if (meiZiModel.getYare().endsWith(meiZiModel1.getYare()) && meiZiModel.getNum().endsWith(meiZiModel1.getNum())&&meiZiModel1.getType()==1) { // 数据库存在
                        collect_btn.setSelected(true);
                    }
                }
            }
        } catch (DbException e) {
            e.printStackTrace();
        }
    }

    // 展示付款的窗口
    private void initShowPay() {
        shareBtn = (Button) findViewById(R.id.share_btn);
        shareBtn.setOnClickListener(this);
        TextView one_t = (TextView) findViewById(R.id.one_t);
        TextView five_t = (TextView) findViewById(R.id.five_t);
        TextView ten_t = (TextView) findViewById(R.id.ten_t);
        TextView hundred_t = (TextView) findViewById(R.id.hundred_t);
        one_t.setOnClickListener(this);
        five_t.setOnClickListener(this);
        ten_t.setOnClickListener(this);
        hundred_t.setOnClickListener(this);
        relativeLayout = (RelativeLayout) findViewById(R.id.fukuan_rl);
        int meiziCishu = PreferenceManager.getInstance().getMeiziCishu();
        int sharedNum = PreferenceManager.getInstance().getSharedNum();
        MyLog.log("meiziCishu"+meiziCishu+"  sharedNum"+sharedNum);
        if (meiziCishu>=PreferenceManager.getInstance().getTypeNum()&&sharedNum<3){
            relativeLayout.setVisibility(View.VISIBLE);
        }else {
            relativeLayout.setVisibility(View.GONE);
        }
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                relativeLayout.setVisibility(View.GONE);
            }
        },3000);
    }

    private void initDate() {

        XUtil.Get((NetConfig.GET_LENGTH_GET + meiZiModel.getNum()), new MyCallBack<String>(this) {
            @Override
            public void onSuccess(String result) {
                super.onSuccess(result);
//                System.out.print("詳情 ： "+result);
                initHtml(result);
            }
        });
    }

    /**
     * 從html裡邊禍去總共有多少張圖片
     *
     * @param result
     */
    private void initHtml(String result) {
        Document parse = Jsoup.parse(result);
        Elements page = parse.getElementsByClass("page");
        Document parse1 = Jsoup.parse(page.toString());
        Elements a = parse1.getElementsByTag("a");
        int size = a.size();
        int max = 1;
        if (size > 2) {
            Element element = a.get(size - 2);
            try {
                max = Integer.parseInt(element.html());
                MyLog.log("element.html() : " + element.html() + "  max : " + max);
            } catch (Exception e) {
                max = 1;
            }
            MyLog.log("element.html() : " + element.html() + "  max : " + max);
        }
        for (int i = 0; i < max; i++) {
            urls.add(NetConfig.URL_SOURCE_BIG + meiZiModel.getYare() + "/" + meiZiModel.getNum() + "/" + (i + 1) + ".jpg");
        }
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.image_ll:
                int po = (int) v.getTag();
                Intent intent = new Intent(this, NewsDetailImageActivity.class);
                String[] strings = new String[urls.size()];
                for (int i = 0; i < urls.size(); i++) {
                    strings[i] = urls.get(i);
                }
                intent.putExtra("imageUrl", urls.get(po));
                Bundle b = new Bundle();
                b.putStringArray("imageUrls", strings);
                intent.putExtras(b);
                ActivityOptionsCompat options =
                        ActivityOptionsCompat.makeSceneTransitionAnimation(this,
                                v, getString(R.string.transition_news_img));
                ActivityCompat.startActivity(this, intent, options.toBundle());
                break;
            case R.id.collect_btn:
                boolean selected = collect_btn.isSelected();
                collect_btn.setSelected(!selected);
                try {
                    List<MeiZiModel> all = MyApplication.dbManager.findAll(MeiZiModel.class);
                    if (all != null) {
                        for (int i = 0; i < all.size(); i++) {
                            MeiZiModel meiZiModel1 = all.get(i);
                            if (meiZiModel.getYare().endsWith(meiZiModel1.getYare()) && meiZiModel.getNum().endsWith(meiZiModel1.getNum())) {
                                MyApplication.dbManager.deleteById(MeiZiModel.class, meiZiModel1.getId());
                            }
                        }
                    }
                    if (!selected) { // 收藏
                        meiZiModel.setType(1);
                        MyApplication.dbManager.save(meiZiModel);
                    }else {
                        meiZiModel.setType(0);
                        MyApplication.dbManager.save(meiZiModel);
                    }
                } catch (DbException e) {
                    e.printStackTrace();
                }
                break;
            case R.id.share_btn:
                Intent textIntent = new Intent(Intent.ACTION_SEND);
                textIntent.setType("text/plain");
                textIntent.putExtra(Intent.EXTRA_TEXT, "http://fir.im/pdh5");
                startActivity(Intent.createChooser(textIntent, getResources().getString(R.string.app_name)));
                int sharedNum = PreferenceManager.getInstance().getSharedNum();
                if((sharedNum+1)==3){ // 分享够了就设置分享次数为0，设置妹子次数为0
                    PreferenceManager.getInstance().setSharedNum(0);
                    PreferenceManager.getInstance().setMeiziCishu(0);

                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            relativeLayout.setVisibility(View.GONE);
                        }
                    },2000);
                }else {
                    PreferenceManager.getInstance().setSharedNum(PreferenceManager.getInstance().getSharedNum()+1);
                }

                shareBtn.setEnabled(false);
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        shareBtn.setEnabled(true);
                    }
                },2000);
                break;
            case R.id.one_t: // 1元
                payType = 1;
                permission();
                break;
            case R.id.five_t: // 5元
                payType = 5;
                permission();
                break;
            case R.id.ten_t: // 10
                payType = 10;
                permission();
                break;
            case R.id.hundred_t:// 100
                payType = 100;
                permission();
                break;
        }
    }

    private void toWeChatScan() {
        try {
            //利用Intent打开微信
            Uri uri = Uri.parse("weixin://scanqrcode");
//            Uri uri = Uri.parse("weixin://qr/Eb1xaaTEFflqreAK9_gD");
//            Uri uri = Uri.parse("weixin://wxp/f2f1Ytbgrx4IIN8Uwgdm8Y7hr3WsrrIUcspk");
            Intent intent = new Intent(Intent.ACTION_VIEW, uri);
            startActivity(intent);
        } catch (Exception e) {
            //若无法正常跳转，在此进行错误处理
            Toast.makeText(this, "无法跳转到，请检查您是否安装了微信！", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * 对于安卓6.0以上需要手动设置权限
     */
    private void permission() {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

            if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)
                    != PackageManager.PERMISSION_GRANTED
                    || ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    != PackageManager.PERMISSION_GRANTED) {
                // 没有权限，申请权限。
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 1);
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 2);
            } else {
                // 有权限然后保存付款码
                saveCode();
            }
        } else {
            saveCode();
        }

    }

    // 保存付款码  todo 付款成功后将账单里边的商品说明复制过去
    private void saveCode() {
        int fukuan_code = 0;
        switch (payType){
            case 1:
                fukuan_code = R.drawable.pay_1;
                break;
            case 5:
                fukuan_code = R.drawable.pay_5;
                break;
            case 10:
                fukuan_code = R.drawable.pay_10;
                break;
            case 100:
                fukuan_code = R.drawable.pay_100;
                break;
        }
        BitmapDrawable drawable = (BitmapDrawable) getResources().getDrawable(fukuan_code);
        saveBitmap(drawable.getBitmap(), "fukuan_code.png", Bitmap.CompressFormat.PNG);
    }
    /**
     * 将Bitmap以指定格式保存到指定路径
     */
    public void saveBitmap(Bitmap bitmap, String name, Bitmap.CompressFormat format) {
        // 创建一个位于SD卡上的文件
        File file = new File(Environment.getExternalStorageDirectory(),name);
        FileOutputStream out = null;
        try{
            // 打开指定文件输出流
            out = new FileOutputStream(file);
            // 将位图输出到指定文件
            bitmap.compress(format, 100, out);
            out.close();
            String path = file.getPath();
            PreferenceManager.getInstance().setFukuanCode(path);
            toAlipay();
        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(this, "失败了", Toast.LENGTH_SHORT).show();
        }
    }

    private void toAlipay() {
        try {
            isGoPay = true;
            String saomiao = "alipayqr://platformapi/startapp?saId=10000007";// 扫描
            Intent intent = Intent.parseUri(saomiao, Intent.URI_INTENT_SCHEME );
            startActivity(intent);
        } catch (Exception e) {
            Toast.makeText(this, "无法跳转到支付宝，请检查您是否安装了支付宝！", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onRestart() {
        super.onRestart();

        if (isGoPay){
            Intent intent = new Intent(this,ForeverActivity.class);
            startActivity(intent);
            isGoPay = false;
        }
    }
}
