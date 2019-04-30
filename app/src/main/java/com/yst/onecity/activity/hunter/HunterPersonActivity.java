package com.yst.onecity.activity.hunter;

import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.yst.basic.framework.App;
import com.yst.basic.framework.base.BaseActivity;
import com.yst.basic.framework.utils.JumpIntent;
import com.yst.basic.framework.utils.ToastUtils;
import com.yst.basic.framework.utils.Utils;
import com.yst.basic.framework.utils.glide.GlideCircleTransform;
import com.yst.basic.framework.view.roundedimageview.RoundedImageView;
import com.yst.onecity.Constant;
import com.yst.onecity.R;
import com.yst.onecity.activity.mine.order.AfterSalesListActivity;
import com.yst.onecity.bean.EventBean;
import com.yst.onecity.bean.HunterBean;
import com.yst.onecity.http.AbstractNetWorkCallback;
import com.yst.onecity.http.RequestApi;
import com.yst.onecity.utils.ConstUtils;

import org.greenrobot.eventbus.EventBus;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * (猎头)个人主页
 *
 * @author LianJinXue
 * @version 1.0.1
 * @date 2018/2/25
 */

public class HunterPersonActivity extends BaseActivity {
    @BindView(R.id.image_head)
    RoundedImageView imageHead;
    @BindView(R.id.txt_hunter_name)
    TextView txtHunterName;
    @BindView(R.id.tv_hunter_more)
    TextView tvHunterMore;
    @BindView(R.id.txt_hunter_my)
    TextView txtHunterMy;
    @BindView(R.id.ll_my_pay)
    LinearLayout llMyPay;
    @BindView(R.id.ll_my_drliver)
    LinearLayout llMyDrliver;
    @BindView(R.id.ll_my_take)
    LinearLayout llMyTake;
    @BindView(R.id.ll_my_appraise)
    LinearLayout llMyAppraise;
    @BindView(R.id.ll_my_after)
    LinearLayout llMyAfter;
    @BindView(R.id.tv_hunter_type)
    ImageView tvHunterType;
    @BindView(R.id.txt_hunter_shopping)
    TextView txtHunterShopping;
    @BindView(R.id.rel_shopping)
    RelativeLayout relShopping;
    @BindView(R.id.tv_main_title)
    TextView tvTitle;
    @BindView(R.id.image_back)
    ImageView imageBack;
    private int type;
    private String personId;
    @Override
    public int getLayoutId() {
        return R.layout.activity_hunter;
    }

    @Override
    public void initData() {
        Constant.isMember = false;
          getDataInfro();

    }

    @OnClick({R.id.image_head, R.id.tv_hunter_more, R.id.txt_hunter_my, R.id.ll_my_pay, R.id.ll_my_drliver, R.id.ll_my_take, R.id.ll_my_appraise, R.id.ll_my_after, R.id.rel_shopping,R.id.image_back})
    public void onClick(View view) {
        if (!Utils.isClickable()) {
            return;
        }
        switch (view.getId()) {
            //头像
            case R.id.image_head:
                break;
            //待售后
            case R.id.ll_my_after:
                Constant.userType = 1;
                JumpIntent.jump(HunterPersonActivity.this, AfterSalesListActivity.class);
                break;
            //商品管理
            case R.id.rel_shopping:
                JumpIntent.jump(this, GoodsManageActivity.class);
                break;
            case R.id.image_back:
                finish();
                EventBus.getDefault().post(new EventBean("refresh"));
                break;
            default:
                break;
        }
    }

    /**
     * 获取个人信息
     *
     * @version 1.0.1
     */
    public void getDataInfro() {
        RequestApi.getHunterInfro(App.manager.getPhoneNum(), App.manager.getUuid(), new AbstractNetWorkCallback<HunterBean>() {
            @Override
            public void onSuccess(HunterBean hunterBean) {
                if (hunterBean.getCode() == 1) {
                    if (hunterBean.getContent()!=null){
                        txtHunterName.setText(ConstUtils.getStringNoEmpty(hunterBean.getContent().getUsername()));
                        Glide.with(HunterPersonActivity.this)
                                .load(hunterBean.getContent().getHeadImg())
                                .bitmapTransform(new GlideCircleTransform(HunterPersonActivity.this)).crossFade(1000)
                                .placeholder(R.mipmap.default_nor_avatar)
                                .into(imageHead);
                        ToastUtils.show(hunterBean.getMsg());
                    }else {
                        ToastUtils.show(hunterBean.getMsg());
                    }
                }else {
                    ToastUtils.show(hunterBean.getMsg());
                }

            }

            @Override
            public void onError(String errorMsg) {
                ToastUtils.show(errorMsg);
            }
        });

    }
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            finish();
            EventBus.getDefault().post(new EventBean("refresh"));
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
