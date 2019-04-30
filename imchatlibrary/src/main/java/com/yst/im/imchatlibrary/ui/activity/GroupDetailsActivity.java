package com.yst.im.imchatlibrary.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.yst.im.imchatlibrary.MyApp;
import com.yst.im.imchatlibrary.R;
import com.yst.im.imchatlibrary.base.BaseActivity;
import com.yst.im.imchatlibrary.bean.AlterNameEntity;
import com.yst.im.imchatlibrary.bean.BaseEntity;
import com.yst.im.imchatlibrary.bean.FriendInfoEntity;
import com.yst.im.imchatlibrary.bean.GroupDetailsEntity;
import com.yst.im.imchatlibrary.bean.IntentGroupEntity;
import com.yst.im.imchatlibrary.bean.IntentGroupMemberEntity;
import com.yst.im.imchatlibrary.bean.SelectGroupStateEntity;
import com.yst.im.imchatlibrary.enumclass.AlterNameEnum;
import com.yst.im.imchatlibrary.enumclass.GroupMemberEnum;
import com.yst.im.imchatlibrary.enumclass.IntentGroupDetail;
import com.yst.im.imchatlibrary.model.DeleteGroupChatMessageModel;
import com.yst.im.imchatlibrary.model.DissolveGroupChatModel;
import com.yst.im.imchatlibrary.model.FindGroupDetailModel;
import com.yst.im.imchatlibrary.model.GroupStickModel;
import com.yst.im.imchatlibrary.model.QuitGroupModel;
import com.yst.im.imchatlibrary.model.SelectGroupStateModel;
import com.yst.im.imchatlibrary.model.SelectReationBetweenThePersonsModel;
import com.yst.im.imchatlibrary.model.ShieldGroupChatModel;
import com.yst.im.imchatlibrary.utils.ImToastUtil;
import com.yst.im.imchatlibrary.widget.AbstractImSmsDialog;
import com.yst.im.imchatlibrary.widget.NoScrollGridView;
import com.yst.im.imsdk.IntentType;
import com.yst.im.imsdk.ShowType;
import com.yst.im.imsdk.bean.MessageBean;
import com.yst.im.imsdk.bean.RxBusEntity;
import com.yst.im.imsdk.dao.MessageDaoUtils;
import com.yst.im.imsdk.utils.RxBusConstants;
import com.zhy.adapter.abslistview.CommonAdapter;
import com.zhy.adapter.abslistview.ViewHolder;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import ch.ielse.view.SwitchView;
import gorden.rxbus2.RxBus;
import gorden.rxbus2.Subscribe;
import gorden.rxbus2.ThreadMode;

import static com.yst.im.imchatlibrary.ui.activity.SettingUserNameActivity.INTENT_SETTING_NAME;
import static com.yst.im.imsdk.MessageConstant.GROUP_ADD;
import static com.yst.im.imsdk.MessageConstant.GROUP_DELETE;
import static com.yst.im.imsdk.MessageConstant.NUM_0;
import static com.yst.im.imsdk.MessageConstant.NUM_1;
import static com.yst.im.imsdk.MessageConstant.NUM_2;
import static com.yst.im.imsdk.MessageConstant.NUM_3;
import static com.yst.im.imsdk.MessageConstant.NUM_58;
import static com.yst.im.imsdk.MessageConstant.NUM_59;
import static com.yst.im.imsdk.MessageConstant.NUM_60;
import static com.yst.im.imsdk.MessageConstant.NUM_61;
import static com.yst.im.imsdk.MessageConstant.NUM_65;
import static com.yst.im.imsdk.MessageConstant.NUM_7;
import static com.yst.im.imsdk.MessageConstant.TYPE_OUTLOGIN;

/**
 * 群聊详情Activity
 *
 * @author Lierpeng
 * @version 1.0.0
 * @date 2018/4/4.
 */
