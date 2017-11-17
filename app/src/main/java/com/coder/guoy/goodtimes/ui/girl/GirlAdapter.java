package com.coder.guoy.goodtimes.ui.girl;

import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.graphics.drawable.Animatable;
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

import com.coder.guoy.goodtimes.R;
import com.coder.guoy.goodtimes.api.bean.ImageBean;
import com.coder.guoy.goodtimes.databinding.ItemFooterBinding;
import com.coder.guoy.goodtimes.databinding.ItemGirlBinding;
import com.coder.guoy.goodtimes.ui.ImageDeatilActivity;
import com.coder.guoy.goodtimes.utils.GlideUtils;
import com.coder.guoy.goodtimes.utils.ToastUtil;

import java.util.List;

/**
 * @Version:V1.0
 * @Author:CoderGuoy
 * @CreateTime:2017年10月30日
 * @Descrpiton:
 */
public class GirlAdapter extends RecyclerView.Adapter {
    private List<ImageBean> mList;
    private LayoutInflater mInflater;
    private Context mContext;
    private ItemGirlBinding binding;
    private ItemFooterBinding footBinding;
    private Animatable animationDrawable;
    private int normalType = 0;     // 第一种ViewType，正常的item
    private int footType = 1;       // 第二种ViewType，底部的提示View

    private boolean hasMore = true;   // 变量，是否有更多数据
    private boolean fadeTips = false; // 变量，是否隐藏了底部的提示

    public GirlAdapter(Context context) {
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

    public void addData(List data, boolean hasMore) {
        if (data != null) {
            mList.addAll(data);
        }
        this.hasMore = hasMore;
        notifyDataSetChanged();
    }

    /**
     * 暴露接口，改变fadeTips的方法
     */
    public boolean isFadeTips() {
        return fadeTips;
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
        public ImageView image;

        public FootViewHolder(View itemView) {
            super(itemView);
            footLayout = footBinding.layoutFoot;
            image = footBinding.imgProgress;
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == normalType) {
            binding = DataBindingUtil.inflate(mInflater, R.layout.item_girl, parent, false);
            return new NormalViewHolder(binding.getRoot());
        } else {
            footBinding = DataBindingUtil.inflate(mInflater, R.layout.item_footer, parent, false);
            return new FootViewHolder(footBinding.getRoot());
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        if ((holder instanceof NormalViewHolder)) {
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
                    mContext.startActivity(intent, options);
                }
            });
        } else {
            final FootViewHolder vh = (FootViewHolder) holder;
            // 只有获取数据为空时，hasMore为false，所以当我们拉到底部时基本都会首先显示“正在加载更多...”
            if (hasMore == true) {
                // 不隐藏footView提示
                fadeTips = false;
                if (mList.size() > 0) {
                    // 如果查询数据发现增加之后，就显示正在加载更多
                    //显示加载动画
                    vh.image.setVisibility(View.VISIBLE);
                    animationDrawable = (Animatable) vh.image.getDrawable();
                    animationDrawable.start();
                }
                //数据不足以显示整个页面
                if (mList.size() < 8 & mList.size() > 0) {
                    //显示没有更多数据了
                    vh.footLayout.setVisibility(View.VISIBLE);
                    ToastUtil.show(mContext.getString(R.string.NO_MORE));
                    //隐藏加载动画
                    vh.image.setVisibility(View.GONE);
                }
            } else {
                if (mList.size() > 0) {//请求数据为空（非首次）
                    // 如果查询数据发现并没有增加时，就显示没有更多数据了
                    ToastUtil.show(mContext.getString(R.string.NO_MORE));
                    //隐藏加载动画
                    vh.image.setVisibility(View.GONE);
                    // 将fadeTips设置true
                    fadeTips = true;
                    // hasMore设为true是为了让再次拉到底时，会先显示正在加载更多
                    hasMore = true;
                } else {//请求数据为空（首次）
                    //隐藏加载更多控件
                    vh.footLayout.setVisibility(View.GONE);
                    // 将fadeTips设置true，隐藏footView提示
                    fadeTips = true;
                }
            }
        }
    }

}
