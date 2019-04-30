package com.yst.im.imchatlibrary.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.yst.im.imchatlibrary.MyApp;
import com.yst.im.imchatlibrary.R;
import com.yst.im.imchatlibrary.base.BaseActivity;
import com.yst.im.imchatlibrary.base.baseview.AbstractTitleView;
import com.yst.im.imchatlibrary.bean.BaseEntity;
import com.yst.im.imchatlibrary.model.AddFriendModel;
import com.yst.im.imchatlibrary.model.JoinGroupChatModel;
import com.yst.im.imchatlibrary.model.SetNameModel;
import com.yst.im.imchatlibrary.model.UpdateFriendRemarkModel;
import com.yst.im.imchatlibrary.utils.ImToastUtil;
import com.yst.im.imsdk.IntentType;
import com.yst.im.imsdk.ShowType;

import java.io.IOException;

import gorden.rxbus2.RxBus;

import static com.yst.im.imsdk.MessageConstant.NUM_0;
import static com.yst.im.imsdk.utils.RxBusConstants.CONST_UPDATE_NAME;

/**
 * 设置姓名
 *
 * @author qinchaoshuai
 * @version 1.0.0
 * @date 2018/4/1.
 */
public class SettingUserNameActivity extends BaseActivity implements
        SetNameModel.SetNameListenerCallBack,
        UpdateFriendRemarkModel.UpdateFriendRemarkListenerCallBack,
        AddFriendModel.AddFriendListenerCallBack,
        JoinGroupChatModel.JoinGroupListenerCallBack {

    private AbstractTitleView titleViewSetName;
    private EditText etSetName;
    private TextView txtAddFriendMsg;
    private SetNameModel mSetNameModel;
    private String name;
    private String friendId = "";
    public static final int INTENT_SETTING_NAME = 0x0011;
    public static final int INTENT_SETTING_CITY = 0x012;
    private ShowType showType;
    private UpdateFriendRemarkModel updateFriendRemarkModel;
    private AddFriendModel addFriendModel;
    private String titleName;
    private JoinGroupChatModel mJoinGroupChatModel;

    @Override
    protected int getLayout() {
        return R.layout.activity_setting_name;
    }

    @Override
    protected int getStatusColor() {
        return 0;
    }

    /**
     * 页面跳转
     *
     * @param context     跳转方
     * @param intentType  跳转方式
     * @param requestCode 请求码
     */
    public static void getJumpSettingUserNameActivity(Activity context, IntentType intentType, ShowType showType, int requestCode, String friendId, String titleName) {
        Intent intent = new Intent(context, SettingUserNameActivity.class);
        intent.putExtra("showType", showType);
        intent.putExtra("friendId", friendId);
        intent.putExtra("titleName", titleName);
        if (intentType == IntentType.startActivity) {
            context.startActivity(intent);
        } else {
            context.startActivityForResult(intent, requestCode);
        }
    }

    @Override
    protected void initView() {
        MyApp.addActivity(this);
        titleViewSetName = (AbstractTitleView) findViewById(R.id.titleview_set_name);
        etSetName = (EditText) findViewById(R.id.et_set_nameet_set_name);
        txtAddFriendMsg = (TextView) findViewById(R.id.txt_add_friend_msg);

        titleViewSetName.getLeftBackTextTv().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        showType = (ShowType) getIntent().getSerializableExtra("showType");
        friendId = getIntent().getStringExtra("friendId");
        titleName = getIntent().getStringExtra("titleName");
        titleViewSetName.setTitleText(titleName);
        if (showType.equals(ShowType.CallBack)) {
            titleViewSetName.setRightTvTextColor(ContextCompat.getColor(this, R.color.colorBlue439));
        } else if (showType.equals(ShowType.Remark)) {
            titleViewSetName.setLeftTvText("取消");
        } else if (showType.equals(ShowType.Descrb)) {
            titleViewSetName.setRightTvText("发送");
            txtAddFriendMsg.setVisibility(View.VISIBLE);
            name = "我是" + MyApp.manager.getNickName();
            etSetName.setText(name);
        } else if (showType.equals(ShowType.GroupDescrb)) {
            txtAddFriendMsg.setVisibility(View.VISIBLE);
            etSetName.setText(name);
            titleViewSetName.setRightTvText("发送");
            txtAddFriendMsg.setVisibility(View.VISIBLE);
            txtAddFriendMsg.setText("你需要发送验证申请，等待群主通过");
        }
        titleViewSetName.getRightTextTv().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                name = etSetName.getText().toString();
                if (TextUtils.isEmpty(name)) {
                    if (showType.equals(ShowType.Remark)) {
                        ImToastUtil.showShortToast(SettingUserNameActivity.this, "请输入备注名");
                    } else if (showType.equals(ShowType.Descrb) || showType.equals(ShowType.GroupDescrb)) {
                        name = "我是" + MyApp.manager.getNickName();
                    } else {
                        ImToastUtil.showShortToast(SettingUserNameActivity.this, "请输入名称");
                    }
                    return;
                }
                if (showType.equals(ShowType.CallBack)) {
                    Intent intent = new Intent();
                    intent.putExtra("settingName", name);
                    setResult(INTENT_SETTING_NAME, intent);
                    finish();
                } else if (showType.equals(ShowType.Interface)) {
                    mSetNameModel.getSetName(name);
                } else if (showType.equals(ShowType.Remark)) {
                    updateFriendRemarkModel.getUpdateFriendRemark(friendId, name);
                } else if (showType.equals(ShowType.GroupDescrb)) {
                    try {
                        mJoinGroupChatModel.getJoinGroupChat(name, friendId);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                } else {
                    addFriendModel.getAddFriend(friendId, name);
                }
            }
        });
    }

    @Override
    protected boolean getStatus() {
        return true;
    }

    @Override
    protected void initData() {
        mSetNameModel = new SetNameModel(this);
        mSetNameModel.setSetNameListenerCallBack(this);
        updateFriendRemarkModel = new UpdateFriendRemarkModel(this);
        updateFriendRemarkModel.setUpdateFriendRemarkListenerCallBack(this);
        addFriendModel = new AddFriendModel(this);
        addFriendModel.setAddFriendListenerCallBack(this);
        mJoinGroupChatModel = new JoinGroupChatModel(this);
        mJoinGroupChatModel.setJoinGroupListenerCallBack(this);
    }

    @Override
    public void onSetName(BaseEntity baseEntity) {
        if (baseEntity.getCode()==NUM_0) {
            MyApp.manager.setNickName(etSetName.getText().toString());
            Intent intent = new Intent();
            intent.putExtra("settingName", name);
            setResult(INTENT_SETTING_NAME, intent);
            finish();
        }
    }

    @Override
    public void onUpdateFriendRemark(BaseEntity baseEntity) {
        ImToastUtil.showShortToast(this, baseEntity.getMsg());
        if (baseEntity.getCode() == NUM_0) {
            RxBus.get().send(CONST_UPDATE_NAME,name);
            finish();
        }
    }

    @Override
    public void onAddFriend(BaseEntity baseEntity) {
        ImToastUtil.showShortToast(this, baseEntity.getMsg());
        if (baseEntity.getCode() == NUM_0) {
            MyApp.quiteActivity();
            finish();
        }
    }


    @Override
    public void onJoinGroupUser(BaseEntity baseEntity) {
        ImToastUtil.showShortToast(this, baseEntity.getMsg());
        if (baseEntity.getCode() == NUM_0) {
            MyApp.quiteActivity();
            finish();
        }
    }
}