package com.yst.im.imchatlibrary.utils;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;

import com.androidkun.xtablayout.XTabLayout;

import java.util.Arrays;
import java.util.List;

/**
 * ViewPager设置适配器，并与TabLayout关联
 *
 * @author Lierpeng
 * @date 2017/12/14.
 * @version 1.0.1
 */
public class ImTabWithVpUtil {

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

    /**
     * 添加头部标题
     *
     * @param titleString tablayout中标题名
     * @return 返回标题集合
     */

    public static List<String> addTitle(String... titleString) {
        String[] title = titleString;
        List<String> list = Arrays.asList(title);
        return list;
    }

    /**
     * 添加fragment页面
     *
     * @param fragments fragment对象
     * @return 返回fragment集合
     */
    public static List<Fragment> addFragment(Fragment... fragments) {
        Fragment[] m = fragments;
        List<Fragment> fragmentList = Arrays.asList(m);
        return fragmentList;
    }
}
