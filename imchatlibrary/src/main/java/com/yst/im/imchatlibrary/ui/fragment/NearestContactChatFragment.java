package com.yst.im.imchatlibrary.ui.fragment;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.View;
import android.widget.AdapterView;
import android.widget.RelativeLayout;

import com.baoyz.swipemenulistview.SwipeMenu;
import com.baoyz.swipemenulistview.SwipeMenuCreator;
import com.baoyz.swipemenulistview.SwipeMenuItem;
import com.baoyz.swipemenulistview.SwipeMenuListView;
import com.google.gson.Gson;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.yst.im.imchatlibrary.MyApp;
import com.yst.im.imchatlibrary.R;
import com.yst.im.imchatlibrary.adapter.NearestContactAdapter;
import com.yst.im.imchatlibrary.base.BaseFragment;
import com.yst.im.imchatlibrary.base.baseview.AbstractTitleView;
import com.yst.im.imchatlibrary.bean.BaseEntity;
import com.yst.im.imchatlibrary.bean.GroupDetailsEntity;
import com.yst.im.imchatlibrary.bean.IntentChatEntity;
import com.yst.im.imchatlibrary.bean.Record;
import com.yst.im.imchatlibrary.model.DeleteNotificaitonModel;
import com.yst.im.imchatlibrary.model.FindGroupDetailModel;
import com.yst.im.imchatlibrary.model.UpdateMessageStateModel;
import com.yst.im.imchatlibrary.ui.activity.SystemNotificationActivity;
import com.yst.im.imchatlibrary.utils.ImToastUtil;
import com.yst.im.imchatlibrary.utils.SignUtils;
import com.yst.im.imsdk.MessageConstant;
import com.yst.im.imsdk.bean.ContactsEntity;
import com.yst.im.imchatlibrary.bean.IntentGroupMemberEntity;
import com.yst.im.imchatlibrary.bean.RecentContactEntity;
import com.yst.im.imchatlibrary.enumclass.GroupMemberEnum;
import com.yst.im.imchatlibrary.model.DeleteGroupChatMessageModel;
import com.yst.im.imchatlibrary.model.DeleteSingleChatMessageModel;
import com.yst.im.imchatlibrary.model.RecentContactsModel;
import com.yst.im.imchatlibrary.ui.activity.AddActivity;
import com.yst.im.imchatlibrary.ui.activity.ChatScreenActivity;
import com.yst.im.imchatlibrary.ui.activity.GroupMemberActivity;
import com.yst.im.imchatlibrary.ui.activity.ImSearchActivity;
import com.yst.im.imchatlibrary.utils.ImLog;
import com.yst.im.imchatlibrary.utils.JumpIntent;
import com.yst.im.imchatlibrary.utils.PopupWindowUtils;
import com.yst.im.imsdk.ChatType;
import com.yst.im.imsdk.bean.MessageBean;
import com.yst.im.imsdk.dao.ContactsEntityDaoUtils;
import com.yst.im.imsdk.dao.GreenDaoManager;
import com.yst.im.imsdk.dao.MessageDaoUtils;
import com.yst.im.imsdk.utils.RxBusConstants;

import java.util.ArrayList;
import java.util.List;

import gorden.rxbus2.RxBus;
import gorden.rxbus2.Subscribe;
import gorden.rxbus2.ThreadMode;

