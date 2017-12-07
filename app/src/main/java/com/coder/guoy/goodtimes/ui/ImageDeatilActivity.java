package com.coder.guoy.goodtimes.ui;

import android.os.Bundle;
import android.util.Log;

import com.coder.guoy.goodtimes.R;
import com.coder.guoy.goodtimes.api.ImageHelper;
import com.coder.guoy.goodtimes.api.bean.ImageBean;
import com.coder.guoy.goodtimes.base.MvvmBaseActivity;
import com.coder.guoy.goodtimes.databinding.ActivityImageDeatilBinding;
import com.coder.guoy.goodtimes.utils.GlideUtils;

import java.util.List;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * @Version:
 * @Author:
 * @CreateTime:
 * @Descrpiton:图片详情页
 */
public class ImageDeatilActivity extends MvvmBaseActivity<ActivityImageDeatilBinding> {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_deatil);
        initView();
    }

    @Override
    protected void getData() {
        super.getData();
        showContentView();
        getNetData();
    }

    private void getNetData() {
        ImageHelper.ImageDetailHelper(getIntent().getStringExtra("linkUrl"))
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
                    public void onNext(List<ImageBean> imageBeans) {
                        for (int i = 0; i < imageBeans.size(); i++) {
                            String imageUrl = imageBeans.get(i).getImageUrl();
                            Log.i("GYdetail", "" + imageUrl);

                        }
                    }
                });
    }

    private void initView() {
        String imageUrl = getIntent().getStringExtra("imageUrl");
        //设置图片
        GlideUtils.setDetailImage(imageUrl, bindingView.imageviewDetail);
    }

}
