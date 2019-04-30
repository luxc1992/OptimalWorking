package com.yst.onecity.activity.mine.accountsafe;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.yst.basic.framework.utils.ToastUtils;
import com.umeng.socialize.UMAuthListener;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.yst.basic.framework.App;
import com.yst.basic.framework.base.BaseActivity;
import com.yst.basic.framework.utils.JumpIntent;
import com.yst.basic.framework.utils.Utils;
import com.yst.onecity.R;
import com.yst.onecity.bean.MsgBean;
import com.yst.onecity.bean.SanFangStateBean;
import com.yst.onecity.bean.accountsafe.AccountSafeBean;
import com.yst.onecity.http.AbstractNetWorkCallback;
import com.yst.onecity.http.RequestApi;
import com.yst.onecity.view.AbstractCommonDialog;

import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;

import static com.yst.onecity.Constant.NO1;
import static com.yst.onecity.Constant.NO2;

/**
 * 账号安全
 *
 * @author luxuchang
 * @version 1.0.1
 * @date 2018/2/7.
 */

public class AccountSafeActivity extends BaseActivity {
    @BindView(R.id.iv_bind_wechat)
    ImageView ivBindWechat;
    @BindView(R.id.tv_account_safe_phone)
    TextView tvAccountSafePhone;
    @BindView(R.id.txt_bind_wechat)
    TextView tvWechat;
    @BindView(R.id.iv_bind_qq)
    ImageView ivBindQq;
    @BindView(R.id.txt_bind_qq)
    TextView tvQq;
    @BindView(R.id.tv_authentication_state)
    TextView tvAuthenticationState;

    /**
     * 是否设置登录密码
     */
    private boolean hasLoginPwd;
    /**
     * 是否设置交易密码
     */
    private boolean hasTradePwd;
    /**
     * 是否实名认证
     */
    private boolean isRealName;
    /**
     * 是否绑定QQ
     */
    private boolean isBindQQ;
    /**
     * 是否绑定QQ
     */
    private boolean isBindWechat;
    private int flag;

    @Override
    public int getLayoutId() {
        return R.layout.activity_account_safe;
    }

    @Override
    public void initData() {
        initTitleBar(getString(R.string.account_safe_str));
    }

    @Override
    protected void onResume() {
        super.onResume();
        getThirdLoginInfo();
    }

    @OnClick({R.id.iv_bind_wechat, R.id.iv_bind_qq, R.id.rl_update_login_password,
            R.id.rl_update_trade_password, R.id.rl_identity_authentication})
    public void onViewClicked(View view) {
        if (!Utils.isClickable()) {
            return;
        }
        Bundle bundle = new Bundle();
        switch (view.getId()) {
            case R.id.iv_bind_wechat:
                if (isBindWechat) {
                    AbstractCommonDialog dialog = new AbstractCommonDialog(this) {
                        @Override
                        public void sureClick() {
                            dismissDialog();
                            removeBindThirdAccount(2);
                        }
                    };
                    dialog.setText("提示", "确定解除绑定么？", "确定", "取消");
                    dialog.showDialog();
                } else {
                    // 绑定微信
                    flag = 2;
                    UMShareAPI.get(App.getInstance()).getPlatformInfo(AccountSafeActivity.this, SHARE_MEDIA.WEIXIN, umAuthListener);
                }

                break;
            case R.id.iv_bind_qq:
                if (isBindQQ) {
                    AbstractCommonDialog dialog = new AbstractCommonDialog(this) {
                        @Override
                        public void sureClick() {
                            dismissDialog();
                            // 解绑qq
                            removeBindThirdAccount(1);
                        }
                    };
                    dialog.setText("提示", "确定解除绑定么？", "确定", "取消");
                    dialog.showDialog();
                } else {
                    // 绑定qq
                    flag = 1;
                    UMShareAPI.get(App.getInstance()).getPlatformInfo(AccountSafeActivity.this, SHARE_MEDIA.QQ, umAuthListener);
                }
                break;
            // type 0 设置登录密码  1 修改登录密码
            case R.id.rl_update_login_password:
                if (hasLoginPwd) {
                    bundle.putInt("type", 1);
                } else {
                    bundle.putInt("type", 0);
                }
                JumpIntent.jump(this, SetLoginPwdActivity.class, bundle);
                break;
            // 2 设置交易密码
            case R.id.rl_update_trade_password:
                if (!hasTradePwd) {
                    bundle.putInt("type", 2);
                    JumpIntent.jump(this, SetLoginPwdActivity.class, bundle);
                } else {
                    JumpIntent.jump(this, SetPwdActivity.class);
                }
                break;
            case R.id.rl_identity_authentication:
                if (isRealName) {
                    bundle.putBoolean("isRealName", true);
                } else {
                    bundle.putBoolean("isRealName", false);
                }
                JumpIntent.jump(this, RealNameAuthenticationActivity.class, bundle);
                break;
            default:
                break;
        }
    }

    /**
     * 三方登录接口回调
     */
    private UMAuthListener umAuthListener = new UMAuthListener() {

        @Override
        public void onComplete(SHARE_MEDIA shareMedia, int type, Map<String, String> map) {
            //微信\QQ(qqUid\openid)
            if (flag == NO1) {
                checkThirdAccountIsBindPhone(1, map.get("openid"), map.get("screen_name"));
            } else if (flag == NO2) {
                checkThirdAccountIsBindPhone(2, map.get("openid"), map.get("screen_name"));
            }
        }

        @Override
        public void onError(SHARE_MEDIA shareMedia, int i, Throwable throwable) {
        }

        @Override
        public void onCancel(SHARE_MEDIA shareMedia, int i) {
        }
    };

