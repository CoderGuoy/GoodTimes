package com.coder.guoy.goodtimes.ui.girl;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.coder.guoy.goodtimes.Constants;
import com.coder.guoy.goodtimes.R;
import com.coder.guoy.goodtimes.api.GirlHelper;
import com.coder.guoy.goodtimes.api.bean.ImageBean;
import com.coder.guoy.goodtimes.base.MvvmBaseFragment;
import com.coder.guoy.goodtimes.databinding.FragmentGirlBinding;

import java.util.List;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;


public class FragmentGirl1 extends MvvmBaseFragment<FragmentGirlBinding> {
    private GridLayoutManager mLayoutManager;
    private GirlAdapter adapter;
    private int PAGE = 1;

    @Override
    public int setContent() {
        return R.layout.fragment_girl;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initRecyclerView();
    }

    @Override
    protected void getData() {
        super.getData();
        getNetData(Constants.NEW, PAGE);
    }

    private void getNetData(String url, int page) {
        GirlHelper.GirlHelper(url, page)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<List<ImageBean>>() {
                    @Override
                    public void onCompleted() {
                        showContentView();
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(List<ImageBean> beanList) {
                        adapter.setNewData(beanList);
                    }
                });
    }

    // 初始化RecyclerView的Adapter
    private void initRecyclerView() {
        adapter = new GirlAdapter(getContext());
        mLayoutManager = new GridLayoutManager(getContext(), 2);
        bindingView.recyclerviewList3.setLayoutManager(mLayoutManager);
        bindingView.recyclerviewList3.setAdapter(adapter);
        bindingView.recyclerviewList3.addOnScrollListener(mOnScrollListener);
    }

    private RecyclerView.OnScrollListener mOnScrollListener = new RecyclerView.OnScrollListener() {
        private boolean loading = true;
        private int previousTotal = 0;
        private int visibleItemCount;
        private int totalItemCount;
        private int lastVisibleItem;
        private int firstVisibleItem;

        @Override
        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
            super.onScrolled(recyclerView, dx, dy);
            visibleItemCount = recyclerView.getChildCount();
            totalItemCount = mLayoutManager.getItemCount();
            lastVisibleItem = mLayoutManager.findLastVisibleItemPosition();
            firstVisibleItem = mLayoutManager.findFirstVisibleItemPosition();
//            Log.i("previousTotal", previousTotal + "");
//            Log.i("visibleItemCount", visibleItemCount + "");
//            Log.i("totalItemCount", totalItemCount + "");
//            Log.i("lastVisibleItem", lastVisibleItem + "");
//            Log.i("firstVisibleItem", firstVisibleItem + "");
//            Log.i("onScrolled", "==========================");
        }

        @Override
        public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
            super.onScrollStateChanged(recyclerView, newState);
            if (newState == RecyclerView.SCROLL_STATE_IDLE && adapter.isFadeTips() == false
                    && lastVisibleItem + 1 == adapter.getItemCount()) {
                PAGE++;
                upNetData(Constants.NEW, PAGE);
            }
        }
    };

    private void upNetData(String url, int page) {
        GirlHelper.GirlHelper(url, page)
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

}
