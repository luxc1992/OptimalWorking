package com.yst.im.imchatlibrary.ui.activity;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.yst.im.imchatlibrary.R;
import com.yst.im.imchatlibrary.base.BaseActivity;
import com.yst.im.imchatlibrary.base.baseview.AbstractTitleView;
import com.yst.im.imchatlibrary.ui.fragment.ApplyFriendListFragment;
import com.yst.im.imchatlibrary.ui.fragment.ApplyGroupListFragment;
import com.yst.im.imchatlibrary.utils.BaseUtils;
import com.yst.im.imsdk.utils.RxBusConstants;

import java.util.ArrayList;
import java.util.List;

import gorden.rxbus2.Subscribe;
import gorden.rxbus2.ThreadMode;

import static com.yst.im.imsdk.MessageConstant.NUM_0;

/**
 * 申請頁
 *
 * @author qinchaoshuai
 * @version 1.0.0
 * @date 2018/3/29.
 */

public class ApplyListActivity extends BaseActivity implements View.OnClickListener{
    private AbstractTitleView titleViewApplyTitle;
    private ViewPager viewPagerapplyList;
    private List<Fragment> fragmentList;
    private RelativeLayout relNewFriend;
    private RelativeLayout relNewGroup;
    private TextView txtNewFriend;
    private TextView txtNewGroup;
    private TextView txtApplyFriendNum;
    private TextView txtApplyGroupNum;
    private View viewLineNewFriend;
    private View viewLineNewGroup;

    @Override
    protected int getLayout() {
        return R.layout.activity_apply_list;
    }

    @Override
    protected int getStatusColor() {
        return R.color.colorWrite;
    }

    @Override
    protected void initView() {
        BaseUtils.setStatusTextColor(false, this);
        titleViewApplyTitle = (AbstractTitleView) findViewById(R.id.titleView_apply_title);
        titleViewApplyTitle.getLeftBackTextTv().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        viewPagerapplyList = (ViewPager) findViewById(R.id.viewpager_apply_list);
        relNewFriend = (RelativeLayout) findViewById(R.id.rel_new_friend);
        relNewGroup = (RelativeLayout) findViewById(R.id.rel_new_group);
        txtNewFriend = (TextView) findViewById(R.id.txt_new_friend);
        txtNewGroup = (TextView) findViewById(R.id.txt_new_group);
        txtApplyFriendNum = (TextView) findViewById(R.id.txt_apply_friend_num);
        txtApplyGroupNum = (TextView) findViewById(R.id.txt_apply_group_num);
        viewLineNewFriend = findViewById(R.id.view_line_new_friend);
        viewLineNewGroup = findViewById(R.id.view_line_new_group);
    }

    @Override
    protected boolean getStatus() {
        return false;
    }

    @Override
    protected void initData() {
        fragmentList = new ArrayList<>();
        ApplyFriendListFragment friendFragemnt = new ApplyFriendListFragment();
        ApplyGroupListFragment groupFragemnt = new ApplyGroupListFragment();
        fragmentList.add(friendFragemnt);
        fragmentList.add(groupFragemnt);
        viewPagerapplyList.setAdapter(new MyPageAdapter(getSupportFragmentManager()));
        relNewFriend.setOnClickListener(this);
        relNewGroup.setOnClickListener(this);
        txtNewFriend.setSelected(true);
        txtNewGroup.setSelected(false);
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        if (id == R.id.rel_new_friend) {
            viewPagerapplyList.setCurrentItem(0);
            txtNewFriend.setSelected(true);
            txtNewGroup.setSelected(false);
            viewLineNewFriend.setVisibility(View.VISIBLE);
            viewLineNewGroup.setVisibility(View.INVISIBLE);
        }else if (id == R.id.rel_new_group) {
            viewPagerapplyList.setCurrentItem(1);
            txtNewFriend.setSelected(false);
            txtNewGroup.setSelected(true);
            viewLineNewFriend.setVisibility(View.INVISIBLE);
            viewLineNewGroup.setVisibility(View.VISIBLE);
        }
    }

    private class MyPageAdapter extends FragmentPagerAdapter{
        @Override
        public Fragment getItem(int position) {
            return fragmentList.get(position);
        }

        @Override
        public int getCount() {
            return fragmentList.size();
        }

        public MyPageAdapter(FragmentManager fm) {
            super(fm);
        }
    }

    /**
     * 待审批好友数量
     *
     * @param count
     */
    @Subscribe(threadMode = ThreadMode.MAIN, code = RxBusConstants.CONST_REFRESH_APPLU_FRIEND_NUM)
    public void getApplyListFriendCountNum(String count) {
        if (String.valueOf(NUM_0).equals(count)) {
            txtApplyFriendNum.setVisibility(View.GONE);
        } else {
            txtApplyFriendNum.setVisibility(View.VISIBLE);
            txtApplyFriendNum.setText(String.valueOf(count));
        }
    }

    /**
     * 代申请批消息数量
     *
     * @param count
     */
    @Subscribe(threadMode = ThreadMode.MAIN, code = RxBusConstants.CONST_REFRESH_APPLY_GROUP_NUM)
    public void getApplyListGroupCountNum(String count) {
        if (String.valueOf(NUM_0).equals(count)) {
            txtApplyGroupNum.setVisibility(View.GONE);
        } else {
            txtApplyGroupNum.setVisibility(View.VISIBLE);
            txtApplyGroupNum.setText(String.valueOf(count));
        }
    }
}
