package com.yst.onecity.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.yst.basic.framework.utils.ToastUtils;
import com.yst.basic.framework.view.RequestProcessDialog;

import butterknife.ButterKnife;

/**
 * 懒加载 fragment 基类
 *
 * @author luxuchang
 * @version 1.1.0
 * @date 2018/6/7
 */
public abstract class LazyBaseFragment extends Fragment {

    protected View view;
    protected boolean isVisible;
    private boolean isPrepared;
    private boolean isFirst = true;

    /**
     * 显示界面等待条
     */
    private RequestProcessDialog mInfoProgressDialog;

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (getUserVisibleHint()) {
            isVisible = true;
            lazyLoad();
        } else {
            isVisible = false;
            onInvisible();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (view == null) {
            view = inflater.inflate(getLayoutId(), null);
            ButterKnife.bind(this, view);

            init();
            initCtrl();
            setListener();
        }
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        isPrepared = true;
        lazyLoad();
    }

    protected void lazyLoad() {
        if (!isPrepared || !isVisible || !isFirst) {
            return;
        }
        loadData();
        isFirst = false;
    }

    /**
     * 加载数据
     */
    protected abstract void loadData();

    /**
     * 点击事件监听
     */
    protected void setListener() {

    }

    /**
     * do something
     */
    protected void onInvisible() {
        view = null;
    }
    /**
     * 设置布局文件
     *
     * @return 布局资源
     */
    public abstract int getLayoutId();

    /**
     * 初始化数据
     */
    public abstract void init();

    public void initCtrl(){

    }

    /**
     * 吐司提醒
     *
     * @param message
     */
    public void showToastContent(String message) {
        if (null == message) {
            return;
        }
        ToastUtils.show(message);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        view = null;
    }

    /**
     * 展示页面等待框
     */
    public void showInfoProgressDialog() {
        if (mInfoProgressDialog == null) {
            mInfoProgressDialog = new RequestProcessDialog(getActivity());
        }
        mInfoProgressDialog.setMessage("加载中");
        mInfoProgressDialog.setCancelable(false);
        if (!getActivity().isFinishing()) {
            try {
                mInfoProgressDialog.show();
            } catch (Exception e) {

            }
        }
    }

    /**
     * 关闭进度条
     */
    public void dismissInfoProgressDialog() {
        if (mInfoProgressDialog != null) {
            mInfoProgressDialog.dismiss();
        }
    }
}
