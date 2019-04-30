package com.yst.basic.framework.base;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.yst.basic.framework.utils.ToastUtils;
import com.yst.basic.framework.view.RequestProcessDialog;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * MVC框架的Fragment
 *
 * @author lixiangchao
 * @date 2017/12/2
 * @version 1.0.1
 */
public abstract class BaseFragment extends Fragment {

    protected View view;
    private Unbinder unbinder;
    protected boolean isFirst = true;

    /**
     * 显示界面等待条
     */
    private RequestProcessDialog mInfoProgressDialog;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
//        if (view == null) {
            view = inflater.inflate(getLayoutId(), null);
            unbinder = ButterKnife.bind(this, view);
            init();
            initCtrl();
            setListener();
//        }
        return view;
    }

    /**
     * 点击事件监听
     */
    protected void setListener() {

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
        if (null != view && null != view.getParent()) {
            ((ViewGroup) view.getParent()).removeView(view);
        }
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