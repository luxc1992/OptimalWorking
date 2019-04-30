package com.yst.onecity.activity.issue;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.yst.basic.framework.utils.ToastUtils;
import com.yst.basic.framework.App;
import com.yst.basic.framework.base.BaseActivity;
import com.yst.basic.framework.utils.JumpIntent;
import com.yst.onecity.Constant;
import com.yst.onecity.R;
import com.yst.onecity.activity.mine.accountsafe.RealNameAuthenticationActivity;
import com.yst.onecity.bean.EventBean;
import com.yst.onecity.bean.accountsafe.AccountSafeBean;
import com.yst.onecity.http.AbstractNetWorkCallback;
import com.yst.onecity.http.RequestApi;

import org.greenrobot.eventbus.EventBus;

import butterknife.BindView;

/**
 * 发布界面
 *
 * @author zhaiyanwu
 * @version 1.0.1
 * @date 2018/02/06
 */
public class IssueActivity extends BaseActivity implements View.OnClickListener {

    @BindView(R.id.issue_ll_product_plan)
    LinearLayout productPlan;
    @BindView(R.id.issue_ll_project_plan)
    LinearLayout projectPlan;
    @BindView(R.id.issue_iv_cancel)
    ImageView ivCancel;
    private boolean isRealName;

    @Override
    public int getLayoutId() {
        return R.layout.activity_issue;
    }

    @Override
    public void initData() {
        getThirdLoginInfo();
        productPlan.setOnClickListener(this);
        projectPlan.setOnClickListener(this);
        ivCancel.setOnClickListener(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        getThirdLoginInfo();
        EventBus.getDefault().post(new EventBean("publish"));
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            //产品计划
            case R.id.issue_ll_product_plan:
                if (isRealName) {
                    JumpIntent.jump(this, AddProductPlanActivity.class);
                } else {
                    Bundle bundle = new Bundle();
                    bundle.putInt("key",0);
                    JumpIntent.jump(this, RealNameAuthenticationActivity.class,bundle);
                }
                break;
            //项目计划
            case R.id.issue_ll_project_plan:
                if (isRealName) {
                    JumpIntent.jump(this, ProjectPlanActivity.class);
                } else {
                    Bundle bundle = new Bundle();
                    bundle.putInt("key",1);
                    JumpIntent.jump(this, RealNameAuthenticationActivity.class);
                }
                break;
            case R.id.issue_iv_cancel:
                finish();
                break;
            default:
                break;
        }
    }

    /**
     * 获取信息
     */
    private void getThirdLoginInfo() {
        RequestApi.getAccountSafe(String.valueOf(App.manager.getId()),App.manager.getPhoneNum(), App.manager.getUuid(), new AbstractNetWorkCallback<AccountSafeBean>() {
            @Override
            public void onSuccess(AccountSafeBean accountSafeBean) {
                if (accountSafeBean.getCode() == Constant.NO1) {
                    if (accountSafeBean.getContent().getRealNameStatus() == 1) {
                        isRealName = true;
                    }
                } else {
                    ToastUtils.show(accountSafeBean.getMsg());
                }
            }

            @Override
            public void onError(String errorMsg) {
                ToastUtils.show(errorMsg);
            }

            @Override
            public void onAfter() {
                super.onAfter();
                dismissInfoProgressDialog();
            }

            @Override
            public void onBefore() {
                super.onBefore();
                showInfoProgressDialog();
            }
        });
    }
}
