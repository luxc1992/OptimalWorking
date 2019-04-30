package com.yst.im.imchatlibrary.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.yst.im.imchatlibrary.MyApp;
import com.yst.im.imchatlibrary.R;
import com.yst.im.imchatlibrary.base.BaseActivity;
import com.yst.im.imchatlibrary.base.baseview.AbstractTitleView;
import com.yst.im.imchatlibrary.bean.BaseEntity;
import com.yst.im.imchatlibrary.bean.FriendInfoEntity;
import com.yst.im.imchatlibrary.model.DeleteUserModel;
import com.yst.im.imchatlibrary.utils.ImToastUtil;
import com.yst.im.imsdk.IntentType;
import com.yst.im.imsdk.ShowType;
import com.yst.im.imsdk.dao.MessageDaoUtils;

import static com.yst.im.imchatlibrary.ui.activity.SettingUserNameActivity.INTENT_SETTING_NAME;
import static com.yst.im.imsdk.MessageConstant.NUM_0;

/**
 * 好友资料设置
 *
 * @author qinchaoshuai
 * @version 1.0.0
 * @date 2018/3/30.
 */

public class FrientInfoSetActivity extends BaseActivity implements View.OnClickListener,
        DeleteUserModel.DeleteUserModelCallBack {

    private AbstractTitleView titleViewFirendInfo;
    private LinearLayout llSetFirendComment;
    private TextView txtSetFirendComment;
    private TextView txtSetCommentName;
    private FriendInfoEntity contentBean;
    private DeleteUserModel deleteUserModel;

    /**
     * 页面跳转
     *
     * @param context     上下文
     * @param contentBean 好友实体类
     */
    public static void getJumpFrientInfoSetActivity(Context context, FriendInfoEntity contentBean) {
        Intent intent = new Intent(context, FrientInfoSetActivity.class);
        intent.putExtra("contentBean", contentBean);
        context.startActivity(intent);
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_firend_info_set;
    }

    @Override
    protected int getStatusColor() {
        return R.color.colorWrite;
    }

    @Override
    protected void initView() {
        titleViewFirendInfo = (AbstractTitleView) findViewById(R.id.titleView_firend_info);
        llSetFirendComment = (LinearLayout) findViewById(R.id.ll_set_firend_comment);
        txtSetFirendComment = (TextView) findViewById(R.id.txt_set_firend_comment);
        txtSetCommentName = (TextView) findViewById(R.id.txt_set_comment_name);
        deleteUserModel = new DeleteUserModel(this);
        deleteUserModel.setDeleteUserModelCallBack(this);
        llSetFirendComment.setOnClickListener(this);
        txtSetFirendComment.setOnClickListener(this);

        titleViewFirendInfo.getLeftBackImageTv().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    @Override
    protected boolean getStatus() {
        return true;
    }

    @Override
    protected void initData() {
        contentBean = new FriendInfoEntity();
        if (getIntent() != null) {
            contentBean = (FriendInfoEntity) getIntent().getSerializableExtra("contentBean");
        }
        String remark = contentBean.getRemark() == null ? contentBean.getNickName() : contentBean.getRemark();
        txtSetCommentName.setText(remark);
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        if (id == R.id.ll_set_firend_comment) {
            SettingUserNameActivity.getJumpSettingUserNameActivity(FrientInfoSetActivity.this,
                    IntentType.startActivity, ShowType.Remark, INTENT_SETTING_NAME, contentBean.getUserId(), "设置备注");
            finish();
        } else if (id == R.id.txt_set_firend_comment) {
            deleteUserModel.deleteUser(contentBean.getUserId());
        }
    }

    @Override
    public void onDeleteUser(BaseEntity baseEntity) {
        ImToastUtil.showShortToast(FrientInfoSetActivity.this, baseEntity.getMsg());
        if (baseEntity.getCode() == NUM_0) {
            MessageDaoUtils.deleteInTx(MyApp.manager.getId() + contentBean.getUserId());
            MyApp.quiteActivity();
            finish();
        }
    }
}
