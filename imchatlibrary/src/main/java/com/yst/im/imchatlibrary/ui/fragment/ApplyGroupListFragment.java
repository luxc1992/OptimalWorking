package com.yst.im.imchatlibrary.ui.fragment;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;

import com.baoyz.swipemenulistview.SwipeMenu;
import com.baoyz.swipemenulistview.SwipeMenuCreator;
import com.baoyz.swipemenulistview.SwipeMenuItem;
import com.baoyz.swipemenulistview.SwipeMenuListView;
import com.bumptech.glide.Glide;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.yst.im.imchatlibrary.MyApp;
import com.yst.im.imchatlibrary.R;
import com.yst.im.imchatlibrary.base.BaseFragment;
import com.yst.im.imchatlibrary.bean.ApplyGroupEntity;
import com.yst.im.imchatlibrary.bean.BaseEntity;
import com.yst.im.imchatlibrary.bean.FriendInfoEntity;
import com.yst.im.imchatlibrary.model.CheckAddFriendModel;
import com.yst.im.imchatlibrary.model.CheckJoinGroupModel;
import com.yst.im.imchatlibrary.model.DeleteFriendApplyModel;
import com.yst.im.imchatlibrary.model.DeleteGroupApplyModel;
import com.yst.im.imchatlibrary.model.QueryApprovedListModel;
import com.yst.im.imchatlibrary.ui.activity.ApplyAffirmActivity;
import com.yst.im.imchatlibrary.utils.ImToastUtil;
import com.yst.im.imsdk.utils.RxBusConstants;
import com.zhy.adapter.abslistview.CommonAdapter;
import com.zhy.adapter.abslistview.ViewHolder;

import java.util.ArrayList;
import java.util.List;

import gorden.rxbus2.RxBus;

import static com.scwang.smartrefresh.layout.util.DensityUtil.dp2px;
import static com.yst.im.imsdk.MessageConstant.NUM_0;
import static com.yst.im.imsdk.MessageConstant.NUM_2;

/**
 * 申請列表 群组
 *
 * @author qinchaoshuai
 * @version 1.0.0
 * @date 2018/3/29.
 */

