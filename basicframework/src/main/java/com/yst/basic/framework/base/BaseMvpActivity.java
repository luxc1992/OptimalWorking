package com.yst.basic.framework.base;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.yst.basic.framework.App;
import com.yst.basic.framework.view.RequestProcessDialog;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * MVP项目框架基类
 *
 * @author lixiangchao
 * @date 2017/12/1
 * @version 1.0.1
 */
public abstract class BaseMvpActivity<T extends BasePresenter, V extends BaseViewInter> extends AppCompatActivity {

    protected T presenter;
    private Bundle savedInstanceState;
    private Unbinder mUnbinder;
    protected boolean isFirst = true;

    /**
     * 显示界面等待条
     */
    private RequestProcessDialog mInfoProgressDialog;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.savedInstanceState = savedInstanceState;
        setContentView(getLayoutId());
        mUnbinder = ButterKnife.bind(this);
        presenter = getPresenter();
        presenter.attach((V) this);
        initData();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //Presenter解除绑定
        if (presenter != null) {
            presenter.deAttach();
            presenter = null;
        }
        mUnbinder.unbind();
        //删除当前Activity
        App.getInstance().removeActivity(this);
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
            mInfoProgressDialog = new RequestProcessDialog(this);
        }
        mInfoProgressDialog.setMessage("加载中");
        mInfoProgressDialog.setCancelable(false);
        if (!this.isFinishing()) {
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
