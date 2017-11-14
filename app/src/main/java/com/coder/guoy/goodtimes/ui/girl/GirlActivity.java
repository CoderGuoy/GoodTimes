package com.coder.guoy.goodtimes.ui.girl;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.coder.guoy.goodtimes.R;
import com.coder.guoy.goodtimes.base.MvvmBaseActivity;
import com.coder.guoy.goodtimes.databinding.ActivityGirlBinding;

import java.util.ArrayList;
import java.util.List;

/**
 * @Version:V1.0
 * @Author:CoderGuoy
 * @CreateTime:2017年11月14日
 * @Descrpiton:
 */
public class GirlActivity extends MvvmBaseActivity<ActivityGirlBinding> {
    private String[] fragmentTitile = {"最新套图", "性感美女", "少女萝莉", "美乳香臀", "丝袜美腿",
            "性感特写", "欧美女神", "女神合集", "美女壁纸"};
    private List<Fragment> fragments = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_girl);
        initView();
    }

    private void initView() {
        initFragment();
        bindingView.viewpager.setAdapter(new FragmentAdapter(this.getSupportFragmentManager()));
        bindingView.tablayoutMain.setupWithViewPager(bindingView.viewpager);
        showContentView();
    }

    // TODO: 初始化Fragment
    private void initFragment() {
        fragments.add(new FragmentGirl1());
        fragments.add(new FragmentGirl2());
        fragments.add(new FragmentGirl3());
        fragments.add(new FragmentGirl4());
        fragments.add(new FragmentGirl5());
        fragments.add(new FragmentGirl6());
        fragments.add(new FragmentGirl7());
        fragments.add(new FragmentGirl8());
        fragments.add(new FragmentGirl9());
    }

    private class FragmentAdapter extends FragmentPagerAdapter {
        public FragmentAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return fragments.get(position);
        }

        @Override
        public int getCount() {
            return fragments.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return fragmentTitile[position];
        }
    }
}
