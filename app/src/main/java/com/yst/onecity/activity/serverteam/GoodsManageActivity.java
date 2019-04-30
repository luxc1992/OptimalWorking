package com.yst.onecity.activity.serverteam;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;

import com.yst.basic.framework.base.BaseActivity;
import com.yst.onecity.R;
import com.yst.onecity.fragment.goodsmanage.DaiOnCarriageFragment;
import com.yst.onecity.fragment.goodsmanage.OnCarriageFragment;
import com.yst.onecity.fragment.goodsmanage.UnCarriageFragment;
import com.yst.onecity.view.viewpagerindicator.ViewPagerIndicator;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 商品管理页面
 *
 * @author wuxiaofang
 * @version 1.1.0
 * @date 2018/05/19
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
    private MyAdapter myAdapter;
    /**
     * 标题集合
     */
    private List<String> titles;
    /**
     * 上架中
     */
    private OnCarriageFragment onCarriageFragment;
    /**
     * 已下架
     */
    private UnCarriageFragment unCarriageFragment;
    /**
     * 待上架
     */
    private DaiOnCarriageFragment daiOnCarriageFragment;

    @Override
    public int getLayoutId() {
        return R.layout.activity_goods_manage;
    }

    @Override
    public void initData() {
        titles = new ArrayList<>();
        titles.add("上架中");
        titles.add("待上架");
        titles.add("已下架");
        onCarriageFragment = OnCarriageFragment.newInstance();
        daiOnCarriageFragment = DaiOnCarriageFragment.newInstance();
        unCarriageFragment = UnCarriageFragment.newInstance();
        list = new ArrayList<>();
        list.add(onCarriageFragment);
        list.add(daiOnCarriageFragment);
        list.add(unCarriageFragment);
        myAdapter = new MyAdapter(getSupportFragmentManager());
        viewpager.setAdapter(myAdapter);
        viewPagerIndicator.bindViewPager(viewpager, true);
//        viewpager.setOffscreenPageLimit(3);
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

        private MyAdapter(FragmentManager fm) {
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
         *
         * @param position s索引
         * @return
         */
        @Override
        public CharSequence getPageTitle(int position) {
            return titles.get(position);
        }
    }
}
