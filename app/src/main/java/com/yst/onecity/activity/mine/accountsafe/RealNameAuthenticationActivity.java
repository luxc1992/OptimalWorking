package com.yst.onecity.activity.mine.accountsafe;

import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.yst.basic.framework.utils.ToastUtils;
import com.yst.basic.framework.App;
import com.yst.basic.framework.base.BaseActivity;
import com.yst.basic.framework.utils.JumpIntent;
import com.yst.basic.framework.utils.Utils;
import com.yst.onecity.R;
import com.yst.onecity.activity.agent.PublishCourseActivity;
import com.yst.onecity.activity.issue.AddProductPlanActivity;
import com.yst.onecity.activity.issue.ProjectPlanActivity;
import com.yst.onecity.bean.MsgBean;
import com.yst.onecity.bean.accountsafe.RealNameAuthenticationInfoBean;
import com.yst.onecity.http.AbstractNetWorkCallback;
import com.yst.onecity.http.RequestApi;
import com.yst.onecity.utils.CommonUtils;
import com.yst.onecity.utils.FormatUtils;
import com.yst.onecity.utils.IsCard;
import com.yst.onecity.utils.MyEditTextChangeListener;
import com.yst.onecity.view.ContainsEmojiEditText;

import butterknife.BindView;
import butterknife.OnClick;

import static com.yst.onecity.Constant.NO0;
import static com.yst.onecity.Constant.NO1;
import static com.yst.onecity.Constant.NO2;

/**
 * 实名认证
 *
 * @author luxuchang
 * @version 1.0.1
 * @date 2018/2/8.
 */

public class RealNameAuthenticationActivity extends BaseActivity {
    @BindView(R.id.tv_phone_num)
    TextView tvPhoneNum;
    @BindView(R.id.et_verification)
    ContainsEmojiEditText etVerification;
    @BindView(R.id.et_real_name)
    ContainsEmojiEditText etRealName;
    @BindView(R.id.et_identity_card)
    ContainsEmojiEditText etIdentityCard;
    @BindView(R.id.btn_get_verification_code)
    TextView btnGetVerificationCode;
    @BindView(R.id.btn_commit)
    TextView btnCommit;
    @BindView(R.id.ll_identity_before)
    LinearLayout llIdentityBefore;
    @BindView(R.id.tv_real_name)
    TextView tvRealName;
    @BindView(R.id.tv_ic_card)
    TextView tvIcCard;
    @BindView(R.id.ll_identity_after)
    LinearLayout llIdentityAfter;
    private boolean isRealName;
    private String strPhone = "";
    private String strUuid = "";
    private int isOk;
    private boolean isPublish;

    @Override
    public int getLayoutId() {
        return R.layout.activity_identity_authentication;
    }

    @Override
    public void initData() {
        initTitleBar(getString(R.string.real_name_authentication));
        strPhone = App.manager.getPhoneNum();
        strUuid = App.manager.getUuid();
        isRealName = getIntent().getBooleanExtra("isRealName", false);
        tvPhoneNum.setText(FormatUtils.formatPhone(strPhone));
        //发布页面判断是否实名认证 跳转标识
        isPublish = getIntent().hasExtra("key");
        if (isPublish) {
            isOk = getIntent().getExtras().getInt("key");
        }
        initView();
        setListener();
    }

    private void initView() {
        CommonUtils.setEditTextInputLength(etRealName, 8, true);
        CommonUtils.setEditTextInputLength(etIdentityCard, 18, true);
        CommonUtils.setEditTextInputLength(etVerification, 6, true);

        if (isRealName) {
            llIdentityAfter.setVisibility(View.VISIBLE);
            llIdentityBefore.setVisibility(View.GONE);
            //实名认证信息回显
            RequestApi.realNameAuthenticationInfo(strUuid, strPhone, new AbstractNetWorkCallback<RealNameAuthenticationInfoBean>() {
                @Override
                public void onSuccess(RealNameAuthenticationInfoBean realNameAuthenticationInfoBean) {
                    if (realNameAuthenticationInfoBean.getCode() == 1) {
                        App.manager.setName(realNameAuthenticationInfoBean.getContent().getName());

                        tvRealName.setText(FormatUtils.formatName(realNameAuthenticationInfoBean.getContent().getName()));
                        tvIcCard.setText(FormatUtils.formatIDCard(realNameAuthenticationInfoBean.getContent().getCardNo()));
                    } else {
                        ToastUtils.show(realNameAuthenticationInfoBean.getMsg());
                    }
                }

                @Override
                public void onError(String errorMsg) {
                    ToastUtils.show(errorMsg);
                }
            });
        } else {
            llIdentityBefore.setVisibility(View.VISIBLE);
            llIdentityAfter.setVisibility(View.GONE);
        }
    }

