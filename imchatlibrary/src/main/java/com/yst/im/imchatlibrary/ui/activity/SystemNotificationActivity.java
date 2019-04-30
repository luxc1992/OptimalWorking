package com.yst.im.imchatlibrary.ui.activity;

import android.os.Build;
import android.support.v4.content.ContextCompat;
import android.text.Html;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.yst.im.imchatlibrary.R;
import com.yst.im.imchatlibrary.adapter.SystemNotificationAdapter;
import com.yst.im.imchatlibrary.base.BaseActivity;
import com.yst.im.imchatlibrary.base.baseview.AbstractTitleView;
import com.yst.im.imchatlibrary.bean.BaseEntity;
import com.yst.im.imchatlibrary.bean.RecentContactEntity;
import com.yst.im.imchatlibrary.model.SystematicNotificationModel;
import com.yst.im.imchatlibrary.model.UpdateMessageStateModel;
import com.yst.im.imchatlibrary.utils.BaseUtils;
import com.yst.im.imchatlibrary.utils.SignUtils;
import com.yst.im.imsdk.bean.ContactsEntity;

import java.util.ArrayList;
import java.util.List;

import static com.yst.im.imsdk.MessageConstant.NUM_0;

/**
 * 系统通知啥
 *
 * @author qinchaoshuai
 * @version 1.0.0
 * @date 2018/5/4.
 */

public class SystemNotificationActivity extends BaseActivity implements
        SystematicNotificationModel.systematicNotificationListenerCallBack,
        UpdateMessageStateModel.UpdateMessageStateListenerCallBack {

    private AbstractTitleView titleViewSystemNotifi;
    private ListView lisViewSystemNotifi;
    private SystematicNotificationModel mSystematicNotificationModel;
    private SystemNotificationAdapter mSystemNotificationAdapter;
    private List<ContactsEntity> mNearestList;
    private UpdateMessageStateModel mUpdateMessageStateModel;

    @Override
    public int getLayout() {
        return R.layout.activity_system_notifi;
    }

    @Override
    protected int getStatusColor() {
        return 0;
    }

    @Override
    protected void initView() {
        BaseUtils.setStatusTextColor(false, SystemNotificationActivity.this);
        titleViewSystemNotifi = (AbstractTitleView) findViewById(R.id.titleview_system_notifi);
        lisViewSystemNotifi = (ListView) findViewById(R.id.listview_system_notifi);
        titleViewSystemNotifi.setTitleText("系統通知");

        titleViewSystemNotifi.getLeftBackImageTv().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mUpdateMessageStateModel.updateMessageState("-1", "1", SignUtils.getTimeStamp());
                finish();
            }
        });
    }

    @Override
    protected boolean getStatus() {
        return false;
    }

    @Override
    protected void initData() {
        mNearestList = new ArrayList<>();
        mSystemNotificationAdapter = new SystemNotificationAdapter(SystemNotificationActivity.this, mNearestList);
        lisViewSystemNotifi.setAdapter(mSystemNotificationAdapter);

        mUpdateMessageStateModel = new UpdateMessageStateModel(this);
        mUpdateMessageStateModel.setUpdateMessageStateListenerCallBack(this);
        mSystematicNotificationModel = new SystematicNotificationModel(this);
        mSystematicNotificationModel.setsystematicNotificationListenerCallBack(this);
        mSystematicNotificationModel.systematicNotification();
    }

    @Override
    public void onsystematicNotification(RecentContactEntity recentContactEntity) {
        if (recentContactEntity.getCode() == NUM_0) {
            mNearestList.clear();
            mNearestList.addAll(recentContactEntity.getContent());
            mSystemNotificationAdapter.notifyDataSetChanged();
        }
    }

    /**
     * 设置退出提示： 对退出的设置时间监听
     */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            mUpdateMessageStateModel.updateMessageState("-1", "1", SignUtils.getTimeStamp());
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public void onUpdateMessageState(BaseEntity baseEntity) {

    }
}
