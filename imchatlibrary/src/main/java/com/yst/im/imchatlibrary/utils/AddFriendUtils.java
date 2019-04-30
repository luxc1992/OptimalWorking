package com.yst.im.imchatlibrary.utils;

import android.content.Context;
import android.content.Intent;

import com.yst.im.imchatlibrary.MyApp;
import com.yst.im.imchatlibrary.bean.BaseEntity;
import com.yst.im.imchatlibrary.bean.IntentChatEntity;
import com.yst.im.imchatlibrary.model.FriendRegistrationModel;
import com.yst.im.imchatlibrary.model.SelectReationBetweenThePersonsModel;
import com.yst.im.imchatlibrary.ui.activity.ChatScreenActivity;

import static com.yst.im.imsdk.MessageConstant.NUM_0;
import static com.yst.im.imsdk.MessageConstant.NUM_1;

/**
 * @author qinchaoshuai
 * @version 1.0.1
 * @date 2018/6/4.
 */

public class AddFriendUtils implements
        FriendRegistrationModel.FriendRegistrationListenerCallBack,
        SelectReationBetweenThePersonsModel.selectReationBetweenThePersonsCallBack {

    private FriendRegistrationModel mFriendRegistrationModel;
    private SelectReationBetweenThePersonsModel mSelectReationBetweenThePersonsModel;
    private IntentChatEntity mIntentChatEntity;
    private Context mContext;

    public AddFriendUtils(Context context, IntentChatEntity intentChatEntity) {
        mFriendRegistrationModel = new FriendRegistrationModel(context);
        mFriendRegistrationModel.setFriendRegistrationListenerCallBack(this);
        mSelectReationBetweenThePersonsModel = new SelectReationBetweenThePersonsModel(context);
        mSelectReationBetweenThePersonsModel.setselectReationBetweenThePersonsCallBack(this);
        mIntentChatEntity = intentChatEntity;
        mContext = context;
        mSelectReationBetweenThePersonsModel.selectReationBetweenThePersons(mIntentChatEntity.getAcceptId());
    }


    @Override
    public void onFriendRegistration(BaseEntity baseEntity) {
        if (baseEntity.getCode() == NUM_0) {
            Intent intent = new Intent(mContext, ChatScreenActivity.class);
            intent.putExtra("acceptUser", mIntentChatEntity);
            mContext.startActivity(intent);
        } else {
            ImToastUtil.showShortToast(MyApp.getInstance(), baseEntity.getMsg());
        }
    }

    @Override
    public void selectReationBetweenThePersons(BaseEntity baseEntity) {
        if (baseEntity.getCode() == NUM_0) {
            if (String.valueOf(NUM_1).equals(baseEntity.getContent())) {
                mFriendRegistrationModel.getFriendRegistration(mIntentChatEntity.getAcceptId());
            } else {
                Intent intent = new Intent(mContext, ChatScreenActivity.class);
                intent.putExtra("acceptUser", mIntentChatEntity);
                mContext.startActivity(intent);
            }
        } else {
            ImToastUtil.showShortToast(MyApp.getInstance(), baseEntity.getMsg());
        }
    }
}
