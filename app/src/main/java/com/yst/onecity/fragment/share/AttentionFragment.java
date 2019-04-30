package com.yst.onecity.fragment.share;

import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;

import com.yst.basic.framework.utils.JumpIntent;
import com.yst.onecity.R;
import com.yst.onecity.activity.home.SearchHomeActivity;
import com.yst.onecity.adapter.CommomFragmentPagerAdapter;
import com.yst.onecity.fragment.LazyBaseFragment;
import com.yst.onecity.view.viewpagerindicator.ViewPagerIndicator;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 关注Fragment
 *
 * @author luxuchang
 * @version 1.1.0
 * @date 2018/5/16
 */
public class AttentionFragment extends LazyBaseFragment {
    @BindView(R.id.indicator_attention)
    ViewPagerIndicator indicator;
    @BindView(R.id.viewpager)
    ViewPager viewpager;

    private ArrayList<String> sortList = new ArrayList<>();
    private ArrayList<Fragment> fragments = new ArrayList<>();

    private CommomFragmentPagerAdapter adapter;

    @Override
    protected void loadData() {

    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_attention;
    }

    @Override
    public void init() {
        sortList.add("服务项目");
        sortList.add("分享");

        fragments.add(ServerProjectFragment.newInstance(0,0));
        fragments.add(new ChildShareFragment());

        adapter = new CommomFragmentPagerAdapter(getChildFragmentManager(), fragments, sortList);
        viewpager.setAdapter(adapter);
        indicator.bindViewPager(viewpager, true);
    }

    @OnClick(R.id.search)
    public void onSearchClicked() {
        JumpIntent.jump(getActivity(), SearchHomeActivity.class);
    }
}
