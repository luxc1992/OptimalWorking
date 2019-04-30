package com.yst.onecity.activity.mine.setting;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.umeng.socialize.UMAuthListener;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.yst.basic.framework.utils.ToastUtils;
import com.yst.basic.framework.App;
import com.yst.basic.framework.base.BaseActivity;
import com.yst.basic.framework.utils.JumpIntent;
import com.yst.onecity.R;
import com.yst.onecity.activity.VersionUpdateActivity;
import com.yst.onecity.activity.mine.accountsafe.RealNameAuthenticationActivity;
import com.yst.onecity.activity.mine.accountsafe.SetLoginPwdActivity;
import com.yst.onecity.activity.mine.accountsafe.SetPwdActivity;
import com.yst.onecity.activity.mine.address.AddressManagerActivity;
import com.yst.onecity.activity.teammanage.TeamManageActivity;
import com.yst.onecity.bean.MsgBean;
import com.yst.onecity.bean.SanFangStateBean;
import com.yst.onecity.bean.accountsafe.AccountSafeBean;
import com.yst.onecity.http.AbstractNetWorkCallback;
import com.yst.onecity.http.RequestApi;
import com.yst.onecity.utils.DataCleanManager;
import com.yst.onecity.view.AbstractCommonDialog;
import com.yst.onecity.view.AbstractLogOutDialog;

import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;
import cn.jpush.android.api.JPushInterface;

import static com.yst.onecity.Constant.NO1;
import static com.yst.onecity.Constant.NO2;


/**
 * 设置
 *
 * @author chenjiadi
 * @version 1.0.1
 * @date 2018/2/9.
 */
public class SettingActivity extends BaseActivity {

    @BindView(R.id.tv_clear_catch)
    TextView tvClearCatch;
    @BindView(R.id.txt_logout_textview)
    TextView txtLogoutTextview;
    @BindView(R.id.iv_bind_wechat)
    ImageView ivBindWechat;
    @BindView(R.id.txt_bind_wechat)
    TextView tvWechat;
    @BindView(R.id.iv_bind_qq)
    ImageView ivBindQq;
    @BindView(R.id.txt_bind_qq)
    TextView tvQq;
    private AbstractLogOutDialog dialog;
    private String totalCacheSize;
    String str = "0.0Byte";
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

    private int advisorId;
    private int examineStatus;

    @Override
    public int getLayoutId() {
        return R.layout.activity_setting;
    }

