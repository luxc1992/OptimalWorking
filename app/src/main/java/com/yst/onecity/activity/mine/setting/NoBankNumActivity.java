package com.yst.onecity.activity.mine.setting;

import android.view.View;
import android.widget.TextView;

import com.yst.basic.framework.utils.ToastUtils;
import com.yst.basic.framework.App;
import com.yst.basic.framework.base.BaseActivity;
import com.yst.basic.framework.utils.JumpIntent;
import com.yst.onecity.R;
import com.yst.onecity.activity.mine.accountsafe.RealNameAuthenticationActivity;
import com.yst.onecity.bean.accountsafe.RealNameAuthenticationInfoBean;
import com.yst.onecity.http.AbstractNetWorkCallback;
import com.yst.onecity.http.RequestApi;
import com.yst.onecity.view.AbstractUnBindDialog;

import butterknife.BindView;


/**
 * (我的)没有绑定银行卡界面
 *
 * @author LianJinXue
 * @version 1.0.1
 * @date 2018/2/24
 */
public class NoBankNumActivity extends BaseActivity {

    @BindView(R.id.txt_nobank_textview)
    TextView tvNoBank;
    private AbstractUnBindDialog mAbstractUnBindDialog;

    @Override
    public int getLayoutId() {
        return R.layout.activity_nobanknum;
    }

    @Override
    public void initData() {
        initTitleBar("银行卡");
        mAbstractUnBindDialog = new AbstractUnBindDialog(this) {
            @Override
            public void sureClick() {

            }
        };
        mAbstractUnBindDialog.setText("解绑成功");
        tvNoBank.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //实名认证
                getRealName();
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mAbstractUnBindDialog.dismissDialog();
    }

    /**
     * 实名认证信息
     */
    private void getRealName(){
        RequestApi.realNameAuthenticationInfo(App.manager.getUuid(), App.manager.getPhoneNum(), new AbstractNetWorkCallback<RealNameAuthenticationInfoBean>() {
            @Override
            public void onSuccess(RealNameAuthenticationInfoBean realNameAuthenticationInfoBean) {
                //未认证转至实名认证页
                if (realNameAuthenticationInfoBean.getCode() == 0) {
                    if ( null==realNameAuthenticationInfoBean.getContent()){
                        JumpIntent.jump(NoBankNumActivity.this,RealNameAuthenticationActivity.class);
                        ToastUtils.show(realNameAuthenticationInfoBean.getMsg());
                    }else {
                        ToastUtils.show(realNameAuthenticationInfoBean.getMsg());
                    }
                //已认证转至添加银行卡页
                }else {
                    JumpIntent.jump(NoBankNumActivity.this,BankActivity.class);
                    finish();
                }
            }

            @Override
            public void onError(String errorMsg) {
                ToastUtils.show(errorMsg);
            }
        });
    }
}
