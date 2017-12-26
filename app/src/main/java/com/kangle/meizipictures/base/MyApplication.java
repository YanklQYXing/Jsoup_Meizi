package com.kangle.meizipictures.base;

import android.app.Application;
import android.content.Context;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.model.GlideUrl;
//import com.kangle.firstarticle.dbModel.DBConfig;
//import com.kangle.firstarticle.https.OkHttpUrlLoader;
import com.kangle.firstarticle.utils.NewsHelper;

import org.xutils.DbManager;
import org.xutils.x;

import java.io.InputStream;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocketFactory;

//import cn.sharesdk.framework.ShareSDK;
//import okhttp3.OkHttpClient;

/**
 * Created by Administrator on 2017/4/30.
 * Application
 */

public class MyApplication extends Application {
    public static Context applicationContext;
    public static MyApplication instence;

    public static DbManager dbManager;
    public static DbManager.DaoConfig daoConfig;
    public static String wMeizi = "";

    @Override
    public void onCreate() {
        super.onCreate();

        applicationContext = this;
        instence = this;
        initDb();
        //让Glide能用HTTPS
//        Glide.get(this).register(GlideUrl.class, InputStream.class, new OkHttpUrlLoader.Factory(new OkHttpClient()));
        NewsHelper.getInstance().init(this);
//        JPushInterface.setDebugMode(true); 	// 设置开启日志,发布时请关闭日志
//        JPushInterface.init(this);     		// 初始化 JPush
//        //在使用SDK各组件之前初始化context信息，传入ApplicationContext
//        //注意该方法要再setContentView方法之前实现
//        SDKInitializer.initialize(this);
//        ShareSDK.initSDK(this);
    }

    private void initDb() {
        x.Ext.init(this); // xUtils初始化
        daoConfig = new DbManager.DaoConfig();
        // 设置是否可以使用事务
        daoConfig.setAllowTransaction(true);
        // 设置数据库的名字
        daoConfig.setDbName("liangmei.db");
        daoConfig.setDbVersion(1);
        daoConfig.setDbUpgradeListener(new DbManager.DbUpgradeListener() {
            @Override
            public void onUpgrade(DbManager db, int oldVersion, int newVersion) {

            }
        });
        // 数据库管理者
        dbManager = x.getDb(daoConfig);
    }

    /**
     * application对象的获取
     *
     * @return
     */
    public static MyApplication getInstance(){
        return instence;
    }


    /*public static OkHttpClient getOkHttpClient(InputStream... certificates)
    {
        SSLSocketFactory sslSocketFactory = HttpsUtils.getSslSocketFactory(certificates, null, null);
        OkHttpClient.Builder builder = new OkHttpClient().newBuilder();
        builder = builder.sslSocketFactory(sslSocketFactory);
        builder.hostnameVerifier(new HostnameVerifier() {

            @Override
            public boolean verify(String hostname, SSLSession session)
            {
                return true;
            }

        });
        return builder.build();
    }*/

}
