package com.coder.guoy.goodtimes.cache;

import android.databinding.DataBindingUtil;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.coder.guoy.goodtimes.R;
import com.coder.guoy.goodtimes.api.bean.ImageBean;
import com.coder.guoy.goodtimes.databinding.ActivityCacheBinding;
import com.coder.guoy.goodtimes.ui.adapter.TypePageAdapter;
import com.coder.guoy.goodtimes.utils.ToastUtil;

import java.util.List;

import rx.Observer;
import rx.Subscription;

import static com.coder.guoy.goodtimes.Constants.ZMBZ;

public class CacheActivity extends AppCompatActivity implements View.OnClickListener {
    private ActivityCacheBinding binding;
    private String classType1 = "list_cont list_cont1 w1180";
    private String classType2 = "list_cont list_cont2 w1180";
    private String classType3 = "list_cont Left_list_cont";
    private String imageType1 = "src";
    private String imageType2 = "url";
    private int Page0 = 0;
    private TypePageAdapter adapter;
    private long startTime;
    protected Subscription subscription;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_cache);
        transparentStatusBar();
        binding.btn1.setOnClickListener(this);
        binding.btn2.setOnClickListener(this);
        binding.btn3.setOnClickListener(this);
        //最新
        getNetData(ZMBZ, Page0, classType1, imageType1, binding.recyclerviewModel2);
    }

    // TODO: 透明状态栏
    private void transparentStatusBar() {
        View decorView = getWindow().getDecorView();
        int option = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                | View.SYSTEM_UI_FLAG_LAYOUT_STABLE;
        decorView.setSystemUiVisibility(option);
        getWindow().setStatusBarColor(Color.TRANSPARENT);
    }

    //TODO: 获取网络数据

    /**
     * @param url          连接地址
     * @param position     页面中数据源条目位置
     * @param recyclerView 对应的控件
     */
    private void getNetData(final String url, final int position,
                            final String classType, final String imageType,
                            final RecyclerView recyclerView) {
        startTime = System.currentTimeMillis();
        unsubscribe();
        subscription = Data.getInstance().subscribeData(new Observer<List<ImageBean>>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                Log.i("cache_onError", e.toString());
            }

            @Override
            public void onNext(List<ImageBean> imageBeen) {
                int loadTime = (int) (System.currentTimeMillis() - startTime);
                Log.i("cache_onNext", loadTime + "ms");
                initRecyclerView(imageBeen, recyclerView);
            }
        }, url, position, classType, imageType);
    }

    // TODO: 图片列表
    private void initRecyclerView(List<ImageBean> beanList, RecyclerView recyclerView) {
        adapter = new TypePageAdapter(this);
        GridLayoutManager mLayoutManager = new GridLayoutManager(this, 2);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setAdapter(adapter);
        recyclerView.setNestedScrollingEnabled(false);
    }

    protected void unsubscribe() {
        if (subscription != null && !subscription.isUnsubscribed()) {
            subscription.unsubscribe();
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_1:
                Data.getInstance().clearMemoryCache();
                ToastUtil.show("内存缓存已清空");
                adapter.removeItems(null);
                break;
            case R.id.btn_2:
                Data.getInstance().clearMemoryAndDiskCache();
                ToastUtil.show("内存缓存和磁盘缓存已清空");
                adapter.removeItems(null);
                break;
            case R.id.btn_3:
                getNetData(ZMBZ, Page0, classType1, imageType1, binding.recyclerviewModel2);
                break;
        }
    }
}