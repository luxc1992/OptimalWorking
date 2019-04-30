package com.yst.im.imchatlibrary.ui.activity;

import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.yst.im.imchatlibrary.MyApp;
import com.yst.im.imchatlibrary.R;
import com.yst.im.imchatlibrary.base.BaseActivity;
import com.yst.im.imchatlibrary.base.baseview.AbstractTitleView;
import com.yst.im.imchatlibrary.bean.BaseEntity;
import com.yst.im.imchatlibrary.bean.FriendInfoEntity;
import com.yst.im.imchatlibrary.bean.IntentGroupMemberEntity;
import com.yst.im.imchatlibrary.bean.SelectFriendEntity;
import com.yst.im.imchatlibrary.enumclass.GroupMemberEnum;
import com.yst.im.imchatlibrary.model.DeleteSingleChatMessageModel;
import com.yst.im.imchatlibrary.model.FriendStickModel;
import com.yst.im.imchatlibrary.model.SelectFriendInfoModel;
import com.yst.im.imchatlibrary.model.ShieldSingleChatModel;
import com.yst.im.imchatlibrary.utils.ImToastUtil;
import com.yst.im.imchatlibrary.widget.AbstractImSmsDialog;
import com.yst.im.imchatlibrary.widget.NoScrollGridView;
import com.yst.im.imsdk.ChatType;
import com.yst.im.imsdk.bean.MessageBean;
import com.yst.im.imsdk.bean.RxBusEntity;
import com.yst.im.imsdk.dao.MessageDaoUtils;
import com.yst.im.imsdk.utils.RxBusConstants;
import com.zhy.adapter.abslistview.CommonAdapter;
import com.zhy.adapter.abslistview.ViewHolder;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ch.ielse.view.SwitchView;
import gorden.rxbus2.RxBus;
import gorden.rxbus2.Subscribe;
import gorden.rxbus2.ThreadMode;

import static com.yst.im.imsdk.MessageConstant.GROUP_ADD;
import static com.yst.im.imsdk.MessageConstant.GROUP_DELETE;
import static com.yst.im.imsdk.MessageConstant.NUM_0;
import static com.yst.im.imsdk.MessageConstant.NUM_1;
import static com.yst.im.imsdk.MessageConstant.NUM_2;
import static com.yst.im.imsdk.MessageConstant.NUM_3;
import static com.yst.im.imsdk.MessageConstant.TYPE_DELETE_USER;
import static com.yst.im.imsdk.MessageConstant.TYPE_OUTLOGIN;

/**
 * 个人信息
 *
 * @author Lierpeng
 * @version 1.0.0
 * @date 2018/4/2.
 */
