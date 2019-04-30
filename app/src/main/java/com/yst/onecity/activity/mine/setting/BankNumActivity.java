package com.yst.onecity.activity.mine.setting;

import android.text.TextUtils;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.yst.basic.framework.utils.ToastUtils;
import com.yst.basic.framework.App;
import com.yst.basic.framework.base.BaseActivity;
import com.yst.basic.framework.utils.JumpIntent;
import com.yst.basic.framework.utils.NetworkUtils;
import com.yst.onecity.R;
import com.yst.onecity.bean.BankinfroBean;
import com.yst.onecity.http.AbstractNetWorkCallback;
import com.yst.onecity.http.RequestApi;
import com.yst.onecity.utils.ConstUtils;
import com.yst.onecity.utils.FormatUtils;
import com.yst.onecity.view.AbstractCommonDialog;

import butterknife.BindView;
import butterknife.OnClick;

import static com.yst.onecity.Constant.NO1;


/**
 * (我的)银行卡界面
 *
 * @author LianJinXue
 * @version 1.0.1
 * @date 2018/2/23
 */

public class BankNumActivity extends BaseActivity {
    @BindView(R.id.tv_bank_name)
    TextView tvBankName;
    @BindView(R.id.tv_bank_type)
    TextView tvBankType;
    @BindView(R.id.tv_card_num)
    TextView tvCardNum;
    @BindView(R.id.ll_bank_card_info)
    LinearLayout llCardInfo;
    private AbstractCommonDialog abstractCommonDialog;

    @Override
    public int getLayoutId() {
        return R.layout.activity_banknum;
    }

    @Override
    public void initData() {
        initTitleBar("银行卡");
        getBankInfo();
        abstractCommonDialog = new AbstractCommonDialog(this) {
            @Override
            public void sureClick() {
                JumpIntent.jump(BankNumActivity.this, VerifyTelphoneActivity.class);
                finish();
            }
        };
        abstractCommonDialog.setText("提示", "确定要解绑银行卡么?", "确定", "取消");
    }

    @OnClick(R.id.tv_unbound)
    public void onClick() {
        abstractCommonDialog.showDialog();
    }

    @Override
    protected void onResume() {
        super.onResume();
        abstractCommonDialog.dismissDialog();
    }

    /**
     * 获取银行卡信息
     *
     * @version 1.0.1
     */
    public void getBankInfo() {
        if(!NetworkUtils.isConnectNet(this)){
            llCardInfo.setVisibility(View.GONE);
            ToastUtils.show("无网络链接");
            return;
        }
        RequestApi.getBindInfo(App.manager.getUuid(), App.manager.getPhoneNum(), new AbstractNetWorkCallback<BankinfroBean>() {
            @Override
            public void onSuccess(BankinfroBean msgBean) {
                ToastUtils.show(msgBean.getMsg());
                if (msgBean.getCode() == NO1) {
                    if (null != msgBean.getContent().getBankNo()) {
                        llCardInfo.setVisibility(View.VISIBLE);
                        String nameAndType = ConstUtils.getStringNoEmpty(msgBean.getContent().getBank());
                        String name = "";
                        String type = "";
                        if(!TextUtils.isEmpty(nameAndType)){
                            String  dian = "·";
                            name = nameAndType.substring(0,nameAndType.indexOf(dian));
                            type = nameAndType.substring((nameAndType.indexOf(dian)+1));
                        }
                        tvBankName.setText(ConstUtils.getStringNoEmpty(name));
                        tvBankType.setText(ConstUtils.getStringNoEmpty(type));
                        tvCardNum.setText(FormatUtils.formatBankNo(msgBean.getContent().getBankNo()));
                    }
                }
            }

            @Override
            public void onError(String errorMsg) {
                llCardInfo.setVisibility(View.GONE);
                ToastUtils.show(errorMsg);
            }
        });
    }
}
