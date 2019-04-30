package com.yst.im.imchatlibrary.ui.activity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.content.ContextCompat;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.yst.im.imchatlibrary.MyApp;
import com.yst.im.imchatlibrary.R;
import com.yst.im.imchatlibrary.base.BaseActivity;
import com.yst.im.imchatlibrary.base.baseview.AbstractTitleView;
import com.yst.im.imchatlibrary.bean.FindUserByPhoneEntity;
import com.yst.im.imchatlibrary.bean.FriendInfoEntity;
import com.yst.im.imchatlibrary.bean.MsgListEntity;
import com.yst.im.imchatlibrary.bean.MyCreatGroupEntity;
import com.yst.im.imchatlibrary.model.AddFriendModel;
import com.yst.im.imchatlibrary.model.FindUserByPhoneModel;
import com.yst.im.imchatlibrary.model.QueryGroupChatModel;
import com.yst.im.imchatlibrary.ui.listener.EditChangedListener;
import com.yst.im.imchatlibrary.utils.BaseUtils;
import com.yst.im.imchatlibrary.utils.Constants;
import com.yst.im.imchatlibrary.utils.ImLog;
import com.yst.im.imchatlibrary.utils.ImToastUtil;
import com.yst.im.imchatlibrary.utils.JumpIntent;
import com.yst.im.imchatlibrary.utils.NoScrollViewPager;
import com.yst.im.imsdk.bean.RxBusEntity;
import com.yst.im.imsdk.utils.RxBusConstants;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.io.IOException;
import java.util.List;

import gorden.rxbus2.RxBus;
import okhttp3.Call;

import static com.yst.im.imchatlibrary.utils.DefendMpUtils.SPACE;
import static com.yst.im.imsdk.MessageConstant.NUM_0;
import static com.yst.im.imsdk.MessageConstant.NUM_1;
import static com.yst.im.imsdk.MessageConstant.NUM_4;

/**
 * 添加 A
 *
 * @author Lierpeng
 * @version 1.0.0
 * @date 2018/4/3.
 */
public class AddActivity extends BaseActivity implements View.OnClickListener,
        FindUserByPhoneModel.FindUserByPhoneListenerCallBack{
    private AbstractTitleView mTitleViewAddTitle;
    private TextView mTxtAddFriend;
    private TextView mTxtAddGroup;
    private EditText mEdtAddInput;
    private FindUserByPhoneModel mFindUserByPhoneModel;
    public static String searchName = "";

    /**
     * 初始化控件
     */
    @Override
    protected int getLayout() {
        return R.layout.activity_add;
    }

    @Override
    protected void initView() {
        mTitleViewAddTitle = (AbstractTitleView) findViewById(R.id.titleView_add_title);
        mTxtAddFriend = (TextView) findViewById(R.id.txt_add_friend);
        mTxtAddGroup = (TextView) findViewById(R.id.txt_add_group);
        mEdtAddInput = (EditText) findViewById(R.id.edt_add_input);
        mTitleViewAddTitle.setTitleText(getResources().getString(R.string.txt_add_frient_adduserbtn));
        mTxtAddGroup.setOnClickListener(this);
        mTxtAddFriend.setOnClickListener(this);
        mTxtAddFriend.setBackgroundColor(ContextCompat.getColor(AddActivity.this, R.color.colorWriteF6));
        mTxtAddGroup.setBackgroundColor(ContextCompat.getColor(AddActivity.this, R.color.colorBlackEB));
        mEdtAddInput.setHint(getResources().getString(R.string.txt_input_friend_hint));
        mTitleViewAddTitle.getLeftBackImageTv().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });    }

    /**
     * 初始化数据
     */
    @Override
    public void initData() {
        mTxtAddFriend.setSelected(true);
        mTxtAddGroup.setSelected(false);
        mFindUserByPhoneModel = new FindUserByPhoneModel(this);
        mFindUserByPhoneModel.setFindUserByPhoneListenerCallBack(this);
        //按下键盘的搜索
        mEdtAddInput.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                String edit = v.getText().toString();
                if ("".equals(edit = edit.replaceAll(SPACE, ""))) {
                    return false;
                }
                if (actionId == EditorInfo.IME_ACTION_SEARCH
                        || actionId == EditorInfo.IME_ACTION_NONE
                        || actionId == EditorInfo.IME_ACTION_GO
                        || actionId == EditorInfo.IME_ACTION_SEND
                        || actionId == EditorInfo.IME_ACTION_UNSPECIFIED
                        ) {
                    if (mTxtAddFriend.isSelected()) {
                        if (!BaseUtils.isMobileNO(mEdtAddInput.getText().toString().trim())) {
                            ImToastUtil.showShortToast(AddActivity.this, "输入手机号有误");
                            return false;
                        }
                        mFindUserByPhoneModel.getFindUserByPhone(mEdtAddInput.getText().toString().trim());
                    } else if (!mTxtAddFriend.isSelected()) {
                        searchName = mEdtAddInput.getText().toString().trim();
                        ImSearchActivity.getJumpFrom(AddActivity.this, ImSearchActivity.JumpIntentFrom.Add);
                    }
                }
                return false;
            }
        });
    }

    @Override
    public void onClick(View v) {
        int i = v.getId();
        if (i == R.id.txt_add_friend) {
            mTxtAddFriend.setBackgroundColor(ContextCompat.getColor(AddActivity.this, R.color.colorWriteF6));
            mTxtAddGroup.setBackgroundColor(ContextCompat.getColor(AddActivity.this, R.color.colorBlackEB));
            mEdtAddInput.setHint(getResources().getString(R.string.txt_input_friend_hint));
            mTxtAddFriend.setSelected(true);
            mTxtAddGroup.setSelected(false);
        } else if (i == R.id.txt_add_group) {
            mTxtAddFriend.setBackgroundColor(ContextCompat.getColor(AddActivity.this, R.color.colorBlackEB));
            mTxtAddGroup.setBackgroundColor(ContextCompat.getColor(AddActivity.this, R.color.colorWriteF6));
            mEdtAddInput.setHint(getResources().getString(R.string.txt_input_group_hint));
            mTxtAddFriend.setSelected(false);
            mTxtAddGroup.setSelected(true);
        }
    }

    @Override
    protected int getStatusColor() {
        return 0;
    }

    @Override
    protected boolean getStatus() {
        return false;
    }

    @Override
    public void onFindUserByPhone(FindUserByPhoneEntity findUserByPhoneEntity) {
        if (findUserByPhoneEntity.getCode() == NUM_0 && findUserByPhoneEntity.getContent() != null && findUserByPhoneEntity.getContent().size() > 0) {
            FindUserByPhoneEntity.ContentBean contentBean = findUserByPhoneEntity.getContent().get(0);
            FriendInfoEntity friendInfoEntity = new FriendInfoEntity();
            friendInfoEntity.setUserId(contentBean.getUserId());
            friendInfoEntity.setNickName(contentBean.getNickName());
            friendInfoEntity.setUserIcon(contentBean.getUserIcon());
            friendInfoEntity.setUserType(contentBean.getUserType());
            friendInfoEntity.setAddress(contentBean.getAddress());
            friendInfoEntity.setPhone(contentBean.getPhone());
            friendInfoEntity.setSex(contentBean.getSex());
            friendInfoEntity.setRemark(contentBean.getRemark());
            friendInfoEntity.setState(contentBean.getState());
            ApplyAffirmActivity.getJumpApplyAffirmActivity(AddActivity.this, 3, friendInfoEntity);
        }
        ImToastUtil.showShortToast(AddActivity.this, findUserByPhoneEntity.getMsg());
    }
}