public class GroupDetailsActivity extends BaseActivity implements View.OnClickListener,
        FindGroupDetailModel.FindGroupDetailListenerCallBack,
        GroupStickModel.GroupStickListenerCallBack,
        ShieldGroupChatModel.ShieldGroupChatListenerCallBack,
        SelectGroupStateModel.SelectGroupStateListenerCallBack,
        DissolveGroupChatModel.DissolveGroupChatListenerCallBack,
        QuitGroupModel.QuitGroupCallBack,
        DeleteGroupChatMessageModel.DeleteGroupChatMessageCallBack,
        SelectReationBetweenThePersonsModel.selectReationBetweenThePersonsCallBack {
    private ImageView mImgGroupDetailBack;
    private TextView mTxtGroupDetailName;
    private TextView mTxtGroupDetailNum;
    private TextView mTxtGroupDetailGroupName;
    private NoScrollGridView mGvGroupDetailMemberList;
    private TextView mTxtGroupDetailMoreMember;
    private TextView mTxtGroupDetailMoreTopic;
    private SwitchView mSvGroupDetailSettingTop;
    private SwitchView mSvGroupDetailSettingDisturb;
    private RelativeLayout mRelGroupDetailUpdateNickname;
    private TextView mTxtGroupDetailNickname;
    private TextView mTxtGroupDetailClearHistory;
    private Button mBtnGroupDetailJoin;
    private FindGroupDetailModel mFindGroupDetailModel;
    private RelativeLayout mRelGroupDetailName;
    private LinearLayout llAddGroup;
    private CommonAdapter<GroupDetailsEntity.GroupsBean> commonAdapter;
    private List<GroupDetailsEntity.GroupsBean> groups = new ArrayList<>();
    private GroupStickModel mGroupStickModel;
    private ShieldGroupChatModel mShieldGroupChatModel;
    private SelectGroupStateModel mSelectGroupStateModel;
    private GroupDetailsEntity groupDetailsEntity;
    private DissolveGroupChatModel mDissolveGroupChatModel;
    private DeleteGroupChatMessageModel mDeleteGroupChatMessageModel;
    private QuitGroupModel mQuitGroupModel;
    private SelectReationBetweenThePersonsModel mSelectReationBetweenThePersonsModel;
    private IntentGroupEntity intentGroupEntity;
    private int relation;
    private String remark;
    private String userId = "";
    private String userName = "";
    private String remarkName = "";

    @Override
    public void onClick(View v) {
        int i = v.getId();
        if (i == R.id.img_group_detail_back) {
            finish();
        } else if (i == R.id.txt_group_detail_more_member) {
            startActivity(new Intent(GroupDetailsActivity.this, MoreGroupMembersActivity.class)
                    .putExtra("groupEntity", groupDetailsEntity)
                    .putExtra("relation", String.valueOf(relation))
            );
        } else if (i == R.id.txt_group_detail_more_topic) {
            startActivity(new Intent(GroupDetailsActivity.this, GroupNoticeActivity.class).putExtra("groupEntity", groupDetailsEntity));
        } else if (i == R.id.rel_group_detail_name) {
            if (relation == NUM_0) {
                AlterNameEntity alterNameEntity = new AlterNameEntity();
                alterNameEntity.setRightName(getResources().getString(R.string.txt_finish));
                alterNameEntity.setTips(getResources().getString(R.string.txt_group_group_name));
                alterNameEntity.setTitleName(getResources().getString(R.string.txt_group_update_name));
                alterNameEntity.setLeftName(getResources().getString(R.string.cancle));
                alterNameEntity.setAlterNameEnum(AlterNameEnum.GroupName);
                alterNameEntity.setName(groupDetailsEntity.getGroupName());
                alterNameEntity.setId(intentGroupEntity.getGroupId());
                UpdateNameActivity.getJumpSource(GroupDetailsActivity.this, alterNameEntity);
            } else {
                ImToastUtil.showShortToast(this, "只有群主才能修改群名");
            }
        } else if (i == R.id.rel_group_detail_update_nickname) {
            AlterNameEntity alterNameEntity = new AlterNameEntity();
            alterNameEntity.setRightName(getResources().getString(R.string.txt_finish));
            alterNameEntity.setTips(getResources().getString(R.string.txt_group_remark));
            alterNameEntity.setTitleName(getResources().getString(R.string.txt_group_mine_name));
            alterNameEntity.setLeftName(getResources().getString(R.string.cancle));
            alterNameEntity.setName(remark);
            alterNameEntity.setId(intentGroupEntity.getGroupId());
            alterNameEntity.setAlterNameEnum(AlterNameEnum.NickNameInGroup);
            UpdateNameActivity.getJumpSource(GroupDetailsActivity.this, alterNameEntity);

        } else if (i == R.id.txt_group_detail_clear_history) {

            AbstractImSmsDialog abstractImSmsDialog = new AbstractImSmsDialog(GroupDetailsActivity.this, false) {
                @Override
                public void sureClick() {
                    super.sureClick();
                    mDeleteGroupChatMessageModel.getDeleteGroupChatMessage(intentGroupEntity.getGroupId(), NUM_0);
                    MessageDaoUtils.deleteInTx(MyApp.manager.getId() + intentGroupEntity.getGroupId());
                }
            };
            abstractImSmsDialog.setTextColor(ContextCompat.getColor(this, R.color.colorBlck666), ContextCompat.getColor(this, R.color.colorBlck666), ContextCompat.getColor(this, R.color.colorBlck666));
            abstractImSmsDialog.setText("是否要清除聊天记录", "确定", "取消");
            abstractImSmsDialog.showDialog();

        } else if (i == R.id.btn_group_detail_join) {
            if (relation == NUM_0) {
                mDissolveGroupChatModel.getDissolveGroupChat(intentGroupEntity.getGroupId());
            } else if (relation == NUM_1) {
                mQuitGroupModel.getQuitGroup(intentGroupEntity.getGroupId());
            } else if (relation == NUM_2) {
                SettingUserNameActivity.getJumpSettingUserNameActivity(GroupDetailsActivity.this,
                        IntentType.startActivity, ShowType.GroupDescrb, INTENT_SETTING_NAME, intentGroupEntity.getGroupId(), "入群验证");
            }
        }
    }

    public static void getJumpGroup(Activity activity, IntentGroupEntity intentGroupEntity) {
        Intent intent = new Intent(activity, GroupDetailsActivity.class);
        intent.putExtra("intentGroupEntity", intentGroupEntity);
        activity.startActivity(intent);
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_group_details;
    }

    @Override
    protected int getStatusColor() {
        return 0;
    }

    @Override
    protected void initView() {
        MyApp.addActivity(this);
        mImgGroupDetailBack = (ImageView) findViewById(R.id.img_group_detail_back);
        mTxtGroupDetailName = (TextView) findViewById(R.id.txt_group_detail_name);
        mTxtGroupDetailNum = (TextView) findViewById(R.id.txt_group_detail_num);
        mGvGroupDetailMemberList = (NoScrollGridView) findViewById(R.id.gv_group_detail_member_list);
        mTxtGroupDetailMoreMember = (TextView) findViewById(R.id.txt_group_detail_more_member);
        mRelGroupDetailName = (RelativeLayout) findViewById(R.id.rel_group_detail_name);
        mTxtGroupDetailGroupName = (TextView) findViewById(R.id.txt_group_detail_group_name);
        mTxtGroupDetailMoreTopic = (TextView) findViewById(R.id.txt_group_detail_more_topic);
        mSvGroupDetailSettingTop = (SwitchView) findViewById(R.id.sv_group_detail_setting_top);
        mSvGroupDetailSettingDisturb = (SwitchView) findViewById(R.id.sv_group_detail_setting_disturb);
        mRelGroupDetailUpdateNickname = (RelativeLayout) findViewById(R.id.rel_group_detail_update_nickname);
        mTxtGroupDetailNickname = (TextView) findViewById(R.id.txt_group_detail_nickname);
        mTxtGroupDetailClearHistory = (TextView) findViewById(R.id.txt_group_detail_clear_history);
        mBtnGroupDetailJoin = (Button) findViewById(R.id.btn_group_detail_join);
        llAddGroup = (LinearLayout) findViewById(R.id.ll_add_group);
        mImgGroupDetailBack.setOnClickListener(this);
        mTxtGroupDetailMoreMember.setOnClickListener(this);
        mTxtGroupDetailMoreTopic.setOnClickListener(this);
        mRelGroupDetailUpdateNickname.setOnClickListener(this);
        mTxtGroupDetailClearHistory.setOnClickListener(this);
        mBtnGroupDetailJoin.setOnClickListener(this);
        mRelGroupDetailName.setOnClickListener(this);
    }

    @Override
    protected boolean getStatus() {
        return false;
    }

    @Override
    protected void initData() {
        intentGroupEntity = (IntentGroupEntity) getIntent().getSerializableExtra("intentGroupEntity");
        mFindGroupDetailModel = new FindGroupDetailModel(this);
        mFindGroupDetailModel.setFindGroupDetailListenerCallBack(this);
        mSelectReationBetweenThePersonsModel = new SelectReationBetweenThePersonsModel(this);
        mSelectReationBetweenThePersonsModel.setselectReationBetweenThePersonsCallBack(this);
        mSelectGroupStateModel = new SelectGroupStateModel(this);
        mSelectGroupStateModel.setSelectGroupStateListenerCallBack(this);
        mGroupStickModel = new GroupStickModel(this);
        mGroupStickModel.setGroupStickListenerCallBack(this);
        mShieldGroupChatModel = new ShieldGroupChatModel(this);
        mShieldGroupChatModel.setFindUserListenerCallBack(this);
        mDissolveGroupChatModel = new DissolveGroupChatModel(this);
        mDissolveGroupChatModel.setDissolveGroupChatListenerCallBack(this);
        mQuitGroupModel = new QuitGroupModel(this);
        mQuitGroupModel.setQuitGroupCallBack(this);
        mDeleteGroupChatMessageModel = new DeleteGroupChatMessageModel(this);
        mDeleteGroupChatMessageModel.setDeleteGroupChatMessageCallBack(this);

        mGvGroupDetailMemberList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                GroupDetailsEntity.GroupsBean contentBean = groups.get(position);
                userId = contentBean.getUserId();
                userName = contentBean.getNickName();
                remarkName = contentBean.getNickName();
                if (relation == NUM_0) {
                    if (position == commonAdapter.getCount() - 1) {
                        IntentGroupMemberEntity intentGroupMemberEntity = new IntentGroupMemberEntity();
                        intentGroupMemberEntity.setGroupId(String.valueOf(groupDetailsEntity.getId()));
                        intentGroupMemberEntity.setGroupMemberEnum(GroupMemberEnum.deleted);
                        intentGroupMemberEntity.setTitleName(getResources().getString(R.string.txt_delete_groups));
                        GroupMemberActivity.getJumpGroupMember(GroupDetailsActivity.this, intentGroupMemberEntity);
                    } else if (position == commonAdapter.getCount() - NUM_2) {
                        IntentGroupMemberEntity intentGroupMemberEntity = new IntentGroupMemberEntity();
                        intentGroupMemberEntity.setGroupId(String.valueOf(groupDetailsEntity.getId()));
                        intentGroupMemberEntity.setGroupMemberEnum(GroupMemberEnum.Invited);
                        intentGroupMemberEntity.setTitleName(getResources().getString(R.string.txt_choose_groups));
                        GroupMemberActivity.getJumpGroupMember(GroupDetailsActivity.this, intentGroupMemberEntity);
                    } else {
                        if (MyApp.manager.getId().equals(contentBean.getUserId())) {
                            return;
                        }
                        mSelectReationBetweenThePersonsModel.selectReationBetweenThePersons(userId);
                    }
                } else if (relation == NUM_1) {
                    if (position == commonAdapter.getCount() - 1) {
                        IntentGroupMemberEntity intentGroupMemberEntity = new IntentGroupMemberEntity();
                        intentGroupMemberEntity.setGroupId(String.valueOf(groupDetailsEntity.getId()));
                        intentGroupMemberEntity.setGroupMemberEnum(GroupMemberEnum.Invited);
                        intentGroupMemberEntity.setTitleName(getResources().getString(R.string.txt_choose_groups));
                        GroupMemberActivity.getJumpGroupMember(GroupDetailsActivity.this, intentGroupMemberEntity);
                    } else {
                        if (MyApp.manager.getId().equals(contentBean.getUserId())) {
                            return;
                        }
                        mSelectReationBetweenThePersonsModel.selectReationBetweenThePersons(userId);
                    }
                } else {
                    if (MyApp.manager.getId().equals(contentBean.getUserId())) {
                        return;
                    }
                    mSelectReationBetweenThePersonsModel.selectReationBetweenThePersons(userId);
                }
            }
        });

        if (intentGroupEntity.getIntentGroupDetail().equals(IntentGroupDetail.OutGroup)) {
            llAddGroup.setVisibility(View.GONE);
        } else {
            llAddGroup.setVisibility(View.VISIBLE);
            mSvGroupDetailSettingTop.setOnStateChangedListener(new SwitchView.OnStateChangedListener() {
                @Override
                public void toggleToOn(SwitchView view) {
                    view.toggleSwitch(true);
                    ImToastUtil.showShortToast(GroupDetailsActivity.this, "置顶");
                    try {
                        mGroupStickModel.getGroupStick(String.valueOf(0), intentGroupEntity.getGroupId());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void toggleToOff(SwitchView view) {
                    view.toggleSwitch(false);
                    ImToastUtil.showShortToast(GroupDetailsActivity.this, "取消置顶");
                    try {
                        mGroupStickModel.getGroupStick(String.valueOf(1), intentGroupEntity.getGroupId());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            });

            mSvGroupDetailSettingDisturb.setOnStateChangedListener(new SwitchView.OnStateChangedListener() {
                @Override
                public void toggleToOn(SwitchView view) {
                    view.toggleSwitch(true);
                    ImToastUtil.showShortToast(GroupDetailsActivity.this, "屏蔽");
                    mShieldGroupChatModel.getShieldGroup(String.valueOf(0), intentGroupEntity.getGroupId());
                }

                @Override
                public void toggleToOff(SwitchView view) {
                    view.toggleSwitch(false);
                    ImToastUtil.showShortToast(GroupDetailsActivity.this, "取消屏蔽");
                    mShieldGroupChatModel.getShieldGroup(String.valueOf(1), intentGroupEntity.getGroupId());
                }
            });
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        mFindGroupDetailModel.getFindGroupDetail(intentGroupEntity.getGroupId());
    }

    /**
     * 获取适配器
     */
    private CommonAdapter getGroupListAdapter(final List<GroupDetailsEntity.GroupsBean> groups) {
        commonAdapter = new CommonAdapter<GroupDetailsEntity.GroupsBean>(
                GroupDetailsActivity.this, R.layout.item_grid_news_more, groups) {
            @Override
            protected void convert(ViewHolder viewHolder, GroupDetailsEntity.GroupsBean item, int position) {
                viewHolder.setText(R.id.txt_news_more_text, item.getNickName());
                ImageView view = viewHolder.getView(R.id.img_news_more_pic);
                Glide.with(GroupDetailsActivity.this)
                        .load(item.getUserIcon())
                        .error(R.mipmap.icon_default)
                        .into(view);
                if (GROUP_ADD.equals(item.getUserIcon())) {
                    Glide.with(GroupDetailsActivity.this)
                            .load(R.drawable.icon_group_add)
                            .error(R.mipmap.icon_default)
                            .into(view);
                }
                if (GROUP_DELETE.equals(item.getUserIcon())) {
                    Glide.with(GroupDetailsActivity.this)
                            .load(R.drawable.icon_group_delete)
                            .error(R.mipmap.icon_default)
                            .into(view);
                }
            }
        };
        return commonAdapter;
    }

    @Override
    public void onFindGroupDetail(GroupDetailsEntity groupDetailsEntity) {
        groups.clear();
        mSelectGroupStateModel.getSelectGroupState(intentGroupEntity.getGroupId());
        mTxtGroupDetailGroupName.setText(groupDetailsEntity.getGroupName());
        this.groupDetailsEntity = groupDetailsEntity;
        if (!TextUtils.isEmpty(groupDetailsEntity.getDescrbe())) {
            if (groupDetailsEntity.getDescrbe().length() > NUM_60) {
                String topic = groupDetailsEntity.getDescrbe().substring(0, NUM_61) + "...";
                mTxtGroupDetailMoreTopic.setText(topic);
            } else {
                mTxtGroupDetailMoreTopic.setText(groupDetailsEntity.getDescrbe());
            }
        } else {
            mTxtGroupDetailMoreTopic.setText("");
        }

        if (groupDetailsEntity.getGroupName().length() > NUM_7) {
            String groupName = groupDetailsEntity.getGroupName().substring(0, 7);
            String name = groupName + "(" + groupDetailsEntity.getGroupNumberByCurrent() + ")";
            mTxtGroupDetailName.setText(name);
            RxBus.get().send(RxBusConstants.CONST_UPDATE_NAME, name);
        } else {
            String name = groupDetailsEntity.getGroupName() + "(" + groupDetailsEntity.getGroupNumberByCurrent() + ")";
            mTxtGroupDetailName.setText(name);
            RxBus.get().send(RxBusConstants.CONST_UPDATE_NAME, name);
        }
        groups = groupDetailsEntity.getGroups();
        for (int i = 0; i < groups.size(); i++) {
            if (groupDetailsEntity.getUserId() == groups.get(i).getId()) {
                GroupDetailsEntity.GroupsBean groupManager = groups.get(i);
                groups.remove(i);
                groups.add(0, groupManager);
            }
        }
        mGvGroupDetailMemberList.setAdapter(getGroupListAdapter(groups));
        setListViewHeightBasedOnChildren(mGvGroupDetailMemberList);
    }

    @Override
    public void onJGroupStick() {

    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }

    @Override
    public void onSelectGroupState(SelectGroupStateEntity.ContentBean selectGroupStateEntity) {
        remark = selectGroupStateEntity.getRemark();
        if (selectGroupStateEntity.getIsShield() == NUM_0) {
            mSvGroupDetailSettingDisturb.toggleSwitch(true);
        } else {
            mSvGroupDetailSettingDisturb.toggleSwitch(false);
        }
        if (selectGroupStateEntity.getIsStick() == NUM_0) {
            mSvGroupDetailSettingTop.toggleSwitch(true);
        } else {
            mSvGroupDetailSettingTop.toggleSwitch(false);
        }
        mTxtGroupDetailNickname.setText(selectGroupStateEntity.getRemark());
        relation = selectGroupStateEntity.getRelation();
        if (relation == NUM_0) {
            mBtnGroupDetailJoin.setText("退出并解散群聊");
            llAddGroup.setVisibility(View.VISIBLE);
        } else if (relation == NUM_1) {
            mBtnGroupDetailJoin.setText("退出群聊");
            llAddGroup.setVisibility(View.VISIBLE);
        } else if (relation == NUM_2) {
            llAddGroup.setVisibility(View.GONE);
            mBtnGroupDetailJoin.setText("申请加入");
        }
        if (IntentGroupDetail.InGroup.equals(intentGroupEntity.getIntentGroupDetail())) {
            GroupDetailsEntity.GroupsBean groupsBean = new GroupDetailsEntity.GroupsBean();
            groupsBean.setUserIcon("jia");
            groups.add(groupsBean);
            for (int i = 0; i < groups.size(); i++) {
                if (groups.get(i).getId() == groupDetailsEntity.getUserId() && MyApp.manager.getId().equals(groups.get(i).getUserId())) {
                    GroupDetailsEntity.GroupsBean groupDelete = new GroupDetailsEntity.GroupsBean();
                    groupDelete.setUserIcon("jian");
                    groups.add(groupDelete);
                }
            }
        }
        getGroupListAdapter(groups).notifyDataSetChanged();
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
                case TYPE_OUTLOGIN:
                    if (relation != NUM_0) {
                        MyApp.quiteActivity();
                        finish();
                    }
                    break;
                default:
                    break;
            }
        }
    }

    @Override
    public void onShieldGroup() {

    }

    @Override
    public void onDissolveGroupChat() {
        MyApp.quiteActivity();
        finish();
    }

    @Override
    public void onQuitGroup(BaseEntity baseEntity) {
        MyApp.quiteActivity();
        finish();
    }

    @Override
    public void onDeleteGroupChatMessage(BaseEntity baseEntity, int position) {
        if (baseEntity.getCode() == NUM_0) {
            ImToastUtil.showShortToast(GroupDetailsActivity.this, "清空聊天记录成功");
            RxBusEntity rxBusEntity = new RxBusEntity();
            rxBusEntity.setCode(RxBusConstants.CONST_LOCAL_UPDATE);
            RxBus.get().send(RxBusConstants.CONST_LOCAL_UPDATE, rxBusEntity);
        }
    }

    @Override
    public void selectReationBetweenThePersons(BaseEntity baseEntity) {
        if (baseEntity.getCode() == NUM_0) {
            FriendInfoEntity friendInfoEntity = new FriendInfoEntity();
            if (String.valueOf(NUM_0).equals(baseEntity.getContent())) {
                friendInfoEntity.setState(baseEntity.getContent());
                friendInfoEntity.setUserId(userId);
                friendInfoEntity.setNickName(userName);
                friendInfoEntity.setRemark(userName);
                ApplyAffirmActivity.getJumpApplyAffirmActivity(GroupDetailsActivity.this, 4, friendInfoEntity);
            } else {
                friendInfoEntity.setState(baseEntity.getContent());
                friendInfoEntity.setUserId(userId);
                friendInfoEntity.setNickName(userName);
                ApplyAffirmActivity.getJumpApplyAffirmActivity(GroupDetailsActivity.this, 4, friendInfoEntity);
            }
        }
    }

    /**
     * 动态设置ListView的高度
     *
     * @param listView
     */
    public static void setListViewHeightBasedOnChildren(GridView listView) {
        if (listView == null) {
            return;
        }
        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter == null) {
            // pre-condition
            return;
        }
        int totalHeight = 0;
        for (int i = 0; i < listAdapter.getCount(); i++) {
            View listItem = listAdapter.getView(i, null, listView);
            listItem.measure(0, 0);
            totalHeight += listItem.getMeasuredHeight();
        }
        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight;
        listView.setLayoutParams(params);
    }

}
