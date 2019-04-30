package com.yst.onecity.activity.yanzhengma;

import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.gyf.barlibrary.ImmersionBar;
import com.yst.basic.framework.App;
import com.yst.basic.framework.adapter.AbstractCommonAdapter;
import com.yst.basic.framework.base.BaseActivity;
import com.yst.basic.framework.entity.holder.CommonViewHolder;
import com.yst.basic.framework.utils.JumpIntent;
import com.yst.basic.framework.utils.ToastUtils;
import com.yst.basic.framework.utils.Utils;
import com.yst.onecity.Constant;
import com.yst.onecity.R;
import com.yst.onecity.activity.mine.CardUnionDetailActivity;
import com.yst.onecity.activity.mine.order.MyServiceOrderDetailActivity;
import com.yst.onecity.http.AbstractNetWorkCallback;
import com.yst.onecity.http.RequestApi;
import com.yst.onecity.view.dialog.AbstractDeleteDialog;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

import static com.yst.onecity.Constant.NO10;
import static com.yst.onecity.Constant.NO4;

/**
 * 输验证码页面
 *
 * @author wuxiaofang
 * @version 1.1.0
 * @date 2018/5/21
 */

public class EditCodeActivity extends BaseActivity implements ViewTreeObserver.OnGlobalLayoutListener, TextWatcher, AdapterView.OnItemClickListener {


    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.tv_input_code)
    TextView tvInputCode;
    @BindView(R.id.et_code)
    EditText etCode;
    @BindView(R.id.gv_num)
    GridView gvNum;
    @BindView(R.id.tv_zero)
    TextView tvZero;
    @BindView(R.id.tv_sure)
    TextView tvSure;
    @BindView(R.id.ll_container)
    LinearLayout llContainer;
    @BindView(R.id.iv_delete)
    ImageView ivDelete;
    @BindView(R.id.rl_sure)
    RelativeLayout rlSure;

    private List<String> numList = new ArrayList<>();
    private int height;
    private int width;
    private String code = "";
    private String orderId;
    private String warnContent = "";
    /**
     * 0、购买周期卡订单  1、购买次卡订单   2、周期卡消费订单   3、次卡消费订单   4、服务订单
     */
    private int type;


    @Override
    public int getLayoutId() {
        return R.layout.activity_edit_code;
    }

    @Override
    public void initData() {
        ImmersionBar.with(this).fitsSystemWindows(true).statusBarDarkFont(false).statusBarColor(R.color.color_FFED5452).init();
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        Class<EditText> cls = EditText.class;
        Method setSoftInputShownOnFocus;
        try {
            setSoftInputShownOnFocus = cls.getMethod("setShowSoftInputOnFocus", boolean.class);
            setSoftInputShownOnFocus.setAccessible(true);
            setSoftInputShownOnFocus.invoke(etCode, false);
        } catch (Exception e) {
            e.printStackTrace();
        }
        for (int i = 1; i < NO10; i++) {
            numList.add(i + "");
        }
    }

    @Override
    public void setListener() {
        llContainer.getViewTreeObserver().addOnGlobalLayoutListener(this);
        etCode.addTextChangedListener(this);
        gvNum.setOnItemClickListener(this);
    }

    @OnClick({R.id.iv_back, R.id.tv_zero, R.id.iv_delete, R.id.tv_sure})
    public void onBackClick(View view) {
        switch (view.getId()) {
            case R.id.tv_zero:
                if (code.length() < Constant.NO8) {
                    code = code + "0";
                    etCode.setText(code);
                }
                break;
            case R.id.iv_delete:
                if (code.length() > 0) {
                    code = code.substring(0, code.length() - 1);
                    etCode.setText(code);
                }
                break;
            case R.id.iv_back:
                if (Utils.isClickable()) {
                    finish();
                }
                break;
            case R.id.tv_sure:
                String codeContent = etCode.getText().toString().trim();
                if (TextUtils.isEmpty(codeContent)) {
                    ToastUtils.show(getString(R.string.hint_input));
                    return;
                }
                if (codeContent.length() < Constant.NO8) {
                    ToastUtils.show("券码的长度小于8位");
                    return;
                }
                yanZheng();
                break;
            default:
                break;
        }
    }

    /**
     * 弹框
     */
    private void shoDialog() {
        AbstractDeleteDialog dialog = new AbstractDeleteDialog(this) {
            @Override
            public void sureClick() {
                Bundle bundle1 = new Bundle();
                bundle1.putString("orderId", orderId);
                bundle1.putInt("from",1);
                bundle1.putInt("status",2);
                bundle1.putInt("type",type);
                if(type == NO4) {
                    JumpIntent.jump(EditCodeActivity.this, MyServiceOrderDetailActivity.class, bundle1);
                }else {
                    JumpIntent.jump(EditCodeActivity.this, CardUnionDetailActivity.class, bundle1);
                }
                EditCodeActivity.this.finish();
            }
        };
        dialog.setTitleisVisible(true);
        dialog.setTContent("温馨提示");
        dialog.setTitleColor(ContextCompat.getColor(this, R.color.color_FFDF5252));
        dialog.setOneVisible(true);
        dialog.setText("验证成功", "我知道了", "");
        dialog.setButtonColor(ContextCompat.getColor(this, R.color.gray_line), ContextCompat.getColor(this, R.color.gray_line));
        dialog.showDialog();
    }

    /**
     * 验证
     */
    private void yanZheng() {
        RequestApi.yanZhengCode(App.manager.getPhoneNum(), App.manager.getUuid(), code, new AbstractNetWorkCallback<EditCodeBean>() {
            @Override
            public void onSuccess(EditCodeBean bean) {
                if (bean.getCode() == Constant.NO1) {
                    orderId = Long.toString(bean.getContent().getOrderId());
                    type = bean.getContent().getType();
                    shoDialog();
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
    public void onGlobalLayout() {
        llContainer.getViewTreeObserver().removeOnGlobalLayoutListener(this);
        height = llContainer.getHeight();
        width = llContainer.getWidth();
        AbstractCommonAdapter<String> adapter = new AbstractCommonAdapter<String>(EditCodeActivity.this, numList, R.layout.item_code_num_layout) {
            @Override
            public void convert(CommonViewHolder holder, int position, String item) {
                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(width / 3, height / 4);
                holder.getView(R.id.tv_num).setLayoutParams(params);
                holder.setText(R.id.tv_num, position + 1);
            }
        };
        gvNum.setAdapter(adapter);
        LinearLayout.LayoutParams tvZeroParams = new LinearLayout.LayoutParams(width / 3 - 2, height / 4);
        tvZeroParams.setMargins(0, 0, 2, 0);
        tvZero.setLayoutParams(tvZeroParams);
        RelativeLayout.LayoutParams tvSureParams = new RelativeLayout.LayoutParams((width * 2) / 3, height / 4);
        tvSureParams.setMargins((width * 2) / 10, height / 24, 0, height / 24);
        tvSure.setLayoutParams(tvSureParams);
    }

    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        if (charSequence.toString().length() > 0) {
            ivDelete.setVisibility(View.VISIBLE);
        } else {
            ivDelete.setVisibility(View.GONE);
        }
    }

    @Override
    public void afterTextChanged(Editable editable) {

    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        if (code.length() < Constant.NO8) {
            code = code + numList.get(i);
            etCode.setText(code);
        }
    }
}
