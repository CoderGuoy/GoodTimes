package com.coder.guoy.goodtimes.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.coder.guoy.goodtimes.R;
import com.coder.guoy.goodtimes.base.MvvmBaseFragment;
import com.coder.guoy.goodtimes.databinding.Fragment3Binding;


public class Fragment3 extends MvvmBaseFragment<Fragment3Binding> {

    @Override
    public int setContent() {
        return R.layout.fragment_3;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        showContentView();
        bindingView.textview.setText(getString(R.string.Tab3));
    }
}
