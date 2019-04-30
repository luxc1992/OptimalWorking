package com.yst.onecity.activity.home;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.LinearLayout;

import com.yst.basic.framework.base.BaseActivity;
import com.yst.im.imchatlibrary.ui.fragment.NearestContactChatFragment;
import com.yst.onecity.R;
import com.yst.onecity.activity.mine.message.MessageActivity;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.OnClick;

import static com.yst.onecity.Constant.NO0;
import static com.yst.onecity.Constant.NO1;
import static com.yst.onecity.Constant.NO2;

/**
 * 平台通知
 *
 * @author qinchaoshuai
 * @version 1.0.0
 * @date 2018/5/17.
 */

public class PlatformInformActivity extends BaseActivity {
    @BindView(R.id.lil_news_inform)
    LinearLayout lilNewsInform;
    @BindView(R.id.lil_new_attention)
    LinearLayout lilNewAttention;
    @BindView(R.id.lil_interaction)
    LinearLayout lilInteraction;
    @BindView(R.id.recent_contacts_viewpager)
    ViewPager viewpagerMessage;

    private NearestContactChatFragment mNearestContactChatFragment;
    private CommomFragmentPagerAdapter adapter;
    private ArrayList<Fragment> fragments = new ArrayList<>();

    /**
     * 页面跳转
     *
     * @param context 上下文
     */
    public static void getJumpApplyAffirmActivity(Context context) {
        Intent intent = new Intent(context, PlatformInformActivity.class);
        context.startActivity(intent);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_platform_inform;
    }

    @Override
    public void initData() {
        initTitleBar("平台通知");
        mNearestContactChatFragment = new NearestContactChatFragment();
        fragments.add(mNearestContactChatFragment);
        adapter = new CommomFragmentPagerAdapter(getSupportFragmentManager(), fragments);
        viewpagerMessage.setAdapter(adapter);
    }

    @OnClick({R.id.lil_news_inform, R.id.lil_new_attention, R.id.lil_interaction})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.lil_news_inform:
                MessageActivity.getJumpApplyAffirmActivity(this, NO0);
                break;
            case R.id.lil_new_attention:
                MessageActivity.getJumpApplyAffirmActivity(this, NO1);
                break;
            case R.id.lil_interaction:
                MessageActivity.getJumpApplyAffirmActivity(this, NO2);
                break;
            default:
                break;
        }
    }

    public class CommomFragmentPagerAdapter extends FragmentPagerAdapter {
        private ArrayList<Fragment> fragments;

        public CommomFragmentPagerAdapter(FragmentManager fm, ArrayList<Fragment> fragments) {
            super(fm);
            notifyDataSetChanged();
            this.fragments = fragments;

        }
        @Override
        public Fragment getItem(int position) {
            return fragments.get(position);
        }

        @Override
        public int getCount() {
            return fragments.size();
        }

    }
}
