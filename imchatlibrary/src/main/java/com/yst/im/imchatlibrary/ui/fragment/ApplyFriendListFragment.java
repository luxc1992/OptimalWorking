package com.yst.im.imchatlibrary.ui.fragment;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;

import com.baoyz.swipemenulistview.SwipeMenu;
import com.baoyz.swipemenulistview.SwipeMenuCreator;
import com.baoyz.swipemenulistview.SwipeMenuItem;
import com.baoyz.swipemenulistview.SwipeMenuListView;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.squareup.picasso.Picasso;
import com.yst.im.imchatlibrary.MyApp;
import com.yst.im.imchatlibrary.R;
import com.yst.im.imchatlibrary.base.BaseFragment;
import com.yst.im.imchatlibrary.bean.ApplyEntity;
import com.yst.im.imchatlibrary.bean.BaseEntity;
import com.yst.im.imchatlibrary.bean.FindUserByPhoneEntity;
import com.yst.im.imchatlibrary.bean.FriendInfoEntity;
import com.yst.im.imchatlibrary.bean.MyCreatGroupEntity;
import com.yst.im.imchatlibrary.model.CheckAddFriendModel;
import com.yst.im.imchatlibrary.model.DeleteFriendApplyModel;
import com.yst.im.imchatlibrary.model.QueryAddFriendModel;
import com.yst.im.imchatlibrary.model.QueryApprovedListModel;
import com.yst.im.imchatlibrary.model.SearchCreatedGroupChatModel;
import com.yst.im.imchatlibrary.ui.activity.ApplyAffirmActivity;
import com.yst.im.imchatlibrary.ui.activity.SettingLoginPwdActivity;
import com.yst.im.imchatlibrary.utils.ImLog;
import com.yst.im.imchatlibrary.utils.ImToastUtil;
import com.yst.im.imchatlibrary.utils.JumpIntent;
import com.yst.im.imsdk.utils.RxBusConstants;
import com.zhy.adapter.abslistview.CommonAdapter;
import com.zhy.adapter.abslistview.ViewHolder;


import java.util.ArrayList;
import java.util.List;

import gorden.rxbus2.RxBus;

import static com.scwang.smartrefresh.layout.util.DensityUtil.dp2px;
import static com.yst.im.imsdk.MessageConstant.NUM_0;
import static com.yst.im.imsdk.MessageConstant.NUM_10;
import static com.yst.im.imsdk.MessageConstant.NUM_2;

/**
 * 申請列表 好友
 *
 * @author qinchaoshuai
 * @version 1.0.0
 * @date 2018/3/29.
 */

