package com.coder.guoy.goodtimes.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.coder.guoy.goodtimes.R;
import com.coder.guoy.goodtimes.base.MvvmBaseFragment;
import com.coder.guoy.goodtimes.databinding.Fragment4Binding;


public class Fragment4 extends MvvmBaseFragment<Fragment4Binding> {

    @Override
    public int setContent() {
        return R.layout.fragment_4;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        showContentView();
        bindingView.textview.setText(getString(R.string.Tab4));
    }
    /**
     * 八宝”的益处：
     * 枸杞具有补肾益精、养肝明目、补血安神、生津止渴、润肺止咳功效；
     * 红枣味甘性温、归脾胃经，补中益气、养血安神、缓和药性；
     * 桂圆滋补强体、补心安神、养血壮阳、益脾开胃、润肤美容；
     * 果干（山楂干）具有消积化滞、收敛止痢、活血化瘀之功效；
     * 冰糖味甘，性平，归脾、肺经。有补中益气、和胃、止咳化痰之功效；
     * 葡萄干性平，味甘、微酸，具有补肝肾、益气血、生津液、利小便的功效；
     * 芝麻补血明目、祛风润肠、生津通乳、益肝养发、强身体、抗疲劳；
     * 茶叶助消化、解油腻、促进新陈代谢，增强心脏功能、解除疲劳、降低血压、防止动脉硬化；
     *
     * 核桃仁补肾通脑、有益智慧、补气养血、润燥化痰、温肺润肠、散肿消毒；
     * 菊花散风清热，平肝明目。用于风热感冒，头痛眩晕，目赤肿痛，眼目昏花；
     * 玫瑰缓和情绪、平衡内分泌、补血气，美颜护肤、对肝及胃有调理的作用；
     * 决明子具有清肝明目，润肠通便，降脂瘦身的功用；
     *
     * 洋参具有抗癌、抗疲劳、抗缺氧、抗辐射、抗衰老等多种功能，对冠心病、高血压、贫血、神经官能症、
     * 糖尿病等具有一定的疗效，能改善消瘦、耳鸣、口干舌燥、腰膝酸软、肺虚久咳等症状，尤其适合年老、
     * 病后体虚、身体素质较差、长期脑力劳动、内分泌失调等人群进补；
     */
}
