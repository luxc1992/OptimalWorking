package com.yst.onecity.activity.mine.message;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.widget.RelativeLayout;

import com.yst.basic.framework.App;
import com.yst.basic.framework.base.BaseActivity;
import com.yst.basic.framework.utils.ToastUtils;
import com.yst.onecity.R;
import com.yst.onecity.adapter.CommomFragmentPagerAdapter;
import com.yst.onecity.bean.MsgBean;
import com.yst.onecity.fragment.message.HuDongFragment;
import com.yst.onecity.fragment.message.NewAttentionFragment;
import com.yst.onecity.fragment.message.NoticeFragment;
import com.yst.onecity.http.AbstractNetWorkCallback;
import com.yst.onecity.http.RequestApi;
import com.yst.onecity.utils.MyLog;
import com.yst.onecity.view.viewpagerindicator.ViewPagerIndicator;

import java.util.ArrayList;

import butterknife.BindView;

import static com.yst.onecity.Constant.NO1;

/**
 * 消息通知页面
 *
 * @author jiaofan
 * @version 1.0.1
 * @date 2018/02/23
 */
public class MessageActivity extends BaseActivity {

    @BindView(R.id.indicator_message)
    ViewPagerIndicator indicatorMessage;
    @BindView(R.id.rel_message)
    RelativeLayout relMessage;
    @BindView(R.id.viewpager_message)
    ViewPager viewpagerMessage;
    private ArrayList<Fragment> fragments = new ArrayList<>();
    private ArrayList<String> sortList = new ArrayList<>();
    private CommomFragmentPagerAdapter adapter;
    private NoticeFragment mNoticeFragment;
    private HuDongFragment mHuDongFragment;
    private NewAttentionFragment mNewAttentionFragment;
    private int index;

    /**
     * 页面跳转
     *
     * @param context 上下文
     * @param index   页面索引
     */
    public static void getJumpApplyAffirmActivity(Context context, int index) {
        Intent intent = new Intent(context, MessageActivity.class);
        intent.putExtra("index", index);
        context.startActivity(intent);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_message;
    }

    @Override
    public void initData() {
        index = getIntent().getIntExtra("index", 0);
        initTitleBar("平台通知");
        sortList.add("消息通知");
        sortList.add("新增关注");
        sortList.add("互动");
        fragments.add(new NoticeFragment());
        fragments.add(new NewAttentionFragment());
        fragments.add(new HuDongFragment());
        adapter = new CommomFragmentPagerAdapter(getSupportFragmentManager(), fragments, sortList);
        viewpagerMessage.setOffscreenPageLimit(2);
        viewpagerMessage.setAdapter(adapter);
        viewpagerMessage.setCurrentItem(index);
        indicatorMessage.bindViewPager(viewpagerMessage, true);
        indicatorMessage.onVPageSelected(index);
//        getMessageState();
    }

    /**
     * 获取消息阅读状态
     */
    private void getMessageState() {
        RequestApi.getMessageState(
                App.manager.getPhoneNum(),
                App.manager.getUuid(),
                new AbstractNetWorkCallback<MsgBean>() {
                    @Override
                    public void onSuccess(MsgBean msgBean) {
                        if (msgBean.getCode() == NO1) {
                            MyLog.e("okhttp", "已阅");
                        }
                    }

                    @Override
                    public void onError(String errorMsg) {
                        ToastUtils.show(errorMsg);
                    }
                });
    }

}