import static com.scwang.smartrefresh.layout.util.DensityUtil.dp2px;
import static com.yst.im.imchatlibrary.utils.PopupWindowUtils.PopWindowType.AddSetting;
import static com.yst.im.imsdk.MessageConstant.NEWS_ACCEPT_PICTURE;
import static com.yst.im.imsdk.MessageConstant.NEWS_ACCEPT_TEXT;
import static com.yst.im.imsdk.MessageConstant.NEWS_ACCEPT_VOICE;
import static com.yst.im.imsdk.MessageConstant.NEWS_SEND_RECALL;
import static com.yst.im.imsdk.MessageConstant.NUM_0;
import static com.yst.im.imsdk.MessageConstant.NUM_1;
import static com.yst.im.imsdk.MessageConstant.NUM_3;
import static com.yst.im.imsdk.MessageConstant.NUM_65;
import static com.yst.im.imsdk.MessageConstant.NUM_70;
import static com.yst.im.imsdk.MessageConstant.NUM_71;
import static com.yst.im.imsdk.MessageConstant.NUM_99;
import static com.yst.im.imsdk.MessageConstant.TYPE_AVI;
import static com.yst.im.imsdk.MessageConstant.TYPE_DELETE_USER;
import static com.yst.im.imsdk.MessageConstant.TYPE_INVITE_JINO_GROUP;
import static com.yst.im.imsdk.MessageConstant.TYPE_OUTLOGIN;
import static com.yst.im.imsdk.MessageConstant.TYPE_PICTURE;
import static com.yst.im.imsdk.MessageConstant.TYPE_RECALL;
import static com.yst.im.imsdk.MessageConstant.TYPE_RECALL_HISTORY;
import static com.yst.im.imsdk.MessageConstant.TYPE_TEMPLATE;
import static com.yst.im.imsdk.MessageConstant.TYPE_TXT;
import static com.yst.im.imsdk.MessageConstant.TYPE_USER_JINO_GROUP;
import static com.yst.im.imsdk.MessageConstant.TYPE_USER_OUT_GROUP;
import static com.yst.im.imsdk.MessageConstant.TYPE_USER_OUT_LOGIN;
import static com.yst.im.imsdk.MessageConstant.TYPE_VOICE;
import static com.yst.im.imsdk.utils.RxBusConstants.CONST_CODE_NEAREST_UPDATE;

/**
 * 最近联系人  F
 *
 * @author Lierpeng
 * @version 1.0.0
 * @date 2017/12/14.
 */
