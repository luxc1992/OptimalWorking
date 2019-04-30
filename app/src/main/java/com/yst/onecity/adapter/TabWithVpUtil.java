package com.yst.onecity.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import com.androidkun.xtablayout.XTabLayout;
import java.util.List;

/**
 * ViewPager设置适配器，与TabLayout关联
 *
 * @author chenxiaowei
 * @version 1.0.1
 * @date 2018/2/6
 */
public class TabWithVpUtil {

    public static void tabWithViewPager(XTabLayout tabLayout, ViewPager viewPager,
                                        final List<Fragment> fragmentList, final List<String> titleList,
                                        FragmentManager fragmentManager) {
        viewPager.setAdapter(new FragmentPagerAdapter(fragmentManager) {
            @Override
            public Fragment getItem(int position) {
                return fragmentList.get(position);
            }

            @Override
            public int getCount() {
                return fragmentList.size();
            }

            @Override
            public CharSequence getPageTitle(int position) {
                return titleList.get(position);
            }
        });
        //TabLayout关联ViewPager
        tabLayout.setupWithViewPager(viewPager);
    }
}
