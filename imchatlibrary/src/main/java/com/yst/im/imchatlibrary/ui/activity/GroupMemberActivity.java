package com.yst.im.imchatlibrary.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.support.v4.content.ContextCompat;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.yst.im.imchatlibrary.MyApp;
import com.yst.im.imchatlibrary.R;
import com.yst.im.imchatlibrary.base.BaseActivity;
import com.yst.im.imchatlibrary.base.baseview.AbstractTitleView;
import com.yst.im.imchatlibrary.bean.CreateGroupChatEntity;
import com.yst.im.imchatlibrary.bean.FriendListEntity;
import com.yst.im.imchatlibrary.bean.GroupChatEntity;
import com.yst.im.imchatlibrary.bean.GroupDetailsEntity;
import com.yst.im.imchatlibrary.bean.GroupStateEntity;
import com.yst.im.imchatlibrary.bean.IntentChatEntity;
import com.yst.im.imchatlibrary.bean.IntentGroupMemberEntity;
import com.yst.im.imchatlibrary.bean.SearchFigureEntity;
import com.yst.im.imchatlibrary.bean.SearchGroupMemberEntity;
import com.yst.im.imchatlibrary.bean.SearchResultEntity;
import com.yst.im.imchatlibrary.enumclass.GroupMemberEnum;
import com.yst.im.imchatlibrary.model.CreateGroupChatModel;
import com.yst.im.imchatlibrary.model.DimSearchFigureModel;
import com.yst.im.imchatlibrary.model.DropGroupChatModel;
import com.yst.im.imchatlibrary.model.FindGroupDetailModel;
import com.yst.im.imchatlibrary.model.FindUserListModel;
import com.yst.im.imchatlibrary.model.InvitingUserModel;
import com.yst.im.imchatlibrary.model.SearchUserInGroupModel;
import com.yst.im.imchatlibrary.model.GroupInviteUserListModel;
import com.yst.im.imchatlibrary.utils.ImLog;
import com.yst.im.imchatlibrary.utils.ImToastUtil;
import com.yst.im.imchatlibrary.utils.SettingNineGroupIcon;
import com.yst.im.imchatlibrary.widget.AbstractImSmsDialog;
import com.yst.im.imchatlibrary.widget.ClearEditText;
import com.yst.im.imchatlibrary.widget.NineGridImageView;
import com.yst.im.imsdk.ChatType;
import com.zhy.adapter.abslistview.CommonAdapter;
import com.zhy.adapter.abslistview.ViewHolder;

import java.util.ArrayList;
import java.util.List;

import static com.yst.im.imsdk.MessageConstant.NUM_0;
import static com.yst.im.imsdk.MessageConstant.NUM_1;
import static com.yst.im.imsdk.utils.BaseUtils.isClickable;

/**
 * 群成员操作
 *
 * @author Lierpeng
 * @version 1.0.0
 * @date 2018/4/4.
 */
