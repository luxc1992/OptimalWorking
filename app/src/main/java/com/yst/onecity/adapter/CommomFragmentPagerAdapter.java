package com.yst.onecity.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;

/**
 * 通用viewpager Fragment适配器
 *
 * @author chenjiadi
 * @version 1.0.1
 * @date 2018/2/6.
 */
public class CommomFragmentPagerAdapter extends FragmentPagerAdapter {
    private ArrayList<Fragment> fragments;
    private String[] titles;

    public CommomFragmentPagerAdapter(FragmentManager fm, ArrayList<Fragment> fragments, ArrayList<String> titleArr) {
        super(fm);
        notifyDataSetChanged();
        this.fragments = fragments;
        titles = new String[titleArr.size()];
        for (int i = 0; i < titleArr.size(); i++) {
            titles[i] = titleArr.get(i);
        }

    }

    public CommomFragmentPagerAdapter(FragmentManager fm, ArrayList<Fragment> fragments, String[] titles) {
        super(fm);
        this.fragments = fragments;
        this.titles = titles;
    }

    @Override
    public Fragment getItem(int position) {
        return fragments.get(position);
    }

    @Override
    public int getCount() {
        return fragments.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        if (titles == null) {
            return "";
        }
        return titles[position];
    }
}
