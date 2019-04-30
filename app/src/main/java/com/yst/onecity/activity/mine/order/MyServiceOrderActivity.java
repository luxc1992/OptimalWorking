package com.yst.onecity.activity.mine.order;

import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;

import com.androidkun.xtablayout.XTabLayout;
import com.yst.basic.framework.base.BaseActivity;
import com.yst.onecity.R;
import com.yst.onecity.adapter.TabWithVpUtil;
import com.yst.onecity.fragment.MyServiceOrderFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * 我的服务订单(买家)
 *
 * @author songbinbin
 * @version 1.1.0
 * @date 2018/05/17
 */
public class MyServiceOrderActivity extends BaseActivity {

    @BindView(R.id.my_service_order_tabLayout)
    XTabLayout tabLayout;
    @BindView(R.id.my_service_order_viewPager)
    ViewPager viewPager;
    private List<Fragment> fragments = new ArrayList<>();
    private List<String> tabs = new ArrayList<>();
    private int from;
    private int flag;

    @Override
    public int getLayoutId() {
        return R.layout.activity_my_service_order;
    }

    @Override
    public void initData() {
        initTitleBar(getString(R.string.my_service_order));
        tabs.add(getString(R.string.all_order));
        tabs.add(getString(R.string.hunter_pay));
        tabs.add(getString(R.string.my_order_use));
        tabs.add(getString(R.string.hunter_appraise));
        tabs.add(getString(R.string.my_order_exchange));
        if (getIntent().getExtras() != null) {
            flag = getIntent().getExtras().getInt("flag");
            from = getIntent().getExtras().getInt("from");
        }
        for (int i = 0; i < tabs.size(); i++) {
            fragments.add(MyServiceOrderFragment.newInstance(i,from));
            TabWithVpUtil.tabWithViewPager(tabLayout, viewPager, fragments, tabs, getSupportFragmentManager());
            tabLayout.setupWithViewPager(viewPager);
        }
        viewPager.setCurrentItem(flag);
    }
}
