package com.yst.onecity.activity.teammanage;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.yst.basic.framework.App;
import com.yst.basic.framework.base.BaseActivity;
import com.yst.basic.framework.utils.ToastUtils;
import com.yst.basic.framework.utils.Utils;
import com.yst.im.imchatlibrary.model.SetLogoModel;
import com.yst.im.imchatlibrary.model.SetNameModel;
import com.yst.onecity.Constant;
import com.yst.onecity.R;
import com.yst.onecity.bean.MsgBean;
import com.yst.onecity.http.AbstractNetWorkCallback;
import com.yst.onecity.http.RequestApi;
import com.yst.onecity.utils.MyTextWatcher;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 团队的基本信息
 *
 * @author wuxiaofang
 * @version 1.1.0
 * @date 2018/5/18
 */

public class TeamInfoActivity extends BaseActivity {


    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.ll_back)
    LinearLayout llBack;
    @BindView(R.id.tv_main_title)
    TextView tvMainTitle;
    @BindView(R.id.tv_right)
    TextView tvRight;
    @BindView(R.id.iv_right)
    ImageView ivRight;
    @BindView(R.id.tv_name)
    TextView tvName;
    @BindView(R.id.et_tean_name)
    EditText etTeanName;
    @BindView(R.id.tv_address)
    TextView tvAddress;
    @BindView(R.id.et_team_address)
    TextView etTeamAddress;
    @BindView(R.id.tv_phone)
    TextView tvPhone;
    @BindView(R.id.et_phone)
    EditText etPhone;
    @BindView(R.id.et_team_intro)
    EditText etTeamIntro;
    @BindView(R.id.tv_et_start)
    TextView tvEtStart;
    @BindView(R.id.tv_et_end)
    TextView tvEtEnd;
    @BindView(R.id.tv_commit)
    TextView tvCommit;
    private String advisorId;
    private String headUrl="";
    private int saleState;
    private String provinceId;
    private String cityId;
    private String districtId;
    private String teamPhone;
    private String name;
    private String address;
    private String intro;
    private String phone;

    @Override
    public int getLayoutId() {
        return R.layout.activity_team_info;
    }

    @Override
    public void initData() {
        initTitleBar(getString(R.string.team_base_info));
        etTeamIntro.addTextChangedListener(new MyTextWatcher(100, etTeamIntro, this, tvEtStart));
        Intent intent = getIntent();
        if (null != intent && null != intent.getExtras()) {
            Bundle bundle = intent.getExtras();
            advisorId = bundle.getString("advisorId");
            String teamName = bundle.getString("teamName");
            String teamAddress = bundle.getString("teamAddress");
            teamPhone = bundle.getString("teamPhone");
            String teamIntro = bundle.getString("teamIntro");
            headUrl = bundle.getString("headUrl");
            saleState = bundle.getInt("saleState");
            provinceId = bundle.getString("provinceId");
            cityId = bundle.getString("cityId");
            districtId = bundle.getString("districtId");
            etTeanName.setText(teamName);
            etTeamAddress.setText(teamAddress);
            etPhone.setText(teamPhone);
            etTeamIntro.setText(teamIntro);
        }
    }

    @OnClick(R.id.tv_commit)
    public void commitInfo(View view) {
        if (!Utils.isClickable()) {
            return;
        }

        name = etTeanName.getText().toString();
        address = etTeamAddress.getText().toString();
        intro = etTeamIntro.getText().toString();
        phone = etPhone.getText().toString().trim();
        if (TextUtils.isEmpty(advisorId)) {
            ToastUtils.show("服务团队id为空");
            return;
        }
        if (TextUtils.isEmpty(name)) {
            ToastUtils.show(getString(R.string.input_team_name));
            return;
        }
        if (TextUtils.isEmpty(address)) {
            ToastUtils.show(getString(R.string.input_team_address));
            return;
        }
        if (TextUtils.isEmpty(intro)) {
            ToastUtils.show(getString(R.string.input_team_intro));
            return;
        }
        if (TextUtils.isEmpty(phone)) {
            ToastUtils.show("手机号不能为空");
            return;
        }
        if (TextUtils.isEmpty(headUrl)) {
            ToastUtils.show("头像路径为空");
            return;
        }
        if (TextUtils.isEmpty(provinceId)) {
            ToastUtils.show("省id为空");
            return;
        }
        if (TextUtils.isEmpty(cityId)) {
            ToastUtils.show("市id为空");
            return;
        }
        if (TextUtils.isEmpty(districtId)) {
            ToastUtils.show("区id为空");
            return;
        }
        comminInfo();
    }

    /**
     * 提交信息
     */
    private void comminInfo() {
        RequestApi.updateTeamInfo(advisorId, App.manager.getUuid(), headUrl, name, address, App.manager.getPhoneNum(), phone,intro, String.valueOf(saleState), cityId, provinceId, districtId, new AbstractNetWorkCallback<MsgBean>() {
            @Override
            public void onSuccess(MsgBean teamInfoBean) {
                if (null != teamInfoBean) {
                    if (teamInfoBean.getCode() == Constant.NO1) {
                        SetLogoModel setLogoModel = new SetLogoModel(TeamInfoActivity.this);
                        setLogoModel.getSetLogo(headUrl);
                        SetNameModel setNameModel = new SetNameModel(TeamInfoActivity.this);
                        setNameModel.getSetName(name);
                        ToastUtils.show(teamInfoBean.getMsg());
                        finish();
                    } else {
                        ToastUtils.show(teamInfoBean.getMsg());
                    }
                }
            }

            @Override
            public void onError(String errorMsg) {
                ToastUtils.show(errorMsg);
            }
        });
    }
}
