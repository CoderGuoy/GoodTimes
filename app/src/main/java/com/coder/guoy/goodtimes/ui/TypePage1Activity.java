package com.coder.guoy.goodtimes.ui;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.coder.guoy.goodtimes.R;
import com.coder.guoy.goodtimes.base.MvvmBaseActivity;
import com.coder.guoy.goodtimes.databinding.ActivityTypePage1Binding;

import java.util.ArrayList;
import java.util.List;
/**
 * @Version:V1.0
 * @Author:CoderGuoy
 * @CreateTime:
 * @Descrpiton:
 */
public class TypePage1Activity extends MvvmBaseActivity<ActivityTypePage1Binding> {
    private String[] fragmentTitile = {"热门", "影视", "明星", "高清", "体育", "美食"};
    private List<Fragment> fragments = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_type_page1);
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
        fragments.add(new Fragment1());
        fragments.add(new Fragment2());
        fragments.add(new Fragment3());
        fragments.add(new Fragment4());
        fragments.add(new Fragment4());
        fragments.add(new Fragment4());
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
