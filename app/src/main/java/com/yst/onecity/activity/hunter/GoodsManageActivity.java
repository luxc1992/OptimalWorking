package com.yst.onecity.activity.hunter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;

import com.yst.basic.framework.base.BaseActivity;
import com.yst.onecity.R;
import com.yst.onecity.fragment.OffShelfFragment;
import com.yst.onecity.fragment.OnSaleFragment;
import com.yst.onecity.view.viewpagerindicator.ViewPagerIndicator;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 商品管理页面
 *
 * @author chenjiadi
 * @version 1.0.1
 * @date 2018/02/09
 */
public class GoodsManageActivity extends BaseActivity {

    @BindView(R.id.indicator)
    ViewPagerIndicator viewPagerIndicator;
    @BindView(R.id.viewpager)
    ViewPager viewpager;
    /**
     * viewpager下fragment集合
     */
    private List<Fragment> list;
    private MyAdapter adapter;
    /**
     * 标题集合
     */
    private List<String> titles;
    /**
     * 出售中fragment
     */
    private OnSaleFragment onSaleFragment;
    /**
     * 已下架fragment
     */
    private OffShelfFragment offShelfFragment;

    @Override
    public int getLayoutId() {
        return R.layout.activity_goods_manage;
    }

    @Override
    public void initData() {
        titles = new ArrayList<>();
        titles.add("出售中");
        titles.add("已下架");
        onSaleFragment = OnSaleFragment.newInstance("member_id");
        offShelfFragment = OffShelfFragment.newInstance("member_id");
        list = new ArrayList<>();
        list.add(onSaleFragment);
        list.add(offShelfFragment);
        adapter = new MyAdapter(getSupportFragmentManager());
        viewpager.setAdapter(adapter);
        viewPagerIndicator.bindViewPager(viewpager, true);
        viewpager.setOffscreenPageLimit(2);
    }

    @OnClick({R.id.ll_back})
    public void onViewClicked(View view) {
        switch (R.id.ll_back) {
            case R.id.ll_back:
                finish();
                break;
            default:
                break;
        }
    }

    /**
     * 适配器
     */
    class MyAdapter extends FragmentPagerAdapter {

        public MyAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return list.get(position);
        }

        @Override
        public int getCount() {
            return list.size();
        }

        /**
         * 重写这个方法，将设置每个Tab的标题
         * @param position s索引
         * @return
         */
        @Override
        public CharSequence getPageTitle(int position) {
            return titles.get(position);
        }
    }
}
