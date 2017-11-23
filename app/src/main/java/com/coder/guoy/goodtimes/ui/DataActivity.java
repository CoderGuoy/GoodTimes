package com.coder.guoy.goodtimes.ui;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.coder.guoy.goodtimes.Constants;
import com.coder.guoy.goodtimes.R;
import com.coder.guoy.goodtimes.api.ImageHelper;
import com.coder.guoy.goodtimes.api.bean.ImageBean;
import com.coder.guoy.goodtimes.databinding.Fragment4Binding;

import java.util.List;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * @Version:
 * @Author:
 * @CreateTime:
 * @Descrpiton:
 */
public class DataActivity extends AppCompatActivity {
    private Fragment4Binding bindingView;
    private long startTime = 0;
    private HomePageAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        bindingView = DataBindingUtil.setContentView(this, R.layout.fragment_4);
        initRecyclerView(bindingView.recyclerviewModel2);

        getNetData(Constants.FL_URl, Constants.TTNS_URl, 1);
    }

    //TODO: 获取网络数据
    private void getNetData(final String baseUrl, final String url, final int page) {
        startTime = System.currentTimeMillis();
        ImageHelper.NvShenHelper(baseUrl, url, page)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<List<ImageBean>>() {
                    @Override
                    public void onNext(List<ImageBean> beanList) {
                        int time = (int) (System.currentTimeMillis() - startTime);
                        Log.i("time", time + "ms");
                        adapter.setNewData(beanList);
                    }

                    @Override
                    public void onCompleted() {
                        Log.i("onCompleted", "完成");
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e("onError", e.toString());
                    }
                });
    }

    // TODO: 初始化RecyclerView

    /**
     * @param recyclerView 对应的控件
     */
    private void initRecyclerView(RecyclerView recyclerView) {
        adapter = new HomePageAdapter(this);
        GridLayoutManager mLayoutManager = new GridLayoutManager(this, 2);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setAdapter(adapter);
        recyclerView.setNestedScrollingEnabled(false);
    }

}
