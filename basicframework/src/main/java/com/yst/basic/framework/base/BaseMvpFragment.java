package com.yst.basic.framework.base;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.yst.basic.framework.view.RequestProcessDialog;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * MVP 基础Fragment
 *
 * @author lixiangchao
 * @date 2017/12/2
 * @version 1.0.1
 */
public abstract class BaseMvpFragment<T extends BasePresenter, V extends BaseViewInter> extends Fragment {

    /**
     * 显示界面等待条
     */
    private RequestProcessDialog mInfoProgressDialog;
    protected View view;
    protected boolean isFirst = true;
    private Unbinder mUnbinder;
    protected T presenter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (view == null) {
            view = inflater.inflate(getLayoutId(), null);
            mUnbinder = ButterKnife.bind(this, view);
            presenter = getPresenter();
            presenter.attach((V) this);
            initData();
        }
        return view;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        //Presenter解除绑定
        if (presenter != null) {
            presenter.deAttach();
            presenter = null;
        }
        mUnbinder.unbind();
    }

    /**
     * 获得视图id
     *
     * @return 布局的id
     */
    protected abstract int getLayoutId();

    /**
     * 获取Presenter
     *
     * @return Presenter
     */
    protected abstract T getPresenter();

    /**
     * 初始化数据
     */
    protected abstract void initData();


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
