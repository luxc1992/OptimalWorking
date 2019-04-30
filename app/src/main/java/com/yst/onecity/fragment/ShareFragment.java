package com.yst.onecity.fragment;

import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;

import com.yst.basic.framework.base.BaseFragment;
import com.yst.onecity.R;
import com.yst.onecity.adapter.CommomFragmentPagerAdapter;
import com.yst.onecity.fragment.share.AttentionFragment;
import com.yst.onecity.fragment.share.IndustryFragment;
import com.yst.onecity.fragment.share.RecommendedFragment;
import com.yst.onecity.view.viewpagerindicator.ViewPagerIndicator;

import java.util.ArrayList;

import butterknife.BindView;

/**
 * 分享Fragment
 *
 * @author chenxiaowei
 * @version 1.0.1
 * @date 2018/5/15
 */
public class ShareFragment extends BaseFragment {

    @BindView(R.id.indicator_share)
    ViewPagerIndicator indicator;
    @BindView(R.id.viewpager)
    ViewPager viewpager;

    private ArrayList<String> sortList = new ArrayList<>();
    private ArrayList<Fragment> fragments = new ArrayList<>();

    private CommomFragmentPagerAdapter adapter;
    @Override
    public int getLayoutId() {
        return R.layout.fragment_share;
    }

    @Override
    public void init() {
        sortList.add("推荐");
        sortList.add("关注");
        sortList.add("行业");

        fragments.add(new RecommendedFragment());
        fragments.add(new AttentionFragment());
        fragments.add(new IndustryFragment());

        adapter = new CommomFragmentPagerAdapter(getChildFragmentManager(), fragments, sortList);
        viewpager.setAdapter(adapter);
        indicator.bindViewPager(viewpager, true);
    }
}
