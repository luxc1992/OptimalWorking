package com.yst.im.imchatlibrary.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.TextView;

import com.yst.im.imchatlibrary.R;
import com.yst.im.imchatlibrary.base.BaseActivity;
import com.yst.im.imchatlibrary.base.baseview.AbstractTitleView;
import com.yst.im.imchatlibrary.bean.AlterNameEntity;
import com.yst.im.imchatlibrary.enumclass.AlterNameEnum;
import com.yst.im.imchatlibrary.model.UpdateGroupChatNameModel;
import com.yst.im.imchatlibrary.model.UpdateGroupRemarkModel;
import com.yst.im.imchatlibrary.widget.ClearEditText;
import com.yst.im.imsdk.ChatType;

import java.io.IOException;

import gorden.rxbus2.RxBus;

import static com.yst.im.imsdk.utils.RxBusConstants.CONST_UPDATE_NAME;

/**
 * 设置群名称
 *
 * @author Lierpeng
 * @version 1.0.0
 * @date 2018/04/02
 */
public class UpdateNameActivity extends BaseActivity implements UpdateGroupChatNameModel.UpdateGroupChatNameListenerCallBack, UpdateGroupRemarkModel.UpdateGroupRemarkListenerCallBack {
    private AbstractTitleView mTitleviewGroupSettingNameTitle;
    private ClearEditText mEdtGroupSettingNameInput;
    private TextView mTxtSettingTipsText;
    private UpdateGroupChatNameModel mUpdateGroupChatNameModel;
    private AlterNameEntity alterNameEntity;
    private UpdateGroupRemarkModel mUpdateGroupRemarkModel;

    @Override
    protected int getLayout() {
        return R.layout.activity_update_name;
    }

    @Override
    protected int getStatusColor() {
        return 0;
    }

    @Override
    protected void initView() {
        mTitleviewGroupSettingNameTitle = (AbstractTitleView) findViewById(R.id.titleview_group_setting_name_title);
        mTxtSettingTipsText = (TextView) findViewById(R.id.txt_setting_tips_text);
        mEdtGroupSettingNameInput = (ClearEditText) findViewById(R.id.edt_group_setting_name_input);
    }

    @Override
    protected boolean getStatus() {
        return true;
    }

    @Override
    protected void initData() {
        alterNameEntity = (AlterNameEntity) getIntent().getSerializableExtra("alterNameEntity");
        if (alterNameEntity != null) {
            mTitleviewGroupSettingNameTitle.setTitleText(alterNameEntity.getTitleName());
            mTitleviewGroupSettingNameTitle.setRightTvText(alterNameEntity.getRightName());
            mTxtSettingTipsText.setText(alterNameEntity.getTips());
            mEdtGroupSettingNameInput.setText(alterNameEntity.getName());
            mEdtGroupSettingNameInput.setHint(alterNameEntity.getTips());
        }
        mUpdateGroupChatNameModel = new UpdateGroupChatNameModel(this);
        mUpdateGroupChatNameModel.setUpdateGroupChatNameListenerCallBack(this);
        mUpdateGroupRemarkModel = new UpdateGroupRemarkModel(this);
        mUpdateGroupRemarkModel.setUpdateGroupRemarkListenerCallBack(this);
        mTitleviewGroupSettingNameTitle.getLeftBackTextTv().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        mTitleviewGroupSettingNameTitle.getRightTextTv().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = mEdtGroupSettingNameInput.getText().toString().trim();
                if (alterNameEntity.getAlterNameEnum() == AlterNameEnum.GroupName) {
                    mUpdateGroupChatNameModel.getUpdateGroupChat(alterNameEntity.getId(), name);
                } else if (alterNameEntity.getAlterNameEnum() == AlterNameEnum.NickNameInGroup) {
                    mUpdateGroupRemarkModel.getUpdateGroupRemark(alterNameEntity.getId(), name);
                }
            }
        });

    }

    /**
     * 页面跳转
     *
     * @param context         上下文
     * @param alterNameEntity 跳转
     */
    public static void getJumpSource(Context context, AlterNameEntity alterNameEntity) {
        Intent intent = new Intent(context, UpdateNameActivity.class);
        intent.putExtra("alterNameEntity", alterNameEntity);
        context.startActivity(intent);
    }

    @Override
    public void onUpdateGroupChatName() {
        finish();
    }

    @Override
    public void onUpdateGroupRemark() {
        finish();
    }
}
