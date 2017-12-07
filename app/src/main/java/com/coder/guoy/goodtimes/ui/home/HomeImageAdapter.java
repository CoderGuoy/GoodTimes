package com.coder.guoy.goodtimes.ui.home;

import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.coder.guoy.goodtimes.Constants;
import com.coder.guoy.goodtimes.R;
import com.coder.guoy.goodtimes.api.bean.ImageBean;
import com.coder.guoy.goodtimes.databinding.ItemHomeBinding;
import com.coder.guoy.goodtimes.databinding.ItemHomefooterBinding;
import com.coder.guoy.goodtimes.ui.ImageDeatilActivity;
import com.coder.guoy.goodtimes.utils.GlideUtils;

import java.util.List;

/**
 * @Version:V1.0
 * @Author:CoderGuoy
 * @CreateTime:2017年11月10日
 * @Descrpiton:首页图片
 */
public class HomeImageAdapter extends RecyclerView.Adapter {
    private List<ImageBean> mList;
    private LayoutInflater mInflater;
    private Context mContext;
    private ItemHomeBinding binding;
    private ItemHomefooterBinding footBinding;
    private int normalType = 0;     // 第一种ViewType，正常的item
    private int footType = 1;       // 第二种ViewType，底部的提示View
    private int color = Color.rgb(229, 67, 124);//默认Logo色

    public HomeImageAdapter(Context context) {
        mInflater = LayoutInflater.from(context);
        mContext = context;
    }

    // 获取条目数量
    @Override
    public int getItemCount() {
        return mList == null ? 0 : mList.size() + 1;
    }

    @Override
    public int getItemViewType(int position) {
        if (position == getItemCount() - 1) {
            return footType;
        } else {
            return normalType;
        }
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
        RecyclerView.LayoutManager layoutManager = recyclerView.getLayoutManager();
        if (layoutManager instanceof GridLayoutManager) {
            final GridLayoutManager manager = (GridLayoutManager) layoutManager;
            manager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
                @Override
                public int getSpanSize(int position) {
                    // TODO: 2017/11/14 return的返回值是RecyclerView列数的比重
                    /**
                     * 动态设置adapter中每个条目所占列数的比重
                     * 假设 GridLayoutManager 设置为4(SpanCount)列
                     * return 1 为只占4分之一，默认所占的列数
                     * return 4 (manager.getSpanCount()),为占一整行
                     */
                    return (position == getItemCount() - 1) ? manager.getSpanCount() : 1;
                }
            });
        }
    }

    public void setNewData(List data) {
        mList = data;
        notifyDataSetChanged();
    }

    public void setColor(int color){
        this.color = color;
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

    // TODO: 底部footView的ViewHolder
    private class FootViewHolder extends RecyclerView.ViewHolder {
        public LinearLayout footLayout;

        public FootViewHolder(View itemView) {
            super(itemView);
            footLayout = footBinding.layoutFoot;
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == normalType) {
            binding = DataBindingUtil.inflate(mInflater, R.layout.item_home, parent, false);
            return new NormalViewHolder(binding.getRoot());
        } else {
            footBinding = DataBindingUtil.inflate(mInflater, R.layout.item_homefooter, parent, false);
            return new FootViewHolder(footBinding.getRoot());
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        if (holder instanceof NormalViewHolder) {
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
                    intent.putExtra("linkUrl",mList.get(position).getLinkUrl());
                    intent.putExtra("imageUrl", mList.get(position).getImageUrl());
                    intent.putExtra("imageTitle", mList.get(position).getImgaeTitle());
                    mContext.startActivity(intent, options);
                }
            });
        } else {
            FootViewHolder fvh = (FootViewHolder) holder;
            fvh.footLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(mContext, MeinvAcitvity.class);
                    intent.putExtra("baseUrl", Constants.MM_URL);
                    intent.putExtra("url", Constants.NEW);
                    intent.putExtra("title", "最新美图");
                    intent.putExtra("color", color);
                    mContext.startActivity(intent);
                }
            });
        }
    }

}
