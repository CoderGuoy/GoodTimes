package com.coder.guoy.goodtimes.ui.home;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.coder.guoy.goodtimes.Constants;
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
 * @CreateTime:2017年11月20日
 * @Descrpiton:首页美女分类
 */
public class ImageAcitvity extends MvvmBaseActivity<ActivityImageTypeBinding> implements View.OnClickListener {
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
        switch (getIntent().getIntExtra("activityType", Constants.MV_TYPE)) {
            case Constants.MV_TYPE:
                getMVNetData(getIntent().getStringExtra("baseUrl"),
                        getIntent().getStringExtra("url"), PAGE);
                break;
            case Constants.FLS_TYPE:
                getFLSNetData(getIntent().getStringExtra("baseUrl"),
                        getIntent().getStringExtra("url"), PAGE);
                break;
        }
    }

    // TODO: 初始化RecyclerView的Adapter
    private void initRecyclerView() {
        adapter = new ImageMoreAdapter(ImageAcitvity.this,
                getIntent().getIntExtra("activityType", Constants.MV_TYPE));
        mLayoutManager = new GridLayoutManager(ImageAcitvity.this, 2);
        bindingView.recyclerviewImage.setLayoutManager(mLayoutManager);
        bindingView.recyclerviewImage.setAdapter(adapter);
        bindingView.recyclerviewImage.addOnScrollListener(mOnScrollListener);
    }

    /**
     *  获取网络数据
     */
    private void getMVNetData(String baseUrl, String url, int page) {
        ImageHelper.MeinvHelper(baseUrl, url, page)
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

    /**
     *  获取网络数据
     */
    private void getFLSNetData(String baseUrl, String url, int page) {
        ImageHelper.FLSHelper(baseUrl, url, page)
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
                switch (getIntent().getIntExtra("activityType", Constants.MV_TYPE)) {
                    case Constants.MV_TYPE:
                        upMeiNvData(getIntent().getStringExtra("baseUrl"),
                                getIntent().getStringExtra("url"), PAGE);
                        break;
                    case Constants.FLS_TYPE:
                        upFLSData(getIntent().getStringExtra("baseUrl"),
                                getIntent().getStringExtra("url"), PAGE);
                        break;
                }
            }
        }
    };

    /**
     *  加载更多
     */
    private void upMeiNvData(String baseUrl, String url, int page) {
        ImageHelper.MeinvHelper(baseUrl, url, page)
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

    /**
     *  加载更多
     */
    private void upFLSData(String baseUrl, String url, int page) {
        ImageHelper.FLSHelper(baseUrl, url, page)
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
