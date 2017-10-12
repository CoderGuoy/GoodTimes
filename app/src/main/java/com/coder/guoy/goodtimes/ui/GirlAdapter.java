package com.coder.guoy.goodtimes.ui;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

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
//        public LinearLayout layout;

        public NormalViewHolder(View itemView) {
            super(itemView);
            imageView = binding.itemImage;
//            layout = binding.itemLayout;
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        binding = DataBindingUtil.inflate(mInflater, R.layout.item_menuclassify, parent, false);
        return new NormalViewHolder(binding.getRoot());
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        NormalViewHolder vh = (NormalViewHolder) holder;
        Log.i("URL", mList.get(position).getImageUrl());
        GlideUtils.setImage(mList.get(position).getImageUrl(), vh.imageView);
    }

}