public class ApplyFriendListFragment extends BaseFragment implements QueryAddFriendModel.QueryAddFriendListenerCallBack,
        CheckAddFriendModel.CheckAddFriendListenerCallBack,
        DeleteFriendApplyModel.DeleteFriendApplyListenerCallBack {

    private SwipeMenuListView mSmlvApplyList;
    private SmartRefreshLayout refApplyRefresh;
    private List<ApplyEntity.ContentBean> applyEntities;
    private QueryAddFriendModel queryAddFriendModel;
    private CheckAddFriendModel checkAddFriendModel;
    private DeleteFriendApplyModel deleteFriendApplyModel;
    private CommonAdapter<ApplyEntity.ContentBean> commonAdapter;

    @Override
    public void initView(View mView) {
        refApplyRefresh = mView.findViewById(R.id.ref_apply_refresh);
        mSmlvApplyList = mView.findViewById(R.id.swipemenu_apply_listview);
        initSwipeMenuListView();
    }

    @Override
    public void initData() {
        queryAddFriendModel = new QueryAddFriendModel(getActivity());
        queryAddFriendModel.setQueryAddFriendListenerCallBack(this);
        checkAddFriendModel = new CheckAddFriendModel(getActivity());
        checkAddFriendModel.setCheckAddFriendListenerCallBack(this);
        deleteFriendApplyModel = new DeleteFriendApplyModel(getActivity());
        deleteFriendApplyModel.setFindUserListenerCallBack(this);
        applyEntities = new ArrayList<>();
        mSmlvApplyList.setAdapter(getApplyAdapter());
        mSmlvApplyList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                ApplyEntity.ContentBean contentBean = applyEntities.get(i);
                FriendInfoEntity friendInfoEntity = new FriendInfoEntity();
                friendInfoEntity.setUserId(contentBean.getUserId());
                friendInfoEntity.setNickName(contentBean.getFriendName());
                friendInfoEntity.setUserIcon(contentBean.getUserIcon());
                friendInfoEntity.setAddress(contentBean.getAddress());
                friendInfoEntity.setSex(contentBean.getSex());
                friendInfoEntity.setRemark(contentBean.getFriendName());
                friendInfoEntity.setState(contentBean.getState());
                friendInfoEntity.setDescrib(contentBean.getDescrib());
                ApplyAffirmActivity.getJumpApplyAffirmActivity(getActivity(), 0, friendInfoEntity);
            }
        });
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_apply_list;
    }

    /**
     * 初始化侧滑listview
     */
    private void initSwipeMenuListView() {
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
        mSmlvApplyList.setMenuCreator(creator);
        // Left
        mSmlvApplyList.setSwipeDirection(SwipeMenuListView.DIRECTION_LEFT);

        mSmlvApplyList.setOnMenuItemClickListener(new SwipeMenuListView.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(int position, SwipeMenu menu, int index) {
                deleteFriendApplyModel.getDeleteFriendApply(applyEntities.get(position).getUserId());
                return false;
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        queryAddFriendModel.getQueryAddFriend();
    }

    private CommonAdapter getApplyAdapter() {
        commonAdapter = new CommonAdapter<ApplyEntity.ContentBean>(getActivity(), R.layout.item_apply_list, applyEntities) {
            @Override
            protected void convert(ViewHolder viewHolder, final ApplyEntity.ContentBean item, int position) {
                if (TextUtils.isEmpty(item.getState()) || String.valueOf(NUM_2).equals(item.getState())) {
                    viewHolder.setVisible(R.id.ll_set_apply, true);
                    viewHolder.setVisible(R.id.txt_apply_status, false);
                } else if (String.valueOf(NUM_0).equals(item.getState())) {
                    viewHolder.setVisible(R.id.ll_set_apply, false);
                    viewHolder.setVisible(R.id.txt_apply_status, true);
                    viewHolder.setText(R.id.txt_apply_status, "已同意");
                } else {
                    viewHolder.setVisible(R.id.ll_set_apply, false);
                    viewHolder.setVisible(R.id.txt_apply_status, true);
                    viewHolder.setText(R.id.txt_apply_status, "已拒绝");
                }
                TextView txtApplyGroupMsg = viewHolder.getView(R.id.txt_apply_group_msg);
                ImageView imgApplyIcon = viewHolder.getView(R.id.img_apply_icon);
                Picasso.with(MyApp.getInstance())
                        .load(item.getUserIcon())
                        .error(R.mipmap.icon_default)
                        .into(imgApplyIcon);
                viewHolder.setText(R.id.txt_apply_name, item.getFriendName());
                viewHolder.setText(R.id.txt_apply_msg, item.getDescrib());
                txtApplyGroupMsg.setVisibility(View.GONE);
                viewHolder.setOnClickListener(R.id.txt_sure_apply, new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        checkAddFriendModel.getCheckAddFriend(item.getUserId(), "0");
                    }
                });
                viewHolder.setOnClickListener(R.id.txt_no_apply, new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        checkAddFriendModel.getCheckAddFriend(item.getUserId(), "1");
                    }
                });
            }
        };
        return commonAdapter;
    }

    @Override
    public void onQueryAddFriend(ApplyEntity applyEntity) {
        applyEntities.clear();
        commonAdapter.notifyDataSetChanged();
        if (applyEntity.getCode() == NUM_0) {
            if (applyEntity.getContent() != null) {
                applyEntities.addAll(applyEntity.getContent());
                commonAdapter.notifyDataSetChanged();
                int applyCount = 0;
                for (int i = 0; i < applyEntity.getContent().size(); i++) {
                    if (String.valueOf(NUM_2).equals(applyEntity.getContent().get(i).getState())) {
                        applyCount++;
                    }
                }
                RxBus.get().send(RxBusConstants.CONST_REFRESH_APPLU_FRIEND_NUM, String.valueOf(applyCount));
            }
        }
    }

    @Override
    public void onCheckAddFriend(BaseEntity baseEntity) {
        if (baseEntity.getCode() == NUM_0) {
            queryAddFriendModel.getQueryAddFriend();
        }
        ImToastUtil.showShortToast(MyApp.getInstance(), baseEntity.getMsg());
    }

    @Override
    public void onDeleteUser(BaseEntity baseEntity) {
        if (baseEntity.getCode() == NUM_0) {
            queryAddFriendModel.getQueryAddFriend();
        }
        ImToastUtil.showShortToast(MyApp.getInstance(), baseEntity.getMsg());
    }
}