    /**
     * 获取信息
     */
    private void getThirdLoginInfo() {
        RequestApi.getAccountSafe(App.manager.getId() + "",App.manager.getPhoneNum(), App.manager.getUuid(), new AbstractNetWorkCallback<AccountSafeBean>() {
            @Override
            public void onSuccess(AccountSafeBean accountSafeBean) {
                if (accountSafeBean.getCode() == NO1) {
                    tvAccountSafePhone.setText(accountSafeBean.getContent().getPhone());
                    if (accountSafeBean.getContent().getPasswordStatus() == 1) {
                        hasLoginPwd = true;
                    }
                    if (accountSafeBean.getContent().getRealNameStatus() == 1) {
                        isRealName = true;
                    }
                    if (accountSafeBean.getContent().getTransactionPasswordStatus() == 1) {
                        hasTradePwd = true;
                    }

                    if (!TextUtils.isEmpty(accountSafeBean.getContent().getQqName())) {
                        ivBindQq.setBackgroundResource(R.mipmap.open);
                        tvQq.setText(String.format("QQ(%1$s)", accountSafeBean.getContent().getQqName()));
                        isBindQQ = true;
                    } else {
                        ivBindQq.setBackgroundResource(R.mipmap.close2);
                        isBindQQ = false;
                    }

                    if (!TextUtils.isEmpty(accountSafeBean.getContent().getWxName())) {
                        ivBindWechat.setBackgroundResource(R.mipmap.open);
                        tvWechat.setText(String.format("微信(%1$s)", accountSafeBean.getContent().getWxName()));
                        isBindWechat = true;
                    } else {
                        ivBindWechat.setBackgroundResource(R.mipmap.close2);
                        isBindWechat = false;
                    }
                } else {
                    ToastUtils.show(accountSafeBean.getMsg());
                }
            }

            @Override
            public void onError(String errorMsg) {
                ToastUtils.show(errorMsg);
            }
        });
    }

    /**
     * 检查三方账号是否绑定过手机号
     *
     * @param type   登录类型  0 qq 1 微信
     * @param openId openId
     */
    private void checkThirdAccountIsBindPhone(final int type, final String openId, final String nickName) {
        RequestApi.checkThirdAccountIsBind(type, openId, new AbstractNetWorkCallback<SanFangStateBean>() {
            @Override
            public void onSuccess(SanFangStateBean bean) {

                if (bean.getCode() == NO1) {
                    if (type == NO1) {
                        switch (bean.getContent().getBindStatus()) {
                            case "0":
                            case "2":
                                bindThirdAccount(type, openId, nickName);
                                break;
                            default:
                                ToastUtils.show("该账号已绑定过手机号");
                                break;
                        }
                    } else {
                        switch (bean.getContent().getBindStatus()) {
                            case "0":
                            case "1":
                                bindThirdAccount(type, openId, nickName);
                                break;
                            default:
                                ToastUtils.show("该账号已绑定过手机号");
                                break;
                        }
                    }
                } else {
                    ToastUtils.show(bean.getMsg());
                }
            }

            @Override
            public void onError(String errorMsg) {
                ToastUtils.show(errorMsg);
            }
        });
    }

    /**
     * 绑定第三方账号
     *
     * @param type     1 qq  2 微信
     * @param openid
     * @param nickName
     */
    private void bindThirdAccount(final int type, String openid, final String nickName) {
        RequestApi.bindThirdAccount(App.manager.getPhoneNum(), type, openid, nickName, new AbstractNetWorkCallback<MsgBean>() {
            @Override
            public void onSuccess(MsgBean bean) {
                if (bean.getCode() == NO1) {
                    if (type == NO1) {
                        AccountSafeActivity.this.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                tvQq.setText(String.format("QQ(%1$s)", nickName));
                            }
                        });
                        ivBindQq.setBackgroundResource(R.mipmap.open);
                        isBindQQ = true;
                    } else {
                        AccountSafeActivity.this.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                tvWechat.setText(String.format("微信(%1$s)", nickName));
                            }
                        });
                        ivBindWechat.setBackgroundResource(R.mipmap.open);
                        isBindWechat = true;
                    }
                }
                ToastUtils.show(bean.getMsg());
            }

            @Override
            public void onError(String errorMsg) {
                ToastUtils.show(errorMsg);
            }
        });
    }

    /**
     * 解除第三方绑定
     *
     * @param type
     */
    private void removeBindThirdAccount(final int type) {
        RequestApi.removeBindThirdAccount(App.manager.getPhoneNum(), App.manager.getUuid(), type, new AbstractNetWorkCallback<MsgBean>() {
            @Override
            public void onSuccess(MsgBean bean) {
                if (bean.getCode() == NO1) {
                    if (type == NO1) {
                        isBindQQ = false;
                        tvQq.setText("QQ");
                        ivBindQq.setBackgroundResource(R.mipmap.close2);
                    } else {
                        isBindWechat = false;
                        tvWechat.setText("微信");
                        ivBindWechat.setBackgroundResource(R.mipmap.close2);
                    }
                }
                ToastUtils.show(bean.getMsg());
            }

            @Override
            public void onError(String errorMsg) {
                ToastUtils.show(errorMsg);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        UMShareAPI.get(context).onActivityResult(requestCode, resultCode, data);
    }
}
