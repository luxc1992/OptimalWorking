package com.yst.onecity.activity.mine.order;

import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;

import com.androidkun.xtablayout.XTabLayout;
import com.yst.basic.framework.base.BaseActivity;
import com.yst.onecity.R;
import com.yst.onecity.adapter.TabWithVpUtil;
import com.yst.onecity.fragment.ServeTeamProductOrderFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * 服务团队商品订单
 *
 * @author chenxiaowei
 * @version 1.1.0
 * @date 2018/05/21
 */
public class ServeTeamProductOrderActivity extends BaseActivity {

    @BindView(R.id.tabLayout)
    XTabLayout tabLayout;
    @BindView(R.id.viewPager)
    ViewPager viewPager;
    private List<Fragment> fragments = new ArrayList<>();
    private List<String> tabs = new ArrayList<>();

    @Override
    public int getLayoutId() {
        return R.layout.activity_serve_team_product_order;
    }

    @Override
    public void initData() {
        initTitleBar("我的订单");
        tabs.add("全部订单");
        tabs.add("待付款");
        tabs.add("待发货");
        tabs.add("待收货");
        tabs.add("待评价");
        for (int i = 0; i < tabs.size(); i++) {
            fragments.add(ServeTeamProductOrderFragment.newInstance(i));
            TabWithVpUtil.tabWithViewPager(tabLayout, viewPager, fragments, tabs, getSupportFragmentManager());
            tabLayout.setupWithViewPager(viewPager);
        }
        if (getIntent().getExtras() != null) {
            int flag = getIntent().getExtras().getInt("flag");
            viewPager.setCurrentItem(flag);
        }
    }
}