public class PersonalInfoActivity extends BaseActivity implements
        FriendStickModel.FriendStickListenerCallBack,
        SelectFriendInfoModel.SelectFriendInfoCallBack,
        ShieldSingleChatModel.ShieldSingleChatListenerCallBack,
        DeleteSingleChatMessageModel.DeleteSingleChatMessageCallBack {

    private AbstractTitleView mTitleViewPersonalTitle;
    private NoScrollGridView mGvPersonalMemberList;
    private SwitchView mSvPersonalSettingTop;
    private SwitchView mSvPersonalSettingDisturb;
    private TextView mTxtPersonalClearHistory;
    private String userId;
    private CommonAdapter<SelectFriendEntity.ContentBean> commonAdapter;
    private List<SelectFriendEntity.ContentBean> friendInfo;
    private FriendStickModel friendStickModel;
    private SelectFriendInfoModel mSelectFriendInfoModel;
    private ShieldSingleChatModel mShieldSingleChatModel;
    private DeleteSingleChatMessageModel mDeleteSingleChatMessageModel;
    private Integer state = NUM_0;

    @Override
    protected int getLayout() {
        return R.layout.activity_personal_info;
    }

    @Override
    protected int getStatusColor() {
        return 0;
    }

    @Override
    protected void initView() {
        MyApp.addActivity(this);
        mTitleViewPersonalTitle = (AbstractTitleView) findViewById(R.id.titleView_personal_title);
        mGvPersonalMemberList = (NoScrollGridView) findViewById(R.id.gv_personal_member_list);
        mSvPersonalSettingTop = (SwitchView) findViewById(R.id.sv_personal_setting_top);
        mSvPersonalSettingDisturb = (SwitchView) findViewById(R.id.sv_personal_setting_disturb);
        mTxtPersonalClearHistory = (TextView) findViewById(R.id.txt_personal_clear_history);
        mTitleViewPersonalTitle.setTitleText("聊天详情");
        mTitleViewPersonalTitle.getLeftBackImageTv().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        mSvPersonalSettingTop.setOnStateChangedListener(new SwitchView.OnStateChangedListener() {
            @Override
            public void toggleToOn(SwitchView view) {
                state = NUM_0;
                friendStickModel.getFriendStick(String.valueOf(state), userId);
            }

            @Override
            public void toggleToOff(SwitchView view) {
                state = NUM_1;
                friendStickModel.getFriendStick(String.valueOf(state), userId);
            }
        });


        mGvPersonalMemberList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (position == NUM_1) {
                    IntentGroupMemberEntity intentGroupMemberEntity = new IntentGroupMemberEntity();
                    intentGroupMemberEntity.setGroupMemberEnum(GroupMemberEnum.created);
                    intentGroupMemberEntity.setTitleName(getResources().getString(R.string.txt_choose_groups));
                    intentGroupMemberEntity.setMemberId(userId);
                    intentGroupMemberEntity.setChatType(ChatType.C2C);
                    GroupMemberActivity.getJumpGroupMember(PersonalInfoActivity.this, intentGroupMemberEntity);
                } else {
                    SelectFriendEntity.ContentBean.UserChatBean contentBean = friendInfo.get(0).getUserChat();
                    FriendInfoEntity friendInfoEntity = new FriendInfoEntity();
                    friendInfoEntity.setUserId(contentBean.getUserId());
                    friendInfoEntity.setNickName(contentBean.getNickName());
                    friendInfoEntity.setUserIcon(contentBean.getUserIcon());
                    friendInfoEntity.setUserType(contentBean.getUserType());
                    friendInfoEntity.setAddress(contentBean.getAddress());
                    friendInfoEntity.setPhone(contentBean.getPhone());
                    friendInfoEntity.setSex(contentBean.getSex());
                    friendInfoEntity.setRemark(contentBean.getRemark());
                    ApplyAffirmActivity.getJumpApplyAffirmActivity(PersonalInfoActivity.this, 2, friendInfoEntity);

                }
            }
        });

        mSvPersonalSettingDisturb.setOnStateChangedListener(new SwitchView.OnStateChangedListener() {
            @Override
            public void toggleToOn(SwitchView view) {
                state = NUM_0;
                mShieldSingleChatModel.setShieldSingleChat(userId, String.valueOf(state));
            }

            @Override
            public void toggleToOff(SwitchView view) {
                state = NUM_1;
                mShieldSingleChatModel.setShieldSingleChat(userId, String.valueOf(state));
            }
        });

        mTxtPersonalClearHistory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AbstractImSmsDialog abstractImSmsDialog=new AbstractImSmsDialog(PersonalInfoActivity.this,false) {
                    @Override
                    public void sureClick() {
                        super.sureClick();
                        mDeleteSingleChatMessageModel.getDeleteSingleChatMessage(userId, 0);
                        MessageDaoUtils.deleteInTx(MyApp.manager.getId() + userId);
                    }
                };
                abstractImSmsDialog.setTextColor(ContextCompat.getColor(PersonalInfoActivity.this,R.color.colorBlck666),ContextCompat.getColor(PersonalInfoActivity.this,R.color.colorBlck666),ContextCompat.getColor(PersonalInfoActivity.this,R.color.colorBlck666));
                abstractImSmsDialog.setText("是否要清除聊天记录","确定","取消");
                abstractImSmsDialog.showDialog();
            }
        });
    }

    @Override
    protected boolean getStatus() {
        return false;
    }

    @Override
    protected void initData() {
        friendInfo = new ArrayList<>();
        for (int i = 0; i < NUM_1; i++) {
            SelectFriendEntity.ContentBean mSelectFriendEntity = new SelectFriendEntity.ContentBean();
            SelectFriendEntity.ContentBean.UserChatBean mUserChatBean = new SelectFriendEntity.ContentBean.UserChatBean();
            mUserChatBean.setUserIcon("http://");
            if (i == NUM_1) {
                mUserChatBean.setUserIcon(GROUP_ADD);
            }
            mSelectFriendEntity.setUserChat(mUserChatBean);
            friendInfo.add(mSelectFriendEntity);
        }
        mGvPersonalMemberList.setAdapter(getGroupListAdapter(friendInfo));
        friendStickModel = new FriendStickModel(this);
        friendStickModel.setFriendStickListenerCallBack(this);

        mSelectFriendInfoModel = new SelectFriendInfoModel(this);
        mSelectFriendInfoModel.setSelectFriendInfoCallBack(this);

        mShieldSingleChatModel = new ShieldSingleChatModel(this);
        mShieldSingleChatModel.setShieldSingleChatListenerCallBack(this);

        mDeleteSingleChatMessageModel = new DeleteSingleChatMessageModel(this);
        mDeleteSingleChatMessageModel.setDeleteSingleChatMessageCallBack(this);

        userId = getIntent().getStringExtra("userId");
        mSelectFriendInfoModel.selectFriendInfo(userId);
    }


    /**
     * 获取适配器
     */
    private CommonAdapter getGroupListAdapter(final List<SelectFriendEntity.ContentBean> friendInfo) {
        commonAdapter = new CommonAdapter<SelectFriendEntity.ContentBean>(
                PersonalInfoActivity.this, R.layout.item_grid_news_more, friendInfo) {
            @Override
            protected void convert(ViewHolder viewHolder, SelectFriendEntity.ContentBean item, int position) {
                if(null !=item.getUserChat().getRemark()){
                    viewHolder.setText(R.id.txt_news_more_text, item.getUserChat().getRemark());
                }else {
                    viewHolder.setText(R.id.txt_news_more_text, item.getUserChat().getNickName());
                }
                ImageView view = viewHolder.getView(R.id.img_news_more_pic);
                Glide.with(PersonalInfoActivity.this)
                        .load(item.getUserChat().getUserIcon())
                        .error(R.mipmap.icon_default)
                        .into(view);
                if (GROUP_ADD.equals(item.getUserChat().getUserIcon())) {
                    Glide.with(PersonalInfoActivity.this)
                            .load(R.drawable.icon_group_add)
                            .error(R.mipmap.icon_default)
                            .into(view);
                }
            }
        };
        return commonAdapter;
    }

    /**
     * 好友详情
     */
    @Override
    public void onSelectFriendInfo(SelectFriendEntity selectFriendEntity) {
        if (selectFriendEntity.getCode() == NUM_0) {
            friendInfo.clear();
            friendInfo.add(selectFriendEntity.getContent());
            SelectFriendEntity.ContentBean mSelectFriendEntity = new SelectFriendEntity.ContentBean();
            SelectFriendEntity.ContentBean.UserChatBean mUserChatBean = new SelectFriendEntity.ContentBean.UserChatBean();
            mUserChatBean.setUserIcon(GROUP_ADD);
            mSelectFriendEntity.setUserChat(mUserChatBean);
//            friendInfo.add(mSelectFriendEntity);
            mGvPersonalMemberList.setAdapter(getGroupListAdapter(friendInfo));

            if (selectFriendEntity.getContent().getIsStick() == NUM_0) {
                mSvPersonalSettingTop.setOpened(true);
            }
            if (selectFriendEntity.getContent().getIsShield() == NUM_0) {
                mSvPersonalSettingDisturb.setOpened(true);
            }
        } else {
            ImToastUtil.showShortToast(this, selectFriendEntity.getMsg());
        }
    }

    /**
     * 消息回调
     *
     * @param msgInfo 实体类
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void messageReceive(MessageBean msgInfo) {
        if (msgInfo == null) {
            return;
        }
        /** 处理不用存储的通知 */
        if (msgInfo.getEvent() == NUM_1 || msgInfo.getEvent() == NUM_3) {
            switch (msgInfo.getType()) {
                case TYPE_DELETE_USER:
                    MyApp.quiteActivity();
                    finish();
                    break;
                default:
                    break;
            }
        }
    }

    /**
     * 好友置顶
     */
    @Override
    public void onFriendStick(BaseEntity baseEntity) {
        ImToastUtil.showShortToast(this, baseEntity.getMsg());
        if (baseEntity.getCode() == NUM_0) {
            if (state == NUM_0) {
                mSvPersonalSettingTop.setOpened(true);
            } else {
                mSvPersonalSettingTop.setOpened(false);
            }
        }
    }

    /**
     * 屏蔽好友
     */
    @Override
    public void onShieldSingleChat(BaseEntity baseEntity) {
        ImToastUtil.showShortToast(this, baseEntity.getMsg());
        if (baseEntity.getCode() == NUM_0) {
            if (state == NUM_0) {
                mSvPersonalSettingDisturb.setOpened(true);
            } else {
                mSvPersonalSettingDisturb.setOpened(false);
            }
        }
    }


    @Override
    public void onDeleteSingleChat(BaseEntity baseEntity, int position) {
        if (baseEntity.getCode() == NUM_0) {
            ImToastUtil.showShortToast(PersonalInfoActivity.this, "清空聊天记录成功");
            RxBusEntity rxBusEntity = new RxBusEntity();
            rxBusEntity.setCode(RxBusConstants.CONST_LOCAL_UPDATE);
            RxBus.get().send(RxBusConstants.CONST_LOCAL_UPDATE, rxBusEntity);
        }
    }
}
