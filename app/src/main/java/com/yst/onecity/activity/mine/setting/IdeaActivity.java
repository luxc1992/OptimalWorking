package com.yst.onecity.activity.mine.setting;

import android.text.TextUtils;
import android.widget.EditText;
import android.widget.TextView;

import com.yst.basic.framework.utils.ToastUtils;
import com.yst.basic.framework.App;
import com.yst.basic.framework.base.BaseActivity;
import com.yst.onecity.R;
import com.yst.onecity.bean.MsgBean;
import com.yst.onecity.http.AbstractNetWorkCallback;
import com.yst.onecity.http.RequestApi;
import com.yst.onecity.view.EditTextWatcher;

import butterknife.BindView;
import butterknife.OnClick;

import static com.yst.onecity.Constant.NO1;
import static com.yst.onecity.Constant.NO100;
import static com.yst.onecity.Constant.NO20;

/**
 * (我的)意见反馈
 *
 * @author LianJinXue
 * @version 1.0.1
 * @date 2018/2/23
 */

public class IdeaActivity extends BaseActivity {
    @BindView(R.id.et_idea_couple)
    EditText mEtIdeaCouple;
    @BindView(R.id.txt_idea_num)
    TextView mTxtIdeaNum;
    @BindView(R.id.et_idea_phone)
    EditText mEtIdeaPhone;
    @BindView(R.id.txt_idea_submit)
    TextView mTxtIdeaSubmit;
    private String couple;
    private String num;

    @Override
    public int getLayoutId() {
        return R.layout.activity_idea;
    }

    @Override
    public void initData() {
        initTitleBar("意见反馈");
        mEtIdeaCouple.addTextChangedListener(new EditTextWatcher(mEtIdeaCouple, mTxtIdeaNum, NO100, this));
    }

    @OnClick(R.id.txt_idea_submit)
    public void onViewClicked() {
        couple = mEtIdeaCouple.getText().toString().trim();
        num = mEtIdeaPhone.getText().toString().trim();
        if (TextUtils.isEmpty(couple) || couple.length() < NO20) {
            ToastUtils.show("请输入意见20~100字");
            return;
        }
        getCouple();
    }

    /**
     * 意见反馈
     *
     * @version 1.0.1
     */
    private void getCouple() {
        RequestApi.myCoupleList(
                App.manager.getPhoneNum(),
                App.manager.getUuid(),
                couple,
                TextUtils.isEmpty(num) ? "" : num,
                new AbstractNetWorkCallback<MsgBean>() {
                    @Override
                    public void onSuccess(MsgBean msgBean) {
                        if (msgBean.getCode() == NO1) {
                            ToastUtils.show(msgBean.getMsg());
                            finish();
                        }else {
                            ToastUtils.show(msgBean.getMsg());
                        }
                    }

                    @Override
                    public void onError(String errorMsg) {
                        ToastUtils.show(errorMsg);
                    }
                });
    }
}