package com.yst.onecity.activity.mine;

import android.view.View;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.yst.basic.framework.App;
import com.yst.basic.framework.base.BaseActivity;
import com.yst.basic.framework.utils.ToastUtils;
import com.yst.basic.framework.view.roundedimageview.RoundedImageView;
import com.yst.onecity.R;
import com.yst.onecity.bean.CardUnionUsedBean;
import com.yst.onecity.http.AbstractNetWorkCallback;
import com.yst.onecity.http.RequestApi;
import com.yst.onecity.utils.ConstUtils;
import butterknife.BindView;
import static com.yst.onecity.Constant.NO1;
import static com.yst.onecity.Constant.NO2;
import static com.yst.onecity.Constant.NO3;

/**
 * 卡联盟详情
 *
 * @author luxuchang
 * @version 1.1.0
 * @date 2018/5/18
 */
public class CardUnionDetailActivity extends BaseActivity {
    @BindView(R.id.riv_head)
    RoundedImageView rivHead;
    @BindView(R.id.tv_name)
    TextView tvName;
    @BindView(R.id.tv_phone)
    TextView tvPhone;
    @BindView(R.id.card_name)
    TextView cardName;
    @BindView(R.id.tv_address)
    TextView tvAddress;
    @BindView(R.id.tv_detail_address)
    TextView tvDetailAddress;
    @BindView(R.id.view_line)
    View viewLine;
    @BindView(R.id.tv_residue_num)
    TextView tvResidueNum;
    @BindView(R.id.tv_time)
    TextView tvTime;

    private int type;
    private String id;

    @Override
    public int getLayoutId() {
        return R.layout.activity_card_union_detail;
    }

    @Override
    public void initData() {
        initTitleBar("卡联盟");
        type = getIntent().getIntExtra("type", 0);
        id = getIntent().getStringExtra("orderId");

        requestData();

        if (type == NO2) {
            cardName.setText("周期卡");
            tvResidueNum.setVisibility(View.GONE);
        } else if (type == NO3) {
            cardName.setText("次数卡");
            tvResidueNum.setVisibility(View.VISIBLE);
        }
    }

    /**
     * 请求数据
     */
    private void requestData() {
        RequestApi.getCardTypeList(App.manager.getPhoneNum(), App.manager.getUuid(), String.valueOf(App.manager.getId()), id, new AbstractNetWorkCallback<CardUnionUsedBean>() {
            @Override
            public void onBefore() {
                super.onBefore();
                showInfoProgressDialog();
            }

            @Override
            public void onAfter() {
                super.onAfter();
                dismissInfoProgressDialog();
            }

            @Override
            public void onSuccess(CardUnionUsedBean cardUnionUsedBean) {
                if (cardUnionUsedBean.getCode() == NO1) {
                    if (cardUnionUsedBean.getContent().size() == 0 || cardUnionUsedBean.getContent().get(0).getServiceOrderVOList() == null || cardUnionUsedBean.getContent().get(0).getServiceOrderVOList().size() == 0) {
                        ToastUtils.show("数据异常");
                        return;
                    }
                    Glide.with(context).load(cardUnionUsedBean.getContent().get(0).getServiceOrderVOList().get(0).getLogoAttachmentAddress()).error(R.mipmap.default_head).into(rivHead);
                    tvName.setText(cardUnionUsedBean.getContent().get(0).getServiceOrderVOList().get(0).getNickName());
                    tvPhone.setText(cardUnionUsedBean.getContent().get(0).getServiceOrderVOList().get(0).getPhone());
                    tvAddress.setText("店铺地址: " + cardUnionUsedBean.getContent().get(0).getServiceOrderVOList().get(0).getAdvisorName());
                    tvDetailAddress.setText(cardUnionUsedBean.getContent().get(0).getServiceOrderVOList().get(0).getDetailAdd());
                    tvResidueNum.setText(ConstUtils.changeEmptyStringToZero(cardUnionUsedBean.getContent().get(0).getServiceOrderVOList().get(0).getUsedNum() + "") + "/" + cardUnionUsedBean.getContent().get(0).getServiceOrderVOList().get(0).getNum());
                    tvTime.setText(cardUnionUsedBean.getContent().get(0).getServiceOrderVOList().get(0).getDetailTime());
                }

            }

            @Override
            public void onError(String errorMsg) {
                ToastUtils.show(errorMsg);
            }
        });
    }
    /**/

}
