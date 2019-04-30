package com.yst.im.imchatlibrary.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.yst.im.imchatlibrary.MyApp;
import com.yst.im.imchatlibrary.R;
import com.yst.im.imchatlibrary.adapter.FriendListAdapter;
import com.yst.im.imchatlibrary.base.BaseFragment;
import com.yst.im.imchatlibrary.base.baseview.AbstractTitleView;
import com.yst.im.imchatlibrary.bean.ApplyEntity;
import com.yst.im.imchatlibrary.bean.ApplyGroupEntity;
import com.yst.im.imchatlibrary.bean.FriendInfoEntity;
import com.yst.im.imchatlibrary.bean.FriendListEntity;
import com.yst.im.imchatlibrary.model.FindUserListModel;
import com.yst.im.imchatlibrary.model.QueryAddFriendModel;
import com.yst.im.imchatlibrary.model.QueryApprovedListModel;
import com.yst.im.imchatlibrary.ui.activity.AddActivity;
import com.yst.im.imchatlibrary.ui.activity.ApplyAffirmActivity;
import com.yst.im.imchatlibrary.ui.activity.ApplyListActivity;
import com.yst.im.imchatlibrary.ui.activity.ImSearchActivity;
import com.yst.im.imchatlibrary.ui.activity.MineGroupActivity;
import com.yst.im.imchatlibrary.utils.JumpIntent;
import com.yst.im.imsdk.ChatType;
import com.yst.im.imsdk.utils.RxBusConstants;

import java.util.ArrayList;
import java.util.List;

import gorden.rxbus2.RxBus;

import static com.yst.im.imsdk.MessageConstant.NUM_0;
import static com.yst.im.imsdk.MessageConstant.NUM_2;

/**
 * 好友列表 F
 *
 * @author Lierpeng
 * @version 1.0.0
 * @date 2018/4/10.
 */
