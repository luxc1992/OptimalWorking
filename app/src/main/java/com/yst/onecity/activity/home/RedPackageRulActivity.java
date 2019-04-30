package com.yst.onecity.activity.home;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.yst.basic.framework.App;
import com.yst.basic.framework.base.BaseActivity;
import com.yst.basic.framework.utils.JumpIntent;
import com.yst.basic.framework.utils.ToastUtils;
import com.yst.onecity.H5Const;
import com.yst.onecity.R;
import com.yst.onecity.activity.h5detail.ServerTeamPageDetailActivity;
import com.yst.onecity.activity.login.LoginActivity;
import com.yst.onecity.bean.MsgBean;
import com.yst.onecity.bean.home.CouponBean;
import com.yst.onecity.http.AbstractNetWorkCallback;
import com.yst.onecity.http.RequestApi;
import com.yst.onecity.utils.MyLog;

import butterknife.BindView;
import butterknife.OnClick;

import static com.yst.onecity.Constant.NO1;
import static com.yst.onecity.Constant.NO2;

/**
 * 红包规则页面
 *
 * @author qinchaoshuai
 * @version 1.0.0
 * @date 2018/5/21.
 */

public class RedPackageRulActivity extends BaseActivity {
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
    @BindView(R.id.iv_rul_icon)
    ImageView ivRulIcon;
    @BindView(R.id.iv_success_icon)
    ImageView ivSuccessIcon;
    @BindView(R.id.iv_defeated_icon)
    ImageView ivDefeatedIcon;

    private int index;
    /**
     * 优惠券
     */
    private int couponId;

    /**
     * 页面跳转
     *
     * @param context 上下文
     */
    public static void getJumpActivity(Context context) {
        Intent intent = new Intent(context, RedPackageRulActivity.class);
        context.startActivity(intent);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_red_package_rul;
    }

    @Override
    public void initData() {
        initTitleBar("红包");
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (!App.manager.getLoginState()) {
            ivRulIcon.setVisibility(View.VISIBLE);
            ivSuccessIcon.setVisibility(View.GONE);
            ivDefeatedIcon.setVisibility(View.GONE);
        } else {
            getCoupon();
        }
    }


    @OnClick({R.id.iv_rul_icon, R.id.iv_success_icon, R.id.iv_defeated_icon})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_rul_icon:
                MyLog.e("mylog", "mylog === ");
                if (!App.manager.getLoginState()) {
                    JumpIntent.jump(this, LoginActivity.class);
                }
                break;
            case R.id.iv_success_icon:
                Bundle bundle = new Bundle();
                bundle.putString("url", H5Const.H5_BANNER1);
                JumpIntent.jump(RedPackageRulActivity.this, ServerTeamPageDetailActivity.class, bundle);
                finish();
                break;
            case R.id.iv_defeated_icon:
                finish();
                break;
            default:
                break;
        }
    }

    /**
     * 优惠券信息查询
     */
    private void getCoupon() {
        RequestApi.getCoupon(new AbstractNetWorkCallback<CouponBean>() {
            @Override
            public void onSuccess(CouponBean mCouponBean) {
                if (mCouponBean.getCode() == NO1) {
                    if (mCouponBean.getContent()!=null) {
                        couponId = mCouponBean.getContent().getId();
                        receiveCoupon();
                    }else {
                        ToastUtils.show("暂无优惠券");
                    }
                } else {
                    getTheFailure();
                }
            }

            @Override
            public void onError(String errorMsg) {
                ToastUtils.show(errorMsg);
                getTheFailure();
            }
        });
    }

    /**
     * 会员登陆领取优惠券
     */
    private void receiveCoupon() {
        RequestApi.receiveCoupon(
                String.valueOf(couponId),
                App.manager.getUuid(),
                App.manager.getPhoneNum(),
                new AbstractNetWorkCallback<MsgBean>() {

                    @Override
                    public void onAfter() {
                        super.onAfter();
                        dismissInfoProgressDialog();
                    }

                    @Override
                    public void onBefore() {
                        super.onBefore();
                        showInfoProgressDialog();
                    }

                    @Override
                    public void onSuccess(MsgBean mMsgBean) {
                        index = mMsgBean.getCode();
                        if (mMsgBean.getCode() == NO1) {
                            ivRulIcon.setVisibility(View.GONE);
                            ivSuccessIcon.setVisibility(View.VISIBLE);
                            ivDefeatedIcon.setVisibility(View.GONE);
                        } else {
                            getTheFailure();
                        }
                    }

                    @Override
                    public void onError(String errorMsg) {
                        getTheFailure();
                        ToastUtils.show(errorMsg);
                    }
                });
    }

    /**
     * 领去失败设置
     */
    private void getTheFailure() {
        index = NO2;
        ivRulIcon.setVisibility(View.GONE);
        ivSuccessIcon.setVisibility(View.GONE);
        ivDefeatedIcon.setVisibility(View.VISIBLE);
    }

}