    /**
     * 添加输入框监听
     */
    @Override
    public void setListener() {
        etRealName.addTextChangedListener(new MyEditTextChangeListener(etRealName, etIdentityCard, etVerification, btnCommit));
        etIdentityCard.addTextChangedListener(new MyEditTextChangeListener(etRealName, etIdentityCard, etVerification, btnCommit));
        etVerification.addTextChangedListener(new MyEditTextChangeListener(etRealName, etIdentityCard, etVerification, btnCommit));
    }

    @OnClick({R.id.btn_get_verification_code, R.id.btn_commit})
    public void onViewClicked(View view) {
        if (!Utils.isClickable()) {
            return;
        }
        switch (view.getId()) {
            case R.id.btn_get_verification_code:
                Utils.startCountDown(btnGetVerificationCode, R.drawable.shape_get_verification_code, R.color.color_ED5452, R.drawable.shape_gray_frame, R.color.color_999999, 60000, "获取验证码");
                //获取验证码接口
                RequestApi.getRealNameAuthenticationVerifyCode(strPhone, new AbstractNetWorkCallback<MsgBean>() {
                    @Override
                    public void onSuccess(MsgBean bean) {
                        ToastUtils.show(bean.getMsg());
                    }

                    @Override
                    public void onError(String errorMsg) {
                        ToastUtils.show(errorMsg);
                    }
                });
                break;
            case R.id.btn_commit:
                if (!new IsCard().verify(etIdentityCard.getText().toString().trim())) {
                    ToastUtils.show("请输入正确的身份证号码");
                    return;
                }
                //实名认证接口
                RequestApi.realNameAuthentication(strUuid, strPhone, CommonUtils.getEditTextInputContent(etRealName), etIdentityCard.getText().toString().trim(), etVerification.getText().toString().trim(), new AbstractNetWorkCallback<MsgBean>() {
                    @Override
                    public void onSuccess(MsgBean bean) {
                        if (bean.getCode() == 1) {
                            llIdentityBefore.setVisibility(View.GONE);
                            llIdentityAfter.setVisibility(View.VISIBLE);
                            ToastUtils.show(bean.getMsg());
                            //认证信息回显
                            RequestApi.realNameAuthenticationInfo(strUuid, strPhone, new AbstractNetWorkCallback<RealNameAuthenticationInfoBean>() {
                                @Override
                                public void onSuccess(RealNameAuthenticationInfoBean realNameAuthenticationInfoBean) {
                                    if (realNameAuthenticationInfoBean.getCode() == 1) {
                                        App.manager.setName(realNameAuthenticationInfoBean.getContent().getName());
                                        tvRealName.setText(FormatUtils.formatName(realNameAuthenticationInfoBean.getContent().getName()));
                                        tvIcCard.setText(FormatUtils.formatIDCard(realNameAuthenticationInfoBean.getContent().getCardNo()));
                                        //实名认证成功之后跳转页面
                                        if (isPublish) {
                                            if (isOk == NO0) {
                                                JumpIntent.jump(RealNameAuthenticationActivity.this, AddProductPlanActivity.class);
                                                finish();
                                            } else if (isOk == NO1) {
                                                JumpIntent.jump(RealNameAuthenticationActivity.this, ProjectPlanActivity.class);
                                                finish();
                                            } else if (isOk == NO2) {
                                                JumpIntent.jump(RealNameAuthenticationActivity.this, PublishCourseActivity.class);
                                                finish();
                                            }
                                        }

                                    } else {
                                        ToastUtils.show(realNameAuthenticationInfoBean.getMsg());
                                    }
                                }

                                @Override
                                public void onError(String errorMsg) {
                                    ToastUtils.show(errorMsg);
                                }
                            });
                        } else {
                            ToastUtils.show(bean.getMsg());
                        }
                    }

                    @Override
                    public void onError(String errorMsg) {
                        ToastUtils.show(errorMsg);
                    }
                });
                break;
            default:
                break;
        }
    }
}
