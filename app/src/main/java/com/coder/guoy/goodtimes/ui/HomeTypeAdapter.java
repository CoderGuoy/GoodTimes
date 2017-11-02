package com.coder.guoy.goodtimes.ui;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.coder.guoy.goodtimes.R;
import com.coder.guoy.goodtimes.databinding.ItemHomeTypeBinding;

/**
 * @Version:V1.0
 * @Author:CoderGuoy
 * @CreateTime:2017年12月2日
 * @Descrpiton:
 */
public class HomeTypeAdapter extends RecyclerView.Adapter {
    private LayoutInflater mInflater;
    private Context mContext;
    private ItemHomeTypeBinding binding;
    private int[] images;
    private String[] titles;
    private int count;

    public HomeTypeAdapter(Context context, int[] images, String[] titles, int count) {
        mInflater = LayoutInflater.from(context);
        mContext = context;
        this.images = images;
        this.titles = titles;
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
//        public CardView cardView;

        public NormalViewHolder(View itemView) {
            super(itemView);
            imageView = binding.itemImage;
            textTitle = binding.itemText;
//            cardView = binding.cardview;
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        binding = DataBindingUtil.inflate(mInflater, R.layout.item_home_type, parent, false);
        return new NormalViewHolder(binding.getRoot());
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        final NormalViewHolder vh = (NormalViewHolder) holder;
        //设置图片
        vh.imageView.setImageResource(images[position]);
        //设置标题
        vh.textTitle.setText(titles[position]);
        //跳转至详细页
//        vh.cardView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(mContext, ImageDeatilActivity.class);
//                Bundle options = ActivityOptions.makeSceneTransitionAnimation(
//                        (Activity) mContext, vh.imageView, "shareimage").toBundle();
//                intent.putExtra("imageUrl", mList.get(position).getImageUrl());
//                intent.putExtra("imageTitle", mList.get(position).getImgaeTitle());
//                mContext.startActivity(intent, options);
//            }
//        });
    }

}
