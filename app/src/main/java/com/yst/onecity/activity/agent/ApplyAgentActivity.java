package com.yst.onecity.activity.agent;

import android.widget.TextView;

import com.yst.basic.framework.base.BaseActivity;
import com.yst.basic.framework.utils.JumpIntent;
import com.yst.onecity.R;
import com.yst.onecity.activity.mine.MyZxingActivity;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 申请经纪人页面
 *
 * @author jiaofan
 * @version 1.0.1
 * @date 2018/5/17
 */
public class ApplyAgentActivity extends BaseActivity {

    @BindView(R.id.txt_apply_invite)
    TextView mTxtApplyInvite;

    @Override
    public int getLayoutId() {
        return R.layout.activity_apply_agent;
    }

    @Override
    public void initData() {
        initTitleBar("申请成为经纪人");
    }

    @OnClick(R.id.txt_apply_invite)
    public void onViewClicked() {
        JumpIntent.jump(this, MyZxingActivity.class);
    }
}
