package com.yst.im.imchatlibrary.base;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import gorden.rxbus2.RxBus;

/**
 * BaseFragment
 *
 * @author Lierpeng
 * @version 1.0.0
 * @date 2018/03/27
 */
public abstract class BaseFragment extends Fragment {
    protected Context mContext;
    protected View mView;
    protected Activity mActivity;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (mView == null) {
            mView = inflater.inflate(getLayoutId(), null);
            initView(mView);
            initData();
            return mView;
        } else {
            return mView;
        }
    }

    /**
     * 初始化上下文
     */
    private void init() {
        mActivity = getActivity();
        mContext = getActivity();
        RxBus.get().register(this);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        RxBus.get().unRegister(this);
    }

    /**
     * 初始化布局
     *
     * @param mView view
     */
    public abstract void initView(View mView);

    /**
     * 初始化数据
     */
    public abstract void initData();

    /**
     * 初始化界面
     *
     * @return int
     */
    public abstract int getLayoutId();
}