public class ApplyGroupListFragment extends BaseFragment implements QueryApprovedListModel.QueryApprovedListListenerCallBack,
        CheckJoinGroupModel.CheckJoinGroupListenerCallBack,
        DeleteGroupApplyModel.DeleteGroupApplyListenerCallBack {

    private SwipeMenuListView mSmlvApplyList;
    private SmartRefreshLayout refApplyRefresh;
    private List<ApplyGroupEntity.ContentBean> applyEntities;
    private QueryApprovedListModel queryApprovedListModel;
    private CheckJoinGroupModel checkJoinGroupModel;
    private DeleteGroupApplyModel deleteGroupApplyModel;
    private CommonAdapter<ApplyGroupEntity.ContentBean> commonAdapter;

    @Override
    public void initView(View mView) {
        refApplyRefresh = mView.findViewById(R.id.ref_apply_refresh);
        mSmlvApplyList = mView.findViewById(R.id.swipemenu_apply_listview);
        initSwipeMenuListView();
    }

    @Override
    public void initData() {
        checkJoinGroupModel = new CheckJoinGroupModel(getActivity());
        checkJoinGroupModel.setCheckJoinGroupListenerCallBack(this);
        deleteGroupApplyModel = new DeleteGroupApplyModel(getActivity());
        deleteGroupApplyModel.setDeleteGroupApplyListenerCallBack(this);
        queryApprovedListModel = new QueryApprovedListModel(getActivity());
        queryApprovedListModel.setQueryApprovedListListenerCallBack(this);
        applyEntities = new ArrayList<>();
        mSmlvApplyList.setAdapter(getApplyAdapter());
        mSmlvApplyList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                ApplyGroupEntity.ContentBean contentBean = applyEntities.get(i);
                FriendInfoEntity friendInfoEntity = new FriendInfoEntity();
                friendInfoEntity.setUserId(contentBean.getUserId());
                friendInfoEntity.setNickName(contentBean.getNickName());
                friendInfoEntity.setUserIcon(contentBean.getUserIcon());
                friendInfoEntity.setGroupChatId(contentBean.getGroupChatId());
                friendInfoEntity.setCurrentMemberNumber(contentBean.getCurrentMemberNumber());
                friendInfoEntity.setAddress(contentBean.getAddress());
                friendInfoEntity.setSex(contentBean.getSex());
                friendInfoEntity.setRemark(contentBean.getNickName());
                friendInfoEntity.setState(contentBean.getState());
                friendInfoEntity.setDescrib(contentBean.getDescrib());
                friendInfoEntity.setGroupName(contentBean.getGroupName());
                ApplyAffirmActivity.getJumpApplyAffirmActivity(getActivity(), 1, friendInfoEntity);
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
                deleteGroupApplyModel.getDeleteGroupApply(applyEntities.get(position).getGroupChatId(), applyEntities.get(position).getUserId());
                return false;
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        queryApprovedListModel.getQueryApprovedList();
    }

    private CommonAdapter getApplyAdapter() {
        commonAdapter = new CommonAdapter<ApplyGroupEntity.ContentBean>(getActivity(), R.layout.item_apply_list, applyEntities) {
            @Override
            protected void convert(ViewHolder viewHolder, final ApplyGroupEntity.ContentBean item, int position) {
                if (String.valueOf(NUM_2).equals(item.getState())) {
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
                Glide.with(MyApp.getInstance())
                        .load(item.getUserIcon())
                        .error(R.mipmap.icon_default)
                        .into(imgApplyIcon);

                viewHolder.setText(R.id.txt_apply_name, item.getNickName());
                viewHolder.setText(R.id.txt_apply_msg, "申请加入 " + item.getGroupName());
                viewHolder.setText(R.id.txt_apply_group_msg, item.getDescrib());
                txtApplyGroupMsg.setVisibility(View.VISIBLE);
                viewHolder.setOnClickListener(R.id.txt_sure_apply, new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        checkJoinGroupModel.getCheckJoinGroup(item.getUserId(), "0", item.getGroupChatId());
                    }
                });
                viewHolder.setOnClickListener(R.id.txt_no_apply, new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        checkJoinGroupModel.getCheckJoinGroup(item.getUserId(), "1", item.getGroupChatId());
                    }
                });
            }
        };
        return commonAdapter;
    }

    @Override
    public void onCheckJoinGroup(BaseEntity baseEntity) {
        if (baseEntity.getCode() == NUM_0) {
            queryApprovedListModel.getQueryApprovedList();
        }
        ImToastUtil.showShortToast(MyApp.getInstance(), baseEntity.getMsg());
    }

    @Override
    public void onDeleteGroupApply(BaseEntity baseEntity) {
        if (baseEntity.getCode() == NUM_0) {
            queryApprovedListModel.getQueryApprovedList();
        }
        ImToastUtil.showShortToast(MyApp.getInstance(), baseEntity.getMsg());
    }

    @Override
    public void onQueryApprovedList(ApplyGroupEntity applyGroupEntity) {
        if (applyGroupEntity.getCode() == NUM_0) {
            applyEntities.clear();
            if (commonAdapter != null) {
                commonAdapter.notifyDataSetChanged();
            }
            if (applyGroupEntity.getContent() != null) {
                applyEntities.addAll(applyGroupEntity.getContent());
                commonAdapter.notifyDataSetChanged();

                int applyCount = 0;
                for (int i = 0; i < applyEntities.size(); i++) {
                    if (String.valueOf(NUM_2).equals(applyEntities.get(i).getState())) {
                        applyCount++;
                    }
                }
                RxBus.get().send(RxBusConstants.CONST_REFRESH_APPLY_GROUP_NUM, String.valueOf(applyCount));
            }
        }
    }
}
