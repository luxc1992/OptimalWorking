package com.yst.im.imchatlibrary.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.yst.im.imchatlibrary.MyApp;
import com.yst.im.imchatlibrary.R;
import com.yst.im.imchatlibrary.base.BaseActivity;
import com.yst.im.imchatlibrary.base.baseview.AbstractTitleView;
import com.yst.im.imchatlibrary.bean.BaseEntity;
import com.yst.im.imchatlibrary.bean.FindUserEntity;
import com.yst.im.imchatlibrary.bean.FriendInfoEntity;
import com.yst.im.imchatlibrary.bean.IntentChatEntity;
import com.yst.im.imchatlibrary.bean.SelectFriendEntity;
import com.yst.im.imchatlibrary.model.CheckAddFriendModel;
import com.yst.im.imchatlibrary.model.CheckJoinGroupModel;
import com.yst.im.imchatlibrary.model.FindUserModel;
import com.yst.im.imchatlibrary.model.SelectFriendInfoModel;
import com.yst.im.imchatlibrary.model.SelectReationBetweenThePersonsModel;
import com.yst.im.imchatlibrary.utils.ImToastUtil;
import com.yst.im.imchatlibrary.widget.ImRoundedImageView;
import com.yst.im.imsdk.ChatType;
import com.yst.im.imsdk.IntentType;
import com.yst.im.imsdk.ShowType;
import com.yst.im.imsdk.utils.RxBusConstants;

import java.util.List;

import gorden.rxbus2.Subscribe;
import gorden.rxbus2.ThreadMode;

import static com.yst.im.imsdk.MessageConstant.MAN;

import static com.yst.im.imsdk.MessageConstant.NUM_0;
import static com.yst.im.imsdk.MessageConstant.NUM_1;
import static com.yst.im.imsdk.MessageConstant.NUM_2;
import static com.yst.im.imsdk.MessageConstant.NUM_3;
import static com.yst.im.imsdk.MessageConstant.NUM_4;

/**
 * 申请确认页面
 *
 * @author qinchaoshuai
 * @version 1.0.0
 * @date 2018/4/16
 */

