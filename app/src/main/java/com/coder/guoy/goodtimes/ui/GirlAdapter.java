package com.coder.guoy.goodtimes.ui;

import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.coder.guoy.goodtimes.R;
import com.coder.guoy.goodtimes.api.bean.GirlBean;
import com.coder.guoy.goodtimes.databinding.ItemMenuclassifyBinding;
import com.coder.guoy.goodtimes.utils.GlideUtils;

import java.util.List;

/**
 * @Version:
 * @Author:
 * @CreateTime:
 * @Descrpiton:
 */
public class GirlAdapter extends RecyclerView.Adapter {
    private List<GirlBean> mList;
    private LayoutInflater mInflater;
    private Context mContext;
    private ItemMenuclassifyBinding binding;

    public GirlAdapter(Context context, List list) {
        mInflater = LayoutInflater.from(context);
        mList = list;
        mContext = context;
    }

    // 获取条目数量
    @Override
    public int getItemCount() {
        return mList.size();
    }

    private class NormalViewHolder extends RecyclerView.ViewHolder {
        public ImageView imageView;
        public TextView textTitle;
        public CardView cardView;

        public NormalViewHolder(View itemView) {
            super(itemView);
            imageView = binding.itemImage;
            textTitle = binding.itemText;
            cardView = binding.cardview;
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        binding = DataBindingUtil.inflate(mInflater, R.layout.item_menuclassify, parent, false);
        return new NormalViewHolder(binding.getRoot());
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        final NormalViewHolder vh = (NormalViewHolder) holder;
        //设置图片
        final String imageUrl = mList.get(position).getImageUrl();
        if (imageUrl != null) {
            GlideUtils.setImage(imageUrl, vh.imageView);
        }
        //设置标题
        final String imageTitle = mList.get(position).getImgaeTitle();
        if (imageTitle != null) {
            vh.textTitle.setText(imageTitle);
        }
        //跳转至详细页
        vh.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, ImageDeatilActivity.class);
                Bundle options = ActivityOptions.makeSceneTransitionAnimation(
                        (Activity) mContext, vh.imageView, "shareimage").toBundle();
                intent.putExtra("imageUrl", imageUrl);
                intent.putExtra("imageTitle", imageTitle);
                mContext.startActivity(intent,options);
            }
        });
    }

}
