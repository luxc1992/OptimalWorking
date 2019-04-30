package com.yst.onecity.fragment;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.yst.basic.framework.App;
import com.yst.basic.framework.base.BaseFragment;
import com.yst.basic.framework.utils.JumpIntent;
import com.yst.basic.framework.utils.ToastUtils;
import com.yst.basic.framework.view.roundedimageview.RoundedImageView;
import com.yst.onecity.Constant;
import com.yst.onecity.H5Const;
import com.yst.onecity.R;
import com.yst.onecity.activity.ApplyHunterActivity;
import com.yst.onecity.activity.agent.AgentCenterActivity;
import com.yst.onecity.activity.h5detail.ServerTeamPageDetailActivity;
import com.yst.onecity.activity.home.PlatformInformActivity;
import com.yst.onecity.activity.login.LoginActivity;
import com.yst.onecity.activity.login.RegisterActivity;
import com.yst.onecity.activity.mine.AttentionActivity;
import com.yst.onecity.activity.mine.FansActivity;
import com.yst.onecity.activity.mine.HuntingBeansActivity;
import com.yst.onecity.activity.mine.MyCollectActivity;
import com.yst.onecity.activity.mine.MyInComeActivity;
import com.yst.onecity.activity.mine.MyZxingActivity;
import com.yst.onecity.activity.mine.cardbag.CardBagListActivity;
import com.yst.onecity.activity.mine.order.MyOrderActivity;
import com.yst.onecity.activity.mine.setting.PersonDetailActivity;
import com.yst.onecity.activity.mine.setting.SettingActivity;
import com.yst.onecity.activity.serverteam.ServerTeamPersonPageActivity;
import com.yst.onecity.activity.teammanage.CreateTeamActivity;
import com.yst.onecity.activity.teammanage.TeamManageActivity;
import com.yst.onecity.bean.EventBean;
import com.yst.onecity.bean.SignInBean;
import com.yst.onecity.bean.mine.PersonalCenterBean;
import com.yst.onecity.http.AbstractNetWorkCallback;
import com.yst.onecity.http.RequestApi;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import butterknife.BindView;
import butterknife.OnClick;

import static com.yst.onecity.Constant.NO0;
import static com.yst.onecity.Constant.NO1;
import static com.yst.onecity.Constant.NO2;
import static com.yst.onecity.Constant.NO3;

/**
 * 我的
 *
 * @author chenxiaowei
 * @version 1.0.1
 * @date 2018/5/15
 */
public class MineFragment extends BaseFragment {
    @BindView(R.id.btn_already_signin)
    TextView alreadySignin;
    @BindView(R.id.mine_signin_line)
    LinearLayout signin;
    @BindView(R.id.mine_name_text)
    TextView tvName;
    @BindView(R.id.line_login)
    LinearLayout lineLogin;
    @BindView(R.id.mine_head_img)
    RoundedImageView imgHead;
    @BindView(R.id.tv_mine_income)
    TextView tvIncome;
    @BindView(R.id.tv_mine_bean)
    TextView tvBean;
    @BindView(R.id.mine_inform_red)
    ImageView imgRedPoint;
    /**
     * 是否签到（0否1是）
     */
    private int signStatus;
    private String advisorName;
    private int advisorId;
    private int isHunter;
    /**
     * 发布的服务团队是否审核通过
     */
    private int examineStatus;

    @Override
    public int getLayoutId() {
        return R.layout.fragment_mine;
    }

    @Override
    public void init() {
        EventBus.getDefault().register(this);
    }