    @Override
    public void initData() {
        initTitleBar("设置");
        logOutDialog();
        getCacheSize();
        if (null != getIntent()) {
            advisorId = getIntent().getIntExtra("advisorId", 0);
            examineStatus = getIntent().getIntExtra("examineStatus", 0);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        getThirdLoginInfo();
    }

    @OnClick({R.id.tv_person_info, R.id.tv_feed_back, R.id.tv_clear_catch, R.id.iv_bind_wechat, R.id.iv_bind_qq, R.id.tv_version_update, R.id.tv_recieve_address,
            R.id.txt_logout_textview, R.id.tv_update_login_password, R.id.tv_update_trade_password, R.id.tv_authentication_state, R.id.tv_about_our})
    public void onViewClicked(View view) {
        Bundle bundle = new Bundle();
        switch (view.getId()) {
            //个人信息
            case R.id.tv_person_info:
                // 0待审核，1审核通过 2 审核失败  3没服务团队
                if (examineStatus == 1) {
                    bundle.putInt("advisorId",advisorId);
                    JumpIntent.jump(SettingActivity.this, TeamManageActivity.class, bundle);
                } else {
                    bundle.putInt("type", NO1);
                    JumpIntent.jump(SettingActivity.this, PersonDetailActivity.class, bundle);
                }
                break;
            //绑定微信
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
                    UMShareAPI.get(App.getInstance()).getPlatformInfo(SettingActivity.this, SHARE_MEDIA.WEIXIN, umAuthListener);
                }
                break;
            //绑定qq
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
                    UMShareAPI.get(App.getInstance()).getPlatformInfo(SettingActivity.this, SHARE_MEDIA.QQ, umAuthListener);
                }
                break;
            //修改登录密码  // type 0 设置登录密码  1 修改登录密码
            case R.id.tv_update_login_password:
                if (hasLoginPwd) {
                    bundle.putInt("type", 1);
                } else {
                    bundle.putInt("type", 0);
                }
                JumpIntent.jump(this, SetLoginPwdActivity.class, bundle);
                break;
            //修改交易密码
            case R.id.tv_update_trade_password:
                //判断是否实名认证
                if (isRealName) {
                    if (!hasTradePwd) {
                        bundle.putInt("type", 2);
                        JumpIntent.jump(this, SetLoginPwdActivity.class, bundle);
                    } else {
                        JumpIntent.jump(this, SetPwdActivity.class);
                    }
                } else {
                    JumpIntent.jump(this, RealNameAuthenticationActivity.class, bundle);
                }
                break;
            //实名认证
            case R.id.tv_authentication_state:
                if (isRealName) {
                    bundle.putBoolean("isRealName", true);
                } else {
                    bundle.putBoolean("isRealName", false);
                }
                JumpIntent.jump(this, RealNameAuthenticationActivity.class, bundle);
                break;
            //关于我们
            case R.id.tv_about_our:
                JumpIntent.jump(this, MyActivity.class);
                break;
            //意见反馈
            case R.id.tv_feed_back:
                JumpIntent.jump(SettingActivity.this, IdeaActivity.class);
                break;
            //清除缓存
            case R.id.tv_clear_catch:
                if (totalCacheSize.contains(str)) {
                    ToastUtils.show("没有可以清除的缓存");
                } else {
                    // showDialog();
                    DataCleanManager.clearAllCache(SettingActivity.this);
                    getCacheSize();
                    ToastUtils.show("清除缓存成功");
                }
                break;
            //版本更新
            case R.id.tv_version_update:
                JumpIntent.jump(SettingActivity.this, VersionUpdateActivity.class);
                break;
            //收货地址
            case R.id.tv_recieve_address:
                JumpIntent.jump(SettingActivity.this, AddressManagerActivity.class);
                break;
            //退出登录
            case R.id.txt_logout_textview:
                dialog.showDialog();
                logOutDialog();
                break;

            default:
                break;
        }
    }

    /**
     * 退出登录
     */
    private void logOutDialog() {
        dialog = new AbstractLogOutDialog(this) {
            @Override
            public void sureClick() {
                App.manager.quitLogin();
                Log.i("liumanqing", App.manager.getLoginState() + "");
                finish();
                //极光推送解除绑定   停止推送
                JPushInterface.clearAllNotifications(getApplicationContext());
                JPushInterface.stopPush(getApplicationContext());
                JPushInterface.setMobileNumber(getApplicationContext(), 2, "");
            }
        };
    }

    /**
     * 获取本地缓存大小
     */
    private void getCacheSize() {
        try {
            totalCacheSize = DataCleanManager.getTotalCacheSize(getApplicationContext());
            if (totalCacheSize.contains(str)) {
                tvClearCatch.setText("0M");
            } else {
                tvClearCatch.setText(totalCacheSize);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        dialog.dismissDialog();
    }

    /**
     * 清除缓存弹出框
     */
    public void showDialog() {
        AbstractCommonDialog dialog = new AbstractCommonDialog(SettingActivity.this) {
            @Override
            public void sureClick() {
                DataCleanManager.clearAllCache(SettingActivity.this);
                getCacheSize();
                dismissDialog();
            }
        };
        dialog.setText("提示", "确定要清除缓存么？", "确定", "取消");
        dialog.showDialog();
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
        RequestApi.getAccountSafe(String.valueOf(App.manager.getId()), App.manager.getPhoneNum(), App.manager.getUuid(), new AbstractNetWorkCallback<AccountSafeBean>() {
            @Override
            public void onSuccess(AccountSafeBean accountSafeBean) {
                if (accountSafeBean.getCode() == NO1) {
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
                        SettingActivity.this.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                tvQq.setText(String.format("QQ(%1$s)", nickName));
                            }
                        });
                        ivBindQq.setBackgroundResource(R.mipmap.open);
                        isBindQQ = true;
                    } else {
                        SettingActivity.this.runOnUiThread(new Runnable() {
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
