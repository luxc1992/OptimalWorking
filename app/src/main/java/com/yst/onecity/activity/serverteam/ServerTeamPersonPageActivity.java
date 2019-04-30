package com.yst.onecity.activity.serverteam;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.gyf.barlibrary.ImmersionBar;
import com.yst.basic.framework.App;
import com.yst.basic.framework.base.BaseActivity;
import com.yst.basic.framework.utils.JumpIntent;
import com.yst.basic.framework.utils.ToastUtils;
import com.yst.basic.framework.utils.Utils;
import com.yst.onecity.Constant;
import com.yst.onecity.R;
import com.yst.onecity.activity.casemanage.CaseManagerActivity;
import com.yst.onecity.activity.comment.CommentManageActivity;
import com.yst.onecity.activity.home.PlatformInformActivity;
import com.yst.onecity.activity.mine.CardUnionDetailActivity;
import com.yst.onecity.activity.mine.CardUnionListActivity;
import com.yst.onecity.activity.mine.order.AfterSalesListActivity;
import com.yst.onecity.activity.mine.order.MyServiceOrderActivity;
import com.yst.onecity.activity.mine.order.MyServiceOrderDetailActivity;
import com.yst.onecity.activity.mine.order.ServeTeamProductOrderActivity;
import com.yst.onecity.activity.teammanage.TeamManageActivity;
import com.yst.onecity.activity.yanzhengma.EditCodeActivity;
import com.yst.onecity.activity.yanzhengma.EditCodeBean;
import com.yst.onecity.bean.MsgBean;
import com.yst.onecity.http.AbstractNetWorkCallback;
import com.yst.onecity.http.RequestApi;
import com.yst.onecity.utils.ConstUtils;
import com.yst.onecity.utils.MyLog;
import com.yst.onecity.zxing.activity.CaptureActivity;

import butterknife.BindView;
import butterknife.OnClick;

import static com.yst.onecity.Constant.HAVE;
import static com.yst.onecity.Constant.NO0;
import static com.yst.onecity.Constant.NO1;
import static com.yst.onecity.Constant.NOHAVE;

/**
 * 服务团队个人中心页面
 *
 * @author chenjiadi
 * @version 1.1.0
 * @date 2018/5/18.
 */
public class ServerTeamPersonPageActivity extends BaseActivity {