    @OnClick({R.id.mine_serveteam_text, R.id.mine_inform_img, R.id.mine_head_img,
            R.id.mine_signin_line, R.id.mine_login_text, R.id.mine_register_text,
            R.id.mine_income_line, R.id.mine_pubean_line, R.id.mine_collect_line,
            R.id.mine_attention_line, R.id.mine_fans_line, R.id.mine_orderfrom_line,
            R.id.mine_zxing_line, R.id.mine_set_line, R.id.mine_cardbag_line,
            R.id.mine_broker_line, R.id.mine_shoppingcar_img, R.id.btn_already_signin})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            //服务团队
            case R.id.mine_serveteam_text:
                if (App.manager.getLoginState()) {
                    // 0待审核，1审核通过 2 审核失败  3没服务团队
                    if (examineStatus == NO1) {
                        Bundle bundle = new Bundle();
                        bundle.putString("advisorName", advisorName);
                        bundle.putString("advisorId", String.valueOf(advisorId));
                        JumpIntent.jump(getActivity(), ServerTeamPersonPageActivity.class, bundle);
                    } else if (examineStatus == NO0) {
                        ToastUtils.show("您的团队正在审核，请耐心等待");
                    } else if (examineStatus == NO2) {
                        ToastUtils.show("您的团队审核失败请重新创建");
                        JumpIntent.jump(getActivity(), CreateTeamActivity.class);
                    } else if (examineStatus == NO3) {
                        JumpIntent.jump(getActivity(), CreateTeamActivity.class);
                    }
                } else {
                    goLogin();
                }
                break;
            //通知
            case R.id.mine_inform_img:
                if (App.manager.getLoginState()) {
                    JumpIntent.jump(getActivity(), PlatformInformActivity.class);
                } else {
                    goLogin();
                }
                break;
            //头像
            case R.id.mine_head_img:
                if (App.manager.getLoginState()) {
                    // 是否团队管理 0不是 1是
                    if (examineStatus == 1) {
                        Bundle bundle = new Bundle();
                        bundle.putInt("advisorId", advisorId);
                        JumpIntent.jump(getActivity(), TeamManageActivity.class, bundle);
                    } else {
                        Bundle bundle = new Bundle();
                        bundle.putInt("type", NO1);
                        JumpIntent.jump(getActivity(), PersonDetailActivity.class, bundle);
                    }
                } else {
                    goLogin();
                }
                break;
            //签到
            case R.id.mine_signin_line:
                if (App.manager.getLoginState()) {
                    isSignin();
                } else {
                    goLogin();
                }
                break;
            //已经签到
            case R.id.btn_already_signin:
                if (App.manager.getLoginState()) {
                    isSignin();
                } else {
                    goLogin();
                }
                break;
            //登录
            case R.id.mine_login_text:
                JumpIntent.jump(getActivity(), LoginActivity.class);
                break;
            //注册
            case R.id.mine_register_text:
                JumpIntent.jump(getActivity(), RegisterActivity.class);
                break;
            //收入
            case R.id.mine_income_line:
                if (App.manager.getLoginState()) {
                    JumpIntent.jump(getActivity(), MyInComeActivity.class);
                } else {
                    goLogin();
                }
                break;
            //普豆  优币
            case R.id.mine_pubean_line:
                if (App.manager.getLoginState()) {
                    JumpIntent.jump(getActivity(), HuntingBeansActivity.class);
                } else {
                    goLogin();
                }
                break;
            //收藏
            case R.id.mine_collect_line:
                if (App.manager.getLoginState()) {
                    JumpIntent.jump(getActivity(), MyCollectActivity.class);
                } else {
                    goLogin();
                }
                break;
            //关注
            case R.id.mine_attention_line:
                if (App.manager.getLoginState()) {
                    JumpIntent.jump(getActivity(), AttentionActivity.class);
                } else {
                    goLogin();
                }
                break;
            //粉丝
            case R.id.mine_fans_line:
                if (App.manager.getLoginState()) {
                    JumpIntent.jump(getActivity(), FansActivity.class);
                } else {
                    goLogin();
                }
                break;
            //订单
            case R.id.mine_orderfrom_line:
                if (App.manager.getLoginState()) {
                    JumpIntent.jump(getActivity(), MyOrderActivity.class);
                } else {
                    goLogin();
                }
                break;
            //二维码
            case R.id.mine_zxing_line:
                if (App.manager.getLoginState()) {
                    JumpIntent.jump(getActivity(), MyZxingActivity.class);
                } else {
                    goLogin();
                }
                break;
            //设置
            case R.id.mine_set_line:
                if (App.manager.getLoginState()) {
                    Bundle bundle=new Bundle();
                    bundle.putInt("examineStatus",examineStatus);
                    bundle.putInt("advisorId", advisorId);
                    JumpIntent.jump(getActivity(), SettingActivity.class,bundle);
                } else {
                    goLogin();
                }
                break;
            //卡包
            case R.id.mine_cardbag_line:
                if (App.manager.getLoginState()) {
                    JumpIntent.jump(getActivity(), CardBagListActivity.class);
                } else {
                    goLogin();
                }
                break;
            //经纪人 0不是 0是
            case R.id.mine_broker_line:
                if (App.manager.getLoginState()) {
                    if (isHunter == 0) {
                        JumpIntent.jump(getActivity(), ApplyHunterActivity.class);
                    } else {
                        JumpIntent.jump(getActivity(), AgentCenterActivity.class);
                    }
                } else {
                    goLogin();
                }
                break;
            //购物车
            case R.id.mine_shoppingcar_img:
                if (App.manager.getLoginState()) {
                    Bundle bundle = new Bundle();
                    bundle.putString("isCart", "isCart");
                    bundle.putString("url", H5Const.SHOP_CART);
                    JumpIntent.jump(getActivity(), ServerTeamPageDetailActivity.class, bundle);
                } else {
                    goLogin();
                }
                break;
            default:
                break;
        }
    }

    /**
     * 个人中心
     */
    private void initData() {
        RequestApi.getPersonalCenter(String.valueOf(App.manager.getId()), App.manager.getUuid(), App.manager.getPhoneNum(), new AbstractNetWorkCallback<PersonalCenterBean>() {
            @Override
            public void onSuccess(PersonalCenterBean persondetailBean) {
                if (persondetailBean.getCode() == 1) {
                    String nickname = persondetailBean.getContent().getNickname();
                    tvName.setText("昵称：" + nickname);
                    tvIncome.setText(persondetailBean.getContent().getScore() + "");
                    tvBean.setText(persondetailBean.getContent().getAfterMoney() + "");
                    advisorName = persondetailBean.getContent().getAdvisorName();
                    advisorId = persondetailBean.getContent().getAdvisorId();
                    isHunter = persondetailBean.getContent().getIsHunter();
                    examineStatus = persondetailBean.getContent().getExamineStatus();
                    Glide.with(getActivity())
                            .load(persondetailBean.getContent().getLogoAttachmentAddress())
                            .error(R.mipmap.default_head)
                            .into(imgHead);
                    signStatus = persondetailBean.getContent().getSignStatus();
                    if (signStatus == 0) {
                        signin.setVisibility(View.VISIBLE);
                        alreadySignin.setVisibility(View.GONE);
                    } else {
                        signin.setVisibility(View.GONE);
                        alreadySignin.setVisibility(View.VISIBLE);
                        alreadySignin.setText("已签到" + persondetailBean.getContent().getSignCount() + "天");
                    }

                } else {
                    tvName.setVisibility(View.GONE);
                    lineLogin.setVisibility(View.VISIBLE);
                    Glide.with(getActivity()).load(R.mipmap.default_head).into(imgHead);
                    signin.setVisibility(View.VISIBLE);
                    alreadySignin.setVisibility(View.GONE);
                    ToastUtils.show(persondetailBean.getMsg());
                    App.manager.setLoginState(false);
                }

            }

            @Override
            public void onError(String errorMsg) {
                ToastUtils.show(errorMsg);
            }
        });
    }

    /**
     * 签到
     */
    private void isSignin() {
        RequestApi.signin(String.valueOf(App.manager.getId()), App.manager.getUuid(), App.manager.getPhoneNum(), new AbstractNetWorkCallback<SignInBean>() {
            @Override
            public void onSuccess(SignInBean bean) {
                if (bean.getCode() == 1) {
                    ToastUtils.show("连续签到" + bean.getContent().getContinuitySign() + "天 +" + bean.getContent().getNewScoreCount() + "优币");
                    signin.setVisibility(View.GONE);
                    alreadySignin.setVisibility(View.VISIBLE);
                    alreadySignin.setText("已签到" + bean.getContent().getContinuitySign() + "天");
                    initData();
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

    @Override
    public void onResume() {
        super.onResume();
        isLogin();
    }

    /**
     * 跳转未登录去登录
     */
    private void goLogin() {
        JumpIntent.jump(getActivity(), LoginActivity.class);
    }

    /**
     * 判断是否登录
     */
    private void isLogin() {
        if (App.manager.getLoginState()) {
            tvName.setVisibility(View.VISIBLE);
            lineLogin.setVisibility(View.GONE);
            initData();
        } else {
            tvName.setVisibility(View.GONE);
            lineLogin.setVisibility(View.VISIBLE);
            Glide.with(getActivity()).load(R.mipmap.default_head).into(imgHead);
            signin.setVisibility(View.VISIBLE);
            alreadySignin.setVisibility(View.GONE);
            tvIncome.setText("0.00");
            tvBean.setText("0");
            imgRedPoint.setVisibility(View.GONE);
        }
    }

    /**
     * 消息通知
     *
     * @param event
     */
    @Subscribe
    public void onEventMainThread(EventBean event) {
        if (event.getFlag() == 0) {
            if (Constant.HAVE.equals(event.getMsg())) {
                imgRedPoint.setVisibility(View.VISIBLE);
            } else if (Constant.NOHAVE.equals(event.getMsg())) {
                imgRedPoint.setVisibility(View.GONE);
            }
        } else {
            if (Constant.REFRESH.equals(event.getMsg())) {
                isLogin();
            }
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        EventBus.getDefault().unregister(this);
    }
}