public class GroupMemberActivity extends BaseActivity implements DropGroupChatModel.DropGroupChatCallBack,
        FindGroupDetailModel.FindGroupDetailListenerCallBack, FindUserListModel.FindUserListListenerCallBack,
        GroupInviteUserListModel.GroupInviteUserListCallBack, CreateGroupChatModel.CreateGroupChatListenerCallBack,
        InvitingUserModel.InvitingListenerCallBack, SearchUserInGroupModel.SearchUserInGroupListenerCallBack,
        DimSearchFigureModel.DimSearchFigureListenerCallBack {
    private AbstractTitleView mTitleViewDeleteGroupsTitle;
    private ListView mLvDeleteGroupsChatList;
    private RelativeLayout mRelDeleteGroupsSearch;
    private LinearLayout mLilGroupMemberShow;
    private ClearEditText mEdtGroupMemberSearchEdit;
    private AbstractImSmsDialog abstractImSmsDialog;
    private IntentGroupMemberEntity mIntentGroupMemberEntity;
    private DropGroupChatModel mDropGroupChatModel;
    private FindGroupDetailModel mFindGroupDetailModel;
    //    private List<GroupDetailsEntity.GroupsBean> groups;
    private CreateGroupChatModel mCreateGroupChatModel;
    private CommonAdapter<SearchResultEntity> mCreateGroupAdapter;
    private GroupInviteUserListModel mGroupInviteUserListModel;
    private List<SearchResultEntity> searchResult = new ArrayList<>();
    private String ids = "";
    private String userName = "";
    private String deleteUserId = "";
    private String inviteUserId = "";
    private String searchName = "";
    private InvitingUserModel mInvitingUserModel;
    private SearchUserInGroupModel mSearchUserInGroupModel;
    private DimSearchFigureModel mDimSearchFigureModel;
    private List<String> mSelectId = new ArrayList<>();
    private FindUserListModel mFindUserListModel;

    @Override
    protected int getLayout() {
        return R.layout.activity_delete_groups;
    }

    @Override
    protected int getStatusColor() {
        return 0;
    }

    @Override
    protected void initView() {
        mRelDeleteGroupsSearch = (RelativeLayout) findViewById(R.id.rel_delete_groups_search);
        mTitleViewDeleteGroupsTitle = (AbstractTitleView) findViewById(R.id.titleView_delete_groups_title);
        mLvDeleteGroupsChatList = (ListView) findViewById(R.id.lv_delete_groups_chatList);
        TextView mTxtDeleteShowItemTips = (TextView) findViewById(R.id.txt_delete_show_item_tips);
        mLilGroupMemberShow = (LinearLayout) findViewById(R.id.lil_group_member_show);
        mEdtGroupMemberSearchEdit = (ClearEditText) findViewById(R.id.edt_group_member_search_edit);
        mLilGroupMemberShow.setVisibility(View.VISIBLE);
        mEdtGroupMemberSearchEdit.setVisibility(View.GONE);
        mRelDeleteGroupsSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mRelDeleteGroupsSearch.setVisibility(View.GONE);
                mEdtGroupMemberSearchEdit.setVisibility(View.VISIBLE);
            }
        });
        mTitleViewDeleteGroupsTitle.getLeftBackTextTv().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        initDeleteDialog();
        mTitleViewDeleteGroupsTitle.getRightTextTv().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isClickable()) {
                    return;
                }
                if (mIntentGroupMemberEntity.getGroupMemberEnum().equals(GroupMemberEnum.created)) {
                    if (mSelectId.size() == 0) {
                        ImToastUtil.showShortToast(GroupMemberActivity.this, "请选择联系人");
                        return;
                    } else if (mSelectId.size() == 1) {
                        IntentChatEntity intentChatEntity = new IntentChatEntity();
                        ImLog.e("ImLog", "userName = " + userName);
                        intentChatEntity.setAcceptName(userName);
                        intentChatEntity.setAcceptId(ids);
                        intentChatEntity.setChatType(ChatType.C2C);
                        ChatScreenActivity.getJumpChatSource(GroupMemberActivity.this, intentChatEntity);
                        finish();
                        return;
                    }
                    mCreateGroupChatModel.getCreateGroupChat(ids);
                } else if (mIntentGroupMemberEntity.getGroupMemberEnum().equals(GroupMemberEnum.Invited)) {
                    mInvitingUserModel.getInvitingUser(mIntentGroupMemberEntity.getGroupId(), inviteUserId);
                } else if (mIntentGroupMemberEntity.getGroupMemberEnum().equals(GroupMemberEnum.deleted)) {
                    if (mSelectId.size() == 0) {
                        ImToastUtil.showShortToast(GroupMemberActivity.this, "当前未选择成员");
                    } else {
                        abstractImSmsDialog.setTextColor(ContextCompat.getColor(GroupMemberActivity.this, R.color.colorBlack333),
                                ContextCompat.getColor(GroupMemberActivity.this, R.color.colorBlue439),
                                ContextCompat.getColor(GroupMemberActivity.this, R.color.colorBlck999));
                        abstractImSmsDialog.setText("确认要删除群成员？", getResources().getString(R.string.sure), getResources().getString(R.string.cancle));
                        abstractImSmsDialog.showDialog();
                    }
                }
            }
        });
        mTxtDeleteShowItemTips.setVisibility(View.GONE);
        mEdtGroupMemberSearchEdit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                searchName = s.toString();
                if (mIntentGroupMemberEntity.getGroupMemberEnum().equals(GroupMemberEnum.Invited)) {
                    if (searchName.length() == NUM_0) {
                        searchResult.clear();
                        mLilGroupMemberShow.setVisibility(View.VISIBLE);
                        mGroupInviteUserListModel.getGroupInviteUserList("", mIntentGroupMemberEntity.getGroupId());
                    } else {
                        mGroupInviteUserListModel.getGroupInviteUserList(searchName, mIntentGroupMemberEntity.getGroupId());
                    }
                } else if (mIntentGroupMemberEntity.getGroupMemberEnum().equals(GroupMemberEnum.deleted)) {
                    if (searchName.length() == NUM_0) {
                        searchResult.clear();
                        mLilGroupMemberShow.setVisibility(View.VISIBLE);
                        mLvDeleteGroupsChatList.setVisibility(View.VISIBLE);
                        mFindGroupDetailModel.getFindGroupDetail(mIntentGroupMemberEntity.getGroupId());
                    } else {
                        mSearchUserInGroupModel.getSearchUserInGroup(mIntentGroupMemberEntity.getGroupId(), searchName);
                    }
                } else if (mIntentGroupMemberEntity.getGroupMemberEnum().equals(GroupMemberEnum.created)) {
                    if (searchName.length() == NUM_0) {
                        searchResult.clear();
                        mFindUserListModel.getFindUserList();
                    } else {
                        mDimSearchFigureModel.getDimSearch(searchName, String.valueOf(2));
                    }
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    @Override
    protected boolean getStatus() {
        return false;
    }

    @Override
    protected void initData() {
        mSelectId.clear();
        mIntentGroupMemberEntity = (IntentGroupMemberEntity) getIntent().getSerializableExtra("intentGroupMemberEntity");
        if (null != mIntentGroupMemberEntity.getChatType() && mIntentGroupMemberEntity.getChatType().equals(ChatType.C2C)) {
            mSelectId.add(mIntentGroupMemberEntity.getMemberId());
            mTitleViewDeleteGroupsTitle.setRightTvText(NUM_0 == mSelectId.size() ? "完成" : String.valueOf("完成(" + mSelectId.size() + ")"));
        }

        if (null != mIntentGroupMemberEntity) {
            mTitleViewDeleteGroupsTitle.setTitleText(mIntentGroupMemberEntity.getTitleName());
        }
        mFindGroupDetailModel = new FindGroupDetailModel(this);
        mFindGroupDetailModel.setFindGroupDetailListenerCallBack(this);

        mDropGroupChatModel = new DropGroupChatModel(this);
        mDropGroupChatModel.setDropGroupChatCallBack(this);

        mFindUserListModel = new FindUserListModel(this);
        mFindUserListModel.setFindUserListListenerCallBack(this);

        mCreateGroupChatModel = new CreateGroupChatModel(this);
        mCreateGroupChatModel.setCreateGroupChatListenerCallBack(this);

        mGroupInviteUserListModel = new GroupInviteUserListModel(this);
        mGroupInviteUserListModel.setGroupInviteUserListCallBack(this);

        mInvitingUserModel = new InvitingUserModel(this);
        mInvitingUserModel.setInvitingListenerCallBack(this);

        mSearchUserInGroupModel = new SearchUserInGroupModel(this);
        mSearchUserInGroupModel.setSearchUserInGroupListenerCallBack(this);

        mDimSearchFigureModel = new DimSearchFigureModel(this);
        mDimSearchFigureModel.setDimSearchFigureListenerCallBack(this);

        if (mIntentGroupMemberEntity.getGroupMemberEnum().equals(GroupMemberEnum.created)) {
            mFindUserListModel.getFindUserList();
        } else if (mIntentGroupMemberEntity.getGroupMemberEnum().equals(GroupMemberEnum.Invited)) {
            mGroupInviteUserListModel.getGroupInviteUserList("", mIntentGroupMemberEntity.getGroupId());
        } else if (mIntentGroupMemberEntity.getGroupMemberEnum().equals(GroupMemberEnum.deleted)) {
            mFindGroupDetailModel.getFindGroupDetail(mIntentGroupMemberEntity.getGroupId());
        }

        mLvDeleteGroupsChatList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                if (null != mIntentGroupMemberEntity.getChatType() && mIntentGroupMemberEntity.getChatType().equals(ChatType.C2C)) {
                    mTitleViewDeleteGroupsTitle.setRightTvText(NUM_0 == mSelectId.size() ? "完成" : String.valueOf("完成(" + mSelectId.size() + ")"));
                } else {
                    mTitleViewDeleteGroupsTitle.setRightTvText("完成");
                }
                StringBuilder sb = new StringBuilder();
                if (mIntentGroupMemberEntity.getGroupMemberEnum().equals(GroupMemberEnum.deleted)) {
                    if (searchResult.size() == 0) {
                        searchResult.get(position).setChecked(!searchResult.get(position).isChecked());
                        mCreateGroupAdapter.notifyDataSetChanged();
                        for (int i = 0; i < searchResult.size(); i++) {
                            if (searchResult.get(i).isChecked()) {
                                sb.append(searchResult.get(i).getUserId());
                                sb.append(",");
                                if (!mSelectId.contains(searchResult.get(i).getUserId())) {
//                                    GroupStateEntity groupStateEntity = new GroupStateEntity();
//                                    groupStateEntity.setUserId(searchResult.get(i).getUserId());
//                                    groupStateEntity.setState(1);
                                    mSelectId.add(searchResult.get(i).getUserId());
                                }
                            } else {
                                if (mSelectId.contains(searchResult.get(i).getUserId())) {
                                    mSelectId.remove(searchResult.get(i).getUserId());
                                }
                            }
                            mTitleViewDeleteGroupsTitle.setRightTvText(NUM_0 == mSelectId.size() ? "完成" : String.valueOf("完成(" + mSelectId.size() + ")"));
                        }
                    } else {
                        searchResult.get(position).setChecked(!searchResult.get(position).isChecked());
                        mCreateGroupAdapter.notifyDataSetChanged();
                        for (int i = 0; i < searchResult.size(); i++) {
                            if (searchResult.get(i).isChecked()) {
                                sb.append(searchResult.get(i).getUserId());
                                sb.append(",");
                                if (!mSelectId.contains(searchResult.get(i).getUserId())) {
//                                    GroupStateEntity groupStateEntity = new GroupStateEntity();
//                                    groupStateEntity.setUserId(searchResult.get(i).getUserId());
//                                    groupStateEntity.setState(1);
                                    mSelectId.add(searchResult.get(i).getUserId());
                                }
                            } else {
                                if (mSelectId.contains(searchResult.get(i).getUserId())) {
                                    mSelectId.remove(searchResult.get(i).getUserId());
                                }
                            }
                        }
                        mTitleViewDeleteGroupsTitle.setRightTvText(NUM_0 == mSelectId.size() ? "完成" : String.valueOf("完成(" + mSelectId.size() + ")"));
                    }
                    deleteUserId = sb.toString();
                    if (deleteUserId.length() > 0) {
                        deleteUserId = deleteUserId.substring(NUM_0, deleteUserId.length() - NUM_1);
                    }
                } else if (mIntentGroupMemberEntity.getGroupMemberEnum().equals(GroupMemberEnum.created)) {
                    if (searchResult.size() > 0) {
                        if (searchResult.get(position).getUserId().equals(mIntentGroupMemberEntity.getMemberId())) {
                            return;
                        }
                        if (TextUtils.isEmpty(searchResult.get(position).getRemark())) {
                            userName = searchResult.get(position).getNickName();
                        } else {
                            userName = searchResult.get(position).getRemark();
                        }
                        ImLog.e("ImLog", "userName = " + userName);
                        searchResult.get(position).setChecked(!searchResult.get(position).isChecked());
                        for (int i = 0; i < searchResult.size(); i++) {
                            if (searchResult.get(i).isChecked()) {
                                sb.append(searchResult.get(i).getUserId());
                                sb.append(",");
                                if (!mSelectId.contains(searchResult.get(i).getUserId())) {
//                                    GroupStateEntity groupStateEntity = new GroupStateEntity();
//                                    groupStateEntity.setUserId(searchResult.get(i).getUserId());
//                                    groupStateEntity.setState(1);
                                    mSelectId.add(searchResult.get(i).getUserId());
                                }
                            } else {
                                if (mSelectId.contains(searchResult.get(i).getUserId())) {
                                    mSelectId.remove(searchResult.get(i).getUserId());
                                }
                            }
                        }
                        mTitleViewDeleteGroupsTitle.setRightTvText(NUM_0 == mSelectId.size() ? "完成" : String.valueOf("完成(" + mSelectId.size() + ")"));
                        mCreateGroupAdapter.notifyDataSetChanged();
                    } else {
                        if (TextUtils.isEmpty(searchResult.get(position).getRemark())) {
                            userName = searchResult.get(position).getNickName();
                        } else {
                            userName = searchResult.get(position).getRemark();
                        }
                        searchResult.get(position).setChecked(!searchResult.get(position).isChecked());
                        mCreateGroupAdapter.notifyDataSetChanged();
                        for (int i = 0; i < searchResult.size(); i++) {
                            if (searchResult.get(i).isChecked()) {
                                sb.append(searchResult.get(i).getUserId());
                                sb.append(",");
                                if (!mSelectId.contains(searchResult.get(i).getUserId())) {
//                                    GroupStateEntity groupStateEntity = new GroupStateEntity();
//                                    groupStateEntity.setUserId(searchResult.get(i).getUserId());
//                                    groupStateEntity.setState(1);
                                    mSelectId.add(searchResult.get(i).getUserId());
                                }
                            } else {
                                if (mSelectId.contains(searchResult.get(i).getUserId())) {
                                    mSelectId.remove(searchResult.get(i).getUserId());
                                }
                            }
                        }
                        mTitleViewDeleteGroupsTitle.setRightTvText(NUM_0 == mSelectId.size() ? "完成" : String.valueOf("完成(" + mSelectId.size() + ")"));
                    }
                    ids = sb.toString();
                    if (ids.length() > 0) {
                        ids = ids.substring(NUM_0, ids.length() - NUM_1);
                    }
                } else if (mIntentGroupMemberEntity.getGroupMemberEnum().equals(GroupMemberEnum.Invited)) {
                    if (searchResult.get(position).getState().equals(String.valueOf(1))) {
                        searchResult.get(position).setChecked(!searchResult.get(position).isChecked());
                        mCreateGroupAdapter.notifyDataSetChanged();
                        for (int i = 0; i < searchResult.size(); i++) {
                            if (searchResult.get(i).isChecked()) {
                                sb.append(searchResult.get(i).getUserId());
                                sb.append(",");
                                if (!mSelectId.contains(searchResult.get(i).getUserId())) {
//                                    GroupStateEntity groupStateEntity = new GroupStateEntity();
//                                    groupStateEntity.setUserId(searchResult.get(i).getUserId());
//                                    groupStateEntity.setState(1);
                                    mSelectId.add(searchResult.get(i).getUserId());
                                }
                            } else {
                                if (mSelectId.contains(searchResult.get(i).getUserId())) {
                                    mSelectId.remove(searchResult.get(i).getUserId());
                                }
                            }
                        }
                        mTitleViewDeleteGroupsTitle.setRightTvText(NUM_0 == mSelectId.size() ? "完成" : String.valueOf("完成(" + mSelectId.size() + ")"));
                        inviteUserId = sb.toString();
                        if (deleteUserId.length() > 0) {
                            inviteUserId = inviteUserId.substring(NUM_0, inviteUserId.length() - NUM_1);
                        }
                    } else {
                        return;
                    }
                } else {
                    searchResult.get(position).setChecked(!searchResult.get(position).isChecked());
                    mCreateGroupAdapter.notifyDataSetChanged();
                    for (int i = 0; i < searchResult.size(); i++) {
                        if (searchResult.get(i).isChecked()) {
                            sb.append(searchResult.get(i).getUserId());
                            sb.append(",");
                            if (!mSelectId.contains(searchResult.get(i).getUserId())) {
//                                GroupStateEntity groupStateEntity = new GroupStateEntity();
//                                groupStateEntity.setUserId(searchResult.get(i).getUserId());
//                                groupStateEntity.setState(1);
                                mSelectId.add(searchResult.get(i).getUserId());
                            }
                        } else {
                            if (mSelectId.contains(searchResult.get(i).getUserId())) {
                                mSelectId.remove(searchResult.get(i).getUserId());
                            }
                        }
                    }
                    mTitleViewDeleteGroupsTitle.setRightTvText(NUM_0 == mSelectId.size() ? "完成" : String.valueOf("完成(" + mSelectId.size() + ")"));
                    deleteUserId = sb.toString();
                    if (deleteUserId.length() > 0) {
                        deleteUserId = deleteUserId.substring(NUM_0, deleteUserId.length() - NUM_1);
                    }
                }
            }
        });
    }

    private void initDeleteDialog() {
        abstractImSmsDialog = new AbstractImSmsDialog(GroupMemberActivity.this, false) {
            @Override
            public void sureClick() {
                super.sureClick();
                mDropGroupChatModel.getDropGroupChat(mIntentGroupMemberEntity.getGroupId(), deleteUserId);
            }

            @Override
            public void cancleClick() {
                super.cancleClick();
                abstractImSmsDialog.dismissDialog();
            }
        };
    }


    public static void getJumpGroupMember(Activity activity, IntentGroupMemberEntity intentGroupMemberEntity) {
        Intent intent = new Intent(activity, GroupMemberActivity.class);
        intent.putExtra("intentGroupMemberEntity", intentGroupMemberEntity);
        activity.startActivity(intent);
    }

    @Override
    public void onDropGroupChat() {
        MyApp.quiteActivity();
        finish();
    }

    @Override
    public void onFindGroupDetail(GroupDetailsEntity groupDetailsEntity) {
        for (int k = 0; k < groupDetailsEntity.getGroups().size(); k++) {
            if (groupDetailsEntity.getGroups().get(k).getId() == (groupDetailsEntity.getUserId())) {
                groupDetailsEntity.getGroups().remove(k);
            }
        }
        for (int i = 0; i < groupDetailsEntity.getGroups().size(); i++) {
            for (int j = 0; j < mSelectId.size(); j++) {
                if (mSelectId.get(j).equals(groupDetailsEntity.getGroups().get(i).getUserId())) {
                    groupDetailsEntity.getGroups().get(i).setChecked(true);
                }
            }

            SearchResultEntity searchResultEntity = new SearchResultEntity();
            searchResultEntity.setId(String.valueOf(groupDetailsEntity.getGroups().get(i).getId()));
            searchResultEntity.setUserIcon(groupDetailsEntity.getGroups().get(i).getUserIcon());
            searchResultEntity.setAddress(groupDetailsEntity.getGroups().get(i).getAddress());
            boolean b = null == groupDetailsEntity.getGroups().get(i).getRemark();
            searchResultEntity.setNickName(b ? groupDetailsEntity.getGroups().get(i).getNickName() : groupDetailsEntity.getGroups().get(i).getRemark());
            searchResultEntity.setId(String.valueOf(groupDetailsEntity.getGroups().get(i).getId()));
            searchResultEntity.setPhone(groupDetailsEntity.getGroups().get(i).getPhone());
            searchResultEntity.setRequestSourceSystem(groupDetailsEntity.getGroups().get(i).getRequestSourceSystem());
            searchResultEntity.setUpdateTime(String.valueOf(groupDetailsEntity.getGroups().get(i).getUpdateTime()));
            searchResultEntity.setUserType(groupDetailsEntity.getGroups().get(i).getUserType());
            searchResultEntity.setUpdateTime(String.valueOf(groupDetailsEntity.getGroups().get(i).getUpdateTime()));
            searchResultEntity.setUserId(groupDetailsEntity.getGroups().get(i).getUserId());
            searchResultEntity.setRemark(groupDetailsEntity.getGroups().get(i).getRemark());
//            if (groupDetailsEntity.getGroups().get(i).getId() == groupDetailsEntity.getUserId()) {
//                groupDetailsEntity.getGroups().remove(i);
//            }else {
            searchResult.add(searchResultEntity);
//            }
        }
        mLvDeleteGroupsChatList.setAdapter(geCreateGroupAdapter(searchResult));
    }

    @Override
    public void onFindUserList(FriendListEntity friendListEntity) {
        searchResult.clear();
        if (friendListEntity.getCode() == NUM_0) {
            for (int i = 0; i < friendListEntity.getContent().size(); i++) {
                for (int j = 0; j < mSelectId.size(); j++) {
                    if (mSelectId.get(j).equals(friendListEntity.getContent().get(i).getUserId())) {
                        friendListEntity.getContent().get(i).setChecked(true);
                    }
                }
                SearchResultEntity searchResultEntity = new SearchResultEntity();
                searchResultEntity.setGroup(friendListEntity.getContent().get(i).isGroup());
                searchResultEntity.setChecked(friendListEntity.getContent().get(i).isChecked());
                searchResultEntity.setAddress(friendListEntity.getContent().get(i).getAddress());
                searchResultEntity.setCreateTime(String.valueOf(friendListEntity.getContent().get(i).getCreateTime()));
                searchResultEntity.setId(String.valueOf(friendListEntity.getContent().get(i).getId()));
                searchResultEntity.setSex(friendListEntity.getContent().get(i).getSex());
                searchResultEntity.setNickName(friendListEntity.getContent().get(i).getNickName());
                searchResultEntity.setPhone(friendListEntity.getContent().get(i).getPhone());
                searchResultEntity.setRemark(friendListEntity.getContent().get(i).getRemark());
                searchResultEntity.setRequestSourceSystem(friendListEntity.getContent().get(i).getRequestSourceSystem());
                searchResultEntity.setUserIcon(friendListEntity.getContent().get(i).getUserIcon());
                searchResultEntity.setState(friendListEntity.getContent().get(i).getState());
                searchResultEntity.setUserId(friendListEntity.getContent().get(i).getUserId());
                searchResultEntity.setUserPassword(friendListEntity.getContent().get(i).getUserPassword());
                searchResultEntity.setUpdateTime(String.valueOf(friendListEntity.getContent().get(i).getUpdateTime()));
                searchResultEntity.setUserType(friendListEntity.getContent().get(i).getUserType());
                searchResult.add(searchResultEntity);
            }

            if (null == mCreateGroupAdapter) {
                mLvDeleteGroupsChatList.setAdapter(geCreateGroupAdapter(searchResult));
            } else {
                mCreateGroupAdapter.notifyDataSetChanged();
            }
        } else {
            if (null == mCreateGroupAdapter) {
                mLvDeleteGroupsChatList.setAdapter(geCreateGroupAdapter(searchResult));
            } else {
                mCreateGroupAdapter.notifyDataSetChanged();
            }
        }

    }

    @Override
    public void onCreateGroupChat(CreateGroupChatEntity baseEntity) {
        if (baseEntity.getCode() == NUM_0) {
            IntentChatEntity intentChatEntity = new IntentChatEntity();
            intentChatEntity.setAcceptName(baseEntity.getGroupName());
            intentChatEntity.setAcceptId(String.valueOf(baseEntity.getId()));
            intentChatEntity.setChatType(ChatType.GROUP);
            intentChatEntity.setGroupNum(baseEntity.getGroupNumberByCurrent() + "");
            MyApp.quiteActivity();
            ChatScreenActivity.getJumpChatSource(GroupMemberActivity.this, intentChatEntity);
            finish();
        } else {
            ImToastUtil.showShortToast(this, baseEntity.getMsg());
        }
    }

    @Override
    public void onGroupInviteUserList(FriendListEntity friendListEntity) {
        searchResult.clear();
        if (friendListEntity.getCode() == NUM_0) {
            for (int i = 0; i < friendListEntity.getContent().size(); i++) {
                for (int j = 0; j < mSelectId.size(); j++) {
                    if (mSelectId.get(j).equals(friendListEntity.getContent().get(i).getUserId())) {
                        friendListEntity.getContent().get(i).setChecked(true);
                    }
                }

                SearchResultEntity searchResultEntity = new SearchResultEntity();
                searchResultEntity.setGroup(friendListEntity.getContent().get(i).isGroup());
                searchResultEntity.setChecked(friendListEntity.getContent().get(i).isChecked());
                searchResultEntity.setAddress(friendListEntity.getContent().get(i).getAddress());
                searchResultEntity.setCreateTime(String.valueOf(friendListEntity.getContent().get(i).getCreateTime()));
                searchResultEntity.setId(String.valueOf(friendListEntity.getContent().get(i).getId()));
                searchResultEntity.setSex(friendListEntity.getContent().get(i).getSex());
                searchResultEntity.setNickName(friendListEntity.getContent().get(i).getNickName());
                searchResultEntity.setPhone(friendListEntity.getContent().get(i).getPhone());
                searchResultEntity.setRemark(friendListEntity.getContent().get(i).getRemark());
                searchResultEntity.setRequestSourceSystem(friendListEntity.getContent().get(i).getRequestSourceSystem());
                searchResultEntity.setUserIcon(friendListEntity.getContent().get(i).getUserIcon());
                searchResultEntity.setState(friendListEntity.getContent().get(i).getState());
                searchResultEntity.setUserId(friendListEntity.getContent().get(i).getUserId());
                searchResultEntity.setUserPassword(friendListEntity.getContent().get(i).getUserPassword());
                searchResultEntity.setUpdateTime(String.valueOf(friendListEntity.getContent().get(i).getUpdateTime()));
                searchResultEntity.setUserType(friendListEntity.getContent().get(i).getUserType());
                searchResult.add(searchResultEntity);
            }
            ImToastUtil.showShortToast(GroupMemberActivity.this, friendListEntity.getMsg());
            if (null == mCreateGroupAdapter) {
                mLvDeleteGroupsChatList.setAdapter(geCreateGroupAdapter(searchResult));
            } else {
                mCreateGroupAdapter.notifyDataSetChanged();
            }
        } else {
            mCreateGroupAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {
    }

    @Override
    public void onInvitingUser() {
        finish();
    }

    @Override
    public void onSearchUserInGroup(SearchGroupMemberEntity mSearchGroupMemberEntity) {
        searchResult.clear();
        if (mSearchGroupMemberEntity.getCode() == NUM_0) {
            for (int i = 0; i < mSearchGroupMemberEntity.getContent().size(); i++) {
                if (mSearchGroupMemberEntity.getContent().size() > 0) {
                    for (int j = 0; j < mSelectId.size(); j++) {
                        if (mSelectId.get(j).equals(mSearchGroupMemberEntity.getContent().get(i).getUserId())) {
                            mSearchGroupMemberEntity.getContent().get(i).setCheck(true);
                        }
                    }
                    SearchResultEntity searchResultEntity = new SearchResultEntity();
                    searchResultEntity.setUserIcon(mSearchGroupMemberEntity.getContent().get(i).getUserIcon());
                    searchResultEntity.setAddress(mSearchGroupMemberEntity.getContent().get(i).getAddress());
                    boolean b = null == mSearchGroupMemberEntity.getContent().get(i).getRemark();
                    searchResultEntity.setNickName(b ? mSearchGroupMemberEntity.getContent().get(i).getNickName() : mSearchGroupMemberEntity.getContent().get(i).getRemark());
                    searchResultEntity.setId(String.valueOf(mSearchGroupMemberEntity.getContent().get(i).getId()));
                    searchResultEntity.setPhone(mSearchGroupMemberEntity.getContent().get(i).getPhone());
                    searchResultEntity.setRequestSourceSystem(mSearchGroupMemberEntity.getContent().get(i).getRequestSourceSystem());
                    searchResultEntity.setUpdateTime(String.valueOf(mSearchGroupMemberEntity.getContent().get(i).getUpdateTime()));
                    searchResultEntity.setUserType(mSearchGroupMemberEntity.getContent().get(i).getUserType());
                    searchResultEntity.setUpdateTime(String.valueOf(mSearchGroupMemberEntity.getContent().get(i).getUpdateTime()));
                    searchResultEntity.setUserId(mSearchGroupMemberEntity.getContent().get(i).getUserId());
                    searchResultEntity.setChecked(mSearchGroupMemberEntity.getContent().get(i).getCheck());
                    searchResultEntity.setRemark(mSearchGroupMemberEntity.getContent().get(i).getRemark());
                    if (mSearchGroupMemberEntity.getContent().get(i).getUserId().equals(MyApp.manager.getId())) {
                        mSearchGroupMemberEntity.getContent().remove(i);
                    } else {
                        searchResult.add(searchResultEntity);
                    }
                } else {
                    ImToastUtil.showShortToast(this, "不能搜索群主");
                }
            }
            if (null == mCreateGroupAdapter) {
                mLvDeleteGroupsChatList.setAdapter(geCreateGroupAdapter(searchResult));
            } else {
                mCreateGroupAdapter.notifyDataSetChanged();
            }
        } else {
            if (mEdtGroupMemberSearchEdit.getText().length() > 0) {
                ImToastUtil.showShortToast(this, mSearchGroupMemberEntity.getMsg());
                mLvDeleteGroupsChatList.setVisibility(View.GONE);
            }
            mCreateGroupAdapter.notifyDataSetChanged();
        }

    }


    @Override
    public void onDimSearch(SearchFigureEntity searchEntity) {
        searchResult.clear();
//        contentBeanList.clear();
        if (searchEntity.getCode() == NUM_0) {
            for (int i = 0; i < searchEntity.getContent().size(); i++) {
                for (int j = 0; j < mSelectId.size(); j++) {
                    if (mSelectId.get(j).equals(searchEntity.getContent().get(i).getUserId())) {
                        searchEntity.getContent().get(i).setChecked(true);
                    }
                }
                SearchResultEntity searchResultEntity = new SearchResultEntity();

            }
            searchResult.addAll(searchEntity.getContent());
            if (null == mCreateGroupAdapter) {
                mLvDeleteGroupsChatList.setAdapter(geCreateGroupAdapter(searchResult));
            } else {
                mCreateGroupAdapter.notifyDataSetChanged();
            }
        } else {
            mCreateGroupAdapter.notifyDataSetChanged();
        }
    }

    /**
     * 获取适配器
     */
    private CommonAdapter geCreateGroupAdapter(final List<SearchResultEntity> contentBeanList) {
        mCreateGroupAdapter = new CommonAdapter<SearchResultEntity>(
                GroupMemberActivity.this, R.layout.item_delete_groups, contentBeanList) {
            @Override
            protected void convert(ViewHolder viewHolder, SearchResultEntity item, int position) {
                if (null != item.getRemark()) {
                    viewHolder.setText(R.id.txt_delete_groups_user_name, item.getRemark());
                } else {
                    viewHolder.setText(R.id.txt_delete_groups_user_name, item.getNickName());
                }
                NineGridImageView view = viewHolder.getView(R.id.img_delete_groups_user_icon);
                TextView textView = viewHolder.getView(R.id.txt_delete_groups_selete);
                List<String> list = new ArrayList<>();
                list.add(item.getUserIcon());
                SettingNineGroupIcon.setGroupIcon(view, list);
                if (String.valueOf(0).equals(item.getState())) {
                    textView.setBackground(ContextCompat.getDrawable(MyApp.getInstance(), R.drawable.icon_gary_select));
                } else if (null != mIntentGroupMemberEntity.getMemberId() && mIntentGroupMemberEntity.getMemberId().equals(item.getUserId())) {
                    textView.setBackground(ContextCompat.getDrawable(MyApp.getInstance(), R.drawable.icon_gary_select));
                } else {
                    if (item.isChecked()) {
                        textView.setBackgroundResource(R.drawable.invitee_checked);
                    } else {
                        textView.setBackgroundResource(R.drawable.invitee_circle);
                    }
                }
                textView.setVisibility(View.VISIBLE);
            }
        };
        return this.mCreateGroupAdapter;
    }
}
