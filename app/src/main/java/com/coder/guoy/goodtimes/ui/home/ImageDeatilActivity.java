package com.coder.guoy.goodtimes.ui.home;

import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.coder.guoy.goodtimes.Constants;
import com.coder.guoy.goodtimes.R;
import com.coder.guoy.goodtimes.api.ImageHelper;
import com.coder.guoy.goodtimes.api.bean.ImageBean;
import com.coder.guoy.goodtimes.base.MvvmBaseActivity;
import com.coder.guoy.goodtimes.databinding.ActivityImageDeatilBinding;
import com.coder.guoy.goodtimes.utils.GlideUtils;

import java.util.ArrayList;
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
public class ImageDeatilActivity extends MvvmBaseActivity<ActivityImageDeatilBinding> implements ViewPager.OnPageChangeListener {

    private List<ImageBean> imageList;
    private MyAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_deatil);
        adapter = new MyAdapter();
        bindingView.viewpagerImage.setAdapter(adapter);
        bindingView.viewpagerImage.setOffscreenPageLimit(3);
        bindingView.viewpagerImage.addOnPageChangeListener(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        imageList = null;
        adapter = null;
    }

    @Override
    protected void getData() {
        super.getData();
        Log.i("type", getIntent().getIntExtra("activityType", 0) + "");
        switch (getIntent().getIntExtra("activityType", Constants.MV_TYPE)) {
            case Constants.MV_TYPE:
                getMeinvNetData();
                break;
            case Constants.FLS_TYPE:
                getFLSNetData();
                break;
        }
    }

    /**
     * 获取美女详情页
     */
    private void getMeinvNetData() {
        ImageHelper.MeinvImageDetailHelper(getIntent().getStringExtra("linkUrl"))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<List<ImageBean>>() {
                    @Override
                    public void onCompleted() {
                        showContentView();
                    }

                    @Override
                    public void onError(Throwable e) {
                        showError();
                    }

                    @Override
                    public void onNext(List<ImageBean> imageBeans) {
                        bindingView.textPage.setText(1 + "/" + imageBeans.size());
                        imageList = imageBeans;
                        for (int i = 0; i < imageBeans.size(); i++) {
                            adapter.setData(imageBeans);
                        }
                    }
                });
    }

    /**
     * 获取福利社详情页
     */
    private void getFLSNetData() {
        ImageHelper.FLSImageDetailHelper(getIntent().getStringExtra("linkUrl"))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<List<ImageBean>>() {
                    @Override
                    public void onCompleted() {
                        showContentView();
                    }

                    @Override
                    public void onError(Throwable e) {
                        showError();
                    }

                    @Override
                    public void onNext(List<ImageBean> imageBeans) {
                        bindingView.textPage.setText(1 + "/" + imageBeans.size());
                        imageList = imageBeans;
                        for (int i = 0; i < imageBeans.size(); i++) {
                            adapter.setData(imageBeans);
                        }
                    }
                });
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        int page = position + 1;
        bindingView.textPage.setText(page + "/" + imageList.size());
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    public class MyAdapter extends PagerAdapter {
        private List<ImageBean> mList = new ArrayList<>();

        @Override
        public int getCount() {
            return mList.size() > 0 ? mList.size() : 0;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            ImageView imageView = new ImageView(getApplicationContext());
            imageView.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT));
            GlideUtils.setDetailImage(mList.get(position).getImageUrl(), imageView);

            container.addView(imageView);
            return imageView;
        }

        public void setData(List<ImageBean> imageBeans) {
            mList = imageBeans;
            notifyDataSetChanged();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }
    }
}
