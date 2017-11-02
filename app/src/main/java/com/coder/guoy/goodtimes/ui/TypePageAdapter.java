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
import com.coder.guoy.goodtimes.api.bean.ImageBean;
import com.coder.guoy.goodtimes.databinding.ItemTypePageBinding;
import com.coder.guoy.goodtimes.utils.GlideUtils;

import java.util.List;

/**
 * @Version:V1.0
 * @Author:CoderGuoy
 * @CreateTime:2017年10月30日
 * @Descrpiton:
 */
public class TypePageAdapter extends RecyclerView.Adapter {
    private List<ImageBean> mList;
    private LayoutInflater mInflater;
    private Context mContext;
    private ItemTypePageBinding binding;
    private int count;

    public TypePageAdapter(Context context, List list, int count) {
        mInflater = LayoutInflater.from(context);
        mList = list;
        mContext = context;
        this.count = count;
    }

    // 获取条目数量
    @Override
    public int getItemCount() {
        return count;
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
        binding = DataBindingUtil.inflate(mInflater, R.layout.item_type_page, parent, false);
        return new NormalViewHolder(binding.getRoot());
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        final NormalViewHolder vh = (NormalViewHolder) holder;
        //设置图片
        if (mList.get(position).getImageUrl() != null) {
            GlideUtils.setImage(mList.get(position).getImageUrl(), vh.imageView);
        }
        //设置标题
        if (mList.get(position).getImgaeTitle() != null) {
            vh.textTitle.setText(mList.get(position).getImgaeTitle());
        }
        //跳转至详细页
        vh.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, ImageDeatilActivity.class);
                Bundle options = ActivityOptions.makeSceneTransitionAnimation(
                        (Activity) mContext, vh.imageView, "shareimage").toBundle();
                intent.putExtra("imageUrl", mList.get(position).getImageUrl());
                intent.putExtra("imageTitle", mList.get(position).getImgaeTitle());
                mContext.startActivity(intent,options);
            }
        });
    }

}