    @BindView(R.id.ll_back)
    LinearLayout llBack;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.iv_notice)
    ImageView ivNotice;
    @BindView(R.id.tv_red_point)
    TextView tvRedPoint;
    @BindView(R.id.tv_edit_proving)
    TextView tvEditProving;
    @BindView(R.id.tv_scan)
    TextView tvScan;
    @BindView(R.id.tv_team_manage)
    TextView tvTeamManage;
    @BindView(R.id.tv_serve_manage)
    TextView tvServeManage;
    @BindView(R.id.tv_good_manage)
    TextView tvGoodManage;
    @BindView(R.id.tv_comment_manage)
    TextView tvCommentManage;
    @BindView(R.id.tv_anli_manage)
    TextView tvAnliManage;
    @BindView(R.id.my_order_tv_service_all)
    TextView myOrderTvServiceAll;
    @BindView(R.id.my_order_iv_service_right_arrow)
    ImageView myOrderIvServiceRightArrow;
    @BindView(R.id.my_order_ll_wait_pay)
    LinearLayout myOrderLlWaitPay;
    @BindView(R.id.my_order_wait_use)
    LinearLayout myOrderWaitUse;
    @BindView(R.id.my_order_ll_wait_evaluate)
    LinearLayout myOrderLlWaitEvaluate;
    @BindView(R.id.my_order_ll_exchange)
    LinearLayout myOrderLlExchange;
    @BindView(R.id.my_order_tv_goods_all)
    TextView myOrderTvGoodsAll;
    @BindView(R.id.my_order_iv_goods_right_arrow)
    ImageView myOrderIvGoodsRightArrow;
    @BindView(R.id.ll_my_pay)
    LinearLayout llMyPay;
    @BindView(R.id.imageView)
    ImageView imageView;
    @BindView(R.id.ll_my_drliver)
    LinearLayout llMyDrliver;
    @BindView(R.id.ll_my_take)
    LinearLayout llMyTake;
    @BindView(R.id.ll_my_appraise)
    LinearLayout llMyAppraise;
    @BindView(R.id.ll_my_after)
    LinearLayout llMyAfter;
    @BindView(R.id.my_order_rl_card)
    RelativeLayout myOrderRlCard;
    private Bundle bundle = new Bundle();
    private String advisorName;
    private String advisorId;

    @Override
    public int getLayoutId() {
        return R.layout.activity_server_team_person_page;
    }

    @Override
    public void initData() {
        ImmersionBar.with(this).fitsSystemWindows(true).statusBarDarkFont(false).statusBarColor(R.color.color_6196FF).init();
        if (getIntent().getExtras() != null) {
            advisorName = getIntent().getExtras().getString("advisorName");
            advisorId = getIntent().getExtras().getString("advisorId");
        }
        ConstUtils.setTextString(advisorName, tvTitle);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (App.manager.getLoginState()) {
            getMessageState();
        }
    }

    @OnClick({R.id.ll_back, R.id.iv_notice, R.id.tv_edit_proving, R.id.tv_scan, R.id.tv_team_manage, R.id.tv_serve_manage, R.id.tv_good_manage, R.id.tv_comment_manage, R.id.tv_anli_manage,
            R.id.my_order_tv_service_all, R.id.my_order_iv_service_right_arrow, R.id.my_order_ll_wait_pay, R.id.my_order_wait_use, R.id.my_order_ll_wait_evaluate,
            R.id.my_order_ll_exchange, R.id.my_order_tv_goods_all, R.id.my_order_iv_goods_right_arrow, R.id.ll_my_pay, R.id.ll_my_drliver,
            R.id.ll_my_take, R.id.ll_my_appraise, R.id.ll_my_after, R.id.my_order_rl_card,})
    public void onViewClicked(View view) {
        if (!Utils.isClickable()) {
            return;
        }
        switch (view.getId()) {
            //卡联盟
            case R.id.my_order_rl_card:
                JumpIntent.jump(ServerTeamPersonPageActivity.this, CardUnionListActivity.class);
                break;
            //商品订单 退换/售后
            case R.id.ll_my_after:
                Constant.userType = 1;
                JumpIntent.jump(ServerTeamPersonPageActivity.this, AfterSalesListActivity.class);
                break;
            //商品订单 待评价
            case R.id.ll_my_appraise:
                bundle.putInt("flag", 4);
                JumpIntent.jump(this, ServeTeamProductOrderActivity.class, bundle);
                break;
            //商品订单 已发货
            case R.id.ll_my_take:
                bundle.putInt("flag", 3);
                JumpIntent.jump(this, ServeTeamProductOrderActivity.class, bundle);
                break;
            //商品订单 待发货
            case R.id.ll_my_drliver:
                bundle.putInt("flag", 2);
                JumpIntent.jump(this, ServeTeamProductOrderActivity.class, bundle);
                break;
            //商品订单 待付款
            case R.id.ll_my_pay:
                bundle.putInt("flag", 1);
                JumpIntent.jump(this, ServeTeamProductOrderActivity.class, bundle);
                break;
            //商品订单查看全部订单
            case R.id.my_order_tv_goods_all:
            case R.id.my_order_iv_goods_right_arrow:
                bundle.putInt("flag", 0);
                JumpIntent.jump(this, ServeTeamProductOrderActivity.class, bundle);
                break;
            //服务订单 退换/售后
            case R.id.my_order_ll_exchange:
                bundle.putInt("flag", 4);
                bundle.putInt("from", 1);
                JumpIntent.jump(ServerTeamPersonPageActivity.this, MyServiceOrderActivity.class, bundle);
                break;
            //服务订单 待评价
            case R.id.my_order_ll_wait_evaluate:
                bundle.putInt("flag", 3);
                bundle.putInt("from", 1);
                JumpIntent.jump(ServerTeamPersonPageActivity.this, MyServiceOrderActivity.class, bundle);
                break;
            //服务订单 待使用
            case R.id.my_order_wait_use:
                bundle.putInt("flag", 2);
                bundle.putInt("from", 1);
                JumpIntent.jump(ServerTeamPersonPageActivity.this, MyServiceOrderActivity.class, bundle);
                break;
                //服务订单 待付款
            case R.id.my_order_ll_wait_pay:
                bundle.putInt("flag", 1);
                bundle.putInt("from", 1);
                JumpIntent.jump(ServerTeamPersonPageActivity.this, MyServiceOrderActivity.class, bundle);
                break;
            //查看全部服务订单
            case R.id.my_order_tv_service_all:
            case R.id.my_order_iv_service_right_arrow:
                bundle.putInt("flag", 0);
                bundle.putInt("from", 1);
                JumpIntent.jump(ServerTeamPersonPageActivity.this, MyServiceOrderActivity.class, bundle);
                break;
            //案例管理
            case R.id.tv_anli_manage:
                bundle.putString("advisorId", advisorId);
                JumpIntent.jump(this, CaseManagerActivity.class, bundle);
                break;
            //评价管理
            case R.id.tv_comment_manage:
                bundle.putString("advisorId", advisorId);
                JumpIntent.jump(this, CommentManageActivity.class, bundle);
                break;
            //商品管理
            case R.id.tv_good_manage:
                JumpIntent.jump(this, GoodsManageActivity.class);
                break;
            //服务管理
            case R.id.tv_serve_manage:
                JumpIntent.jump(this, ServerManagerActivity.class);
                break;
            //团队管理
            case R.id.tv_team_manage:
                bundle.putInt("advisorId", Integer.parseInt(advisorId));
                JumpIntent.jump(this, TeamManageActivity.class, bundle);
                break;
            //扫描验证
            case R.id.tv_scan:
                if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(ServerTeamPersonPageActivity.this, new String[]{Manifest.permission.CAMERA}, Constant.REQ_PERM_CAMERA);
                    return;
                }
                JumpIntent.jump(this, CaptureActivity.class, Constant.REQ_QR_CODE);
                break;
            //输入验证码
            case R.id.tv_edit_proving:
                JumpIntent.jump(this, EditCodeActivity.class);
                break;
            //通知
            case R.id.iv_notice:
                PlatformInformActivity.getJumpApplyAffirmActivity(this);
                break;
            //返回
            case R.id.ll_back:
                finish();
                break;
            default:
                break;
        }
    }

    /**
     * 获取消息阅读状态
     */
    private void getMessageState() {
        RequestApi.getMessage(App.manager.getPhoneNum(), App.manager.getUuid(), new AbstractNetWorkCallback<MsgBean>() {
            @Override
            public void onSuccess(MsgBean msgBean) {
                if (msgBean.getCode() == NO1) {
                    if (HAVE.equals(msgBean.getMsg())) {
                        tvRedPoint.setVisibility(View.VISIBLE);
                    } else if (NOHAVE.equals(msgBean.getMsg())) {
                        tvRedPoint.setVisibility(View.GONE);
                    }
                }
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
        if (requestCode == Constant.REQ_QR_CODE && resultCode == RESULT_OK) {
            Bundle bundle = data.getExtras();
            String scanResult = bundle.getString(Constant.INTENT_EXTRA_KEY_QR_SCAN);
            String[] str;
            if (!TextUtils.isEmpty(scanResult)) {
                str = scanResult.split("&");
            } else {
                ToastUtils.show("二维码信息不全");
                return;
            }
            //0服务 1商品 2、周期卡消费订单   3、次卡消费订单
            final int type = Integer.parseInt(str[0]);
            final String orderId = str[1];
            String code = str[2];
            if (TextUtils.isEmpty(orderId) || TextUtils.isEmpty(code)) {
                ToastUtils.show("二维码信息不全");
                return;
            }
            RequestApi.scanErCode(orderId, code, App.manager.getPhoneNum(), App.manager.getUuid(), new AbstractNetWorkCallback<EditCodeBean>() {
                @Override
                public void onSuccess(EditCodeBean msgBean) {
                    MyLog.e("sss", "—扫描二维码：" + msgBean.toString());
                    ToastUtils.show(msgBean.getMsg());
                    if (msgBean.getCode() == Constant.NO1) {
                        Bundle bundle1 = new Bundle();
                        bundle1.putInt("from", 1);
                        bundle1.putInt("status", 2);
                        if (NO0 == type) {
                            bundle1.putString("orderId", orderId);
                            JumpIntent.jump(ServerTeamPersonPageActivity.this, MyServiceOrderDetailActivity.class, bundle1);
                        } else {
                            bundle1.putString("orderId", Long.toString(msgBean.getContent().getOrderId()));
                            bundle1.putInt("type", msgBean.getContent().getType());
                            JumpIntent.jump(ServerTeamPersonPageActivity.this, CardUnionDetailActivity.class, bundle1);
                        }
                        ServerTeamPersonPageActivity.this.finish();
                    }
                }

                @Override
                public void onError(String errorMsg) {
                    ToastUtils.show(errorMsg);
                }
            });
        }
    }
}
