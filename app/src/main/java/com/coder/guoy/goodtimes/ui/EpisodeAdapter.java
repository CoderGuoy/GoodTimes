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
 * @Descrpiton:段子
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
        GlideUtils.setImage(notNull(mList.get(position).getImageIcon()), vh.imageIcon);
        //作者
        vh.textAuthor.setText(notNull(mList.get(position).getAuthor()));
        //时间
        vh.textTime.setText(notNull(mList.get(position).getTime()));
        //标题
        vh.textTitle.setText(notNull(mList.get(position).getTitle()));
        //内容
        vh.textContent.setText(notNull(mList.get(position).getContent()));
        //评论
        String comment = mList.get(position).getCommnet();
        if (comment != null) {
            vh.textComment.setVisibility(View.VISIBLE);
            vh.textComment.setText(comment);
        } else {
            vh.textComment.setVisibility(View.GONE);
//            }
        }
    }

    private String notNull(String str) {
        if (str == null) {
            return "";
        } else {
            return str;
        }
    }

}