public class NearestContactChatFragment extends BaseFragment implements
        AdapterView.OnItemClickListener,
        RecentContactsModel.RecentContactsCallBack,
        DeleteGroupChatMessageModel.DeleteGroupChatMessageCallBack,
        DeleteSingleChatMessageModel.DeleteSingleChatMessageCallBack,
        DeleteNotificaitonModel.DeleteNotificaitonCallBack,
        UpdateMessageStateModel.UpdateMessageStateListenerCallBack,
        FindGroupDetailModel.FindGroupDetailListenerCallBack {

    private List<ContactsEntity> mNearestList;
    private NearestContactAdapter friendAdapter;
    private SwipeMenuListView mSmlvNearestChatList;
    private RecentContactsModel recentContactsModel;
    private DeleteGroupChatMessageModel mDeleteGroupChatMessageModel;
    private DeleteSingleChatMessageModel mDeleteSingleChatMessageModel;
    private DeleteNotificaitonModel mDeleteNotificaitonModel;
    private AbstractTitleView mTitleViewNearestTitle;
    private FindGroupDetailModel mFindGroupDetailModel;
    private UpdateMessageStateModel mUpdateMessageStateModel;
    private MessageBean mMessageBean;

    @Override
    public void initView(View mView) {
        mTitleViewNearestTitle = (AbstractTitleView) mView.findViewById(R.id.titleView_nearest_title);
        RelativeLayout mRelNearestSearch = (RelativeLayout) mView.findViewById(R.id.rel_nearest_search);
        SmartRefreshLayout mRefNearestView = (SmartRefreshLayout) mView.findViewById(R.id.ref_nearest_view);
        mRefNearestView.setEnableLoadmore(false);
        mRefNearestView.setEnableRefresh(false);
        mSmlvNearestChatList = (SwipeMenuListView) mView.findViewById(R.id.smlv_nearest_chatList);
        RelativeLayout mRelNearestNothing = (RelativeLayout) mView.findViewById(R.id.rel_nearest_nothing);
        mTitleViewNearestTitle.setTitleText(MyApp.getInstance().getString(R.string.txt_main_msglist));
        mTitleViewNearestTitle.getRightImageIv().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new PopupWindowUtils(getActivity(), new PopupWindowUtils.OnPopWindowClickListener() {
                    @Override
                    public void onPopWindowClickListener(View view) {
                        int i = view.getId();
                        if (i == R.id.lil_new_group) {
                            IntentGroupMemberEntity intentGroupMemberEntity = new IntentGroupMemberEntity();
                            intentGroupMemberEntity.setGroupMemberEnum(GroupMemberEnum.created);
                            intentGroupMemberEntity.setTitleName(getResources().getString(R.string.txt_choose_groups));
                            GroupMemberActivity.getJumpGroupMember(getActivity(), intentGroupMemberEntity);
                        } else if (i == R.id.lil_new_friend) {
                            JumpIntent.jump(getActivity(), AddActivity.class);
                        }
                    }
                }, AddSetting, false, false).show(MyApp.screenWidth - com.yst.im.imsdk.utils.BaseUtils.dp2px(getActivity(), 188),
                        com.yst.im.imsdk.utils.BaseUtils.dp2px(getActivity(), 75));
            }
        });
        mRelNearestSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                JumpIntent.jump(getActivity(), ImSearchActivity.class);
            }
        });
        initListView();
    }

    /**
     * 初始化侧滑listview
     */
    private void initListView() {
        SwipeMenuCreator creator = new SwipeMenuCreator() {

            @Override
            public void create(SwipeMenu menu) {
                SwipeMenuItem openItem = new SwipeMenuItem(
                        MyApp.getInstance());
                openItem.setBackground(new ColorDrawable(Color.RED));
                openItem.setWidth(dp2px(90));
                openItem.setTitle("删除");
                openItem.setTitleSize(15);
                openItem.setTitleColor(Color.WHITE);
                menu.addMenuItem(openItem);
            }
        };
//        mSmlvNearestChatList.setMenuCreator(creator);
        // Left
        mSmlvNearestChatList.setSwipeDirection(SwipeMenuListView.DIRECTION_LEFT);

        mSmlvNearestChatList.setOnMenuItemClickListener(new SwipeMenuListView.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(int position, SwipeMenu menu, int index) {
                boolean isNotice = mNearestList.get(position).getType() == NUM_65
                        || mNearestList.get(position).getType() == NUM_70
                        || mNearestList.get(position).getType() == TYPE_DELETE_USER;
                if (isNotice) {
                    mDeleteNotificaitonModel.getDeleteNotificaiton(position);
                    mUpdateMessageStateModel.updateMessageState(mNearestList.get(position).getGroupId(), "0", SignUtils.getTimeStamp());
                } else {
                    String contactsId = "";
                    if (mNearestList.get(position).getEvent() == NUM_1) {
                        if (mNearestList.get(position).getSenderId().equals(MyApp.manager.getId())) {
                            mDeleteSingleChatMessageModel.getDeleteSingleChatMessage(mNearestList.get(position).getAccepteId(), position);
                            contactsId = MyApp.manager.getId() + mNearestList.get(position).getAccepteId();
                            mUpdateMessageStateModel.updateMessageState(mNearestList.get(position).getAccepteId(), "1", SignUtils.getTimeStamp());
                        } else {
                            mDeleteSingleChatMessageModel.getDeleteSingleChatMessage(mNearestList.get(position).getSenderId(), position);
                            contactsId = MyApp.manager.getId() + mNearestList.get(position).getSenderId();
                            mUpdateMessageStateModel.updateMessageState(mNearestList.get(position).getSenderId(), "1", SignUtils.getTimeStamp());
                        }
                        MessageDaoUtils.deleteInTx(contactsId);
                        ContactsEntityDaoUtils.deleteInTx(contactsId);
                    } else if (mNearestList.get(position).getEvent() == NUM_3) {
                        mDeleteGroupChatMessageModel.getDeleteGroupChatMessage(mNearestList.get(position).getGroupId(), position);
                        mUpdateMessageStateModel.updateMessageState(mNearestList.get(position).getGroupId(), "0", SignUtils.getTimeStamp());
                        ContactsEntityDaoUtils.deleteInTx(MyApp.manager.getId() + mNearestList.get(position).getGroupId());
                        MessageDaoUtils.deleteInTx(MyApp.manager.getId() + mNearestList.get(position).getGroupId());
                    }
                }
                return false;
            }
        });
    }

    @Override
    public void initData() {
        recentContactsModel = new RecentContactsModel(getActivity());
        recentContactsModel.setRecentContactsCallBack(this);
        mNearestList = new ArrayList<>();
        friendAdapter = new NearestContactAdapter(getActivity(), mNearestList);
        mSmlvNearestChatList.setAdapter(friendAdapter);
        mSmlvNearestChatList.setOnItemClickListener(this);
        mDeleteGroupChatMessageModel = new DeleteGroupChatMessageModel(getActivity());
        mDeleteGroupChatMessageModel.setDeleteGroupChatMessageCallBack(this);
        mDeleteSingleChatMessageModel = new DeleteSingleChatMessageModel(getActivity());
        mDeleteSingleChatMessageModel.setDeleteSingleChatMessageCallBack(this);
        mDeleteNotificaitonModel = new DeleteNotificaitonModel(getActivity());
        mDeleteNotificaitonModel.setDeleteNotificaitonCallBack(this);
        mUpdateMessageStateModel = new UpdateMessageStateModel(getActivity());
        mUpdateMessageStateModel.setUpdateMessageStateListenerCallBack(this);
        mFindGroupDetailModel = new FindGroupDetailModel(getActivity());
        mFindGroupDetailModel.setFindGroupDetailListenerCallBack(this);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        String acceptId;
        if (MyApp.manager.getId().equals(mNearestList.get(position).getAccepteId())) {
            acceptId = mNearestList.get(position).getSenderId();
        } else {
            acceptId = mNearestList.get(position).getAccepteId();
        }
        String acceptNickName = mNearestList.get(position).getNickName();
        boolean isNotice = mNearestList.get(position).getType() == NUM_65
                || mNearestList.get(position).getType() == NUM_70
                || mNearestList.get(position).getType() == TYPE_DELETE_USER;
        if (isNotice) {
            JumpIntent.jump(getActivity(), SystemNotificationActivity.class);
        } else {
            if (mNearestList.get(position).getEvent() == NUM_3) {
                IntentChatEntity intentChatEntity = new IntentChatEntity();
                intentChatEntity.setAcceptName(acceptNickName);
                intentChatEntity.setGroupNum(mNearestList.get(position).getGroupNum());
                intentChatEntity.setAcceptId(String.valueOf(mNearestList.get(position).getGroupId()));
                intentChatEntity.setChatType(ChatType.GROUP);
                ChatScreenActivity.getJumpChatSource(getActivity(), intentChatEntity);
            } else {
                IntentChatEntity intentChatEntity = new IntentChatEntity();
                intentChatEntity.setAcceptName(acceptNickName);
                intentChatEntity.setAcceptId(acceptId);
                intentChatEntity.setChatType(ChatType.C2C);
                ChatScreenActivity.getJumpChatSource(getActivity(), intentChatEntity);
            }
        }
    }


    @Override
    public int getLayoutId() {
        return R.layout.fragment_person_chat;
    }


    @Subscribe(code = CONST_CODE_NEAREST_UPDATE, threadMode = ThreadMode.MAIN)
    public void messageReceive(MessageBean msgInfo) {
        if (msgInfo == null) {
            return;
        }
        boolean isReceive = msgInfo.getEvent() == NUM_1 || msgInfo.getEvent() == NUM_3;
        if (!isReceive) {
            return;
        }
        String contactsId = "";
        if (msgInfo.getEvent() == NUM_1) {
            if (MyApp.manager.getId().equals(msgInfo.getSenderId())) {
                contactsId = MyApp.manager.getId() + msgInfo.getAccepteId();
            } else {
                contactsId = MyApp.manager.getId() + msgInfo.getSenderId();
            }
        } else {
            contactsId = MyApp.manager.getId() + msgInfo.getGroupId();
        }
        if (msgInfo.getType() == TYPE_USER_OUT_LOGIN) {
            return;
        }
        mMessageBean = msgInfo;
        List<ContactsEntity> contactsEntities = ContactsEntityDaoUtils.queryConstacts(contactsId);
        ImLog.e("ImLog", " 用来查询的 contactsId = " + contactsId);
        ImLog.e("ImLog", "根据条件查询到的数据 contactsEntities = " + contactsEntities.size());
        if (msgInfo.getEvent() == NUM_1 && MyApp.activityList.size() == NUM_0) {
            if (contactsEntities.size() == 0) {
                ContactsEntity contactsEntity = new ContactsEntity();
                contactsEntity.setId(null);
                contactsEntity.setContent(msgInfo.getContent());
                contactsEntity.setNickName(msgInfo.getNickName());
                contactsEntity.setSenderId(msgInfo.getSenderId());
                contactsEntity.setAccepteId(msgInfo.getAccepteId());
                contactsEntity.setVersion(msgInfo.getVersion());
                contactsEntity.setOccureTime(msgInfo.getOccureTime());
                contactsEntity.setPortrait("[\"" + msgInfo.getPortrait() + "\"]");
                contactsEntity.setType(msgInfo.getType());
                contactsEntity.setIsShield(1);
                contactsEntity.setIsStick(1);
                contactsEntity.setCount(1);
                contactsEntity.setEvent(1);
                contactsEntity.setContactsId(contactsId);
                contactsEntity.setType(msgInfo.getType());
                ContactsEntityDaoUtils.insertOrReplace(contactsEntity);
            } else {
                ContactsEntity contactsEntity = contactsEntities.get(0);
                contactsEntity.setContent(msgInfo.getContent());
                contactsEntity.setSenderId(msgInfo.getSenderId());
                contactsEntity.setAccepteId(msgInfo.getAccepteId());
                contactsEntity.setVersion(msgInfo.getVersion());
                contactsEntity.setOccureTime(msgInfo.getOccureTime());
                ImLog.e("im", "count" + contactsId + "");
                if (msgInfo.getType() != TYPE_RECALL) {
                    int count = contactsEntity.getCount() + 1;
                    contactsEntity.setCount(count);
                }
                contactsEntity.setType(msgInfo.getType());
                ContactsEntityDaoUtils.update(contactsEntity);
            }
            recentContacts();
        } else {
            if (MyApp.activityList.size() == NUM_0) {
                if (contactsEntities.size() == 0) {
                    mFindGroupDetailModel.getFindGroupDetail(msgInfo.getGroupId());
                } else {
                    ContactsEntity contactsEntity = contactsEntities.get(0);
                    contactsEntity.setContent(msgInfo.getContent());
                    contactsEntity.setSenderId(msgInfo.getSenderId());
                    contactsEntity.setAccepteId(msgInfo.getAccepteId());
                    ImLog.e("im", "count" + contactsEntity.getCount() + "");
                    if (msgInfo.getType() != TYPE_RECALL) {
                        int count = contactsEntity.getCount() + 1;
                        contactsEntity.setCount(count);
                    }
                    contactsEntity.setOccureTime(msgInfo.getOccureTime());
                    contactsEntity.setType(msgInfo.getType());
                    ContactsEntityDaoUtils.update(contactsEntity);
                    recentContacts();
                }
            }
        }
        msgInfo.setContactsId(contactsId);
        msgInfo.setId(null);
        switch (msgInfo.getType()) {
            case TYPE_TXT:
                msgInfo.setMessageType(NEWS_ACCEPT_TEXT);
                MessageDaoUtils.insertOrReplace(msgInfo);
                break;
            case TYPE_PICTURE:
                msgInfo.setMessageType(NEWS_ACCEPT_PICTURE);
                MessageDaoUtils.insertOrReplace(msgInfo);
                break;
            case TYPE_AVI:
                msgInfo.setMessageType(NEWS_ACCEPT_PICTURE);
                MessageDaoUtils.insertOrReplace(msgInfo);
                break;
            case TYPE_VOICE:
                msgInfo.setMessageType(NEWS_ACCEPT_VOICE);
                Gson gson = new Gson();
                Record record = gson.fromJson(msgInfo.getContent(), Record.class);
                msgInfo.setPath(record.getPath());
                MessageDaoUtils.insertOrReplace(msgInfo);
                break;
            case TYPE_TEMPLATE:
                if (msgInfo.getSenderId().equals(MyApp.manager.getId())) {
                    msgInfo.setMessageType(MessageConstant.NEWS_SEND_TEMPLATE_MESSAGE);
                } else {
                    msgInfo.setMessageType(MessageConstant.NEWS_ACCEPT_TEMPLATE_MESSAGE);
                }
                MessageDaoUtils.insertOrReplace(msgInfo);
                break;
            case TYPE_RECALL:
                msgInfo.setMessageType(NEWS_SEND_RECALL);
                msgInfo.setType(TYPE_RECALL_HISTORY);
                MessageDaoUtils.insertOrReplace(msgInfo);
                break;
            case TYPE_INVITE_JINO_GROUP:
                msgInfo.setMessageType(NEWS_SEND_RECALL);
                MessageDaoUtils.insertOrReplace(msgInfo);
                break;
            case TYPE_USER_JINO_GROUP:
                msgInfo.setMessageType(NEWS_SEND_RECALL);
                MessageDaoUtils.insertOrReplace(msgInfo);
                break;
            case TYPE_USER_OUT_GROUP:
                msgInfo.setMessageType(NEWS_SEND_RECALL);
                MessageDaoUtils.insertOrReplace(msgInfo);
                break;
            case NUM_65:
            case TYPE_OUTLOGIN:
                recentContactsModel.getRecentContacts();
                break;

            default:
                break;
        }
        friendAdapter.notifyDataSetChanged();
    }

    @Override
    public void onResume() {
        super.onResume();
        MyApp.activityList.clear();
        if (MyApp.manager.getLoginState()) {
            recentContactsModel.getRecentContacts();
        }
    }

    /**
     * 更新最近联系人列表页面
     */
    private void recentContacts() {
        List<ContactsEntity> contactsEntities = ContactsEntityDaoUtils.queryConstactsAll();
        ImLog.e("ImLog", "contactsEntities  = " + contactsEntities.size());
        mNearestList.clear();
        mNearestList.addAll(contactsEntities);
        friendAdapter.notifyDataSetChanged();
        int count = 0;
        for (int i = 0; i < mNearestList.size(); i++) {
            if (mNearestList.get(i).getIsShield() == NUM_0) {
                ImLog.e("isShield", mNearestList.get(i).getIsShield() + "");
            } else {
                count += mNearestList.get(i).getCount();
            }
        }
        if (count <= NUM_99) {
            RxBus.get().send(RxBusConstants.CONST_REFRESH_NEAREST_NUM, String.valueOf(count));
        } else {
            RxBus.get().send(RxBusConstants.CONST_REFRESH_NEAREST_NUM, "99+");
        }
    }

    @Override
    public void onRecentContacts(RecentContactEntity recentContactEntity) {
        mNearestList.clear();
        GreenDaoManager.getInstance().getmDaoSession().getContactsEntityDao().deleteAll();
        if (recentContactEntity.getCode() == NUM_0) {
            mNearestList.addAll(recentContactEntity.getContent());
            int count = 0;
            String contactsId = "";
            for (int i = 0; i < mNearestList.size(); i++) {
                if (mNearestList.get(i).getEvent() == NUM_1) {
                    if (MyApp.manager.getId().equals(mNearestList.get(i).getSenderId())) {
                        contactsId = MyApp.manager.getId() + mNearestList.get(i).getAccepteId();
                    } else {
                        contactsId = MyApp.manager.getId() + mNearestList.get(i).getSenderId();
                    }
                } else {
                    contactsId = MyApp.manager.getId() + mNearestList.get(i).getGroupId();
                }
                mNearestList.get(i).setContactsId(contactsId);
                if (mNearestList.get(i).getIsShield() == NUM_0) {
                    ImLog.e("isShield", mNearestList.get(i).getIsShield() + "");
                } else {
                    count += mNearestList.get(i).getCount();
                }
                ContactsEntityDaoUtils.insertOrReplace(mNearestList.get(i));
            }
            if (count <= NUM_99) {
                RxBus.get().send(RxBusConstants.CONST_REFRESH_NEAREST_NUM, String.valueOf(count));
            } else {
                RxBus.get().send(RxBusConstants.CONST_REFRESH_NEAREST_NUM, "99+");
            }
            friendAdapter.notifyDataSetChanged();
        } else {
            friendAdapter.notifyDataSetChanged();
            RxBus.get().send(RxBusConstants.CONST_REFRESH_NEAREST_NUM, String.valueOf(0));
        }

    }

    @Override
    public void onDeleteGroupChatMessage(BaseEntity baseEntity, int position) {
        if (baseEntity.getCode() == NUM_0) {
            recentContactsModel.getRecentContacts();
        }
    }

    @Override
    public void onDeleteSingleChat(BaseEntity baseEntity, int position) {
        if (baseEntity.getCode() == NUM_0) {
            recentContactsModel.getRecentContacts();
        }
    }

    @Override
    public void onDeleteNotificaiton(BaseEntity baseEntity, int position) {
        ImToastUtil.showShortToast(getActivity(), baseEntity.getMsg());
        mNearestList.remove(position);
        friendAdapter.notifyDataSetChanged();
        recentContactsModel.getRecentContacts();
    }

    @Override
    public void onUpdateMessageState(BaseEntity baseEntity) {
        ImLog.e("cehua", baseEntity.getMsg());
    }

    @Override
    public void onFindGroupDetail(GroupDetailsEntity groupDetailsEntity) {
        ContactsEntity contactsEntity = new ContactsEntity();
        contactsEntity.setId(null);
        contactsEntity.setContactsId(MyApp.manager.getId() + mMessageBean.getGroupId());
        contactsEntity.setContent(mMessageBean.getContent());
        contactsEntity.setSenderId(mMessageBean.getSenderId());
        contactsEntity.setAccepteId(mMessageBean.getAccepteId());
        contactsEntity.setVersion(mMessageBean.getVersion());
        contactsEntity.setOccureTime(mMessageBean.getOccureTime());
        contactsEntity.setPortrait(groupDetailsEntity.getImageUrl());
        contactsEntity.setGroupId(mMessageBean.getGroupId());
        contactsEntity.setGroupNum(String.valueOf(groupDetailsEntity.getGroupNumberByCurrent()));
        contactsEntity.setNickName(groupDetailsEntity.getGroupName());
        contactsEntity.setType(mMessageBean.getType());
        contactsEntity.setIsShield(1);
        contactsEntity.setIsStick(1);
        contactsEntity.setCount(1);
        contactsEntity.setEvent(3);
        ContactsEntityDaoUtils.insertOrReplace(contactsEntity);
        recentContacts();
    }
}
