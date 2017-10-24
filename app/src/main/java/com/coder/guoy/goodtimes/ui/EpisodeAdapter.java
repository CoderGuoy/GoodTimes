package com.coder.guoy.goodtimes.ui;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.coder.guoy.goodtimes.R;
import com.coder.guoy.goodtimes.api.bean.EpisodeBean;
import com.coder.guoy.goodtimes.databinding.ItemEpisodeBinding;
import com.coder.guoy.goodtimes.utils.GlideUtils;

import java.util.List;

/**
 * @Version:
 * @Author:
 * @CreateTime:
 * @Descrpiton:
 */
public class EpisodeAdapter extends RecyclerView.Adapter {
    private List<EpisodeBean> mList;
    private LayoutInflater mInflater;
    private Context mContext;
    private ItemEpisodeBinding binding;

    public EpisodeAdapter(Context context, List list) {
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
        public ImageView imageIcon;
        public TextView textAuthor;
        public TextView textTime;
        public TextView textTitle;
        public TextView textContent;
        public TextView textComment;
        public CardView cardView;

        public NormalViewHolder(View itemView) {
            super(itemView);
            imageIcon = binding.itemImage;
            textAuthor = binding.itemAuthor;
            textTime = binding.itemTime;
            textTitle = binding.itemTitle;
            textContent = binding.itemContent;
            cardView = binding.cardview;
            textComment = binding.itemComment;
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        binding = DataBindingUtil.inflate(mInflater, R.layout.item_episode, parent, false);
        return new NormalViewHolder(binding.getRoot());
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        NormalViewHolder vh = (NormalViewHolder) holder;
        //头像
        String imageUrl = mList.get(position).getImageIcon();
        if (imageUrl != null) {
            GlideUtils.setImage(imageUrl, vh.imageIcon);
        }
        //作者
        String author = mList.get(position).getAuthor();
        if (author != null) {
            vh.textAuthor.setText(author);
        }
        //时间
        String time = mList.get(position).getTime();
        if (time != null) {
            vh.textTime.setText(time);
        }
        //标题
        String title = mList.get(position).getTitle();
        if (title != null) {
            vh.textTitle.setText(title);
        }
        //内容
        String content = mList.get(position).getContent();
        if (content != null) {
            vh.textContent.setText(content);
        }
        //评论
        String comment = mList.get(position).getCommnet();
        if (comment != null) {
            vh.textComment.setVisibility(View.VISIBLE);
            vh.textComment.setText(comment);
        } else {
            vh.textComment.setVisibility(View.GONE);
        }
    }

}
