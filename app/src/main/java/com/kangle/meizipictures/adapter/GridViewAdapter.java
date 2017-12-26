package com.kangle.meizipictures.adapter;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.model.GlideUrl;
import com.bumptech.glide.load.model.LazyHeaders;
import com.kangle.firstarticle.utils.MyGlide;
import com.kangle.firstarticle.utils.MyLog;
import com.kangle.meizipictures.R;
import com.kangle.meizipictures.model.MeiZiModel;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2017/7/9.
 */

public class GridViewAdapter extends BaseAdapter {

    List<String> meiZiModels;
    View.OnClickListener mOnClickListener;

    public GridViewAdapter(List<String> meiZiModels, View.OnClickListener mOnClickListener) {
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
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.image_g_layout, parent, false);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        }else {
            holder = (ViewHolder) convertView.getTag();
        }

        MyLog.log(meiZiModels.get(position));
//        Glide.with(parent.getContext())
//                .load(meiZiModels.get(position))
//                .into(holder.smallIv);
//        LazyHeaders.Builder builder = new LazyHeaders.Builder();
//        builder.addHeader("Referer","http://www.mmjpg.com/");
        String imgUrl = meiZiModels.get(position);
        int dian = imgUrl.lastIndexOf("."); // 截取到最后一个点
        int gang = imgUrl.lastIndexOf("/");
        String substring1 = imgUrl.substring(0, gang); // 截取 妹子号后剩余下的
        int gang2 = substring1.lastIndexOf("/");
        String substring2 = substring1.substring((gang2+1), gang); // 截取 妹子号后剩余下的
        MyGlide.GlideDetail(parent.getContext(),imgUrl,"http://www.mmjpg.com/mm/"+substring2,holder.smallIv);

        holder.image_ll.setTag(position);
        holder.image_ll.setOnClickListener(mOnClickListener);
        if (position%2==0){
            holder.left.setVisibility(View.GONE);
        }else {
            holder.left.setVisibility(View.VISIBLE);
        }

        return convertView;
    }

    static class ViewHolder {
        @BindView(R.id.item_iv)
        ImageView smallIv;
        @BindView(R.id.image_ll)
        LinearLayout image_ll;
        @BindView(R.id.left)
        TextView left;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
