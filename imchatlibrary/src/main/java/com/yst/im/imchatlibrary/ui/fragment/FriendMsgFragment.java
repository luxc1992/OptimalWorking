package com.yst.im.imchatlibrary.ui.fragment;

import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import com.yst.im.imchatlibrary.MyApp;
import com.yst.im.imchatlibrary.R;
import com.yst.im.imchatlibrary.base.BaseFragment;
import com.yst.im.imchatlibrary.base.baseview.AbstractTitleView;
import com.yst.im.imchatlibrary.bean.FindUserEntity;
import com.yst.im.imchatlibrary.model.FindUserModel;
import com.yst.im.imchatlibrary.ui.activity.SettingActivity;
import com.yst.im.imchatlibrary.ui.activity.SettingUserNameActivity;
import com.yst.im.imchatlibrary.ui.activity.SettingUserSexActivity;
import com.yst.im.imchatlibrary.ui.activity.TierOneCitiesActivity;
import com.yst.im.imchatlibrary.utils.JumpIntent;
import com.yst.im.imchatlibrary.widget.ImRoundedImageView;
import com.yst.im.imsdk.IntentType;
import com.yst.im.imsdk.ShowType;

import java.util.List;

import static com.yst.im.imchatlibrary.ui.activity.SettingUserNameActivity.INTENT_SETTING_NAME;

/**
 * 好友信息
 *
 * @author Lierpeng
 * @version 1.0.0
 * @date 2018/4/14.
 */
public class FriendMsgFragment extends BaseFragment implements View.OnClickListener, FindUserModel.FindUserListenerCallBack {
    private AbstractTitleView mTitleViewUserTitle;
    private ImRoundedImageView mImgUserIcon;
    private TextView mTxtUserNickName;
    private LinearLayout mLilUserPortrait;
    private ImageView mImgUserUserIconImg;
    private LinearLayout mLilUserNickName;
    private TextView mTxtUserNiceNameTxt;
    private LinearLayout mLilUserSex;
    private TextView mTxtUserSexTxt;
    private LinearLayout mLilUserRegion;
    private TextView mTxtUserRegionTxt;
    private LinearLayout mLilUserSetting;
    private FindUserModel mFindUserModel;


    @Override
    public void initView(View mView) {
        mTitleViewUserTitle = (AbstractTitleView) mView.findViewById(R.id.titleView_user_title);
        mImgUserIcon = (ImRoundedImageView) mView.findViewById(R.id.img_user_icon);
        mTxtUserNickName = (TextView) mView.findViewById(R.id.txt_user_nickName);
        mLilUserPortrait = (LinearLayout) mView.findViewById(R.id.lil_user_portrait);
        mImgUserUserIconImg = (ImageView) mView.findViewById(R.id.img_user_userIcon_img);
        mLilUserNickName = (LinearLayout) mView.findViewById(R.id.lil_user_nickName);
        mTxtUserNiceNameTxt = (TextView) mView.findViewById(R.id.txt_user_NiceName_txt);
        mLilUserSex = (LinearLayout) mView.findViewById(R.id.lil_user_sex);
        mTxtUserSexTxt = (TextView) mView.findViewById(R.id.txt_user_sex_txt);
        mLilUserRegion = (LinearLayout) mView.findViewById(R.id.lil_user_region);
        mTxtUserRegionTxt = (TextView) mView.findViewById(R.id.txt_user_region_txt);
        mLilUserSetting = (LinearLayout) mView.findViewById(R.id.lil_user_setting);

        mLilUserPortrait.setOnClickListener(this);
        mLilUserNickName.setOnClickListener(this);
        mLilUserSex.setOnClickListener(this);
        mLilUserRegion.setOnClickListener(this);
        mLilUserSetting.setOnClickListener(this);
    }

    /**
     * 初始化数据
     */
    @Override
    public void initData() {
        mTitleViewUserTitle.setTitleText(getResources().getString(R.string.txt_main_other));
        mFindUserModel = new FindUserModel(getActivity());
        mFindUserModel.setFindUserListenerCallBack(this);
    }

    @Override
    public void onResume() {
        super.onResume();
        if(MyApp.manager.getLoginState()){
            mFindUserModel.getFindUser(MyApp.manager.getId());
        }
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_friend_msg;
    }


    @Override
    public void onClick(View v) {
        int i = v.getId();
        if (i == R.id.lil_user_portrait) {
        } else if (i == R.id.lil_user_nickName) {
            SettingUserNameActivity.getJumpSettingUserNameActivity(getActivity(),
                    IntentType.startActivityForResult, ShowType.Interface, INTENT_SETTING_NAME, "","设置名称");
        } else if (i == R.id.lil_user_sex) {
            JumpIntent.jump(getActivity(), SettingUserSexActivity.class);
        } else if (i == R.id.lil_user_region) {
            JumpIntent.jump(getActivity(), TierOneCitiesActivity.class,"address",mTxtUserRegionTxt.getText().toString());
        } else if (i == R.id.lil_user_setting) {
            JumpIntent.jump(getActivity(), SettingActivity.class);
        }
    }

    @Override
    public void onFindUser(List<FindUserEntity.ContentBean> contentBeanList) {
        FindUserEntity.ContentBean contentBean = contentBeanList.get(0);
        Glide.with(MyApp.getInstance()).load(contentBean.getUserIcon()).into(mImgUserIcon);
        Glide.with(MyApp.getInstance()).load(contentBean.getUserIcon()).into(mImgUserUserIconImg);
        mTxtUserNickName.setText(contentBean.getNickName());
        mTxtUserNiceNameTxt.setText(contentBean.getNickName());
        mTxtUserSexTxt.setText(contentBean.getSex());
        mTxtUserRegionTxt.setText(contentBean.getAddress());
    }
}
