package com.kangle.meizipictures.adapter;

import android.graphics.Bitmap;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.model.GlideUrl;
import com.bumptech.glide.load.model.LazyHeaders;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.kangle.firstarticle.utils.MyGlide;
import com.kangle.firstarticle.utils.MyLog;
import com.kangle.firstarticle.xutil.XUtil;
import com.kangle.meizipictures.R;
import com.kangle.meizipictures.model.MeiZiModel;

import org.xutils.x;

import java.net.HttpCookie;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import jp.wasabeef.glide.transformations.BlurTransformation;

/**
 * Created by Administrator on 2017/7/9.
 */

public class SmallMeiziAdapter extends BaseAdapter {

    List<MeiZiModel> meiZiModels;
    View.OnClickListener mOnClickListener;

    public SmallMeiziAdapter(List<MeiZiModel> meiZiModels, View.OnClickListener mOnClickListener) {
        this.meiZiModels = meiZiModels;
        this.mOnClickListener = mOnClickListener;
    }

    @Override
    public int getCount() {
        return meiZiModels.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder holder;
        if (convertView == null) {
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.simple_item_layout, parent, false);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        }else {
            holder = (ViewHolder) convertView.getTag();
        }

        MyLog.log(meiZiModels.get(position).getPictureUrl());
        LazyHeaders.Builder builder = new LazyHeaders.Builder();
        builder.addHeader("Accept","image/webp,image/apng,image/*,*/*;q=0.8");
        builder.addHeader("Accept-Encoding","gzip, deflate");
        builder.addHeader("Accept-Language","zh-CN,zh;q=0.9");
        builder.addHeader("Cache-Control","no-cache");
        builder.addHeader("Connection","keep-alive");
        builder.addHeader("Host","img.mmjpg.com");
        builder.addHeader("Pragma","no-cache");
        builder.addHeader("Referer","http://www.mmjpg.com/");
        builder.addHeader("User-Agent","Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/62.0.3202.75 Safari/537.36");
        GlideUrl cookie1 = new GlideUrl(meiZiModels.get(position).getPictureUrl(), builder.build());
        Glide.with(parent.getContext())
                .load(cookie1)
                .skipMemoryCache(false)
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .bitmapTransform(new BlurTransformation(parent.getContext(), 25), new CenterCrop(parent.getContext()))
                .into(holder.smallIv);
        MyGlide.GlideDetail(parent.getContext(), meiZiModels.get(position).getPictureUrl().toString(), "http://www.mmjpg.com/", holder.smallIv1);
        if (!TextUtils.isEmpty(meiZiModels.get(position).getName())){
            holder.name.setText(meiZiModels.get(position).getName());
        }else {
            holder.name.setText("妹子");
        }

        holder.click_ll.setTag(position);
        holder.click_ll.setOnClickListener(mOnClickListener);

        return convertView;
    }

    static class ViewHolder {
        @BindView(R.id.small_iv)
        ImageView smallIv;
        @BindView(R.id.small_iv1)
        ImageView smallIv1;
        @BindView(R.id.name)
        TextView name;
        @BindView(R.id.click_ll)
        LinearLayout click_ll;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