public class FriendListFragment extends BaseFragment implements
        View.OnClickListener,
        FindUserListModel.FindUserListListenerCallBack,
        QueryAddFriendModel.QueryAddFriendListenerCallBack,
        QueryApprovedListModel.QueryApprovedListListenerCallBack {

    private FriendListAdapter mNearestContactAdapter;
    private AbstractTitleView mTitleViewAddressTitle;
    private RelativeLayout mRelAddressSearch;
    private LinearLayout mLilAddressNewFriends;
    private TextView mTxtAddressNewFriendNum;
    private LinearLayout mLilAddressGroup;
    private TextView mTxtAddressNewGroupNum;
    private ListView mLvAddressFriendList;
    private QueryApprovedListModel queryApprovedListModel;
    private QueryAddFriendModel queryAddFriendModel;
    private List<FriendListEntity.ContentBean> friendList;
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            mNearestContactAdapter.notifyDataSetChanged();
        }
    };
    private FindUserListModel mFindUserListModel;
    private int applyCount;

    @Override
    public void initView(View mView) {
        mTitleViewAddressTitle = (AbstractTitleView) mView.findViewById(R.id.titleView_address_title);
        mRelAddressSearch = (RelativeLayout) mView.findViewById(R.id.rel_address_search);
        mLilAddressNewFriends = (LinearLayout) mView.findViewById(R.id.lil_address_new_friends);
        mTxtAddressNewFriendNum = (TextView) mView.findViewById(R.id.txt_address_new_friend_num);
        mLilAddressGroup = (LinearLayout) mView.findViewById(R.id.lil_address_group);
        mTxtAddressNewGroupNum = (TextView) mView.findViewById(R.id.txt_address_new_group_num);
        mLvAddressFriendList = (ListView) mView.findViewById(R.id.lv_address_friendList);
        mTxtAddressNewFriendNum.setVisibility(View.GONE);
        mTitleViewAddressTitle.getRightImageIv().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), AddActivity.class));
            }
        });
        mRelAddressSearch.setOnClickListener(this);
        mLilAddressNewFriends.setOnClickListener(this);
        mLilAddressGroup.setOnClickListener(this);
    }

    @Override
    public void initData() {
        mTitleViewAddressTitle.setTitleText(getResources().getString(R.string.txt_main_friendlist));
        friendList = new ArrayList<>();
        mFindUserListModel = new FindUserListModel(getActivity());
        mFindUserListModel.setFindUserListListenerCallBack(this);
        queryApprovedListModel = new QueryApprovedListModel(getActivity());
        queryApprovedListModel.setQueryApprovedListListenerCallBack(this);
        queryAddFriendModel = new QueryAddFriendModel(getActivity());
        queryAddFriendModel.setQueryAddFriendListenerCallBack(this);
        mLvAddressFriendList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                FriendListEntity.ContentBean contentBean = friendList.get(position);
                FriendInfoEntity friendInfoEntity = new FriendInfoEntity();
                friendInfoEntity.setUserId(contentBean.getUserId());
                friendInfoEntity.setNickName(contentBean.getNickName());
                friendInfoEntity.setUserIcon(contentBean.getUserIcon());
                friendInfoEntity.setUserType(contentBean.getUserType());
                friendInfoEntity.setAddress(contentBean.getAddress());
                friendInfoEntity.setPhone(contentBean.getPhone());
                friendInfoEntity.setSex(contentBean.getSex());
                friendInfoEntity.setRemark(contentBean.getRemark());
                ApplyAffirmActivity.getJumpApplyAffirmActivity(getActivity(), 2, friendInfoEntity);
            }
        });
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_friendlist;
    }

    @Override
    public void onClick(View v) {
        int i = v.getId();
        if (i == R.id.rel_address_search) {
            JumpIntent.jump(getActivity(), ImSearchActivity.class);
        } else if (i == R.id.lil_address_new_friends) {
            JumpIntent.jump(getActivity(), ApplyListActivity.class);
        } else if (i == R.id.lil_address_group) {
            JumpIntent.jump(getActivity(), MineGroupActivity.class);
        }
    }

    public void refreshFriendList() {
        if (null != mFindUserListModel && MyApp.manager.getLoginState()) {
            mFindUserListModel.getFindUserList();
        }
    }

    @Override
    public void onFindUserList(FriendListEntity friendListEntity) {
        friendList.clear();
        if (friendListEntity.getCode() == NUM_0) {
            friendList.addAll(friendListEntity.getContent());
        }
        if (mNearestContactAdapter == null) {
            mNearestContactAdapter = new FriendListAdapter(getActivity(), friendList, ChatType.C2C);
            mLvAddressFriendList.setAdapter(mNearestContactAdapter);
        } else {
            mNearestContactAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if (MyApp.manager.getLoginState()) {
            mFindUserListModel.getFindUserList();
            queryApprovedListModel.getQueryApprovedList();
        }
    }

    /**
     * 条目点击事件
     */
    private class OnFriendItemClick implements AdapterView.OnItemClickListener {

        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            FriendListEntity.ContentBean contentBean = friendList.get(position);
            Bundle bundle = new Bundle();
            bundle.putSerializable("friendListEntity", contentBean);
            Intent intent = new Intent();
            intent.putExtras(bundle);
            intent.setClass(getActivity(), FriendMsgFragment.class);
            startActivity(intent);
        }
    }

    @Override
    public void onQueryApprovedList(ApplyGroupEntity applyGroupEntity) {
        applyCount = 0;
        if (applyGroupEntity.getCode() == NUM_0 && applyGroupEntity.getContent() != null) {
            for (int i = 0; i < applyGroupEntity.getContent().size(); i++) {
                if (String.valueOf(NUM_2).equals(applyGroupEntity.getContent().get(i).getState())) {
                    applyCount++;
                }
            }
        }
        queryAddFriendModel.getQueryAddFriend();
    }

    @Override
    public void onQueryAddFriend(ApplyEntity applyEntity) {
        if (applyEntity.getCode() == NUM_0 && applyEntity.getContent() != null) {
            for (int i = 0; i < applyEntity.getContent().size(); i++) {
                if (String.valueOf(NUM_2).equals(applyEntity.getContent().get(i).getState())) {
                    applyCount++;
                }
            }
        }
        if (applyCount > 0) {
            mTxtAddressNewFriendNum.setVisibility(View.VISIBLE);
            mTxtAddressNewFriendNum.setText(String.valueOf(applyCount));
        } else {
            mTxtAddressNewFriendNum.setVisibility(View.GONE);
        }
        RxBus.get().send(RxBusConstants.CONST_REFRESH_FRIEND_NUM, String.valueOf(applyCount));
    }
}