public class ApplyAffirmActivity extends BaseActivity implements View.OnClickListener,
        CheckAddFriendModel.CheckAddFriendListenerCallBack,
        CheckJoinGroupModel.CheckJoinGroupListenerCallBack,
        SelectFriendInfoModel.SelectFriendInfoCallBack,
        FindUserModel.FindUserListenerCallBack, SelectReationBetweenThePersonsModel.selectReationBetweenThePersonsCallBack {

    private AbstractTitleView titleViewApplyAffirm;
    private ImageView imgApplySex;
    private TextView txtApplyComment;
    private TextView txtApplyNick;
    private TextView txtApplyGroupName;
    private TextView txtApplyNameGroup;
    private TextView txtApplyNameFirend;
    private TextView txtCommentName;
    private TextView txtApplyAddress;
    private TextView txtAffireBtn;
    private ImageView imgCommentName;
    private LinearLayout llApplyGroupAffirm;
    private LinearLayout llApplyFriendAffirm;
    private LinearLayout llFirendComment;
    private ImRoundedImageView groupIconView;

    /**
     * 0，审批好友申请， 1 审批群組申請， 2 詳細資料, 3 添加联系人搜索, 4 群成员列表点击
     */
    private int applyType = 0;
    private CheckAddFriendModel checkAddFriendModel;
    private CheckJoinGroupModel checkJoinGroupModel;
    private FriendInfoEntity friendInfoEntity;
    private SelectFriendInfoModel mSelectFriendInfoModel;
    private FindUserModel mFindUserModel;

    /**
     * 页面跳转
     *
     * @param context   上下文
     * @param applyType 跳转来源类型
     */
    public static void getJumpApplyAffirmActivity(Context context, int applyType, FriendInfoEntity friendInfoEntity) {
        Intent intent = new Intent(context, ApplyAffirmActivity.class);
        intent.putExtra("applyType", applyType);
        intent.putExtra("friendInfoEntity", friendInfoEntity);
        context.startActivity(intent);
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_apply_affirm;
    }

    @Override
    protected int getStatusColor() {
        return R.color.colorWrite;
    }

    @Override
    protected void initView() {
        MyApp.addActivity(this);
        titleViewApplyAffirm = (AbstractTitleView) findViewById(R.id.titleview_apply_affirm);
        imgApplySex = (ImageView) findViewById(R.id.img_apply_sex);
        txtAffireBtn = (TextView) findViewById(R.id.txt_affire_btn);
        txtApplyComment = (TextView) findViewById(R.id.txt_apply_comment);
        txtApplyNick = (TextView) findViewById(R.id.txt_apply_nick);
        txtApplyGroupName = (TextView) findViewById(R.id.txt_apply_group_name);
        txtApplyNameGroup = (TextView) findViewById(R.id.txt_apply_name_group);
        txtApplyNameFirend = (TextView) findViewById(R.id.txt_apply_name_firend);
        txtCommentName = (TextView) findViewById(R.id.txt_comment_name);
        txtApplyAddress = (TextView) findViewById(R.id.txt_apply_address);
        llApplyGroupAffirm = (LinearLayout) findViewById(R.id.ll_apply_group_affirm);
        llApplyFriendAffirm = (LinearLayout) findViewById(R.id.ll_apply_friend_affirm);
        llFirendComment = (LinearLayout) findViewById(R.id.ll_firend_comment);
        groupIconView = (ImRoundedImageView) findViewById(R.id.group_icon_view);
        imgCommentName = (ImageView) findViewById(R.id.img_comment_name);
        titleViewApplyAffirm.getLeftBackImageTv().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        titleViewApplyAffirm.getRightImageIv().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean state = applyType == NUM_0 && String.valueOf(NUM_0).equals(friendInfoEntity.getState())
                        || applyType == NUM_2
                        || applyType == NUM_4 && String.valueOf(NUM_0).equals(friendInfoEntity.getState());
                if (state) {
                    FrientInfoSetActivity.getJumpFrientInfoSetActivity(ApplyAffirmActivity.this, friendInfoEntity);
                }
            }
        });
        txtAffireBtn.setOnClickListener(this);
        llFirendComment.setOnClickListener(this);
    }

    @Override
    protected boolean getStatus() {
        return true;
    }

    @Override
    protected void initData() {
        if (getIntent() != null) {
            applyType = getIntent().getIntExtra("applyType", 0);
            friendInfoEntity = (FriendInfoEntity) getIntent().getSerializableExtra("friendInfoEntity");
        }
        mSelectFriendInfoModel = new SelectFriendInfoModel(this);
        mSelectFriendInfoModel.setSelectFriendInfoCallBack(this);
        mFindUserModel = new FindUserModel(this);
        mFindUserModel.setFindUserListenerCallBack(this);
        getViewDate();
    }

    /**
     * 获取状态
     */
    private void getViewDate() {
        if (applyType == NUM_0) {
            txtApplyNick.setVisibility(View.INVISIBLE);
            llApplyGroupAffirm.setVisibility(View.GONE);
            titleViewApplyAffirm.setTitleText("好友申请");
            Glide.with(MyApp.getInstance())
                    .load(friendInfoEntity.getUserIcon())
                    .error(R.mipmap.icon_default)
                    .into(groupIconView);
            if (MAN.equals(friendInfoEntity.getSex())) {
                imgApplySex.setBackground(ContextCompat.getDrawable(ApplyAffirmActivity.this, R.drawable.icon_man));
            } else {
                imgApplySex.setBackground(ContextCompat.getDrawable(ApplyAffirmActivity.this, R.drawable.icon_woman));
            }
            txtApplyComment.setText(friendInfoEntity.getNickName());
            txtApplyAddress.setText(friendInfoEntity.getAddress());
            txtApplyNameFirend.setText(friendInfoEntity.getDescrib());

            if (String.valueOf(NUM_2).equals(friendInfoEntity.getState())) {
                checkAddFriendModel = new CheckAddFriendModel(this);
                checkAddFriendModel.setCheckAddFriendListenerCallBack(this);
                txtAffireBtn.setText("通过验证");
            } else if (String.valueOf(NUM_0).equals(friendInfoEntity.getState())) {
                imgCommentName.setVisibility(View.VISIBLE);
                txtAffireBtn.setText("发消息");
            } else {
                txtAffireBtn.setText("已拒绝该申请");
                txtAffireBtn.setBackgroundResource(R.drawable.btn_shape_width_gray);
            }
        } else if (applyType == NUM_1) {
            checkJoinGroupModel = new CheckJoinGroupModel(ApplyAffirmActivity.this);
            checkJoinGroupModel.setCheckJoinGroupListenerCallBack(this);
            txtApplyNick.setVisibility(View.INVISIBLE);
            llApplyFriendAffirm.setVisibility(View.GONE);
            txtApplyNameGroup.setVisibility(View.VISIBLE);
            Glide.with(MyApp.getInstance())
                    .load(friendInfoEntity.getUserIcon())
                    .error(R.mipmap.icon_default)
                    .into(groupIconView);
            if (MAN.equals(friendInfoEntity.getSex())) {
                imgApplySex.setBackground(ContextCompat.getDrawable(ApplyAffirmActivity.this, R.drawable.icon_man));
            } else {
                imgApplySex.setBackground(ContextCompat.getDrawable(ApplyAffirmActivity.this, R.drawable.icon_woman));
            }

            txtApplyComment.setText(friendInfoEntity.getNickName());
            txtApplyAddress.setText(friendInfoEntity.getAddress());
            txtApplyNameGroup.setText(friendInfoEntity.getDescrib());
            if (String.valueOf(NUM_2).equals(friendInfoEntity.getState())) {
                checkAddFriendModel = new CheckAddFriendModel(this);
                checkAddFriendModel.setCheckAddFriendListenerCallBack(this);
                txtAffireBtn.setText("通过验证");
            } else if (String.valueOf(NUM_0).equals(friendInfoEntity.getState())) {
                txtAffireBtn.setText("发消息");
            } else {
                txtAffireBtn.setText("已拒绝该申请");
                txtAffireBtn.setBackgroundResource(R.drawable.btn_shape_width_gray);
            }
            titleViewApplyAffirm.setTitleText("加群申请");
            txtApplyGroupName.setText(friendInfoEntity.getGroupName());
        } else if (applyType == NUM_2) {
            imgCommentName.setVisibility(View.VISIBLE);
            llApplyGroupAffirm.setVisibility(View.GONE);
            llApplyFriendAffirm.setVisibility(View.GONE);
            llFirendComment.setOnClickListener(this);
            txtApplyNick.setVisibility(View.VISIBLE);
            txtCommentName.setVisibility(View.VISIBLE);
            txtApplyAddress.setVisibility(View.VISIBLE);
            txtApplyComment.setVisibility(View.VISIBLE);
            txtAffireBtn.setText("发消息");
            titleViewApplyAffirm.setTitleText("详细资料");

            Glide.with(MyApp.getInstance())
                    .load(friendInfoEntity.getUserIcon())
                    .error(R.mipmap.icon_default)
                    .into(groupIconView);
            if (MAN.equals(friendInfoEntity.getSex())) {
                imgApplySex.setBackground(ContextCompat.getDrawable(ApplyAffirmActivity.this, R.drawable.icon_man));
            } else {
                imgApplySex.setBackground(ContextCompat.getDrawable(ApplyAffirmActivity.this, R.drawable.icon_woman));
            }

            if (TextUtils.isEmpty(friendInfoEntity.getRemark())) {
                txtApplyComment.setText(friendInfoEntity.getNickName());
                txtApplyNick.setVisibility(View.GONE);
                txtCommentName.setText("");
            } else {
                txtApplyNick.setVisibility(View.VISIBLE);
                txtApplyNick.setText(String.valueOf("昵称: " + String.valueOf(friendInfoEntity.getNickName())));
                txtCommentName.setText(String.valueOf(friendInfoEntity.getRemark()));
                txtApplyComment.setText(friendInfoEntity.getRemark());
            }
            txtApplyAddress.setText(String.valueOf(friendInfoEntity.getAddress()));
        } else if (applyType == NUM_3) {
            llApplyGroupAffirm.setVisibility(View.GONE);
            llApplyFriendAffirm.setVisibility(View.GONE);
            txtApplyNick.setVisibility(View.GONE);
            llFirendComment.setOnClickListener(this);
            txtApplyNick.setVisibility(View.INVISIBLE);
            txtCommentName.setVisibility(View.VISIBLE);
            if (String.valueOf(NUM_0).equals(friendInfoEntity.getState())) {
                txtAffireBtn.setText("发消息");
            } else {
                txtAffireBtn.setText("添加到通讯录");
            }
            titleViewApplyAffirm.setTitleText("详细资料");
            Glide.with(MyApp.getInstance())
                    .load(friendInfoEntity.getUserIcon())
                    .error(R.mipmap.icon_default)
                    .into(groupIconView);
            if (MAN.equals(friendInfoEntity.getSex())) {
                imgApplySex.setBackground(ContextCompat.getDrawable(ApplyAffirmActivity.this, R.drawable.icon_man));
            } else {
                imgApplySex.setBackground(ContextCompat.getDrawable(ApplyAffirmActivity.this, R.drawable.icon_woman));
            }
            txtApplyComment.setText(friendInfoEntity.getNickName());
            txtApplyAddress.setText(friendInfoEntity.getAddress());
        } else {

            if (String.valueOf(NUM_0).equals(friendInfoEntity.getState())) {
                imgCommentName.setVisibility(View.VISIBLE);
                mSelectFriendInfoModel.selectFriendInfo(friendInfoEntity.getUserId());
                txtAffireBtn.setText("发消息");
                titleViewApplyAffirm.setTitleText("详细资料");
                imgCommentName.setVisibility(View.VISIBLE);
                llApplyGroupAffirm.setVisibility(View.GONE);
                llApplyFriendAffirm.setVisibility(View.GONE);
                llFirendComment.setOnClickListener(this);
                txtApplyNick.setVisibility(View.VISIBLE);
                txtCommentName.setVisibility(View.VISIBLE);
                txtApplyAddress.setVisibility(View.VISIBLE);
                txtApplyComment.setVisibility(View.VISIBLE);

            } else {
                llApplyGroupAffirm.setVisibility(View.GONE);
                llApplyFriendAffirm.setVisibility(View.GONE);
                txtApplyNick.setVisibility(View.GONE);
                llFirendComment.setOnClickListener(this);
                txtApplyNick.setVisibility(View.INVISIBLE);
                txtCommentName.setVisibility(View.VISIBLE);
                mFindUserModel.getFindUser(friendInfoEntity.getUserId());
                txtAffireBtn.setText("添加到通讯录");
                titleViewApplyAffirm.setTitleText("详细资料");
            }
        }
        if (applyType != NUM_4) {
            SelectReationBetweenThePersonsModel mSelectReationBetweenThePersonsModel = new SelectReationBetweenThePersonsModel(this);
            mSelectReationBetweenThePersonsModel.setselectReationBetweenThePersonsCallBack(this);
            mSelectReationBetweenThePersonsModel.selectReationBetweenThePersons(friendInfoEntity.getUserId());
        }
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        if (id == R.id.txt_affire_btn) {
            if (applyType == NUM_0 && null != friendInfoEntity) {
                if (String.valueOf(NUM_0).equals(friendInfoEntity.getState())) {
                    if (null != friendInfoEntity.getRemark()) {
                        toChat(friendInfoEntity.getRemark(), friendInfoEntity.getUserId());
                    } else {
                        toChat(friendInfoEntity.getNickName(), friendInfoEntity.getUserId());
                    }
                } else if (String.valueOf(NUM_2).equals(friendInfoEntity.getState())) {
                    checkAddFriendModel.getCheckAddFriend(friendInfoEntity.getUserId(), "0");
                }
            } else if (applyType == NUM_1 && null != friendInfoEntity) {
                if (String.valueOf(NUM_0).equals(friendInfoEntity.getState())) {

                    IntentChatEntity intentChatEntity = new IntentChatEntity();
                    if (null != friendInfoEntity.getRemark()) {
                        intentChatEntity.setAcceptName(friendInfoEntity.getRemark());
                    } else {
                        intentChatEntity.setAcceptName(friendInfoEntity.getGroupName());
                    }
                    intentChatEntity.setAcceptId(friendInfoEntity.getGroupChatId());
                    intentChatEntity.setChatType(ChatType.GROUP);
                    intentChatEntity.setGroupNum(friendInfoEntity.getCurrentMemberNumber());
                    ChatScreenActivity.getJumpChatSource(ApplyAffirmActivity.this, intentChatEntity);
                } else if (String.valueOf(NUM_2).equals(friendInfoEntity.getState())) {
                    checkJoinGroupModel.getCheckJoinGroup(friendInfoEntity.getUserId(), "0", friendInfoEntity.getGroupChatId());
                }
            } else if (applyType == NUM_2 && null != friendInfoEntity) {
                if (null != friendInfoEntity.getRemark()) {
                    toChat(friendInfoEntity.getRemark(), friendInfoEntity.getUserId());
                } else {
                    toChat(friendInfoEntity.getNickName(), friendInfoEntity.getUserId());
                }
            } else if (applyType == NUM_3 && null != friendInfoEntity) {
                if (String.valueOf(NUM_0).equals(friendInfoEntity.getState())) {
                    IntentChatEntity intentChatEntity = new IntentChatEntity();
                    if (null != friendInfoEntity.getRemark()) {
                        intentChatEntity.setAcceptName(friendInfoEntity.getRemark());
                    } else {
                        intentChatEntity.setAcceptName(friendInfoEntity.getGroupName());
                    }
                    intentChatEntity.setAcceptId(friendInfoEntity.getUserId());
                    intentChatEntity.setChatType(ChatType.C2C);
                    ChatScreenActivity.getJumpChatSource(ApplyAffirmActivity.this, intentChatEntity);
                } else {
                    SettingUserNameActivity.getJumpSettingUserNameActivity(ApplyAffirmActivity.this,
                            IntentType.startActivity, ShowType.Descrb, 0, friendInfoEntity.getUserId(), "朋友验证");
                }
            } else {
                if (String.valueOf(NUM_0).equals(friendInfoEntity.getState())) {
                    if (null != friendInfoEntity.getRemark()) {
                        toChat(friendInfoEntity.getRemark(), friendInfoEntity.getUserId());
                    } else {
                        toChat(friendInfoEntity.getNickName(), friendInfoEntity.getUserId());
                    }
                } else {
                    SettingUserNameActivity.getJumpSettingUserNameActivity(ApplyAffirmActivity.this,
                            IntentType.startActivity, ShowType.Descrb, 0, friendInfoEntity.getUserId(), "朋友验证");
                }
            }
        } else if (id == R.id.ll_firend_comment) {
            boolean state = applyType == NUM_0 && String.valueOf(NUM_0).equals(friendInfoEntity.getState())
                    || applyType == NUM_2
                    || applyType == NUM_4 && String.valueOf(NUM_0).equals(friendInfoEntity.getState());
            if (state) {
                FrientInfoSetActivity.getJumpFrientInfoSetActivity(ApplyAffirmActivity.this, friendInfoEntity);
            }
        }
    }

    private void toChat(String nickName, String userId) {
        IntentChatEntity intentChatEntity = new IntentChatEntity();
        intentChatEntity.setAcceptName(nickName);
        intentChatEntity.setAcceptId(userId);
        intentChatEntity.setChatType(ChatType.C2C);
        if (applyType == NUM_4) {
            MyApp.quiteActivity();
        }
        ChatScreenActivity.getJumpChatSource(ApplyAffirmActivity.this, intentChatEntity);
        finish();
    }

    @Override
    public void onCheckAddFriend(BaseEntity baseEntity) {
        ImToastUtil.showShortToast(MyApp.getInstance(), baseEntity.getMsg());
        if (baseEntity.getCode() == NUM_0) {
            finish();
        }
    }


    @Override
    public void onCheckJoinGroup(BaseEntity baseEntity) {
        ImToastUtil.showShortToast(MyApp.getInstance(), baseEntity.getMsg());
        if (baseEntity.getCode() == NUM_0) {
            finish();
        }
    }

    @Override
    public void onSelectFriendInfo(SelectFriendEntity selectFriendEntity) {
        if (selectFriendEntity.getCode() == NUM_0) {
            SelectFriendEntity.ContentBean.UserChatBean friendInfoEntity = selectFriendEntity.getContent().getUserChat();


            Glide.with(MyApp.getInstance())
                    .load(friendInfoEntity.getUserIcon())
                    .error(R.mipmap.icon_default)
                    .into(groupIconView);
            if (MAN.equals(friendInfoEntity.getSex())) {
                imgApplySex.setBackground(ContextCompat.getDrawable(ApplyAffirmActivity.this, R.drawable.icon_man));
            } else {
                imgApplySex.setBackground(ContextCompat.getDrawable(ApplyAffirmActivity.this, R.drawable.icon_woman));
            }
            if (TextUtils.isEmpty(friendInfoEntity.getRemark())) {
                txtApplyNick.setVisibility(View.GONE);
                txtCommentName.setText(String.valueOf(friendInfoEntity.getNickName()));
                txtApplyComment.setText(friendInfoEntity.getNickName());

            } else {
                txtApplyNick.setText("昵称: " + String.valueOf(friendInfoEntity.getNickName()));
                txtCommentName.setText(String.valueOf(friendInfoEntity.getRemark()));
                txtApplyComment.setText(friendInfoEntity.getRemark());
                this.friendInfoEntity.setRemark(friendInfoEntity.getRemark());
            }
            txtApplyAddress.setText(String.valueOf(friendInfoEntity.getAddress()));
        }
    }

    @Override
    public void onFindUser(List<FindUserEntity.ContentBean> contentBeanList) {
        if (null == contentBeanList || contentBeanList.size() == 0) {
            return;
        }
        FindUserEntity.ContentBean friendInfoEntity = contentBeanList.get(0);

        Glide.with(MyApp.getInstance())
                .load(friendInfoEntity.getUserIcon())
                .error(R.mipmap.icon_default)
                .into(groupIconView);
        if (MAN.equals(friendInfoEntity.getSex())) {
            imgApplySex.setBackground(ContextCompat.getDrawable(ApplyAffirmActivity.this, R.drawable.icon_man));
        } else {
            imgApplySex.setBackground(ContextCompat.getDrawable(ApplyAffirmActivity.this, R.drawable.icon_woman));
        }
        txtApplyComment.setText(friendInfoEntity.getNickName());
        txtApplyAddress.setText(friendInfoEntity.getAddress());
    }


    @Subscribe(code = RxBusConstants.CONST_UPDATE_NAME, threadMode = ThreadMode.MAIN)
    public void messageReceive(String titleName) {
        if (titleName != null) {
            txtApplyNick.setVisibility(View.VISIBLE);
            txtApplyNick.setText(String.valueOf("昵称: " + friendInfoEntity.getNickName()));
            txtCommentName.setText(titleName);
            txtApplyComment.setText(titleName);
        }
    }

    @Override
    public void selectReationBetweenThePersons(BaseEntity baseEntity) {
        if (baseEntity.getCode() == NUM_0) {
            if (baseEntity.getContent().equals(String.valueOf(0))) {
                mSelectFriendInfoModel.selectFriendInfo(friendInfoEntity.getUserId());
            }
        }
    }
}
