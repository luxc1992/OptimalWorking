package com.yst.onecity.activity.mine;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.widget.LinearLayout;

import com.yst.basic.framework.base.BaseActivity;
import com.yst.onecity.R;
import com.yst.onecity.adapter.CommomFragmentPagerAdapter;
import com.yst.onecity.fragment.CollectGoodsFragment;
import com.yst.onecity.fragment.MyCollectServeProjectFragment;
import com.yst.onecity.fragment.MyCollectShareFragment;
import com.yst.onecity.utils.TabLayoutIndicatorUtils;

import java.util.ArrayList;
import java.util.Arrays;

import butterknife.BindView;

/**
 * 我的收藏
 *
 * @author liumanqing
 * @version 1.0.1
 * @date 2018/2/25
 */
public class MyCollectActivity extends BaseActivity {
    @BindView(R.id.mycollect_vp_tablayout)
    TabLayout tabLayout;
    @BindView(R.id.mycollect_vp)
    ViewPager viewPager;
    /**
     * 分类适配器
     */
    private CommomFragmentPagerAdapter adapter;
    /**
     * 分类
     */
    private ArrayList<String> sortList;
    private ArrayList<Fragment> fragments;
    private String[] sortName = {"服务项目", "商品", "分享"};

    @Override
    public int getLayoutId() {
        return R.layout.activity_my_collect;
    }

    @Override
    public void initData() {
        initTitleBar(getString(R.string.mycollect));
        tabLayout.addTab(tabLayout.newTab().setText(sortName[0]));
        tabLayout.addTab(tabLayout.newTab().setText(sortName[1]));
        tabLayout.addTab(tabLayout.newTab().setText(sortName[2]));
        initSort();
    }

    /**
     * 获取分类
     */
    private void initSort() {
        sortList = new ArrayList<>();
        fragments = new ArrayList<>();
        sortList.addAll(Arrays.asList(sortName));
        fragments.add(new MyCollectServeProjectFragment());
        fragments.add(CollectGoodsFragment.newInstance());
        fragments.add(new MyCollectShareFragment());
        adapter = new CommomFragmentPagerAdapter(getSupportFragmentManager(), fragments, sortList);
        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);
        // 设置tabLayout的分隔线
        LinearLayout linearLayout = (LinearLayout) tabLayout.getChildAt(0);
        linearLayout.setShowDividers(LinearLayout.SHOW_DIVIDER_MIDDLE);
        linearLayout.setDividerDrawable(ContextCompat.getDrawable(this,
                R.drawable.shape_vpindicator_right));
        linearLayout.setDividerPadding(30);
        //设置游标的宽度
        tabLayout.post(new Runnable() {
            @Override
            public void run() {
                TabLayoutIndicatorUtils.setIndicator(tabLayout, 30, 30);
            }
        });
    }
}
