package com.yst.im.imchatlibrary.ui.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.yst.im.imchatlibrary.MyApp;
import com.yst.im.imchatlibrary.R;
import com.yst.im.imchatlibrary.base.BaseActivity;
import com.yst.im.imchatlibrary.base.baseview.AbstractSortEnumType;
import com.yst.im.imchatlibrary.base.baseview.AbstractTitleView;
import com.yst.im.imchatlibrary.bean.BaseEntity;
import com.yst.im.imchatlibrary.bean.GroupDetailsEntity;
import com.yst.im.imchatlibrary.bean.GroupOwnerEntity;
import com.yst.im.imchatlibrary.model.GroupOwnerModel;
import com.yst.im.imchatlibrary.model.UpdateGroupChatNoticeModel;
import com.yst.im.imchatlibrary.ui.listener.EditChangedListener;
import com.yst.im.imchatlibrary.utils.ImToastUtil;
import com.yst.im.imchatlibrary.utils.IntentConst;
import com.yst.im.imchatlibrary.widget.ImRoundedImageView;
import com.yst.im.imsdk.utils.BaseUtils;

import java.util.Collections;
import java.util.List;

import gorden.rxbus2.RxBus;

import static com.yst.im.imsdk.MessageConstant.NUM_0;
import static com.yst.im.imsdk.MessageConstant.NUM_1000;
import static com.yst.im.imsdk.MessageConstant.NUM_51;
import static com.yst.im.imsdk.MessageConstant.NUM_58;
import static com.yst.im.imsdk.MessageConstant.NUM_59;
import static com.yst.im.imsdk.utils.RxBusConstants.CONST_UPDATE_NAME;

/**
 * 群聊公告
 *
 * @author Lierpeng
 * @version 1.0.0
 * @date 2018/4/4.
 */
public class GroupNoticeActivity extends BaseActivity implements GroupOwnerModel.GroupOwnerCallBack, UpdateGroupChatNoticeModel.UpdateGroupChatNoticeListenerCallBack {
    private AbstractTitleView mTitleGroupNotice;
    private ImRoundedImageView mImgGroupNoticeIcon;
    private TextView mTxtGroupNoticeNickName;
    private TextView mTxtGroupNoticeTime;
    private EditText mEdtGroupNoticeInput;
    private LinearLayout mLilGroupNoticeInfo;
    private GroupDetailsEntity groupDetailsEntity;
    private UpdateGroupChatNoticeModel mUpdateGroupChatNoticeModel;
    private TextView mTxtGroupNoticeEdit;

    @Override
    protected int getLayout() {
        return R.layout.activity_group_notice;
    }

    @Override
    protected int getStatusColor() {
        return 0;
    }

    @Override
    protected void initView() {
        mTitleGroupNotice = (AbstractTitleView) findViewById(R.id.title_group_notice);
        mImgGroupNoticeIcon = (ImRoundedImageView) findViewById(R.id.img_group_notice_icon);
        mTxtGroupNoticeNickName = (TextView) findViewById(R.id.txt_group_notice_nick_name);
        mTxtGroupNoticeTime = (TextView) findViewById(R.id.txt_group_notice_time);
        mEdtGroupNoticeInput = (EditText) findViewById(R.id.edt_group_notice_input);
        mTitleGroupNotice.setTitleText(getResources().getString(R.string.txt_group_notice));
        mLilGroupNoticeInfo = (LinearLayout) findViewById(R.id.lil_group_notice_info);
        mTxtGroupNoticeEdit = (TextView) findViewById(R.id.txt_group_notice_edit);
        mTitleGroupNotice.getLeftBackTextTv().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        mTitleGroupNotice.getRightTextTv().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String notice = mEdtGroupNoticeInput.getText().toString().trim();
                if (notice.length() == NUM_0) {
                    ImToastUtil.showShortToast(GroupNoticeActivity.this, "请输入公告内容");
                    return;
                } else if (notice.length() > NUM_1000) {
                    ImToastUtil.showShortToast(GroupNoticeActivity.this, "群公告字数太多，请重新编辑");
                    return;
                }
                mUpdateGroupChatNoticeModel.getUpdateGroupChatNotice(String.valueOf(groupDetailsEntity.getId()), notice);
            }
        });
    }

    @Override
    protected boolean getStatus() {
        return false;
    }

    @Override
    protected void initData() {
        groupDetailsEntity = (GroupDetailsEntity) getIntent().getSerializableExtra("groupEntity");
        if (null == groupDetailsEntity.getDescrbe()) {
            mLilGroupNoticeInfo.setVisibility(View.GONE);
        } else {
            mLilGroupNoticeInfo.setVisibility(View.VISIBLE);
        }
        GroupOwnerModel mGroupOwnerModel = new GroupOwnerModel(this);
        mUpdateGroupChatNoticeModel = new UpdateGroupChatNoticeModel(this);
        mGroupOwnerModel.setGroupOwnerCallBack(this);
        mUpdateGroupChatNoticeModel.setUpdateGroupChatNoticeListenerCallBack(this);
        if (null != groupDetailsEntity) {
            mGroupOwnerModel.getGroupOwner(String.valueOf(groupDetailsEntity.getId()));
        }
    }


    @Override
    public void onGroupOwner(GroupOwnerEntity.ContentBean contentBean) {

        if (contentBean.getUserId().equals(MyApp.manager.getId())) {
            mTitleGroupNotice.getRightTextTv().setText("编辑");
            mTxtGroupNoticeEdit.setVisibility(View.GONE);
            mEdtGroupNoticeInput.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    mLilGroupNoticeInfo.setVisibility(View.GONE);
                    mTitleGroupNotice.getRightTextTv().setText(getResources().getString(R.string.txt_finish));
                    mEdtGroupNoticeInput.setFocusable(true);
                    mEdtGroupNoticeInput.setFocusableInTouchMode(true);
                    return false;
                }
            });
        } else {
            // 禁止输入
            mEdtGroupNoticeInput
                    .setEnabled(false);
            mTitleGroupNotice.getRightTextTv().setText("");
            mTxtGroupNoticeEdit.setVisibility(View.VISIBLE);
        }
        mTxtGroupNoticeTime.setText(null == BaseUtils.stampToDate(contentBean.getUpdateTime()) ? "" : BaseUtils.stampToDate(contentBean.getUpdateTime()));
        mEdtGroupNoticeInput.setText(null == contentBean.getDescrib() ? "" : contentBean.getDescrib());
        mTxtGroupNoticeNickName.setText(contentBean.getNickName());
        Glide.with(this).load(contentBean.getUserIcon()).into(mImgGroupNoticeIcon);
    }

    @Override
    public void onUpdateGroupChatNotice(BaseEntity baseEntity) {
        if (baseEntity.getCode() == NUM_0) {
            mLilGroupNoticeInfo.setVisibility(View.VISIBLE);
            RxBus.get().send(CONST_UPDATE_NAME, mEdtGroupNoticeInput.getText().toString());
            finish();
        } else {
            ImToastUtil.showShortToast(this, baseEntity.getMsg());
        }
    }
}