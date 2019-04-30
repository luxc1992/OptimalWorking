package com.yst.onecity.activity.serverteam;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.widget.LinearLayout;

import com.yst.basic.framework.base.BaseActivity;
import com.yst.basic.framework.utils.Utils;
import com.yst.onecity.R;
import com.yst.onecity.bean.servermanage.ServerManageBean;
import com.yst.onecity.fragment.servermanage.DaiOnCarriageFragment;
import com.yst.onecity.fragment.servermanage.OnCarriageFragment;
import com.yst.onecity.fragment.servermanage.UnCarriageFragment;
import com.yst.onecity.view.viewpagerindicator.ViewPagerIndicator;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.OnClick;


/**
 * 服务管理
 *
 * @author wuxiaofang
 * @version 1.1.0
 * @date 2018/5/17
 */

public class ServerManagerActivity extends BaseActivity {

    @BindView(R.id.ll_back)
    LinearLayout llBack;
    @BindView(R.id.indicator)
    ViewPagerIndicator indicator;
    @BindView(R.id.viewpager)
    ViewPager viewpager;
    private ArrayList<String> titles;
    private OnCarriageFragment onCarriageFragment;
    private DaiOnCarriageFragment daiOnCarriageFragment;
    private UnCarriageFragment unCarriageFragment;
    private ArrayList<Fragment> list;
    private MyAdapter myAdapter;
    private ArrayList<ServerManageBean> dataList;

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
        indicator.bindViewPager(viewpager, true);
        viewpager.setOffscreenPageLimit(3);
    }


    /**
     * 点击事件
     */
    @OnClick(R.id.ll_back)
    public void click() {
        if (!Utils.isClickable()) {
            return;
        }
        finish();
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
