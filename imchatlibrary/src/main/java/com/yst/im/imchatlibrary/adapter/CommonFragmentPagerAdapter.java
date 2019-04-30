package com.yst.im.imchatlibrary.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;

/**
 * 表情vpAdapter
 *
 * @author Lierpeng
 * @date 2018/04/11.
 * @version 1.0.0
 */
public class CommonFragmentPagerAdapter extends FragmentPagerAdapter {
    private ArrayList<Fragment> list;

    public CommonFragmentPagerAdapter(FragmentManager fm, ArrayList<Fragment> list) {
        super(fm);
        this.list = list;
    }

    @Override
    public Fragment getItem(int position) {
        return list.get(position);
    }

    @Override
    public int getCount() {
        return list != null ? list.size() : 0;
    }
}
