package com.coder.guoy.goodtimes.ui.home;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.coder.guoy.goodtimes.R;
import com.coder.guoy.goodtimes.api.ImageHelper;
import com.coder.guoy.goodtimes.api.bean.ImageBean;
import com.coder.guoy.goodtimes.base.MvvmBaseActivity;
import com.coder.guoy.goodtimes.databinding.ActivityImageTypeBinding;
import com.coder.guoy.goodtimes.utils.StatusBarUtils;
import com.coder.guoy.goodtimes.utils.ToastUtil;

import java.util.List;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * @Version:V1.0
 * @Author:CoderGuoy
 * @CreateTime:2017年11月23日
 * @Descrpiton:侧拉福利社
 */
public class FulisheAcitvity extends MvvmBaseActivity<ActivityImageTypeBinding> implements View.OnClickListener {
    private GridLayoutManager mLayoutManager;
    private ImageMoreAdapter adapter;
    private int PAGE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_type);
        initRecyclerView();
        initView();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mLayoutManager = null;
        adapter = null;
        PAGE = 1;
    }

    private void initView() {
        bindingView.textTitle.setText(getIntent().getStringExtra("title"));
        bindingView.toolbar.setBackgroundColor(getIntent().getIntExtra("color",
                Color.rgb(229, 67, 124)));
        StatusBarUtils.setColor(this, getIntent().getIntExtra("color",
                Color.rgb(229, 67, 124)), 0);
        bindingView.back.setOnClickListener(this);
    }

    @Override
    protected void getData() {
        super.getData();
        getNetData(getIntent().getStringExtra("baseUrl"),
                getIntent().getStringExtra("url"), PAGE);
    }

    // TODO: 初始化RecyclerView的Adapter
    private void initRecyclerView() {
        adapter = new ImageMoreAdapter(FulisheAcitvity.this);
        mLayoutManager = new GridLayoutManager(FulisheAcitvity.this, 2);
        bindingView.recyclerviewImage.setLayoutManager(mLayoutManager);
        bindingView.recyclerviewImage.setAdapter(adapter);
        bindingView.recyclerviewImage.addOnScrollListener(mOnScrollListener);
    }

    private void getNetData(String baseUrl, String url, int page) {
        ImageHelper.NvShenHelper(baseUrl, url, page)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<List<ImageBean>>() {
                    @Override
                    public void onCompleted() {
                        showContentView();
                    }

                    @Override
                    public void onError(Throwable e) {
                        ToastUtil.show(e.toString());
                    }

                    @Override
                    public void onNext(List<ImageBean> beanList) {
                        adapter.setNewData(beanList);
                    }
                });
    }

    private RecyclerView.OnScrollListener mOnScrollListener = new RecyclerView.OnScrollListener() {
        private int lastVisibleItem;

        @Override
        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
            super.onScrolled(recyclerView, dx, dy);
            lastVisibleItem = mLayoutManager.findLastVisibleItemPosition();
        }

        @Override
        public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
            super.onScrollStateChanged(recyclerView, newState);
            if (newState == RecyclerView.SCROLL_STATE_IDLE && adapter.isFadeTips() == false
                    && lastVisibleItem + 1 == adapter.getItemCount()) {
                PAGE++;
                upNetData(getIntent().getStringExtra("baseUrl"),
                        getIntent().getStringExtra("url"), PAGE);
            }
        }
    };

    private void upNetData(String baseUrl, String url, int page) {
        ImageHelper.NvShenHelper(baseUrl, url, page)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<List<ImageBean>>() {
                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(List<ImageBean> beanList) {
                        if (beanList.size() > 0 && beanList != null) {
                            adapter.addData(beanList, true);
                        } else {
                            adapter.addData(null, false);
                        }
                    }
                });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.back:
                finish();
                break;
        }
    }
}
