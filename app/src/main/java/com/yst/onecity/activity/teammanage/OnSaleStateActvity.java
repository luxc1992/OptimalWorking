package com.yst.onecity.activity.teammanage;

import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.yst.basic.framework.base.BaseActivity;
import com.yst.basic.framework.utils.MyLog;
import com.yst.onecity.Constant;
import com.yst.onecity.R;

import butterknife.BindView;


/**
 * 营业状态页面
 *
 * @author wuxiaofang
 * @version 1.1.0
 * @date 2018/5/18
 */

public class OnSaleStateActvity extends BaseActivity implements View.OnClickListener {

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
    @BindView(R.id.rbtn_sale)
    RadioButton rbtnSale;
    @BindView(R.id.rbtn_pause_sale)
    RadioButton rbtnPauseSale;
    @BindView(R.id.rbtn_close_door)
    RadioButton rbtnCloseDoor;
    @BindView(R.id.radio_group)
    RadioGroup radioGroup;

    @Override
    public int getLayoutId() {
        return R.layout.activity_sale_state;
    }

    @Override
    public void initData() {
        initTitleBar(getString(R.string.sale_state));
        Intent intent = getIntent();
        if (null != intent && null != intent.getExtras()) {
            String type = intent.getExtras().getString("type");
            MyLog.e("sss", "type: " + type);
            if (TextUtils.isEmpty(type)) {
                return;
            }
            switch (type) {
                case Constant.ON_SALE:
                    rbtnSale.setChecked(true);
                    rbtnPauseSale.setChecked(false);
                    rbtnCloseDoor.setChecked(false);
                    break;
                case Constant.PAUSE_SALE:
                    rbtnSale.setChecked(false);
                    rbtnPauseSale.setChecked(true);
                    rbtnCloseDoor.setChecked(false);
                    break;
                case Constant.CLOSE_DOOR:
                    rbtnSale.setChecked(false);
                    rbtnPauseSale.setChecked(false);
                    rbtnCloseDoor.setChecked(true);
                    break;
                default:
                    break;
            }
        }
        rbtnSale.setOnClickListener(this);
        rbtnPauseSale.setOnClickListener(this);
        rbtnCloseDoor.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        Intent huiDiaointent = new Intent();
        switch (view.getId()) {
            case R.id.rbtn_sale:
                huiDiaointent.putExtra("type", Constant.NO0);
                setResult(Constant.NO1, huiDiaointent);
                finish();
                break;
            case R.id.rbtn_pause_sale:
                huiDiaointent.putExtra("type", Constant.NO1);
                setResult(Constant.NO1, huiDiaointent);
                finish();
                break;
            case R.id.rbtn_close_door:
                huiDiaointent.putExtra("type", Constant.NO2);
                setResult(Constant.NO1, huiDiaointent);
                finish();
                break;
            default:
                break;
        }
    }
}
